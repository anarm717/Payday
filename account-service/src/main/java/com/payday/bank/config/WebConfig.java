
package com.payday.bank.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


/**
 *
 * @author anar
 *
 */
@Configuration
//@ComponentScan(basePackages = { "" })
public class WebConfig extends WebMvcConfigurationSupport {

	/**
	 * configure the message converters with the date formatter.
	 */
	@Override
	public void configureMessageConverters(
			List<HttpMessageConverter<?>> converters) {
		// Configure JSON support
		MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter = new MappingJackson2HttpMessageConverter();
		mappingJacksonHttpMessageConverter.setSupportedMediaTypes(Arrays
				.asList(MediaType.APPLICATION_JSON));
		//mappingJacksonHttpMessageConverter.getObjectMapper().configure(
		//		Feature.WRITE_DATES_AS_TIMESTAMPS, true);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
		// There is no need to set the timezone as Jackson uses GMT and not the
		// local time zone (which is exactly what you want)
		// Note: While SimpleDateFormat is not threadsafe, Jackson Marshaller's
		// StdSerializerProvider clones the configured formatter for each thread
		mappingJacksonHttpMessageConverter.getObjectMapper().setDateFormat(
				format);
		//mappingJacksonHttpMessageConverter.getObjectMapper().configure(
		//		Feature.INDENT_OUTPUT, true);
		// mappingJacksonHttpMessageConverter.getObjectMapper().getSerializationConfig().setSerializationInclusion(Inclusion.NON_NULL);
		converters.add(mappingJacksonHttpMessageConverter);
	}
	/*
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void configureHandlerExceptionResolvers(
			List<HandlerExceptionResolver> exceptionResolvers) {
		ExtendedExceptionHandlerExceptionResolver customResolver = new ExtendedExceptionHandlerExceptionResolver();
		customResolver.setExceptionHandler(new GlobalExceptionHandler());
		customResolver.setMessageConverters(getMessageConverters());
		customResolver.afterPropertiesSet();
		exceptionResolvers.add(customResolver);
	}
*/
}
