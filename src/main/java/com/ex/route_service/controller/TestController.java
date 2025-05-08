package com.ex.route_service.controller;

import com.ex.route_service.service.GeoIpService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/geo")
public class TestController {
    private final GeoIpService geoIpService;


    @GetMapping("/search")
    public List<Map<String, Object>> search(@RequestParam String query, HttpServletRequest request) {
        String ipAddress = extractClientIp(request);
        System.out.println(ipAddress);

        return geoIpService.getCountryCodeByIp(query);
    }

    private String extractClientIp(HttpServletRequest request) {
        String forwardedFor = request.getHeader("X-Forwarded-For");
        if (forwardedFor != null && !forwardedFor.isEmpty()) {
            return forwardedFor.split(",")[0].trim(); // В случае прокси может быть список IP
        }
        return request.getRemoteAddr();
    }
}