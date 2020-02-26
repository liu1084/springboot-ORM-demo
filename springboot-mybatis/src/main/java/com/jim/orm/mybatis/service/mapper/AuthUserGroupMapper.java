package com.jim.orm.mybatis.service.mapper;

import com.jim.orm.common.entity.mybatis.AuthUserGroup;
import org.apache.ibatis.annotations.Param;

public interface AuthUserGroupMapper {
    int deleteByPrimaryKey(@Param("userId") Long userId, @Param("groupId") Long groupId);

    int insert(AuthUserGroup record);

    int insertSelective(AuthUserGroup record);
}