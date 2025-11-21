package it.unisalento.music_virus_project.event_fundraising_service.repositories;

import it.unisalento.music_virus_project.event_fundraising_service.domain.entity.Fundraising;
import it.unisalento.music_virus_project.event_fundraising_service.domain.enums.FundraisingStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.Instant;
import java.util.List;

public interface FundraisingRepository extends MongoRepository<Fundraising, String> {

    Fundraising findByFundraisingId(String fundraisingId);
    List<Fundraising> findByArtistId(String artistId);
    Fundraising findByFundraisingName(String fundraisingName);
    List<Fundraising> findByVenueId(String venueId);
    List<Fundraising> findByStatus(FundraisingStatus status);
    List<Fundraising> findByEventDate(Instant eventDate);
    List<Fundraising> findByEventDateAndVenueId(Instant eventDate, String venueId);
}
