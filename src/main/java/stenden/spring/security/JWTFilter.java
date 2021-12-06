package stenden.spring.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * We use this filter to read and verify the JWT in the incoming HTTP Request.
 */
public class JWTFilter extends GenericFilterBean {

    // We use the JWTProvider to verify tokens on incoming requests
    private final JWTProvider jwtProvider;

    public JWTFilter(JWTProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    /**
     * Check whether the filter has a valid JSON Web Token.
     * If so, we place an Authentication object in the {@link SecurityContextHolder}.
     * If there is no valid JWT, the {@link SecurityContextHolder} stays empty.
     *
     * @param request  The HTTP Request that triggered this whole chain
     * @param response The response we'll be returning to the client
     * @param chain    The filter chain.
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // Get the token from the request
        String token = jwtProvider.getToken((HttpServletRequest) request);
        try {
            // If there was a token, and it is valid (e.g.: not expired)
            if (token != null && jwtProvider.validateToken(token)) {
                // Get the authentication instance and set it in the SecurityContext
                Authentication authentication = jwtProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // Check if the token is about to expire and we need to generate a fresh one
                String newToken = jwtProvider.getRefreshToken(token);
                if (newToken != null) {
                    // In that case we will return it on a different header
                    ((HttpServletResponse) response).addHeader("jwt-new-token", newToken);
                }
            }
        } catch (Exception e) {
            // Be sure to clear everything if something when wrong
            SecurityContextHolder.clearContext();
        }

        // Let the filter chain go on, authentication failure should not stop the filter.
        // It's up to Spring to decide whether the authentication in hte SecurityHolder is sufficient.
        chain.doFilter(request, response);
    }

}
