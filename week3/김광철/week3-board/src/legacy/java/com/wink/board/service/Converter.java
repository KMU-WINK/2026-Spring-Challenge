package com.wink.board.service;

import com.wink.board.domain.Post;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Converter {
    // 저장 파일이 URL용 Base64 형식인지 확인하기 위한 표시.
    private static final String FORMAT = "BASE64_URL_V1";

    public String convertPost(Post post) {
        String encodedTitle = encode(post.getTitle());
        String encodedContent = encode(post.getContent());
        String encodedWriter = encode(post.getWriter());

        return String.join("/",
                FORMAT,
                post.getId().toString(),
                encodedTitle,
                encodedContent,
                encodedWriter,
                post.getCreatedAt(),
                post.getIsNotice().toString(),
                Integer.toString(post.getLikeCount()));
    }

    public Post restorePost(String savedPost) {
        String[] fields = savedPost.split("/", -1);
        if (fields.length != 8 || !FORMAT.equals(fields[0])) {
            throw new IllegalArgumentException("게시글 저장 형식이 올바르지 않습니다.");
        }

        long id = Long.parseLong(fields[1]);
        String title = decode(fields[2]);
        String content = decode(fields[3]);
        String writer = decode(fields[4]);
        String createdAt = fields[5];
        String notice = fields[6];
        int likeCount = Integer.parseInt(fields[7]);

        if (!"true".equals(notice) && !"false".equals(notice)) {
            throw new IllegalArgumentException("공지 여부는 true 또는 false여야 합니다.");
        }

        return new Post(id, title, content, writer, createdAt,
                Boolean.parseBoolean(notice), likeCount);
    }

    private String encode(String text) {
        return Base64.getUrlEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
    }

    private String decode(String encodedText) {
        byte[] bytes = Base64.getUrlDecoder().decode(encodedText);
        return new String(bytes, StandardCharsets.UTF_8);
    }
}