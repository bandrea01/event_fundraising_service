package it.unisalento.music_virus_project.event_fundraising_service.dto.event;

import java.util.ArrayList;
import java.util.List;

public class FeedbackListResponseDTO {
    private List<FeedbackResponseDTO> feedbacks;

    public FeedbackListResponseDTO() {
        this.feedbacks = new ArrayList<>();
    }

    public List<FeedbackResponseDTO> getFeedbacks() {
        return feedbacks;
    }
    public void setFeedbacks(List<FeedbackResponseDTO> feedbacks) {
        this.feedbacks = feedbacks;
    }
    public void addFeedback(FeedbackResponseDTO feedback) {
        this.feedbacks.add(feedback);
    }
}
