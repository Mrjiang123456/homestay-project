package com.example.controller;


import com.example.common.Result;
import com.example.entity.PermissionInfo;
import com.example.entity.UserInfo;
import com.example.mapper.SystemMapper;
import com.example.service.PermissionService;
import com.example.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody PermissionInfo permissionInfo) {
        permissionService.saveOrUpdate(permissionInfo);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @PostMapping("/delete")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        permissionService.removeBatchByIds(ids);
        return Result.success();
    }


    /**
     * 权限列表
     */
    @GetMapping("/list")
    public Result getPermissionList(){
        List<PermissionInfo> list = permissionService.selectlist();
        return Result.success(list);
    }


    /**
     * 权限树
     */
    @GetMapping("/menu-tree")
    public Result selectMenuTree(){
        UserInfo userInfo = TokenUtils.getCurrentUser();
        return Result.success(permissionService.menuTree(userInfo.getRole()));
    }


    /**
     * 权限数量
     */
    @GetMapping("/count")
    public Result getPermissionCount(){
        long count = permissionService.count();
        return Result.success(count);
    }
}
