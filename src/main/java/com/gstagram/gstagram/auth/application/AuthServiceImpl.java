package com.gstagram.gstagram.auth.application;

import com.gstagram.gstagram.auth.component.JwtTokenProvider;
import com.gstagram.gstagram.auth.dto.reqeust.LoginReqDto;
import com.gstagram.gstagram.auth.dto.reqeust.SignUpDto;
import com.gstagram.gstagram.auth.dto.response.ResponseTokenDto;
import com.gstagram.gstagram.common.api.ResponseCode;
import com.gstagram.gstagram.common.exception.UserException;
import com.gstagram.gstagram.user.domain.User;
import com.gstagram.gstagram.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    @Transactional
    public boolean signUp(SignUpDto signUpDto) {
        validateDuplicateUser(signUpDto.getEmail());

        LocalDateTime lastConnectDate = LocalDateTime.now();


        User user = User.builder()
                .email(signUpDto.getEmail())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .username(signUpDto.getUsername())
                .nickname(signUpDto.getNickname())
                .lastConnectDate(lastConnectDate)
                .isUserAuthorized(signUpDto.isUserAuthorized())
                .profileImageS3Url(signUpDto.getProfileS3ImageUrl())
                .build();

        userRepository.save(user);

        return true;
    }

    @Override
    public ResponseTokenDto login(LoginReqDto loginReqDto) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginReqDto.getEmail(), loginReqDto.getPassword())
            );

            return ResponseTokenDto.builder()
                    .accessToken(jwtTokenProvider.createAccessToken(authentication))
                    .refreshToken(jwtTokenProvider.createRefreshToken(authentication))
                    .build();
        } catch (BadCredentialsException e) {
            throw new UserException(ResponseCode.USER_LOGIN_FAILURE);
        }
    }

    @Override
    public ResponseTokenDto reissueToken(String refreshToken) {
        jwtTokenProvider.validateToken(refreshToken);

        Authentication authentication = jwtTokenProvider.getAuthentication(refreshToken);

        String redisRefreshToken = redisTemplate.opsForValue().get(authentication.getName());
        if (!refreshToken.equals(redisRefreshToken)) {
            throw new UserException(ResponseCode.REFRESH_TOKEN_VALIDATION_FAILURE);
        }

        return ResponseTokenDto.builder()
                .accessToken(jwtTokenProvider.createAccessToken(authentication))
                .refreshToken(jwtTokenProvider.createRefreshToken(authentication))
                .build();
    }

    private void validateDuplicateUser(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new UserException(ResponseCode.USER_ALREADY_EXIST);
        });
    }
}
