package com.qdtz.SpringWebService.interceptor;


import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qdtz.SpringWebService.config.WebServiceConfig;
import com.qdtz.SpringWebService.mapper.master.TestOracleMapper;

@Component
public class IPaddressIntercepter extends AbstractPhaseInterceptor<SoapMessage> {
	
    private static final Logger LOG = LoggerFactory.getLogger(WebServiceConfig.class);
    
	@Autowired
    private TestOracleMapper testOracleMapper;
	
	public IPaddressIntercepter() {
		super(Phase.PRE_INVOKE);
	}
	
	@Override
	public void handleMessage(SoapMessage message) throws Fault {
		HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
		String ipAddress = getIP(request);
		boolean b = isHaveIp(ipAddress);
		if (!b)
			throw new Fault(new IllegalAccessException("The client is not allowed!(禁止访问)"));
		LOG.info("The client ip["+ipAddress+"] is allowed !!!");
	}
	
	private boolean isHaveIp(String ip) {
		try {
			int conunt = testOracleMapper.getUserCount();
			boolean isHaveTheIp = (conunt == 0)?false:true;
			return isHaveTheIp;
		}catch (Exception e) {
		    LOG.error("The IP check error:"+e.getMessage());
			return false;
		}

	}
	
	private String getIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
