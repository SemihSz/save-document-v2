package com.assistant.savedocument.service.document;

import com.assistant.savedocument.entity.DocumentEntity;
import com.assistant.savedocument.entity.UserEntity;
import com.assistant.savedocument.exception.BusinessException;
import com.assistant.savedocument.model.request.document.SaveDocumentRequest;
import com.assistant.savedocument.repository.DocumentRepository;
import com.assistant.savedocument.service.auth.JwtUserDetailsService;
import com.assistant.savedocument.service.auth.UserInfoService;
import com.assistant.savedocument.task.SimpleTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UserDetails;
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

    private final JwtUserDetailsService jwtUserDetailsService;

    private final UserInfoService userInfoService;


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
                    final UserDetails userInfo = jwtUserDetailsService.loadUserByUsername(request.getUsername());
                    final UserEntity userEntity = userInfoService.apply(request.getUsername());
                    final DocumentEntity document = DocumentEntity.builder()
                            .userId(userEntity.getId())
                            .username(request.getUsername())
                            .fileType(multipartFile.getContentType())
                            .fileName(fileName)
                            .data(multipartFile.getBytes())
                            .time(LocalDateTime.now())
                            .build();
                    documentRepository.save(document);
                } catch (IOException e) {
                    throw new BusinessException( "İşlemlerinizi şu vakit gerçekleştiremiyoruz.");
                }
            });
        }

        // Java Virtual Threads Executor'ünü kapatın (Java 17+ kullanılıyorsa bu gerekli değil)
        if (virtualThreadExecutor instanceof AutoCloseable) {
            try {
                ((AutoCloseable) virtualThreadExecutor).close();
            } catch (Exception e) {
                throw new BusinessException( "İşlemlerinizi şu vakit gerçekleştiremiyoruz.");
            }
        }

        return Boolean.TRUE;
    }
}
