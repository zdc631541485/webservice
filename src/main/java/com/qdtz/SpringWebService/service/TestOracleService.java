package com.qdtz.SpringWebService.service;

import javax.jws.WebService;

@WebService
public interface TestOracleService {
    
    Integer getUserCount();
    
    Integer getSlaveUserCount();
}
