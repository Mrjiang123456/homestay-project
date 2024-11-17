package com.example.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.Result;
import com.example.entity.LogInfo;
import com.example.entity.UserInfo;
import com.example.service.LogService;
import com.example.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 日志接口
 **/
@RestController
@RequestMapping("/log")
public class LogController {

    @Resource
    private LogService logService;

    /**
     * 新增或者编辑
     */
    @PostMapping("/add")
    public Result add(@RequestBody LogInfo logInfo) {
        if (StrUtil.isNotEmpty(logInfo.getNickname())){
            logService.saveOrUpdate(logInfo);
        }
        return Result.success();
    }

    /**
     * 批量删除
     */
    @PostMapping("/delete")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        logService.removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * 分页查询
     */
    @GetMapping("/page")
    public Result selectPage(@RequestParam() Integer pageNum,
                             @RequestParam() Integer pageSize) {
        QueryWrapper<LogInfo> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        Page<LogInfo> page = logService.page(new Page<>(pageNum, pageSize),wrapper);
        return Result.success(page);
    }

    /**
     * 登录历史记录
     */
    @GetMapping("/login-list")
    public Result selectLoginList(@RequestParam Integer id) {
        return Result.success(logService.selectLoginList(id));
    }


    /**
     * 最近30天活跃趋势
     */
    @GetMapping("/active-trend")
    public Result selectActiveTrend(@RequestParam Integer id) {
        return Result.success(logService.selectActiveTrend(id));
    }
}
