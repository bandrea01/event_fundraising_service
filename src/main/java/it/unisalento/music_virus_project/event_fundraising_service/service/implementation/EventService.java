package it.unisalento.music_virus_project.event_fundraising_service.service.implementation;

import it.unisalento.music_virus_project.event_fundraising_service.domain.entity.Event;
import it.unisalento.music_virus_project.event_fundraising_service.domain.entity.Fundraising;
import it.unisalento.music_virus_project.event_fundraising_service.domain.entity.Role;
import it.unisalento.music_virus_project.event_fundraising_service.domain.enums.EventStatus;
import it.unisalento.music_virus_project.event_fundraising_service.dto.event.*;
import it.unisalento.music_virus_project.event_fundraising_service.exceptions.NotFoundException;
import it.unisalento.music_virus_project.event_fundraising_service.repositories.EventRepository;
import it.unisalento.music_virus_project.event_fundraising_service.service.IEventService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EventService implements IEventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public EventListResponseDTO getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return mapToListDTO(events);
    }

    @Override
    public EventResponseDTO getEventById(String eventId) {
        Event event = eventRepository.findByEventId(eventId);
        if (event == null) {
            throw new NotFoundException("Errore: Evento non trovato!");
        }
        return mapToDTO(event);
    }

    @Override
    public EventResponseDTO getEventByFundraisingId(String fundraisingId) {
        Event event = eventRepository.findByFundraisingId(fundraisingId);
        if (event == null) {
            throw new NotFoundException("Errore: Evento non trovato!");
        }
        return mapToDTO(event);
    }

    @Override
    public EventListResponseDTO getEventsByArtistId(String artistId) {
        List<Event> events = eventRepository.findByArtistId(artistId);
        return mapToListDTO(events);
    }

    @Override
    public EventListResponseDTO getEventsByVenueId(String venueId) {
        List<Event> events = eventRepository.findByVenueId(venueId);
        return mapToListDTO(events);
    }

    @Override
    public EventListResponseDTO getEventsByStatus(String status) {
        EventStatus eventStatus;
        try {
            eventStatus = EventStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new NotFoundException("Errore: Stato evento non valido!");
        }
        List<Event> events = eventRepository.findByStatus(eventStatus);
        return mapToListDTO(events);
    }

    @Override
    public EventListResponseDTO getEventsByDateRange(Instant startDate, Instant endDate) {
        List<Event> events = eventRepository.findByEventDateBetween(startDate, endDate);
        return mapToListDTO(events);
    }

    @Override
    public EventListResponseDTO getEventsByDate(Instant date) {
        List<Event> events = eventRepository.findByEventDate(date);
        return mapToListDTO(events);
    }

    @Override
    public EventVenueCounterListResponseDTO getEventVenueCounter() {
        Map<String, Integer> counterMap = new HashMap<>();

        List<Event> events = eventRepository.findAll();
        for (Event event : events) {
            String venueId = event.getVenueId();
            if (venueId == null) continue;
            counterMap.merge(venueId, 1, Integer::sum);
        }

        var response = counterMap.entrySet().stream()
                .map(entry -> new EventVenueCounterResponseDTO(entry.getKey(), entry.getValue()))
                .toList();

        return new EventVenueCounterListResponseDTO(response);
    }

    @Override
    @Transactional
    public EventResponseDTO createEventFromFundraising(Fundraising fundraising) {
        Event event = new Event(fundraising);
        event = eventRepository.save(event);
        return mapToDTO(event);
    }

    @Override
    @Transactional
    public EventResponseDTO updateEvent(String eventId, EventUpdateRequestDTO request) {
        Event event = eventRepository.findByEventId(eventId);
        if (event == null) {
            throw new NotFoundException("Errore: Evento non trovato!");
        }
        if (request.getEventName() != null) {
            event.setEventName(request.getEventName());
        }
        if (request.getEventDate() != null) {
            event.setEventDate(request.getEventDate());
        }
        event = eventRepository.save(event);
        return mapToDTO(event);
    }

    @Override
    @Transactional
    public EventResponseDTO confirmEvent(String eventId) {
        Event event = eventRepository.findByEventId(eventId);
        if (event == null) {
            throw new NotFoundException("Errore: Evento non trovato!");
        }
        event.setStatus(EventStatus.CONFIRMED);
        event = eventRepository.save(event);
        return mapToDTO(event);
    }

    @Override
    @Transactional
    public EventResponseDTO cancelEventById(String eventId) {
        Event event = eventRepository.findByEventId(eventId);
        if (event == null) {
            throw new NotFoundException("Errore: Evento non trovato!");
        }
        event.setStatus(EventStatus.CANCELLED);
        event = eventRepository.save(event);
        return mapToDTO(event);
    }


    //Rabbit
    @Override
    @Transactional
    public EventListResponseDTO disableEventsByUserId(String userId, Role role) {
        return switch (role) {
            case ARTIST -> disableArtistEvents(userId);
            case VENUE -> disableVenueEvents(userId);
            default -> new EventListResponseDTO();
        };
    }


    //utils
    private EventResponseDTO mapToDTO(Event event) {
        return new EventResponseDTO(
                event.getEventId(),
                event.getFundraisingId(),
                event.getArtistId(),
                event.getVenueId(),
                event.getStatus(),
                event.getEventName(),
                event.getEventDate()
        );
    }

    private EventListResponseDTO mapToListDTO(List<Event> events) {
        EventListResponseDTO list = new EventListResponseDTO();
        for (Event event : events) {
            list.getEvents().add(mapToDTO(event));
        }
        return list;
    }

    @Override
    @Transactional
    public EventListResponseDTO disableArtistEvents(String artistId) {
        EventListResponseDTO responseDTO = new EventListResponseDTO();
        List<Event> events = eventRepository.findByArtistId(artistId);
        for (Event event : events) {
            event.setStatus(EventStatus.CANCELLED);
            event = eventRepository.save(event);
            responseDTO.addEvent(mapToDTO(event));
        }
        return responseDTO;
    }

    @Override
    @Transactional
    public EventListResponseDTO disableVenueEvents(String venueId) {
        EventListResponseDTO responseDTO = new EventListResponseDTO();
        List<Event> events = eventRepository.findByVenueId(venueId);
        for (Event event : events) {
            event.setStatus(EventStatus.CANCELLED);
            event = eventRepository.save(event);
            responseDTO.addEvent(mapToDTO(event));
        }
        return responseDTO;
    }
}
