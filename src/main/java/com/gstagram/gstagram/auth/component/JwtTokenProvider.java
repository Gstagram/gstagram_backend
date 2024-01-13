package com.gstagram.gstagram.auth.component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final RedisTemplate<String, String> redisTemplate;

    private final UserDetailsService userDetailsService;

    //내부 암호화 키
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String createAccessToken(String userPK) {
        Claims claims = Jwts.claims().setSubject(userPK);
        Date now = new Date();
        //1시간
        long accessTokenValidTime = 60 * 60 * 1000L;
        return Jwts.builder()
                .signWith(key)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidTime))
                .compact();
    }

    public String createRefreshToken(String userPK) {
        Claims claims = Jwts.claims().setSubject(userPK);
        Date now = new Date();
        //1주일
        long refreshTokenValidTime = 7 * 24 * 60 * 60 * 1000L;
        String refreshToken = Jwts.builder()
                .signWith(key)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                .compact();

        redisTemplate.opsForValue().set(
                userPK,
                refreshToken,
                refreshTokenValidTime,
                TimeUnit.SECONDS
        );
        return refreshToken;
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(String.valueOf(this.getUserPk(token)));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserPk(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String jwtToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtToken);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("ExpiredJwtException", e);
            return false;
        } catch(JwtException e) {
            log.error("JwtException", e);
            return false;
        } catch (Exception e) {
            log.error("Exception", e);
            return false;
        }
    }
}
