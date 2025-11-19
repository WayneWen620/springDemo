package com.example.demo.modules.investment.service;

import com.example.demo.modules.investment.model.DailyTranctionStockData;
import com.example.demo.modules.investment.response.DailyTranctionStockDataRepository;
import com.example.demo.modules.investment.util.TWSIOpenAPIUrl;
import com.example.demo.modules.investment.util.TWSIOpenAPIUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TWSIOpenService {
    private final DailyTranctionStockDataRepository dailyTranctionStockDataRepository;

    public DailyTranctionStockData[] getDailyTranctionStockData(){
        DailyTranctionStockData[] resultList =
                TWSIOpenAPIUtil.send(
                        TWSIOpenAPIUrl.EXCHANGE_REPORT_STOCK_DAY_ALL.getUrl(),
                        TWSIOpenAPIUrl.EXCHANGE_REPORT_STOCK_DAY_ALL.getMethod(),
                        DailyTranctionStockData[].class);
        return	resultList;
    }
    @Transactional(rollbackFor = Exception.class)
    public void schedule_AddDailyTranctionStockData() {
        DailyTranctionStockData[] dailyTranctionStockData_array = getDailyTranctionStockData();
        dailyTranctionStockDataRepository.batchAdd(dailyTranctionStockData_array);
    }
}
