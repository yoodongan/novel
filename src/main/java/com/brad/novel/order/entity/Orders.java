package com.brad.novel.order.entity;

import com.brad.novel.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Orders extends BaseEntity {
    private LocalDateTime payDate;
    private LocalDateTime isPaid;
    private LocalDateTime isRefund;
}
