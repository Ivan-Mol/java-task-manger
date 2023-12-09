package dev.ivanmol.taskmanager.repository;

import dev.ivanmol.taskmanager.exception.NotFoundException;
import dev.ivanmol.taskmanager.model.user.User;
import jakarta.validation.ValidationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    default User getByIdAndCheck(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException("User with id: " + id + " is not found"));
    }

    List<User> getAllByIdInOrderByIdDesc(List<Long> userIds, Pageable pageable);

    default User saveUnique(User user) {
        getByUsername(user.getUsername()).orElseThrow(() -> new ValidationException("Email already exists"));
        return save(user);
    }

    Optional<User> getByUsername(String username);
}