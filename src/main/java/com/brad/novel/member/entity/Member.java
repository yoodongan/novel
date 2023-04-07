package com.brad.novel.member.entity;

import com.brad.novel.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@SuperBuilder
public class Member extends BaseEntity {
    @Column(unique = true)
    private String name;
    @JsonIgnore
    private String password;
    private Long restPoint;

    @Column(unique = true)
    private String nickname;

    public List<GrantedAuthority> genAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));

        if(StringUtils.hasText(nickname)) {   // 작가명(nickname) 이 있는 경우에만 권한 부여.
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_AUTHOR"));
        }
        return grantedAuthorities;
    }

}
