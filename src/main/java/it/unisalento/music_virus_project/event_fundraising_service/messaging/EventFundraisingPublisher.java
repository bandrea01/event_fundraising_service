package it.unisalento.music_virus_project.event_fundraising_service.messaging;

import it.unisalento.music_virus_project.event_fundraising_service.messaging.dto.EventCreationDTO;
import it.unisalento.music_virus_project.event_fundraising_service.messaging.dto.FundraisingRefundDTO;
import it.unisalento.music_virus_project.event_fundraising_service.messaging.keys.EventFundraisingsRoutingKeys;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class EventFundraisingPublisher {

    private static final Logger log = Logger.getLogger(EventFundraisingPublisher.class.getName());

    private final RabbitTemplate rabbitTemplate;
    private final TopicExchange eventFundraisingExchange;

    public EventFundraisingPublisher(RabbitTemplate rabbitTemplate, TopicExchange eventFundraisingExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.eventFundraisingExchange = eventFundraisingExchange;
    }

    public void publishEventCreation(EventCreationDTO event) {
        log.info("Publishing EventCreation for artistId= " + event.getArtistId());
        rabbitTemplate.convertAndSend(eventFundraisingExchange.getName(),
                EventFundraisingsRoutingKeys.EVENT_CREATED,
                event);
        log.info("Published ContributionAddedEvent");
    }

    public void publishFundraisingRefunded(FundraisingRefundDTO event) {
        log.info("Publishing FundraisingRefunded for fundraisingId= " + event.getFundraisingId());
        rabbitTemplate.convertAndSend(eventFundraisingExchange.getName(),
                EventFundraisingsRoutingKeys.FUNDRAISING_REFUNDED,
                event);
        log.info("Published FundraisingRefundedEvent");
    }

}