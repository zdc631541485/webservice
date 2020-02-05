package com.qdtz.SpringWebService.config;

import java.net.UnknownHostException;

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

import com.qdtz.SpringWebService.interceptor.IPaddressIntercepter;
import com.qdtz.SpringWebService.service.TestOracleService;

@Configuration
@EnableAutoConfiguration
public class WebServiceConfig {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(WebServiceConfig.class);
    //服务注册地址
    protected  String registeredAddress = "/webservice";
    //服务发布地址
    protected  String publishAddress = "/interfaces";
    
	@Autowired
    private Bus bus;
	
	@Autowired
	private TestOracleService testOracleService;
	
	@Autowired
	private IPaddressIntercepter iPaddressIntercepter;
	
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
        return new ServletRegistrationBean(new CXFServlet(), registeredAddress+"/*");
    }
 
    @Bean
    public Endpoint endpoint() throws UnknownHostException {
        EndpointImpl endpoint = new EndpointImpl(bus, testOracleService);
        endpoint.publish(publishAddress);
        endpoint.getInInterceptors().add(iPaddressIntercepter);
        LOGGER.info("webservice publish success!!!");
        return endpoint;
    }
    
//    @Bean
//    public Endpoint endpoint2() {
//        EndpointImpl endpoint = new EndpointImpl(bus, testOracleService);
//        endpoint.publish("/znsjInterf1");
//        return endpoint;
//    }

}
