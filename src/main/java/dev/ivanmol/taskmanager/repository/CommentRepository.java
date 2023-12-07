package dev.ivanmol.taskmanager.repository;

import dev.ivanmol.taskmanager.exception.NotFoundException;
import dev.ivanmol.taskmanager.model.comment.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    default Comment getByIdAndCheck(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException("Comment with id: " + id + " is not found"));
    }

    List<Comment> getAllByAuthorId(Long authorId, Pageable pageable);

    List<Comment> getAllByTaskId(Long eventId, Pageable pageable);

    Comment getByTextAndAuthorIdAndTaskId(String text, Long authorId, Long taskId);
}
