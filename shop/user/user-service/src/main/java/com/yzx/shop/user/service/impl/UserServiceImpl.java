package com.yzx.shop.user.service.impl;

import com.yzx.shop.commen.utils.CodecUtils;
import com.yzx.shop.commen.utils.NumberUtils;
import com.yzx.shop.user.entity.User;
import com.yzx.shop.user.mapper.UserMapper;
import com.yzx.shop.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private final String PREFIX="user:code:phone:";

    @Override
    public Boolean checkUserData(String data, Integer type) {
        User record=new User();
        if(type==1){
            record.setUsername(data);
        }else if(type==2){
            record.setPhone(data);
        }else {
            return null;
        }
        return userMapper.selectCount(record)==0;
    }

    @Override
    public Boolean sendVerifyCode(String phone) {
        try{
            if(StringUtils.isBlank(phone)){
                return null;
            }
            Map<String,String> prop=new HashMap<>();
            String code= NumberUtils.generateCode(6);
            prop.put("phone",phone);
            prop.put("code",code);

            rabbitTemplate.convertAndSend("shop.sms.exchange","sms.verify",prop);
            stringRedisTemplate.opsForValue().set(PREFIX+phone,code,5, TimeUnit.MINUTES);

            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Boolean register(User user, String code) {
        if(StringUtils.isBlank(code) || user==null){
            return null;
        }

        String phone=user.getPhone();
        String name=user.getUsername();
        String pwd=user.getPassword();

        if(StringUtils.isBlank(phone)|| StringUtils.isBlank(name) || StringUtils.isBlank(pwd)){
            return null;
        }

        String realCode = stringRedisTemplate.opsForValue().get(PREFIX + phone);
        if(!code.equals(realCode)){
            return false;
        }

        user.setId(null);
        user.setSalt(CodecUtils.generateSalt());
        user.setPassword(CodecUtils.md5Hex(user.getPassword(),user.getSalt()));
        user.setCreated(new Date());
        userMapper.insert(user);
        return true;
    }

    @Override
    public User queryUser(String username, String password) {
        User record=new User();
        record.setUsername(username);
        User user = userMapper.selectOne(record);
        if(user==null){
            return null;
        }

        String pwd=user.getPassword();
        password=CodecUtils.md5Hex(password,user.getSalt());
        if(!pwd.equals(password)){
            return null;
        }

        return user;
    }

}
