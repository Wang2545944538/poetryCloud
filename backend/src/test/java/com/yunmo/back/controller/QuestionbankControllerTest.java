package com.yunmo.back.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yunmo.back.pojo.Options;
import com.yunmo.back.pojo.Questionbank;
import com.yunmo.back.service.IOptionsService;
import com.yunmo.back.service.IQuestionbankService;
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
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class QuestionbankControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IQuestionbankService questionbankService;

    @MockBean
    private IOptionsService optionsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testPageList() throws Exception {
        Mockito.when(questionbankService.pageList(any(), any()))
                .thenReturn(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(1, 2, 1));

        mockMvc.perform(get("/questionbank/page")
                        .param("page", "1")
                        .param("limit", "2"))
                .andExpect(status().isOk());
    }

    @Test
    void testAddQuestion() throws Exception {
        Questionbank q = new Questionbank();
        q.setQuestion("1+1=?");
        q.setAnswer("B");

        Mockito.when(questionbankService.addAQuestionBank(any(Questionbank.class))).thenReturn(true);

        mockMvc.perform(post("/questionbank/addQuestion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(q)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void testDeleteQuestionBank() throws Exception {
        Mockito.when(questionbankService.deleteQuestionBank(eq(1))).thenReturn(true);

        mockMvc.perform(get("/questionbank/deleteQuestionBank")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void testDeleteAll() throws Exception {
        Mockito.when(questionbankService.deleteAll(anyList())).thenReturn(true);

        mockMvc.perform(post("/questionbank/deleteAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Arrays.asList(1, 2, 3))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void testUpdateQuestion() throws Exception {
        Questionbank q = new Questionbank();
        q.setQuestionBank_id(1);
        q.setQuestion("修改后的问题");
        q.setAnswer("A");

        Options opt = new Options();
        opt.setOptions_id(1);
        opt.setA("选项A");
        opt.setB("选项B");
        opt.setC("选项C");
        opt.setD("选项D");
        q.setOptions(opt);

        Mockito.when(questionbankService.update(any())).thenReturn(true);
        Mockito.when(optionsService.update(any())).thenReturn(true);

        mockMvc.perform(post("/questionbank/updateQuestion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(q)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }
}
