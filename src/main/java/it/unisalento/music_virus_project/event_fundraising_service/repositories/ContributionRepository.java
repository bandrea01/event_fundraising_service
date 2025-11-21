package it.unisalento.music_virus_project.event_fundraising_service.repositories;

import it.unisalento.music_virus_project.event_fundraising_service.domain.entity.Contribution;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ContributionRepository extends MongoRepository<Contribution, String> {

    List<Contribution> findByFundraisingId(String fundraisingId);
    List<Contribution> findByUserId(String userId);

}
