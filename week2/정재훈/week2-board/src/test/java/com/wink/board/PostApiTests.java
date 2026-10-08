package com.wink.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PostApiTests {
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;

    @Test
    void postLifecycle() throws Exception {
        String response = mvc.perform(post("/api/posts").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"첫 게시글\",\"content\":\"내용\",\"writer\":\"정재훈\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.likeCount").value(0))
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andReturn().getResponse().getContentAsString();
        long id = mapper.readTree(response).get("id").asLong();
        String path = "/api/posts/" + id;

        mvc.perform(get("/api/posts")).andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.id == " + id + ")]").isNotEmpty());
        mvc.perform(get(path)).andExpect(status().isOk())
                .andExpect(jsonPath("$.writer").value("정재훈"));
        mvc.perform(patch(path).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"수정된 게시글\",\"content\":\"수정된 내용\"}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.title").value("수정된 게시글"))
                .andExpect(jsonPath("$.content").value("수정된 내용"))
                .andExpect(jsonPath("$.writer").value("정재훈"));
        mvc.perform(post(path + "/likes")).andExpect(status().isOk())
                .andExpect(jsonPath("$.likeCount").value(1));
        mvc.perform(get(path)).andExpect(jsonPath("$.title").value("수정된 게시글"))
                .andExpect(jsonPath("$.likeCount").value(1));
        mvc.perform(delete(path)).andExpect(status().isNoContent()).andExpect(content().string(""));
        mvc.perform(get("/api/posts")).andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.id == " + id + ")]").isEmpty());
    }
}
