package it.unisalento.music_virus_project.event_fundraising_service.dto.admin;

public class GenericCounterDTO {
    private String type;
    private Integer count;

    public GenericCounterDTO() {
    }

    public GenericCounterDTO(String type, Integer count) {
        this.type = type;
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
