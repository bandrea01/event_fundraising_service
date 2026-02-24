package it.unisalento.music_virus_project.event_fundraising_service.messaging;

import it.unisalento.music_virus_project.event_fundraising_service.messaging.dto.EventCreationDTO;
import it.unisalento.music_virus_project.event_fundraising_service.messaging.dto.FundraisingRefundDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class RabbitEventFundraisingService {

    private final EventFundraisingPublisher publisher;

    public RabbitEventFundraisingService(EventFundraisingPublisher publisher) {
        this.publisher = publisher;
    }

    public void sendEventCreation(String eventId, String fundraisingId, String artistId, BigDecimal amount) {
        EventCreationDTO event = new EventCreationDTO();
        event.setEventId(eventId);
        event.setFundraisingId(fundraisingId);
        event.setArtistId(artistId);
        event.setAmount(amount);
        publisher.publishEventCreation(event);
    }

    public void sendFundraisingRefundRequest(String fundraisingId, String artistId) {
        FundraisingRefundDTO event = new FundraisingRefundDTO();
        event.setFundraisingId(fundraisingId);
        event.setArtistId(artistId);
        publisher.publishFundraisingRefunded(event);
    }
}
