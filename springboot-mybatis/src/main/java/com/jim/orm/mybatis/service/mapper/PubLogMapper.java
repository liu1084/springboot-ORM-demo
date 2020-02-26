package com.jim.orm.mybatis.service.mapper;

import com.jim.orm.common.entity.mybatis.PubLog;

public interface PubLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PubLog record);

    int insertSelective(PubLog record);

    PubLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PubLog record);

    int updateByPrimaryKeyWithBLOBs(PubLog record);

    int updateByPrimaryKey(PubLog record);
}