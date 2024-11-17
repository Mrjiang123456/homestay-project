package com.example.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.Result;
import com.example.entity.PermissionInfo;
import com.example.entity.RoleInfo;
import com.example.mapper.PermissionMapper;
import org.apache.catalina.util.ToStringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PermissionService extends ServiceImpl<PermissionMapper, PermissionInfo> {

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private RoleService roleService;

    public List<PermissionInfo> selectlist() {
        List<PermissionInfo> permissionInfos = list();
        return selectlist(permissionInfos);
    }

    public List<PermissionInfo> selectlist(List<PermissionInfo> permissionInfos) {
        return getPermissionTree(null, permissionInfos);
    }

    private List<PermissionInfo> getPermissionTree(Integer id, List<PermissionInfo> permissionInfos) {
        List<PermissionInfo> list = new ArrayList<>();
        for (PermissionInfo permissionInfo : permissionInfos) {
            if(Objects.equals(permissionInfo.getPid(), id)){

//              创建 Meta 对象并设置
                PermissionInfo.Meta meta = new PermissionInfo.Meta();
                meta.setTitle(permissionInfo.getTitle());
                meta.setIcon(permissionInfo.getIcon());
                meta.setIsShow(permissionInfo.getIsShow());
                meta.setDescr(permissionInfo.getDescr());
                meta.setXName(permissionInfo.getXName());
                meta.setYName(permissionInfo.getYName());
                permissionInfo.setMeta(meta);

//                遍历获取树的子节点
                List<PermissionInfo> permissionTree = getPermissionTree(permissionInfo.getId(), permissionInfos);
                permissionInfo.setChildren(permissionTree);
                list.add(permissionInfo);
            }
        }
        return list.stream().sorted(Comparator.comparing(PermissionInfo::getOrders)).collect(Collectors.toList());
    }

    public List<PermissionInfo> menuTree(String flag){

        QueryWrapper<RoleInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("flag",flag);
        RoleInfo roleInfo = roleService.getOne(queryWrapper);

        String permissionIds = roleInfo.getPermissionIds();
        String[] ids = permissionIds.split(",");

        List<PermissionInfo> all = new ArrayList<>();
        List<PermissionInfo> PermissionInfos = this.list();
        for (PermissionInfo info : PermissionInfos) {
            for (String id : ids) {
                if (info.getId().equals(Integer.valueOf(id))){
                    all.add(info);
                }
            }
        }
        return this.selectlist(all);
    }
}
