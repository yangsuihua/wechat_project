package com.atguigu.auth.service;

import com.atguigu.model.system.SysMenu;
import com.atguigu.vo.system.AssginMenuVo;
import com.atguigu.vo.system.RouterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author atguigu
 * @since 2023-03-22
 */
public interface SysMenuService extends IService<SysMenu> {
    List<SysMenu> findNodes();

    List<SysMenu> findSysMenuByRoleId(Long roleId);

    void doAssign(AssginMenuVo assignMenuVo);
    List<RouterVo> findUserMenuList(Long userId);

    List<String> findUserPermsList(Long userId);
    List<RouterVo> buildMenus(List<SysMenu> sysMenuTreeList);
}
