package com.example.demo.modules.investment.util;

import org.springframework.http.HttpMethod;

public enum  TWSIOpenAPIUrl {
    //證卷交易(exchangeReport)
    EXCHANGE_REPORT_STOCK_DAY_ALL(1,"exchange_report",HttpMethod.GET,"https://openapi.twse.com.tw/v1/exchangeReport/STOCK_DAY_ALL");
    private  Integer id;
    private String type;
    private HttpMethod method;
    private String url;

    private TWSIOpenAPIUrl(Integer id, String type, HttpMethod method, String url) {
        this.id = id;
        this.type = type;
        this.method =method;
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }
}
