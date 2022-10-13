package com.sparta.week04.dto;

import com.sparta.week04.entity.Memo;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemoResponseDto {
    private String title;
    private String contents;

    private Long id;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public MemoResponseDto (Memo memo){
        this.title = memo.getTitle();
        this.contents = memo.getContents();

        this.id = memo.getId();
        this.author = memo.getUser().getNickname();
        this.createdAt = memo.getCreatedAt();
        this.modifiedAt = memo.getModifiedAt();
    }


}
