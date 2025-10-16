package com.example.demo.domain;

import com.example.demo.enums.Market;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 股票主表
 */
@Entity
@Table(name="stock")
@Data
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "symbol", nullable = false) // 股票代號
    private String symbol;

    @Column(name = "name")
    private String name; // 股票名稱（選填）

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Market market; // 台股、美股

    @Column(length = 3)
    private String currency; // 幣別：TWD, USD, HKD

    @Column(precision = 12, scale = 4)
    private BigDecimal exchangeRate;
    // 匯率：1 美元 = 幾台幣，台股可預設 1.0

    @Column(name = "current_price")
    private BigDecimal currentPrice; // 目前市價 (以原幣別計）（可從外部 API 更新）

    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Trade> trades = new ArrayList<>();

    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dividend> dividends = new ArrayList<>();
}
}
