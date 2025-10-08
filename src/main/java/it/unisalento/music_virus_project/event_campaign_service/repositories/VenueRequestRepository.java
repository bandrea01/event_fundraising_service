package it.unisalento.music_virus_project.event_campaign_service.repositories;

import it.unisalento.music_virus_project.event_campaign_service.domain.entity.VenueRequest;
import it.unisalento.music_virus_project.event_campaign_service.domain.enums.VenueRequestStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VenueRequestRepository extends MongoRepository<VenueRequest, String> {

    List<VenueRequest> findByVenueIdAndStatus(String venueId, VenueRequestStatus status);

    List<VenueRequest> findByEventId(String eventId);
}
