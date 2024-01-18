package com.gstagram.gstagram.user.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gstagram.gstagram.auth.presentation.AuthController;
import com.gstagram.gstagram.auth.application.AuthServiceImpl;
import com.gstagram.gstagram.auth.dto.reqeust.LoginReqDto;
import com.gstagram.gstagram.auth.dto.reqeust.SignUpDto;
import com.gstagram.gstagram.auth.dto.response.ResponseTokenDto;
import com.gstagram.gstagram.common.api.ApiResponse;
import com.gstagram.gstagram.common.api.ResponseCode;
import com.gstagram.gstagram.config.TestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = AuthController.class)
@Import(TestConfig.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private AuthServiceImpl authService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void init(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @Test
    public void mockMvcIsNotNull(){
        assertThat(mockMvc).isNotNull();
    }

    @Test
    @WithMockUser("test")
    public void register_success() throws Exception {
        //given
        SignUpDto signUpDto = SignUpDto.builder()
                .email("test@test.com")
                .username("test")
                .password("test")
                .nickname("test")
                .profileS3ImageUrl("test")
                .userAuthorized(true)
                .build();

        when(authService.signUp(any(SignUpDto.class))).thenReturn(true);
        String json = objectMapper.writeValueAsString(signUpDto);

        //when & then
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.header.code").value(HttpStatus.CREATED.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.header.message").value(ResponseCode.USER_CREATE_SUCCESS.getMessage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value(true));
    }

    @Test
    public void register_fail() throws Exception {
        //given
        ApiResponse<Void> expectedResponse = ApiResponse.fail(ResponseCode.BAD_REQUEST, null);
        SignUpDto signUpDto = SignUpDto.builder()
                .email("")
                .username("")
                .password("")
                .nickname("")
                .userAuthorized(true)
                .profileS3ImageUrl("")
                .build();
        when(authService.signUp(any(SignUpDto.class))).thenReturn(true);
        String json = objectMapper.writeValueAsString(signUpDto);

        //when & then
        final ResultActions resultActions = mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/auth/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.header.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.header.message").value(ResponseCode.BAD_REQUEST.getMessage()));
    }

    @Test
    @WithMockUser("test")
    public void login_success() throws Exception {
        //given
        LoginReqDto loginReqDto = LoginReqDto.builder()
                .email("test@test.com")
                .password("test")
                .build();
        String json = objectMapper.writeValueAsString(loginReqDto);

        when(authService.login(any(LoginReqDto.class))).thenReturn(ResponseTokenDto.builder()
                .accessToken("test")
                .refreshToken("test")
                .build());

        //when & then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/auth/login")
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
        LoginReqDto loginReqDto = LoginReqDto.builder()
                .email("")
                .password("")
                .build();
        String json = objectMapper.writeValueAsString(loginReqDto);

        when(authService.login(any(LoginReqDto.class))).thenReturn(ResponseTokenDto.builder()
                .accessToken("test")
                .refreshToken("test")
                .build());

        //when & then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.header.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.header.message").value(ResponseCode.BAD_REQUEST.getMessage()));
    }

    @Test
    public void reissue_Token() throws Exception {
        //given
        String refreshToken = "test";
        ResponseTokenDto refreshedResponseTokenDto = ResponseTokenDto.builder()
                .accessToken("newAccess")
                .refreshToken("newRefresh")
                .build();

        when(authService.reissueToken(any(String.class))).thenReturn(refreshedResponseTokenDto);

        //when & then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/auth/reissue")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(refreshToken)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.header.code").value(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.header.message").value(ResponseCode.TOKEN_REISSUE_SUCCESS.getMessage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.accessToken").value("newAccess"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.refreshToken").value("newRefresh"));
    }

}
