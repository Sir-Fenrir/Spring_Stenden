package stenden.spring.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HTTPStatusHandler implements AuthenticationFailureHandler {

    private HttpStatus status;

    public HTTPStatusHandler(HttpStatus status) {
        this.status = status;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) {
        response.setStatus(status.value());
    }

}
