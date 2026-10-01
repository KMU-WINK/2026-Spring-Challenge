package com.wink.board.repository;

import com.wink.board.domain.Post;
import com.wink.board.service.Converter;

import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FilePostRepository implements PostRepository {

    private final Map<Long, Post> store = new HashMap<>();
    private final Path path = Path.of("./data/posts.txt");

    @Override
    public Post save(Post post) {
        long nextId = store.keySet().stream().mapToLong(Long::longValue).max().orElse(0L) + 1;

        Post savedPost = new Post(nextId, post.getTitle(), post.getContent(), post.getWriter(), post.getCreatedAt(), post.getIsNotice(), post.getLikeCount());

        store.put(savedPost.getId(), savedPost);
        saveToFile();
        return savedPost;
    }

    public void saveToFile(){
        Converter converter = new Converter();
        StringJoiner result = new StringJoiner(" | ");

        for(Post e: store.values()){
            result.add(converter.convertPost(e));
        }

        String convertedString = result.toString();

        try{
            Files.writeString(path, convertedString);
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패", e);
        }
    }

    public void restore(String[] postData) {
        Converter converter = new Converter();
        for (String savedPost : postData) {
            Post post = converter.restorePost(savedPost);
            store.put(post.getId(), post);
        }
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
