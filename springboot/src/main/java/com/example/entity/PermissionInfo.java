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
    import java.util.List;

    @Data
    @TableName("t_permission_info")
    public class PermissionInfo {

        @TableId(type = IdType.AUTO)
        private Integer Id;

        private String name;
        private String path;
        private Integer orders;
        private Integer pid;
        private String title;
        private String icon;
        private Boolean isShow;
        private String descr;
        private String xName;
        private String yName;


        @TableField(exist = false)
        private String component;

        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate createTime;

        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate updateTime;

        @TableField(exist = false)
        private Meta meta;

        @TableField(exist = false)
        private List<PermissionInfo> children;

        @Data
        public static class Meta {
            private String title;
            private String icon;
            private Boolean isShow;
            private String descr;
            private String xName;
            private String yName;
        }
    }
