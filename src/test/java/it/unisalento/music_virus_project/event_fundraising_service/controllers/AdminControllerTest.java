package it.unisalento.music_virus_project.event_fundraising_service.controllers;

import it.unisalento.music_virus_project.event_fundraising_service.dto.admin.EventsStatisticDTO;
import it.unisalento.music_virus_project.event_fundraising_service.dto.admin.GenericCounterDTO;
import it.unisalento.music_virus_project.event_fundraising_service.service.implementation.AdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminController.class)
@AutoConfigureMockMvc(addFilters = false) // 🔥 disabilita Spring Security filters
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    @Test
    void getStatistics_returnsOk_andBody() throws Exception {
        EventsStatisticDTO dto = new EventsStatisticDTO();
        dto.setCounters(List.of(
                new GenericCounterDTO("FUNDRAISING", 5),
                new GenericCounterDTO("EVENTS", 3)
        ));

        when(adminService.getEventsStatistics()).thenReturn(dto);

        mockMvc.perform(get("/api/event-fundraising/admin/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.counters").isArray())
                .andExpect(jsonPath("$.counters[0].type").value("FUNDRAISING"))
                .andExpect(jsonPath("$.counters[0].count").value(5))
                .andExpect(jsonPath("$.counters[1].type").value("EVENTS"))
                .andExpect(jsonPath("$.counters[1].count").value(3));
    }
}
