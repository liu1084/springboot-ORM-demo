package com.jim.orm.mybatis.service.mapper;

import com.jim.orm.common.entity.mybatis.AuthPermissionResource;
import org.apache.ibatis.annotations.Param;

public interface AuthPermissionResourceMapper {
    int deleteByPrimaryKey(@Param("permissionId") Long permissionId, @Param("resourceId") Long resourceId);

    int insert(AuthPermissionResource record);

    int insertSelective(AuthPermissionResource record);
}