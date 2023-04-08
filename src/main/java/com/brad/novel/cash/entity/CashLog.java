package com.brad.novel.cash.entity;

import com.brad.novel.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class CashLog extends BaseEntity {
    private int price;
    private Enum eventType;
}
