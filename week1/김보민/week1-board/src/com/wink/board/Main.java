package com.wink.board;

import com.wink.board.domain.*;
import com.wink.board.repository.*;
import com.wink.board.service.PostService;
import java.util.List;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final PostService postService =
            new PostService(new MemoryPostRepository());

    public static void main(String[] args) {
        while (true) {
            printMenu();
            String input = sc.nextLine();

            try {
                switch (input) {
                    case "1" -> create();
                    case "2" -> findAll();
                    case "3" -> findOne();
                    case "4" -> update();
                    case "5" -> delete();
                    case "6" -> like();
                    case "7" -> search();
                    case "8" -> createNotice();
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
        System.out.println("1. 작성  2. 전체조회  3. 상세조회  4. 수정  5. 삭제");
        System.out.println("6. 좋아요  7. 검색        8. 공지작성  0. 종료");
        System.out.print("선택 > ");
    }

    private static void create() {
        System.out.print("제목: ");
        String title = sc.nextLine();
        System.out.print("내용: ");
        String content = sc.nextLine();
        System.out.print("작성자: ");
        String writer = sc.nextLine();

        Post post = postService.create(title, content, writer);
        System.out.println("✅ 등록 완료! id=" + post.getId());
    }

    private static void findAll() {
        List<Post> posts = postService.findAll();
        if (posts.isEmpty()) {
            System.out.println("게시글이 없습니다.");
            return;
        }
        for (Post post : posts) {
            System.out.printf("[%d]%s (%s) ♥%d%n",
                    post.getId(), post.getTitle(), post.getWriter(), post.getLikeCount());
        }
    }

    private static void findOne() {
        Post post = postService.findById(inputId());
        String createdAt = post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        System.out.println("─────────────");
        System.out.println("제목: " + post.getTitle());
        System.out.println("작성자: " + post.getWriter());
        System.out.println("내용: " + post.getContent());
        System.out.println("좋아요: " + post.getLikeCount());
        System.out.println("작성 시간: "+ createdAt);
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
        Post post = postService.like(inputId());
        System.out.println("♥ 좋아요 " + post.getLikeCount() + "개");
    }

    private static Long inputId() {
        System.out.print("게시글 번호: ");
        return Long.parseLong(sc.nextLine());
    }

    private static void search() {
        System.out.print("검색어: ");
        String keyword = sc.nextLine();

        List<Post> results = postService.searchByTitle(keyword);
        if (results.isEmpty()) {
            System.out.println("검색 결과가 없습니다.");
            return;
        }
        for (Post post : results) {
            System.out.printf("[%d]%s (%s) ♥%d%n", post.getId(), post.getTitle(), post.getWriter(), post.getLikeCount());
        }
    }

    private static void createNotice() {
        System.out.print("제목: ");
        String title = sc.nextLine();
        System.out.print("내용: ");
        String content = sc.nextLine();
        System.out.print("작성자: ");
        String writer = sc.nextLine();

        Post post = postService.createNotice(title, content, writer);
        System.out.println("✅ 등록 완료! id=" + post.getId());
    }
}