package com.wink.board.repository;

import com.wink.board.domain.Post;
import com.wink.board.service.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.*;

@Repository
public class FilePostRepository implements PostRepository {
    private final Map<Long, Post> store = new HashMap<>();
    private final Path path;
    private final Converter converter = new Converter();

    @Autowired
    public FilePostRepository() {
        this(defaultPath());
    }

    // 프로젝트 폴더와 저장소 루트 양쪽에서 week2 데이터를 사용한다.
    private static Path defaultPath() {
        Path project = Path.of("week2", "김광철", "week2-board");
        return (Files.isDirectory(project) ? project : Path.of(""))
                .resolve("data").resolve("posts.txt");
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
    public Long nextId() {
        return store.keySet().stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0L) + 1;
    }

    @Override
    public Post save(Post post) {
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
