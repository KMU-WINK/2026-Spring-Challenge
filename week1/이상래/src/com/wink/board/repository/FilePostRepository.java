package com.wink.board.repository;

import com.wink.board.domain.Post;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

// 메모리에 들고 있다가, 바뀔 때마다 전체를 파일 하나에 통째로 다시 쓴다
public class FilePostRepository implements PostRepository {

    private final Path file;
    private Map<Long, Post> store = new HashMap<>();
    private long sequence = 0L;

    public FilePostRepository(Path file) {
        this.file = file;
        load();
    }

    @Override
    public Long nextId() {
        return sequence + 1;
    }

    @Override
    public Post save(Post post) {
        store.put(post.getId(), post);
        sequence = Math.max(sequence, post.getId());
        flush();
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
        flush();
    }

    // sequence도 같이 저장해야, 마지막 글을 지우고 재시작해도 id가 재사용되지 않는다
    @SuppressWarnings("unchecked")
    private void load() {
        if (Files.notExists(file)) {
            return;
        }
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(file))) {
            sequence = in.readLong();
            store = (Map<Long, Post>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalStateException("게시글 파일을 읽지 못했습니다: " + file, e);
        }
    }

    private void flush() {
        try {
            if (file.getParent() != null) {
                Files.createDirectories(file.getParent());
            }
            try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(file))) {
                out.writeLong(sequence);
                out.writeObject(store);
            }
        } catch (IOException e) {
            throw new UncheckedIOException("게시글 파일을 저장하지 못했습니다: " + file, e);
        }
    }
}
