package com.assistant.savedocument.model.request.auth;

import com.assistant.savedocument.model.enums.RoleTypes;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Semih, 2.07.2023
 */
@Getter
@Setter
public class UserRegisterRequestDTO {

    private String username;

    private String password;

    private String email;

    private RoleTypes role;
}
