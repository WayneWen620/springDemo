package com.example.demo.modules.investment.response;

import com.example.demo.modules.investment.model.DailyTranctionStockData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyTranctionStockDataRepository extends JpaRepository<DailyTranctionStockData, Integer> {
    List<DailyTranctionStockData> findByDate(String date);
    void deleteByDate(String date);
}
