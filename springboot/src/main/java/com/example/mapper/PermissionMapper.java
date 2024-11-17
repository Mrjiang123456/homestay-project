package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.PermissionInfo;
import com.example.entity.RoleInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作permission相关数据接口
 */
@Mapper
public interface PermissionMapper extends BaseMapper<PermissionInfo> {


}
