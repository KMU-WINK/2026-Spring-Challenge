package com.wink.board;

import com.wink.board.dto.PostSearchRequest;
import com.wink.board.exception.PostNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import com.wink.board.controller.PostController;
import com.wink.board.service.PostService;
import com.wink.board.dto.PostCreateRequest;
import com.wink.board.dto.PostUpdateRequest;
import com.wink.board.dto.UserCreateRequest;
import com.wink.board.repository.UserRepository;
import com.wink.board.service.UserService;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardApplicationTests {
    @Autowired
    private PostController controller;

    @Autowired
    private PostService service;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void contextLoads() {
        assertNotNull(controller);
    }

    @Test
    void postLifecycle() {
        var nickname = UUID.randomUUID().toString().substring(0, 20);
        var user = userService.create(new UserCreateRequest(nickname));
        try {
            var created = service.create(new PostCreateRequest("제목", "내용", user.id()));
            var id = created.id();
            try {
                assertEquals("제목", service.findById(id).title());
                assertEquals(nickname, created.writerNickname());
                assertTrue(service.findAll(new PostSearchRequest(nickname, 0, 10))
                        .stream().anyMatch(post -> post.id().equals(id)));
                var updated = service.update(id, new PostUpdateRequest("수정", null));
                assertEquals("수정", updated.title());
                assertEquals("내용", updated.content());
            } finally {
                service.delete(id);
            }
            assertThrows(PostNotFoundException.class, () -> service.findById(id));
        } finally {
            userRepository.deleteById(user.id());
        }
    }

}
