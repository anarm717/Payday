package com.payday.bank;


import com.fasterxml.jackson.databind.ObjectMapper;

import com.payday.bank.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 *
 * @author anar
 *
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = NotificationServiceApplication.class)
//@WebAppConfiguration
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@WebMvcTest(PortfolioApplication.class)
public class PortfolioTests {

//	private final String SPRING_BOOT_MATCH = "Spring Boot";
//	private final String CLOUD_MATCH = "Cloud";
//	@SuppressWarnings("rawtypes")
//	private HttpMessageConverter mappingJackson2HttpMessageConverter;
//	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
//			MediaType.APPLICATION_JSON.getSubtype(),
//			Charset.forName("utf8"));

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	ObjectMapper mapper;

	@MockBean
	OrderRepository repository;

	@Test
	public void getReport() throws Exception {

//		Mockito.when(
//				repository.report(Mockito.anyString(), Mockito.anyString())).thenReturn();

	 RequestBuilder request = MockMvcRequestBuilders.get(
				"/report1").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(request).andReturn();

		String expected = "OK";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

	}



}
