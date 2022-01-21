package com.payday.notification.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author anar
 *
 */
@Entity
@Table(name = "notification")
@Data
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "userName")
	@NotNull
	private String userName;

	@Column(name = "completiondate")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "LL")
	private Date completionDate;

	@Column(name = "orderId")
	@NotNull
	private BigDecimal orderId;


	private String message;

}
