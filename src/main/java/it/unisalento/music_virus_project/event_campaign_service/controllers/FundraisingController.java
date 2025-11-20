package it.unisalento.music_virus_project.event_campaign_service.controllers;

import it.unisalento.music_virus_project.event_campaign_service.dto.fundraising.FundraisingCreateRequestDTO;
import it.unisalento.music_virus_project.event_campaign_service.dto.fundraising.FundraisingCreateResponseDTO;
import it.unisalento.music_virus_project.event_campaign_service.service.IFundraisingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fundraising")
@Validated
public class FundraisingController {

    private final IFundraisingService IFundraisingService;

    public FundraisingController(IFundraisingService IFundraisingService) {
        this.IFundraisingService = IFundraisingService;
    }

    @PostMapping()
    public ResponseEntity<FundraisingCreateResponseDTO> createFundraising(@Valid @RequestBody FundraisingCreateRequestDTO request) {
        var response = IFundraisingService.createFundraising(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
