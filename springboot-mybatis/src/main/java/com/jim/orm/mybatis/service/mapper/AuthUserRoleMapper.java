package com.jim.orm.mybatis.service.mapper;

import com.jim.orm.common.entity.mybatis.AuthUserRole;
import org.apache.ibatis.annotations.Param;

public interface AuthUserRoleMapper {
    int deleteByPrimaryKey(@Param("roleId") Long roleId, @Param("userId") Long userId);

    int insert(AuthUserRole record);

    int insertSelective(AuthUserRole record);
}