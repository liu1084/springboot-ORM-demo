package com.jim.orm.mybatis.service.mapper;

import com.jim.orm.common.entity.mybatis.AuthRole;

public interface AuthRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AuthRole record);

    int insertSelective(AuthRole record);

    AuthRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AuthRole record);

    int updateByPrimaryKey(AuthRole record);
}