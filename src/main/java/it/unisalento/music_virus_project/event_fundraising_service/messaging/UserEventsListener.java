package it.unisalento.music_virus_project.event_fundraising_service.messaging;

import it.unisalento.music_virus_project.event_fundraising_service.messaging.dto.UserApprovalChangedEventDTO;
import it.unisalento.music_virus_project.event_fundraising_service.messaging.dto.UserEnabledChangedEventDTO;
import it.unisalento.music_virus_project.event_fundraising_service.service.implementation.EventService;
import it.unisalento.music_virus_project.event_fundraising_service.service.implementation.FundraisingService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class UserEventsListener {

    private static final Logger log = LoggerFactory.getLogger(UserEventsListener.class);

    private final EventService eventService;
    private final FundraisingService fundraisingService;

    public UserEventsListener(EventService eventService, FundraisingService fundraisingService) {
        this.eventService = eventService;
        this.fundraisingService = fundraisingService;
    }

    /**
     * Handles the UserApprovalChangedEvent by canceling the user's fundraisings and events if the user is not approved.
     */
    @RabbitListener(queues = "${app.rabbitmq.user-approval-queue}")
    public void handleUserApprovalChangedEvent(UserApprovalChangedEventDTO event) {
        log.info("Received UserApprovalChangedEvent with userId={}, approved={}", event.getUserId(), event.isApproved());
        if(!event.isApproved()) {
            fundraisingService.disableFundraisingsByUserId(event.getUserId(), event.getRole());
            log.info("Fundraisings canceled for userId={} with role={}", event.getUserId(), event.getRole());
            eventService.disableEventsByUserId(event.getUserId(), event.getRole());
            log.info("Events canceled for userId={} with role={}", event.getUserId(), event.getRole());
        }
    }

    /**
     * Handles the UserEnableChangedEvent by disabling the user's fundraisings and events if the user is disabled.
     */
    @RabbitListener(queues = "${app.rabbitmq.user-enable-queue}")
    public void handleUserEnableChangedEvent(UserEnabledChangedEventDTO event) {
        log.info("Received UserEnableChangedEvent with userId={}, enabled={}", event.getUserId(), event.isEnabled());
        if (!event.isEnabled()) {
            fundraisingService.disableFundraisingsByUserId(event.getUserId(), event.getRole());
            log.info("Fundraisings canceled for userId={} with role={}", event.getUserId(), event.getRole());
            eventService.disableEventsByUserId(event.getUserId(), event.getRole());
            log.info("Events enabled for userId={} with role={}", event.getUserId(), event.getRole());
        }
    }

}
