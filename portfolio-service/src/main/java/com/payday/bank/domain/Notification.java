package com.payday.bank.domain;

import lombok.Data;

import java.util.Date;


/**
 *
 *
 * @author anar
 *
 */
@Data
public class Notification {

	private Integer id;
	private String userName;
	private Date completionDate;
	private Integer orderId;
	private String message;

}
