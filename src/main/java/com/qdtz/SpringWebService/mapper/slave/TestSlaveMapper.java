package com.qdtz.SpringWebService.mapper.slave;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestSlaveMapper {
    
    Integer getSlaveUserCount();

    Integer updateSlaveUserLastLogin();
}
