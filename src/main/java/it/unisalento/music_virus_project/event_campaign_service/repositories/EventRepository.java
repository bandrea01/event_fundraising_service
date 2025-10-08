package it.unisalento.music_virus_project.event_campaign_service.repositories;

import it.unisalento.music_virus_project.event_campaign_service.domain.entity.Event;
import it.unisalento.music_virus_project.event_campaign_service.domain.enums.EventStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.Instant;
import java.util.List;

public interface EventRepository extends MongoRepository<Event, String> {

    List<Event> findByStatus(EventStatus status);

    List<Event> findByArtistId(String artistId);

    List<Event> findByStatusAndEventDateAfter(EventStatus status, Instant date);
}
