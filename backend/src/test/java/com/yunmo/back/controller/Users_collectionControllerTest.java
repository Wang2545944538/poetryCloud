package com.yunmo.back.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yunmo.back.pojo.Users_collection;
import com.yunmo.back.service.IUsers_collectionService;
import com.yunmo.back.util.TestJwtUtils;
import lombok.var;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class Users_collectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUsers_collectionService usersCollectionService;

    @Test
    void testUsersCollectionList() throws Exception {
        // 模拟数据
        Users_collection uc = new Users_collection();
        uc.setUser_id(1);
        uc.setCollection_id(101);

        var page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<Users_collection>();
        page.setRecords(Collections.singletonList(uc));
        page.setTotal(1);

        // Mock 服务层
        Mockito.when(usersCollectionService.getAllCollectionById(any(), any())).thenReturn(page);

        // 模拟调用接口
        mockMvc.perform(get("/users_collection/list")
                        .header("token", TestJwtUtils.createTokenForUserId(1))
                        .param("page", "1")
                        .param("limit", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.records[0].collection_id").value(101));
    }
}
