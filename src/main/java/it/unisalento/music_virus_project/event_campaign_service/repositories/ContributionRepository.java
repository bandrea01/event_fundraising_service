package it.unisalento.music_virus_project.event_campaign_service.repositories;

import it.unisalento.music_virus_project.event_campaign_service.domain.entity.Contribution;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ContributionRepository extends MongoRepository<Contribution, String> {

    List<Contribution> findByEventId(String eventId);

    List<Contribution> findByFanId(String fanId);
}
