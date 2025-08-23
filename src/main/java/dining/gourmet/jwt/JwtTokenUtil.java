package dining.gourmet.jwt;
import dining.gourmet.auth.UserType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
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
    private Key key;

    public JwtTokenUtil() {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String createToken(String loginId, String nickName, UserType role, long exp) {
        return Jwts.builder()
                .claim("loginId", loginId)
                .claim("nickName", nickName)
                .claim("role", role)
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
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getLoginID(String token) {
        return extractAllClaims(token).get("loginId", String.class);
    }

    public String getNickName(String token) {
        return extractAllClaims(token).get("nickName", String.class);
    }

    public UserType getRole(String token) {
        return extractAllClaims(token).get("role", UserType.class);
    }
}
