package com.example.demo.filter;

import com.example.demo.constants.ApplicationConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 讀取請求 Header 的 JWT
 * 驗證 JWT 是否合法（簽名 + 過期）
 * 解析 JWT 取得使用者帳號與角色
 * 建立 Authentication 並放到 SecurityContextHolder
 * 讓 Spring Security 其他授權機制（@PreAuthorize、hasRole）可以使用
 * (所有受保護 API → 驗 Token → 放到 SecurityContext)
 */
public class JWTTokenValidatorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt =request.getHeader(ApplicationConstants.JWT_HEADER);
        if (jwt != null && jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7).trim();
        } else {
            filterChain.doFilter(request, response);
            return;
        }
            try {
                Environment env = getEnvironment();
                if(null != env) {
                    String secret = env.getProperty(ApplicationConstants.JWT_SECRET, ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
                    SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                    if(null != secretKey) {
                        Claims claims = Jwts.parser()
                                .verifyWith(secretKey)
                                .build()
                                .parseClaimsJws(jwt)
                                .getBody();
                        String username = String.valueOf(claims.get("username"));
                        String authorities = String.valueOf(claims.get("authorities"));


                        Authentication authentication = new UsernamePasswordAuthenticationToken(username,null ,
                                AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
                        SecurityContextHolder.getContext().setAuthentication(authentication);

                    }
                }

            } catch (Exception exception) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return; // 直接回 401
            }
        filterChain.doFilter(request,response);
    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/user");
    }
}
