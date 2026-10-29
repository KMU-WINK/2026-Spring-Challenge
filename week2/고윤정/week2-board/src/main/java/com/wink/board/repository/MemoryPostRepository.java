package com.wink.board.repository;

import com.wink.board.domain.Post;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MemoryPostRepository implements PostRepository {

    private final Map<Long, Post> store = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    @Override
    public Post save(Post post) {
        store.put(post.getId(), post);
        return post;
    }

    @Override
    public List<Post> findAll() {
        return store.values().stream().sorted(Comparator.comparing(Post::getId)).toList();
    }

    @Override
    public List<Post> findByWriter(String writer) {
        return store.values().stream().filter(post -> post.getWriter().equals(writer)).sorted(Comparator.comparing(Post::getId)).toList();
    }

    @Override
    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public void deleteById(Long id) {
        store.remove(id);
    }

    public Long nextId() {
        return sequence.incrementAndGet();
    }
}
