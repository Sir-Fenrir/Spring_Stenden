package stenden.spring.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This is used when the authentication of an user succeeds.
 */
public class JWTStatusHandler implements AuthenticationSuccessHandler {

    // The JWTProvider for creating JSON Web Tokens
    private final JWTProvider jwtProvider;

    public JWTStatusHandler(JWTProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    /**
     * On a successful authentication attempt, we'll return a 200 and a JSON Web Token.
     *
     * @param request        The request that triggered the authentication
     * @param response       The response that is going back to the client. We can write to this object.
     * @param authentication The Authentication object, representing the successful authentication
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        String token = jwtProvider.createToken(authentication.getName());
        response.addHeader("jwt-token", token);
        response.setStatus(HttpStatus.OK.value());
    }

}
