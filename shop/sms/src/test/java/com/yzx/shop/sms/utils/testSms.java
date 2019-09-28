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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class testSms {


    @Test
    public void test(){
        DefaultProfile profile = DefaultProfile.getProfile("default", "LTAIpmmNQtAZrp8p", "Hq5ozK5rQ9UjsFgJLDKpW4jhl8kop3");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", "13325456805");
//        request.putQueryParameter("SignName", "FrameExc");
//        request.putQueryParameter("TemplateCode", "SMS_167527459");
        request.putQueryParameter("SignName", "FrameExc");
        request.putQueryParameter("TemplateCode", "SMS_167527459");
        request.putQueryParameter("TemplateParam", "{\"code\":"+"1234"+"}");
        CommonResponse response=null;
        try {
            response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

}
