package yjhb.meeti.service.jwt.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.web.filter.OncePerRequestFilter;
import yjhb.meeti.service.jwt.JwtService;
import yjhb.meeti.repository.UserRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtService jwtUtils;
    private final UserRepository userRepository;

    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
    private final String CHECK_URL = "/login";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("request.getRequestURI() = " + request.getRequestURI());
        System.out.println("request = " + request.getHeader("Authrization"));
        System.out.println("request = " + request.getHeader("Authrization_refresh"));
        if (request.getRequestURI().equals(CHECK_URL)){
            filterChain.doFilter(request, response);
            return;
        } else
            System.out.println("not login request header = " + request.getHeader("Authorization_refresh"));

        String refreshToken = jwtUtils.
    }
}
