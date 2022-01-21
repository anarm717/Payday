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

@Data
@Entity
@Table(name = "sells")
public class Sell {
	public static BigDecimal DEFAULT_ORDER_FEE = new BigDecimal(10.50);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "orderid")
	private Integer orderId;

	@Column(name = "userName")
	@NotNull
	private String userName;

	@Column(name = "symbol", length = 10)
//	@NotNull
	private String symbol;

	@Column(name = "buyPrice", precision = 14, scale = 2)
	private BigDecimal buyPrice;

	@Column(name = "completiondate")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "LL")
	private Date completionDate;

	@Column(name = "price", precision = 14, scale = 2)
	@NotNull
	private BigDecimal price;

	@Column(name = "quantity")
	@NotNull
	private Integer quantity;



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Order [orderId=").append(orderId).append(", userName=").append(userName).append(", symbol=").append(symbol)
				.append(", buyPrice=").append(buyPrice)
				.append(", completionDate=").append(completionDate).append(", orderType=")
//				.append(orderType)
				.append(", price=").append(price).append(", quantity=").append(quantity).append("]");
		return builder.toString();
	}


}
