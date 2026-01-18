package it.unisalento.music_virus_project.event_fundraising_service.repositories;

import it.unisalento.music_virus_project.event_fundraising_service.domain.entity.Feedback;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FeedbackRepository extends MongoRepository<Feedback, String> {
    List<Feedback> findByEventId(String eventId);
    Feedback deleteFeedbackByFeedbackId(String feedbackId);
}
