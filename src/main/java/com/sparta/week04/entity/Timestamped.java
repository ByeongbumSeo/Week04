package com.sparta.week04.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter // private으로 선언 되었으므로 반드시 Getter를 해줘야함, 아니면 작동이 안됨
@MappedSuperclass // Entity가 자동으로 컬럼으로 인식합니다.
// Timestamped class를 상속한 것이 자동으로 생성시간, 수정시간을 컬럼으로 잡도록 도와줌
@EntityListeners(AuditingEntityListener.class) // 생성/변경 시간을 자동으로 업데이트합니다.
public abstract class Timestamped { // abstract는 상속이 되어야지만 사용할 수 있는 클래스라는 것을 의미

    @CreatedDate// 생성일자임을 나타냄
    private LocalDateTime createdAt; // LocalDateTime은 시간을 나타내는 자료형

    @LastModifiedDate// 마지막 수정일자임을 나타냄
    private LocalDateTime modifiedAt;
}
