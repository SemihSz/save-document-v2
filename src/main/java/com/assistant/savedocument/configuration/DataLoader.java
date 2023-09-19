package com.assistant.savedocument.configuration;

import com.assistant.savedocument.entity.RoleEntity;
import com.assistant.savedocument.model.enums.RoleTypes;
import com.assistant.savedocument.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements ApplicationRunner {

    private final RoleRepository repository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        log.info("Initial save operation start");
        repository.save(new RoleEntity(1L, RoleTypes.ROLE_ADMIN));
        repository.save(new RoleEntity(2L, RoleTypes.ROLE_USER));
        repository.save(new RoleEntity(3L, RoleTypes.ROLE_MODERATOR));
        log.info("Initial save operation finished");
    }
}
