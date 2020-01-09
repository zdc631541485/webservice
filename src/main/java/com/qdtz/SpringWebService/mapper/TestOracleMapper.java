package com.qdtz.SpringWebService.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestOracleMapper {
    Integer getUserCount();
}
