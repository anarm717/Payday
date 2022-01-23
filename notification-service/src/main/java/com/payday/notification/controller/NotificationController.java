package com.payday.notification.controller;


import com.payday.notification.domain.Notification;
import com.payday.notification.service.EmailService;
import com.payday.notification.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.*;

/**
 *
 * @author anar
 *
 */

@RestController
public class NotificationController {
	private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);

	/**
	 * the service to delegate to.
	 */
	@Autowired
	private NotificationService service;
	
	@Autowired
       private EmailService emailService;
	
	 @Scheduled(fixedRate = 5000)
    public void operation() {
		System.out.println("1");
        List<Notification> notifications = service.findBySendStatus(0);
        notifications.forEach(notification -> {
			try {
				emailService.sendEmail(notification.getEmail(),notification.getMessage());
				notification.setSendStatus(1);
                service.update(notification);
			} catch (MessagingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		});
    }


	@PostMapping(value = "/notification")
	public ResponseEntity<?> sendNotification(@RequestBody Notification notification) {
		logger.debug("NotificationController: Retrieving notification with user name:" + notification.getUserName());
		Integer entityId= service.save(notification);

		return new ResponseEntity<Integer>(entityId, HttpStatus.OK);
	}

	private HttpHeaders getNoCacheHeaders() {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Cache-Control", "no-cache");
		return responseHeaders;
	}

}
