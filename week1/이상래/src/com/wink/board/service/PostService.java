package com.wink.board.service;

import com.wink.board.domain.Notice;
import com.wink.board.domain.Post;
import com.wink.board.exception.InvalidPostException;
import com.wink.board.exception.PostNotFoundException;
import com.wink.board.repository.PostRepository;

import java.util.Comparator;
import java.util.List;

public class PostService {

    // 공지를 먼저, 같은 종류끼리는 id 순으로
    private static final Comparator<Post> NOTICE_FIRST =
            Comparator.comparing(Post::isNotice).reversed()
                    .thenComparing(Post::getId);

    private final PostRepository postRepository;
    private Long sequence = 0L;   // id 자동 증가용

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 제목 검증은 Post가 스스로 한다 (생성자, update 모두)
    public Post create(String title, String content, String writer) {
        return register(new Post(sequence + 1, title, content, writer));
    }

    public Post createNotice(String title, String content, String writer) {
        return register(new Notice(sequence + 1, title, content, writer));
    }

    // 검증을 통과해 객체가 만들어진 뒤에만 번호를 올린다
    private Post register(Post post) {
        sequence++;
        return postRepository.save(post);
    }

    // 정렬은 저장소가 아니라 서비스의 규칙 → 저장소를 갈아끼워도 공지는 항상 위
    public List<Post> findAll() {
        return postRepository.findAll().stream()
                .sorted(NOTICE_FIRST)
                .toList();
    }

    // 저장소에 검색 메서드를 추가하지 않고 서비스에서 거른다
    // → 저장소를 갈아끼워도 검색 기능은 그대로 동작
    public List<Post> search(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            throw new InvalidPostException("검색어를 입력해주세요.");
        }
        return findAll().stream()
                .filter(post -> post.getTitle().contains(keyword))
                .toList();
    }

    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    public Post update(Long id, String title, String content) {
        Post post = findById(id);
        post.update(title, content);
        return post;
    }

    public void delete(Long id) {
        findById(id);              // 없으면 여기서 예외
        postRepository.deleteById(id);
    }

    public Post like(Long id) {
        Post post = findById(id);
        post.like();
        return post;
    }
}
