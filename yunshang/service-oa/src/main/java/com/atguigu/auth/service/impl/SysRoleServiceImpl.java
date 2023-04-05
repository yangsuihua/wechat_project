package com.atguigu.auth.service.impl;

import com.atguigu.auth.mapper.SysRoleMapper;
import com.atguigu.auth.mapper.SysUserRoleMapper;
import com.atguigu.auth.service.SysRoleService;
import com.atguigu.auth.service.SysUserRoleService;
import com.atguigu.model.system.SysRole;
import com.atguigu.model.system.SysUserRole;

import com.atguigu.vo.system.AssginRoleVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper,SysRole>
implements SysRoleService {
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Transactional
    @Override
    public void doAssign(AssginRoleVo assginRoleVo) {
          sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getRoleId,assginRoleVo.getUserId()));
          for(Long roleId :assginRoleVo.getRoleIdList()) {
              if(StringUtils.isEmpty(roleId))  continue;
              SysUserRole sysUserRole = new SysUserRole();
              sysUserRole.setRoleId(roleId);
              sysUserRole.setUserId(assginRoleVo.getUserId());
              sysUserRoleMapper.insert(sysUserRole);

          }
    }

    @Override
    public Map<String, Object> findRoleByAdminId(Long userId) {
        //查询所以角色，返回list集合
        List<SysRole> sysRoles = baseMapper.selectList(null);
        //格局userid查询角色用户关系表，查询userid对应的所以角色id
        LambdaQueryWrapper<SysUserRole> wrapper = new LambdaQueryWrapper();
        wrapper.eq(SysUserRole::getUserId,userId);
        List<SysUserRole> existUserRoleList = sysUserRoleService.list(wrapper);
        //通过java8特性，遍历除角色id
        List<Long> existRoleList = existUserRoleList.stream().map(c -> c.getRoleId()).collect(Collectors.toList());


        ArrayList<SysRole> assignRoleList = new ArrayList<>();
        //根据查询所有角色id，找到对应 的角色信息
        for( Long roleid:existRoleList){
            for(SysRole sysRole :sysRoles)
            if(sysRole.getId()==roleid){
                assignRoleList.add(sysRole);
            }
        }
//        List<SysRole> assginRoleList = new ArrayList<>();
//        for (SysRole role : sysRoles) {
//            //已分配
//            if(existRoleList.contains(role.getId())) {
//                assginRoleList.add(role);
//            }
//        }
        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("assginRoleList", assignRoleList);
        roleMap.put("allRolesList", sysRoles);
        System.out.println(roleMap);
        return roleMap;
    }
}
