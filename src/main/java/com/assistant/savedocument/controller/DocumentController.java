package com.assistant.savedocument.controller;

import com.assistant.savedocument.model.RestResponse;
import com.assistant.savedocument.model.request.document.SaveDocumentBase64Request;
import com.assistant.savedocument.model.request.document.SaveDocumentRequest;
import com.assistant.savedocument.model.response.DocumentInfoResponse;
import com.assistant.savedocument.model.response.DocumentListResponse;
import com.assistant.savedocument.service.document.DocumentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/doc/v1")
@Api(value = "Document Operation", description = "Rest API for all Document operations", tags = {"document"})
public class DocumentController {

    private final DocumentService documentService;

    @ApiOperation(value = "Save document with multipart file")
    @PostMapping("/save")
    public ResponseEntity<RestResponse<Boolean>> uploadDocument(@ApiParam(required = true, value = "MultipartFile") @RequestParam("file") MultipartFile file,
                                                       @ApiParam(required = true, value = "Username", example = "testusername") @RequestParam("username") String username,
                                                       @ApiParam(required = true, value = "User Id", example = "1") @RequestParam("id") Long id) {
        final SaveDocumentRequest request = SaveDocumentRequest.builder()
                .file(file)
                .username(username)
                .userId(id)
                .build();

        return ResponseEntity.ok(new RestResponse<>(200, documentService.save(request)));
    }

    @ApiOperation(value = "Save document with multipart files")
    @PostMapping("/save-multipart")
    public ResponseEntity<RestResponse> uploadDocuments(@ApiParam(required = true, value = "MultipartFile") @RequestParam("file") MultipartFile[] files,
                                                        @ApiParam(required = true, value = "Username", example = "testusername") @RequestParam("username") String username,
                                                        @ApiParam(required = true, value = "User Id", example = "1") @RequestParam("id") Long id) {
        final SaveDocumentRequest request = SaveDocumentRequest.builder()
                .files(files)
                .username(username)
                .userId(id)
                .build();

        return ResponseEntity.ok(new RestResponse<>(200, documentService.saveMultipleFile(request)));
    }

    @PostMapping(value = "/save-base64", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse<Boolean>> uploadBase64Document(@ApiParam(required = true, value = "Save document with base64 string request model body") @RequestBody SaveDocumentBase64Request request) {

        return ResponseEntity.ok(new RestResponse<>(200, documentService.saveBase64(request)));
    }

    @GetMapping("/document-list/{username}/{userId}")
    public ResponseEntity<RestResponse<DocumentListResponse>> documentList(@ApiParam(required = true, value = "Username", example = "testusername") @PathVariable String username,
                                                                           @ApiParam(required = true, value = "User Id", example = "1") @PathVariable Long userId) {

        return ResponseEntity.ok(new RestResponse<>(200, documentService.documents(username, userId)));
    }

    @GetMapping("/specific-document/{documentId}")
    public ResponseEntity<RestResponse<DocumentInfoResponse>> documentInfo(@ApiParam(required = true, value = "Document Id", example = "1") @PathVariable Long documentId) {

        return ResponseEntity.ok(new RestResponse<>(200, documentService.documentInfo(documentId)));
    }

    @GetMapping("/download-document/{documentId}")
    public ResponseEntity<ByteArrayResource> downloadDocument(@ApiParam(required = true, value = "Document Id", example = "1") @PathVariable Long documentId) {

        final DocumentInfoResponse response = documentService.documentInfo(documentId);
        // Create a ByteArrayResource from the file data

        ByteArrayResource resource = new ByteArrayResource(response.getDocument().getData());

        return ResponseEntity.ok()
                .contentLength(response.getDocument().getData().length)
                .contentType(MediaType.parseMediaType(response.getDocument().getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + response.getDocument().getFileName() + "\"")
                .body(resource);

    }
}