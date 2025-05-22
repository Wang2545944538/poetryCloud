package com.yunmo.back.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yunmo.back.dao.ICollectionDao;
import com.yunmo.back.pojo.Collection;
import com.yunmo.back.pojo.Users;
import com.yunmo.back.service.*;
import com.yunmo.back.service.impl.PoemServiceImpl;
import com.yunmo.back.service.impl.Poem_collectionServiceImpl;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CollectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICollectionService collectionService;
    /*@MockBean
    private ICollectionDao collectionDao;*/
    @MockBean
    private IUsers_collectionService users_collectionService;
    @MockBean
    private Poem_collectionServiceImpl poem_collectionService;
    @MockBean
    private PoemServiceImpl poemService;
    @MockBean
    private ILikeService likeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testListCollections() throws Exception {
        Collection c = new Collection();
        c.setCollection_id(1);
        c.setCollection_name("唐诗三百首");

        when(collectionService.page(any(), any())).thenReturn(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(1, 10, 1).setRecords(Arrays.asList(c)));

        mockMvc.perform(get("/collection/select")
                        .param("page", "1")
                        .param("limit", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.records[0].collection_name").value("唐诗三百首"));
    }

    @Test
    void testAddPoetryCollection() throws Exception {
        Collection c = new Collection();
        c.setCollection_name("新诗集");

        when(collectionService.addPoems(Mockito.anyInt(), any(Collection.class))).thenReturn(true);

        mockMvc.perform(post("/collection/addPoems")
                        .header("token", TestJwtUtils.createTokenForUserId(1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(c)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void testDoCollection_duplicate() throws Exception {
        CollectionController.doCl doCl = new CollectionController.doCl();
        doCl.setPoem_id(1);
        doCl.setSelectedCollectionId(1001);

        // 模拟已存在
        when(collectionService.getById(1001)).thenReturn(new Collection());
        when(poem_collectionService.getOne(any())).thenReturn(new com.yunmo.back.pojo.Poem_collection(1,1));

        mockMvc.perform(post("/collection/doCollection")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doCl)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500));
    }

    @Test
    void testDoCollection_success() throws Exception {
        CollectionController.doCl doCl = new CollectionController.doCl();
        doCl.setPoem_id(2);
        doCl.setSelectedCollectionId(1002);

        when(collectionService.getById(1002)).thenReturn(new Collection());
        when(poem_collectionService.getOne(any())).thenReturn(null);
        when(poem_collectionService.save(any())).thenReturn(true);
        when(collectionService.update(any())).thenReturn(true);

        mockMvc.perform(post("/collection/doCollection")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doCl)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void testAddCollection_success() throws Exception {
        com.yunmo.back.VO.UCollection uc = new com.yunmo.back.VO.UCollection();
        uc.setCollection_id(1);
        uc.setUser_id(1);
        uc.setCollection_name("李白精选");

        when(collectionService.save(any(Collection.class))).thenReturn(true);
        when(users_collectionService.save(any())).thenReturn(true);

        mockMvc.perform(post("/collection/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(uc)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }
}
