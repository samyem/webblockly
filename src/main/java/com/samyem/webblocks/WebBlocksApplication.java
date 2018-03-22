package com.samyem.webblocks;

import java.util.Arrays;

import org.realityforge.gwt.cache_filter.GWTCacheControlFilter;
import org.realityforge.gwt.cache_filter.GWTGzipFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
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

	@Bean
	public FilterRegistrationBean<GWTCacheControlFilter> cacheFilter() {
		FilterRegistrationBean<GWTCacheControlFilter> frb = new FilterRegistrationBean<>();
		frb.setFilter(new GWTCacheControlFilter());
		frb.setUrlPatterns(Arrays.asList("/*"));
		return frb;
	}

	@Bean
	public FilterRegistrationBean<GWTGzipFilter> myFilterRegistration() {
		FilterRegistrationBean<GWTGzipFilter> frb = new FilterRegistrationBean<>();
		frb.setFilter(new GWTGzipFilter());
		frb.setUrlPatterns(Arrays.asList("/*"));
		return frb;
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
