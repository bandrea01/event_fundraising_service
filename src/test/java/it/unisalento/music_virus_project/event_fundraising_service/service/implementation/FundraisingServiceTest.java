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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FundraisingServiceTest {

    @Mock
    private FundraisingRepository fundraisingRepository;

    @Mock
    private EventService eventService;

    @InjectMocks
    private FundraisingService fundraisingService;

    private static final Instant NOW = Instant.parse("2026-02-11T10:00:00Z");

    @BeforeEach
    void setup() {
        // nothing
    }

    // ------------------------------------------------------------
    // Helpers
    // ------------------------------------------------------------

    /**
     * Stubs ONLY the getters that mapToDTO() uses.
     * Keep this tight to avoid UnnecessaryStubbingException: call it only when you actually need mapping.
     */
    private void stubForMapping(
            Fundraising fundraising,
            String fundraisingId,
            String fundraisingName,
            String artistId,
            String venueId,
            BigDecimal currentAmount,
            BigDecimal targetAmount,
            FundraisingStatus status,
            Instant eventDate,
            Instant expirationDate
    ) {
        when(fundraising.getFundraisingId()).thenReturn(fundraisingId);
        when(fundraising.getFundraisingName()).thenReturn(fundraisingName);
        when(fundraising.getArtistId()).thenReturn(artistId);
        when(fundraising.getVenueId()).thenReturn(venueId);
        when(fundraising.getCurrentAmount()).thenReturn(currentAmount);
        when(fundraising.getTargetAmount()).thenReturn(targetAmount);
        when(fundraising.getStatus()).thenReturn(status);
        when(fundraising.getEventDate()).thenReturn(eventDate);
        when(fundraising.getExpirationDate()).thenReturn(expirationDate);
    }

    private FundraisingCreateRequestDTO buildCreateRequest() {
        FundraisingCreateRequestDTO req = new FundraisingCreateRequestDTO();
        req.setArtistId("artist-1");
        req.setVenueId("venue-1");
        req.setFundraisingName("My Fundraising");
        req.setTargetAmount(new BigDecimal("100"));
        req.setEventDate(NOW.plusSeconds(86400));
        return req;
    }

    // ------------------------------------------------------------
    // getFundraisingById
    // ------------------------------------------------------------

    @Test
    void getFundraisingById_whenNotFound_throws() {
        when(fundraisingRepository.findByFundraisingId("f1")).thenReturn(null);
        assertThrows(NotFoundException.class, () -> fundraisingService.getFundraisingById("f1"));
    }

    @Test
    void getFundraisingById_whenFound_returnsDTO() {
        Fundraising fundraising = mock(Fundraising.class);
        when(fundraisingRepository.findByFundraisingId("f1")).thenReturn(fundraising);

        stubForMapping(
                fundraising,
                "f1", "Name", "artist-1", "venue-1",
                new BigDecimal("10"), new BigDecimal("100"),
                FundraisingStatus.ACTIVE,
                NOW, NOW.plusSeconds(1000)
        );

        FundraisingResponseDTO out = fundraisingService.getFundraisingById("f1");
        assertNotNull(out);
        assertEquals("f1", out.getFundraisingId());
        assertEquals(FundraisingStatus.ACTIVE, out.getStatus());
    }

    // ------------------------------------------------------------
    // createFundraising
    // ------------------------------------------------------------

    @Test
    void createFundraising_whenSamePlaceAndDate_throws() {
        FundraisingCreateRequestDTO req = buildCreateRequest();

        when(fundraisingRepository.findByEventDateAndVenueId(req.getEventDate(), req.getVenueId()))
                .thenReturn(List.of(mock(Fundraising.class)));

        assertThrows(SamePlaceAndDateFundraisingException.class, () -> fundraisingService.createFundraising(req));
    }

    @Test
    void createFundraising_whenSameName_throws() {
        FundraisingCreateRequestDTO req = buildCreateRequest();

        when(fundraisingRepository.findByEventDateAndVenueId(req.getEventDate(), req.getVenueId()))
                .thenReturn(List.of()); // ok
        when(fundraisingRepository.findByFundraisingName(req.getFundraisingName()))
                .thenReturn(mock(Fundraising.class)); // name collision

        assertThrows(SameNameFundraisingException.class, () -> fundraisingService.createFundraising(req));
    }

    @Test
    void createFundraising_whenOk_savesAndReturnsDTO() throws Exception {
        FundraisingCreateRequestDTO req = buildCreateRequest();

        when(fundraisingRepository.findByEventDateAndVenueId(req.getEventDate(), req.getVenueId()))
                .thenReturn(List.of());
        when(fundraisingRepository.findByFundraisingName(req.getFundraisingName()))
                .thenReturn(null);

        // capture what gets saved (real entity created inside service)
        ArgumentCaptor<Fundraising> captor = ArgumentCaptor.forClass(Fundraising.class);

        Fundraising saved = mock(Fundraising.class);
        when(fundraisingRepository.save(captor.capture())).thenReturn(saved);

        // mapping uses getters of "saved"
        stubForMapping(
                saved,
                "f1", req.getFundraisingName(), req.getArtistId(), req.getVenueId(),
                new BigDecimal("0"), req.getTargetAmount(),
                FundraisingStatus.ACTIVE,
                req.getEventDate(),
                req.getEventDate().minusSeconds(Fundraising.EXPIRATION_OFFSET_SECONDS)
        );

        FundraisingResponseDTO out = fundraisingService.createFundraising(req);

        assertNotNull(out);
        assertEquals("f1", out.getFundraisingId());
        assertEquals(FundraisingStatus.ACTIVE, out.getStatus());

        Fundraising toSave = captor.getValue();
        assertEquals(req.getArtistId(), toSave.getArtistId());
        assertEquals(req.getVenueId(), toSave.getVenueId());
        assertEquals(req.getFundraisingName(), toSave.getFundraisingName());
        assertEquals(req.getTargetAmount(), toSave.getTargetAmount());
        assertEquals(FundraisingStatus.ACTIVE, toSave.getStatus());
    }

    // ------------------------------------------------------------
    // updateFundraising
    // ------------------------------------------------------------

    @Test
    void updateFundraising_whenNotFound_throws() {
        when(fundraisingRepository.findById("id")).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> fundraisingService.updateFundraising("id", new FundraisingUpdateRequestDTO()));
    }

    @Test
    void updateFundraising_whenTargetLessThanCurrent_throwsIllegalArgument() {
        Fundraising fundraising = mock(Fundraising.class);
        when(fundraisingRepository.findById("id")).thenReturn(Optional.of(fundraising));

        when(fundraising.getCurrentAmount()).thenReturn(new BigDecimal("80"));

        FundraisingUpdateRequestDTO req = new FundraisingUpdateRequestDTO();
        req.setTargetAmount(new BigDecimal("50"));

        assertThrows(IllegalArgumentException.class, () -> fundraisingService.updateFundraising("id", req));
    }

    @Test
    void updateFundraising_whenAchievedAndTargetIncreases_setsActive() {
        Fundraising fundraising = mock(Fundraising.class);
        when(fundraisingRepository.findById("id")).thenReturn(Optional.of(fundraising));

        when(fundraising.getCurrentAmount()).thenReturn(new BigDecimal("100"));
        // first call used by if, second call used by mapToDTO -> ACTIVE
        when(fundraising.getStatus()).thenReturn(FundraisingStatus.ACHIEVED, FundraisingStatus.ACTIVE);

        FundraisingUpdateRequestDTO req = new FundraisingUpdateRequestDTO();
        req.setTargetAmount(new BigDecimal("150"));

        when(fundraisingRepository.save(fundraising)).thenReturn(fundraising);

        // mapping getters (current/target/status/date/expiration)
        when(fundraising.getFundraisingId()).thenReturn("f1");
        when(fundraising.getFundraisingName()).thenReturn("Name");
        when(fundraising.getArtistId()).thenReturn("artist-1");
        when(fundraising.getVenueId()).thenReturn("venue-1");
        when(fundraising.getTargetAmount()).thenReturn(new BigDecimal("150"));
        when(fundraising.getEventDate()).thenReturn(NOW);
        when(fundraising.getExpirationDate()).thenReturn(NOW.minusSeconds(1));

        FundraisingResponseDTO out = fundraisingService.updateFundraising("id", req);

        assertNotNull(out);
        assertEquals(FundraisingStatus.ACTIVE, out.getStatus());

        verify(fundraising).setStatus(FundraisingStatus.ACTIVE);
        verify(fundraising).setTargetAmount(new BigDecimal("150"));
        verify(fundraisingRepository).save(fundraising);
    }

    // ------------------------------------------------------------
    // addContributionToFundraising
    // ------------------------------------------------------------

    @Test
    void addContributionToFundraising_whenNotFound_throws() {
        when(fundraisingRepository.findById("id")).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class,
                () -> fundraisingService.addContributionToFundraising("id", BigDecimal.TEN));
    }

    @Test
    void addContributionToFundraising_whenReachesTarget_setsAchieved() {
        Fundraising fundraising = mock(Fundraising.class);
        when(fundraisingRepository.findById("id")).thenReturn(Optional.of(fundraising));

        // 90 + 10 = 100 -> reached target
        when(fundraising.getCurrentAmount()).thenReturn(new BigDecimal("90"));
        when(fundraising.getTargetAmount()).thenReturn(new BigDecimal("100"));

        // after update, mapping reads current = 100, status = ACHIEVED
        when(fundraising.getCurrentAmount()).thenReturn(new BigDecimal("90"), new BigDecimal("100"));
        when(fundraising.getStatus()).thenReturn(FundraisingStatus.ACHIEVED);

        when(fundraisingRepository.save(fundraising)).thenReturn(fundraising);

        when(fundraising.getFundraisingId()).thenReturn("f1");
        when(fundraising.getFundraisingName()).thenReturn("Name");
        when(fundraising.getArtistId()).thenReturn("artist-1");
        when(fundraising.getVenueId()).thenReturn("venue-1");
        when(fundraising.getEventDate()).thenReturn(NOW);
        when(fundraising.getExpirationDate()).thenReturn(NOW.minusSeconds(1));

        FundraisingResponseDTO out = fundraisingService.addContributionToFundraising("id", BigDecimal.TEN);

        assertNotNull(out);
        assertEquals(FundraisingStatus.ACHIEVED, out.getStatus());

        verify(fundraising).setCurrentAmount(new BigDecimal("100"));
        verify(fundraising).setStatus(FundraisingStatus.ACHIEVED);
        verify(fundraisingRepository).save(fundraising);
    }

    // ------------------------------------------------------------
    // disableFundraisingById
    // ------------------------------------------------------------

    @Test
    void disableFundraisingById_whenNotOwner_throwsForbidden() {
        Fundraising fundraising = mock(Fundraising.class);
        when(fundraisingRepository.findById("id")).thenReturn(Optional.of(fundraising));

        when(fundraising.getArtistId()).thenReturn("artist-owner");

        assertThrows(ForbiddenActionException.class,
                () -> fundraisingService.disableFundraisingById("another-artist", "id"));
    }

    @Test
    void disableFundraisingById_whenOk_setsCancelled() {
        Fundraising fundraising = mock(Fundraising.class);
        when(fundraisingRepository.findById("id")).thenReturn(Optional.of(fundraising));

        when(fundraising.getArtistId()).thenReturn("artist-1");
        when(fundraisingRepository.save(fundraising)).thenReturn(fundraising);

        stubForMapping(
                fundraising,
                "f1", "Name", "artist-1", "venue-1",
                new BigDecimal("10"), new BigDecimal("100"),
                FundraisingStatus.CANCELLED,
                NOW, NOW.plusSeconds(1000)
        );

        FundraisingResponseDTO out = fundraisingService.disableFundraisingById("artist-1", "id");

        assertNotNull(out);
        assertEquals(FundraisingStatus.CANCELLED, out.getStatus());
        verify(fundraising).setStatus(FundraisingStatus.CANCELLED);
        verify(fundraisingRepository).save(fundraising);
    }

    // ------------------------------------------------------------
    // confirmFundraisingById
    // ------------------------------------------------------------

    @Test
    void confirmFundraisingById_whenNotOwner_throwsForbidden() {
        Fundraising fundraising = mock(Fundraising.class);
        when(fundraisingRepository.findById("id")).thenReturn(Optional.of(fundraising));

        when(fundraising.getArtistId()).thenReturn("artist-owner");

        assertThrows(ForbiddenActionException.class,
                () -> fundraisingService.confirmFundraisingById("another-artist", "id"));
    }

    @Test
    void confirmFundraisingById_whenNotAchieved_throwsForbidden() {
        Fundraising fundraising = mock(Fundraising.class);
        when(fundraisingRepository.findById("id")).thenReturn(Optional.of(fundraising));

        when(fundraising.getArtistId()).thenReturn("artist-1");
        when(fundraising.getStatus()).thenReturn(FundraisingStatus.ACTIVE);

        assertThrows(ForbiddenActionException.class,
                () -> fundraisingService.confirmFundraisingById("artist-1", "id"));
    }

    @Test
    void confirmFundraisingById_whenOk_setsConfirmed_andCreatesEvent() {
        Fundraising fundraising = mock(Fundraising.class);
        when(fundraisingRepository.findById("id")).thenReturn(Optional.of(fundraising));

        when(fundraising.getArtistId()).thenReturn("artist-1");

        // 1a chiamata: passa la if (ACHIEVED)
        // 2a chiamata: mapToDTO vede CONFIRMED
        when(fundraising.getStatus())
                .thenReturn(FundraisingStatus.ACHIEVED)
                .thenReturn(FundraisingStatus.CONFIRMED);

        when(fundraisingRepository.save(fundraising)).thenReturn(fundraising);

        // mapping calls
        when(fundraising.getFundraisingId()).thenReturn("f1");
        when(fundraising.getFundraisingName()).thenReturn("Name");
        when(fundraising.getVenueId()).thenReturn("venue-1");
        when(fundraising.getCurrentAmount()).thenReturn(new BigDecimal("100"));
        when(fundraising.getTargetAmount()).thenReturn(new BigDecimal("100"));
        when(fundraising.getEventDate()).thenReturn(NOW);
        when(fundraising.getExpirationDate()).thenReturn(NOW.minusSeconds(1));

        FundraisingResponseDTO out = fundraisingService.confirmFundraisingById("artist-1", "id");

        assertNotNull(out);
        assertEquals(FundraisingStatus.CONFIRMED, out.getStatus());

        verify(fundraising).setStatus(FundraisingStatus.CONFIRMED);
        verify(fundraisingRepository).save(fundraising);
        verify(eventService).createEventFromFundraising(fundraising);
    }

    // ------------------------------------------------------------
    // disableFundraisingsByUserId (simple smoke)
    // ------------------------------------------------------------

    @Test
    void disableFundraisingsByUserId_whenRoleOther_returnsEmptyListDTO() {
        FundraisingListResponseDTO out = fundraisingService.disableFundraisingsByUserId("u1", Role.FAN);
        assertNotNull(out);
        assertNotNull(out.getFundraisings());
        assertTrue(out.getFundraisings().isEmpty());
    }
}
