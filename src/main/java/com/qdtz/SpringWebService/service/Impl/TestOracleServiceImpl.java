package com.qdtz.SpringWebService.service.Impl;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.qdtz.SpringWebService.mapper.master.TestOracleMapper;
import com.qdtz.SpringWebService.mapper.slave.TestSlaveMapper;
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
  
    @Autowired
    private TestSlaveMapper testSlaveMapper;
    
    @Override
    public Integer getUserCount() {
        return testOracleMapper.getUserCount();
    }

    @Override
    public Integer getSlaveUserCount() {
        return testSlaveMapper.getSlaveUserCount();
    }

    @Override
    @Transactional(rollbackFor = Exception.class,transactionManager="masterTransactionManager")
    public Integer updateUserLastLogin() {
        int rows = testOracleMapper.updateUserLastLogin();
        int t = 1/0;
        return rows;
    }

    @Override
    @Transactional(rollbackFor = Exception.class,transactionManager="slaveTransactionManager")
    public Integer updateSlaveUserLastLogin() {
        int rows = testSlaveMapper.updateSlaveUserLastLogin();
        int t = 1/0;
        return rows;
    }

}
