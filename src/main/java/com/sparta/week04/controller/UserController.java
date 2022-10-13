package com.sparta.week04.controller;

import com.sparta.week04.dto.LoginRequestDto;
import com.sparta.week04.dto.SignupRequestDto;
import com.sparta.week04.dto.UserResponseDto;
import com.sparta.week04.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    // 회원가입
    @PostMapping("/api/user/signup")
    public UserResponseDto signup(@RequestBody @Valid SignupRequestDto signupRequestDto) throws IllegalAccessException {
        return userService.registerUser(signupRequestDto);
    }

    // 로그인
    @PostMapping("/api/user/login")
    public UserResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }
}
