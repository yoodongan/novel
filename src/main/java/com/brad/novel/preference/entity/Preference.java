package com.brad.novel.preference.entity;

import com.brad.novel.base.BaseEntity;
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
public class Preference extends BaseEntity {
    private int recentCh;  // 가장 마지막으로 읽은 화
    private int like;      // 좋아요(선호) 개수
    private int lashCh;   // 마지막화
}
