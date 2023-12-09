package dev.ivanmol.taskmanager.controller;

import dev.ivanmol.taskmanager.dto.comment.CommentDto;
import dev.ivanmol.taskmanager.dto.comment.CommentShortDto;
import dev.ivanmol.taskmanager.dto.comment.NewCommentDto;
import dev.ivanmol.taskmanager.dto.comment.UpdateCommentDto;
import dev.ivanmol.taskmanager.model.user.User;
import dev.ivanmol.taskmanager.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/comments")
@RequiredArgsConstructor
@Tag(name = "Comment controller", description = "Controller for managing comments")
@Validated
public class CommentController {
    private final CommentService commentService;

    @Operation(
            summary = "Create comment by user",
            description = "Allows user to create a comment"
    )
    @PostMapping("/task/{taskId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto createComment(@Parameter(description = "DTO for new comment")
                                    @RequestBody @Valid NewCommentDto newCommentDto,
                                    @Parameter(description = "Task id")
                                    @PathVariable Long taskId,
                                    @AuthenticationPrincipal User user) {
        log.info("POST /users/userId/comments: {}, userId = {}, eventIdId = {}", newCommentDto, user.getId(), taskId);
        return commentService.createComment(newCommentDto, user.getId(), taskId);
    }

    @Operation(
            summary = "Get all comments for task",
            description = "Allows to get comments for task with pagination"
    )
    @GetMapping("/task/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentShortDto> getAllForTask(@Parameter(description = "Task Id")
                                               @PathVariable Long taskId,
                                               @Parameter(description = "From", required = false)
                                               @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                               @Parameter(description = "Size", required = false)
                                               @Positive @RequestParam(defaultValue = "10") Integer size) {
        return commentService.getAllForTask(taskId, from, size);
    }

    @Operation(
            summary = "Get all comments by author",
            description = "Allows an author to get all comments with pagination"
    )
    @GetMapping("/my")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentShortDto> getAllByAuthor(@Parameter(description = "From", required = false)
                                                @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                @Parameter(description = "Size", required = false)
                                                @Positive @RequestParam(defaultValue = "10") Integer size,
                                                @AuthenticationPrincipal User user) {
        return commentService.getAllByAuthor(user.getId(), from, size);
    }

    @Operation(
            summary = "Get comment by author",
            description = "Allows an author to create a comment"
    )
    @GetMapping("/my/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto getCommentByAuthor(@Parameter(description = "Comment id")
                                         @PathVariable Long commentId,
                                         @AuthenticationPrincipal User user) {
        return commentService.getCommentByAuthor(user.getId(), commentId);
    }

    @Operation(
            summary = "Update comment by author",
            description = "Allows an author to update a comment"
    )
    @PatchMapping("/my/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto updateCommentByAuthor(@Parameter(description = "DTO for update comment entity")
                                            @RequestBody @Valid UpdateCommentDto updateCommentDto,
                                            @Parameter(description = "Comment Id")
                                            @PathVariable Long commentId,
                                            @AuthenticationPrincipal User user) {
        return commentService.updateCommentByAuthor(updateCommentDto, user.getId(), commentId);
    }

    @Operation(
            summary = "Delete comment by author",
            description = "Allows an author to delete a comment"
    )
    @DeleteMapping("/my/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommentByAuthor(@Parameter(description = "Comment Id")
                                      @PathVariable Long commentId,
                                      @AuthenticationPrincipal User user) {
        commentService.deleteCommentByAuthor(user.getId(), commentId);
    }
}
