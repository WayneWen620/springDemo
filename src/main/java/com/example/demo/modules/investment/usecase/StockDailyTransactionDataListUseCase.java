package com.example.demo.modules.investment.usecase;

import com.example.demo.common.api.APIReturnObject;
import com.example.demo.modules.investment.model.DailyTranctionStockData;
import com.example.demo.modules.investment.service.TWSIOpenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StockDailyTransactionDataListUseCase {

    private final TWSIOpenService tWSIOpenService;

    public APIReturnObject execute() {
        APIReturnObject aPIReturnObject = new APIReturnObject();
        Map<String, Object> data = new HashMap<String, Object>();
        DailyTranctionStockData[] dailyTranctionStockDatas = tWSIOpenService.getDailyTranctionStockData();
        aPIReturnObject.setMessage("上市個股日成交資訊-取得成功");
        data.put("dailyTranctionStockDatas", dailyTranctionStockDatas);
        aPIReturnObject.setData(data);
        return aPIReturnObject;
    }
}
