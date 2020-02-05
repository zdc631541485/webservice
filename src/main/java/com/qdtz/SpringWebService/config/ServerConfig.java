package com.qdtz.SpringWebService.config;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.alibaba.druid.util.StringUtils;

@Configuration
public class ServerConfig implements ApplicationListener<EmbeddedServletContainerInitializedEvent> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerConfig.class);
	
	private int serverPort;
	
	@Value("${server.context-path:/}")
    private String contextPath;
	
	@Autowired
	private WebServiceConfig webServiceConfig;
	
	
	 
	@Override
    public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
		this.serverPort = event.getEmbeddedServletContainer().getPort();
		LOGGER.info("fileAbsolutePath:"+ getFileAbsolutePath());
		LOGGER.info("wsdl:"+ getWebservciePath());
		LOGGER.info("web:"+ getWebAbsolutePath());
    }
	
	/**
	* @Title: 
	* @Description:获取项目的ip和端口
	* @author: ZDC
	* @date 2020年2月5日 上午11:25:31
	* @return http://ipAddress:Port 如:http://127.0.0.1:8080 
	* @version v1.0.0
	 */
    public String getIpAndPort() {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
        	LOGGER.error("getIpAndPort error"+e.getMessage());
        }
        return "http://"+address.getHostAddress() +":"+serverPort;
    }
    
    /**
	* @Title: 
	* @Description:获取项目绝对路径
	* @author: ZDC
	* @date 2020年2月5日 上午11:25:31
	* @return C:\Users\63154\git\webservice\target\classes
	* @version v1.0.0
	 */
    public String getFileAbsolutePath() {
		try {
			Resource resource = new ClassPathResource("");
			return resource.getFile().getAbsolutePath();
		} catch (Exception e) {
			LOGGER.error("getFileAbsolutePath error!!!"+e.getMessage());
		}
		return "";
    }
    
    /**
   	* @Title: 
   	* @Description:获取web项目的访问路径
   	* @author: ZDC
   	* @date 2020年2月5日 上午11:25:31
   	* @return http://192.168.255.1:8080/web
   	* @version v1.0.0
   	 */
    public String getWebAbsolutePath() {
		try {
			String contextPath = StringUtils.isEmpty(this.contextPath) ? "/":this.contextPath;
			return getIpAndPort()+contextPath;
		} catch (Exception e) {
			LOGGER.error("getWebAbsolutePath error!!!"+e.getMessage());
		}
		return "";
    }
    /**
   	* @Title: 
   	* @Description:获取wsdl项目的访问路径
   	* @author: ZDC
   	* @date 2020年2月5日 上午11:25:31
   	* @return http://192.168.255.1:8080/webService/interface?wsdl
   	* @version v1.0.0
   	 */
    public String getWebservciePath() {
		try {
			return "wsdl:"+getIpAndPort() + webServiceConfig.registeredAddress + webServiceConfig.publishAddress + "?wsdl";
		} catch (Exception e) {
			LOGGER.error("getWebservciePath error!!!"+e.getMessage());
		}
		return "";
    }
}
