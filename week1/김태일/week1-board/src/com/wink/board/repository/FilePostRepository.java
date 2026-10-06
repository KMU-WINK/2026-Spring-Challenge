package com.wink.board.repository;

import com.wink.board.domain.Notice;
import com.wink.board.domain.Post;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;

/**
 * 게시글을 파일(직렬화)로 저장하는 저장소.
 * 변경이 생길 때마다 파일에 다시 쓰고, 생성 시 파일에서 읽어온다.
 */
public class FilePostRepository implements PostRepository {

    private final Path path;
    private Map<Long, Post> store = new HashMap<>();
    private long sequence = 0L;

    public FilePostRepository() {
        this("posts.dat");
    }

    public FilePostRepository(String fileName) {
        this.path = Path.of(fileName);
        load();
    }

    @Override
    public Post save(Post post) {
        if (post.getId() == null) {
            post.assignId(++sequence);
        }
        store.put(post.getId(), post);
        flush();
        return post;
    }

    @Override
    public List<Post> findAll() {
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
        if (store.remove(id) != null) {
            flush();
        }
    }

    @SuppressWarnings("unchecked")
    private void load() {
        if (!Files.exists(path)) {
            return;   // 첫 실행: 빈 저장소로 시작
        }
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(path))) {
            sequence = in.readLong();
            store = (Map<Long, Post>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalStateException("저장 파일을 읽을 수 없습니다: " + path, e);
        }
    }

    private void flush() {
        // 임시 파일에 먼저 쓰고 교체 → 쓰는 도중 꺼져도 기존 파일이 깨지지 않음
        Path tmp = Path.of(path + ".tmp");
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(tmp))) {
            out.writeLong(sequence);
            out.writeObject(new HashMap<>(store));
        } catch (IOException e) {
            throw new UncheckedIOException("저장 파일에 쓸 수 없습니다: " + path, e);
        }
        try {
            Files.move(tmp, path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new UncheckedIOException("저장 파일에 쓸 수 없습니다: " + path, e);
        }
    }
}
