package stenden.spring.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

public class JWTProvider {

    private static final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS256;
    private final UserDetailsService myUserDetails;
    private String secretKey;
    private long validityInMilliseconds = 600000; // 1minute

    public JWTProvider(UserDetailsService myUserDetails, String secretKey) {
        this.myUserDetails = myUserDetails;
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + validityInMilliseconds);
        return Jwts.builder()//
                .setSubject(username)//
                .setIssuedAt(now)//
                .setExpiration(expiration)//
                .signWith(ALGORITHM, secretKey)//
                .compact();
    }

    public Authentication getAuthentication(String tokenString) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(tokenString);
        String user = claims.getBody().getSubject();
        UserDetails userDetails = myUserDetails.loadUserByUsername(user);
        return new UsernamePasswordAuthenticationToken(userDetails, "",
                userDetails.getAuthorities());
    }

    public String getRefreshToken(String tokenString) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(tokenString);
        String user = claims.getBody().getSubject();
        Date expiration = claims.getBody().getExpiration();
        if (new Date(new Date().getTime() + validityInMilliseconds / 10).after(expiration)) {
            return createToken(user);
        }
        return null;
    }

    public String getToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        return SignatureAlgorithm.forName(claims.getHeader().getAlgorithm()) == ALGORITHM;
    }
}
