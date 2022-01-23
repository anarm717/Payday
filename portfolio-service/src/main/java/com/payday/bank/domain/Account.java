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
public class Account {

    private Integer id;

    private String address;

    private String password;

    private String userName;

    private String email;

    private String creditcard;

    private String fullname;

    private Integer logoutcount;

    private BigDecimal balance = new BigDecimal(0);

    private Integer logincount;
}
