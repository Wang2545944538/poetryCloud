package com.yunmo.back.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yunmo.back.pojo.Activity;
import com.yunmo.back.service.IActivityService;
import org.junit.jupiter.api.BeforeEach;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ActivityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IActivityService activityService;

    @Autowired
    private ObjectMapper objectMapper;

    private Activity activity;

    @BeforeEach
    void setUp() {
        activity = new Activity();
        activity.setActive_id(1);
        activity.setActive_content("测试内容");
        activity.setActive_profile("测试简介");
        activity.setActive_type_id("001");
        activity.setActive_img("img.jpg");
    }

    @Test
    void activities() throws Exception {
        when(activityService.selectAllActive()).thenReturn(Collections.singletonList(activity));

        mockMvc.perform(get("/activity/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].active_content").value("测试内容"));
    }

    @Test
    void activityIPage() throws Exception {
        // 由于分页使用 IPage，需构造假数据，可以略过或用真实分页对象模拟
        // 此处简写为 200 成功返回（你也可以 mock 返回 Page<Activity>）
        mockMvc.perform(get("/activity/page")
                        .param("page", "1")
                        .param("limit", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void addActivity() throws Exception {
        when(activityService.save(any(Activity.class))).thenReturn(true);

        mockMvc.perform(post("/activity/addActivity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(activity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void updateActive() throws Exception {
        when(activityService.update(any())).thenReturn(true);

        mockMvc.perform(post("/activity/updateActive")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(activity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void deleteActive() throws Exception {
        when(activityService.removeById(anyInt())).thenReturn(true);

        mockMvc.perform(get("/activity/deleteActive")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void deleteAllActivity() throws Exception {
        when(activityService.removeByIds(any())).thenReturn(true);

        mockMvc.perform(post("/activity/deleteAllActivity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Arrays.asList(1, 2, 3))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }
}
