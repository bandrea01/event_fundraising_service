package it.unisalento.music_virus_project.event_fundraising_service.service.implementation;

import it.unisalento.music_virus_project.event_fundraising_service.repositories.EventRepository;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl {

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

//    @Override
//    @Transactional
//    public EventResponse create(EventCreateRequest request) {
//        if (request.getEventDate() == null || request.getEventDate().isBefore(Instant.now())) {
//            throw new BadRequestException("La data dell'evento deve essere futura.");
//        }
//        if (request.getGoalAmount() == null || request.getGoalAmount().signum() <= 0) {
//            throw new BadRequestException("Obiettivo economico non valido.");
//        }
//
//        OldEvent e = new OldEvent(
//                request.getArtistId(),
//                request.getTitle(),
//                request.getDescription(),
//                request.getEventDate(),
//                request.getGoalAmount()
//        );
//        e.setGenres(request.getGenres());
//        e.setCity(request.getCity());
//        e.setCountry(request.getCountry());
//        e.setCurrentAmount(BigDecimal.ZERO);
//        e.setStatus(EventStatus.DRAFT);
//
//        OldEvent saved = eventRepository.save(e);
//        return toResponse(saved);
//    }
//
//    @Override
//    @Transactional
//    public EventResponse update(String eventId, String requesterArtistId, EventUpdateRequest request) {
//        OldEvent e = eventRepository.findById(eventId)
//                .orElseThrow(() -> new NotFoundException("Evento non trovato."));
//        if (!e.getArtistId().equals(requesterArtistId)) {
//            throw new ForbiddenActionException("Non puoi modificare un evento di un altro artista.");
//        }
//        if (e.getStatus() != EventStatus.DRAFT) {
//            throw new BadRequestException("Puoi modificare solo eventi in DRAFT.");
//        }
//        if (request.getEventDate() == null || request.getEventDate().isBefore(Instant.now())) {
//            throw new BadRequestException("La data dell'evento deve essere futura.");
//        }
//        if (request.getGoalAmount() == null || request.getGoalAmount().signum() <= 0) {
//            throw new BadRequestException("Obiettivo economico non valido.");
//        }
//
//        e.setTitle(request.getTitle());
//        e.setDescription(request.getDescription());
//        e.setEventDate(request.getEventDate());
//        e.setGoalAmount(request.getGoalAmount());
//        e.setGenres(request.getGenres());
//        e.setCity(request.getCity());
//        e.setCountry(request.getCountry());
//
//        OldEvent saved = eventRepository.save(e);
//        return toResponse(saved);
//    }
//
//    @Override
//    @Transactional
//    public void deleteDraft(String eventId, String requesterArtistId) {
//        OldEvent e = eventRepository.findById(eventId)
//                .orElseThrow(() -> new NotFoundException("Evento non trovato."));
//        if (!e.getArtistId().equals(requesterArtistId)) {
//            throw new ForbiddenActionException("Non puoi eliminare un evento di un altro artista.");
//        }
//        if (e.getStatus() != EventStatus.DRAFT) {
//            throw new BadRequestException("Puoi eliminare solo eventi in DRAFT.");
//        }
//        eventRepository.deleteById(eventId);
//    }
//
//    @Override
//    @Transactional
//    public EventResponse publish(String eventId, String requesterArtistId) {
//        OldEvent e = eventRepository.findById(eventId)
//                .orElseThrow(() -> new NotFoundException("Evento non trovato."));
//        if (!e.getArtistId().equals(requesterArtistId)) {
//            throw new ForbiddenActionException("Non puoi pubblicare un evento di un altro artista.");
//        }
//        if (e.getStatus() != EventStatus.DRAFT) {
//            throw new BadRequestException("Solo eventi in DRAFT possono essere pubblicati.");
//        }
//        if (e.getGoalAmount() == null || e.getGoalAmount().signum() <= 0) {
//            throw new BadRequestException("Obiettivo economico non valido.");
//        }
//        e.setStatus(EventStatus.LIVE);
//        OldEvent saved = eventRepository.save(e);
//        return toResponse(saved);
//    }
//
//    @Override
//    public EventResponse findById(String eventId) {
//        return eventRepository.findById(eventId)
//                .map(this::toResponse)
//                .orElseThrow(() -> new NotFoundException("Evento non trovato."));
//    }
//
//    @Override
//    public List<EventResponse> findLiveAfter(Instant after) {
//        return eventRepository.findByStatusAndEventDateAfter(EventStatus.LIVE, after)
//                .stream().map(this::toResponse).collect(Collectors.toList());
//    }
//
//    @Override
//    public List<EventResponse> findByArtist(String artistId) {
//        return eventRepository.findByArtistId(artistId)
//                .stream().map(this::toResponse).collect(Collectors.toList());
//    }
//
//    private EventResponse toResponse(OldEvent e) {
//        EventResponse dto = new EventResponse();
//        dto.setId(e.getId());
//        dto.setArtistId(e.getArtistId());
//        dto.setVenueId(e.getVenueId());
//        dto.setStatus(e.getStatus());
//        dto.setTitle(e.getTitle());
//        dto.setDescription(e.getDescription());
//        dto.setEventDate(e.getEventDate());
//        dto.setGoalAmount(e.getGoalAmount());
//        dto.setCurrentAmount(e.getCurrentAmount());
//        dto.setGenres(e.getGenres());
//        dto.setCity(e.getCity());
//        dto.setCountry(e.getCountry());
//        dto.setCreatedAt(e.getCreatedAt());
//        dto.setUpdatedAt(e.getUpdatedAt());
//        return dto;
//    }
}
