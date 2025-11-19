package com.example.demo.modules.investment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
@Getter
@Setter
@ToString
public class DailyTranctionStockData {

    //上市個股日成交資訊

//	{
//		  "Code": "string", //證券代號
//		  "Name": "string", //證券名稱
//		  "TradeVolume": "string", //成交股數
//		  "TradeValue": "string", //成交金額
//		  "OpeningPrice": "string", //開盤價
//		  "HighestPrice": "string", //最高價
//		  "LowestPrice": "string", //最低價
//		  "ClosingPrice": "string", //收盤價
//		  "Change": "string", //漲跌價差
//		  "Transaction": "string" //成交筆數
//		}
    private Integer id;

    private Timestamp create_time;

    @JsonProperty(value="Date")
    private String date;

    @JsonProperty(value="Code")
    private String code;

    @JsonProperty(value="Name")
    private String name;

    @JsonProperty(value="TradeVolume")
    private Integer trade_volume;

    @JsonProperty(value="TradeValue")
    private BigInteger trade_value;

    @JsonProperty(value="OpeningPrice")
    private BigDecimal opening_price;

    @JsonProperty(value="HighestPrice")
    private BigDecimal highest_price;

    @JsonProperty(value="LowestPrice")
    private BigDecimal lowest_price;

    @JsonProperty(value="ClosingPrice")
    private BigDecimal closing_price;

    @JsonProperty(value="Change")
    private BigDecimal change_gap;

    @JsonProperty(value="Transaction")
    private Integer transaction_count;

    public DailyTranctionStockData() {

    }

    public DailyTranctionStockData(String date , String code, String name, Integer trade_volume, BigInteger trade_value,
                                   BigDecimal opening_price, BigDecimal highest_price, BigDecimal lowest_price, BigDecimal closing_price,
                                   BigDecimal change_gap, Integer transaction_count) {
        super();
        this.date = date;
        this.code = code;
        this.name = name;
        this.trade_volume = trade_volume;
        this.trade_value = trade_value;
        this.opening_price = opening_price;
        this.highest_price = highest_price;
        this.lowest_price = lowest_price;
        this.closing_price = closing_price;
        this.change_gap = change_gap;
        this.transaction_count = transaction_count;
    }


}
