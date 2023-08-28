package com.brad.novel.member.entity;

import com.brad.novel.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@SuperBuilder
public class Member extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String username;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String profileImage;

    @Column(nullable = false)
    private Integer restPoint;

    @Column(nullable = false)
    private Integer restTicket;

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

    public void addNickname(String nickname) {
        this.nickname = nickname;
    }

    public void addPoint(Integer addPoint) {
        restPoint += addPoint;
    }

    public void usePoint(Integer usePoint) {
        restPoint -= usePoint;
    }

    public Map<String, Object> toClaims() {
        return Map.of(
                "id", getId(),
                "username", getUsername()
        );
    }

    public void useTicket(Integer ticketPrice) {
        this.restTicket -= ticketPrice;
    }

    public void addTicket(Integer amount) {
        this.restTicket += amount;
    }
}
