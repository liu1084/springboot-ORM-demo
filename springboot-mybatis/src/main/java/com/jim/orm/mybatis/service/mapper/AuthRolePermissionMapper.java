package com.jim.orm.mybatis.service.mapper;

import com.jim.orm.common.entity.mybatis.AuthRolePermission;
import org.apache.ibatis.annotations.Param;

public interface AuthRolePermissionMapper {
    int deleteByPrimaryKey(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);

    int insert(AuthRolePermission record);

    int insertSelective(AuthRolePermission record);
}