package com.sparta.week04.service;

import com.sparta.week04.dto.CommentDto;
import com.sparta.week04.dto.CommentRequestDto;
import com.sparta.week04.dto.CommentResponseDto;
import com.sparta.week04.entity.Comment;
import com.sparta.week04.entity.Memo;
import com.sparta.week04.entity.User;
import com.sparta.week04.repository.CommentRepository;
import com.sparta.week04.repository.MemoRepository;
import com.sparta.week04.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final MemoRepository memoRepository;

    private User getUser(String username) {
        User user = userRepository.findByNickname(username)
                .orElseThrow( () -> new UsernameNotFoundException("유저를 찾을 수 없습니다"));
        return user;
    }
    public void memoCheck(CommentRequestDto commentRequestDto) {
        memoRepository.findById( commentRequestDto.getPostId() )
                .orElseThrow( () -> new IllegalArgumentException("해당 글이 존재하지 않습니다"));
    }
//    @Transactional
//    public CommentResponseDto createComment(Long id, CommentDto commentDto, String nickname ) {
//        // 댓글 작성자 정보
//        User user = userRepository.findByNickname(nickname).orElseThrow(
//                () -> new IllegalArgumentException("회원가입이 필요합니다")
//        );
//        //메모 타이틀
//        Memo memo = memoRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("회원가입이 필요합니다")
//        );
//
//        Comment comment = new Comment(memo, user, commentDto);
//
//        commentRepository.save(comment);
//
//        return new CommentResponseDto(comment);
//    }
    @Transactional
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, String username) {
        User user = getUser(username);
        memoCheck(commentRequestDto);

        Comment comment = new Comment(commentRequestDto, user);
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

//    @Transactional
//    public String updateComment(Long id, CommentDto commentDto, String nickname) {
//        User user = userRepository.findByNickname(nickname).orElseThrow(
//                () -> new IllegalArgumentException("회원가입이 필요합니다")
//        );
//        Comment comment = commentRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("댓글이 존재하지 않습니다")
//        );
//        if(!user.getNickname().equals(comment.getNickname()))
//            return "작성자만 수정할 수 있습니다.";
//        comment.commentUpdate(commentDto);
//        return "댓글이 수정되었습니다";
//    }

//댓글 수정
    @Transactional//댓글 수정
    public CommentResponseDto updateComment(Long id, CommentRequestDto commentRequestDto, String username) {
        User user = getUser(username);
        memoCheck(commentRequestDto);

        Comment comment = commentRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("찾으시는 댓글이 없습니다"));

        if(!user.getNickname().equals(comment.getUser().getNickname())){
            throw new IllegalArgumentException("댓글 작성자가 다릅니다");
        }

        comment.commentupdate(commentRequestDto);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }



    @Transactional //댓글 삭제
    public String deleteComment(Long id, String username) {
        User user = getUser(username);

        Comment comment = commentRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("찾으시는 댓글이 없습니다"));

        if(!user.getNickname().equals(comment.getUser().getNickname())){
            throw new IllegalArgumentException("댓글 작성자가 다릅니다");
        }

        commentRepository.deleteById(id);
        return "댓글이 삭제되었습니다";
    }

    //댓글 전체목록 보기
    public List<CommentResponseDto> getCommentAllOfMemo(Long id) {
        List<Comment> list = commentRepository.findAllByPostId(id);
        List<CommentResponseDto> clist = new ArrayList<>();
        for (Comment c : list) {
            //CommentResponseDto commentResponseDto = new CommentResponseDto(c);
            clist.add(new CommentResponseDto(c));
        }
        return clist;
    }
}
