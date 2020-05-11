package com.biyesheji.bookmanagerservice.serviceImpl;

import com.biyesheji.bookmanagerservice.Enum.ApiResultEnum;
import com.biyesheji.bookmanagerservice.Enum.PreEnum;
import com.biyesheji.bookmanagerservice.dto.LoginInfoDto;
import com.biyesheji.bookmanagerservice.entity.LoginInfo;
import com.biyesheji.bookmanagerservice.mapper.LoginInfoMapper;
import com.biyesheji.bookmanagerservice.service.LoginInfoService;
import com.biyesheji.bookmanagerservice.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class LoginInfoServiceImpl implements LoginInfoService {

    @Autowired
    private LoginInfoMapper loginInfoMapper;
    @Autowired
    private EmailUtil emailUtil;

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public String login(LoginInfoDto loginInfoDto) throws Exception{
        loginInfoDto.setPassword(MD5Util.encode(loginInfoDto.getPassword()));
        LoginInfo loginInfo=loginInfoMapper.queryLoginInfo(loginInfoDto);
        if(loginInfo == null){
            throw new Exception("密码错误!");
        }
        loginInfoDto.setVersion(loginInfo.getVersion()+1);
        loginInfoDto.setLoginState(ApiResultEnum.LOGINED.getIndex());
        loginInfoMapper.updateLoginInfo(loginInfoDto);
        String token = UUID.randomUUID().toString();
        RedisUtils.setObj(token,loginInfo,60*60*2);
        return token;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public int register(LoginInfoDto loginInfoDto) throws Exception{
        String token=PreEnum.PHONECODE.getValue()+loginInfoDto.getUserName();
//        校验验证码
        String code=RedisUtils.getObj(token,String.class);
        if(code == null || "".equals(code)){
            throw new Exception("验证码不存在");
        }else{
            if(!code.equals(loginInfoDto.getCode())){
                throw new Exception("验证码错误!");
            }else{
//                查询用户是否已经存在
                List<LoginInfo> list= loginInfoMapper.queryByName(loginInfoDto.getUserName());
                if(list != null && list.size() > 0){
//            已存在记录
                    throw new Exception("该用户已存在!");
                }
                loginInfoDto.setPassword(MD5Util.encode(loginInfoDto.getPassword()));
                loginInfoDto.setVersion(1);                     //版本号初始化
                loginInfoDto.setLoginState(ApiResultEnum.NOT_LOGIN.getIndex());         //初始化登录状态
                loginInfoDto.setUserType(ApiResultEnum.USER.getIndex());                //初始化用户类型
                try{
                    return loginInfoMapper.insertLoginInfo(loginInfoDto);
                }catch (Exception e){
                    throw new Exception("注册失败!");
                }
            }
        }
    }

    @Override
    public boolean logout() throws Exception{
        LoginInfo loginInfo = RedisUtils.getObj(HttpServletRequestUtil.getCookieToken(), LoginInfo.class);
        if(loginInfo == null ){
            throw new Exception("用户为空!");
        }
        return RedisUtils.del(HttpServletRequestUtil.getCookieToken());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public int updateLoginInfoPsw(LoginInfoDto loginInfoDto) throws Exception{
        LoginInfo loginInfo = RedisUtils.getObj(HttpServletRequestUtil.getCookieToken(), LoginInfo.class);
        if(loginInfo == null){
            throw new Exception("请先登录");
        }
        loginInfoDto.setUserName(loginInfo.getUserName());
        loginInfoDto.setOriginPsw(MD5Util.encode(loginInfoDto.getOriginPsw()));
        loginInfoDto.setNewPsw(MD5Util.encode(loginInfoDto.getNewPsw()));
        try{
            int i = loginInfoMapper.updateLoginInfo(loginInfoDto);
//          清空redis账户信息，让用户重新登录
            RedisUtils.del(HttpServletRequestUtil.getCookieToken());
            return i;
        }catch (Exception e){
            throw new Exception("修改密码失败");
        }

    }

    @Override
    public String getCode(String phoneNo) throws Exception{
//        发送短信前先校验是否已经发送
        String token= PreEnum.PHONECODE.getValue() +phoneNo;
        String code=RedisUtils.getObj(token,String.class);
        if(code==null ||  "".equals(code)){
            String phoneCode=new SendPhoneCode().sendPhoneCode(phoneNo,token);
            RedisUtils.setObj(token,phoneCode,60*20);
            return phoneCode;
        }else{
            throw new Exception("验证码已经发送了!");
        }

    }

    @Override
    public int email(String email) throws Exception{
        LoginInfo loginInfo = RedisUtils.getObj(HttpServletRequestUtil.getCookieToken(),LoginInfo.class);
        if(loginInfo == null){
            throw new Exception("用户未登录!");
        }
        String code = UUID.randomUUID().toString();
        RedisUtils.setObj(code,loginInfo.getUserName()+"-"+email,60*60*2);
        emailUtil.sendHtmlMail(email,"xxx图书信息管理系统邮箱验证",
                "<html><head></head><body><a href='http://127.0.0.1:9999/book/logininfo/emailCheck?code="+code+"'>激活邮箱</a></body></html>");
        return 1;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void emailCheck(String code) throws Exception{
        LoginInfo loginInfo = RedisUtils.getObj(HttpServletRequestUtil.getCookieToken(),LoginInfo.class);
        if(loginInfo == null){
            throw new Exception("用户未登录!");
        }

//        校验用户身份
        String obj = RedisUtils.getObj(code, String.class);
        if(obj == null || "".equals(obj)){
            throw new Exception("邮箱验证已过期!");
        }

        String[] split = obj.split("-");
        if(loginInfo.getUserName().equals(split[0])){
            loginInfo.setEmail(split[1]);
        }else{
            throw new Exception("用户信息异常!");
        }
        try{
            LoginInfoDto loginInfoDto = new LoginInfoDto();
            loginInfoDto.setUserName(loginInfo.getUserName());
            loginInfoDto.setEmail(loginInfo.getEmail());
            loginInfoMapper.updateLoginInfo(loginInfoDto);
//            验证成功则同步修改redis里面用户的信息
            RedisUtils.setObj(HttpServletRequestUtil.getCookieToken(),loginInfo,2*60*60);
        }catch(Exception e){
            e.printStackTrace();
            throw new Exception("邮箱验证失败");
        }
    }

    @Override
    public List<LoginInfo> queryAllUsers() {
        return loginInfoMapper.queryAllUsers();
    }

    @Override
    public LoginInfo queryByToken() {
        return RedisUtils.getObj(HttpServletRequestUtil.getCookieToken(),LoginInfo.class);
    }
}
