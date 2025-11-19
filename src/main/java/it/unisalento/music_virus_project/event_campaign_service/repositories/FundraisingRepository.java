package it.unisalento.music_virus_project.event_campaign_service.repositories;

import it.unisalento.music_virus_project.event_campaign_service.domain.entity.Fundraising;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FundraisingRepository extends MongoRepository<Fundraising, String> {

    Fundraising findByFundraisingId(String fundraisingId);
    Fundraising findByEventId(String eventId);
    List<Fundraising> findByArtistId(String artistId);
    List<Fundraising> findByVenueId(String venueId);
    List<Fundraising> findByStatus(String status);

}
