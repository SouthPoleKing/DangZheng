package com.party.dangzheng.controller;

import com.github.pagehelper.PageInfo;
import com.party.dangzheng.common.Result;
import com.party.dangzheng.entity.StudentInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/StudentInfo")
public class StudentInfoController {
    @Resource
    private com.party.dangzheng.service.StudentInfoService StudentInfoService;

    @PostMapping//新增
    public Result add(@RequestBody StudentInfo StudentInfo) {
        StudentInfoService.add(StudentInfo);
        return Result.success();
    }

    @PutMapping//更新
    public Result updateStudentInfo(@RequestBody StudentInfo StudentInfo) {
        StudentInfoService.update(StudentInfo);
        return Result.success();
    }

    @GetMapping//获取全部管理员信息
    public Result findAll() {
        List<StudentInfo> list=StudentInfoService.findAll();
        return Result.success(list);
    }

    @DeleteMapping("/{id}")//删除信息,前端进行二次提醒，确认是否要删除
    public Result deleteById(@PathVariable Long id) {
        StudentInfoService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/page")//分页
    public Result findPage(@RequestParam Integer pageNo,
                           @RequestParam Integer pageSize) {
        PageInfo<StudentInfo> info = StudentInfoService.findPage(pageNo,pageSize);
        return Result.success(info);
    }

    @GetMapping("/page/{name}")//分页
    public Result findPageName(@RequestParam Integer pageNo,
                                @RequestParam Integer pageSize,
                               @PathVariable String name) {
        PageInfo<StudentInfo> info = StudentInfoService.findPageName(pageNo,pageSize,name);
        return Result.success(info);
    }
}