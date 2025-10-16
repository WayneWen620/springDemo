package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 股利紀錄表
 * 紀錄該股票何時配息、每股配多少。
 */
@Entity
@Table(name="dividend")
@Data
public class Dividend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    @Column(name = "dividend_date", nullable = false)
    private LocalDate dividendDate;

    @Column(name = "amount_per_share", nullable = false, precision = 10, scale = 4)
    private BigDecimal amountPerShare; // 每股股利（原幣別）
}
