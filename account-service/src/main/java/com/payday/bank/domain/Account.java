package com.payday.bank.domain;

import java.io.Serializable;
import java.math.BigDecimal;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
/**
 *
 * @author anar
 *
 */
@Entity
@Table(name = "ACCOUNT")
@Data
public class Account implements Serializable {

	private static final long serialVersionUID = -3057275461420965784L;

	@Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id")
	private Integer id;

	@Column(name = "address", length = 250)
    private String address;

	@Column(name = "password", length = 250)
	@NotNull
    private String password;

	@Column(name = "userName", length = 250, unique = true)
    @NotNull
    private String userName;

	@Column(name = "email", length = 250)
	@NotNull
    private String email;

	@Column(name = "creditcard", length = 250)
	@NotNull
    private String creditcard;

	@Column(name = "fullname", length = 250)
    private String fullname;


	@Column(name = "logoutcount")
	@NotNull
    private Integer logoutcount;

	@Column(name = "balance", precision = 14, scale = 2, nullable=false)
	@NotNull
    private BigDecimal balance = new BigDecimal(0);


	@Column(name = "logincount")
	@NotNull
    private Integer logincount;



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Account [id=").append(id).append(", address=")
				.append(address).append(", passwd=").append(password)
				.append(", userName=").append(userName).append(", email=")
				.append(email).append(", creditcard=").append(creditcard)
				.append(", fullname=").append(fullname).append(", authtoken=")
				.append(", logoutcount=")
				.append(logoutcount)
				.append(", balance=").append(balance)
				.append(", logincount=").append(logincount).append("]");
		return builder.toString();
	}



}
