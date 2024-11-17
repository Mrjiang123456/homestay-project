package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 操作user相关数据接口
*/
@Mapper
public interface UserMapper extends BaseMapper<UserInfo> {

    @Select("select code from t_city_code_info WHERE city like concat('%',#{city},'%')")
    String selectCodeByCity(String city);

    @Select("select * from t_user_info where username = #{username}")
    UserInfo selectByUsername(String username);

//    /**
//      * 新增
//    */
//    int insert(UserInfo user);
//
//    /**
//      * 删除
//    */
//    int deleteById(Integer id);
//
//    /**
//      * 修改
//    */
//    int updateById(UserInfo user);
//
//    /**
//      * 根据ID查询
//    */
//    UserInfo selectById(Integer id);
//
//    /**
//      * 查询所有
//    */
//    List<UserInfo> selectAll(UserInfo user);


}