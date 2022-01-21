package com.example.stock;

import com.example.stock.controller.StockController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Timer;

@SpringBootApplication
@EnableScheduling
public class StockServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockServiceApplication.class, args);
//		Timer timer = new Timer();
//		timer.schedule(new StockController(), 0, 5000);
	}

}
