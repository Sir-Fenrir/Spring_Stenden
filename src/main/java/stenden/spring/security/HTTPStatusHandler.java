package stenden.spring.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HTTPStatusHandler implements AuthenticationFailureHandler {

    private HttpStatus status;

    public HTTPStatusHandler(HttpStatus status) {
        this.status = status;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        response.setStatus(status.value());
        response.getWriter().write("{\"message\":\"Invalid credentials.\"}");
    }

}
