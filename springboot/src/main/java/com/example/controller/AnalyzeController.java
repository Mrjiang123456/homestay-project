package com.example.controller;

import com.example.common.Result;
import com.example.service.AnalyzeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/analysis")
public class AnalyzeController {


    @Resource
    private AnalyzeService analyzeService;

    // 评分与消费分析
    @GetMapping("/analyze1")
    public Result selectConsumptionByRating(){
        return analyzeService.selectConsumptionByRating();
    }

    // 区域民宿数量分析
    @GetMapping("/analyze2")
    public Result selectTopDistrictsByHomestayCount(){
        return analyzeService.selectTopDistrictsByHomestayCount();
    }

    // 户型数量分析
    @GetMapping("/analyze3")
    public Result selectLayoutCount(){
        return analyzeService.selectLayoutCount();
    }

    // 价格与消费的分析
    @GetMapping("/analyze4")
    public Result selectPriceConsumptionRelation(){
        return analyzeService.selectPriceConsumptionRelation();
    }

    // 收藏与消费的分析
    @GetMapping("/analyze5")
    public Result selectRelationBetweenFavAndConsumption(){
        return analyzeService.selectRelationBetweenFavAndConsumption();
    }

    // 民宿词云标签分析
    @GetMapping("/analyze6")
    public Result selectHomestayTagInfos(){
        return analyzeService.selectHomestayTagInfos();
    }

}
