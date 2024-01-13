package com.gstagram.gstagram.auth.repository;

import com.gstagram.gstagram.auth.domain.RefreshToken;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.repository.CrudRepository;

import java.sql.Ref;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class RefreshTokenRepository {
    private RedisTemplate redisTemplate;

    public RefreshTokenRepository(final RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(RefreshToken refreshToken) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(refreshToken.getRefreshToken(), refreshToken.getUserId());
        redisTemplate.expire(refreshToken.getRefreshToken(), 60 * 60 * 24 * 30, TimeUnit.SECONDS);
    }

    public Optional<RefreshToken> findById(String refreshToken) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String userId = valueOperations.get(refreshToken);
        if (Objects.isNull(userId)) {
            return Optional.empty();
        }
        return Optional.of(new RefreshToken(refreshToken, userId));
    }
}
