package com.assistant.savedocument.service.document;

import com.assistant.savedocument.model.request.document.SaveDocumentBase64Request;
import com.assistant.savedocument.model.request.document.SaveDocumentRequest;
import com.assistant.savedocument.model.response.DocumentInfoResponse;
import com.assistant.savedocument.model.response.DocumentListResponse;
import org.springframework.stereotype.Service;

@Service
public interface DocumentService {

    /**
     * Save user document information via, multipart file format
     * @param request
     * @return
     */
    Boolean save(SaveDocumentRequest request);


    /**
     * Save user multiple documents information via, multipart files format
     * @param request
     * @return
     */
    Boolean saveMultipleFile(SaveDocumentRequest request);

    /**
     * Save document information via. base64 format
     * @param request
     * @return
     */
    Boolean saveBase64(SaveDocumentBase64Request request);

    /**
     * User documents information response
     * @param username
     * @return
     */
    DocumentListResponse documents(String username);

    /**
     * Specific selected document info
     * @param documentId
     * @return
     */
    DocumentInfoResponse documentInfo(Long documentId);
}
