package com.party.dangzheng.dao;

import com.party.dangzheng.entity.TutorInfo;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
@org.apache.ibatis.annotations.Mapper
public interface TutorInfoDao extends Mapper<TutorInfo> {
 @Select("select * from partycomment.tutorinfo where account=#{acoount} and password=#{password} and role=#{role}" )
 TutorInfo findByAccountAndPassword(@Param("account") Long account,
                                      @Param("password") String password,
                                      @Param("role")int role);
@Select("select * from partycomment.tutorinfo where account=#{account};")
TutorInfo findByAccount(@Param("account") long account);

    //模糊查询姓名和学工号
    @Select("select * from partycomment.tutorinfo where name and account like concat ('%',#{name} ,'%')")
    List<TutorInfo> findByNamePage(@Param("name") String name);
}
