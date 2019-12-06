package stenden.spring.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import stenden.spring.security.*;

import javax.sql.DataSource;

/**
 * The annotation @{@link EnableWebSecurity} triggers Spring to load the {@link WebSecurityConfiguration},
 * which for one thing exports the springSecurityFilterChainBean, which the filter registered by {@link SecurityConfig}
 * delegates to.
 * That filter looks for classes implementing {@link WebSecurityConfigurer}.
 * {@link WebSecurityConfigurerAdapter} is a subclass of {@link WebSecurityConfigurer} and thus this class is as well.
 * Then this class gets used for configuring the security of your application.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    @Autowired
    private RestAccessDeniedHandler restAccessDeniedHandler;
    @Autowired
    private JWTProvider jwtProvider;

    /**
     * Here we define what we want to use for password encoding.
     *
     * @return The desired encoder implementation.
     */
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * To expose an UserDetailService in our application,
     * we can just override this method from the superclass.
     * <p>
     * We can also create our own UserDetailService if we so desire,
     * to customize how users are retrieved!
     *
     * @return The UserDetailService
     * @throws Exception
     */
    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }

    /**
     * We create the JWTProvider bean, which
     *
     * @param userDetailsService
     * @param secretKey
     * @return
     */
    @Bean
    public JWTProvider jwtProvider(UserDetailsService userDetailsService, @Value("secret.key") String secretKey) {
        return new JWTProvider(userDetailsService, secretKey);
    }

    /**
     * We're configuring the {@link AuthenticationManagerBuilder} to tell it where to get the users from.
     * By default Spring expects certain tables to be present if you simpy use {@link AuthenticationManagerBuilder#jdbcAuthentication()}
     * but you can customize that by giving your own queries, as shown in the method.
     * You still need to make sure the query returns the right columns.
     * You can also implement your own {@link UserDetailsService} where you'll retrieve users instead.
     *
     * @param auth
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        JdbcUserDetailsManager userDetailsService = auth.jdbcAuthentication()
                .dataSource(dataSource)
                .getUserDetailsService();

        // Spring requires USERNAME, PASSWORD and ENABLED
        userDetailsService
                .setUsersByUsernameQuery("SELECT USERNAME, PASSWORD, ENABLED FROM USERS WHERE USERNAME = ?");

        // Here Spring requires USERNAME and ROLE
        userDetailsService
                .setAuthoritiesByUsernameQuery("SELECT USERNAME, ROLE FROM USERS WHERE USERNAME = ?");
    }

    /**
     * In this method we get a {@link HttpSecurity} object we can use for configuring the security in our application.
     * <p>
     * It's important to know that the top most configuration should be more specific than the bottom configuration.
     * If I say the url /admin is accessible by admins only and then say all the URLS are open for everyone,
     * that is interpreted as "/admin is secured, but everything else is open for business".
     * However, if I were to do that the other way around, my rule for /admin would be ignored.
     *
     * @param http The object we configure our security in
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // First we configure it to allow authentication and authorization in REST
        enableRESTAuthentication(http)
                // Now let's say which requests we want to authorize
                .authorizeRequests()
                // Every single request needs to be authorized... with the exception of /login,
                // which was defined in enableRESTAuthentication(), which was earlier and thus trumps this configuration.
                .anyRequest()
                // We require every user to have the role USER (this translates to 'ROLE_USER' in the database
                // and in other places)
                .hasRole("USER")
                // Alright, we're done, let's move on to the next part using and()
                .and()
                // We're disabling defenses against Cross-Site Request Forgery,
                // as the browser is not responsible for adding authentication information to the request
                // which is wat the CSRF exploit relies on.
                .csrf()
                .disable();
    }

    /**
     * I separated the configuration for security in REST to simplify it.
     * In this method we enable logging in and configure it for a REST API.
     *
     * @param http The {@link HttpSecurity} object we can use for configuration
     * @return
     * @throws Exception
     */
    private HttpSecurity enableRESTAuthentication(HttpSecurity http) throws Exception {
        http
                // We'll allow logging in as if it were a form, using form headers
                .formLogin()
                // Everyone is allowed to reach this endpoint
                .permitAll()
                // If login fails, return a 401 instead of redirecting, as is normal for form login
                .failureHandler(new HTTPStatusHandler(HttpStatus.UNAUTHORIZED))
                // If login succeeds return a 200 instead of redirecting, as is normal for form login
                // We also return a JSON Web Token
                .successHandler(new JWTStatusHandler(jwtProvider))
                // Configuring the HttpSecurity object is done in steps, we just configured the formLogin,
                // now we're continuing to exception handling. To signal this, we need to use and(),
                // otherwise we'd still be trying to configure formLogin.
                .and()
                // What to do when it goes wrong?
                .exceptionHandling()
                // When the access is denied to a certain part of the application, use the given handler.
                .accessDeniedHandler(restAccessDeniedHandler)
                // When the authentication fails, use the given handler.
                .authenticationEntryPoint(restAuthenticationEntryPoint);

        // We need to register our JWTFilter. We register it before the UsernamePasswordAuthenticationFilter,
        // as that is part of the group of filters where Spring expects updates to the SecurityContextHolder
        http.addFilterBefore(new JWTFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);

        // As it's a REST API, we don't want Spring remembering sessions for users. It should be stateless.
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // We return it so we can chain more configuration
        return http;
    }


}







