package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("t_role_info")
public class RoleInfo {
    @TableId(type = IdType.AUTO)
    private Integer Id;
    private String name;
    private String description;
    private String flag;
    private String permissionIds;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate updateTime;

}
