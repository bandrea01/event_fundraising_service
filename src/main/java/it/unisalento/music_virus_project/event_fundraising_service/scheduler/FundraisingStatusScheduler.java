package it.unisalento.music_virus_project.event_fundraising_service.scheduler;

import it.unisalento.music_virus_project.event_fundraising_service.domain.entity.Fundraising;
import it.unisalento.music_virus_project.event_fundraising_service.domain.enums.FundraisingStatus;
import it.unisalento.music_virus_project.event_fundraising_service.repositories.FundraisingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class FundraisingStatusScheduler {

    private static final Logger log = LoggerFactory.getLogger(FundraisingStatusScheduler.class);

    private final FundraisingRepository fundraisingRepository;

    public FundraisingStatusScheduler(FundraisingRepository fundraisingRepository) {
        this.fundraisingRepository = fundraisingRepository;
    }

    @Scheduled(fixedDelay = 10 * 60 * 1000)
    public void scheduleNotAchievedFundraising() {
        List<Fundraising> fundraisings = fundraisingRepository.findByStatus(FundraisingStatus.ACTIVE);

        for (Fundraising fundraising : fundraisings) {
            if (fundraising.getExpirationDate().isBefore(Instant.now())) {
                fundraising.setStatus(FundraisingStatus.NOT_ACHIEVED);
                fundraisingRepository.save(fundraising);
            }
        }
    }

}
