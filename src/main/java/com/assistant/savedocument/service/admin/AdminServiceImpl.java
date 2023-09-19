package com.assistant.savedocument.service.admin;

import com.assistant.savedocument.DocumentConstant;
import com.assistant.savedocument.entity.DocumentEntity;
import com.assistant.savedocument.exception.AuthException;
import com.assistant.savedocument.model.admin.DashboardModel;
import com.assistant.savedocument.model.enums.RoleTypes;
import com.assistant.savedocument.repository.DocumentRepository;
import com.assistant.savedocument.repository.UserRepository;
import com.assistant.savedocument.service.auth.JwtUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
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

    private final MessageSource messageSource;

    @Override
    public DashboardModel dashboardService(String username) {
        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
        final Optional<GrantedAuthority> grantedAuthority = (Optional<GrantedAuthority>) userDetails.getAuthorities().stream().findFirst();
        if (grantedAuthority.isPresent() && grantedAuthority.get().getAuthority().equals(RoleTypes.ROLE_ADMIN.name())) {
            final List<DocumentEntity> documentList = documentRepository.findAll();
            return new DashboardModel(userCount(), numberDocumentSaveCount(), countFileType(documentList));
        }
        throw new com.assistant.savedocument.exception.AuthException((messageSource.getMessage(DocumentConstant.Exception.AUTH_DASHBOARD_REJECTED, null, Locale.ENGLISH)));
    }

    private Long userCount() {
        return userRepository.count();
    }

    private Long numberDocumentSaveCount() {
        return documentRepository.count();
    }

    private Map<String, Integer> countFileType(List<DocumentEntity> documentList) {

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
