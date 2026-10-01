package com.wink.board.repository;

import com.wink.board.domain.Post;

import java.util.*;

public class MemoryPostRepository implements PostRepository {

    private final Map<Long, Post> store = new HashMap<>();
    private long sequence = 0L;

    @Override
    public Long nextId() {
        return sequence + 1;
    }

    @Override
    public Post save(Post post) {
        store.put(post.getId(), post);
        sequence = Math.max(sequence, post.getId());
        return post;
    }

    @Override
    public List<Post> findAll() {
        return new ArrayList<>(store.values());
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
