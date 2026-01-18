package it.unisalento.music_virus_project.event_fundraising_service.domain.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.Instant;

public class Feedback {

    @Id
    private String feedbackId;

    @Indexed
    private String eventId;
    @Indexed
    private String userId;

    private int rating;
    private String comment;

    @CreatedDate
    private Instant createdAt;

    public Feedback() {}

    public void validate() {
        if (rating < 0 || rating > 5)
            throw new IllegalArgumentException("Il rating deve essere compreso tra 0 e 5");
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
