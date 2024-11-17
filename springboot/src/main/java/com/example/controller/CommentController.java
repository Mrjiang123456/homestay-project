package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.Result;
import com.example.entity.CommentInfo;
import com.example.service.CommentService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    @GetMapping("/list")
    public Result getListInfo(){
        return Result.success(commentService.selectCommentList());
    }

    @PostMapping("/add")
    public Result saveOrUpdateInfo(@RequestBody CommentInfo commentInfo){
        return Result.success(commentService.saveOrUpdate(commentInfo));
    }

    @DeleteMapping("/delete")
    public Result deleteInfo(@RequestParam Integer id){
        QueryWrapper<CommentInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id).or().eq("pid",id);
        return Result.success(commentService.remove(queryWrapper));
    }
}
