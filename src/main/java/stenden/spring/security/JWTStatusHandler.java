package stenden.spring.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JWTStatusHandler implements AuthenticationSuccessHandler {

    private final JWTProvider jwtProvider;

    public JWTStatusHandler(JWTProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        String token = jwtProvider.createToken(authentication.getName());
        response.addHeader("jwt-token", token);
        response.setStatus(HttpStatus.OK.value());
    }

}
