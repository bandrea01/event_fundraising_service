package it.unisalento.music_virus_project.event_campaign_service.service.implementation;

import it.unisalento.music_virus_project.event_campaign_service.repositories.ContributionRepository;
import it.unisalento.music_virus_project.event_campaign_service.repositories.EventRepository;
import org.springframework.stereotype.Service;

public class ContributionServiceImpl {

    private final ContributionRepository contributionRepository;
    private final EventRepository eventRepository;

    public ContributionServiceImpl(ContributionRepository contributionRepository,
                                   EventRepository eventRepository) {
        this.contributionRepository = contributionRepository;
        this.eventRepository = eventRepository;
    }

//    @Override
//    @Transactional
//    public ContributionResponse contribute(ContributionCreateRequest request) {
//        OldEvent event = eventRepository.findById(request.getEventId())
//                .orElseThrow(() -> new NotFoundException("Evento non trovato."));
//        if (event.getStatus() != EventStatus.LIVE) {
//            throw new BadRequestException("Puoi contribuire solo a campagne LIVE.");
//        }
//        if (request.getAmount() == null || request.getAmount().signum() <= 0) {
//            throw new BadRequestException("Importo non valido.");
//        }
//
//        OldContribution c = new OldContribution(
//                request.getEventId(),
//                request.getFanId(),
//                request.getAmount(),
//                request.getVisibility()
//        );
//        c.setStatus(ContributionStatus.AUTHORIZED);
//
//        OldContribution saved = contributionRepository.save(c);
//
//        BigDecimal current = event.getCurrentAmount() == null ? BigDecimal.ZERO : event.getCurrentAmount();
//        BigDecimal newTotal = current.add(request.getAmount());
//        event.setCurrentAmount(newTotal);
//
//        if (event.getGoalAmount() != null && newTotal.compareTo(event.getGoalAmount()) >= 0) {
//            event.setStatus(EventStatus.FUNDED);
//            // eventuale emissione evento dominio "CampaignFunded"
//        }
//        eventRepository.save(event);
//
//        return toResponse(saved);
//    }
//
//    @Override
//    public List<ContributionResponse> findByEvent(String eventId) {
//        return contributionRepository.findByEventId(eventId)
//                .stream().map(this::toResponse).collect(Collectors.toList());
//    }
//
//    @Override
//    public List<ContributionResponse> findByFan(String fanId) {
//        return contributionRepository.findByFanId(fanId)
//                .stream().map(this::toResponse).collect(Collectors.toList());
//    }

}
