package dev.ivanmol.taskmanager.controller;

import dev.ivanmol.taskmanager.dto.comment.CommentDto;
import dev.ivanmol.taskmanager.dto.comment.CommentShortDto;
import dev.ivanmol.taskmanager.dto.comment.NewCommentDto;
import dev.ivanmol.taskmanager.dto.comment.UpdateCommentDto;
import dev.ivanmol.taskmanager.service.CommentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{taskId}/users/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto createComment(@RequestBody @Valid NewCommentDto newCommentDto,
                                    @PathVariable Long userId,
                                    @PathVariable Long taskId) {
        log.info("POST /users/userId/comments: {}, userId = {}, eventIdId = {}", newCommentDto, userId, taskId);
        return commentService.createComment(newCommentDto, userId, taskId);
    }

    @GetMapping("/{commentId}/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto getCommentByAuthor(@PathVariable Long userId,
                                         @PathVariable Long commentId) {
        return commentService.getCommentByAuthor(userId, commentId);
    }

    @PatchMapping("/{commentId}/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto updateCommentByAuthor(@RequestBody @Valid UpdateCommentDto updateCommentDto,
                                            @PathVariable Long userId,
                                            @PathVariable Long commentId) {
        return commentService.updateCommentByAuthor(updateCommentDto, userId, commentId);
    }

    @DeleteMapping("/{commentId}/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommentByAuthor(@PathVariable Long userId,
                                      @PathVariable Long commentId) {
        commentService.deleteCommentByAuthor(userId, commentId);
    }

    @GetMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentShortDto> getAllByAuthor(@PathVariable Long userId,
                                                @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                @Positive @RequestParam(defaultValue = "10") Integer size) {
        return commentService.getAllByAuthor(userId, from, size);
    }

    @GetMapping("/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentShortDto> getAllForTask(@PathVariable Long taskId,
                                               @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                               @Positive @RequestParam(defaultValue = "10") Integer size) {
        return commentService.getAllForTask(taskId, from, size);
    }
}
