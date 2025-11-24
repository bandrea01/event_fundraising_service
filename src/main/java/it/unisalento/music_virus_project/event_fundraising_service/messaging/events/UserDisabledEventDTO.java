package it.unisalento.music_virus_project.event_fundraising_service.messaging.events;

import it.unisalento.music_virus_project.event_fundraising_service.domain.entity.Role;

public class UserDisabledEventDTO {

    private String userId;
    private Role role;

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Role getRole() {
        return this.role;
    }
    public void setRole(Role role) {
        this.role = role;
    }

}
