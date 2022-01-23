package com.payday.bank.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 * @author anar
 *
 */
@Data
public class Portfolio {

	private String userName;
	private BigDecimal balance;
	private BigDecimal currentTotalValue = BigDecimal.ZERO;
	private BigDecimal purchaseValue = BigDecimal.ZERO;
	private Map<String, Holding> holdings = new HashMap<>();

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}


	public Map<String, Holding> getHoldings() {
		return holdings;
	}

	public void setHoldings(Map<String, Holding> holdings) {
		this.holdings = holdings;
	}

	public void addHolding(Holding holding) {
		holdings.put(holding.getSymbol(), holding);
	}

	public Holding getHolding(String symbol) {
		return holdings.get(symbol);
	}

	public BigDecimal getCurrentTotalValue() {
		return currentTotalValue;
	}

	public void setCurrentTotalValue(BigDecimal currentTotalValue) {
		this.currentTotalValue = currentTotalValue;
	}

	/**
	 * Iterates through each of the holdings aggregating the values.
	 */
	public void refreshTotalValue() {
		this.currentTotalValue = BigDecimal.ZERO;
		this.purchaseValue = BigDecimal.ZERO;
		holdings.values().forEach(holding -> {
			this.currentTotalValue = this.currentTotalValue.add(holding.getCurrentValue().multiply(new BigDecimal(holding.getQuantity())));
			this.purchaseValue = this.purchaseValue.add(holding.getPurchaseValue().multiply(new BigDecimal(holding.getQuantity())));
		});
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Portfolio [userName=").append(userName).append(", currentTotalValue=").append(currentTotalValue).append(", purchaseValue=")
				.append(purchaseValue).append(", holdings=").append(holdings).append("]");
		return builder.toString();
	}

	public BigDecimal getPurchaseValue() {
		return purchaseValue;
	}

	public void setPurchaseValue(BigDecimal purchaseValue) {
		this.purchaseValue = purchaseValue;
	}


}
