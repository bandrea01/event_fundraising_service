package it.unisalento.music_virus_project.event_campaign_service.dto;

import java.util.List;

public class ArtistProfileDTO {
    private String userId;
    private String name;
    private String surname;
    private Boolean approved;
    private String role; // "ARTIST"
    private List<String> artistGenres;
    private List<String> artistSocials;

    public ArtistProfileDTO() {}

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }
    public Boolean getApproved() { return approved; }
    public void setApproved(Boolean approved) { this.approved = approved; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public List<String> getArtistGenres() { return artistGenres; }
    public void setArtistGenres(List<String> artistGenres) { this.artistGenres = artistGenres; }
    public List<String> getArtistSocials() { return artistSocials; }
    public void setArtistSocials(List<String> artistSocials) { this.artistSocials = artistSocials; }
}
