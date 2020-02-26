package com.jim.orm.mybatis.service.mapper;

import com.jim.orm.common.entity.mybatis.AuthGroupRole;
import org.apache.ibatis.annotations.Param;

public interface AuthGroupRoleMapper {
    int deleteByPrimaryKey(@Param("roleId") Long roleId, @Param("groupId") Long groupId);

    int insert(AuthGroupRole record);

    int insertSelective(AuthGroupRole record);
}