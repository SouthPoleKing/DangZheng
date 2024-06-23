package com.party.dangzheng.controller;

import com.party.dangzheng.common.Result;
import com.party.dangzheng.entity.AdminInfo;
import com.party.dangzheng.entity.StudentInfo;
import com.party.dangzheng.entity.TutorInfo;
import com.party.dangzheng.entity.userInfo;
import com.party.dangzheng.service.AdminInfoService;
import com.party.dangzheng.service.StudentInfoService;
import com.party.dangzheng.service.TutorInfoService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class userInfoController {
    @Resource
    private AdminInfoService adminInfoService;

    @Resource
    private StudentInfoService studentInfoService;

    @Resource
    private TutorInfoService tutorInfoService;

    @PostMapping("/login")
    public Result login(@RequestBody userInfo user, HttpServletRequest request) {

        //校验数据是否为空
        if(ObjectUtils.isEmpty(user.getAccount())||ObjectUtils.isEmpty(user.getPassword())
                ||ObjectUtils.isEmpty(user.getRole())){
            return Result.error("-1","请完整输入信息");
        }
        int role=user.getRole();
        userInfo loginUser=new userInfo();

        //辅导员（管理员登陆）
        if(1==role){
           adminInfoService.login(user.getAccount(),user.getPassword(),user.getRole(),user.getToken());
        }
        //教师登录
        if(2==role){
            tutorInfoService.login(user.getAccount(),user.getPassword(),user.getRole(),user.getToken());
        }
        //学生登陆
        if(3==role) {
            studentInfoService.login(user.getAccount(), user.getPassword(), user.getRole(), user.getToken());
        }
        //在session里面把用户信息存一份
       request.getSession().setAttribute("User",loginUser);
        return Result.success(user);
    }

    //用户激活（service代码待完善）
    @PostMapping("/register")
    public Result register(@RequestBody userInfo user, HttpServletRequest request) {
        //校验数据是否为空
        if(ObjectUtils.isEmpty(user.getAccount())||ObjectUtils.isEmpty(user.getPassword())
                ||ObjectUtils.isEmpty(user.getRole())){
            return Result.error("-1","请完整输入信息");
        }
        int role=user.getRole();
        if(2==role){
            //教师注册
            TutorInfo tutorInfo=new TutorInfo();
            //将页面上传来的用户名拷贝到教师类中
            BeanUtils.copyProperties(user,tutorInfo);
            tutorInfoService.register(tutorInfo);
        }
        if(3==role){
            //学生注
            StudentInfo studentInfo=new StudentInfo();
            //将页面上传来的用户名拷贝到学生类中
            BeanUtils.copyProperties(user,studentInfo);
            studentInfoService.register(studentInfo);
        }
        return Result.success();
    }
    @GetMapping("/getUser")
    public Result getUser(HttpServletRequest request) {
        //先从session里面获取当前存的登陆用户信息
        userInfo user = (userInfo) request.getSession().getAttribute("User");
        //应该从token中获取用户登陆信息？
        //判断当前登录用户是什么角色
        int role =user.getRole();

        //从管理员表获取管理员信息,返回到前台
        if(1==role){
            AdminInfo admin =adminInfoService.findById(user.getId());
            //不想让密码显示出来的操作
            //admin.setPassword("");
            return Result.success(admin);
        }
        //从教师表获取登录信息
        if(2==role){
            TutorInfo tutor =tutorInfoService.findById(user.getId());
            //tutor.setPassword("");
            return Result.success(tutor);
        }
        //从学生表获取登录信息
        if(3==role){
            StudentInfo student =studentInfoService.findById(user.getId());
            //student.setPassword("");
            return Result.success(student);
        }
        return Result.success(new userInfo());
    }
    //退出登录
    @GetMapping("/logout")
    public Result logout(HttpServletRequest request) {
        request.getSession().removeAttribute("User");
        return Result.success();
    }
}
