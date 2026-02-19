package it.unisalento.music_virus_project.event_fundraising_service.scheduler;

import it.unisalento.music_virus_project.event_fundraising_service.domain.entity.Event;
import it.unisalento.music_virus_project.event_fundraising_service.domain.entity.Fundraising;
import it.unisalento.music_virus_project.event_fundraising_service.domain.enums.EventStatus;
import it.unisalento.music_virus_project.event_fundraising_service.domain.enums.FundraisingStatus;
import it.unisalento.music_virus_project.event_fundraising_service.repositories.EventRepository;
import it.unisalento.music_virus_project.event_fundraising_service.repositories.FundraisingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class FundraisingStatusScheduler {

    private static final Logger log = LoggerFactory.getLogger(FundraisingStatusScheduler.class);

    private final FundraisingRepository fundraisingRepository;
    private final EventRepository eventRepository;

    public FundraisingStatusScheduler(FundraisingRepository fundraisingRepository, EventRepository eventRepository) {
        this.fundraisingRepository = fundraisingRepository;
        this.eventRepository = eventRepository;
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

    @Scheduled(cron = "0 0 0 * * *", zone = "Europe/Rome")
    public void scheduleFinishedEvents() {
        System.out.println("Controllo eventi confermati per verificare se sono passati...");
        List<Event> events = eventRepository.findByStatus(EventStatus.CONFIRMED);
        System.out.println("Eventi confermati trovati: " + events.size());

        for (Event event : events) {
            if (event.getEventDate().isBefore(Instant.now())) {
                event.setStatus(EventStatus.FINISHED);
                eventRepository.save(event);
            }
        }
    }

}
