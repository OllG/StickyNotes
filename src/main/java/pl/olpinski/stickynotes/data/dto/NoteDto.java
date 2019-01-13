package pl.olpinski.stickynotes.data.dto;

import java.time.LocalDateTime;

public class NoteDto {

    private Long id;
    private Long userId;
    private Long perUserId;
    private String title;
    private String content;
    private LocalDateTime creationTime;
    private LocalDateTime lastEditionTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPerUserId() {
        return perUserId;
    }

    public void setPerUserId(Long perUserId) {
        this.perUserId = perUserId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public LocalDateTime getLastEditionTime() {
        return lastEditionTime;
    }

    public void setLastEditionTime(LocalDateTime lastEditionTime) {
        this.lastEditionTime = lastEditionTime;
    }
}
