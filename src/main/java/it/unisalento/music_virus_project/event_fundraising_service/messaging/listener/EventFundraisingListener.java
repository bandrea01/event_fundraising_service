package it.unisalento.music_virus_project.event_fundraising_service.messaging.listener;

import it.unisalento.music_virus_project.event_fundraising_service.messaging.dto.ContributionEventDTO;
import it.unisalento.music_virus_project.event_fundraising_service.service.IFundraisingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EventFundraisingListener {

    private static final Logger log = LoggerFactory.getLogger(EventFundraisingListener.class);

    private final IFundraisingService fundraisingService;

    public EventFundraisingListener(IFundraisingService fundraisingService) {
        this.fundraisingService = fundraisingService;
    }

    @RabbitListener(queues="${app.rabbitmq.contribution-events-queue}")
    public void handleContributionAdded(@Payload ContributionEventDTO event) {
        log.info("Received contribution fundraisingId={} amount={}", event.getFundraisingId(), event.getAmount());
        fundraisingService.addContributionToFundraising(event.getFundraisingId(), event.getAmount());
        System.out.println("Received contribution fundraisingId=" + event.getFundraisingId() + " amount=" + event.getAmount());
    }

}
