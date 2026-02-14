package it.unisalento.music_virus_project.event_fundraising_service.service.implementation;

import it.unisalento.music_virus_project.event_fundraising_service.domain.entity.Event;
import it.unisalento.music_virus_project.event_fundraising_service.domain.entity.Feedback;
import it.unisalento.music_virus_project.event_fundraising_service.domain.entity.Fundraising;
import it.unisalento.music_virus_project.event_fundraising_service.domain.entity.Role;
import it.unisalento.music_virus_project.event_fundraising_service.domain.enums.EventStatus;
import it.unisalento.music_virus_project.event_fundraising_service.dto.event.*;
import it.unisalento.music_virus_project.event_fundraising_service.exceptions.NotFoundException;
import it.unisalento.music_virus_project.event_fundraising_service.messaging.RabbitEventFundraisingService;
import it.unisalento.music_virus_project.event_fundraising_service.repositories.EventRepository;
import it.unisalento.music_virus_project.event_fundraising_service.repositories.FeedbackRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private RabbitEventFundraisingService rabbitEventFundraisingService;

    @InjectMocks
    private EventService eventService;

    private Event e1;
    private Event e2;

    @BeforeEach
    void setUp() {
        e1 = new Event();
        e1.setEventId("E1");
        e1.setFundraisingId("F1");
        e1.setArtistId("A1");
        e1.setVenueId("V1");
        e1.setStatus(EventStatus.CONFIRMED);
        e1.setEventName("Event 1");
        e1.setEventDate(Instant.parse("2026-01-01T10:00:00Z"));

        e2 = new Event();
        e2.setEventId("E2");
        e2.setFundraisingId("F2");
        e2.setArtistId("A2");
        e2.setVenueId("V2");
        e2.setStatus(EventStatus.PENDING);
        e2.setEventName("Event 2");
        e2.setEventDate(Instant.parse("2026-02-01T10:00:00Z"));
    }

    // ---------------------------
    // GETTERS / LISTS
    // ---------------------------

    @Test
    void getAllEvents_returnsListDTO() {
        when(eventRepository.findAll()).thenReturn(List.of(e1, e2));

        EventListResponseDTO res = eventService.getAllEvents();

        assertNotNull(res);
        assertNotNull(res.getEvents());
        assertEquals(2, res.getEvents().size());
        assertEquals("E1", res.getEvents().get(0).getEventId());
        assertEquals("E2", res.getEvents().get(1).getEventId());

        verify(eventRepository).findAll();
    }

    @Test
    void getEventById_returnsDTO_whenFound() {
        when(eventRepository.findByEventId("E1")).thenReturn(e1);

        EventResponseDTO dto = eventService.getEventById("E1");

        assertNotNull(dto);
        assertEquals("E1", dto.getEventId());
        assertEquals("F1", dto.getFundraisingId());
        verify(eventRepository).findByEventId("E1");
    }

    @Test
    void getEventById_throwsNotFound_whenMissing() {
        when(eventRepository.findByEventId("missing")).thenReturn(null);

        assertThrows(NotFoundException.class, () -> eventService.getEventById("missing"));

        verify(eventRepository).findByEventId("missing");
    }

    @Test
    void getEventByFundraisingId_returnsDTO_whenFound() {
        when(eventRepository.findByFundraisingId("F1")).thenReturn(e1);

        EventResponseDTO dto = eventService.getEventByFundraisingId("F1");

        assertNotNull(dto);
        assertEquals("E1", dto.getEventId());
        assertEquals("F1", dto.getFundraisingId());
        verify(eventRepository).findByFundraisingId("F1");
    }

    @Test
    void getEventsByArtistId_returnsList() {
        when(eventRepository.findByArtistId("A1")).thenReturn(List.of(e1));

        EventListResponseDTO res = eventService.getEventsByArtistId("A1");

        assertEquals(1, res.getEvents().size());
        assertEquals("E1", res.getEvents().get(0).getEventId());
        verify(eventRepository).findByArtistId("A1");
    }

    @Test
    void getEventsByVenueId_returnsList() {
        when(eventRepository.findByVenueId("V2")).thenReturn(List.of(e2));

        EventListResponseDTO res = eventService.getEventsByVenueId("V2");

        assertEquals(1, res.getEvents().size());
        assertEquals("E2", res.getEvents().get(0).getEventId());
        verify(eventRepository).findByVenueId("V2");
    }

    @Test
    void getEventsByStatus_parsesAndCallsRepo() {
        when(eventRepository.findByStatus(EventStatus.CONFIRMED)).thenReturn(List.of(e1));

        EventListResponseDTO res = eventService.getEventsByStatus("confirmed");

        assertEquals(1, res.getEvents().size());
        assertEquals("E1", res.getEvents().get(0).getEventId());
        verify(eventRepository).findByStatus(EventStatus.CONFIRMED);
    }

    @Test
    void getEventsByStatus_throwsNotFound_whenInvalidStatus() {
        assertThrows(NotFoundException.class, () -> eventService.getEventsByStatus("boh"));
        verifyNoInteractions(eventRepository);
    }

    @Test
    void getEventsByDateRange_callsRepo() {
        Instant start = Instant.parse("2026-01-01T00:00:00Z");
        Instant end = Instant.parse("2026-03-01T00:00:00Z");
        when(eventRepository.findByEventDateBetween(start, end)).thenReturn(List.of(e1, e2));

        EventListResponseDTO res = eventService.getEventsByDateRange(start, end);

        assertEquals(2, res.getEvents().size());
        verify(eventRepository).findByEventDateBetween(start, end);
    }

    @Test
    void getEventsByDate_callsRepo() {
        Instant date = Instant.parse("2026-01-01T10:00:00Z");
        when(eventRepository.findByEventDate(date)).thenReturn(List.of(e1));

        EventListResponseDTO res = eventService.getEventsByDate(date);

        assertEquals(1, res.getEvents().size());
        assertEquals("E1", res.getEvents().get(0).getEventId());
        verify(eventRepository).findByEventDate(date);
    }

    // ---------------------------
    // COUNTER
    // ---------------------------

    @Test
    void getEventVenueCounter_countsByVenueId_skipsNullVenue() {
        Event withNullVenue = new Event();
        withNullVenue.setEventId("E3");
        withNullVenue.setVenueId(null);

        Event anotherV1 = new Event();
        anotherV1.setEventId("E4");
        anotherV1.setVenueId("V1");

        when(eventRepository.findAll()).thenReturn(List.of(e1, e2, withNullVenue, anotherV1));
        // V1 = 2, V2 = 1

        EventVenueCounterListResponseDTO res = eventService.getEventVenueCounter();

        assertNotNull(res);
        assertNotNull(res.getEventVenueCounters());
        assertEquals(2, res.getEventVenueCounters().size());

        assertTrue(
                res.getEventVenueCounters().stream()
                        .anyMatch(c -> "V1".equals(c.getVenueId()) && c.getEventCounter() == 2)
        );
        assertTrue(
                res.getEventVenueCounters().stream()
                        .anyMatch(c -> "V2".equals(c.getVenueId()) && c.getEventCounter() == 1)
        );

        verify(eventRepository).findAll();
    }

    // ---------------------------
    // CREATE from fundraising
    // ---------------------------

    @Test
    void createEventFromFundraising_savesEvent_andSendsRabbit() {
        Fundraising fundraising = mock(Fundraising.class);
        when(fundraising.getArtistId()).thenReturn("A99");
        when(fundraising.getTargetAmount()).thenReturn(new BigDecimal("150.00"));

        when(eventRepository.save(any(Event.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        EventResponseDTO dto = eventService.createEventFromFundraising(fundraising);

        assertNotNull(dto);

        verify(eventRepository).save(any(Event.class));
        verify(rabbitEventFundraisingService)
                .sendEventCreation("A99", "organizer-1", new BigDecimal("150.00"));
    }

    // ---------------------------
    // UPDATE / CONFIRM / CANCEL
    // ---------------------------

    @Test
    void updateEvent_updatesFields_whenProvided() {
        when(eventRepository.findByEventId("E1")).thenReturn(e1);
        when(eventRepository.save(any(Event.class))).thenAnswer(inv -> inv.getArgument(0));

        EventUpdateRequestDTO req = new EventUpdateRequestDTO();
        req.setEventName("New Name");
        Instant newDate = Instant.parse("2026-05-01T10:00:00Z");
        req.setEventDate(newDate);

        EventResponseDTO dto = eventService.updateEvent("E1", req);

        assertEquals("E1", dto.getEventId());
        assertEquals("New Name", dto.getEventName());
        assertEquals(newDate, dto.getEventDate());

        verify(eventRepository).findByEventId("E1");
        verify(eventRepository).save(any(Event.class));
    }

    @Test
    void updateEvent_throwsNotFound_whenMissing() {
        when(eventRepository.findByEventId("missing")).thenReturn(null);

        EventUpdateRequestDTO req = new EventUpdateRequestDTO();
        req.setEventName("x");

        assertThrows(NotFoundException.class, () -> eventService.updateEvent("missing", req));
        verify(eventRepository).findByEventId("missing");
        verify(eventRepository, never()).save(any());
    }

    @Test
    void confirmEvent_setsConfirmed() {
        e1.setStatus(EventStatus.PENDING);

        when(eventRepository.findByEventId("E1")).thenReturn(e1);
        when(eventRepository.save(any(Event.class))).thenAnswer(inv -> inv.getArgument(0));

        EventResponseDTO dto = eventService.confirmEvent("E1");

        assertEquals(EventStatus.CONFIRMED, dto.getStatus());
        verify(eventRepository).save(any(Event.class));
    }

    @Test
    void cancelEventById_setsCancelled() {
        when(eventRepository.findByEventId("E1")).thenReturn(e1);
        when(eventRepository.save(any(Event.class))).thenAnswer(inv -> inv.getArgument(0));

        EventResponseDTO dto = eventService.cancelEventById("E1");

        assertEquals(EventStatus.CANCELLED, dto.getStatus());
        verify(eventRepository).save(any(Event.class));
    }

    // ---------------------------
    // DISABLE (artist / venue / switch by role)
    // ---------------------------

    @Test
    void disableArtistEvents_setsCancelled_forAllArtistEvents() {
        when(eventRepository.findByArtistId("A1")).thenReturn(List.of(e1));
        when(eventRepository.save(any(Event.class))).thenAnswer(inv -> inv.getArgument(0));

        EventListResponseDTO res = eventService.disableArtistEvents("A1");

        assertEquals(1, res.getEvents().size());
        assertEquals(EventStatus.CANCELLED, res.getEvents().get(0).getStatus());

        verify(eventRepository).findByArtistId("A1");
        verify(eventRepository).save(any(Event.class));
    }

    @Test
    void disableVenueEvents_setsCancelled_forAllVenueEvents() {
        when(eventRepository.findByVenueId("V2")).thenReturn(List.of(e2));
        when(eventRepository.save(any(Event.class))).thenAnswer(inv -> inv.getArgument(0));

        EventListResponseDTO res = eventService.disableVenueEvents("V2");

        assertEquals(1, res.getEvents().size());
        assertEquals(EventStatus.CANCELLED, res.getEvents().get(0).getStatus());

        verify(eventRepository).findByVenueId("V2");
        verify(eventRepository).save(any(Event.class));
    }

    @Test
    void disableEventsByUserId_switchArtist() {
        when(eventRepository.findByArtistId("A1")).thenReturn(List.of(e1));
        when(eventRepository.save(any(Event.class))).thenAnswer(inv -> inv.getArgument(0));

        EventListResponseDTO res = eventService.disableEventsByUserId("A1", Role.ARTIST);

        assertEquals(1, res.getEvents().size());
        assertEquals(EventStatus.CANCELLED, res.getEvents().get(0).getStatus());
    }

    @Test
    void disableEventsByUserId_switchVenue() {
        when(eventRepository.findByVenueId("V2")).thenReturn(List.of(e2));
        when(eventRepository.save(any(Event.class))).thenAnswer(inv -> inv.getArgument(0));

        EventListResponseDTO res = eventService.disableEventsByUserId("V2", Role.VENUE);

        assertEquals(1, res.getEvents().size());
        assertEquals(EventStatus.CANCELLED, res.getEvents().get(0).getStatus());
    }

    @Test
    void disableEventsByUserId_switchDefault_returnsEmpty() {
        EventListResponseDTO res = eventService.disableEventsByUserId("X", Role.FAN);
        assertNotNull(res);
        assertNotNull(res.getEvents());
        assertEquals(0, res.getEvents().size());
        verifyNoInteractions(eventRepository);
    }

    // ---------------------------
    // FEEDBACK
    // ---------------------------

    @Test
    void getEventFeedbacks_mapsList() {
        Feedback f1 = new Feedback();
        f1.setEventId("E1");
        f1.setUserId("U1");

        when(feedbackRepository.findByEventId("E1")).thenReturn(List.of(f1));

        FeedbackListResponseDTO res = eventService.getEventFeedbacks("E1");

        assertNotNull(res);
        assertNotNull(res.getFeedbacks());
        assertEquals(1, res.getFeedbacks().size());
        verify(feedbackRepository).findByEventId("E1");
    }

    @Test
    void addEventFeedback_savesAndReturnsDTO_whenFinished() {
        Event finished = new Event();
        finished.setEventId("E1");
        finished.setStatus(EventStatus.FINISHED);

        when(eventRepository.findByEventId("E1")).thenReturn(finished);
        when(feedbackRepository.save(any(Feedback.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        FeedbackCreateRequestDTO req = new FeedbackCreateRequestDTO();
        req.setUserId("U1");
        req.setRating(5);
        req.setComment("Bellissimo evento");

        FeedbackResponseDTO dto = eventService.addEventFeedback("E1", req);

        assertNotNull(dto);
        verify(eventRepository).findByEventId("E1");

        ArgumentCaptor<Feedback> captor = ArgumentCaptor.forClass(Feedback.class);
        verify(feedbackRepository).save(captor.capture());
        assertEquals("E1", captor.getValue().getEventId());
        assertEquals("U1", captor.getValue().getUserId());
        assertEquals(5, captor.getValue().getRating());
        assertEquals("Bellissimo evento", captor.getValue().getComment());
        assertNotNull(captor.getValue().getCreatedAt());
    }

    @Test
    void addEventFeedback_throwsNotFound_whenEventMissing() {
        when(eventRepository.findByEventId("missing")).thenReturn(null);

        FeedbackCreateRequestDTO req = new FeedbackCreateRequestDTO();
        req.setUserId("U1");
        req.setRating(5);
        req.setComment("ok");

        assertThrows(NotFoundException.class, () -> eventService.addEventFeedback("missing", req));

        verify(eventRepository).findByEventId("missing");
        verifyNoInteractions(feedbackRepository);
    }

    @Test
    void addEventFeedback_throwsIllegalState_whenEventNotFinished() {
        Event notFinished = new Event();
        notFinished.setEventId("E1");
        notFinished.setStatus(EventStatus.CONFIRMED);

        when(eventRepository.findByEventId("E1")).thenReturn(notFinished);

        FeedbackCreateRequestDTO req = new FeedbackCreateRequestDTO();
        req.setUserId("U1");
        req.setRating(5);
        req.setComment("ok");

        assertThrows(IllegalStateException.class, () -> eventService.addEventFeedback("E1", req));

        verify(eventRepository).findByEventId("E1");
        verifyNoInteractions(feedbackRepository);
    }
}
