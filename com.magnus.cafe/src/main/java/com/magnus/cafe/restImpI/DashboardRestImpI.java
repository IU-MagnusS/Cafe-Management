package com.magnus.cafe.restImpI;

import com.magnus.cafe.rest.DashboardRest;
import com.magnus.cafe.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DashboardRestImpI implements DashboardRest {

    @Autowired
    DashboardService dashboardService;


    @Override
    public ResponseEntity<Map<String, Object>> getCount() {
        return dashboardService.getCount();
    }
}
