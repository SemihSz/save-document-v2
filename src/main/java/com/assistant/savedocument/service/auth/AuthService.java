package com.assistant.savedocument.service.auth;

import com.assistant.savedocument.model.request.auth.AuthRequest;
import com.assistant.savedocument.model.request.auth.UserRegisterRequestDTO;
import com.assistant.savedocument.model.response.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RegisterService registerService;

    private final LoginService loginService;
    public Boolean registerUser(UserRegisterRequestDTO registerRequest) {

        return registerService.apply(registerRequest);
    }

    public JwtResponse loginUser(AuthRequest authRequest) {

        return loginService.apply(authRequest);
    }

}
