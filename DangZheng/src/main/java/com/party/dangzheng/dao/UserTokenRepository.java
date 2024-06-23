package com.party.dangzheng.dao;

import com.party.dangzheng.entity.userToken;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
@org.apache.ibatis.annotations.Mapper
public interface UserTokenRepository extends Mapper<userToken> {
    int insertSelective(userToken usertoken);
    @Override
    userToken selectByPrimaryKey(Object key);
    userToken selectByToken(String token);
    int updateSelective(userToken usertoken);
}
