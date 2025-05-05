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
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserServiceImpl userDetailsService;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    // List of public endpoints that don't require JWT authentication
    private static final List<String> PUBLIC_PATHS = List.of(
            "/api/jwt/generate",
            "/api/user/register",
            "/api/post/paginated",
            "/api/reservation",
            "/error/"
    );

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String requestURI = request.getRequestURI();

            // Skip JWT check for public paths
            if (PUBLIC_PATHS.stream().anyMatch(requestURI::startsWith)) {
                filterChain.doFilter(request, response);
                return;
            }

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

                if (jwtService.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                    authenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    securityContext.setAuthentication(authenticationToken);
                }
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Authentication error: " + e.getMessage());
        }
    }
}