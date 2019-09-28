package com.yzx.shop.sms.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.yzx.shop.sms.config.SmsPropertiesConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/*
pom.xml
<dependency>
  <groupId>com.aliyun</groupId>
  <artifactId>aliyun-java-sdk-core</artifactId>
  <version>4.0.3</version>
</dependency>
*/
@EnableConfigurationProperties(SmsPropertiesConfig.class)
@Component
public class SmsUtil {

    @Autowired
    private  SmsPropertiesConfig smsPropertiesConfig;

    public  CommonResponse sendSms(String phone, String code, String signName, String template) {
        DefaultProfile profile = DefaultProfile.getProfile("default", smsPropertiesConfig.getAccessKeyId(), smsPropertiesConfig.getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", phone);
//        request.putQueryParameter("SignName", "FrameExc");
//        request.putQueryParameter("TemplateCode", "SMS_167527459");
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", template);
        request.putQueryParameter("TemplateParam", "{\"code\":"+code+"}");
        CommonResponse response=null;
        try {
            response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return response;
    }
}