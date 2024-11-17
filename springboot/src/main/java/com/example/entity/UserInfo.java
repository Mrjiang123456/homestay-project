package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 普通用户
*/
@Data
@TableName("t_user_info")
public class UserInfo {
    /** ID */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /** 用户名 */
    private String username;
    /** 昵称 */
    private String nickname;
    /** 密码 */
    private String password;
    /** 新密码 */
    @TableField(exist = false)
    private String newPassword;
    /** 电话 */
    private String phone;
    /** 邮箱 */
    private String email;
    /** 头像 */
    private String avatar;
    /** 角色标识 */
    private String role;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate updateTime;

    @TableField(exist = false)
    private String address;

    @TableField(exist = false)
    private String weather;
}