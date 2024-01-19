package com.gstagram.gstagram.user.application;

/*import com.gstagram.gstagram.auth.application.AuthService;
import com.gstagram.gstagram.auth.dto.reqeust.LoginReqDto;
import com.gstagram.gstagram.auth.dto.response.ResponseTokenDto;
import com.gstagram.gstagram.auth.dto.reqeust.SignUpDto;
import com.gstagram.gstagram.common.api.ResponseCode;
import com.gstagram.gstagram.common.exception.UserException;
import com.gstagram.gstagram.user.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest{
    @Mock
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    private final String testUserEmail = "testUserEmail@email.com";
    private final String testUserPassword = "testUserPassword";

    private final User user = User.builder()
            .uuid("testUserUuid")
            .email(testUserEmail)
            .password(testUserPassword)
            .username("testUserName")
            .nickname("testUserNickname")
            .isUserAuthorized(true)
            .profileImageS3Url("testUserImageUrl")
            .build();


    @Test
    public void 로그인에_성공(){
        // given
        LoginReqDto loginReqDto = LoginReqDto.builder()
                .email(testUserEmail)
                .password(testUserPassword)
                .build();

        userRepository.save(User.builder()
                .email(testUserEmail)
                .password(testUserPassword)
                .build()

        )

        // when
        ResponseTokenDto responseTokenDto = authService.login(loginReqDto);


        // then
        assertNotNull(responseTokenDto.getAccessToken());
        assertNotNull(responseTokenDto.getRefreshToken());
    }

    @Test
    public void 로그인에_실패(){
        // given
        LoginReqDto loginReqDto = LoginReqDto.builder()
                .email(testUserEmail)
                .password(testUserPassword)
                .build();

        // when


        // then
        assertThrows(UserException.class, () -> authService.login(loginReqDto));
    }

    @Test
    public void 회원가입_성공(){
        // given
        SignUpDto signUpDto = SignUpDto.builder()
                .email(testUserEmail)
                .username("testUserName")
                .password(testUserPassword)
                .nickname("testUserNickname")
                .isUserAuthorized(true)
                .profileS3ImageUrl("testUserImageUrl")
                .build();

        // when
        when(userRepository.findByEmail(testUserEmail))
                .thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        // then
        assertEquals(true, authService.signUp(signUpDto));
    }

    @Test
    public void 회원가입_실패(){
        // given
        SignUpDto signUpDto = SignUpDto.builder()
                .email(testUserEmail)
                .username("testUserName")
                .password(testUserPassword)
                .nickname("testUserNickname")
                .isUserAuthorized(true)
                .profileS3ImageUrl("testUserImageUrl")
                .build();

        // when
        when(userRepository.findByEmail(testUserEmail))
                .thenReturn(Optional.of(user));

        // then
        assertThrows(UserException.class, () -> authService.signUp(signUpDto));
    }

    @Test
    public void AccessToken_재발급(){
        // given
        String refreshToken = "refreshToken";
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();

        ResponseTokenDto responseTokenDto = ResponseTokenDto.builder()
                        .accessToken("accessToken")
                                .refreshToken("refreshToken")
                                .build();

        //when
        when(redisTemplate.opsForValue().get(user.getEmail()))
                .thenReturn("refreshToken");

        // then
        assertEquals(responseTokenDto, authService.reissueToken(refreshToken));
    }
}*/