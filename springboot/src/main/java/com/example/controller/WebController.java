package com.example.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.example.common.Result;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.UserInfo;
import com.example.exception.CustomException;
import com.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 基础前端接口
 */
@RestController
public class WebController {

    @Resource
    private UserService userService;

    @GetMapping("/")
    public Result hello() {
        return Result.success("访问成功");
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public Result login(@RequestBody UserInfo userInfo) {
        if (ObjectUtil.isEmpty(userInfo.getUsername()) || ObjectUtil.isEmpty(userInfo.getPassword())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        String token = userService.login(userInfo);
        return Result.success(token);
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    public Result register(@RequestBody UserInfo userInfo) {
        if (StrUtil.isBlank(userInfo.getUsername()) || StrUtil.isBlank(userInfo.getPassword())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        userService.register(userInfo);
        return Result.success();
    }

    /**
     * 修改密码
     */
    @PutMapping("/update-password")
    public Result updatePassword(@RequestBody UserInfo userInfo) {
        if (StrUtil.isBlank(userInfo.getUsername()) || StrUtil.isBlank(userInfo.getPassword())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        userService.updatePassword(userInfo);
        return Result.success();
    }
}