package com.jim.orm.mybatis.service.mapper;

import com.jim.orm.common.entity.mybatis.AuthResource;

public interface AuthResourceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AuthResource record);

    int insertSelective(AuthResource record);

    AuthResource selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AuthResource record);

    int updateByPrimaryKey(AuthResource record);
}