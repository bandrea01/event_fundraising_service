package it.unisalento.music_virus_project.event_fundraising_service.dto.event;

import it.unisalento.music_virus_project.event_fundraising_service.domain.entity.Feedback;

import java.time.Instant;

public class FeedbackResponseDTO {
    private String feedbackId;
    private String eventId;
    private String userId;
    private int rating;
    private String comment;
    private Instant createdAt;

    public FeedbackResponseDTO() {}
    public FeedbackResponseDTO(Feedback feedback) {
        this.feedbackId = feedback.getFeedbackId();
        this.eventId = feedback.getEventId();
        this.userId = feedback.getUserId();
        this.rating = feedback.getRating();
        this.comment = feedback.getComment();
        this.createdAt = feedback.getCreatedAt();
    }

    public String getFeedbackId() {
        return feedbackId;
    }
    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }
    public String getEventId() {
        return eventId;
    }
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
