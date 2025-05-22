package com.yunmo.back.controller;

import com.yunmo.back.service.impl.DeepSeekServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeepSeekServiceImpl aiService;

    @Test
    void testInterpretPoem_valid() throws Exception {
        Mockito.when(aiService.generateResponse(anyString())).thenReturn("解析结果");

        mockMvc.perform(post("/ai/interpret")
                        .param("content", "床前明月光"))
                .andExpect(status().isOk())
                .andExpect(content().string("解析结果"));
    }

    @Test
    void testGeneratePoem() throws Exception {
        Mockito.when(aiService.generateResponse(anyString())).thenReturn("生成的诗");

        mockMvc.perform(post("/ai/generate")
                        .param("style", "七言律诗")
                        .param("title", "春日游"))
                .andExpect(status().isOk())
                .andExpect(content().string("生成的诗"));
    }

    @Test
    void testGenerateAcrostic() throws Exception {
        Mockito.when(aiService.generateResponse(anyString())).thenReturn("藏头诗");

        mockMvc.perform(post("/ai/generate-acrostic")
                        .param("input", "中华腾飞"))
                .andExpect(status().isOk())
                .andExpect(content().string("藏头诗"));
    }

    @Test
    void testGenerateChat() throws Exception {
        Mockito.when(aiService.generateResponse(anyString())).thenReturn("AI 回复");

        mockMvc.perform(post("/ai/ai2")
                        .param("message", "你好"))
                .andExpect(status().isOk())
                .andExpect(content().string("AI 回复"));
    }

    @Test
    void testPoetryQa() throws Exception {
        Mockito.when(aiService.generateResponse(anyString())).thenReturn("诗词问答结果");

        mockMvc.perform(post("/ai/poetry-qa")
                        .param("question", "李白的将进酒表达了什么情感？"))
                .andExpect(status().isOk())
                .andExpect(content().string("诗词问答结果"));
    }

    @Test
    void testGenerateCouplet() throws Exception {
        Mockito.when(aiService.generateResponse(anyString())).thenReturn("下联生成");

        mockMvc.perform(post("/ai/generate-couplet")
                        .param("topLine", "春风又绿江南岸"))
                .andExpect(status().isOk())
                .andExpect(content().string("下联生成"));
    }

    @Test
    void testFileUploadAndGenerate() throws Exception {
        String content = "测试文件内容";
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.txt", "text/plain", content.getBytes()
        );

        Mockito.when(aiService.generateResponse(anyString())).thenReturn("生成结果");

        mockMvc.perform(multipart("/ai/f").file(file))
                .andExpect(status().isOk());
        // 注意：此接口无返回值，只写 output.txt，如果需要断言结果，建议写入逻辑调整
    }
}
