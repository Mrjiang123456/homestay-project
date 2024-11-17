package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.RoleInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作role相关数据接口
 */
@Mapper
public interface RoleMapper extends BaseMapper<RoleInfo> {


}
