package com.wink.board.repository;

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
        List<Post> posts = new ArrayList<>(store.values());

        posts.sort(
                Comparator.comparing(Post::getIsNotice).reversed()
        );

        return posts;
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