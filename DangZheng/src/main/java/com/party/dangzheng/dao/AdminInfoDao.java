package  com.party.dangzheng.dao;

import com.party.dangzheng.entity.AdminInfo;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
@org.apache.ibatis.annotations.Mapper
public interface AdminInfoDao extends Mapper<AdminInfo> {
    @Select("select * from admininfo where account=#{acoount} and password=#{password} and role=#{role}" )
    AdminInfo findByAccountAndPassword(@Param("account") Long account,
                                       @Param("password") String password,
                                       @Param("role")int role);

    @Select("select * from admininfo where account=#{account};")
    AdminInfo findByAccount(@Param("account") long account);

    //模糊查询
    @Select("select * from admininfo where name and account like concat ('%',#{name} ,'%')")
    List<AdminInfo> findByNamePage(@Param("name") String name);
//    @Select("select usrname , account , role from usr_info")
//    AdminInfo findByID(@Param("id") Long id);
}
