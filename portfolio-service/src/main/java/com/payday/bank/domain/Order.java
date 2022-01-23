package com.payday.bank.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * 
 * @author anar
 *
 */
@Entity
@Table(name = "ORDERS")
@Data
public class Order {
	public static BigDecimal DEFAULT_ORDER_FEE = new BigDecimal(10.50);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "orderid")
	private Integer orderId;

	@Column(name = "userName")
	@NotNull
	private String userName;

	@Column(name = "symbol", length = 10)
	@NotNull
	private String symbol;

	@Column(name = "orderfee", precision = 14, scale = 2)
	private BigDecimal orderFee;

	@Column(name = "completiondate")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "LL")
	private Date completionDate;

	@Column(name = "ordertype")
	@NotNull
	@Enumerated
	private OrderType orderType;

	@Column(name = "price", precision = 14, scale = 2)
	@NotNull
	private BigDecimal price;

	@Column(name = "quantity")
	@NotNull
	private Integer quantity;

	@Column(name = "initial_quantity")
	private Integer initialQuantity;


}
