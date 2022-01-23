package com.payday.bank.controller;


import com.payday.bank.domain.Order;
import com.payday.bank.domain.Portfolio;
import com.payday.bank.service.PortfolioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author anar
 */
@RestController
public class PortfolioController {
    private static final Logger logger = LoggerFactory.getLogger(PortfolioController.class);

    /**
     * the service to delegate to.
     */
    @Autowired
    private PortfolioService service;

    /**
     * Retrieves the portfolio for the given account.
     *
     * @param userName the account to retrieve the portfolio for.
     * @return The portfolio with HTTP OK.
     */
    @RequestMapping(value = "/portfolio/{userName}", method = RequestMethod.GET)
    public ResponseEntity<Portfolio> getPortfolio(@PathVariable("userName") final String userName) {
        logger.debug("PortfolioController: Retrieving portfolio with user id:" + userName);
        Portfolio folio = service.getPortfolio(userName);
//		logger.debug("PortfolioController: Retrieved portfolio:" + folio);
        return new ResponseEntity<Portfolio>(folio, getNoCacheHeaders(), HttpStatus.OK);
    }

    /**
     * Adds an order to the portfolio of the given account.
     *
     * @param userName the account to add the order to.
     * @param order    The order to add.
     * @param builder
     * @return The order with HTTP CREATED or BAD REQUEST if it couldn't save.
     */
    @RequestMapping(value = "/portfolio/{userName}", method = RequestMethod.POST)
    public ResponseEntity<?> addOrder(@PathVariable("userName") final String userName,
                                      @RequestBody final Order order, UriComponentsBuilder builder) {
        logger.debug("Adding Order: " + order);

        //TODO: can do a test to ensure userName == order.getUserName();

        Order savedOrder = (Order) service.addOrder(order).getBody();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(builder.path("/portfolio/{userName}")
                .buildAndExpand(userName).toUri());
        logger.debug("Order added: " + savedOrder);
//		if (savedOrder != null && savedOrder.getOrderId() != null) {
//			return new ResponseEntity<Order>(savedOrder, responseHeaders, HttpStatus.CREATED);
//		} else {
//			logger.warn("Order not saved: " + order);
//			return new ResponseEntity<Order>(savedOrder, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
//		}


//        if (savedOrder == 1) {
            return new ResponseEntity<>(savedOrder, responseHeaders, HttpStatus.CREATED);
//        } else if (savedOrder == 2) {
//            return new ResponseEntity<>("Not enough balance", responseHeaders, HttpStatus.OK);
//        } else if (savedOrder == 3) {
//            return new ResponseEntity<>("Not enough quantity", responseHeaders, HttpStatus.OK);
//        } else if (savedOrder == 4) {
//            return new ResponseEntity<>("Negative value", responseHeaders, HttpStatus.OK);
//        }else {
//            return new ResponseEntity<>("Dont have this symbol", responseHeaders, HttpStatus.OK);
//        }
    }

    private HttpHeaders getNoCacheHeaders() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Cache-Control", "no-cache");
        return responseHeaders;
    }


    @GetMapping(value = "/report/{userName}")
    public ResponseEntity<?> getReport(@PathVariable("userName") final String userName) {
        return ResponseEntity.ok(service.getReport(userName));
    }


}
