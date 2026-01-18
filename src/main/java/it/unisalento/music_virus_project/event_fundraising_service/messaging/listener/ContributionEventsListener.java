package it.unisalento.music_virus_project.event_fundraising_service.messaging.listener;

import it.unisalento.music_virus_project.event_fundraising_service.messaging.events.ContributionEventDTO;
import it.unisalento.music_virus_project.event_fundraising_service.service.IFundraisingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ContributionEventsListener {

    private static final Logger log = LoggerFactory.getLogger(ContributionEventsListener.class);
    private static final String queue = "${app.rabbitmq.contribution-events-queue}";

    private final IFundraisingService fundraisingService;

    public ContributionEventsListener(IFundraisingService fundraisingService) {
        this.fundraisingService = fundraisingService;
    }

    @RabbitListener(queues = queue)
    public void onContributionAdded(@Payload ContributionEventDTO event) {
        log.info("Received contribution fundraisingId={} amount={}", event.getFundraisingId(), event.getAmount());
        fundraisingService.addContributionToFundraising(event.getFundraisingId(), event.getAmount());
        System.out.println("Received contribution fundraisingId=" + event.getFundraisingId() + " amount=" + event.getAmount());
    }

}
