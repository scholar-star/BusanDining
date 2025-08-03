package dining.gourmet.jwt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenUtil {
    @Value("${jwt.secret}")
    private String secretKey;

    private static String staticSecretKey;

    @PostConstruct
    public void init() {
        staticSecretKey = secretKey;
    }

    public static String createToken(String loginId, long exp) {
        Claims claims = Jwts.claims();
        claims.put("loginId", loginId);
        Key key = Keys.hmacShaKeyFor(staticSecretKey.getBytes());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis())) // 발행
                .setExpiration(new Date(System.currentTimeMillis()+(exp*1000*60))) // 만료
                .signWith(key, SignatureAlgorithm.HS512)
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
                .setSigningKey(secretKey)
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
