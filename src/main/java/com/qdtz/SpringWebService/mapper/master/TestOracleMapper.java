package com.qdtz.SpringWebService.mapper.master;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestOracleMapper {
    Integer getUserCount();
}
