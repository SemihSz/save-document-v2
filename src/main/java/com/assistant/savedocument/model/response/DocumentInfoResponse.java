package com.assistant.savedocument.model.response;

import com.assistant.savedocument.model.DocumentInfoDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Semih, 3.07.2023
 */
@Getter
@Setter
@Builder
public class DocumentInfoResponse {

    private DocumentInfoDTO document;
}
