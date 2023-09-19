package com.assistant.savedocument.service.admin;

import com.assistant.savedocument.model.admin.DashboardModel;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {

    /**
     * Dashboard service return user count and number of save document information.
     * @param username
     * @return DashboardModel
     */
    DashboardModel dashboardService(String username);
}
