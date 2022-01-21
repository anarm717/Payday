package com.payday.bank.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 *
 * @author anar
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Stock {

	private Long id;

	private String name;

	private String symbol;

	private BigDecimal lastPrice;

	private BigDecimal change;

	private Float changePercent;

	private Date actionDate;

}
