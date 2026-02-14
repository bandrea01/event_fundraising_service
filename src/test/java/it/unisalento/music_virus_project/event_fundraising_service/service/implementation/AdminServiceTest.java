package it.unisalento.music_virus_project.event_fundraising_service.service.implementation;

import it.unisalento.music_virus_project.event_fundraising_service.domain.enums.EventStatus;
import it.unisalento.music_virus_project.event_fundraising_service.domain.enums.FundraisingStatus;
import it.unisalento.music_virus_project.event_fundraising_service.dto.admin.EventsStatisticDTO;
import it.unisalento.music_virus_project.event_fundraising_service.dto.admin.GenericCounterDTO;
import it.unisalento.music_virus_project.event_fundraising_service.repositories.EventRepository;
import it.unisalento.music_virus_project.event_fundraising_service.repositories.FundraisingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private FundraisingRepository fundraisingRepository;

    @Mock
    private EventRepository eventRepository;

    private AdminService adminService;

    @BeforeEach
    void setUp() {
        adminService = new AdminService(fundraisingRepository, eventRepository);
    }

    @Test
    void getEventsStatistics_returnsCorrectCounters() {

        when(fundraisingRepository.countFundraisingByStatusIn(
                List.of(FundraisingStatus.CONFIRMED,
                        FundraisingStatus.ACTIVE,
                        FundraisingStatus.ACHIEVED)
        )).thenReturn(5);

        when(eventRepository.countEventsByStatusIn(
                List.of(EventStatus.CONFIRMED,
                        EventStatus.PENDING)
        )).thenReturn(3);

        EventsStatisticDTO result = adminService.getEventsStatistics();

        assertNotNull(result);
        assertEquals(2, result.getCounters().size());

        GenericCounterDTO fundraisingCounter = result.getCounters().stream()
                .filter(c -> "FUNDRAISING".equals(c.getType()))
                .findFirst()
                .orElseThrow();

        GenericCounterDTO eventCounter = result.getCounters().stream()
                .filter(c -> "EVENTS".equals(c.getType()))
                .findFirst()
                .orElseThrow();

        assertEquals(5, fundraisingCounter.getCount());
        assertEquals(3, eventCounter.getCount());

        verify(fundraisingRepository, times(1))
                .countFundraisingByStatusIn(
                        List.of(FundraisingStatus.CONFIRMED,
                                FundraisingStatus.ACTIVE,
                                FundraisingStatus.ACHIEVED)
                );

        verify(eventRepository, times(1))
                .countEventsByStatusIn(
                        List.of(EventStatus.CONFIRMED,
                                EventStatus.PENDING)
                );

        verifyNoMoreInteractions(fundraisingRepository, eventRepository);
    }

    @Test
    void getEventsStatistics_passesCorrectStatusLists() {

        when(fundraisingRepository.countFundraisingByStatusIn(anyList())).thenReturn(0);
        when(eventRepository.countEventsByStatusIn(anyList())).thenReturn(0);

        ArgumentCaptor<List<FundraisingStatus>> fundraisingCaptor =
                ArgumentCaptor.forClass(List.class);

        ArgumentCaptor<List<EventStatus>> eventCaptor =
                ArgumentCaptor.forClass(List.class);

        adminService.getEventsStatistics();

        verify(fundraisingRepository)
                .countFundraisingByStatusIn(fundraisingCaptor.capture());

        verify(eventRepository)
                .countEventsByStatusIn(eventCaptor.capture());

        assertEquals(
                List.of(FundraisingStatus.CONFIRMED,
                        FundraisingStatus.ACTIVE,
                        FundraisingStatus.ACHIEVED),
                fundraisingCaptor.getValue()
        );

        assertEquals(
                List.of(EventStatus.CONFIRMED,
                        EventStatus.PENDING),
                eventCaptor.getValue()
        );
    }
}
