package it.unisalento.music_virus_project.event_campaign_service.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) { super(message); }
}
