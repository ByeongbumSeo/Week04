package com.sparta.week04.dto;

import lombok.Getter;
import lombok.Setter;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Setter
@Getter
public class SignupRequestDto {

    @NotBlank //null, 빈칸, 띄워쓰기 불가
    @Pattern(regexp = "[a-zA-Z0-9]{4,12}", message = "닉네임양식을 확인해주세요!")
    private String nickname;
    //최소 4자 이상, 12자 이하 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성
    @NotBlank
    @Pattern(regexp = "[a-z0-9]{4,32}", message = "비밀번호양식을 확인해주세요!")
    private String password;
    //최소 4자 이상이며, 32자 이하 알파벳 소문자(a~z), 숫자(0~9) 로 구성
    @NotBlank
    private String passwordConfirm;

}
