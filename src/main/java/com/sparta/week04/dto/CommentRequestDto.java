package com.sparta.week04.dto;

import com.sparta.week04.entity.User;
import lombok.Getter;

@Getter
public class CommentRequestDto {
    private Long postId;
    private String comment;
    private User user;
}
