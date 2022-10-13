package com.sparta.week04.controller;

import com.sparta.week04.dto.CommentDto;
import com.sparta.week04.dto.CommentRequestDto;
import com.sparta.week04.dto.CommentResponseDto;
import com.sparta.week04.entity.Comment;
import com.sparta.week04.security.UserDetailsImpl;
import com.sparta.week04.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    //댓글 쓰기
    @PostMapping("/api/auth/comment")
    public CommentResponseDto createComment(@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailImp) {
        return commentService.createComment(commentRequestDto, userDetailImp.getUsername());
    }

    //댓글 수정
    @PostMapping("/api/auth/comment/{id}")
    public CommentResponseDto updateComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailImp) throws IllegalAccessException {
        return commentService.updateComment(id, commentRequestDto, userDetailImp.getUsername());
    }

    //댓글 삭제
    @DeleteMapping("/api/auth/comment/{id}")
    public String deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetailImp) {
        return commentService.deleteComment(id, userDetailImp.getUsername());
    }


    //댓글 전체목록 보기
    @GetMapping("/api/comment/{id}")
    public List<CommentResponseDto> getCommentAllOfMemo(@PathVariable Long id) {
        return commentService.getCommentAllOfMemo(id);
    }
}
