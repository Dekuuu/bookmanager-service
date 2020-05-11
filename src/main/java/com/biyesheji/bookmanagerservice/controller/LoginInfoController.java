package com.biyesheji.bookmanagerservice.controller;

import com.biyesheji.bookmanagerservice.Enum.ApiResultEnum;
import com.biyesheji.bookmanagerservice.Enum.PreEnum;
import com.biyesheji.bookmanagerservice.annotation.LoginLogAnnotation;
import com.biyesheji.bookmanagerservice.dto.LoginInfoDto;
import com.biyesheji.bookmanagerservice.service.LoginInfoService;
import com.biyesheji.bookmanagerservice.utils.ApiResult;
import com.biyesheji.bookmanagerservice.utils.HttpServletRequestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Api(value="登录信息控制器")
@RequestMapping("/book/logininfo")
public class LoginInfoController {

    @Autowired
    private LoginInfoService loginInfoService;

    @ApiOperation("登录")
    @PostMapping("login")
    @LoginLogAnnotation(name = "登录日志")
    public ApiResult login(@RequestBody LoginInfoDto loginInfoDto, HttpServletResponse response){
        try{
            ApiResult apiResult = ApiResult.success(ApiResultEnum.SUCCESS.getIndex(), loginInfoService.login(loginInfoDto));
            Cookie cookie = new Cookie(PreEnum.LOGINED_PRE.getValue(),apiResult.getData().toString());
            cookie.setMaxAge(60*60*2);
            cookie.setPath("/");             //设置path，设置cookie的可使用路径，这里的路径是相对于应用服务器的跟布鲁而言的
            response.addCookie(cookie);
            return apiResult;
        }catch (Exception e){
            return ApiResult.success(ApiResultEnum.FAILED.getIndex(),e.getMessage());
        }
    }

    @ApiOperation("退出登录")
    @GetMapping("logout")
    @LoginLogAnnotation(name = "登录日志")
    public ApiResult logout(HttpServletRequest httpServletRequest , HttpServletResponse httpServletResponse){
        try{
            ApiResult apiResult = ApiResult.success(ApiResultEnum.SUCCESS.getIndex(), loginInfoService.logout());
            Cookie[] cookies = httpServletRequest.getCookies();
            for(Cookie c : cookies){
                if(PreEnum.LOGINED_PRE.getValue().equals(c.getName())){
                    c.setMaxAge(0);
                    c.setPath("/");
                    httpServletResponse.addCookie(c);
                    break ;
                }
            }
            return apiResult;
        }catch (Exception e){
            return ApiResult.success(ApiResultEnum.FAILED.getIndex(),e.getMessage());
        }
    }

    @ApiOperation("注册")
    @PostMapping("register")
    public ApiResult register(@RequestBody  LoginInfoDto loginInfoDto){
        try{
            return ApiResult.success(ApiResultEnum.SUCCESS.getIndex(),loginInfoService.register(loginInfoDto));
        }catch (Exception e){
            return ApiResult.success(ApiResultEnum.FAILED.getIndex(),e.getMessage());
        }
    }

    @ApiOperation("获取用户信息")
    @GetMapping("getUserInfo")
    public ApiResult getUserInfo(){
        try{
            return ApiResult.success(ApiResultEnum.SUCCESS.getIndex(),loginInfoService.queryByToken());
        }catch (Exception e){
            return ApiResult.success(ApiResultEnum.FAILED.getIndex(),e.getMessage());
        }
    }

    @ApiOperation("获取验证码")
    @GetMapping("getCode")
    public ApiResult getCode(String phoneNo){
        try{
            return ApiResult.success(ApiResultEnum.SUCCESS.getIndex(),loginInfoService.getCode(phoneNo));
        }catch (Exception e){
            return ApiResult.success(ApiResultEnum.FAILED.getIndex(),e.getMessage());
        }
    }

    @ApiOperation("邮箱发送邮件")
    @GetMapping("email")
    public void sendEmail(String email) {
        try{
           loginInfoService.email(email);
        }catch (Exception e){

        }
    }

    @ApiOperation("邮箱校验")
    @GetMapping("emailCheck")
    public ModelAndView emailCheck(String code) {
        try{
            loginInfoService.emailCheck(code);
            return  new ModelAndView("redirect:http://127.0.0.1:9999/bookservice-web/myAccount");
        }catch (Exception e){
            return  new ModelAndView("redirect:http://127.0.0.1:9999/bookservice-web/myAccount");
        }
    }

    @ApiOperation("修改用户密码")
    @PostMapping("updatePsw")
    public ApiResult updatePsw(@RequestBody LoginInfoDto loginInfoDto) {
        try{
            return ApiResult.success(ApiResultEnum.SUCCESS.getIndex(),loginInfoService.updateLoginInfoPsw(loginInfoDto));
        }catch (Exception e){
            return ApiResult.success(ApiResultEnum.SUCCESS.getIndex(),e.getMessage());
        }
    }

    @ApiOperation("修改用户邮箱")
    @PostMapping("updateEmail")
    public ApiResult updateEmail(@RequestBody LoginInfoDto loginInfoDto) {
        try{
            return ApiResult.success(ApiResultEnum.SUCCESS.getIndex(),loginInfoService.email(loginInfoDto.getEmail()));
        }catch (Exception e){
            return ApiResult.success(ApiResultEnum.SUCCESS.getIndex(),e.getMessage());
        }
    }
}
