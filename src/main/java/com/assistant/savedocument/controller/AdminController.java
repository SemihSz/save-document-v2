package com.assistant.savedocument.controller;

import com.assistant.savedocument.model.RestResponse;
import com.assistant.savedocument.model.admin.DashboardModel;
import com.assistant.savedocument.model.response.DocumentListResponse;
import com.assistant.savedocument.service.admin.AdminService;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/dashboard/{username}")
    public ResponseEntity<RestResponse<DashboardModel>> adminDashboard(@ApiParam(required = true, value = "Username", example = "testusername") @PathVariable String username) {

        return ResponseEntity.ok(new RestResponse<>(200, adminService.dashboardService(username)));
    }

}
