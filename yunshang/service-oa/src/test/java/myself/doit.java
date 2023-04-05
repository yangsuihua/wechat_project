package myself;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.auth.service.SysUserService;
import com.atguigu.model.process.Process;
import com.atguigu.model.process.ProcessTemplate;
import com.atguigu.model.system.SysUser;
import com.atguigu.model.wechat.Menu;
import com.atguigu.vo.wechat.MenuVo;
import com.atguigu.wechat.service.MenuService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@SpringBootTest
@Slf4j
//@SneakyThrows
public class doit {

    @Autowired
    private WxMpService wxMpService;



    //    String processId="d";
//    String taskId="d";
//    String processId="d";
    @Test
    @SneakyThrows

    public void doit1() {

        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        //设置模板ID
        templateMessage.setTemplateId("or2ms-hN0FBcdR_xpHnYgfXw70LFdWDchc2USbGdss0");
        //设置发送给哪个用户
        templateMessage.setToUser("oloP26J_abCriden2r7ZH7iehpW4");


//        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
//                .toUser()//要推送的用户openid
//                .templateId()//模板id
//                .url("http://oa.atguigu.cn/#/show/")//点击模板消息要访问的网址
//                .build();
        templateMessage.addData(new WxMpTemplateData("first", "2023年4月4日", "#272727"));
        templateMessage.addData(new WxMpTemplateData("keyword1", "320天", "#272727"));
        templateMessage.addData(new WxMpTemplateData("keyword2", "广州", "#272727"));
        templateMessage.addData(new WxMpTemplateData("keyword3", "23°", "#272727"));
        templateMessage.addData(new WxMpTemplateData("keyword4", "217天", "#272727"));
        templateMessage.addData(new WxMpTemplateData("content", "知命不惧，日日自新", "#272727"));
//         templateMessage.addData(new WxMpTemplateData("content", content.toString(), "#272727"));
//        String msg = wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        try {
            //发送模板
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
            log.error("发送模板消息异常：{}", e.getMessage());
            e.printStackTrace();
        }

    }
}
