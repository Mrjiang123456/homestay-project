package com.example.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SystemMapper {

    @Update("UPDATE sys.sys_config SET value = #{str} WHERE variable = 'statement_dev'")
    void updateValue(String str);


    @Select("SELECT value FROM sys.sys_config WHERE  variable = 'statement_dev'")
    String selectValue();
}
