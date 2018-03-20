package com.samyem.webblocks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.samyem.webblocks.server.WebBlocksServletImpl;

@SpringBootApplication
public class WebBlocksApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(WebBlocksApplication.class, args);
	}

	@Bean
	public ServletRegistrationBean<WebBlocksServletImpl> servletRegistrationBean() {
		ServletRegistrationBean<WebBlocksServletImpl> bean = new ServletRegistrationBean<>(new WebBlocksServletImpl(),
				"/webblocks/webBlocksService");
		return bean;
	}

	// Register ServletContextListener
	// @Bean
	// public ServletListenerRegistrationBean<ServletContextListener>
	// listenerRegistrationBean() {
	// ServletListenerRegistrationBean<ServletContextListener> bean = new
	// ServletListenerRegistrationBean<>();
	// // bean.setListener(new MyServletContextListener());
	// return bean;
	//
	// }
}
