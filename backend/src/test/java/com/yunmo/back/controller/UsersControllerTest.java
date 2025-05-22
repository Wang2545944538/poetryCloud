package com.yunmo.back.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yunmo.back.pojo.Users;
import com.yunmo.back.service.IUsersService;
import com.yunmo.back.util.RedisUtils;
import com.yunmo.back.util.TestJwtUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUsersService usersService;

    @MockBean
    private RedisUtils redisUtils;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testLogin_success() throws Exception {
        Users user = new Users();
        user.setUser_id(1);
        user.setUsername("test");
        user.setPassword("123456");
        user.setNickname("昵称");
        user.setAvatar("avatar.jpg");
        user.setType_id(1);

        Mockito.when(usersService.getOne(any())).thenReturn(user);

        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void testRegister_success() throws Exception {
        Users user = new Users();
        user.setUsername("newuser");
        user.setPassword("123456");

        Mockito.when(usersService.save(any())).thenReturn(true);

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void testCheckUsername_exist() throws Exception {
        Users user = new Users();
        user.setUsername("existing");

        Mockito.when(usersService.getOne(any())).thenReturn(user);

        mockMvc.perform(get("/users/checkUsername")
                        .param("username", "existing"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(201));
    }

    @Test
    void testCheckPhoneNumber_notExist() throws Exception {
        Mockito.when(usersService.getOne(any())).thenReturn(null);

        mockMvc.perform(get("/users/checkPhoneNumber")
                        .param("phoneNumber", "1234567890"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void testSetAvatar() throws Exception {
        String token = TestJwtUtils.createTokenForUserId(1);
        Mockito.when(usersService.update(any())).thenReturn(true);

        mockMvc.perform(get("/users/setAvatar")
                        .header("token", token)
                        .param("files", "avatar123.jpg"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void testAddScore() throws Exception {
        String token = TestJwtUtils.createTokenForUserId(1);
        Mockito.when(usersService.update(any())).thenReturn(true);

        mockMvc.perform(get("/users/addScore")
                        .header("token", token)
                        .param("point", "5"))
                .andExpect(status().isOk());
    }
}