package it.unisalento.music_virus_project.event_fundraising_service.messaging.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class UserEventsListener {

    private static final Logger log = LoggerFactory.getLogger(UserEventsListener.class);

    // qui puoi iniettare i tuoi service, es. ArtistProfileService, ecc.

    @RabbitListener(queues = "${app.rabbitmq.user-events-queue}")
    public void onUserRegistered(@Payload UserCreatedEventDTO event) {
//        log.info("Received UserRegisteredEvent userId={}", event.getUserId());
        // TODO: creare eventuali entità o sincronizzare dati user -> event_campaign
    }

//    @RabbitListener(queues = "${app.rabbitmq.user-events-queue}")
//    public void onUserProfileUpdated(@Payload UserProfileUpdatedEvent event) {
//        log.info("Received UserProfileUpdatedEvent userId={} enabled={}", event.getUserId(), event.isEnabled());
//        // TODO: aggiornare stato, nome visualizzato, ecc. nel dominio event_campaign
//    }

    @RabbitListener(queues = "${app.rabbitmq.user-events-queue}")
    public void onUserDisabled(@Payload UserDisabledEventDTO event) {
        log.info("Received UserDisabledEvent userId={}", event.getUserId());
        // TODO: bloccare creazione nuove campagne, aggiornare flag, ecc.
    }
}
