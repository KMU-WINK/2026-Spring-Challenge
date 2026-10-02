package com.wink.board.repository;

import com.wink.board.domain.Post;
import java.util.*;

//PostRepository Interface의 필수 4가지 기능을 Hashmap으로 구현
public class MemoryPostRepository implements PostRepository{
    private final Map<Long, Post> store = new HashMap<>();

    @Override
    public Post save(Post post){
        store.put(post.getId(), post);
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
