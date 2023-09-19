package com.assistant.savedocument.service.auth;

import com.assistant.savedocument.entity.UserEntity;
import com.assistant.savedocument.repository.UserRepository;
import com.assistant.savedocument.task.SimpleTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserInfoService implements SimpleTask<String, UserEntity> {

    private final UserRepository userRepository;

    /**
     * This service getter user entity information for consuming user information.
     * @param username the function argument
     * @return
     */
    @Override
    public UserEntity apply(String username) {

        final UserEntity user = userRepository.findByUsernameEntity(username);

        return Objects.nonNull(user) ?  user : null;
    }
}
