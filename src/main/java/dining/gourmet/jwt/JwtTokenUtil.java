package dining.gourmet.jwt;
import dining.gourmet.security.SecurityConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

public class JwtTokenUtil {
    String SECRET_KEY = "hello";
    public static String createToken(String loginId, String key, long exp) {
        Claims claims = Jwts.claims();
        claims.put("loginId", loginId);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis())) // 발행
                .setExpiration(new Date(System.currentTimeMillis())) // 만료
                .compact();
    }

    public boolean validateToken(String token, String username) {
        try {
            Claims claims = extractAllClaims(token);

            String tokenUsername = claims.getSubject();
            if(!tokenUsername.equals(username)) {
                return false;
            }

            Date expiration = claims.getExpiration();
            return !expiration.before(new Date());
        } catch(Exception e) {
            return false;
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimdistract) {
        final Claims claims = extractAllClaims(token);
        return claimdistract.apply(claims);
    }

    public String extractUser(String token) {
        return extractClaim(token, Claims::getSubject);
    }
}
