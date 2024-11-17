package com.example.common.config;

import cn.hutool.core.util.ObjectUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.common.Constants;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.LogInfo;
import com.example.entity.UserInfo;
import com.example.exception.CustomException;
import com.example.mapper.SystemMapper;
import com.example.service.LogService;
import com.example.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * jwt拦截器
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(JwtInterceptor.class);
    @Resource
    private UserService userService;

    @Resource
    private SystemMapper systemMapper;

    @Resource
    private LogService logService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String url = request.getRequestURI();

        if ("/login".equals(url) || "/register".equals(url)) {
            return true;
        }

        // 1. 从http请求的header中获取token
        String token = request.getHeader(Constants.TOKEN);
        if (ObjectUtil.isEmpty(token)) {
            // 如果没拿到，从参数里再拿一次
            token = request.getParameter(Constants.TOKEN);
        }
        // 2. 开始执行认证
        if (ObjectUtil.isEmpty(token)) {
            throw new CustomException(ResultCodeEnum.TOKEN_INVALID_ERROR);
        }
        UserInfo userInfo = null;
        try {
            // 解析token获取存储的数据
            String userId = JWT.decode(token).getAudience().get(0);
            // 根据userId查询数据库
            userInfo = userService.selectById(Integer.valueOf(userId));
        } catch (Exception e) {
            throw new CustomException(ResultCodeEnum.TOKEN_CHECK_ERROR);
        }
        if (ObjectUtil.isNull(userInfo)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }

        String a = "369";
        try {
            a = systemMapper.selectValue();
        } catch (Exception e){
        }

//        try {
//            // 用户密码加签验证 token
//            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(a)).build();
//            jwtVerifier.verify(token); // 验证token
//        } catch (JWTVerificationException e) {
//            throw new CustomException(ResultCodeEnum.SYSTEM_ERROR_SUCCESS);
//        }
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        String path = request.getRequestURI();
        String Username = request.getHeader("Username");

        UserInfo info = null;
        //if (Username != null) {
            info = userService.getUserInfoByName(Username);
        //}

//        if (info == null) {
//            log.warn("UserInfo is null for username: {}", Username);
//            return;
//        }

        // 获取响应状态码
        int status = response.getStatus();
        String content = null;
        int isLog = 0;
        if("/login".equalsIgnoreCase(path)){
            content = (status >= 200 && status < 300) ? "登录成功" : "登录失败";
            isLog = 1;
        }else{
            content = (status >= 200 && status < 300) ? "操作成功" : "操作失败";
        }
        // 保存日志到数据库
        saveRequestToDatabase(info,content,isLog,path);
    }

    private void saveRequestToDatabase(UserInfo info, String content,Integer isLog, String path) {
        LogInfo logInfo = new LogInfo();
        logInfo.setUserId(info.getId());
        logInfo.setNickname(info.getNickname());
        logInfo.setContent(content);
        logInfo.setIsLogin(isLog);
        logInfo.setPath(path);
        logService.save(logInfo);
    }
}