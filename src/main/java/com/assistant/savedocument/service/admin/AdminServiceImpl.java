package com.assistant.savedocument.service.admin;

import com.assistant.savedocument.entity.DocumentEntity;
import com.assistant.savedocument.model.admin.DashboardModel;
import com.assistant.savedocument.repository.DocumentRepository;
import com.assistant.savedocument.repository.UserRepository;
import com.assistant.savedocument.service.auth.JwtUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {

    private final JwtUserDetailsService jwtUserDetailsService;

    private final UserRepository userRepository;

    private final DocumentRepository documentRepository;

    @Override
    public DashboardModel dashboardService(String username) {
        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);

        if (userDetails.getAuthorities().stream().findFirst().get().getAuthority().equals("ROLE_ADMIN")) {
            DashboardModel dashboardModel = new DashboardModel(null, null, countFileType());

            return dashboardModel;
        }

        return null;
    }


    private Map<String, Integer> countFileType() {

        final List<DocumentEntity> documentList = documentRepository.findAll();
        Map<String, Integer> countFileType = new HashMap<>();

        for (DocumentEntity documentEntity : documentList) {
            if (countFileType.containsKey(documentEntity.getFileType())) {
                countFileType.put(documentEntity.getFileType(), countFileType.get(documentEntity.getFileType()) + 1);
            } else {
                countFileType.put(documentEntity.getFileType(), 1);
            }
        }
        return countFileType;
    }
}
