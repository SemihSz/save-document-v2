package com.assistant.savedocument.model.admin;

import java.util.Map;
import java.util.Set;

public record DashboardModel(Long userCount, Long numberOfSaveDocumentCount, Map<String, Integer> countFileType) {
}

