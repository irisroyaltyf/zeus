package top.yulegou.zeus.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.yulegou.zeus.constant.Constants;
import top.yulegou.zeus.constant.ErrorCode;
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
        ZConfig imageConfig = zeusConfigManager.getCachedConfig(Constants.ZCONFIG_IMAGE_CONFIG);
        if (imageConfig != null) {
            model.addAttribute("imageConfig",JSONObject.parseObject(imageConfig.getCdata()));
        }
        return "setting";
    }
    @RequestMapping("/setting")
    public String setting(final Model model) {
        ZConfig robots =  zeusConfigManager.getCachedConfig(Constants.ZCONFIG_ROBOTS);
        if (robots != null) {
            model.addAttribute("robots", robots.getCdata());
        }
        ZConfig auto = zeusConfigManager.getCachedConfig(Constants.ZCONFIG_AUTO_COLLECT);
        if (auto != null) {
            model.addAttribute("auto", auto.getCdata());
        }
        ZConfig imageConfig = zeusConfigManager.getCachedConfig(Constants.ZCONFIG_IMAGE_CONFIG);
        if (imageConfig != null) {
            model.addAttribute("imageConfig", imageConfig);
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
            config.setCname(Constants.ZCONFIG_ROBOTS);
            zeusConfigManager.updateConfig(config);
        }
        if (zGlobalSettingDTO.getAuto() != null) {
            ZConfig config = new ZConfig();
            config.setCtype(1);
            config.setCdata(String.valueOf(zGlobalSettingDTO.getAuto()));
            config.setCname(Constants.ZCONFIG_AUTO_COLLECT);
            zeusConfigManager.updateConfig(config);
        }
        return Result.success();
    }

    @RequestMapping("/config/image.do")
    @ResponseBody
    public String saveImageConfig(
            ZGlobalSettingDTO zImageConfigDTO
    ) {
        JSONObject obj = new JSONObject();
        if (zImageConfigDTO == null || zImageConfigDTO.getImageTransfer() == null) {
            return Result.failed(ErrorCode.REQUEST_PARAM_ERROR.getCode(), "参数错误");
        } else if (zImageConfigDTO.getImageTransfer() == 0) {
            obj.put("imageTransfer", 0);
            ZConfig config = new ZConfig();
            config.setCtype(1);
            config.setCname(Constants.ZCONFIG_IMAGE_CONFIG);
            config.setCdata(obj.toJSONString());
            if (zeusConfigManager.updateConfig(config) > 0) {
                return Result.success();
            } else {
                return Result.failed(ErrorCode.DB_ERROR_UPDATE_ERROR.getCode(), "数据更新失败，请刷新后重试");
            }
        } else if (zImageConfigDTO.getImageTransfer() == 1) {
            obj.put(Constants.ZCONFIG_IMAGE_TRANSFER, 1);
            if (StringUtils.isNotEmpty(zImageConfigDTO.getImageDir())) {
                obj.put(Constants.ZCONFIG_IMAGE_DIR, zImageConfigDTO.getImageDir());
            } else {
                obj.put(Constants.ZCONFIG_IMAGE_DIR, "/data/zeus");
            }
            if (StringUtils.isNotEmpty(zImageConfigDTO.getImageUrl())) {
                obj.put("imageUrl", zImageConfigDTO.getImageUrl());
            } else {
                obj.put("imageUrl", "http://localhost:8080/data/images");
            }
            ZConfig config = new ZConfig();
            config.setCtype(1);
            config.setCname(Constants.ZCONFIG_IMAGE_CONFIG);
            config.setCdata(obj.toJSONString());
            if (zeusConfigManager.updateConfig(config) > 0) {
                return Result.success();
            } else {
                return Result.failed(ErrorCode.DB_ERROR_UPDATE_ERROR.getCode(), "数据更新失败，请刷新后重试");
            }
        } else {
            return Result.failed(ErrorCode.NOT_IMPLEMENTED_YET.getCode(), "等待实现");
        }
    }

}
