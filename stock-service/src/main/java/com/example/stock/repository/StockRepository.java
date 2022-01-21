package com.example.stock.repository;

import com.example.stock.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author anar
 *
 */

@Repository
public interface StockRepository extends JpaRepository<Stock,Long> {

    Stock findBySymbol(String symbol);
}
