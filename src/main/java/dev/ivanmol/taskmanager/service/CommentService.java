package dev.ivanmol.taskmanager.service;

import dev.ivanmol.taskmanager.dto.comment.CommentDto;
import dev.ivanmol.taskmanager.dto.comment.CommentShortDto;
import dev.ivanmol.taskmanager.dto.comment.NewCommentDto;
import dev.ivanmol.taskmanager.dto.comment.UpdateCommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(NewCommentDto newCommentDto, Long userId, Long eventId);

    CommentDto getCommentByAuthor(Long userId, Long commentId);

    CommentDto updateCommentByAuthor(UpdateCommentDto updateCommentDto, Long userId, Long commentId);

    List<CommentShortDto> getAllByAuthor(Long authorId, Integer from, Integer size);

    List<CommentShortDto> getAllForTask(Long taskId, Integer from, Integer size);

    void deleteCommentByAuthor(Long userId, Long commentId);

}
