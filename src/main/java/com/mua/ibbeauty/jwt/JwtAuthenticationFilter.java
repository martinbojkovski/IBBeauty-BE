package com.mua.ibbeauty.jwt;

import com.mua.ibbeauty.service.impl.UserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserServiceImpl userDetailsService;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws IOException {
        try {
            String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
            String jwt = null;
            String email = null;

            if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
                jwt = authorizationHeader.substring(BEARER_PREFIX.length());
                email = jwtService.extractUserName(jwt);
            }

            SecurityContext securityContext = SecurityContextHolder.getContext();
            if (email != null && securityContext.getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                boolean isTokenValid = jwtService.validateToken(jwt, userDetails);
                if (Boolean.TRUE.equals(isTokenValid)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    WebAuthenticationDetails authenticationDetails =
                            new WebAuthenticationDetailsSource().buildDetails(request);
                    authenticationToken.setDetails(authenticationDetails);
                    securityContext.setAuthentication(authenticationToken);
                }
            }

            filterChain.doFilter(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
