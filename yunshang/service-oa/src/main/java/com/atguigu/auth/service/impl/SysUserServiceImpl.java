package com.atguigu.auth.service.impl;
import com.atguigu.auth.service.SysMenuService;
import com.atguigu.model.system.SysDept;
import com.atguigu.model.system.SysPost;
import com.atguigu.model.system.SysUser;
import com.atguigu.auth.mapper.SysUserMapper;
import com.atguigu.auth.service.SysUserService;

import com.atguigu.process.service.SysDeptService;
import com.atguigu.process.service.SysPostService;
import com.atguigu.security.custom.LoginUserInfoHelper;
import com.atguigu.vo.system.RouterVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-03-21
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private SysPostService sysPostService;
    @Autowired
    private  SysUserMapper sysUserMapper;


    @Transactional
    @Override
    public void updateStatus(Long id, Integer status) {
        SysUser sysUser = this.getById(id);
        if(status.intValue() == 1) {
            sysUser.setStatus(status);
        } else {
            sysUser.setStatus(0);
        }
        this.updateById(sysUser);
    }

    @Override
    public SysUser getByusername(String username) {
        return this.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));
    }


        @Override
        public Map<String, Object> getUserInfo(String username){
            Map<String, Object> result = new HashMap<>();
            SysUser sysUser = this.getByusername(username);

            //根据用户id获取菜单权限值
            List<RouterVo> routerVoList = sysMenuService.findUserMenuList(sysUser.getId());
            //根据用户id获取用户按钮权限
            List<String> permsList = sysMenuService.findUserPermsList(sysUser.getId());

            result.put("name", sysUser.getName());
            result.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
            //当前权限控制使用不到，我们暂时忽略
            result.put("roles",  new HashSet<>());
            result.put("buttons", permsList);
            result.put("routers", routerVoList);
            return result;
        }

    @Override
    public Map<String, Object> getCurrentUser() {
        SysUser sysUser = sysUserMapper.selectById(LoginUserInfoHelper.getUserId());
        SysDept sysDept = sysDeptService.getById(sysUser.getDeptId());
        SysPost sysPost = sysPostService.getById(sysUser.getPostId());
        Map<String, Object> map = new HashMap<>();
        map.put("name", sysUser.getName());
        map.put("phone", sysUser.getPhone());
        map.put("deptName", sysDept.getName());
        map.put("postName", sysPost.getName());
        return map;
    }

}



