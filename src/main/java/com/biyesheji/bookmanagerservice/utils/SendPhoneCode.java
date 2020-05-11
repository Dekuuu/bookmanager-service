package com.biyesheji.bookmanagerservice.utils;

import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 发送手机验证码
 * @author HP
 *
 */
@Component
public class SendPhoneCode {
	@Autowired
	private SessionUtil sessionUtil;
	public String sendPhoneCode(String phonenum,String token)  {
		//		调用第三方阿里云短信API			三网合一
		String host = "https://fesms.market.alicloudapi.com";
		String path = "/sms/";
		String method = "GET";
		String appcode = "a4428619a66a43a3b77b3949d83b9b8d";
		String code =CodeUtil.CreateRandowCode();				//创建随机的6位数短信验证码
		Map<String, String> headers = new HashMap<String, String>();
		//最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		Map<String, String> querys = new HashMap<String, String>();
		querys.put("code", code);	
		querys.put("phone", phonenum);
		querys.put("skin", "1");
		querys.put("sign", "175622");
		//JDK 1.8示例代码请在这里下载：  http://code.fegine.com/Tools.zip
		/**
		 * 重要提示如下:
		 * HttpUtils请从
		 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
		 * 或者直接下载：
		 * http://code.fegine.com/HttpUtils.zip
		 * 下载
		 *
		 * 相应的依赖请参照
		 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
		 * 相关jar包（非pom）直接下载：
		 * http://code.fegine.com/aliyun-jar.zip
		 */
		try{
//			HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
//			System.out.println(response.toString());//如不输出json, 请打开这行代码，打印调试头部状态码。
			//状态码: 200 正常；400 URL无效；401 appCode错误； 403 次数用完； 500 API网管错误
			//获取response的body
//			System.out.println(EntityUtils.toString(response.getEntity()));
			return code;
		}catch (Exception e){
			e.printStackTrace();
		}
		return code ;
	}
}
