package com.payday.bank.service;

import com.payday.bank.domain.*;
import com.payday.bank.exception.ItemNotFoundException;
import com.payday.bank.exception.NotEnoughException;
import com.payday.bank.exception.NotEnoughQuantityException;
import com.payday.bank.repository.OrderRepository;
import com.payday.bank.repository.SellRepository;
import com.payday.bank.response.Reason;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author anar
 */
@Service
@RefreshScope
public class PortfolioService {
    private static final Logger logger = LoggerFactory.getLogger(PortfolioService.class);

    @Autowired
    OrderRepository repository;

    @Autowired
    SellRepository sellRepository;

    @Autowired
    public RestTemplate restTemplate;


    /**
     * Retrieves the portfolio for the given accountId.
     *
     * @param userName The account id to retrieve for.
     * @return The portfolio.
     */
    public Portfolio getPortfolio(String userName) {
        /*
         * Retrieve all orders for accounts id and build portfolio. - for each
         * order create holding. - for each holding find current price.
         */

        logger.debug("Getting portfolio for accountId: " + userName);
        List<Order> orders = repository.findByUserName(userName);
        Portfolio folio = new Portfolio();
        orders.forEach(order -> {
            if(order.getQuantity()>0) {
            Holding holding = new Holding();
            holding.setSymbol(order.getSymbol());
            ResponseEntity<Stock> responseEntity = restTemplate.getForEntity("http://localhost:8083/stocks/?query=" + order.getSymbol(),
                    Stock.class);
            Stock quote = responseEntity.getBody();
            holding.setId(order.getOrderId());
            holding.setCurrentValue(quote.getLastPrice());
            holding.setQuantity(order.getQuantity());
            holding.setPurchaseValue(order.getPrice());
            
            Integer change = holding.getCurrentValue().subtract(holding.getPurchaseValue()).intValue();
            if (change > 0) {
                holding.setChangePercent(holding.getCurrentValue().divide(holding.getPurchaseValue(),2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).subtract(new BigDecimal(100)));
            } else {
                holding.setChangePercent(holding.getCurrentValue().divide(holding.getPurchaseValue(),2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).subtract(new BigDecimal(100)));
            }
            
            folio.getHoldings().put(order.getSymbol(), holding);
            }
        });
        folio.refreshTotalValue();
        folio.setUserName(userName);
        
          try {
            ResponseEntity<Account> responseEntity = restTemplate.getForEntity("http://localhost:8080/account/?name=" + userName,
                    Account.class);
            folio.setBalance(responseEntity.getBody().getBalance());
        }catch (Exception e){
            
        }
        return folio;
    }

    /**
     * Add an order to the repository and modify account balance.
     *
     * @param order the order to add.
     * @return the saved order.
     */
    @Transactional
    public ResponseEntity<?> addOrder(Order order) {
        Notification entity = new Notification();
        logger.debug("Adding order: " + order);
        if (order.getOrderFee() == null) {
            order.setOrderFee(Order.DEFAULT_ORDER_FEE);
            logger.debug("Adding Fee to order: " + order);
        }
        if (order.getOrderType() == OrderType.BUY) {

            double amount = order.getQuantity()
                    * order.getPrice().doubleValue()
                    + order.getOrderFee().doubleValue();

            try {
                ResponseEntity<Double> result = restTemplate.getForEntity("http://localhost:8080/accounts/{userName}/decreaseBalance/{amount}",
                        Double.class,
                        order.getUserName(), amount
                );


                if (result.getStatusCode() == HttpStatus.CREATED) {
                    logger.info(String
                            .format("Account funds updated successfully for account: %s and new funds are: %s",
                                    order.getUserName(), result.getBody()));


                    order.setInitialQuantity(order.getQuantity());
                    order.setCompletionDate(new Date());
                    order.setOrderId(null);
                    Order db = repository.save(order);

                    notificationEntityConvert(db, entity);
                    Integer id = restTemplate.postForObject("http://localhost:8082/notification/", entity, Integer.class);

                    return ResponseEntity.ok(db);
                } else {
                    // TODO: throw exception - not enough funds!
                    // SK - Whats the expected behaviour?
                    logger.warn("PortfolioService:addOrder - decresing balance HTTP not ok: ");
                    throw new NotEnoughException(Reason.NOT_ENOUGH_AMOUNT.getValue());
                }
            } catch (Exception ex) {
                throw new NotEnoughException(Reason.NOT_ENOUGH_AMOUNT.getValue());
            }
        } else {
            double amount = order.getQuantity()
                    * order.getPrice().doubleValue()
                    - order.getOrderFee().doubleValue();
            ResponseEntity<Double> result = restTemplate.getForEntity("http://localhost:8080/accounts/{userName}/increaseBalance/{amount}",
                    Double.class, order.getUserName(), amount);
            if (result.getStatusCode() == HttpStatus.OK) {
                logger.info(String
                        .format("Account funds updated successfully for account: %s and new funds are: %s",
                                order.getUserName(), result.getBody()));


                Optional<Order> currentOrder = repository.findByOrderId(order.getOrderId());

                if (!currentOrder.isPresent()){
                    throw new ItemNotFoundException(Reason.NOT_FOUND.getValue());
                }
                int quantity = currentOrder.get().getQuantity() - order.getQuantity();
                if(quantity<0){
                    throw new NotEnoughQuantityException(Reason.NOT_ENOUGH_QUANTITY.getValue());
                }
                currentOrder.get().setQuantity(quantity);
                repository.save(currentOrder.get());


                Sell sell = new Sell();
                sell.setOrderId(currentOrder.get().getOrderId());
                sell.setUserName(currentOrder.get().getUserName());
                sell.setQuantity(order.getQuantity());
                sell.setBuyPrice(currentOrder.get().getPrice());
                sell.setSymbol(order.getSymbol());
                sell.setPrice(order.getPrice());
                sell.setCompletionDate(new Date());
                sellRepository.save(sell);

                notificationEntityConvert(order, entity);
                Integer id = restTemplate.postForObject("http://localhost:8082/notification/", entity, Integer.class);
                return ResponseEntity.ok(order);
            } else {
                // TODO: throw exception - negative value???
                logger.warn("PortfolioService:addOrder - increasing balance HTTP not ok: ");
                throw new NotEnoughException(Reason.NOT_ENOUGH_QUANTITY.getValue());
            }
        }
    }

    private void notificationEntityConvert(Order order, Notification entity) {
        entity.setMessage("Transaction successfull");
        entity.setCompletionDate(new Date());
        entity.setUserName(order.getUserName());
        entity.setOrderId(order.getOrderId());
        
         ResponseEntity<Account> responseEntity = restTemplate.getForEntity("http://localhost:8080/account/?name=" + order.getUserName(),
            Account.class);
         entity.setEmail(responseEntity.getBody().getEmail());
    }

  public List<DumpDto> getReport(String userName){

        return repository.report(userName);

    }



}
