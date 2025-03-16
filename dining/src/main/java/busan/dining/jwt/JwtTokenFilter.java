package busan.dining.jwt;

import busan.dining.dto.SignupDTO;
import busan.dining.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// 필터화(자동화)
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final AuthService authService;
    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!authHeader.startsWith("Bearer ")) {
            // jwt 토큰인지 확인
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.split(" ")[1];
        // 토큰 가려내기

        if (jwtTokenUtil.isExpired(token, secretKey)) {
            filterChain.doFilter(request, response);
            return;
        }

        String loginId = jwtTokenUtil.getLoginId(token, secretKey);
        SignupDTO user = authService.findUser(loginId);

    }
}
