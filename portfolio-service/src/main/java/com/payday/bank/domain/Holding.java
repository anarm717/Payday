package com.payday.bank.domain;

import java.math.BigDecimal;

/**
 *
 *
 * @author anar
 *
 */
public class Holding {

	private Integer id;
	private String symbol;
	private Integer quantity = 0;
	private BigDecimal purchaseValue = BigDecimal.ZERO;
	private BigDecimal currentValue = BigDecimal.ZERO;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPurchaseValue() {
		return purchaseValue;
	}

	public void setPurchaseValue(BigDecimal purchaseValue) {
		this.purchaseValue = purchaseValue;
	}

	public BigDecimal getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(BigDecimal currentValue) {
		this.currentValue = currentValue;
	}


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
