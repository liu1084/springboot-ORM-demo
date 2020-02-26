package com.jim.orm.mybatis.service.mapper;

import com.jim.orm.common.entity.mybatis.AuthGroup;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface AuthGroupMapper {
	int deleteByPrimaryKey(Long id);

	int insert(AuthGroup record);

	int insertSelective(AuthGroup record);

	AuthGroup selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(AuthGroup record);

	int updateByPrimaryKey(AuthGroup record);
}