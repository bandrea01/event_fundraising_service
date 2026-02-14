package it.unisalento.music_virus_project.event_fundraising_service.service.implementation;

import it.unisalento.music_virus_project.event_fundraising_service.domain.entity.Fundraising;
import it.unisalento.music_virus_project.event_fundraising_service.domain.entity.Role;
import it.unisalento.music_virus_project.event_fundraising_service.domain.enums.FundraisingStatus;
import it.unisalento.music_virus_project.event_fundraising_service.dto.fundraising.*;
import it.unisalento.music_virus_project.event_fundraising_service.exceptions.ForbiddenActionException;
import it.unisalento.music_virus_project.event_fundraising_service.exceptions.NotFoundException;
import it.unisalento.music_virus_project.event_fundraising_service.exceptions.SameNameFundraisingException;
import it.unisalento.music_virus_project.event_fundraising_service.exceptions.SamePlaceAndDateFundraisingException;
import it.unisalento.music_virus_project.event_fundraising_service.messaging.RabbitEventFundraisingService;
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

    private final RabbitEventFundraisingService rabbitEventFundraisingService;

    public FundraisingService(FundraisingRepository fundraisingRepository, EventService eventService, RabbitEventFundraisingService rabbitEventFundraisingService) {
        this.fundraisingRepository = fundraisingRepository;
        this.eventService = eventService;
        this.rabbitEventFundraisingService = rabbitEventFundraisingService;
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
    public FundraisingListResponseDTO getFundraisingsByArtistId(String artistId) {
        List<Fundraising> fundraisings = fundraisingRepository.findByArtistId(artistId);
        return mapToDTOList(fundraisings);
    }

    @Override
    public FundraisingListResponseDTO getOthersFundraisings(String artistId) {
        List<Fundraising> fundraisings = fundraisingRepository.findByArtistIdNot(artistId);
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
        Fundraising fundraisingByName = fundraisingRepository.findByFundraisingNameAndVenueId(request.getFundraisingName(), request.getVenueId());
        if (fundraisingByName != null) {
            if (fundraisingByName.getStatus() != FundraisingStatus.CANCELLED) {
                throw new SameNameFundraisingException("Errore: Esiste già una raccolta fondi con lo stesso nome nello stesso luogo!");
            }
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
            if (request.getTargetAmount().compareTo(fundraising.getCurrentAmount()) < 0) {
                throw new IllegalArgumentException("Errore: L'importo target non può essere inferiore all'importo attuale!");
            }
            if (fundraising.getStatus() == FundraisingStatus.ACHIEVED &&
                    request.getTargetAmount().compareTo(fundraising.getCurrentAmount()) > 0) {
                fundraising.setStatus(FundraisingStatus.ACTIVE);
            }
            fundraising.setTargetAmount(request.getTargetAmount());
        }
        if (request.getEventDate() != null) {
            fundraising.setEventDate(request.getEventDate());
            fundraising.setExpirationDate(request.getEventDate().minusSeconds(Fundraising.EXPIRATION_OFFSET_SECONDS));
        }

        fundraising = fundraisingRepository.save(fundraising);

        return mapToDTO(fundraising);
    }

    @Override
    @Transactional
    public FundraisingResponseDTO disableFundraisingById(String artistId, String fundraisingId) {
        Fundraising fundraising = fundraisingRepository.findById(fundraisingId)
                .orElseThrow(() -> new NotFoundException("Errore: Fundraising non trovato!"));

        //Only the artist who created the fundraising can cancel it
        if (!fundraising.getArtistId().equals(artistId)) {
            throw new ForbiddenActionException("Errore: Non sei autorizzato a cancellare questa raccolta fondi!");
        }

        fundraising.setStatus(FundraisingStatus.CANCELLED);
        fundraising = fundraisingRepository.save(fundraising);

        //rabbit
        rabbitEventFundraisingService.sendFundraisingRefundRequest(fundraisingId, artistId);

        return mapToDTO(fundraising);
    }

    @Override
    @Transactional
    public FundraisingListResponseDTO disableFundraisingsByUserId(String userId, Role role) {
        return switch (role) {
            case ARTIST -> disableArtistFundraisings(userId);
            case VENUE -> disableVenuesFundraisings(userId);
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
        }

        fundraising = fundraisingRepository.save(fundraising);

        return mapToDTO(fundraising);
    }

    @Override
    @Transactional
    public FundraisingResponseDTO confirmFundraisingById(String artistId, String fundraisingId) {
        Fundraising fundraising = fundraisingRepository.findById(fundraisingId)
                .orElseThrow(() -> new NotFoundException("Errore: Fundraising non trovato!"));

        //Only the artist who created the fundraising can confirm it
        if (!fundraising.getArtistId().equals(artistId)) {
            throw new ForbiddenActionException("Errore: Non sei autorizzato a confermare questa raccolta fondi!");
        }
        if (fundraising.getStatus() != FundraisingStatus.ACHIEVED) {
            throw new ForbiddenActionException("Errore: La raccolta fondi deve essere completata prima di essere confermata!");
        }

        fundraising.setStatus(FundraisingStatus.CONFIRMED);
        fundraising = fundraisingRepository.save(fundraising);

        eventService.createEventFromFundraising(fundraising);

        return mapToDTO(fundraising);
    }

    @Override
    @Transactional
    public FundraisingResponseDTO addVenuePromotionToFundraising(String fundraisingId, VenuePromotionRequestDTO request) {
        Fundraising fundraising = fundraisingRepository.findById(fundraisingId)
                .orElseThrow(() -> new NotFoundException("Errore: Fundraising non trovato!"));

        System.out.println("Promozione richiesta: " + request.getPromotion());
        fundraising.setVenuePromotion(request.getPromotion());
        fundraising = fundraisingRepository.save(fundraising);
        System.out.println("Promozione salvata: " + fundraising.getVenuePromotion());
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
                fundraising.getVenuePromotion(),
                fundraising.getEventDate(),
                fundraising.getExpirationDate()
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
        List<Fundraising> fundraisings = fundraisingRepository.findByArtistId(artistId);
        FundraisingListResponseDTO responseDTO = new FundraisingListResponseDTO();
        for (Fundraising fundraising : fundraisings) {
            fundraising.setStatus(FundraisingStatus.CANCELLED);
            fundraising = fundraisingRepository.save(fundraising);
            if (fundraising.getStatus() == FundraisingStatus.CANCELLED) {
                responseDTO.addFundraising(mapToDTO(fundraising));
                //rabbit
                rabbitEventFundraisingService.sendFundraisingRefundRequest(fundraising.getFundraisingId(), artistId);
            } else {
                throw new RuntimeException("Errore: Impossibile cancellare la raccolta fondi con id " + fundraising.getFundraisingId());
            }
        }
        return responseDTO;
    }

    private FundraisingListResponseDTO disableVenuesFundraisings(String venueId) {
        List<Fundraising> fundraisings = fundraisingRepository.findByVenueId(venueId);
        FundraisingListResponseDTO responseDTO = new FundraisingListResponseDTO();
        for (Fundraising fundraising : fundraisings) {
            fundraising.setStatus(FundraisingStatus.CANCELLED);
            fundraising = fundraisingRepository.save(fundraising);
            if (fundraising.getStatus() == FundraisingStatus.CANCELLED) {
                responseDTO.addFundraising(mapToDTO(fundraising));
                //rabbit
                rabbitEventFundraisingService.sendFundraisingRefundRequest(fundraising.getFundraisingId(), fundraising.getArtistId());
            } else {
                throw new RuntimeException("Errore: Impossibile cancellare la raccolta fondi con id " + fundraising.getFundraisingId());
            }
        }
        return responseDTO;
    }
}
