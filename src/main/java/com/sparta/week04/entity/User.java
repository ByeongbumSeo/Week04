package com.sparta.week04.entity;

import com.sparta.week04.dto.LoginRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Setter
@Getter
@NoArgsConstructor
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;


    public User(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }
    public User(LoginRequestDto loginRequestDto) {
        this.nickname = loginRequestDto.getNickname();
        this.password = loginRequestDto.getPassword();
    }

//    @OneToMany(mappedBy = "user")
//    List<Board> board = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user")
//    List<Comment> comment = new ArrayList<>();
}
