package yjhb.meeti.jwt.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import yjhb.meeti.jwt.utils.JwtUtils;
import yjhb.meeti.service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final UserService userService;
    @Value("${jwt.secret}")
    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("authorization: {}", authorization);

        //token없을 시 or Bearer 안달려 있을 시 Block
        if (authorization == null || !(authorization.startsWith("Bearer "))){
            log.info("authorization이 없습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        //token 꺼내기
        String token = authorization.split(" ")[1];

        //expired 확인
        if (JwtUtils.isExpired(token, secretKey)){
            log.error("token이 만료되었습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        //token에서 username 추출
        String username = JwtUtils.getUsername(token, secretKey);
        log.info("username: {}", username);

        //권한 부여
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, null, List.of(new SimpleGrantedAuthority("MEMBER")));

        //Detail 추가
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
