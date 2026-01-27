package it.unisalento.music_virus_project.event_fundraising_service.service.implementation;

import it.unisalento.music_virus_project.event_fundraising_service.domain.enums.EventStatus;
import it.unisalento.music_virus_project.event_fundraising_service.domain.enums.FundraisingStatus;
import it.unisalento.music_virus_project.event_fundraising_service.dto.admin.EventsStatisticDTO;
import it.unisalento.music_virus_project.event_fundraising_service.dto.admin.GenericCounterDTO;
import it.unisalento.music_virus_project.event_fundraising_service.repositories.EventRepository;
import it.unisalento.music_virus_project.event_fundraising_service.repositories.FundraisingRepository;
import it.unisalento.music_virus_project.event_fundraising_service.service.IAdminService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService implements IAdminService {

    private final FundraisingRepository fundraisingRepository;
    private final EventRepository eventRepository;

    public AdminService(FundraisingRepository fundraisingRepository, EventRepository eventRepository) {
        this.fundraisingRepository = fundraisingRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public EventsStatisticDTO getEventsStatistics() {
        EventsStatisticDTO statsDTO = new EventsStatisticDTO();

        statsDTO.addCounter(new GenericCounterDTO("FUNDRAISING",
                Math.toIntExact(fundraisingRepository.countFundraisingByStatusIn(
                        List.of(FundraisingStatus.CONFIRMED, FundraisingStatus.ACTIVE, FundraisingStatus.ACHIEVED)
                ))
        ));
        statsDTO.addCounter(new GenericCounterDTO("EVENTS",
                Math.toIntExact(eventRepository.countEventsByStatusIn(
                        List.of(EventStatus.CONFIRMED, EventStatus.PENDING)
                ))
        ));

        return statsDTO;
    }

}
