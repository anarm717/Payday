package com.payday.bank.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


import com.payday.bank.domain.Account;
import com.payday.bank.exception.*;
import com.payday.bank.repository.AccountRepository;
import com.payday.bank.response.MessageResponse;
import com.payday.bank.response.Reason;
import com.payday.bank.util.BcryptEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;

/**
 *
 * @author anar
 *
 */
@Service
public class AccountService {

	private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

	/**
	 * The accounts repository.
	 */
	@Autowired
	AccountRepository accounts;

	/**
	 * Retrieve an account with given id. The id here is the unique id value of
	 * the account managed by the repository (auto-increment).
	 * 
	 * @param id
	 *            The id of the account.
	 * @return The account object if found or throws a NoRecordsFoundException.
	 */
	public Account findAccount(Integer id) {

		logger.debug("AccountService.findAccount: id=" + id);

//		EduDegreeEntity rankInDb = eduDegreeRepository.findById(id)
//				.orElseThrow(() -> new ItemNotFoundException(Reason.NOT_FOUND.getValue()));

	    Account  account = accounts.findById(id).orElseThrow(()-> new ItemNotFoundException(Reason.NOT_FOUND.getValue()));
//		if (account == null) {
//			logger.warn("AccountService.findAccount: could not find account with id: " + id);
//			throw new ItemNotFoundException(Reason.NOT_FOUND.getValue());
//		}

		logger.info(String.format("AccountService.findAccount - retrieved account with id: %s. Payload is: %s", id, account));

		return account;
	}



	/**
	 * Retrieve an account with given id. The id here is the unique user id
	 * value of the account, ie the username.
	 * 
	 * @param id
	 *            The user id of the account.
	 * @return The account object if found or throws a NoRecordsFoundException.
	 */
	public Account findAccount(String id) {

		logger.info("AccountService.findAccount: id=" + id);

		Account account = accounts.findByUserName(id);


		System.out.println("111 " +account);

		if (account == null) {
			logger.warn("AccountService.findAccount: could not find account with id: " + id);
			throw new ItemNotFoundException(Reason.NOT_FOUND.getValue());
		}

		logger.info(String.format("AccountService.findAccount - retrieved account with id: %s. Payload is: %s", id, account));

		return account;
	}


	/**
	 * Saves the given account in the repository.
	 * 
	 * @param accountRequest
	 *            The account to save.
	 * @return the id of the account.
	 */
	public Integer saveAccount(Account accountRequest) {


		Account accountinDB = accounts.findByUserName(accountRequest.getUserName());
		if (accountinDB != null) {
			logger.warn("AccountService.findAccount: could find account with id: " + accountRequest.getUserName());
			throw new NameAlreadyExistException(Reason.ALREADY_EXIST.getValue());
		}

		logger.debug("AccountService.saveAccount:" + accountRequest.toString());
		// need to set some stuff that cannot be null!
		if (accountRequest.getLogincount() == null) {
			accountRequest.setLogincount(0);
		}
		if (accountRequest.getLogoutcount() == null) {
			accountRequest.setLogoutcount(0);
		}

		accountRequest.setPassword(BcryptEncoder.endoce(accountRequest.getPassword()));
		Account account = accounts.save(accountRequest);
		logger.info("AccountService.saveAccount: account saved: " + account);
		return account.getId();
	}

	public Integer updateAccount(Account accountRequest) {


		logger.debug("AccountService.updateAccount:" + accountRequest.toString());
		// need to set some stuff that cannot be null!
	
		
		Account account = accounts.save(accountRequest);
		logger.info("AccountService.saveAccount: account saved: " + account);
		return account.getId();
	}

	/**
	 * Attempts to login the user with the given username and password. Throws
	 * AuthenticationException if an account with the given username and
	 * password cannot be found.
	 * 
	 * @param username
	 *            The username to login.
	 * @param password
	 *            The password to use.
	 * @return a map with the authtoken, account Id.
	 */
	public Map<String, Object> login(String username, String password) {
		logger.debug("login in user: " + username);
		Map<String, Object> loginResponse = null;
		Account account = accounts.findByUserNameAndPassword(username, BcryptEncoder.endoce(password));
		if (account != null) {
			logger.debug("Found Account for user: " + username);
//			account.setAuthtoken(UUID.randomUUID().toString());
			account.setLogincount(account.getLogincount() + 1);
//			account.setLastlogin(new Date());
			account = accounts.save(account); // persist new auth token and last
												// login
			loginResponse = new HashMap<String, Object>();

//			loginResponse.put("authToken", account.getAuthtoken());
			loginResponse.put("accountid", account.getId());
			// loginResponse.put("password", account.getPasswd());

			logger.info("AccountService.login success for " + username + " username::token=" + loginResponse.get("authToken"));

		} else {
			logger.warn("AccountService.login failed to find username=" + username + " password=" + password);
			throw new AuthenticationException("Login failed for user: " + username);
		}
		return loginResponse;
	}

	/**
	 * logs the give user out of the system.
	 * 
	 * @param userName
	 *            the userid to logout.
	 * @return The account object or null;
	 */
	public Account logout(String userName) {
		logger.debug("AccountService.logout: Logging out account with userId: " + userName);
		Account account = accounts.findByUserName(userName);
		if (account != null) {
//			account.setAuthtoken(null); // remove token
			account.setLogoutcount(account.getLogoutcount() + 1);
			accounts.save(account);
			logger.info("AccountService.logout: Account logged out: " + account.getUserName());
		} else {
			logger.warn("AccountService.logout: Could not find account to logout with userId: " + userName);
		}
		return account;
	}
}
