package com.assistant.savedocument.service.document;

import com.assistant.savedocument.entity.DocumentEntity;
import com.assistant.savedocument.model.DocumentInfoDTO;
import com.assistant.savedocument.model.response.DocumentInfoResponse;
import com.assistant.savedocument.repository.DocumentRepository;
import com.assistant.savedocument.task.SimpleTask;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Created by Semih, 3.07.2023
 */
@Service
@RequiredArgsConstructor
public class GetDocumentInfoService implements SimpleTask<Long, DocumentInfoResponse> {

    private final DocumentRepository documentRepository;

    /**
     * Get specific document information executable service
     * @param documentId the function argument
     * @return DocumentInfoResponse
     */
    @Override
    public DocumentInfoResponse apply(Long documentId) {

        final DocumentEntity documentInfo = documentRepository.findByDocumentId(documentId);

        if (Objects.nonNull(documentInfo)) {
            final DocumentInfoDTO documentInfoDTO = DocumentInfoDTO.builder()
                    .userId(documentInfo.getUserId())
                    .documentId(documentInfo.getId())
                    .fileType(documentInfo.getFileType())
                    .fileName(documentInfo.getFileName())
                    .data(documentInfo.getData())
                    .time(documentInfo.getTime())
                    .build();
            return DocumentInfoResponse.builder().document(documentInfoDTO).build();
        }

        return null;
    }
}
