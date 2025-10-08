package it.unisalento.music_virus_project.event_campaign_service.repositories;

import it.unisalento.music_virus_project.event_campaign_service.domain.entity.Tier;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TierRepository extends MongoRepository<Tier, String> {

    List<Tier> findByEventIdOrderByMinAmountAsc(String eventId);
}
