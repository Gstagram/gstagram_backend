package com.gstagram.gstagram.user.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gstagram.gstagram.auth.application.AuthServiceImpl;
import com.gstagram.gstagram.auth.component.JwtTokenProvider;
import com.gstagram.gstagram.auth.presentation.AuthController;
import com.gstagram.gstagram.config.TestConfig;
import com.gstagram.gstagram.user.application.UserServiceImpl;
import com.gstagram.gstagram.user.dto.ResponseUserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = UserControllerTest.class)
@Import(TestConfig.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        when(jwtTokenProvider.validateToken("testToken")).thenReturn(true);
        when(jwtTokenProvider.getUserPk("testToken")).thenReturn("testUserUuid");
    }

    @Test
    public void mockMvcIsNotNull() {
        assertThat(mockMvc).isNotNull();
    }

    @Test
    public void 유저조회_성공() {
//        //given
//        when(userService.getUserInfoByUuid("testUserUuid")).thenReturn(ResponseUserDto.builder()
//                .email("testUserEmail")
//                .username("testUserName")
//                .nickname("testUserNickname")
//                .profileImageS3Url("testUserImageUrl")
//                .build());
//
//        //when & then
//        mockMvc.perform(
//                MockMvcRequestBuilders.get("/user")
//                        .param("uuid", "testUserUuid")
//                        .contentType("application/json")
    }

    @Test
    public void 유저정보_수정_성공() {
    }

    @Test
    public void 유저삭제_성공() {
    }

}
