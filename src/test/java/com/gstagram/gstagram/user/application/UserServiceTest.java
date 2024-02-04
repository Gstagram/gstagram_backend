package com.gstagram.gstagram.user.application;

import com.gstagram.gstagram.auth.component.JwtTokenProvider;
import com.gstagram.gstagram.user.domain.User;
import com.gstagram.gstagram.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserService userService;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private UserRepository userRepository;


    private final String testUserEmail = "testUserEmail";
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
    public void 유저조회_성공(){
        // given
        when(userRepository.findByUuid(any(User.class))).thenReturn(user);

        // when
        User user = userService.getUserInfoByUuid("testUserUuid");

        // then
        assertEquals(user.getEmail(), testUserEmail);
        assertEquals(user.getPassword(), testUserPassword);
    }

    @Test
    public void 유저정보_수정_성공(){
        // given
        when(userRepository.findByUuid(any(User.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        UpdateUserDto updateUserDto = UpdateUserDto.builder()
                .nickname("testUserNickname")
                .profileS3ImageUrl("testUserImageUrl")
                .build();

        // when
        userService.updateUserInfo(updateUserDto);

        // then
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void 유저정보_수정_실패(){
        // given
        when(userRepository.findByUuid(any(User.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        UpdateUserDto updateUserDto = UpdateUserDto.builder()
                .nickname("")
                .profileS3ImageUrl("")
                .build();

        // when
        userService.updateUserInfo(updateUserDto);

        // then
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void 유저_삭제_성공(){
        // given
        when(userRepository.findByUuid(any(User.class))).thenReturn(user);
        doNothing().when(userRepository).delete(any(User.class));

        // when
        userService.deleteUser("testUserUuid");

        // then
        verify(userRepository, times(1)).delete(any(User.class));
    }
}
