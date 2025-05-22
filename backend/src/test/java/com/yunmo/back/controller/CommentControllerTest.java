package com.yunmo.back.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yunmo.back.pojo.Comment;
import com.yunmo.back.pojo.Users;
import com.yunmo.back.service.ICommentService;
import com.yunmo.back.util.TestJwtUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICommentService commentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetComments() throws Exception {
        Comment comment = new Comment();
        comment.setContent("好诗");

        Mockito.when(commentService.getComments(1)).thenReturn(Arrays.asList(comment));

        mockMvc.perform(get("/comment/getComments")
                        .param("poemId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data[0].content").value("好诗")); // 注意这里
    }

    @Test
    void testSubmitComment() throws Exception {
        CommentController.newComment comment = new CommentController.newComment();
        comment.setPoem_id(1);
        comment.setContent("写得真棒！");

        Mockito.when(commentService.submitComment(eq(1), eq("写得真棒！"), eq(1))).thenReturn(200);
        mockMvc.perform(post("/comment/submitComment")
                        .header("token", TestJwtUtils.createTokenForUserId(1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").value("200")); // data 正确为 200
    }
}
