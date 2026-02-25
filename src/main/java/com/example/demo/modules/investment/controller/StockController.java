package com.example.demo.modules.investment.controller;

import com.example.demo.common.api.APIReturnObject;
import com.example.demo.modules.investment.usecase.StockDailyTransactionDataListUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/investment/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockDailyTransactionDataListUseCase stockDailyTransactionDataListUseCase;

    @GetMapping("/all")
    public APIReturnObject getStockDayAll() {
        return stockDailyTransactionDataListUseCase.execute();
    }


    @PostMapping("/insert")
    public String insertStock() {
        return "insert Stock";
        //        return stockService.getStockBySymbol(symbol);
    }
}
