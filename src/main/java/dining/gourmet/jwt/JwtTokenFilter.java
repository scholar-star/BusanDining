package dining.gourmet.jwt;

import dining.gourmet.auth.details.Member;
import dining.gourmet.auth.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp,
                                    FilterChain filterChain) throws IOException, ServletException {
        String authorizationHeader = req.getHeader(HttpHeaders.AUTHORIZATION);
        String token = null;
        String username = null;

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            username = jwtTokenUtil.getNickName(token);
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = new Member(jwtTokenUtil.getLoginID(token), username, jwtTokenUtil.getRole(token));
            if(jwtTokenUtil.validateToken(token, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken authenticationToken
                        = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                // 토큰 검증, credential은 null로 놔둔다.
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                // 요청의 부가정보(Client IP 등) - req을 WebAuthenticationDetailsSource에 넣어 AuthenticationToken의 detail로 삼는다.
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                // Spring Security의 Context에 Authentication token을 넣는다.
            }
        }
        filterChain.doFilter(req, resp);
    }
}
