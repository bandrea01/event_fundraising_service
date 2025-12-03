package it.unisalento.music_virus_project.event_fundraising_service.service.implementation;

import it.unisalento.music_virus_project.event_fundraising_service.domain.entity.Fundraising;
import it.unisalento.music_virus_project.event_fundraising_service.domain.entity.Role;
import it.unisalento.music_virus_project.event_fundraising_service.domain.enums.FundraisingStatus;
import it.unisalento.music_virus_project.event_fundraising_service.dto.fundraising.FundraisingCreateRequestDTO;
import it.unisalento.music_virus_project.event_fundraising_service.dto.fundraising.FundraisingListResponseDTO;
import it.unisalento.music_virus_project.event_fundraising_service.dto.fundraising.FundraisingResponseDTO;
import it.unisalento.music_virus_project.event_fundraising_service.dto.fundraising.FundraisingUpdateRequestDTO;
import it.unisalento.music_virus_project.event_fundraising_service.exceptions.ForbiddenActionException;
import it.unisalento.music_virus_project.event_fundraising_service.exceptions.NotFoundException;
import it.unisalento.music_virus_project.event_fundraising_service.exceptions.SameNameFundraisingException;
import it.unisalento.music_virus_project.event_fundraising_service.exceptions.SamePlaceAndDateFundraisingException;
import it.unisalento.music_virus_project.event_fundraising_service.repositories.FundraisingRepository;
import it.unisalento.music_virus_project.event_fundraising_service.service.IFundraisingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
public class FundraisingService implements IFundraisingService {

    private final FundraisingRepository fundraisingRepository;
    private final EventService eventService;

    public FundraisingService(FundraisingRepository fundraisingRepository, EventService eventService) {
        this.fundraisingRepository = fundraisingRepository;
        this.eventService = eventService;
    }

    @Override
    public FundraisingResponseDTO getFundraisingById(String fundraisingId) {
        Fundraising fundraising = fundraisingRepository.findByFundraisingId(fundraisingId);
        if (fundraising == null) {
            throw new NotFoundException("Errore: Fundraising non trovato!");
        }
        return mapToDTO(fundraising);
    }

    @Override
    public FundraisingListResponseDTO getAllFundraisings() {
        List<Fundraising> fundraisings = fundraisingRepository.findAll();
        return mapToDTOList(fundraisings);
    }

    @Override
    public FundraisingListResponseDTO getFundraisingsByArtistId(String artistId) {
        List<Fundraising> fundraisings = fundraisingRepository.findByArtistId(artistId);
        return mapToDTOList(fundraisings);
    }

    @Override
    public FundraisingListResponseDTO getFundraisingsByVenueId(String venueId) {
        List<Fundraising> fundraisings = fundraisingRepository.findByVenueId(venueId);
        return mapToDTOList(fundraisings);
    }

    @Override
    public FundraisingListResponseDTO getFundraisingsByStatus(FundraisingStatus status) {
        List<Fundraising> fundraisings = fundraisingRepository.findByStatus(status);
        return mapToDTOList(fundraisings);
    }

    @Override
    public FundraisingListResponseDTO getFundraisingsByEventDate(Instant eventDate) {
        List<Fundraising> fundraisings = fundraisingRepository.findByEventDate(eventDate);
        return mapToDTOList(fundraisings);
    }

    @Override
    public FundraisingListResponseDTO getFundraisingsByArtistIdAndStatus(String artistId, FundraisingStatus status) {
        List<Fundraising> fundraisings = fundraisingRepository.findByArtistIdAndStatus(artistId, status);
        return mapToDTOList(fundraisings);
    }

    @Override
    public FundraisingListResponseDTO getFundraisingsByVenueIdAndStatus(String venueId, FundraisingStatus status) {
        List<Fundraising> fundraisings = fundraisingRepository.findByVenueIdAndStatus(venueId, status);
        return mapToDTOList(fundraisings);
    }

    @Override
    @Transactional
    public FundraisingResponseDTO createFundraising(FundraisingCreateRequestDTO request)
            throws SamePlaceAndDateFundraisingException, SameNameFundraisingException {

        //If a fundraising with the same venue and date exist then throw error
        List<Fundraising> fundraisings = fundraisingRepository.findByEventDateAndVenueId(request.getEventDate(), request.getVenueId());
        if (!fundraisings.isEmpty()) {
            throw new SamePlaceAndDateFundraisingException("Errore: Esiste già una raccolta fondi nello stesso luogo e data!");
        }

        //If a fundraising with the same name exist then throw error
        Fundraising fundraisingByName = fundraisingRepository.findByFundraisingName(request.getFundraisingName());
        if (fundraisingByName != null) {
            throw new SameNameFundraisingException("Errore: Esiste già una raccolta fondi con lo stesso nome!");
        }

        Fundraising fundraising = new Fundraising(
                request.getArtistId(),
                request.getVenueId(),
                request.getFundraisingName(),
                request.getTargetAmount(),
                FundraisingStatus.ACTIVE,
                request.getEventDate()
        );

        fundraising = fundraisingRepository.save(fundraising);

        return mapToDTO(fundraising);
    }

