package com.wink.board.service;

import com.wink.board.domain.Notice;
import com.wink.board.domain.Post;
import com.wink.board.domain.User;
import com.wink.board.exception.InvalidPostException;
import com.wink.board.exception.PostNotFoundException;
import com.wink.board.repository.PostRepository;
import java.util.List;

public class PostService {

    private final PostRepository postRepository;

    // 어떤 구현체(Memory/File)가 들어오든 PostService는 인터페이스만 안다
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post create(String title, String content, User writer) {
        validateTitle(title);
        return postRepository.save(new Post(title, content, writer));
    }

    public Post createNotice(String title, String content, User writer) {
        validateTitle(title);
        return postRepository.save(new Notice(title, content, writer));
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public List<Post> findByTitle(String keyword) {
        return postRepository.findByTitle(keyword);
    }

    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(
                        "존재하지 않는 게시글입니다. id=" + id));
    }

    public Post update(Long id, String title, String content) {
        validateTitle(title);
        Post post = findById(id);
        post.update(title, content);
        return postRepository.save(post);   // 변경 사항을 저장소에 반영
    }

    public void delete(Long id) {
        findById(id);              // 없으면 여기서 예외
        postRepository.deleteById(id);
    }

    public Post like(Long id, User user) {
        Post post = findById(id);
        post.like(user);           // 같은 유저면 여기서 DuplicateLikeException
        return postRepository.save(post);
    }

    private void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new InvalidPostException("제목은 비어 있을 수 없습니다.");
        }
        if (title.length() > 20) {
            throw new InvalidPostException("제목의 최대 길이는 20자입니다.");
        }
    }
}
