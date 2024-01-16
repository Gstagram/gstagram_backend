import com.gstagram.gstagram.common.api.ResponseCode;
import com.gstagram.gstagram.user.domain.User;
import com.gstagram.gstagram.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest{
    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    private final User user = User.builder()
            .id(1L)
            .email(testUserEmail)
            .password(testUserPassword)
            .username("testUserName")
            .nickname("testUserNickname")
            .isUserAuthorized(true)
            .profileS3ImageUrl("testUserImageUrl")
            .build();
    private final String testUserEmail = "testUserEmail@email.com";
    private final String testUserPassword = "testUserPassword";

    @Test
    public void 로그인에_성공(){
        // given
        LoginReqDto loginReqDto = LoginReqDto.builder
                .email(testUserEmail)
                .password(testUserPassword)
                .build();

        ResponseTokenDto responseTokenDto = ResponseTokenDto.builder
                .accessToken("accessToken")
                        .refreshToken("refreshToken")
                        .build();

        // when
        when(userRepository.findByEmailAndPassword(testUserEmail, testUserPassword))
                .thenReturn(Optional.of(user));

        // then
        assertEquals(authService.login(testUserEmail, testUserPassword), responseTokenDto);
    }

    @Test
    public void 로그인에_실패(){
        // given
        LoginReqDto loginReqDto = LoginReqDto.builder
                .email(testUserEmail)
                .password(testUserPassword)
                .build();

        // when
        when(userRepository.findByEmailAndPassword(testUserEmail, testUserPassword))
                .thenReturn(Optional.empty());

        // then
        assertThrows(ResponseCode.USER_NOT_FOUND, () -> authService.login(testUserEmail, testUserPassword)
    }

    @Test
    public void 회원가입_성공(){
        // given
        CreateUserDto createUserDto = CreateUserDto.builder
                .email(testUserEmail)
                .username("testUserName")
                .password(testUserPassword)
                .nickname("testUserNickname")
                .isUserAuthorized(true)
                .profileS3ImageUrl("testUserImageUrl")
                .build();

        ResponseUserDto responseUserDto = ResponseUserDto.from(user);

        // when
        when(userRepository.findByEmail(testUserEmail))
                .thenReturn(Optional.empty());

        // then
        assertEquals(authService.signUp(createUserDto), responseUserDto);
    }

    @Test
    public void 회원가입_실패(){
        // given
        CreateUserDto createUserDto = CreateUserDto.builder
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
        assertThrows(ResponseCode.USER_ALREADY_EXIST, () -> authService.signUp(createUserDto));
    }

    @Test
    public void AccessToken_재발급(){
        // given
        String refreshToken = "refreshToken";
        RedisTemplate redisTemplate = new RedisTemplate();

        ResponseTokenDto responseTokenDto = ResponseTokenDto.builder()
                        .accessToken("accessToken")
                                .refreshToken("refreshToken")
                                .build();

        //when
        when(redisTemplate.opsForValue().get(user.getEmail()))
                .thenReturn("refreshToken");

        // then
        assertEquals(authService.reissueToken(refreshToken), responseTokenDto);
    }
}