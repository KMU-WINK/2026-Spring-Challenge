package com.wink.board.repository;

import com.wink.board.domain.Notice;
import com.wink.board.domain.Post;
import java.util.*;

public class MemoryPostRepository implements PostRepository {

    private final Map<Long, Post> store = new HashMap<>();

    @Override
    public Post save(Post post) {
        store.put(post.getId(), post);
        return post;
    }

    @Override
    public List<Post> findAll() {
        return store.values().stream()
                .sorted((p1, p2) -> {
                    if (p1 instanceof Notice && !(p2 instanceof Notice)) {
                        return -1;
                    }
                    if (!(p1 instanceof Notice) && p2 instanceof Notice) {
                        return 1;
                    }
                    return 0;
                })
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