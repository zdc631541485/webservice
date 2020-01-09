package com.qdtz.SpringWebService.service.Impl;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qdtz.SpringWebService.mapper.TestOracleMapper;
import com.qdtz.SpringWebService.service.TestOracleService;

/**
 * 
 * @version: v1.0.0
 * @Description: serviceName 对外发布的服务名,targetNamespace 指定你想要的名称空间，通常使用使用包名反转,
 *               endpointInterface 服务接口全路径, 指定做SEI（Service EndPoint
 *               Interface）服务端点接口
 * @author: ZDC
 * @date: 2020年1月9日 上午11:27:17
 */
@WebService(serviceName = "UserService", 
        targetNamespace = "http://service.demo.example.com")
@Component
public class TestOracleServiceImpl implements TestOracleService {
    

    @Autowired
    private TestOracleMapper testOracleMapper;
    
    @Override
    public Integer getUserCount() {
        return testOracleMapper.getUserCount();
    }

}
