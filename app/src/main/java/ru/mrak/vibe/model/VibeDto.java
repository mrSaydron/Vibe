package ru.mrak.vibe.model;

import java.time.Instant;
import java.time.LocalDateTime;

public class VibeDto {
    public String title;
    public String description;
    public Instant createDate;
    public Instant voteEndDate;
    public Instant vibeEndDate;
    public String userCreateId;
    public String userCreateName;

    public VibeDto() {
    }

    public VibeDto(String title, String description, Instant createDate, Instant voteEndDate, Instant vibeEndDate, String userCreateId, String userCreateName) {
        this.title = title;
        this.description = description;
        this.createDate = createDate;
        this.voteEndDate = voteEndDate;
        this.vibeEndDate = vibeEndDate;
        this.userCreateId = userCreateId;
        this.userCreateName = userCreateName;
    }
}
