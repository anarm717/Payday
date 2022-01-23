package com.payday.bank.domain;

import java.util.Date;

public interface DumpDto {
  String getUserName();
    BigDecimal getPrice();
    Integer getInitialQuantity();
    String getSymbol();
    Date getCompletionDate();
    String getType();
}
