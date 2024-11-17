package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName("t_comment_info")
public class CommentInfo {

    @TableId(type = IdType.AUTO)
    private Integer id; // 自增ID

    private Integer likeNum; // 点赞数

    private String text; // 内容

    private Integer pid; // 父级id

    private Integer senderId; // 发送者id

    private String senderName; // 发送者名称

    private String senderAvatar; // 发送者头像

    private String senderProvince; // 发送者省份

    private Integer receiverId; // 接收者id

    private String receiverName; // 接收者名称

    private String receiverAvatar; // 接收者头像

    private String receiverProvince; // 接收者省份

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime; // 创建时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime; // 更新时间

    @TableField(exist = false)
    private List<CommentInfo> children;
}
