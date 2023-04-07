package com.brad.novel.member.entity;

import com.brad.novel.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Member extends BaseEntity {
    private String name;
    @JsonIgnore
    private String password;
    private Long restPoint;

    @Column(unique = true)
    private String nickname;

}
