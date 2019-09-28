package com.yzx.shop.sms.listener;

import com.yzx.shop.sms.config.SmsPropertiesConfig;
import com.yzx.shop.sms.utils.SmsUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@EnableConfigurationProperties(SmsPropertiesConfig.class)
public class SmsListener {

    @Autowired
    private SmsPropertiesConfig smsPropertiesConfig;

    @Autowired
    private SmsUtil smsUtil;

    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(value = "shop.sms.exchange",durable = "true",ignoreDeclarationExceptions = "true"),
            value = @Queue(value = "shop.sms.quene",durable = "true",ignoreDeclarationExceptions = "true"),
            key = {"sms.verify"}
    ))
    public void SmsListener(Map<String,String> map) throws Exception {
        if(map==null||map.size()<=0){
            return;
        }
        String phone= map.get("phone");
        String code=map.get("code");
        if(StringUtils.isBlank(phone)||StringUtils.isBlank(code)){
            return;
        }
        smsUtil.sendSms(phone,code,smsPropertiesConfig.getSignName(),smsPropertiesConfig.getVerifyCodeTemplate());
    }
}
