package com.qdtz.SpringWebService.config;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.qdtz.SpringWebService.interceptor.MyIntercepter;
import com.qdtz.SpringWebService.service.TestOracleService;

@Configuration
@EnableAutoConfiguration
public class WebServiceConfig {
	
    private static final Logger LOG = LoggerFactory.getLogger(WebServiceConfig.class);
    
	@Autowired
    private Bus bus;
	
	@Autowired
	private TestOracleService testOracleService;
	
	@Autowired
	private MyIntercepter myIntercepter;
	
	/**
	* @Title: dispatcherServlet 
	* @Description:注册servlet 拦截/ws 开头的请求 不设置 默认为：/services/*
	* @author: ZDC
	* @date 2020年1月9日 上午11:25:31
	* @return ServletRegistrationBean 
	* @version v1.0.0
	 */
	@Bean
    public ServletRegistrationBean dispatcherServletWeb() {
        return new ServletRegistrationBean(new CXFServlet(), "/webservice/*");
    }
 
    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, testOracleService);
        endpoint.publish("/znsjInterf");
        endpoint.getInInterceptors().add(myIntercepter);
        LOG.info("webservice publish success!!!");
        return endpoint;
    }
    
//    @Bean
//    public Endpoint endpoint2() {
//        EndpointImpl endpoint = new EndpointImpl(bus, testOracleService);
//        endpoint.publish("/znsjInterf1");
//        return endpoint;
//    }

}
