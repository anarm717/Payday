package com.payday.bank.controller;

import java.math.BigDecimal;

import com.payday.bank.domain.Account;
import com.payday.bank.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

/**
 *
 * @author anar
 *
 */
@RestController
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    /**
     * The service to delegate calls to.
     */
    @Autowired
    private AccountService service;

    /**
     * REST call to retrieve the account with the given id as userId.
     *
     * @param id The id of the user to retrieve the account for.
     * @return The account object if found.
     */
    @RequestMapping(value = "/account/{id}", method = RequestMethod.GET)
    public ResponseEntity<Account> find(@PathVariable("id") final Integer id) {

        logger.info("AccountController.find: id=" + id);

        Account accountResponse = this.service.findAccount(id);
        return new ResponseEntity<Account>(accountResponse, getNoCacheHeaders(), HttpStatus.OK);

    }

    // TODO: do we need this? need to change web service to use find() above.
    @RequestMapping(value = "/account/", method = RequestMethod.GET)
    public ResponseEntity<Account> findAccount(@RequestParam(value = "name") final String id) {

        logger.info("AccountController.findAccount: id=" + id);

        Account accountResponse = this.service.findAccount(id);
        return new ResponseEntity<Account>(accountResponse, getNoCacheHeaders(), HttpStatus.OK);

    }

    /**
     * REST call to save the account provided in the request body.
     *
     * @param accountRequest The account to save.
     * @param builder
     * @return
     */
    @PostMapping(value = "/account")
    public ResponseEntity<String> save(@Valid @RequestBody Account accountRequest, UriComponentsBuilder builder) {

//        logger.debug("AccountController.save: userId=" + accountRequest.getUserName());
//        Account accountResponse = this.service.findAccount(accountRequest.getUserName());
//
//        System.out.println(" accountResponse " +accountResponse);
//
//        if(accountResponse != null){
//            return new ResponseEntity<String>("Data is already exists",HttpStatus.OK);
//        }
        Integer accountProfileId = this.service.saveAccount(accountRequest);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(builder.path("/account/{id}").buildAndExpand(accountProfileId).toUri());
        return new ResponseEntity<String>(responseHeaders, HttpStatus.CREATED);

    }

    /**
     * REST call to decrease the balance in the account. Decreases the balance
     * of the account if the new balance is not lower than zero. Returns HTTP OK
     * and the new balance if the decrease was successful, or HTTP
     * EXPECTATION_FAILED if the new balance would be negative and the
     * old/current balance.
     *
     * @param userName The id of the account.
     * @param amount   The amount to decrease the balance by.
     * @return The new balance of the account with HTTP OK.
     */
    @RequestMapping(value = "/accounts/{userName}/decreaseBalance/{amount}", method = RequestMethod.GET)
    public ResponseEntity<Double> decreaseBalance(@PathVariable("userName") final String userName, @PathVariable("amount") final double amount) {

        logger.debug("AccountController.decreaseBalance: id='" + userName + "', amount='" + amount + "'");

        Account accountResponse = this.service.findAccount(userName);

        BigDecimal currentBalance = accountResponse.getBalance();

        BigDecimal newBalance = currentBalance.subtract(new BigDecimal(amount));

        if (newBalance.compareTo(BigDecimal.ZERO) >= 0) {
            accountResponse.setBalance(newBalance);
            this.service.updateAccount(accountResponse);
            return new ResponseEntity<Double>(accountResponse.getBalance().doubleValue(), getNoCacheHeaders(), HttpStatus.CREATED);

        } else {
            // no sufficient founds available
            return new ResponseEntity<Double>(accountResponse.getBalance().doubleValue(), getNoCacheHeaders(), HttpStatus.OK);
        }

    }

    /**
     * REST call to increase the balance in the account. Increases the balance
     * of the account if the amount is not negative. Returns HTTP OK and the new
     * balance if the increase was successful, or HTTP EXPECTATION_FAILED if the
     * amount given is negative.
     *
     * @param userName The id of the account.
     * @param amount   The amount to increase the balance by.
     * @return The new balance of the account with HTTP OK.
     */
    @GetMapping(value = "/accounts/{userName}/increaseBalance/{amount}")
    public ResponseEntity<Double> increaseBalance(@PathVariable("userName") final String userName, @PathVariable("amount") final double amount) {

        logger.info("AccountController.increaseBalance: id='" + userName + "', amount='" + amount + "'");

        Account accountResponse = this.service.findAccount(userName);

        System.out.println(" accountResponse " +accountResponse );

        BigDecimal currentBalance = accountResponse.getBalance();

        logger.debug("AccountController.increaseBalance: current balance='" + currentBalance + "'.");

        if (amount > 0) {

            BigDecimal newBalance = currentBalance.add(new BigDecimal(amount));
            logger.debug("AccountController.increaseBalance: new balance='" + newBalance + "'.");

            accountResponse.setBalance(newBalance);
            this.service.updateAccount(accountResponse);
//            if (-1 == newBalance.compareTo(BigDecimal.ZERO)) {
                return new ResponseEntity<Double>(accountResponse.getBalance().doubleValue(), getNoCacheHeaders(), HttpStatus.OK);
//            } else
//                return new ResponseEntity<Double>(accountResponse.getBalance().doubleValue(), getNoCacheHeaders(), HttpStatus.CREATED);

        } else {
            // amount can not be negative for increaseBalance, please use
            // decreaseBalance
            return new ResponseEntity<Double>(accountResponse.getBalance().doubleValue(), getNoCacheHeaders(), HttpStatus.EXPECTATION_FAILED);
        }

    }

    private HttpHeaders getNoCacheHeaders() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Cache-Control", "no-cache");
        return responseHeaders;
    }
}
