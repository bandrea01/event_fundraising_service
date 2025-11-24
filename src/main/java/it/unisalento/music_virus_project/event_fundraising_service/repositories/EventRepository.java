package it.unisalento.music_virus_project.event_fundraising_service.repositories;

import it.unisalento.music_virus_project.event_fundraising_service.domain.entity.Event;
import it.unisalento.music_virus_project.event_fundraising_service.domain.enums.EventStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.Instant;
import java.util.List;

public interface EventRepository extends MongoRepository<Event, String> {

    Event findByEventId(String eventId);
    Event findByFundraisingId(String fundraisingId);
    List<Event> findByArtistId(String artistId);
    List<Event> findByVenueId(String venueId);
    List<Event> findByStatus(EventStatus status);
    List<Event> findByEventDate(Instant eventDate);
    List<Event> findByEventDateBetween(Instant startDate, Instant endDate);

}
