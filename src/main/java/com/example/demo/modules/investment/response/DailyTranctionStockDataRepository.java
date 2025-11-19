package com.example.demo.modules.investment.response;

import com.example.demo.modules.investment.model.DailyTranctionStockData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DailyTranctionStockDataRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public int[] batchAdd(DailyTranctionStockData[] dailyTranctionStockData_array) {
        List<DailyTranctionStockData> dailyTranctionStockData_list = new ArrayList<DailyTranctionStockData>();
        for(DailyTranctionStockData dailyTranctionStockData:dailyTranctionStockData_array) {
            dailyTranctionStockData_list.add(dailyTranctionStockData);
        }
        String sql = " INSERT INTO daily_tranction_stock_data ( "
                + " CODE, NAME, TRADE_VOLUME, TRADE_VALUE, OPENING_PRICE, HIGHEST_PRICE,"
                + "LOWEST_PRICE, CLOSING_PRICE, CHANGE_GAP, TRANSACTION_COUNT )"
                + " VALUES ( :code, :name, :trade_volume, :trade_value, :opening_price,"
                + ":highest_price, :change_gap, :transaction_count,"
                + ":lowest_price, :closing_price ) " ;
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(dailyTranctionStockData_list.toArray());
        int[] updateCounts = namedParameterJdbcTemplate.batchUpdate(sql, batch);
        return updateCounts;

    }
}
