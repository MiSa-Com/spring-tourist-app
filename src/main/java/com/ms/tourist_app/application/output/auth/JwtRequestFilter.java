package com.ms.tourist_app.application.output.auth;

import com.ms.tourist_app.application.constants.AppConst;
import com.ms.tourist_app.application.constants.AppStr;
import com.ms.tourist_app.application.service.imp.security.MyUserDetailService;
import com.ms.tourist_app.application.utils.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final MyUserDetailService userDetailService;

    JwtUtil jwtUtil;

    public JwtRequestFilter(MyUserDetailService userDetailService, JwtUtil jwtUtil) {
        this.userDetailService = userDetailService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            final String authorizationHeader = request.getHeader(AppStr.Auth.authorization);
            String username = null;
            String jwt = null;
            if (authorizationHeader != null && authorizationHeader.startsWith(AppStr.Auth.bearer)){
                jwt = authorizationHeader.substring(AppConst.Auth.bearerSubstring);
                username = jwtUtil.extractUsername(jwt);
            }
            if (username != null) {
                UserDetails userDetails = userDetailService.loadUserByUsername(username);
                if (jwtUtil.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}
