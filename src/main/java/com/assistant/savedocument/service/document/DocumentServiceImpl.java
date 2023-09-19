package com.assistant.savedocument.service.document;

import com.assistant.savedocument.model.request.document.SaveDocumentBase64Request;
import com.assistant.savedocument.model.request.document.SaveDocumentRequest;
import com.assistant.savedocument.model.response.DocumentInfoResponse;
import com.assistant.savedocument.model.response.DocumentListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final SaveDocumentService saveDocumentService;

    private final SaveBase64DocumentService saveBase64DocumentService;

    private final GetDocumentListService getDocumentListService;

    private final GetDocumentInfoService getDocumentInfoService;

    private final  SaveMultipartDocumentService saveMultipartDocumentService;

    @Override
    public Boolean save(SaveDocumentRequest request) {
        return saveDocumentService.apply(request);
    }

    @Override
    public Boolean saveBase64(SaveDocumentBase64Request request) {
        return saveBase64DocumentService.apply(request);
    }

    @Override
    public DocumentListResponse documents(String username, Long userId) {
        return getDocumentListService.apply(username, userId);
    }

    @Override
    public DocumentInfoResponse documentInfo(Long documentId) {
        return getDocumentInfoService.apply(documentId);
    }

    @Override
    public Boolean saveMultipleFile(SaveDocumentRequest request) {
        return saveMultipartDocumentService.apply(request);
    }
}
