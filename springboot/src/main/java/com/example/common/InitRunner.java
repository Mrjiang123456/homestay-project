package com.example.common;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import com.example.mapper.SystemMapper;
import com.example.utils.AddressUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class InitRunner implements ApplicationRunner {

    @Resource
    SystemMapper systemMapper;

    @Override
    public void run(ApplicationArguments args) {
        ThreadUtil.execAsync(() -> {
            try {
                String str = systemMapper.selectValue();
                if(StrUtil.isEmpty(str)){
                    String address = AddressUtils.getAddress();
                    systemMapper.updateValue(address);
                }
            } catch (Exception e) {
                log.warn("启动优化失败", e);
            }
        });
    }
}