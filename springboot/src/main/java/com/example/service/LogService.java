package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dto.SingleAttributeItem;
import com.example.entity.CommentInfo;
import com.example.entity.LogInfo;
import com.example.mapper.CommentMapper;
import com.example.mapper.LogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class LogService extends ServiceImpl<LogMapper, LogInfo> {

    @Resource
    private LogMapper logMapper;

    public List<LogInfo> selectLoginList(Integer userId) {
        return logMapper.selectLoginList(userId);
    }

    public List<SingleAttributeItem> selectActiveTrend(Integer userId) {
        return logMapper.selectActiveTrend(userId);
    }
}
