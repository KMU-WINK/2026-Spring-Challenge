package com.wink.board.repository;

import com.wink.board.domain.Post;
import com.wink.board.service.Converter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.*;

public class FilePostRepository implements PostRepository {
    private final Map<Long, Post> store = new HashMap<>();
    private final Path path;
    private final Converter converter = new Converter();

    public FilePostRepository() {
        this(Path.of("week1", "김광철", "week1-board", "data", "posts.txt"));
    }

    public FilePostRepository(Path path) {
        this.path = path;
        loadFromFile();
    }

    private void loadFromFile() {
        try {
            Path parent = path.toAbsolutePath().getParent();
            Files.createDirectories(parent);
            String saved;
            try {
                saved = Files.readString(path);
            } catch (NoSuchFileException e) {
                Files.createFile(path);
                return;
            }
            if (saved.isBlank()) {
                return;
            }
            for (String savedPost : saved.split(" \\| ", -1)) {
                Post post = converter.restorePost(savedPost);
                if (store.putIfAbsent(post.getId(), post) != null) {
                    throw new IllegalArgumentException("중복된 게시글 ID: " + post.getId());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("파일 불러오기 실패", e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("게시글 파일 형식이 올바르지 않습니다.", e);
        }
    }

    @Override
    public Post save(Post post) {
        // 신규 저장과 수정 모두 전달된 게시글의 ID를 유지한다.
        store.put(post.getId(), post);
        saveToFile();
        return post;
    }

    private void saveToFile() {
        StringJoiner result = new StringJoiner(" | ");
        for (Post post : store.values()) {
            result.add(converter.convertPost(post));
        }
        try {
            Files.writeString(path, result.toString());
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패", e);
        }
    }

    @Override
    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>(store.values());
        posts.sort(Comparator.comparing(Post::getIsNotice).reversed());
        return posts;
    }

    @Override
    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public void deleteById(Long id) {
        store.remove(id);
        saveToFile();
    }
}
