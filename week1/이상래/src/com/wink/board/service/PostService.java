package com.wink.board.service;

import com.wink.board.domain.Notice;
import com.wink.board.domain.Post;
import com.wink.board.domain.User;
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

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 제목 검증은 Post가 스스로 한다 (생성자, update 모두)
    // id는 저장소가 발급한다 → 파일 저장소라면 재시작해도 번호가 이어진다
    public Post create(String title, String content, User writer) {
        return postRepository.save(new Post(postRepository.nextId(), title, content, writer));
    }

    public Post createNotice(String title, String content, User writer) {
        return postRepository.save(new Notice(postRepository.nextId(), title, content, writer));
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

    // 메모리 저장소는 참조 덕분에 save 없이도 반영되지만,
    // 파일·DB 저장소는 save를 불러야 바뀐 내용이 기록된다
    public Post update(Long id, String title, String content) {
        Post post = findById(id);
        post.update(title, content);
        return postRepository.save(post);
    }

    public void delete(Long id) {
        findById(id);              // 없으면 여기서 예외
        postRepository.deleteById(id);
    }

    public Post like(Long id, User user) {
        Post post = findById(id);
        post.like(user);
        return postRepository.save(post);
    }
}
