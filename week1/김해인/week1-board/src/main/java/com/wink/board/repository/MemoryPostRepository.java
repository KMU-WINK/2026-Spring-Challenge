package com.wink.board.repository;

import com.wink.board.domain.Post;
import java.util.*;

//PostRepository Interface의 필수 4가지 기능을 Hashmap으로 구현
public class MemoryPostRepository implements PostRepository{
    //HashMap은 저장 순서 보장X -> LinkedHashMap은 삽입 순서를 유지함
    private final Map<Long, Post> store = new LinkedHashMap<>();

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
