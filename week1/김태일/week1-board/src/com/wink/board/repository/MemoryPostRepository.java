package com.wink.board.repository;

import com.wink.board.domain.Notice;
import com.wink.board.domain.Post;
import java.util.*;

public class MemoryPostRepository implements PostRepository {

    private final Map<Long, Post> store = new HashMap<>();
    private Long sequence = 0L;   // id 자동 증가용 (저장소가 책임)

    @Override
    public Post save(Post post) {
        if (post.getId() == null) {
            post.assignId(++sequence);
        }
        store.put(post.getId(), post);
        return post;
    }

    @Override
    public List<Post> findAll() {
        // 공지가 맨 위, 그 안에서는 id 순
        return store.values().stream()
                .sorted(Comparator.comparing((Post p) -> !(p instanceof Notice))
                        .thenComparing(Post::getId))
                .toList();
    }

    @Override
    public List<Post> findByTitle(String keyword) {
        return store.values().stream()
                .filter(post -> post.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }

    @Override
    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public void deleteById(Long id) {
        store.remove(id);
    }
}
