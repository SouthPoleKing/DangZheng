package com.party.dangzheng.dao;

import com.party.dangzheng.entity.StudentInfo;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
@org.apache.ibatis.annotations.Mapper
public interface StudentInfoDao extends Mapper<StudentInfo> {
     @Select("select * from Studentinfo where account=#{acoount} and password=#{password} and role=#{role}" )
     StudentInfo findByAccountAndPassword(@Param("account") Long account,
                                        @Param("password") String password,
                                        @Param("role")int role);

    @Select("select * from Studentinfo where account=#{account};")
    StudentInfo findByAccount(@Param("account") long account);

    //模糊查询
    @Select("select * from Studentinfo where name and account like concat ('%',#{name} ,'%')")
    List<StudentInfo> findByNamePage(@Param("name") String name);
}
