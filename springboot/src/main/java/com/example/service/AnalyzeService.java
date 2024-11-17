package com.example.service;


import com.example.common.Result;
import com.example.mapper.AnalyzeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AnalyzeService {

    @Resource
    private AnalyzeMapper analyzeMapper;

    public Result selectConsumptionByRating() {
        return Result.success(analyzeMapper.selectConsumptionByRating());
    }
    
    public Result selectTopDistrictsByHomestayCount() {
        return Result.success(analyzeMapper.selectTopDistrictsByHomestayCount());
    }

    public Result selectLayoutCount() {
        return Result.success(analyzeMapper.selectLayoutCount());
    }

    public Result selectPriceConsumptionRelation() {
        return Result.success(analyzeMapper.selectPriceConsumptionRelation());
    }

    public Result selectRelationBetweenFavAndConsumption() {
        return Result.success(analyzeMapper.selectRelationBetweenFavAndConsumption());
    }

    public Result selectHomestayTagInfos() {
        return Result.success(analyzeMapper.selectHomestayTagInfos());
    }
}
