package com.payday.bank.domain;


import java.math.BigDecimal;
import java.util.Date;

public interface ReportItems {
  String getUserName();
    BigDecimal getPrice();
    Integer getInitialQuantity();
    String getSymbol();
    Date getCompletionDate();
    String getType();
}
