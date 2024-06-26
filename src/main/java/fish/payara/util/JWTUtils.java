package fish.payara.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;


public class JWTUtils {
    private static final String SECRET_KEY;

    static {
        final SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[32]; // 256 bits
        secureRandom.nextBytes(bytes);
        SECRET_KEY = Base64.getEncoder().encodeToString(bytes);
    }

    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 1 day

    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuer("payara")
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static Claims decodeToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
