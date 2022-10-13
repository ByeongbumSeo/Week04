package com.sparta.week04.controller;

import com.sparta.week04.dto.MemoRequestDto;
import com.sparta.week04.dto.MemoResponseDto;
import com.sparta.week04.entity.Memo;
import com.sparta.week04.repository.MemoRepository;
import com.sparta.week04.security.UserDetailsImpl;
import com.sparta.week04.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MemoController {
    private final MemoService memoService;

    // Get all the memos
    @GetMapping("/api/memos")
    public List<Memo> getAllMemo() {
        return memoService.showAllMemo();
    }

    // Create new memo
    @PostMapping("/api/auth/memos")
    public MemoResponseDto createPost(@RequestBody MemoRequestDto memoRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailImp) {
        return memoService.createMemo(memoRequestDto, userDetailImp.getUsername());
    }

    // Gets a memo
//    @GetMapping("/api/memos/{id}")
//    public Memo getMemo(@PathVariable Long id) {
//        return memoService.getMemo(id);
//    }

    @GetMapping("/api/memos/{id}")
    public MemoResponseDto getMemoDetail(@PathVariable Long id) {
        return memoService.getDetailMemo(id);
    }

//    // Login attempt
//    @PostMapping("/api/posts/{id}")
//    public Boolean loginAttempt(@PathVariable Long id, @RequestBody PasswordRequestDto requestDto) {
//        return postService.loginAttempt(id, requestDto);
//    }

    // Update memo
//    @PutMapping("/api/memos/{id}")
//    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
//        return memoService.update(id, requestDto);
//    }
    @PutMapping("/api/auth/memos/{id}")
    public MemoResponseDto updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto memoRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailImp) {
        return memoService.updateMemo(id, memoRequestDto, userDetailImp.getUsername());
    }

    // Delete memo
//    @DeleteMapping("/api/memos/{id}")
//    public Long deleteMemo(@PathVariable Long id) {
//        return memoService.deleteMemo(id);
//    }
//}
    @DeleteMapping("/api/auth/posts/{id}")
    public String deleteMemo(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetailImp) {
        return memoService.deleteMemo(id, userDetailImp.getUsername());
    }
}

