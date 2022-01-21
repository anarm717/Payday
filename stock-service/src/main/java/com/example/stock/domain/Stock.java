package com.example.stock.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author anar
 *
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;

    private String symbol;

    private BigDecimal lastPrice;

    private BigDecimal change;

    private Float changePercent;

    private Date actionDate;

    public Stock(String name, String symbol, BigDecimal lastPrice, BigDecimal change, Float changePercent, Date date) {
        this.name = name;
        this.symbol = symbol;
        this.lastPrice = lastPrice;
        this.change = change;
        this.changePercent = changePercent;
        this.actionDate = date;

    }


}
