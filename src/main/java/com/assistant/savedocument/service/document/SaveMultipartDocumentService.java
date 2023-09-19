package com.assistant.savedocument.service.document;

import com.assistant.savedocument.entity.DocumentEntity;
import com.assistant.savedocument.model.request.document.SaveDocumentRequest;
import com.assistant.savedocument.repository.DocumentRepository;
import com.assistant.savedocument.task.SimpleTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.Executors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SaveMultipartDocumentService implements SimpleTask<SaveDocumentRequest, Boolean> {

    private final DocumentRepository documentRepository;

    private final MultipartFileControlService fileControlService;


    private static final DataSize MAX_FILE_SIZE = DataSize.ofMegabytes(5);
    
    @Override
    public Boolean apply(SaveDocumentRequest request) {

        var virtualThreadExecutor = Executors.newVirtualThreadPerTaskExecutor();
        
        for (MultipartFile multipartFile: request.getFiles()) {
            virtualThreadExecutor.execute(() -> {
                final String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));

                /**
                 * Control file size and allowed extensions
                 */
                try {
                    fileControlService.accept(multipartFile);
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }

                try {
                    final DocumentEntity document = DocumentEntity.builder()
                            .userId(request.getUserId())
                            .username(request.getUsername())
                            .fileType(multipartFile.getContentType())
                            .fileName(fileName)
                            .data(multipartFile.getBytes())
                            .time(LocalDateTime.now())
                            .build();
                    documentRepository.save(document);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        return Boolean.TRUE;
    }
}
