package it.unisalento.music_virus_project.event_fundraising_service.exceptions;

public class SameNameFundraisingException extends RuntimeException{
    public SameNameFundraisingException(String message) {
        super(message);
    }
}
