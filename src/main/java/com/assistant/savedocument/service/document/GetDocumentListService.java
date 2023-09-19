package com.assistant.savedocument.service.document;

import com.assistant.savedocument.entity.DocumentEntity;
import com.assistant.savedocument.model.DocumentInfoDTO;
import com.assistant.savedocument.model.response.DocumentListResponse;
import com.assistant.savedocument.repository.DocumentRepository;
import com.assistant.savedocument.task.Mappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Semih, 3.07.2023
 */
@Service
@RequiredArgsConstructor
public class GetDocumentListService implements Mappers<String, Long, DocumentListResponse> {

    private final DocumentRepository documentRepository;

    /**
     * Get user documents information, response to documents list.
     * @param username the first function argument
     * @param userId the second function argument
     * @return DocumentListResponse
     */
    @Override
    public DocumentListResponse apply(String username, Long userId) {

        final List<DocumentEntity> userDocuments = documentRepository.findByUsernameDocuments(username);
        final List<DocumentInfoDTO> base64Files = new ArrayList<>();
        if (Objects.nonNull(userDocuments)) {

            userDocuments.forEach(t -> {
                final DocumentInfoDTO infoDTO = DocumentInfoDTO.builder()
                        .userId(t.getUserId())
                        .username(t.getUsername())
                        .documentId(t.getId())
                        .data(t.getData())
                        .fileName(t.getFileName())
                        .fileType(t.getFileType())
                        .time(t.getTime())
                        .build();
                base64Files.add(infoDTO);
            });
        }
        return DocumentListResponse.builder().documents(base64Files).build();
    }
}
