package org.quagh.blogbackend.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.quagh.blogbackend.components.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    @Value("${api.prefix}")
    private String apiPrefix;
    @Autowired
    private UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            if(isBypassToken(request)){
                filterChain.doFilter(request, response); //enable bypass
                return;
            }
            //request need to check
            String authHeader = request.getHeader("Authorization");
            if(authHeader == null || !authHeader.startsWith("Bearer ")){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized!");
                return;
            }
            if(authHeader.startsWith("Bearer ")){
                authHeader = authHeader.replace("Bearer ", "");
                final String username = jwtTokenUtil.extractUsername(authHeader);
                if(username != null
                        && SecurityContextHolder.getContext().getAuthentication() == null){
                    UserDetails existingUser = userDetailsService.loadUserByUsername(username);
                    if(jwtTokenUtil.validateToken(authHeader, existingUser)){
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(
                                        existingUser,
                                        null,
                                        existingUser.getAuthorities()
                                );
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
                filterChain.doFilter(request, response);
            }
        }catch (Exception e){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized!");
        }

    }

    private boolean isBypassToken(@NonNull HttpServletRequest request){
        final List<Pair<String, String>> byPassToken = Arrays.asList(
                Pair.of(String.format("%s/posts", apiPrefix), "GET"),
                Pair.of(String.format("%s/categories",apiPrefix), "GET"),
                Pair.of(String.format("%s/users/register",apiPrefix), "POST"),
                Pair.of(String.format("%s/users/login",apiPrefix), "POST"),
                Pair.of(String.format("%s/users/verify",apiPrefix), "GET"),
                Pair.of(String.format("%s/categories", apiPrefix), "POST")
        );

        for(Pair<String, String> bypassToken: byPassToken){
            if(request.getServletPath().contains(bypassToken.getFirst()) &&
                    request.getMethod().equals(bypassToken.getSecond())){
                return true;
            }
        }

        return false;
    }
}
