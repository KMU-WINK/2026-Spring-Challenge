package com.wink.board.domain;

import com.wink.board.exception.InvalidPostException;
import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String name;

    public User(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidPostException("사용자 이름은 비어 있을 수 없습니다.");
        }
        this.name = name.trim();
    }

    public String getName() { return name; }

    // 이름이 같으면 같은 유저로 취급 → Set<User>에서 중복 판별 기준
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        return name.equals(((User) o).name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
