package it.unisalento.music_virus_project.event_campaign_service.exceptions;

public class ForbiddenActionException extends RuntimeException {
    public ForbiddenActionException(String message) { super(message); }
}
