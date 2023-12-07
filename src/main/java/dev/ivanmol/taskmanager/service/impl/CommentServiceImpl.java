package dev.ivanmol.taskmanager.service.impl;

import dev.ivanmol.taskmanager.dto.comment.CommentDto;
import dev.ivanmol.taskmanager.dto.comment.CommentShortDto;
import dev.ivanmol.taskmanager.dto.comment.NewCommentDto;
import dev.ivanmol.taskmanager.dto.comment.UpdateCommentDto;
import dev.ivanmol.taskmanager.mapper.CommentMapper;
import dev.ivanmol.taskmanager.model.comment.Comment;
import dev.ivanmol.taskmanager.model.task.Task;
import dev.ivanmol.taskmanager.model.user.User;
import dev.ivanmol.taskmanager.repository.CommentRepository;
import dev.ivanmol.taskmanager.repository.TaskRepository;
import dev.ivanmol.taskmanager.repository.UserRepository;
import dev.ivanmol.taskmanager.service.CommentService;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Override
    public CommentDto createComment(NewCommentDto newCommentDto, Long userId, Long taskId) {
        User author = userRepository.getByIdAndCheck(userId);
        Task task = taskRepository.getByIdAndCheck(taskId);
        isAuthorAlreadyHaveThisComment(author, newCommentDto, task.getId());
        Comment comment = CommentMapper.toComment(newCommentDto, author, task);
        return CommentMapper.commentToCommentResponseDto(commentRepository.save(comment));
    }

    @Override
    public CommentDto getCommentByAuthor(Long userId, Long commentId) {
        userRepository.getByIdAndCheck(userId);
        return CommentMapper.commentToCommentResponseDto(commentRepository.getByIdAndCheck(commentId));
    }

    @Override
    public CommentDto updateCommentByAuthor(UpdateCommentDto updateCommentDto, Long userId, Long commentId) {
        User user = userRepository.getByIdAndCheck(userId);
        Comment oldComment = commentRepository.getByIdAndCheck(commentId);
        isUserAuthorOfComment(user, oldComment);
        Comment updatedComment = CommentMapper.updatedCommentDtoToComment(updateCommentDto, oldComment);
        return CommentMapper.commentToCommentResponseDto(commentRepository.save(updatedComment));
    }

    @Override
    public void deleteCommentByAuthor(Long userId, Long commentId) {
        User user = userRepository.getByIdAndCheck(userId);
        Comment comment = commentRepository.getByIdAndCheck(commentId);
        isUserAuthorOfComment(user, comment);
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentShortDto> getAllByAuthor(Long authorId, Integer from, Integer size) {
        userRepository.getByIdAndCheck(authorId);
        Pageable pageable = PageRequest.of(from / size, size);
        return commentRepository
                .getAllByAuthorId(authorId, pageable)
                .stream()
                .map(CommentMapper::toCommentShortDto)
                .toList();
    }

    @Override
    public List<CommentShortDto> getAllForTask(Long taskId, Integer from, Integer size) {
        taskRepository.getByIdAndCheck(taskId);
        Pageable pageable = PageRequest.of(from / size, size);
        return commentRepository
                .getAllByTaskId(taskId, pageable)
                .stream()
                .map(CommentMapper::toCommentShortDto)
                .toList();
    }

    public void isAuthorAlreadyHaveThisComment(User user, NewCommentDto newCommentDto, Long taskId) {
        if (commentRepository.getByTextAndAuthorIdAndTaskId(newCommentDto.getText(), user.getId(), taskId) != null) {
            throw new ValidationException("User can not to create the same comment two times for one task");
        }
    }

    public void isUserAuthorOfComment(User user, Comment comment) {
        if (!comment.getAuthor().equals(user)) {
            throw new ValidationException("User is not comment author");
        }
    }
}
