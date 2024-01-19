package com.gstagram.gstagram.auth.component;

import com.gstagram.gstagram.common.api.ResponseCode;
import com.gstagram.gstagram.common.exception.BaseException;
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

    //AccessToken 생성 (user의 pk를 subject로 저장)
    public String createAccessToken(Authentication authentication) {
        Claims claims = Jwts.claims().setSubject(authentication.getName());
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

    //RefreshToken 생성 (AccessToken이 만료되었을 떄, 이를 이용해서 AccessToken을 재발급)
    public String createRefreshToken(Authentication authentication) {
        Claims claims = Jwts.claims().setSubject(authentication.getName());
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
                authentication.getName(),
                refreshToken,
                refreshTokenValidTime,
                TimeUnit.SECONDS
        ); //Redis에 RefreshToken 저장
        return refreshToken;
    }

    //여기서 Token은 AccessToken
    //받은 AccessToken에서 user pk를 추출하고, 이를 통해서 UserDetail 객체를 만든다.
    //이떄 UserDetails는 User이 UserDetail을 상속받고 있으므로 UserDetail 객체를 반환한다.
    //만든 UserDetail 객체를 통해서 Authentication 객체를 만들어서 반환한다.
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserPk(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    //Header에서 Token을 가져온다.
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
            throw new BaseException(ResponseCode.TOKEN_VALIDATION_FAILURE);
        } catch(JwtException e) {
            log.error("JwtException", e);
            throw new BaseException(ResponseCode.TOKEN_VALIDATION_FAILURE);
        } catch (Exception e) {
            throw new BaseException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }
}
