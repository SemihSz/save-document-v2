package com.assistant.savedocument.service.auth;

import com.assistant.savedocument.model.request.auth.AuthRequest;
import com.assistant.savedocument.model.request.auth.UserRegisterRequestDTO;
import com.assistant.savedocument.model.response.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    Boolean registerUser(UserRegisterRequestDTO registerRequest);

    JwtResponse loginUser(AuthRequest authRequest);
}