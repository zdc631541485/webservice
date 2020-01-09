package com.qdtz.SpringWebService.interceptor;


import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qdtz.SpringWebService.mapper.TestOracleMapper;

@Component
public class MyIntercepter extends AbstractPhaseInterceptor<SoapMessage> {
	
	@Autowired
    private TestOracleMapper testOracleMapper;
	
	public MyIntercepter() {
		super(Phase.PRE_INVOKE);
		
	}
	
	@Override
	public void handleMessage(SoapMessage message) throws Fault {
		HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
		String ipAddress = getIP(request);
		boolean b = ishave(ipAddress);
		if (!b) {
			throw new Fault(new IllegalAccessException("the client is not allowed!(禁止访问)"));
		}
	}
	
	private boolean ishave(String ip) {
		try {
			int conunt = testOracleMapper.getUserCount();
			boolean ishave = conunt == 0?false:true;
			return ishave;
		}catch (Exception e) {
			e.printStackTrace();
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
