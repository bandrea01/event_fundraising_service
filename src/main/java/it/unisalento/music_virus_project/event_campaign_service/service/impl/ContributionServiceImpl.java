package it.unisalento.music_virus_project.event_campaign_service.service.impl;

import it.unisalento.music_virus_project.event_campaign_service.domain.enums.ContributionStatus;
import it.unisalento.music_virus_project.event_campaign_service.domain.enums.EventStatus;
import it.unisalento.music_virus_project.event_campaign_service.dto.contribution.ContributionCreateRequest;
import it.unisalento.music_virus_project.event_campaign_service.dto.contribution.ContributionResponse;
import it.unisalento.music_virus_project.event_campaign_service.exceptions.BadRequestException;
import it.unisalento.music_virus_project.event_campaign_service.exceptions.NotFoundException;
import it.unisalento.music_virus_project.event_campaign_service.repositories.ContributionRepository;
import it.unisalento.music_virus_project.event_campaign_service.repositories.EventRepository;
import it.unisalento.music_virus_project.event_campaign_service.service.ContributionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContributionServiceImpl implements ContributionService {

    private final ContributionRepository contributionRepository;
    private final EventRepository eventRepository;

    public ContributionServiceImpl(ContributionRepository contributionRepository,
                                   EventRepository eventRepository) {
        this.contributionRepository = contributionRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    @Transactional
    public ContributionResponse contribute(ContributionCreateRequest request) {
        OldEvent event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new NotFoundException("Evento non trovato."));
        if (event.getStatus() != EventStatus.LIVE) {
            throw new BadRequestException("Puoi contribuire solo a campagne LIVE.");
        }
        if (request.getAmount() == null || request.getAmount().signum() <= 0) {
            throw new BadRequestException("Importo non valido.");
        }

        OldContribution c = new OldContribution(
                request.getEventId(),
                request.getFanId(),
                request.getAmount(),
                request.getVisibility()
        );
        c.setStatus(ContributionStatus.AUTHORIZED);

        OldContribution saved = contributionRepository.save(c);

        BigDecimal current = event.getCurrentAmount() == null ? BigDecimal.ZERO : event.getCurrentAmount();
        BigDecimal newTotal = current.add(request.getAmount());
        event.setCurrentAmount(newTotal);

        if (event.getGoalAmount() != null && newTotal.compareTo(event.getGoalAmount()) >= 0) {
            event.setStatus(EventStatus.FUNDED);
            // eventuale emissione evento dominio "CampaignFunded"
        }
        eventRepository.save(event);

        return toResponse(saved);
    }

    @Override
    public List<ContributionResponse> findByEvent(String eventId) {
        return contributionRepository.findByEventId(eventId)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<ContributionResponse> findByFan(String fanId) {
        return contributionRepository.findByFanId(fanId)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    private ContributionResponse toResponse(OldContribution c) {
        ContributionResponse dto = new ContributionResponse();
        dto.setId(c.getId());
        dto.setEventId(c.getEventId());
        dto.setFanId(c.getFanId());
        dto.setAmount(c.getAmount());
        dto.setVisibility(c.getVisibility());
        dto.setStatus(c.getStatus());
        dto.setCreatedAt(c.getCreatedAt());
        dto.setUpdatedAt(c.getUpdatedAt());
        return dto;
    }
}
