package com.payday.notification.service;


import com.payday.notification.domain.Notification;
import com.payday.notification.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 *
 * @author anar
 *
 */
@Service
public class NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    @Autowired
    private NotificationRepository repository;

    public Integer save(Notification entity) {
        entity.setSendStatus(0);
        repository.save(entity);
        return entity.getId();
    }

    public List<Notification> findBySendStatus(int sendStatus) {
        List<Notification> notifications = repository.findBySendStatus(sendStatus);
        return notifications;
    }

    public Integer update(Notification entity) {
        repository.save(entity);
        return entity.getId();
    }


}
