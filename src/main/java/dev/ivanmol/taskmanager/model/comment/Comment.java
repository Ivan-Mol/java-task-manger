package dev.ivanmol.taskmanager.model.comment;

import dev.ivanmol.taskmanager.model.task.Task;
import dev.ivanmol.taskmanager.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@Table(name = "comments")
public class Comment {
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    User author;
    @ManyToOne
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    Task task;
    @Column(name = "text", nullable = false, length = 800)
    String text;
    @CreationTimestamp
    @Column(name = "created_on")
    LocalDateTime createdOn;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
}