package it.unisalento.music_virus_project.event_fundraising_service.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unisalento.music_virus_project.event_fundraising_service.domain.enums.VenuePromotionEnum;
import it.unisalento.music_virus_project.event_fundraising_service.dto.event.*;
import it.unisalento.music_virus_project.event_fundraising_service.service.IEventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventController.class)
@AutoConfigureMockMvc(addFilters = false)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IEventService eventService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllEvents_returnsOk() throws Exception {

        EventListResponseDTO response = new EventListResponseDTO();
        response.getEvents().add(new EventResponseDTO(
                "e1", "f1", "a1", "v1",
                null, "EventName", Instant.now(), VenuePromotionEnum.DRINK_DISCOUNT_10_PERCENT
        ));

        when(eventService.getAllEvents()).thenReturn(response);

        mockMvc.perform(get("/api/event-fundraising/event"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.events[0].eventId").value("e1"));
    }

    @Test
    void getEventById_returnsOk() throws Exception {

        EventResponseDTO dto = new EventResponseDTO(
                "e1", "f1", "a1", "v1",
                null, "EventName", Instant.now(), VenuePromotionEnum.DRINK_DISCOUNT_10_PERCENT
        );

        when(eventService.getEventById("e1")).thenReturn(dto);

        mockMvc.perform(get("/api/event-fundraising/event/e1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventId").value("e1"));
    }

    @Test
    void getEventsByStatus_returnsOk() throws Exception {

        EventListResponseDTO response = new EventListResponseDTO();
        response.getEvents().add(new EventResponseDTO(
                "e1", "f1", "a1", "v1",
                null, "EventName", Instant.now(), VenuePromotionEnum.DRINK_DISCOUNT_10_PERCENT
        ));

        when(eventService.getEventsByStatus("CONFIRMED")).thenReturn(response);

        mockMvc.perform(get("/api/event-fundraising/event")
                        .param("status", "CONFIRMED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.events[0].eventId").value("e1"));
    }

    @Test
    void getEventsByDate_returnsOk() throws Exception {

        Instant now = Instant.now();

        EventListResponseDTO response = new EventListResponseDTO();
        when(eventService.getEventsByDate(any())).thenReturn(response);

        mockMvc.perform(get("/api/event-fundraising/event")
                        .param("eventDate", now.toString()))
                .andExpect(status().isOk());
    }

    @Test
    void getVenueCounter_returnsOk() throws Exception {

        EventVenueCounterListResponseDTO response =
                new EventVenueCounterListResponseDTO(
                        List.of(new EventVenueCounterResponseDTO("v1", 3))
                );

        when(eventService.getEventVenueCounter()).thenReturn(response);

        mockMvc.perform(get("/api/event-fundraising/event/venues/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventVenueCounters[0].venueId").value("v1"))
                .andExpect(jsonPath("$.eventVenueCounters[0].eventCounter").value(3));
    }

    @Test
    void addEventFeedback_returnsCreated() throws Exception {

        FeedbackCreateRequestDTO request = new FeedbackCreateRequestDTO();
        request.setUserId("user1");
        request.setRating(5);
        request.setComment("Great!");

        FeedbackResponseDTO response = new FeedbackResponseDTO();
        response.setRating(5);

        when(eventService.addEventFeedback(eq("e1"), any()))
                .thenReturn(response);

        mockMvc.perform(post("/api/event-fundraising/event/e1/feedbacks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.rating").value(5));
    }

    @Test
    void updateEvent_returnsOk() throws Exception {

        EventUpdateRequestDTO request = new EventUpdateRequestDTO();
        request.setEventName("Updated");

        EventResponseDTO response = new EventResponseDTO(
                "e1", "f1", "a1", "v1",
                null, "Updated", Instant.now(), VenuePromotionEnum.DRINK_DISCOUNT_10_PERCENT
        );

        when(eventService.updateEvent(eq("e1"), any()))
                .thenReturn(response);

        mockMvc.perform(patch("/api/event-fundraising/event/e1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventName").value("Updated"));
    }

    @Test
    void cancelEvent_returnsOk() throws Exception {

        EventResponseDTO response = new EventResponseDTO(
                "e1", "f1", "a1", "v1",
                null, "EventName", Instant.now(), VenuePromotionEnum.DRINK_DISCOUNT_10_PERCENT
        );

        when(eventService.cancelEventById("e1")).thenReturn(response);

        mockMvc.perform(patch("/api/event-fundraising/event/cancel/e1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventId").value("e1"));
    }

}
