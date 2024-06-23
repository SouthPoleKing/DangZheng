package com.party.dangzheng.service;

import com.party.dangzheng.common.ResultCode;
import com.party.dangzheng.dao.UserTokenRepository;
import com.party.dangzheng.entity.userInfo;
import com.party.dangzheng.entity.userToken;
import com.party.dangzheng.util.NumberUtil;
import com.party.dangzheng.util.SystemUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserTokenService<T> {
    @Resource
    private UserTokenRepository userTokenRepository;

    public  String createToken (userInfo USER) {
         //登陆后立即执行修改token的操作
        userToken userToken = userTokenRepository.selectByPrimaryKey(USER);
        String token = getNewToken(System.currentTimeMillis()+"", USER.getId());
        //当前时间
        Date now=new Date();
        //过期时间为1天
            Date expireTime=new Date(now.getTime()+1*1000*60*60*24);
        if(userToken==null){
                userToken=new userToken();
                userToken.setId(USER.getId());
                userToken.setToken(token);
                userToken.setCreateTime(now);
                userToken.setExpireTime(expireTime);
                //新增一条token数据
                if(userTokenRepository.insertSelective(userToken)>0) {
                    //新增成功后返回
                    return token;
                    }
            }else {
                    userToken.setToken(token);
                    userToken.setCreateTime(now);
                    userToken.setExpireTime(expireTime);
                    //更新
                    if(userTokenRepository.updateSelective(userToken)>0){
                        //修改成功后返回
                        return token;
                    }
                }
        return ResultCode.ERROR.code;
    }
    public String getNewToken(String timeStr, Long userId) {
        String src=timeStr+userId+ NumberUtil.genRandomNum(4);
        return SystemUtil.genToken(src);
    }
}
