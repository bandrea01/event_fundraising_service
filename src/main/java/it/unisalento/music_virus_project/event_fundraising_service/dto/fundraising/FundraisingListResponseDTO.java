package it.unisalento.music_virus_project.event_fundraising_service.dto.fundraising;

import java.util.ArrayList;
import java.util.List;

public class FundraisingListResponseDTO {
    private List<FundraisingResponseDTO> fundraisings;

    public FundraisingListResponseDTO(List<FundraisingResponseDTO> fundraisings) {
        this.fundraisings = fundraisings;
    }
    public FundraisingListResponseDTO() {
        this.fundraisings = new ArrayList<>();
    }

    public List<FundraisingResponseDTO> getFundraisings() {
        return fundraisings;
    }

    public void setFundraisings(List<FundraisingResponseDTO> fundraisings) {
        this.fundraisings = fundraisings;
    }

    public void addFundraising(FundraisingResponseDTO fundraising) {
        this.fundraisings.add(fundraising);
    }
}
