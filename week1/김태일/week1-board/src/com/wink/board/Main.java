package com.wink.board;

import com.wink.board.domain.Post;
import com.wink.board.domain.Notice;
import com.wink.board.domain.User;
import com.wink.board.repository.FilePostRepository;
import com.wink.board.service.PostService;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final PostService postService =
            new PostService(new FilePostRepository());   // MemoryPostRepository로 바꿔도 PostService는 그대로

    public static void main(String[] args) {
        while (true) {
            printMenu();
            String input = sc.nextLine();

            try {
                switch (input) {
                    case "1" -> create();
                    case "2" -> createNotice();
                    case "3" -> findAll();
                    case "4" -> findOne();
                    case "5" -> findByTitle();
                    case "6" -> update();
                    case "7" -> delete();
                    case "8" -> like();
                    case "0" -> {
                        System.out.println("종료합니다.");
                        return;
                    }
                    default -> System.out.println("잘못된 입력입니다.");
                }
            } catch (Exception e) {
                System.out.println("⚠️  " + e.getMessage());
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=== 커뮤니티 게시판 ===");
        System.out.println("1. 작성  2. 공지작성  3. 전체조회  4. 상세조회  5. 키워드검색");
        System.out.println("6. 수정  7. 삭제  8. 좋아요  0. 종료");
        System.out.print("선택 > ");
    }

    private static void create() {
        System.out.print("제목: ");
        String title = sc.nextLine();
        System.out.print("내용: ");
        String content = sc.nextLine();
        System.out.print("작성자: ");
        User writer = new User(sc.nextLine());

        Post post = postService.create(title, content, writer);
        System.out.println("✅ 등록 완료! id=" + post.getId());
    }

    private static void createNotice() {
        System.out.print("제목: ");
        String title = sc.nextLine();
        System.out.print("내용: ");
        String content = sc.nextLine();
        System.out.print("작성자: ");
        User writer = new User(sc.nextLine());

        Post post = postService.createNotice(title, content, writer);
        System.out.println("✅ 공지 등록 완료! id=" + post.getId());
    }

    private static void findAll() {
        List<Post> posts = postService.findAll();
        if (posts.isEmpty()) {
            System.out.println("게시글이 없습니다.");
            return;
        }
        for (Post post : posts) {

            if (post instanceof Notice) {
                System.out.printf("[%d] [공지] %s (%s) ♥%d%n",
                        post.getId(), post.getTitle(), post.getWriter(), post.getLikeCount());
            } else {
                System.out.printf("[%d] %s (%s) ♥%d%n",
                        post.getId(), post.getTitle(), post.getWriter(), post.getLikeCount());
            }
        }
    }

    private static void findOne() {
        Post post = postService.findById(inputId());
        System.out.println("─────────────");
        System.out.println("제목: " + post.getTitle());
        System.out.println("작성자: " + post.getWriter());
        System.out.println("내용: " + post.getContent());
        System.out.println("좋아요: " + post.getLikeCount());
        System.out.println("작성시간: " + post.getCreatedAt());
    }

    private static void findByTitle() {
        System.out.print("제목에서 검색할 키워드: ");
        String keyword = sc.nextLine();

        List<Post> result = postService.findByTitle(keyword);

        if(result.isEmpty()) {
            System.out.println("검색 결과가 없습니다.");
        } else {
            result.forEach(post -> System.out.println(post.getId() + " | " + post.getTitle()));
        }
    }

    private static void update() {
        Long id = inputId();
        System.out.print("새 제목: ");
        String title = sc.nextLine();
        System.out.print("새 내용: ");
        String content = sc.nextLine();
        postService.update(id, title, content);
        System.out.println("✅ 수정 완료!");
    }

    private static void delete() {
        postService.delete(inputId());
        System.out.println("✅ 삭제 완료!");
    }

    private static void like() {
        Long id = inputId();
        System.out.print("좋아요 누르는 사용자: ");
        User user = new User(sc.nextLine());
        Post post = postService.like(id, user);
        System.out.println("♥ 좋아요 " + post.getLikeCount() + "개");
    }

    private static Long inputId() {
        System.out.print("게시글 번호: ");
        return Long.parseLong(sc.nextLine());
    }
}