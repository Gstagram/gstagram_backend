package com.gstagram.gstagram.user.application;

import com.gstagram.gstagram.auth.component.JwtTokenProvider;
import com.gstagram.gstagram.user.domain.User;
import com.gstagram.gstagram.user.dto.ResponseUserDto;
import com.gstagram.gstagram.user.dto.UpdateUserDto;
import com.gstagram.gstagram.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userService;

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
        given(userRepository.findByUuid(any(String.class))).willReturn(Optional.of(user));

        // when
        ResponseUserDto responseUserDto = userService.getUserInfoByUuid("testUserUuid");

        // then
        assertEquals(testUserEmail, responseUserDto.getEmail());
    }

    @Test
    public void 유저정보_수정_성공(){
        // given
        given(userRepository.findByUuid(any(String.class))).willReturn(Optional.of(user));
        given(userRepository.save(any(User.class))).willReturn(user);
        UpdateUserDto updateUserDto = UpdateUserDto.builder()
                .uuid("testUserUuid")
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
        given(userRepository.findByUuid(any(String.class))).willReturn(Optional.of(user));
        given(userRepository.save(any(User.class))).willReturn(user);
        UpdateUserDto updateUserDto = UpdateUserDto.builder()
                .uuid("testUserUuid")
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
        given(userRepository.findByUuid(any(String.class))).willReturn(Optional.of(user));
        doNothing().when(userRepository).delete(any(User.class));

        // when
        userService.deleteUser("testUserUuid");

        // then
        verify(userRepository, times(1)).delete(any(User.class));
    }
}
