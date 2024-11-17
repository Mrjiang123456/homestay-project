package com.example.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.Result;
import com.example.entity.RoleInfo;
import com.example.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色接口
 **/
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody RoleInfo roleInfo) {
        roleService.saveOrUpdate(roleInfo);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @PostMapping("/delete")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        roleService.removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * 分页查询
     */
    @GetMapping("/page")
    public Result selectPage(@RequestParam() Integer pageNum,
                             @RequestParam() Integer pageSize) {
        Page<RoleInfo> page = roleService.page(new Page<>(pageNum, pageSize));
        return Result.success(page);
    }

    /**
     * 列表查询
     */
    @GetMapping("/list")
    public Result selectList() {
        return Result.success(roleService.list());
    }
}
