package com.example.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.PermissionInfo;
import com.example.entity.RoleInfo;
import com.example.mapper.PermissionMapper;
import com.example.mapper.RoleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RoleService extends ServiceImpl<RoleMapper, RoleInfo> {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private PermissionService permissionService;

}
