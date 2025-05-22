package com.yunmo.back.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yunmo.back.pojo.Author;
import com.yunmo.back.pojo.Poem;
import com.yunmo.back.service.IAuthorService;
import com.yunmo.back.service.IPoemService;
import com.yunmo.back.VO.DynastyAuthor;
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

import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IAuthorService authorService;

    @MockBean
    private IPoemService poemService;

    @Autowired
    private ObjectMapper objectMapper;

    private Author sampleAuthor;

    @BeforeEach
    void setUp() {
        sampleAuthor = new Author();
        sampleAuthor.setAuthor_id(1);
        sampleAuthor.setAuthor_name("李白");
        sampleAuthor.setAuthor_gender("男");
        sampleAuthor.setDynasty_id(1);
        sampleAuthor.setAuthor_intro("盛唐诗人");
        sampleAuthor.setAuthor_img("libai.jpg");
    }

    @Test
    void testAuthorsList() throws Exception {
        Mockito.when(authorService.list()).thenReturn(Collections.singletonList(sampleAuthor));

        mockMvc.perform(get("/author/authorList"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data[0].author_name").value("李白"));
    }

    @Test
    void testFindByIdauthor() throws Exception {
        Mockito.when(authorService.getById(1)).thenReturn(sampleAuthor);

        mockMvc.perform(get("/author/findByIdauthor")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author_name").value("李白"));
    }

    @Test
    void testByIdAuthor() throws Exception {
        Mockito.when(authorService.getById(1)).thenReturn(sampleAuthor);

        mockMvc.perform(get("/author/byIdAuthor")
                        .param("author_id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.author_name").value("李白"));
    }

    @Test
    void testFindByIdPoems() throws Exception {
        Poem poem = new Poem();
        poem.setTitle("将进酒");

        Mockito.when(poemService.findByIdPoems(1)).thenReturn(Collections.singletonList(poem));

        mockMvc.perform(get("/author/findByIdPoems")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("将进酒")); // 改为正确字段名
    }

    @Test
    void testAddAuthor() throws Exception {
        Mockito.when(authorService.save(any(Author.class))).thenReturn(true);

        mockMvc.perform(post("/author/addAuthor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleAuthor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.author_name").value("李白"));
    }

    @Test
    void testDeleteAuthor() throws Exception {
        Mockito.when(authorService.removeById(1)).thenReturn(true);

        mockMvc.perform(get("/author/deleteAuthor")
                        .param("author_id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void testSubmitEditAuthor() throws Exception {
        Mockito.when(authorService.update(any())).thenReturn(true);

        mockMvc.perform(post("/author/submitEditAuthor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleAuthor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void testFindByDynastyidAuthor() throws Exception {
        DynastyAuthor dynastyAuthor = new DynastyAuthor();

        Author author = new Author();
        author.setAuthor_name("杜甫");

        dynastyAuthor.setAuthor(author);  // 正确设置嵌套对象
        dynastyAuthor.setDynasty(null);   // 可选

        Mockito.when(authorService.findByDynastyidAuthor(1))
                .thenReturn(Collections.singletonList(dynastyAuthor));

        mockMvc.perform(get("/author/findByDynastyidAuthor")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].author.author_name").value("杜甫"));  // 路径修正
    }

}
