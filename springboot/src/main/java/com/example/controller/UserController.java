package com.example.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.Result;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.UserInfo;
import com.example.exception.CustomException;
import com.example.service.UserService;
import com.example.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * 用户接口
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 根据token查询
     */
    @GetMapping("/info")
    public Result getUserInfo() {
        return Result.success(userService.getUserInfo());
    }
    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody UserInfo user) {
        if(null == user.getId() || StrUtil.isEmpty(String.valueOf(user.getId()))){
            userService.saveItem(user);
        }else{
            userService.updateById(user);
        }
        return Result.success();
    }

    /**
     * 批量删除
     */
    @PostMapping("/delete")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        userService.removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody UserInfo user) {
        userService.updateById(user);
        return Result.success();
    }

    /**
     * 分页查询
     */
    @GetMapping("/page")
    public Result selectPage(@RequestParam() Integer pageNum,
                             @RequestParam() Integer pageSize) {
        Page<UserInfo> page = userService.page(new Page<>(pageNum, pageSize));
        return Result.success(page);
    }
}