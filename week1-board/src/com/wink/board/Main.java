package com.wink.board;

import com.wink.board.domain.Post;
import com.wink.board.exception.InvalidPostException;
import com.wink.board.repository.*;
import com.wink.board.service.PostService;
import java.util.List;
import java.util.Scanner;

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
                    case "4" -> searchPost();
                    case "5" -> update();
                    case "6" -> delete();
                    case "7" -> like();
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
        System.out.println("1. 작성  2. 전체조회  3. 상세조회 4. 검색");
        System.out.println("5. 수정  6. 삭제     7. 좋아요   0. 종료");
        System.out.print("선택 > ");
    }

    private static void create() {
        System.out.print("제목: ");
        String title = sc.nextLine();

        if (title == null || title.isBlank()) {
            throw new InvalidPostException("제목은 비어 있을 수 없습니다.");
        }
        if (title.length() > 20){
            throw new InvalidPostException("제목은 20자를 이하여야 합니다.");
        }

        System.out.print("내용: ");
        String content = sc.nextLine();
        System.out.print("작성자: ");
        String writer = sc.nextLine();

        Post post = postService.create(title, content, writer);
        System.out.println("✅ 등록 완료! id=" + post.getId());
    }

    private static void searchPost(){
        System.out.print("검색할 제목: ");
        String title = sc.nextLine();
        if(title == null || title.isBlank()){
            throw new InvalidPostException("제목은 비어 있을 수 없습니다.");
        }

        List<Post> filteredPosts = postService.findByTitle(title);

        if (filteredPosts.isEmpty()){
            System.out.println("게시글이 없습니다.");
            return;
        }

        System.out.println("검색된 게시글");

        for (Post post : filteredPosts) {
            System.out.printf("[%d]%s (%s) ♥%d%n",
                    post.getId(), post.getTitle(), post.getWriter(), post.getLikeCount());
        }


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
        System.out.println("─────────────");
        System.out.println("제목: " + post.getTitle());
        System.out.println("작성자: " + post.getWriter());
        System.out.println("내용: " + post.getContent());
        System.out.println("좋아요: " + post.getLikeCount());
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
}