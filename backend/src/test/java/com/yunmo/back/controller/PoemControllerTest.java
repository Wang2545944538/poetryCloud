package com.yunmo.back.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yunmo.back.VO.PoemMy;
import com.yunmo.back.pojo.Poem;
import com.yunmo.back.pojo.Users;
import com.yunmo.back.service.IPoemService;
import com.yunmo.back.VO.aPoem;
import com.yunmo.back.VO.uPoem;
import com.yunmo.back.util.TestJwtUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PoemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPoemService poemService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testPoemList() throws Exception {
        Mockito.when(poemService.list()).thenReturn(Arrays.asList(new Poem()));

        mockMvc.perform(get("/poem/poemList"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void testGetPoemDetails() throws Exception {
        Poem poem = new Poem();
        poem.setPoem_id(1);
        poem.setTitle("将进酒");

        Mockito.when(poemService.getPoemDetails(1)).thenReturn(poem);

        mockMvc.perform(get("/poem/getPoemDetails")
                        .param("poemId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.title").value("将进酒"));
    }

    @Test
    void testAddPoem() throws Exception {
        aPoem poem = new aPoem();
        Mockito.when(poemService.addPoem(any())).thenReturn(2);

        mockMvc.perform(post("/poem/addPoem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(poem)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void testUpdatePoem() throws Exception {
        uPoem poem = new uPoem();
        Mockito.when(poemService.updatePoem(any())).thenReturn(1);

        mockMvc.perform(post("/poem/updatePoem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(poem)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void testDeletePoem() throws Exception {
        Mockito.when(poemService.deletePoem(1, 2)).thenReturn(2);

        mockMvc.perform(post("/poem/deletePoem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"poem_id\":1,\"pf_id\":2}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void testAddMyPoem() throws Exception {
        Poem poem = new Poem();
        poem.setTitle("自创诗");

        Mockito.when(poemService.save(any())).thenReturn(true);

        mockMvc.perform(post("/poem/addMyPoem")
                        .header("token", TestJwtUtils.createTokenForUserId(1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(poem)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void testGetMyPoem() throws Exception {
        IPage<PoemMy> mockPage = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>();
        mockPage.setRecords(Collections.emptyList());

        Mockito.when(poemService.getMyPoem(anyString(), anyInt(), anyInt()))
                .thenReturn(mockPage);

        mockMvc.perform(get("/poem/getMyPoem")
                        .param("page", "1")
                        .param("limit", "10")
                        .header("token", TestJwtUtils.createTokenForUserId(1)))
                .andExpect(status().isOk());
    }

    @Test
    void testAdminPassPoem() throws Exception {
        Mockito.when(poemService.adminPassPoem(1)).thenReturn(1);

        mockMvc.perform(get("/poem/adminPassPoem")
                        .param("poem_id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    void testAdminRejectPoem() throws Exception {
        Mockito.when(poemService.adminRejectPoem(1)).thenReturn(1);

        mockMvc.perform(get("/poem/adminRejectPoem")
                        .param("poem_id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    void testSize() throws Exception {
        Mockito.when(poemService.count()).thenReturn(888);

        mockMvc.perform(get("/poem/size"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(888));
    }
}
