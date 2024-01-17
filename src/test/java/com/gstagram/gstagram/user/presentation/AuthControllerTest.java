package com.gstagram.gstagram.user.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gstagram.gstagram.auth.application.AuthService;
import com.gstagram.gstagram.auth.dto.reqeust.LoginReqDto;
import com.gstagram.gstagram.auth.dto.reqeust.SignUpDto;
import com.gstagram.gstagram.auth.dto.response.ResponseTokenDto;
import com.gstagram.gstagram.common.api.ApiResponse;
import com.gstagram.gstagram.common.api.ResponseCode;
import com.gstagram.gstagram.common.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.assertj.core.api.Assertions;
import org.hibernate.validator.constraints.pl.REGON;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.junit.jupiter.MockitoExtension.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = AuthController.class)
public class AuthControllerTest {
    @InjectMocks
    private AuthController authController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Mock
    private AuthService authService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }
    @Test
    public void mockMvcIsNotNull(){
        assertThat(mockMvc).isNotNull();
    }

    @Test
    public void register_success() throws Exception {
        //given
        ApiResponse<Void> expectedResponse = ApiResponse.success(null, ResponseCode.USER_CREATE_SUCCESS.getMessage());
        SignUpDto signUpDto = SignUpDto.builder()
                .email("test@test.com")
                .username("test")
                .password("test")
                .nickname("test")
                .isUserAuthorized(true)
                .profileS3ImageUrl("test")
                .build();

        when(authService.signUp(any(SignUpDto.class))).thenReturn(true);
        String json = objectMapper.writeValueAsString(expectedResponse);

        //when & then
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.header.code").value(HttpStatus.CREATED.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.header.message").value(ResponseCode.USER_CREATE_SUCCESS.getMessage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }

    @Test
    public void register_fail() throws Exception {
        //given
        ApiResponse<Void> expectedResponse = ApiResponse.fail(ResponseCode.USER_ALREADY_EXIST, null);
        SignUpDto signUpDto = SignUpDto.builder()
                .email("")
                .username("")
                .password("")
                .nickname("")
                .isUserAuthorized(true)
                .profileS3ImageUrl("")
                .build();
        when(authService.signUp(any(SignUpDto.class))).thenReturn(true);
        String json = objectMapper.writeValueAsString(expectedResponse);

        //when & then
        final ResultActions resultActions = mockMvc.perform(
                        MockMvcRequestBuilders.post("/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.header.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.header.message").value(ResponseCode.BAD_REQUEST.getMessage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }

    @Test
    public void login_success() throws Exception {
        //given
        ApiResponse<Void> expectedResponse = ApiResponse.success(null, ResponseCode.USER_LOGIN_SUCCESS.getMessage());
        LoginReqDto loginReqDto = LoginReqDto.builder()
                .email("test@test.com")
                .password("test")
                .build();
        String json = objectMapper.writeValueAsString(expectedResponse);

        when(authService.login(any(LoginReqDto.class))).thenReturn(ResponseTokenDto.builder()
                .accessToken("test")
                .refreshToken("test")
                .build());

        //when & then
        final ResultActions resultActions = mockMvc.perform(
                        MockMvcRequestBuilders.post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.header.code").value(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.header.message").value(ResponseCode.USER_LOGIN_SUCCESS.getMessage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.accessToken").value("test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.refreshToken").value("test"));
    }

    @Test
    public void login_bad_request() throws Exception {
        //given
        ApiResponse<Void> expectedResponse = ApiResponse.fail(ResponseCode.USER_LOGIN_FAILURE, null);
        LoginReqDto loginReqDto = LoginReqDto.builder()
                .email("")
                .password("")
                .build();
        String json = objectMapper.writeValueAsString(expectedResponse);

        when(authService.login(any(LoginReqDto.class))).thenReturn(ResponseTokenDto.builder()
                .accessToken("test")
                .refreshToken("test")
                .build());

        //when & then
        final ResultActions resultActions = mockMvc.perform(
                        MockMvcRequestBuilders.post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.header.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.header.message").value(ResponseCode.BAD_REQUEST.getMessage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }

    @Test
    public void login_fail() throws Exception {
        //given
        ApiResponse<Void> expectedResponse = ApiResponse.fail(ResponseCode.USER_LOGIN_FAILURE, null);
        LoginReqDto loginReqDto = LoginReqDto.builder()
                .email("test@test.com")
                .password("test")
                .build();
        when(authService.login(loginReqDto)).thenThrow(new UserException(ResponseCode.USER_LOGIN_FAILURE));
        String json = objectMapper.writeValueAsString(expectedResponse);

        //when & then
        final ResultActions resultActions = mockMvc.perform(
                        MockMvcRequestBuilders.post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.jsonPath("$.header.code").value(HttpStatus.UNAUTHORIZED.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.header.message").value(ResponseCode.USER_LOGIN_FAILURE.getMessage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }
}
