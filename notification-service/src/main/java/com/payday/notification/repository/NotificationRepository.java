package com.payday.notification.repository;


import com.payday.notification.domain.Notification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * 
 * @author anar
 *
 */
@Repository
public interface NotificationRepository extends CrudRepository<Notification,Integer> {

  List<Notification> findBySendStatus(int sendStatus);
  
}
