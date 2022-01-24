package com.payday.bank.repository;


import com.payday.bank.domain.DumpDto;
import com.payday.bank.domain.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
/**
 * 
 * @author anar
 *
 */
public interface OrderRepository extends CrudRepository<Order,Integer> {

	List<Order> findByUserName(String userName);
	Optional<Order>  findByOrderId(Integer orderId);
	
	@Query(value = "select * from (SELECT user_name as userName,price, initial_quantity as initialquantity,symbol,completiondate,'buying' as type" +
			" FROM ORDERS where completiondate>=sysdate-60 and user_name = :userName " +
			"union all " +
			"select user_name as userName,price,quantity as initialquantity,symbol,completiondate,'selling' as type " +
			"from sells where completiondate>=sysdate-60 and user_name = :userName ) bb  " +
			"order by bb.type,bb.completiondate asc ",nativeQuery = true)
	 List<DumpDto> report(@Param("userName") String userName);
}
