package it.unisalento.music_virus_project.event_fundraising_service.service;

import it.unisalento.music_virus_project.event_fundraising_service.dto.admin.EventsStatisticDTO;

public interface IAdminService {
    EventsStatisticDTO getEventsStatistics();
}
