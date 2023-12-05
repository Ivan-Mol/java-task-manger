package dev.ivanmol.taskmanager.repository;

import dev.ivanmol.taskmanager.exception.NotFoundException;
import dev.ivanmol.taskmanager.model.task.Task;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    default Task getByIdAndCheck(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException("Task with id: " + id + " is not found"));
    }

    List<Task> getAllByIdInOrderByIdDesc(List<Long> taskIds, Pageable pageable);

    void deleteById(Long id);



}
