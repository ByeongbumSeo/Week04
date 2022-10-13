package com.sparta.week04.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.week04.dto.MemoRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor // 기본생성자를 만듭니다.
@Getter
@Entity // 테이블과 연계됨을 스프링에게 알려줍니다.
public class Memo extends Timestamped { // 상속되었기 때문에 생성,수정 시간이 자동으로 만들어진다.
    @GeneratedValue(strategy = GenerationType.AUTO) // 자동 증가 명령입니다., 아이디값 하나씩 증가
    @Id // ID 값, Primary Key로 사용하겠다는 뜻입니다.
    private Long id;
    //postId라는 멤버변수는 @Id와 같이 primary key로 사용한다고 하는 것을 스프링에게 알려주어서,
    //유일한 값으로 객체들이 구분될 수 있도록 하는 역할

    @Column(nullable = false) //@Column : 이 테이블의 컬럼 값이고, nullable = false: 반드시 값이 존재해야 함을 나타냅니다.
    private String title;
    @Column(nullable = false)
    private String contents;
    @Column(nullable = false)
    private String author;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="postId")
    @OrderBy("id asc")
    private List<Comment> commentList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Memo(MemoRequestDto requestDto, User user) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.author = user.getNickname();
    }

    public void update(MemoRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }
//    public void check(MemoCheckPwDto checkRequestDto){
//        this.password = checkRequestDto.getPassword();
//    }
}
