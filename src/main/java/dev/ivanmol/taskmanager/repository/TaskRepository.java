package dev.ivanmol.taskmanager.repository;

import dev.ivanmol.taskmanager.exception.NotFoundException;
import dev.ivanmol.taskmanager.model.task.Task;
import dev.ivanmol.taskmanager.model.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    default Task getByIdAndCheck(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException("Task with id: " + id + " is not found"));
    }

    List<Task> getAllByIdInOrderByIdDesc(List<Long> taskIds, Pageable pageable);

    void deleteById(Long id);

    List<Task> getByAuthor(User author);

    List<Task> getAllByAssignee(User assignee);
}
