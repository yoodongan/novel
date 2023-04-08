package com.brad.security.dto;

import com.brad.novel.member.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class MemberContext extends User {
    private final Long id;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;
    private final String username;
    private final String nickname;

    public MemberContext(Member member, List<GrantedAuthority> authorities) {
        super(member.getName(), member.getPassword(), authorities);
        this.id = member.getId();
        this.createdDate = member.getCreatedDate();
        this.modifiedDate = member.getModifiedDate();
        this.username = member.getName();
        this.nickname = member.getNickname();
    }
    public boolean hasAuthority(String name) {
        return getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(name));
    }
}