    @Override
    @Transactional
    public FundraisingResponseDTO updateFundraising(String fundraisingId, FundraisingUpdateRequestDTO request) {
        Fundraising fundraising = fundraisingRepository.findById(fundraisingId)
                .orElseThrow(() -> new NotFoundException("Errore: Fundraising non trovato!"));

        if (request.getFundraisingName() != null) {
            fundraising.setFundraisingName(request.getFundraisingName());
        }
        if (request.getVenueId() != null) {
            fundraising.setVenueId(request.getVenueId());
        }
        if (request.getTargetAmount() != null) {
            fundraising.setTargetAmount(request.getTargetAmount());
        }
        if (request.getEventDate() != null) {
            fundraising.setEventDate(request.getEventDate());
        }

        fundraising = fundraisingRepository.save(fundraising);

        return mapToDTO(fundraising);
    }

    @Override
    @Transactional
    public FundraisingResponseDTO cancelFundraisingById(String artistId, String fundraisingId) {
        Fundraising fundraising = fundraisingRepository.findById(fundraisingId)
                .orElseThrow(() -> new NotFoundException("Errore: Fundraising non trovato!"));

        //Only the artist who created the fundraising can cancel it
        if (!fundraising.getArtistId().equals(artistId)) {
            throw new ForbiddenActionException("Errore: Non sei autorizzato a cancellare questa raccolta fondi!");
        }

        fundraising.setStatus(FundraisingStatus.CANCELLED);

        fundraising = fundraisingRepository.save(fundraising);

        return mapToDTO(fundraising);
    }

    //Rabbit
    @Override
    @Transactional
    public FundraisingListResponseDTO disableFundraisingsByUserId(String userId, Role role) {
        return switch (role) {
            case ARTIST -> disableArtistFundraisings(userId);
            //case VENUE_OWNER:
            //TODO implement venue owner disable fundraisings
            default -> new FundraisingListResponseDTO();
        };
    }

    @Override
    @Transactional
    public FundraisingResponseDTO addContributionToFundraising(String fundraisingId, BigDecimal amount) {
        Fundraising fundraising = fundraisingRepository.findById(fundraisingId)
                .orElseThrow(() -> new NotFoundException("Errore: Fundraising non trovato!"));

        fundraising.setCurrentAmount(fundraising.getCurrentAmount().add(amount));

        if (fundraising.getCurrentAmount().compareTo(fundraising.getTargetAmount()) >= 0) {
            fundraising.setStatus(FundraisingStatus.ACHIEVED);
            eventService.createEventFromFundraising(fundraising);
        }

        fundraising = fundraisingRepository.save(fundraising);

        return mapToDTO(fundraising);
    }

    //Utils
    private FundraisingResponseDTO mapToDTO(Fundraising fundraising) {
        return new FundraisingResponseDTO(
                fundraising.getFundraisingId(),
                fundraising.getFundraisingName(),
                fundraising.getArtistId(),
                fundraising.getVenueId(),
                fundraising.getCurrentAmount(),
                fundraising.getTargetAmount(),
                fundraising.getStatus(),
                fundraising.getEventDate()
        );
    }
    private FundraisingListResponseDTO mapToDTOList(List<Fundraising> fundraisings) {
        FundraisingListResponseDTO responseDTO = new FundraisingListResponseDTO();
        for (Fundraising f : fundraisings) {
            responseDTO.addFundraising(mapToDTO(f));
        }
        return responseDTO;
    }
    private FundraisingListResponseDTO disableArtistFundraisings(String artistId) {
        FundraisingListResponseDTO responseDTO = new FundraisingListResponseDTO();
        List<Fundraising> fundraisings = fundraisingRepository.findByArtistId(artistId);
        for (Fundraising f : fundraisings) {
            try {
                if (f.getStatus() != FundraisingStatus.CANCELLED && f.getStatus() != FundraisingStatus.NOT_ACHIEVED) {
                    f.setStatus(FundraisingStatus.CANCELLED);
                    f = fundraisingRepository.save(f);
                    responseDTO.addFundraising(mapToDTO(f));
                }
            } catch (Exception e) {
                return responseDTO;
            }
        }
        return responseDTO;
    }
}
