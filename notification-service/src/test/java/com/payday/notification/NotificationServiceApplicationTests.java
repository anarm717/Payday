package com.payday.notification;


import com.payday.notification.domain.Notification;
import com.payday.notification.repository.NotificationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 *
 * @author anar
 *
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = NotificationServiceApplication.class)
//@WebAppConfiguration
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@WebMvcTest(NotificationServiceApplication.class)
public class NotificationServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	ObjectMapper mapper;

	@MockBean
	NotificationRepository repository;

	@Test
	public void add() throws Exception {
		Notification notification =new Notification();
		notification.setMessage("Spring Boot Testing");
		notification.setUserName("anar");
		notification.setOrderId(new BigDecimal(1));
//		mockMvc.perform(post("/notification")
//				.content(this.toJsonString())
//						.contentType(contentType)).andExpect(status().isOk());
		Mockito.when(repository.save(notification)).thenReturn(notification);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/notification")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(notification));

		mockMvc.perform(mockRequest)
				.andExpect(status().isOk());
//				.andExpect(jsonPath("$", notNullValue()))
//				.andExpect(jsonPath("$.name", is("John Doe")));

	}



}
