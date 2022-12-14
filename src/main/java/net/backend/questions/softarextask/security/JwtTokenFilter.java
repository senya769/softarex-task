package net.backend.questions.softarextask.security;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import net.backend.questions.softarextask.security.utils.JwtAuthentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest);
        if (token != null && jwtTokenProvider.validateAccessToken(token)) {
            final Claims claims = jwtTokenProvider.getAccessClaims(token);
            final JwtAuthentication auth = JwtTokenProvider.generate(claims);
            auth.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
