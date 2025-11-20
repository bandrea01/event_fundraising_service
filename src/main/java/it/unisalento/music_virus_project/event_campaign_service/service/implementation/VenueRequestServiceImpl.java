package it.unisalento.music_virus_project.event_campaign_service.service.implementation;

import org.springframework.stereotype.Service;

@Service
public class VenueRequestServiceImpl {
//
//    private final VenueRequestRepository venueRequestRepository;
//    private final EventRepository eventRepository;
//
//    public VenueRequestServiceImpl(VenueRequestRepository venueRequestRepository,
//                                   EventRepository eventRepository) {
//        this.venueRequestRepository = venueRequestRepository;
//        this.eventRepository = eventRepository;
//    }
//
//    @Override
//    @Transactional
//    public VenueRequestResponse create(VenueRequestCreateRequest request, String requesterArtistId) {
//        OldEvent e = eventRepository.findById(request.getEventId())
//                .orElseThrow(() -> new NotFoundException("Evento non trovato."));
//        if (!e.getArtistId().equals(requesterArtistId)) {
//            throw new ForbiddenActionException("Non puoi inviare richieste per un evento di un altro artista.");
//        }
//        if (e.getStatus() != EventStatus.DRAFT) {
//            throw new BadRequestException("Puoi contattare un venue solo quando l'evento è in DRAFT.");
//        }
//
//        VenueRequest vr = new VenueRequest();
//        vr.setEventId(request.getEventId());
//        vr.setVenueId(request.getVenueId());
//        vr.setStatus(VenueRequestStatus.PENDING);
//
//        VenueRequest saved = venueRequestRepository.save(vr);
//        return toResponse(saved);
//    }
//
//    @Override
//    @Transactional
//    public VenueRequestResponse decide(String venueRequestId, String requesterVenueId, VenueRequestDecisionRequest request) {
//        VenueRequest vr = venueRequestRepository.findById(venueRequestId)
//                .orElseThrow(() -> new NotFoundException("Richiesta venue non trovata."));
//        if (!vr.getVenueId().equals(requesterVenueId)) {
//            throw new ForbiddenActionException("Non puoi decidere richieste indirizzate a un altro venue.");
//        }
//        if (vr.getStatus() != VenueRequestStatus.PENDING) {
//            throw new BadRequestException("La richiesta è già stata decisa.");
//        }
//
//        vr.setStatus(request.getStatus());
//        vr.setCoFundingAmount(request.getCoFundingAmount());
//        vr.setBenefitDescription(request.getBenefitDescription());
//        vr.setDecisionAt(Instant.now());
//
//        VenueRequest saved = venueRequestRepository.save(vr);
//
//        if (request.getStatus() == VenueRequestStatus.ACCEPTED) {
//            OldEvent e = eventRepository.findById(vr.getEventId())
//                    .orElseThrow(() -> new NotFoundException("Evento non trovato."));
//            if (e.getStatus() != EventStatus.DRAFT) {
//                throw new BadRequestException("Evento non più in DRAFT: non può essere assegnato al venue.");
//            }
//            e.setVenueId(vr.getVenueId());
//            eventRepository.save(e);
//        }
//
//        return toResponse(saved);
//    }
//
//    @Override
//    public List<VenueRequestResponse> listByVenueAndStatus(String venueId, String status) {
//        VenueRequestStatus st = VenueRequestStatus.valueOf(status);
//        return venueRequestRepository.findByVenueIdAndStatus(venueId, st)
//                .stream().map(this::toResponse).collect(Collectors.toList());
//    }
//
//    @Override
//    public List<VenueRequestResponse> listByEvent(String eventId) {
//        return venueRequestRepository.findByEventId(eventId)
//                .stream().map(this::toResponse).collect(Collectors.toList());
//    }
//
//    private VenueRequestResponse toResponse(VenueRequest v) {
//        VenueRequestResponse dto = new VenueRequestResponse();
//        dto.setId(v.getId());
//        dto.setEventId(v.getEventId());
//        dto.setVenueId(v.getVenueId());
//        dto.setStatus(v.getStatus());
//        dto.setCoFundingAmount(v.getCoFundingAmount());
//        dto.setBenefitDescription(v.getBenefitDescription());
//        dto.setDecisionAt(v.getDecisionAt());
//        dto.setCreatedAt(v.getCreatedAt());
//        dto.setUpdatedAt(v.getUpdatedAt());
//        return dto;
//    }
}
