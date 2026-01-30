package it.unisalento.music_virus_project.event_fundraising_service.controllers;

import it.unisalento.music_virus_project.event_fundraising_service.dto.admin.EventsStatisticDTO;
import it.unisalento.music_virus_project.event_fundraising_service.service.implementation.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/event-fundraising/admin")
@Validated
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/stats")
    public ResponseEntity<EventsStatisticDTO> getStatistics() {
        var response = adminService.getEventsStatistics();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
