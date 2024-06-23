package com.party.dangzheng.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.party.dangzheng.common.ResultCode;
import com.party.dangzheng.dao.TutorInfoDao;
import com.party.dangzheng.entity.TutorInfo;
import com.party.dangzheng.excption.CustomException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class TutorInfoService {
    @Resource
    private TutorInfoDao TutorInfoDao;

    public TutorInfo login(Long account, String password, int role, String token) {
        //通过学工号和密码在数据库找一条信息出来
        TutorInfo TutorInfo = TutorInfoDao.findByAccountAndPassword(account,password,role);
        //找不到信息
        if(ObjectUtils.isEmpty(TutorInfo)){
            throw new CustomException("-1","学工号或密码有误或身份不匹配");
        }
        UserTokenService userTokenService = new UserTokenService();
        if(userTokenService.createToken(TutorInfo).equals(ResultCode.ERROR.code)){
            throw new CustomException("-1","无法生成token");
        }
        token=userTokenService.createToken(TutorInfo);
        return TutorInfo;
    }
    public TutorInfo findById(Long id) {
        return TutorInfoDao.selectByPrimaryKey(id);
    }

    public void update(TutorInfo TutorInfo) {
        TutorInfoDao.updateByPrimaryKeySelective(TutorInfo);//根据主键改了什么更新什么
    }

    public void add(TutorInfo TutorInfo) {
        //能否直接插入呢？
        //当没有学工号的时候不可以直接插入，没有密码可以新增一个
        //新增一个重名管理员怎么办

        TutorInfo info= TutorInfoDao.findByAccount(TutorInfo.getAccount());
        if(ObjectUtils.isEmpty(TutorInfo.getAccount())){
            //先看新增的信息里是否有必须存在的学工号
            throw new CustomException(ResultCode.PARAM_ERROR);
        }
        if(!ObjectUtils.isEmpty(info)){
            //先查询里面是否有重名管理员
            throw new CustomException(ResultCode.USER_EXIST_ERROR);
        }
        if(!ObjectUtils.isEmpty(TutorInfo.getPassword())){
            //没有密码初始化一个密码为88888888
            TutorInfo.setPassword("88888888");
        }
        TutorInfoDao.insert(TutorInfo);
    }
    public void deleteById(Long id) {TutorInfoDao.deleteByPrimaryKey(id);}

    public List<TutorInfo> findAll() {
        return TutorInfoDao.selectAll();
    }

    public PageInfo<TutorInfo> findPage(Integer pageNo, Integer pageSize) {
        //开始分页
        PageHelper.startPage(pageNo,pageSize);
        //下次查询会自动根据页数和数量分配显示数据
        List<TutorInfo> infos = TutorInfoDao.selectAll();
        return PageInfo.of(infos);
    }

    public PageInfo<TutorInfo> findPageName(Integer pageNo, Integer pageSize, String name) {
        PageHelper.startPage(pageNo,pageSize);
        List<TutorInfo> infos =TutorInfoDao.findByNamePage(name);
        return PageInfo.of(infos);
    }

    public void register(TutorInfo tutorInfo) {
        TutorInfo Info = TutorInfoDao.findByAccount(tutorInfo.getAccount());
        if(ObjectUtils.isEmpty(Info)){
            throw new CustomException(ResultCode.PARAM_ERROR);
        }
        TutorInfoDao.insertSelective(tutorInfo);
    }
}
