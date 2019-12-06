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

/**
 * Class for generating, validating and refreshing validation JSON Web Tokens.
 */
public class JWTProvider {

    // This is the algorithm we use for signing the JSON Web Token
    private static final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS256;
    // We use this service to retrieve users
    private final UserDetailsService myUserDetails;
    // When we sign a JSON Web Token, we use a secret key so nobody can re-sign an edited token and present it as valid
    private String secretKey;
    // For how long do we want a token to stay valid?
    private long validityInMilliseconds = 600000; // 10 minutes


    public JWTProvider(UserDetailsService myUserDetails, String secretKey) {
        this.myUserDetails = myUserDetails;
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /**
     * We're creating a token for a given username.
     *
     * @param username The username to create a token for
     * @return A JSON Web Token, signed with a secret key.
     */
    public String createToken(String username) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + validityInMilliseconds);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(ALGORITHM, secretKey)
                .compact();
    }

    /**
     * Based on the token, we can retrieve the user from the database.
     * First we parse the token and extract the username. We then use this to query the {@link UserDetailsService}.
     *
     * @param tokenString The token corresponding to a user
     * @return The {@link Authentication} object corresponding to the requested user.
     */
    public Authentication getAuthentication(String tokenString) {
        Claims claims = getClaims(tokenString);
        String user = claims.getSubject();
        UserDetails userDetails = myUserDetails.loadUserByUsername(user);
        return new UsernamePasswordAuthenticationToken(userDetails, "",
                userDetails.getAuthorities());
    }

    /**
     * Check whether the token is about to expire, if yes, return a new one.
     *
     * @param tokenString The token
     * @return null if the token is not close to expiring, a new one if it is.
     */
    public String getRefreshToken(String tokenString) {
        Claims claims = getClaims(tokenString);
        String user = claims.getSubject();
        Date expiration = claims.getExpiration();
        if (new Date(new Date().getTime() + validityInMilliseconds / 10).after(expiration)) {
            return createToken(user);
        }
        return null;
    }

    /**
     * Extract the token from the HTTP request.
     * This is done by looking at the 'Authorization' request header.
     *
     * @param req The HTTP Request
     * @return The header, if one is found.
     */
    public String getToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * Validate the token. This is done by simply parsing it and then checking whether the used algorithm
     * is the one we used.
     * The parsing will fail and throw an exception if the token has expired.
     *
     * @param tokenString The token
     * @return True if the token is valid, false if not.
     */
    public boolean validateToken(String tokenString) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(tokenString);
        return SignatureAlgorithm.forName(claims.getHeader().getAlgorithm()) == ALGORITHM;
    }

    /**
     * Parse the JSON Web Token and return the claims from it.
     *
     * @param tokenString The token
     * @return The claims
     */
    private Claims getClaims(String tokenString) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(tokenString);
        return claims.getBody();
    }
}
