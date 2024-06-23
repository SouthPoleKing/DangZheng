package com.party.dangzheng.controller;

import com.github.pagehelper.PageInfo;
import com.party.dangzheng.common.Result;
import com.party.dangzheng.entity.TutorInfo;
import com.party.dangzheng.service.TutorInfoService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/tutorInfo")
public class TutorInfoController {
    @Resource
    private TutorInfoService TutorInfoService;

    @PostMapping//新增
    public Result add(@RequestBody TutorInfo TutorInfo) {
        TutorInfoService.add(TutorInfo);
        return Result.success();
    }

    @PutMapping//更新
    public Result updateTutorInfo(@RequestBody TutorInfo TutorInfo) {
        TutorInfoService.update(TutorInfo);
        return Result.success();
    }

    @GetMapping//获取全部教师信息（应该在管理员界面使用）
    public Result findAll() {
        List<TutorInfo> list=TutorInfoService.findAll();
        return Result.success(list);
    }

    @DeleteMapping("/{id}")//删除信息,前端进行二次提醒，确认是否要删除
    public Result deleteById(@PathVariable Long id) {
        TutorInfoService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/page")//分页
    public Result findPage(@RequestParam Integer pageNo,
                           @RequestParam Integer pageSize) {
        PageInfo<TutorInfo> info = TutorInfoService.findPage(pageNo,pageSize);
        return Result.success(info);
    }

    @GetMapping("/page/{name}")//分页
    public Result findPageName(@RequestParam Integer pageNo,
                                @RequestParam Integer pageSize,
                               @PathVariable String name) {
        PageInfo<TutorInfo> info = TutorInfoService.findPageName(pageNo,pageSize,name);
        return Result.success(info);
    }

}