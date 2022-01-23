package com.payday.bank.domain;

import lombok.Data;
import java.math.BigDecimal;

/**
 *
 *
 * @author anar
 *
 */
@Data
public class Holding {

	private Integer id;
	private String symbol;
	private Integer quantity = 0;
	private BigDecimal changePercent;
	private BigDecimal purchaseValue = BigDecimal.ZERO;
	private BigDecimal currentValue = BigDecimal.ZERO;



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Holding [id=").append(id)
				.append(", symbol=").append(symbol)
				.append(", quantity=").append(quantity)
				.append(", purchasePrice=").append(purchaseValue)
				.append(", currentValue=").append(currentValue).append("]");
		return builder.toString();
	}


}
