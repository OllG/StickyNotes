package pl.olpinski.stickynotes.data.dto;

public class NoteCreationDto {

    private Long userId;
    private String title;
    private String content;

    public NoteCreationDto(Long userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
