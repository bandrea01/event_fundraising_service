package it.unisalento.music_virus_project.event_fundraising_service.controllers;

import it.unisalento.music_virus_project.event_fundraising_service.domain.enums.FundraisingStatus;
import it.unisalento.music_virus_project.event_fundraising_service.dto.fundraising.FundraisingCreateRequestDTO;
import it.unisalento.music_virus_project.event_fundraising_service.dto.fundraising.FundraisingListResponseDTO;
import it.unisalento.music_virus_project.event_fundraising_service.dto.fundraising.FundraisingResponseDTO;
import it.unisalento.music_virus_project.event_fundraising_service.dto.fundraising.FundraisingUpdateRequestDTO;
import it.unisalento.music_virus_project.event_fundraising_service.service.IFundraisingService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = FundraisingController.class)
@Import(FundraisingControllerTest.TestSecurityConfig.class)
class FundraisingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IFundraisingService fundraisingService;

    /**
     * IMPORTANTISSIMO:
     * serve perché oauth2ResourceServer().jwt() richiede un JwtDecoder bean.
     * In @WebMvcTest non viene creato automaticamente -> lo mockiamo.
     */
    @MockBean
    private JwtDecoder jwtDecoder;

    /**
     * Security di test:
     * - filtri attivi (così @AuthenticationPrincipal Jwt viene risolto)
     * - permitAll (così non ti blocca)
     * - oauth2ResourceServer(jwt) (così .with(jwt()) funziona)
     * - disabilitiamo i @PreAuthorize per test "senza security"
     */
    @EnableMethodSecurity(prePostEnabled = false)
    static class TestSecurityConfig {
        @Bean
        SecurityFilterChain testFilterChain(HttpSecurity http) throws Exception {
            return http
                    .csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                    .oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()))
                    .build();
        }
    }

    // -------------------------
    // Helpers JSON
    // -------------------------
    private static String jsonCreateRequest() {
        return """
            {
              "artistId": "artist1",
              "artist": "Artist Name",
              "venueName": "Venue Name",
              "venueId": "venue1",
              "eventDate": "2026-02-11T10:00:00Z",
              "fundraisingName": "My Fundraising",
              "targetAmount": 100
            }
        """;
    }

    private static String jsonUpdateRequest() {
        return """
            {
              "fundraisingName": "Updated Name",
              "venueId": "venue2",
              "targetAmount": 200,
              "eventDate": "2026-03-01T10:00:00Z"
            }
        """;
    }

    private static FundraisingResponseDTO sampleResponse(String id, FundraisingStatus status) {
        return new FundraisingResponseDTO(
                id,
                "My Fundraising",
                "artist1",
                "venue1",
                BigDecimal.ZERO,
                new BigDecimal("100"),
                status,
                Instant.parse("2026-02-11T10:00:00Z"),
                Instant.parse("2026-02-11T10:00:00Z")
        );
    }

    // -------------------------
    // TESTS
    // -------------------------

    @Test
    void getPersonalFundraising_returnsOk() throws Exception {
        FundraisingListResponseDTO list = new FundraisingListResponseDTO(List.of(
                sampleResponse("f1", FundraisingStatus.ACTIVE)
        ));

        when(fundraisingService.getFundraisingsByArtistId("artist1")).thenReturn(list);

        mockMvc.perform(get("/api/event-fundraising/fundraising/me")
                        .with(jwt().jwt(j -> j.claim("userId", "artist1"))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void confirmFundraising_returnsOk() throws Exception {
        FundraisingResponseDTO response = sampleResponse("f1", FundraisingStatus.CONFIRMED);
        when(fundraisingService.confirmFundraisingById("artist1", "f1")).thenReturn(response);

        mockMvc.perform(patch("/api/event-fundraising/fundraising/confirm/{fundraisingId}", "f1")
                        .with(jwt().jwt(j -> j.claim("userId", "artist1"))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.fundraisingId").value("f1"))
                .andExpect(jsonPath("$.status").value("CONFIRMED"));
    }

    @Test
    void cancelFundraising_returnsOk() throws Exception {
        FundraisingResponseDTO response = sampleResponse("f1", FundraisingStatus.CANCELLED);
        when(fundraisingService.disableFundraisingById("artist1", "f1")).thenReturn(response);

        mockMvc.perform(patch("/api/event-fundraising/fundraising/cancel/{fundraisingId}", "f1")
                        .with(jwt().jwt(j -> j.claim("userId", "artist1"))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CANCELLED"));
    }

    @Test
    void getFundraisingById_returnsOk() throws Exception {
        FundraisingResponseDTO response = sampleResponse("f1", FundraisingStatus.ACTIVE);
        when(fundraisingService.getFundraisingById("f1")).thenReturn(response);

        mockMvc.perform(get("/api/event-fundraising/fundraising/{fundraisingId}", "f1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fundraisingId").value("f1"));
    }

    @Test
    void getOthersFundraisings_returnsOk() throws Exception {
        FundraisingListResponseDTO list = new FundraisingListResponseDTO(List.of(
                sampleResponse("f2", FundraisingStatus.ACTIVE)
        ));
        when(fundraisingService.getOthersFundraisings("artist1")).thenReturn(list);

        mockMvc.perform(get("/api/event-fundraising/fundraising")
                        .with(jwt().jwt(j -> j.claim("userId", "artist1"))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void createFundraising_returnsCreated() throws Exception {
        FundraisingResponseDTO response = sampleResponse("f1", FundraisingStatus.ACTIVE);

        when(fundraisingService.createFundraising(ArgumentMatchers.any(FundraisingCreateRequestDTO.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/event-fundraising/fundraising")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCreateRequest()))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fundraisingId").value("f1"));
    }

    @Test
    void updateFundraising_returnsOk() throws Exception {
        FundraisingResponseDTO response = sampleResponse("f1", FundraisingStatus.ACTIVE);

        when(fundraisingService.updateFundraising(
                ArgumentMatchers.eq("f1"),
                ArgumentMatchers.any(FundraisingUpdateRequestDTO.class)
        )).thenReturn(response);

        mockMvc.perform(patch("/api/event-fundraising/fundraising/{fundraisingId}", "f1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdateRequest()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fundraisingId").value("f1"));
    }
}
