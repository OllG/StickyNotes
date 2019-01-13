package pl.olpinski.stickynotes.data.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Note {

    private static Long lastId = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long perUserId;

    //to delete
    public Note() {
        perUserId = lastId++;
    }

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
    private String title;

    @Column(length = 10000)
    private String content;
    private LocalDateTime creationTime;
    private LocalDateTime lastEditionTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPerUserId() {
        return perUserId;
    }

    public void setPerUserId(Long perUserId) {
        this.perUserId = perUserId;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
