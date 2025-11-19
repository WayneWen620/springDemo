package com.example.demo.modules.investment.controller;

import com.example.demo.common.api.APIReturnObject;
import com.example.demo.modules.investment.model.DailyTranctionStockData;
import com.example.demo.modules.investment.service.TWSIOpenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/investment/stocks")
@RequiredArgsConstructor
public class StockController {

    private final TWSIOpenService tWSIOpenService;

//    public StockController(StockService stockService) {
//        this.stockService = stockService;
//    }

    @GetMapping("/all")
    public APIReturnObject getStockDayAll() {
        APIReturnObject aPIReturnObject = new APIReturnObject();
        Map<String, Object> data = new HashMap<String, Object>();
        DailyTranctionStockData[] dailyTranctionStockDatas = tWSIOpenService.getDailyTranctionStockData();
        aPIReturnObject.setMessage("上市個股日成交資訊-取得成功");
        data.put("dailyTranctionStockDatas", dailyTranctionStockDatas);
        aPIReturnObject.setData(data);
        return aPIReturnObject;

    }


    @PostMapping("/insert")
    public String insertStock() {
        return "insert Stock";
        //        return stockService.getStockBySymbol(symbol);
    }
}
