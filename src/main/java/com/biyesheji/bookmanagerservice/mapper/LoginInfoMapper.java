package com.biyesheji.bookmanagerservice.mapper;

import com.biyesheji.bookmanagerservice.dto.LoginInfoDto;
import com.biyesheji.bookmanagerservice.entity.LoginInfo;

import javax.websocket.server.PathParam;
import java.util.List;

public interface LoginInfoMapper {

    /**
    * Description: 添加用户<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/14 15:44<br/>
    * @param:<br/>
    * @return:
    */
    public int insertLoginInfo(LoginInfoDto loginInfoDto);

    /**
    * Description: 查询登录信息<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/15 9:52<br/>
    * @param:<br/>
    * @return:
    */
    public LoginInfo queryLoginInfo(LoginInfoDto loginInfoDto);

    /**
    * Description: 修改登录信息<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/15 9:53<br/>
    * @param:<br/>
    * @return:
    */
    public int updateLoginInfo(LoginInfo loginInfo);

    /**
    * Description: 查询所有的已经通过邮箱验证的用户名<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/3/23 8:14<br/>
    * @param:<br/>
    * @return:
    */
    public List<LoginInfo> queryAllUsers();

    /**
    * Description: 分页查询所有用户<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/4/9 17:32<br/>
    * @param:<br/>
    * @return:
    */
    public List<LoginInfo> queryByPage(LoginInfoDto loginInfoDto);

    /**
    * Description: 查询所有用户信息数量<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/4/9 17:40<br/>
    * @param:<br/>
    * @return:
    */
    public int queryCountsByPage(LoginInfoDto loginInfoDto);

    /**
    * Description: 根据名字查询用户<br/>
    * @author: wuwenguang<br/>
    * @date: 2020/4/9 23:05<br/>
    * @param:<br/>
    * @return:
    */
    public List<LoginInfo> queryByName(@PathParam("userName") String userName);
}
