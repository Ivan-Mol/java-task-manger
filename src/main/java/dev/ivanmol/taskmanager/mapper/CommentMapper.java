package dev.ivanmol.taskmanager.mapper;

import dev.ivanmol.taskmanager.dto.comment.CommentDto;
import dev.ivanmol.taskmanager.dto.comment.CommentShortDto;
import dev.ivanmol.taskmanager.dto.comment.NewCommentDto;
import dev.ivanmol.taskmanager.dto.comment.UpdateCommentDto;
import dev.ivanmol.taskmanager.model.comment.Comment;
import dev.ivanmol.taskmanager.model.task.Task;
import dev.ivanmol.taskmanager.model.user.User;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class CommentMapper {
    public static CommentDto commentToCommentResponseDto(Comment comment) {
        CommentDto commentResponseDto = new CommentDto();
        commentResponseDto.setId(comment.getId());
        commentResponseDto.setAuthorId(comment.getAuthor().getId());
        commentResponseDto.setText(comment.getText());
        commentResponseDto.setCreatedOn(comment.getCreatedOn());
        commentResponseDto.setTaskId(comment.getTask().getId());
        return commentResponseDto;
    }

    public static Comment toComment(NewCommentDto newCommentDto, User author, Task task) {
        Comment comment = new Comment();
        comment.setText(newCommentDto.getText());
        comment.setAuthor(author);
        comment.setTask(task);
        comment.setCreatedOn(LocalDateTime.now());
        return comment;
    }

    public static Comment updatedCommentDtoToComment(UpdateCommentDto updateCommentDto, Comment oldComment) {
        Comment comment = new Comment();
        comment.setId(oldComment.getId());
        comment.setAuthor(oldComment.getAuthor());
        comment.setTask(oldComment.getTask());
        if (updateCommentDto.getText() != null) {
            comment.setText(updateCommentDto.getText());
        } else {
            comment.setText(oldComment.getText());
        }
        comment.setCreatedOn(LocalDateTime.now());
        return comment;
    }

    public static CommentShortDto toCommentShortDto(Comment comment) {
        CommentShortDto commentShortDto = new CommentShortDto();
        commentShortDto.setAuthorId(comment.getAuthor().getId());
        commentShortDto.setText(comment.getText());
        commentShortDto.setTaskId(comment.getTask().getId());
        commentShortDto.setCreatedOn(comment.getCreatedOn());
        return commentShortDto;
    }
}
