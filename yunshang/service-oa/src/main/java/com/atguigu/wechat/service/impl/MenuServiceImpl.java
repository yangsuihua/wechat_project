package com.atguigu.wechat.service.impl;
import com.atguigu.model.wechat.Menu;
import com.atguigu.wechat.mapper.MenuMapper;
import com.atguigu.wechat.service.MenuService;
import com.atguigu.vo.wechat.MenuVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.SneakyThrows;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-04-03
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
//       @Autowired
//       private MenuMapper menuMapper;
    @Autowired
    private WxMpService wxMpService;
    @Override
    public List<MenuVo> findMenuInfo() {
        ArrayList<MenuVo> list = new ArrayList<>();
        List<Menu> menulist = baseMapper.selectList(null);
        List<Menu> oneMenuList = menulist.stream().filter(menu -> menu.getParentId().longValue() == 0)
                .collect(Collectors.toList());
        for(Menu oneMenu:oneMenuList){
            MenuVo onemenuVo = new MenuVo();
            //前是有值的，后是要赋值的
            BeanUtils.copyProperties(oneMenu,onemenuVo);
            List<Menu> twoMenuList = menulist.stream().filter(menu -> menu.getParentId().longValue()
                    == oneMenu.getId().longValue()).collect(Collectors.toList());
            List<MenuVo> children = new ArrayList<>();

            for(Menu twomenu: twoMenuList){
                MenuVo twomenuVo = new MenuVo();
                BeanUtils.copyProperties(twomenu,twomenuVo);
                children.add(twomenuVo);
            }
            onemenuVo.setChildren(children);
            list.add(onemenuVo);
        }
        return list;
    }
    @Override
    public void syncMenu() {
        List<MenuVo> menuVoList = this.findMenuInfo();
        //菜单
        JSONArray buttonList = new JSONArray();
        for(MenuVo oneMenuVo:menuVoList){
            JSONObject one = new JSONObject();
            one.put("name", oneMenuVo.getName());
            if(CollectionUtils.isEmpty(oneMenuVo.getChildren())) {
                one.put("type", oneMenuVo.getType());
                one.put("url", "http://lovett2.gz2vip.91tunnel.com"+oneMenuVo.getUrl());
            } else {
                JSONArray subButton = new JSONArray();
                for(MenuVo twoMenuVo : oneMenuVo.getChildren()) {
                    JSONObject view = new JSONObject();
                    view.put("type", twoMenuVo.getType());
                    if(twoMenuVo.getType().equals("view")) {
                        view.put("name", twoMenuVo.getName());
                        //H5页面地址
                        view.put("url", "http://lovett2.gz2vip.91tunnel.com"+twoMenuVo.getUrl());
                    } else {
                        view.put("name", twoMenuVo.getName());
                        view.put("key", twoMenuVo.getMeunKey());
                    }
                    subButton.add(view);
                }
                one.put("sub_button", subButton);
            }
            buttonList.add(one);
        }
        JSONObject button = new JSONObject();
        button.put("button", buttonList);
        try {
            wxMpService.getMenuService().menuCreate(button.toJSONString());
        } catch (WxErrorException e) {
            throw new RuntimeException(e);
        }
        }
    @SneakyThrows
    @Override
    public void removeMenu() {
        wxMpService.getMenuService().menuDelete();
    }
}


