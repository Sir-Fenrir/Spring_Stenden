package stenden.spring.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import stenden.spring.security.*;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    @Autowired
    private RestAccessDeniedHandler restAccessDeniedHandler;
    @Autowired
    private JWTProvider jwtProvider;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }

    @Bean
    public JWTProvider jwtProvider(UserDetailsService userDetailsService, @Value("secret.key") String secretKey) {
        return new JWTProvider(userDetailsService, secretKey);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        JdbcUserDetailsManager userDetailsService = auth.jdbcAuthentication()
                .dataSource(dataSource)
                .getUserDetailsService();

        userDetailsService
                .setUsersByUsernameQuery("SELECT USERNAME, PASSWORD, ENABLED FROM USERS WHERE USERNAME = ?");

        userDetailsService
                .setAuthoritiesByUsernameQuery("SELECT USERNAME, ROLE FROM USERS WHERE USERNAME = ?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        enableRESTAuthentication(http)
                .authorizeRequests()
                .anyRequest()
                .hasRole("USER")
                .and()
                .csrf().disable();
    }

    private HttpSecurity enableRESTAuthentication(HttpSecurity http) throws Exception {
        http
                .formLogin().permitAll()
                // If login fails, return 401
                .failureHandler(new HTTPStatusHandler(HttpStatus.UNAUTHORIZED))
                // If login succeeds return 200
                .successHandler(new JWTStatusHandler(jwtProvider))
                .and()
                .exceptionHandling()
                .accessDeniedHandler(restAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .httpBasic();

        http.addFilterBefore(new JWTFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http;
    }


}
