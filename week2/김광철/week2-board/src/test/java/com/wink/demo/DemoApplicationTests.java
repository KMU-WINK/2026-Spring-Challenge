package com.wink.demo;

import com.wink.board.dto.PostSearchRequest;
import com.wink.board.exception.PostNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import com.wink.board.controller.PostController;
import com.wink.board.service.PostService;
import com.wink.board.dto.PostCreateRequest;
import com.wink.board.dto.PostUpdateRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    private PostController controller;

    @Autowired
    private PostService service;

    @Test
    void contextLoads() {
        assertNotNull(controller);
    }

    @Test
    void postLifecycle() {
        var created = service.create(new PostCreateRequest("제목", "내용", "작성자"));
        var id = created.id();
        try {
            assertEquals("제목", service.findById(id).title());
            assertTrue(service.findAll(new PostSearchRequest("작성자", 0, 10)).stream().anyMatch(post -> post.id().equals(id)));
            assertEquals("수정", service.update(id, new PostUpdateRequest("수정", "수정 내용")).title());
            assertEquals(1, service.like(id).likeCount());
        } finally {
            service.delete(id);
        }
        assertThrows(PostNotFoundException.class, () -> service.findById(id));
    }

}
