package busan.dining.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private String secretKey = "busandining";
    private long tokenValidTime = 30*60;
    private final UserDetailsService userDetailsService;

    @PostConstruct // 의존성 주입 후 초기화
    public void init() {
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(String username) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("user", username); // 정보를 key - value로 저장
        Date now = new Date();
        return Jwts.builder() // 정보 입력
                .setClaims(claims) // 클레임 지정
                .setIssuedAt(now) // 발행 시간
                .setExpiration(new Date(now.getTime()+this.tokenValidTime)) // 토큰만료
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화
                .compact();// 마무리
    }

    public String getToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public String getUserName(String jwtToken) {
        return Jwts.parserBuilder().setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwtToken) // JWT<Claims>
                .getBody() // Claims
                .getSubject(); // 서브젝트 획득
    }

    public boolean validateToken(String token) {
        if (token == null)
            return false;

        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody() //  claims에 있는 정보들 추출
                    .getExpiration()
                    .after(new Date()); // 만료 시간 > 현재 시간
        } catch (Exception e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        return new UsernamePasswordAuthenticationToken(getUserName(token), "", )
    }
}
