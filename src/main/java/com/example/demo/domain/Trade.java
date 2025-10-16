package com.example.demo.domain;

import com.example.demo.enums.TradeType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 交易紀錄表
 * 紀錄每次買入或賣出的資訊。
 */
@Entity
@Table(name="trade")
@Data
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    @Column(name = "trade_date", nullable = false)
    private LocalDate tradeDate;

    @Column(name = "price", nullable = false)
    private BigDecimal price;  // 單股成交價（原幣別）

    @Column(name = "quantity", nullable = false)
    private Integer quantity; // 買入或賣出股數（賣出可為負數）

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TradeType type; // BUY or SELL

    @Column(name = "fee",precision = 12, scale = 4)
    private BigDecimal fee; // 交易手續費（原幣別）
}
