package com.wink.board.domain;

import com.wink.board.exception.InvalidPostException;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String name;

    public User(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidPostException("이름은 비어 있을 수 없습니다.");
        }
        this.name = name;
    }

    public String getName() { return name; }

    // 이름이 같으면 같은 유저로 본다 → Set이 중복 좋아요를 걸러내는 기준
    // equals와 hashCode는 반드시 같이 재정의해야 HashSet이 제대로 동작한다
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return name.equals(user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
