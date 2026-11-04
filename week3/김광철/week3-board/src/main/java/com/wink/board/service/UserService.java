package com.wink.board.service;

import com.wink.board.domain.User;
import com.wink.board.dto.UserCreateRequest;
import com.wink.board.dto.UserResponse;
import com.wink.board.exception.DuplicateNicknameException;
import com.wink.board.exception.UserNotFoundException;
import com.wink.board.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponse create(UserCreateRequest request) {
        if (userRepository.existsByNickname(request.nickname())) {
            throw new DuplicateNicknameException(request.nickname());
        }
        User user = userRepository.save(new User(request.nickname()));
        return UserResponse.from(user);
    }

    public UserResponse findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return UserResponse.from(user);
    }
}