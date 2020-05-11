package com.biyesheji.bookmanagerservice.service;

import com.biyesheji.bookmanagerservice.dto.LoginInfoDto;
import com.biyesheji.bookmanagerservice.entity.LoginInfo;

import java.util.List;

public interface LoginInfoService {
    /**
    * Description: 登录<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/15 10:00<br/>
    * @param:<br/>
    * @return:
    */
    public String login(LoginInfoDto loginInfoDto)throws Exception;

    /**
    * Description: 注册用户<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/15 10:01<br/>
    * @param:<br/>
    * @return:
    */
    public int register(LoginInfoDto loginInfoDto) throws Exception;

    /**
    * Description: 退出登录<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/4/20 16:08<br/>
    * @param:<br/>
    * @return:
    */
    public boolean logout() throws Exception;

    /**
    * Description: 修改用户密码<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/15 10:01<br/>
    * @param:<br/>
    * @return:
    */
    public int updateLoginInfoPsw(LoginInfoDto loginInfoDto) throws Exception;

    /**
    * Description: 获取验证码<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/16 10:36<br/>
    * @param:<br/>
    * @return:
    */
    public String getCode(String phoneNo) throws Exception;

    /**
    * Description: 邮箱发送邮件<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/16 10:37<br/>
    * @param:<br/>
    * @return:
    */
    public int email(String email) throws Exception;

    /**
    * Description: 邮箱校验<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/4/24 9:27<br/>
    * @param:<br/>
    * @return:
    */
    public void emailCheck(String code) throws Exception;

    /**
    * Description: 获取所有的用户名<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/23 8:15<br/>
    * @param:<br/>
    * @return:
    */
    public List<LoginInfo> queryAllUsers();

    /**
    * Description: 根据token获取用户信息<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/25 16:36<br/>
    * @param:<br/>
    * @return:
    */
    public LoginInfo queryByToken();
}
