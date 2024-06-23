package com.party.dangzheng.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.party.dangzheng.common.ResultCode;
import com.party.dangzheng.dao.AdminInfoDao;
import com.party.dangzheng.entity.AdminInfo;
import com.party.dangzheng.excption.CustomException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class AdminInfoService {
    @Resource
    private AdminInfoDao adminInfoDao;

    public AdminInfo login(Long account, String password,int role,String token) {
        //通过学工号和密码在数据库找一条信息出来
        AdminInfo adminInfo = adminInfoDao.findByAccountAndPassword(account,password,role);
        //找不到信息
        if(ObjectUtils.isEmpty(adminInfo)){
            throw new CustomException("-1","学工号或密码有误或身份不匹配");
        }
        UserTokenService userTokenService = new UserTokenService();
        if(userTokenService.createToken(adminInfo).equals(ResultCode.ERROR.code)){
            throw new CustomException("-1","无法生成token");
        }
        token=userTokenService.createToken(adminInfo);
        return adminInfo;
    }
    public AdminInfo findById(Long id) {
        return adminInfoDao.selectByPrimaryKey(id);
    }

    public void update(AdminInfo adminInfo) {
        adminInfoDao.updateByPrimaryKeySelective(adminInfo);//根据主键改了什么更新什么
    }

    public void add(AdminInfo adminInfo) {
        //能否直接插入呢？
        //当没有学工号的时候不可以直接插入，没有密码可以新增一个
        //新增一个重名管理员怎么办

        AdminInfo info= adminInfoDao.findByAccount(adminInfo.getAccount());
        if(ObjectUtils.isEmpty(adminInfo.getAccount())){
            //先看新增的信息里是否有必须存在的学工号
            throw new CustomException(ResultCode.PARAM_ERROR);
        }
        if(!ObjectUtils.isEmpty(info)){
            //先查询里面是否有重名管理员
            throw new CustomException(ResultCode.USER_EXIST_ERROR);
        }
        if(!ObjectUtils.isEmpty(adminInfo.getPassword())){
            //没有密码初始化一个密码为88888888
            adminInfo.setPassword("88888888");
        }
        adminInfoDao.insert(adminInfo);
    }
    public void deleteById(Long id) {adminInfoDao.deleteByPrimaryKey(id);}

    public List<AdminInfo> findAll() {
        return adminInfoDao.selectAll();
    }

    public PageInfo<AdminInfo> findPage(Integer pageNo, Integer pageSize) {
        //开始分页
        PageHelper.startPage(pageNo,pageSize);
        //下次查询会自动根据页数和数量分配显示数据
        List<AdminInfo> infos = adminInfoDao.selectAll();
        return PageInfo.of(infos);
    }

    public PageInfo<AdminInfo> findPageName(Integer pageNo, Integer pageSize, String name) {
        PageHelper.startPage(pageNo,pageSize);
        List<AdminInfo> infos =adminInfoDao.findByNamePage(name);
        return PageInfo.of(infos);
    }
}
