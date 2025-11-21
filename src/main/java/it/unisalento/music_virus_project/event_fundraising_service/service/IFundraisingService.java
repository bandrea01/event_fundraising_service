package it.unisalento.music_virus_project.event_fundraising_service.service;

import it.unisalento.music_virus_project.event_fundraising_service.dto.fundraising.FundraisingCreateRequestDTO;
import it.unisalento.music_virus_project.event_fundraising_service.dto.fundraising.FundraisingCreateResponseDTO;

public interface IFundraisingService {
    FundraisingCreateResponseDTO createFundraising(FundraisingCreateRequestDTO request);
}
