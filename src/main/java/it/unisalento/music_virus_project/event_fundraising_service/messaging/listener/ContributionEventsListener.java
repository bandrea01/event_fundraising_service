package it.unisalento.music_virus_project.event_fundraising_service.messaging.listener;

import it.unisalento.music_virus_project.event_fundraising_service.dto.fundraising.FundraisingResponseDTO;
import it.unisalento.music_virus_project.event_fundraising_service.messaging.events.ContributionAddedEventDTO;
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

    IFundraisingService fundraisingService;

    @RabbitListener(queues = queue)
    public FundraisingResponseDTO onContributionAdded(@Payload ContributionAddedEventDTO event) {
        log.info("Received contribution fundraisingId={} amount={}", event.getFundraisingId(), event.getAmount());
        return fundraisingService.addContributionToFundraising(event.getFundraisingId(), event.getAmount());
    }

}
