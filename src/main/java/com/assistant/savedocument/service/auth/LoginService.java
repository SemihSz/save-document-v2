package com.assistant.savedocument.service.auth;

import com.assistant.savedocument.model.request.auth.AuthRequest;
import com.assistant.savedocument.model.response.JwtResponse;
import com.assistant.savedocument.task.SimpleTask;
import com.assistant.savedocument.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Semih, 2.07.2023
 */

@Service
@RequiredArgsConstructor
public class LoginService implements SimpleTask<AuthRequest, JwtResponse> {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    /**
     * This executable task process to login operations using with JWT token
     * @param authRequest the function argument
     * @return ${{@link JwtResponse} returning token information to user.}
     */
    @Override
    public JwtResponse apply(AuthRequest authRequest) {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String jwt = jwtTokenUtil.generateJwtToken(authentication);

        final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        final List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }
}
