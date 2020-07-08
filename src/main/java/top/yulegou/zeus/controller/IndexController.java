package top.yulegou.zeus.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.yulegou.zeus.dao.domain.Result;
import top.yulegou.zeus.dao.domain.ZConfig;
import top.yulegou.zeus.dao.domain.ZTask;
import top.yulegou.zeus.domain.ZGlobalSettingDTO;
import top.yulegou.zeus.manager.ZeusConfigManager;
import top.yulegou.zeus.task.quartz.ScheduledJobManager;


@Controller
@Slf4j
public class IndexController {
    @Autowired
    private ZeusConfigManager zeusConfigManager;

    @RequestMapping(value = {"", "/"})
    public String index(final Model model) {
        // TODO 配置化菜单
        ZConfig robots =  zeusConfigManager.getCachedConfig("robots");
        if (robots != null) {
            model.addAttribute("robots", robots.getCdata());
        }
        ZConfig auto = zeusConfigManager.getCachedConfig("auto_collect");
        if (auto != null) {
            model.addAttribute("auto", auto.getCdata());
        }
        return "setting";
    }
    @RequestMapping("/setting")
    public String setting(final Model model) {
        ZConfig robots =  zeusConfigManager.getCachedConfig("robots");
        if (robots != null) {
            model.addAttribute("robots", robots.getCdata());
        }
        ZConfig auto = zeusConfigManager.getCachedConfig("auto_collect");
        if (auto != null) {
            model.addAttribute("auto", auto.getCdata());
        }
        return "setting";
    }

    @RequestMapping("/config.do")
    @ResponseBody
    public String saveConfig(
            ZGlobalSettingDTO zGlobalSettingDTO
    ) {
        if (zGlobalSettingDTO.getRobots() != null) {
            ZConfig config = new ZConfig();
            config.setCtype(1);
            config.setCdata(String.valueOf(zGlobalSettingDTO.getRobots()));
            config.setCname("robots");
            zeusConfigManager.updateConfig(config);
        }
        if (zGlobalSettingDTO.getAuto() != null) {
            ZConfig config = new ZConfig();
            config.setCtype(1);
            config.setCdata(String.valueOf(zGlobalSettingDTO.getAuto()));
            config.setCname("auto_collect");
            zeusConfigManager.updateConfig(config);
        }
        return Result.success();
    }
}
