package com.example.demo.filter;

import com.example.demo.constants.ApplicationConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * 取得登入成功的使用者資訊
 * 產生 JWT Token
 * 回傳給前端
 *(登入成功 → 產 Token → 回前端)
 */
public class JWTTokenGeneratorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(null!=authentication){
            Environment env = getEnvironment();
            if(null != env){
                String secret = env.getProperty(ApplicationConstants.JWT_SECRET,ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                String  jwt = Jwts.builder().issuer("System").subject("JWT Token")
                        .claim("username",authentication.getName())
                        .claim("authorities",authentication.getAuthorities().stream().map(
                             GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                        .issuedAt(new Date())
                        .expiration(Date.from(Instant.now().plus(30, ChronoUnit.MINUTES)))//設定 8.3 小時
                        .signWith(secretKey).compact();
                response.setHeader(ApplicationConstants.JWT_HEADER,"Bearer "+jwt);
            }
        }
        filterChain.doFilter(request,response);
    }

    /**
     * 只對 /user 這個登入 API 生效
     * @param request current HTTP request
     * @return
     * @throws ServletException
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/user");
    }
}
