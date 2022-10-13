package com.sparta.week04.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginRequestDto {
    private String nickname;
    private String password;
}
