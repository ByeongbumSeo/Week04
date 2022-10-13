package com.sparta.week04.entity;

import com.sparta.week04.dto.CommentDto;
import com.sparta.week04.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
public class Comment extends Timestamped {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    @Column(name = "postId")
    private Long postId;
//    @Column(nullable = false)
//    private String nickname;

    @Column(nullable = false)
    private String comment;
    @ManyToOne
    @JoinColumn (name = "user_id", nullable = false)
    private User user;  // 댓글 작성자

//    public Comment(Memo memo, User user, CommentDto commentDto) {
//        this.postId = memo.getId();
//        this.nickname = user.getNickname();
//        this.comment = commentDto.getComment();
//    }

    public Comment(CommentRequestDto commentRequestDto, User user) {
        this.postId = commentRequestDto.getPostId();
        this.comment = commentRequestDto.getComment();
        this.user = user;
    }

    public void commentupdate(CommentRequestDto commentRequestDto) {
        this.postId = commentRequestDto.getPostId();
        this.comment = commentRequestDto.getComment();
    }
}
