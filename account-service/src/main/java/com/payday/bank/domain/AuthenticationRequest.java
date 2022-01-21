package com.payday.bank.domain;

import lombok.Data;

/**
 *
 * @author anar
 *
 */
@Data
public class AuthenticationRequest {
	private String username;
	private String password;

	@Override
	public String toString() {
		return "AuthenticationRequest [username=" + username + ", password=" + password + "]";
	}

}
