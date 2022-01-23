package com.example.stock.services;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author anar
 *
 */

@Service
public class StockService {
    @Autowired
    private StockRepository repository;

    public void add(Stock stock){
        repository.save(stock);
    }

    public List<Stock> allStock(){
        return repository.findAll();
    }

    public Stock getStock(String symbol ){
        return  repository.findBySymbol(symbol);
    }
    
     public List<Stock> getAllStock(){
        return  repository.findAll();
    }

}
