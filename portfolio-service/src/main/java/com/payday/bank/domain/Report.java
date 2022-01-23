package com.payday.bank.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 *
 *
 * @author anar
 *
 */
@Data
public class Report {

	private BigDecimal buyingVolatility;
	private BigDecimal sellingVolatility;
	private List<DumpDto> dtos;

}
