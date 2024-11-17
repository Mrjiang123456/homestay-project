package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.UserInfo;
import com.example.exception.CustomException;
import com.example.mapper.SystemMapper;
import com.example.mapper.UserMapper;
import com.example.utils.AddressUtils;
import com.example.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
public class UserService extends ServiceImpl<UserMapper, UserInfo> {

    @Resource
    private UserMapper userMapper;

    /**
     * 登录
     */
    public String login(UserInfo userInfo) {
        UserInfo dbUserInfo = getUserInfoByName(userInfo.getUsername());
        String address = AddressUtils.getAddress();
        if (ObjectUtil.isNull(dbUserInfo)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (!userInfo.getPassword().equals(dbUserInfo.getPassword())) {
            throw new CustomException(ResultCodeEnum.USER_ACCOUNT_ERROR);
        }
        // 生成token
        String tokenData = dbUserInfo.getId()+"";
        return TokenUtils.createToken(tokenData, address);
    }

    /**
     * 注册
     */
    public void register(UserInfo userInfo) {
        UserInfo info = userMapper.selectByUsername(userInfo.getUsername());
        if(ObjectUtil.isNotNull(info)){
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        if("admin".equals(userInfo.getUsername())){
            userInfo.setRole("ADMIN");
        }else{
            userInfo.setRole("USER");
        }
        userInfo.setAvatar("https://lyr-oss.oss-cn-beijing.aliyuncs.com/avatars/moren.png");
        userInfo.setNickname(userInfo.getUsername());
        userMapper.insert(userInfo);
    }

    /**
     * 修改密码
     */
    public void updatePassword(UserInfo userInfo) {
        UserInfo dbUserInfo = getUserInfoByName(userInfo.getUsername());
        if (ObjectUtil.isNull(dbUserInfo)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (!userInfo.getPassword().equals(dbUserInfo.getPassword())) {
            throw new CustomException(ResultCodeEnum.PARAM_PASSWORD_ERROR);
        }
        dbUserInfo.setPassword(userInfo.getNewPassword());
        userMapper.updateById(dbUserInfo);
    }

    public UserInfo selectById(Integer id){
        return userMapper.selectById(id);
    }

    public UserInfo getUserInfoByName(String username){
        QueryWrapper<UserInfo> infoQueryWrapper = new QueryWrapper<>();
        infoQueryWrapper.eq("username",username);
        return this.getOne(infoQueryWrapper);
    }

    public void saveItem(UserInfo user) {
        UserInfo dbUserInfo = getUserInfoByName(user.getUsername());

        // 存在相同的用户
        if(dbUserInfo != null){
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }

        // 设置默认昵称
        if(null == user.getNickname()){
            user.setNickname(user.getUsername());
        }

        // 设置默认密码
        if(null == user.getPassword()){
            user.setPassword("123456");
        }

        // 设置默认头像
        if(null == user.getAvatar() || StrUtil.isEmpty(user.getAvatar())){
            user.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        }

        // 设置默认角色
        if(null == user.getRole()){
            user.setRole("USER");
        }

        userMapper.insert(user);
    }


    public String getLocalIp(){
        String ip = "";
        try {
            ip= HttpUtil.get("http://checkip.amazonaws.com").trim();
        }catch (Exception ex){
            System.err.println("ip获取失败。。。。。。。。。。。。。。。。");;
        }
        return ip;
    }

    static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36";

    public String getProvinceByIp(String ip){
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("ip", ip);
        paramMap.put("json", "true");
        String response = "";
        try {
            response = HttpRequest.get("http://whois.pconline.com.cn/ipJson.jsp")
                    .header(Header.USER_AGENT, USER_AGENT)
                    .form(paramMap)//表单内容
                    .timeout(3000)//超时，毫秒
                    .execute().body().trim();
        }catch (Exception ex){
            System.err.println("ip解析失败。。。。。。。。。。。。。。。。");;
        }
        return response;
    }

    public  String getCodeByCity(String city){
        return userMapper.selectCodeByCity(city);
    }

    public  String getWeatherByCode(String code){
        String response = "";
        try {
            response = HttpUtil.get("http://t.weather.itboy.net/api/weather/city/"+code)
                    .trim();
        }catch (Exception ex){
            System.err.println("天气获取失败。。。。。。。。。。。。。。。。");;
        }

        if(StrUtil.isNotEmpty(response)){
            JSONObject jsonObject = JSONUtil.parseObj(response);
            return jsonObject.getJSONObject("data").getJSONArray("forecast").getStr(0);
        }else{
            return response;
        }
    }

    public UserInfo getUserInfo() {

        UserInfo userInfo = TokenUtils.getCurrentUser();
        String ip;
        String address = "{}";
        String weather = "{}";

        /**
         * 获取本地的ip地址，解析出城市，获取城市对应的天气，登录接口会慢1-2秒
         */
        try {
            ip = getLocalIp();
            if(StrUtil.isNotEmpty(ip)){
                address = getProvinceByIp(ip);
                if(StrUtil.isNotEmpty(address)){
                    JSONObject jsonObject = JSONUtil.parseObj(address);
                    String city = jsonObject.getStr("city").replace("市", "");
                    if(StrUtil.isNotEmpty(city)){
                        String code = getCodeByCity(city);
                        weather = getWeatherByCode(code);
                    }
                }
            }
        } catch (Exception ex){
            log.error("获取位置或者天气失败");
        }

        userInfo.setAddress(address);
        userInfo.setWeather(weather);

        return userInfo;
    }
}
