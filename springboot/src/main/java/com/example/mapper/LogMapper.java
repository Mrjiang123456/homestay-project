package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.dto.SingleAttributeItem;
import com.example.entity.LogInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LogMapper extends BaseMapper<LogInfo> {


    @Select("SELECT * FROM t_log_info WHERE is_login = 1 AND user_id = #{userId} AND nickname is not null ORDER BY id DESC LIMIT 5")
    List<LogInfo> selectLoginList(Integer userId);


    @Select("SELECT " +
            "    DATE_FORMAT(create_time, '%Y-%m-%d') AS name, " +
            "    COUNT(1) AS value " +
            "FROM " +
            "    t_log_info " +
            "WHERE " +
            "  user_id = #{userId} AND create_time >= DATE_SUB(CURDATE(), INTERVAL 1 MONTH) " +
            "GROUP BY " +
            "    name " +
            "ORDER BY " +
            "    name;")
    List<SingleAttributeItem> selectActiveTrend(Integer userId);

}
