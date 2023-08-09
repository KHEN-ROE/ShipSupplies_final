package com.shipsupply.controller;

import com.shipsupply.dto.AddAndUpdateCommentDto;
import com.shipsupply.dto.CommentDto;
import com.shipsupply.dto.DeleteCommentDto;
import com.shipsupply.entity.Comment;
import com.shipsupply.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {


    private final CommentService commentService;

    @Operation(summary = "댓글 보기")
    @ApiResponse(responseCode = "200", description = "댓글이 정상적으로 조회되었습니다.")
    @GetMapping("/get/{boardId}")
    public List<CommentDto> getComment(@PathVariable Long boardId) {
        log.info("글번호 : " + boardId);
        return commentService.getComment(boardId);
    }

    @Operation(summary = "댓글 작성")
    @ApiResponse(responseCode = "200", description = "댓글이 정상적으로 작성되었습니다.")
    @PostMapping("/add")
    public void addComment(@RequestBody @Valid AddAndUpdateCommentDto addCommentDto) {
        commentService.addComment(addCommentDto);
    }

    @Operation(summary = "댓글 수정")
    @ApiResponse(responseCode = "200", description = "댓글이 정상적으로 수정되었습니다.")
    @PatchMapping("/update/{id}")
    public void updateComment(@PathVariable Long id, @RequestBody @Valid AddAndUpdateCommentDto updateCommentDto) {
        log.info("받은 댓글 정보 : " + id + "," + updateCommentDto);
        commentService.updateComment(id, updateCommentDto);
    }

    @Operation(summary = "댓글 삭제")
    @ApiResponse(responseCode = "200", description = "댓글이 정상적으로 삭제되었습니다.")
    @DeleteMapping("/delete/{id}")
    public void deleteComment(@PathVariable Long id, @RequestBody @Valid DeleteCommentDto deleteCommentDto) {
        log.info("받은 정보 : " + id + "," + deleteCommentDto);
        commentService.deleteComment(id, deleteCommentDto);
    }
}
