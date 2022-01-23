package com.example.stock.controller;

import com.example.stock.domain.Stock;
import com.example.stock.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;


/**
 *
 * @author anar
 *
 */
@Component
@RestController
@RequestMapping("/stocks")
public class StockController {

     Map<String, Stock> stocks = new HashMap<>();
     @Autowired
    private   StockService service;
    public StockController(StockService service){
        this.service = service;
        stocks.put("EU",new Stock( "AZERIQAZ","EU",new BigDecimal("4.567"),null,null,new Date()));
        stocks.put("AZ",new Stock( "AZERCEL","AZ",new BigDecimal("5.678"),null,null, new Date()));
        stocks.put("GB",new Stock( "YAHOO","GB",new BigDecimal("5.789"),null,null,new Date()));

        System.out.println("service= " +service);
        service.add(stocks.get("EU"));
        service.add(stocks.get("AZ"));
        service.add(stocks.get("GB"));
    }
    @PostConstruct
    void init(){

    }

    @GetMapping("/operate")
    @Scheduled(fixedRate = 5000)
    public void operation(){

        System.out.println(new Date());

        List<Stock> stocksList = service.allStock();
        Map<String, Stock > stocksMap = new HashMap<>();
        stocksList.forEach(it->{
            stocksMap.put(it.getSymbol(),it);
        });
        Object[] crunchifyKeys = stocksMap.keySet().toArray();
        Object key = crunchifyKeys[new Random().nextInt(crunchifyKeys.length)];
        int v = new Random().nextInt(10)+1;

        if(v>=5) v= -v;

        Stock stock =stocksMap.get(key);

        BigDecimal val= stock.getLastPrice();

        stock.setLastPrice(new BigDecimal(v).add(val));
        System.out.println("v"+v);
        System.out.println("val"+val);
        System.out.println("stock.getLastPrice().compareTo(new BigDecimal(0))="+stock.getLastPrice().compareTo(new BigDecimal(0)));
        if(stock.getLastPrice().compareTo(new BigDecimal(0))>0){

        stock.setChange(val);
        Float changePercent=null;
        BigDecimal firstPrice=   stock.getLastPrice().subtract(stock.getChange());

        System.out.println("stock.getLastPrice()"+stock.getLastPrice());
        System.out.println("stock.getChange()"+stock.getChange());
        System.out.println("firstPrice"+firstPrice);

        changePercent= stock.getChange().divide(firstPrice, MathContext.DECIMAL128).multiply(new BigDecimal(100)).floatValue();

        System.out.println("divide="+stock.getChange().divide(firstPrice, MathContext.DECIMAL128));
        System.out.println("changePercent="+changePercent);
        stock.setChangePercent(changePercent);

        service.add(stock);}
    }


    @GetMapping
    public ResponseEntity<?> getStock(@RequestParam String query ){
        Stock s = service.getStock(query);
        return ResponseEntity.ok(s);
    }
     
       @GetMapping("/all")
    public ResponseEntity<?> getAllStock(){
        List<Stock> s = service.getAllStock();
        return ResponseEntity.ok(s);
    }




}
