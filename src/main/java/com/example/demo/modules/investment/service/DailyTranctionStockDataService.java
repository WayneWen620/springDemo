package com.example.demo.modules.investment.service;

import com.example.demo.modules.investment.model.DailyTranctionStockData;
import com.example.demo.modules.investment.response.DailyTranctionStockDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class DailyTranctionStockDataService {

    private final DailyTranctionStockDataRepository repository;

    public DailyTranctionStockDataService(DailyTranctionStockDataRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void saveDailyData(DailyTranctionStockData[] dailyTranctionStockData_array) {
        if (dailyTranctionStockData_array.length == 0) return;

        String tradeDate = dailyTranctionStockData_array[0].getDate();

        List<DailyTranctionStockData> dailyTranctionStockDataList = repository.findByDate(tradeDate);
        if(!dailyTranctionStockDataList.isEmpty()){
            return;
        }
        // 再批量插入
        List<DailyTranctionStockData> list = Arrays.asList(dailyTranctionStockData_array);
        repository.saveAll(list);
    }
}
