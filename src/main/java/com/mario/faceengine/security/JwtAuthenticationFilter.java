package com.mario.faceengine.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mario.faceengine.exception.ErrorCodeMessage;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.ErrorResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private final ObjectMapper objectMapper = new ObjectMapper(); // Jackson ObjectMapper


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(jwtSecret.getBytes())
                        .parseClaimsJws(token)
                        .getBody();

                String username = claims.getSubject();
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList()); // Add authorities if needed
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } catch (ExpiredJwtException e) {
                // Handle expired token
                sendCustomErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, ErrorCodeMessage.JWT_EXPIRY);
                return;
            } catch (MalformedJwtException e) {
                // Handle malformed token
                sendCustomErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, ErrorCodeMessage.JWT_INVALID_FORMAT);
                return;
            } catch (SignatureException e) {
                // Handle invalid signature
                sendCustomErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, ErrorCodeMessage.JWT_INVALID_SIGNATURE);
                return;
            } catch (Exception e) {
                // Handle any other exceptions
                sendCustomErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, ErrorCodeMessage.JWT_INVALID_ERROR);
                return;
            }
        } else {
            sendCustomErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, ErrorCodeMessage.INVALID_JWT);
            return;
        }
        chain.doFilter(request, response);
    }

    private void sendCustomErrorResponse(HttpServletResponse response, int status, ErrorCodeMessage errorCodeMessage) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String jsonResponse = objectMapper.writeValueAsString(errorCodeMessage.toString());
        response.getWriter().write(jsonResponse);
    }
}

