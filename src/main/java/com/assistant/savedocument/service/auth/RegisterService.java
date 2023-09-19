package com.assistant.savedocument.service.auth;

import com.assistant.savedocument.DocumentConstant;
import com.assistant.savedocument.entity.RoleEntity;
import com.assistant.savedocument.entity.UserEntity;
import com.assistant.savedocument.model.enums.RoleTypes;
import com.assistant.savedocument.model.request.auth.UserRegisterRequestDTO;
import com.assistant.savedocument.repository.RoleRepository;
import com.assistant.savedocument.repository.UserRepository;
import com.assistant.savedocument.task.SimpleTask;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * Created by Semih, 2.07.2023
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterService implements SimpleTask<UserRegisterRequestDTO, Boolean> {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final MessageSource messageSource;

    /**
     *  This executable task manage the all register operation. If the username or email has been existed, service will return ${@link AuthException}
     * @param registerRequest the function argument
     * @return
     */
    @Override
    public Boolean apply(UserRegisterRequestDTO registerRequest) {

        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new com.assistant.savedocument.exception.AuthException(messageSource.getMessage(DocumentConstant.Exception.AUTH_USER_EXIST, null, Locale.ENGLISH));
        } else if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new com.assistant.savedocument.exception.AuthException((messageSource.getMessage(DocumentConstant.Exception.AUTH_EMAIL_EXIST, null, Locale.ENGLISH)));
        }
        BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();
        registerRequest.setPassword(bcryptEncoder.encode(registerRequest.getPassword()));
        try {
            final String strRoles = registerRequest.getRole();
            Set<RoleEntity> roles = new HashSet<>();
            // TODO Upgrade here!
            if (strRoles == null) {
                final RoleEntity userRole = roleRepository.findByName(RoleTypes.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            } else {
                switch (strRoles) {
                    case "admin":
                        RoleEntity adminRole = roleRepository.findByName(RoleTypes.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        RoleEntity modRole = roleRepository.findByName(RoleTypes.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        RoleEntity userRole = roleRepository.findByName(RoleTypes.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            }

            final UserEntity user = UserEntity.builder()
                    .username(registerRequest.getUsername())
                    .password(registerRequest.getPassword())
                    .roles(roles)
                    .build();
            userRepository.save(user);

            return Boolean.TRUE;

        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }
}
