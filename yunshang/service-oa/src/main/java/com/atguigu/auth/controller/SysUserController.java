package com.atguigu.auth.controller;


import com.atguigu.auth.service.SysUserService;
import com.atguigu.common.result.Result;
import com.atguigu.model.system.SysUser;

import com.atguigu.vo.system.SysUserQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-03-21
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/admin/system/sysUser")
@CrossOrigin
public class SysUserController {
@Autowired
    private SysUserService sysUserService;

@ApiOperation("用户条件分页查询")
@GetMapping("{page}/{limit}")
public Result index(@PathVariable Long page,
                    @PathVariable long limit,
                    SysUserQueryVo sysUserQueryVo){
    Page<SysUser> page1 = new Page<>(page, limit);
    LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
    String username = sysUserQueryVo.getKeyword();
    String createTimeBegin = sysUserQueryVo.getCreateTimeBegin();
    String createTimeEnd = sysUserQueryVo.getCreateTimeEnd();
    //判断条件值不为空
    //like 模糊查询
//    if(!StringUtils.isEmpty(username)) {
//        queryWrapper.like("username",username);
//    }
    if(!StringUtils.isEmpty(username)) {
        queryWrapper.like(SysUser::getUsername,username);
    }
    if(!StringUtils.isEmpty(createTimeBegin)) {
        queryWrapper.ge(SysUser::getCreateTime,createTimeBegin);
    }
    //le 小于等于
    if(!StringUtils.isEmpty(createTimeEnd)) {
        queryWrapper.le(SysUser::getCreateTime,createTimeEnd);
    }
    Page<SysUser> pageModel = sysUserService.page(page1, queryWrapper);
    return Result.ok(pageModel);
}
    @ApiOperation(value = "获取用户")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        SysUser user = sysUserService.getById(id);
        return Result.ok(user);
    }

    @ApiOperation(value = "保存用户")
    @PostMapping("save")
    public Result save(@RequestBody SysUser user) {
        sysUserService.save(user);
        return Result.ok();
    }

    @ApiOperation(value = "更新用户")
    @PutMapping("update")
    public Result updateById(@RequestBody SysUser user) {
        sysUserService.updateById(user);
        return Result.ok();
    }
    @PreAuthorize("hasAuthority('bnt.sysUser.update')")
    @ApiOperation(value = "删除用户")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        sysUserService.removeById(id);
        return Result.ok();
    }
    @ApiOperation(value = "更新状态")
    @GetMapping("updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable Long id, @PathVariable Integer status) {
        sysUserService.updateStatus(id, status);
        return Result.ok();
    }
    @ApiOperation(value = "获取当前用户基本信息")
    @GetMapping("getCurrentUser")
    public Result getCurrentUser() {
        return Result.ok(sysUserService.getCurrentUser());
    }
}


