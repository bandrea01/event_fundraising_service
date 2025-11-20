package it.unisalento.music_virus_project.event_campaign_service.service.implementation;

import it.unisalento.music_virus_project.event_campaign_service.domain.entity.Fundraising;
import it.unisalento.music_virus_project.event_campaign_service.domain.enums.FundraisingStatus;
import it.unisalento.music_virus_project.event_campaign_service.dto.fundraising.FundraisingCreateRequestDTO;
import it.unisalento.music_virus_project.event_campaign_service.dto.fundraising.FundraisingCreateResponseDTO;
import it.unisalento.music_virus_project.event_campaign_service.repositories.EventRepository;
import it.unisalento.music_virus_project.event_campaign_service.repositories.FundraisingRepository;
import it.unisalento.music_virus_project.event_campaign_service.service.IFundraisingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FundraisingService implements IFundraisingService {

    private final FundraisingRepository fundraisingRepository;
//    private final EventRepository eventRepository;

    public FundraisingService(FundraisingRepository fundraisingRepository, EventRepository eventRepository) {
        this.fundraisingRepository = fundraisingRepository;
//        this.eventRepository = eventRepository;
    }

    @Override
    @Transactional
    public FundraisingCreateResponseDTO createFundraising(FundraisingCreateRequestDTO request) {

        //If a fundraising with the same venue and date exist then throw error
        List<Fundraising> fundraisings = fundraisingRepository.findByEventDateAndVenueId(request.getEventDate(), request.getVenueId());
        if (!fundraisings.isEmpty()) {
            //TODO throw error
        }
        Fundraising fundraisingByName = fundraisingRepository.findByFundraisingName(request.getFundraisingName());
        //If a fundraising with the same name exist then throw error
        //TODO

        //Creation
        Fundraising fundraising = new Fundraising(
                request.getArtistId(),
                request.getVenueId(),
                request.getFundraisingName(),
                request.getTargetAmount(),
                FundraisingStatus.ACTIVE,
                request.getEventDate()
        );

        fundraising = fundraisingRepository.save(fundraising);

        return new FundraisingCreateResponseDTO(
                fundraising.getFundraisingId(),
                fundraising.getFundraisingName(),
                fundraising.getStatus()
        );
    }
}
