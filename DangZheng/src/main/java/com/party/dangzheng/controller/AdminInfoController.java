package com.party.dangzheng.controller;

import com.github.pagehelper.PageInfo;
import com.party.dangzheng.common.Result;
import com.party.dangzheng.entity.AdminInfo;
import com.party.dangzheng.service.AdminInfoService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adminInfo")
public class AdminInfoController {
    @Resource
    private AdminInfoService adminInfoService;

    @PostMapping//新增
    public Result add(@RequestBody AdminInfo adminInfo) {
        adminInfoService.add(adminInfo);
        return Result.success();
    }

    @PutMapping//更新
    public Result updateAdminInfo(@RequestBody AdminInfo adminInfo) {
        adminInfoService.update(adminInfo);
        return Result.success();
    }

    @GetMapping//获取全部管理员信息
    public Result findAll() {
        List<AdminInfo> list=adminInfoService.findAll();
        return Result.success(list);
    }

    @DeleteMapping("/{id}")//删除信息,前端进行二次提醒，确认是否要删除
    public Result deleteById(@PathVariable Long id) {
        adminInfoService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/page")//分页
    public Result findPage(@RequestParam Integer pageNo,
                           @RequestParam Integer pageSize) {
        PageInfo<AdminInfo> info = adminInfoService.findPage(pageNo,pageSize);
        return Result.success(info);
    }

    @GetMapping("/page/{name}")//分页
    public Result findPageName(@RequestParam Integer pageNo,
                                @RequestParam Integer pageSize,
                               @PathVariable String name) {
        PageInfo<AdminInfo> info = adminInfoService.findPageName(pageNo,pageSize,name);
        return Result.success(info);
    }
}
