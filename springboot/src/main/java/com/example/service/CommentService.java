package com.example.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.CommentInfo;
import com.example.entity.PermissionInfo;
import com.example.mapper.CommentMapper;
import com.example.mapper.PermissionMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CommentService extends ServiceImpl<CommentMapper, CommentInfo> {


    @Resource
    private CommentMapper commentMapper;

    public List<CommentInfo> selectCommentList() {
        List<CommentInfo> commentInfos = commentMapper.selectList(null);
        return selectCommentList(commentInfos);
    }

    public List<CommentInfo> selectCommentList(List<CommentInfo> commentInfos) {
        return getCommentTree(null, commentInfos);
    }

    private List<CommentInfo> getCommentTree(Integer id, List<CommentInfo> commentInfos) {
        List<CommentInfo> list = new ArrayList<>();
        for (CommentInfo commentInfo : commentInfos) {
            if(Objects.equals(commentInfo.getPid(), id)){
                // 遍历获取树的子节点
                List<CommentInfo> commentTree = getCommentTree(commentInfo.getId(), commentInfos);
                commentInfo.setChildren(commentTree);
                list.add(commentInfo);
            }
        }
        return list.stream().sorted(Comparator.comparing(CommentInfo::getId).reversed()).collect(Collectors.toList());
    }
}
