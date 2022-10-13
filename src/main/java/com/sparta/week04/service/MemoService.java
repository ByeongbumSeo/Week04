package com.sparta.week04.service;

import com.sparta.week04.dto.MemoRequestDto;
import com.sparta.week04.dto.MemoResponseDto;
import com.sparta.week04.entity.Memo;
import com.sparta.week04.entity.User;
import com.sparta.week04.repository.CommentRepository;
import com.sparta.week04.repository.MemoRepository;
import com.sparta.week04.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MemoService {
    private final MemoRepository memoRepository;
    private final UserRepository userRepository;

    private final CommentRepository commentRepository;

    private User getUser(String username) {
        User user = userRepository.findByNickname(username)
                .orElseThrow( () -> new UsernameNotFoundException("유저를 찾을 수 없습니다"));
        return user;
    }
    @Transactional    // 글 전체보기
    public List<Memo> showAllMemo() {
        return memoRepository.findAllByOrderByCreatedAtDesc();
    }
//    public List<PostListResponseDto> getPostAll() {
//        List<Post> list = postRepository.findAllByOrderByModifiedAtDesc();
//
//        List<PostListResponseDto> plist = new ArrayList<>();
//
//        for(Post post : list) {
//            PostListResponseDto postListResponseDto = PostListResponseDto.builder()
//                    .id(post.getId())
//                    .title(post.getTitle())
//                    .author(post.getUser().getUsername())
//                    .createAt(post.getCreateAt())
//                    .modifiedAt(post.getModifiedAt())
//                    .build();
//            plist.add(postListResponseDto);
//        }
//        return plist;
//    }

//    @Transactional
//    public Memo saveMemo(MemoRequestDto memoRequestDto) {
//        Memo memo = new Memo(memoRequestDto);
//        return memoRepository.save(memo);
//    }
    @Transactional    // 글작성
    public MemoResponseDto createMemo(MemoRequestDto memoRequestDto, String username) {
        User user = getUser(username);

        Memo memo = new Memo(memoRequestDto, user);
        memoRepository.save(memo);

        return new MemoResponseDto(memo);
    }
    @Transactional    // 글 상세보기
    public MemoResponseDto getDetailMemo(Long id) {
        Memo memo = memoRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("해당 글이 존재하지 않습니다"));
        return new MemoResponseDto(memo);
    }

    // find post by id
//    @Transactional
//    public Memo getMemo(Long id) {
//        return memoRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("Post ID does not exist")
//        );
//    }


    // check if password match --> Login attempt
//    @Transactional
//    public Boolean loginAttempt(Long id, PasswordRequestDto requestDto) {
//        Memo memo = memoRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("Post ID does not exist")
//        );
//        return memo.getPassword().equals(requestDto.getPassword());
//    }

    // update post
//    @Transactional
//    public Long update(Long id, MemoRequestDto requestDto) {
//        Memo memo = memoRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("Post ID does not exist")
//        );
//        memo.update(requestDto);
//        return memo.getId();
//    }
    @Transactional// 글 수정
    public MemoResponseDto updateMemo(Long id, MemoRequestDto memoRequestDto, String username) {
        User user = getUser(username);
        Memo memo = memoRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException ("해당 글이 존재하지 않습니다"));

        if (!user.getNickname().equals(memo.getUser().getNickname())) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다");
        }

        memo.update(memoRequestDto);
        memoRepository.save(memo);
        return new MemoResponseDto(memo);
    }


    // delete post
//    @Transactional
//    public Long deleteMemo(Long id) {
//        memoRepository.deleteById(id);
//        return id;
//    }
    @Transactional
    public String deleteMemo(Long id, String username) {
        User user = getUser(username);
        Memo memo = memoRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException ("해당 글이 존재하지 않습니다"));
        if (!user.getNickname().equals(memo.getUser().getNickname())) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다");
        }
        memoRepository.deleteById(id);
//        List<Comment> list = commentRepository.findAllByPostId(id);
//        for(Comment comment : list) {
//            commentRepository.deleteById(comment.getId());
//        }
        return "글이 삭제되었습니다";
    }
//    public List<MemoResponseDto> getMemoAll() {
//        List<Memo> memos = memoRepository.findAllByOrderByCreatedAtDesc();
//        List<MemoResponseDto> rMemo = new ArrayList<>();
//        for(Memo memo : memos ){
//            MemoResponseDto rMemos = MemoResponseDto.builder()
//                    .id(memo.getId())
//                    .title(memo.getTitle())
//                    .author(memo.getUser().getNickname())
//                    .createdAt(memo.getCreatedAt())
//                    .modifiedAt(memo.getModifiedAt())
//                    .build();
//            rMemo.add(rMemos);
//        }
//        return rMemo;
//    }
}
