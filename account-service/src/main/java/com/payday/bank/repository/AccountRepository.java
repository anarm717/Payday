package com.payday.bank.repository;


import com.payday.bank.domain.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 *
 * @author anar
 *
 */
public interface AccountRepository extends CrudRepository<Account,Integer> {
	Optional<Account> findByUserNameAndPassword(String userId, String passwd);
	Optional<Account> findByUserName(String userId);

}
