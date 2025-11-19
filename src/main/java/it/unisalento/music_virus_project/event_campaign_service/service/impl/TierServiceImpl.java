package it.unisalento.music_virus_project.event_campaign_service.service.impl;

import it.unisalento.music_virus_project.event_campaign_service.domain.enums.EventStatus;
import it.unisalento.music_virus_project.event_campaign_service.dto.tier.TierCreateRequest;
import it.unisalento.music_virus_project.event_campaign_service.dto.tier.TierResponse;
import it.unisalento.music_virus_project.event_campaign_service.exceptions.BadRequestException;
import it.unisalento.music_virus_project.event_campaign_service.exceptions.ForbiddenActionException;
import it.unisalento.music_virus_project.event_campaign_service.exceptions.NotFoundException;
import it.unisalento.music_virus_project.event_campaign_service.repositories.EventRepository;
import it.unisalento.music_virus_project.event_campaign_service.repositories.FundraisingRepository;
import it.unisalento.music_virus_project.event_campaign_service.service.TierService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TierServiceImpl implements TierService {

    private final FundraisingRepository fundraisingRepository;
    private final EventRepository eventRepository;

    public TierServiceImpl(FundraisingRepository fundraisingRepository, EventRepository eventRepository) {
        this.fundraisingRepository = fundraisingRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    @Transactional
    public TierResponse create(TierCreateRequest request, String requesterArtistId) {
        OldEvent e = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new NotFoundException("Evento non trovato."));
        if (!e.getArtistId().equals(requesterArtistId)) {
            throw new ForbiddenActionException("Non puoi creare tier per un evento di un altro artista.");
        }
        if (e.getStatus() != EventStatus.DRAFT) {
            throw new BadRequestException("I tier possono essere creati solo quando l'evento è in DRAFT.");
        }

        Tier t = new Tier(request.getEventId(), request.getLabel(), request.getMinAmount(), request.getBenefits());

        Tier saved = fundraisingRepository.save(t);

        return toResponse(saved);
    }

    @Override
    public List<TierResponse> listByEvent(String eventId) {
        return fundraisingRepository.findByEventIdOrderByMinAmountAsc(eventId)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    private TierResponse toResponse(Tier t) {
        TierResponse dto = new TierResponse();
        dto.setId(t.getId());
        dto.setEventId(t.getEventId());
        dto.setLabel(t.getLabel());
        dto.setMinAmount(t.getMinAmount());
        dto.setBenefits(t.getBenefits());
        return dto;
    }
}
