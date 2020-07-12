package top.yulegou.zeus.controller;/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.yulegou.zeus.config.DbFieldData;
import top.yulegou.zeus.constant.Constants;
import top.yulegou.zeus.constant.ErrorCode;
import top.yulegou.zeus.dao.domain.Result;
import top.yulegou.zeus.dao.domain.ZCrawlerRule;
import top.yulegou.zeus.dao.domain.ZPublishRule;
import top.yulegou.zeus.dao.domain.ZTask;
import top.yulegou.zeus.dao.domain.publish.*;
import top.yulegou.zeus.domain.DbColumnsDTO;
import top.yulegou.zeus.domain.PublishBindDTO;
import top.yulegou.zeus.domain.PublishDTO;
import top.yulegou.zeus.domain.PublishTableParamsDTO;
import top.yulegou.zeus.manager.ZeusCrawlerRuleManager;
import top.yulegou.zeus.manager.ZeusPublishRuleManager;
import top.yulegou.zeus.manager.ZeusTaskManager;
import top.yulegou.zeus.manager.db.MysqlManager;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author irisroyalty
 * @date 2020/6/27
 **/
@Controller
@RequestMapping("/task")
@Slf4j
public class PublishController extends BaseController{
    @Autowired
    private ZeusTaskManager zeusTaskManager;
    @Autowired
    private ZeusCrawlerRuleManager zeusCrawlerRuleManager;
    @Autowired
    private ZeusPublishRuleManager zeusPublishRuleManager;
    @Autowired
    private MysqlManager mysqlManager;

    @RequestMapping("/publish")
    public String publishEdit(
            final Model model,
            @RequestParam(value = "task_id", required = true, defaultValue = "0")Integer taskId
    ) {
        if (taskId == 0) {
            return errorPage(model, "缺少相关任务id");
        }
        ZTask task = zeusTaskManager.getTaskById(taskId);
        if (task == null) {
            return  errorPage(model, "任务不存在");
        }
        ZPublishRule publishRule = zeusPublishRuleManager.getPublishRuleByTaskId(taskId);
        ZCrawlerRule crawlerRule = zeusCrawlerRuleManager.getCrawlerRuleByTaskId(taskId);
        if (publishRule != null) {
            model.addAttribute("publishRule", publishRule);
            if (publishRule.getRuleConfig() != null) {
                model.addAttribute("ruleConfig", publishRule.getRuleConfig());
                if (publishRule.getRuleConfig().getType() == 2) {
                    DbPublishRuleConfig dbRuleConfig = (DbPublishRuleConfig) publishRule.getRuleConfig();
                    if (dbRuleConfig.getConnectionConfig() != null) {
                        model.addAttribute("dbStep", 2);
                        try {
                            model.addAttribute("tableNames",
                                    mysqlManager.getTables(dbRuleConfig.getConnectionConfig()));
                            if (dbRuleConfig.getTableConfig() != null && dbRuleConfig.getTableConfig().getTableFields() != null) {
                                Map<String, DbColumnsDTO> obj = new TreeMap<>();
                                for (DbColumnBindConfig bindConfig: dbRuleConfig.getTableConfig().getTableFields()) {
                                    Map<String, DbColumnsDTO> showColumnsDTO = new TreeMap<>();
                                    mysqlManager.getColumnNames(dbRuleConfig.getConnectionConfig()
                                            , bindConfig.getTableName()).stream().forEach(c -> {
                                        showColumnsDTO.put(c.getColumnName(), c);
                                    });
                                    bindConfig.setForShow(showColumnsDTO);
                                }
                            }
                            model.addAttribute("fieldConfig", dbRuleConfig.getTableConfig());
                        } catch (SQLException throwables) {
                            model.addAttribute("dbError", throwables.getMessage());
                        } catch (Exception e) {
                            model.addAttribute("dbError", "请先保存\"数据库配置\"");
                        }
                    } else {
                        model.addAttribute("dbStep", 1);
                        model.addAttribute("dbError", "请先保存\"数据库配置\"");
                    }
                }
            }
        }
        if (crawlerRule != null
                && crawlerRule.getCrawlerRuleConfig() != null
                && crawlerRule.getCrawlerRuleConfig().getZCrawlerContentConfig() != null) {
            model.addAttribute("fieldList",
                    crawlerRule.getCrawlerRuleConfig().getZCrawlerContentConfig().getFieldConfigList());
        }
        model.addAttribute("taskId", taskId);
        return "/task/publish";
    }


    /**
     * publish rule
     * @param publishDTO
     * @return
     */
    @RequestMapping("/publish/addorupdate.do")
    @ResponseBody
    public String addOrUpdatePublishConfig(
            PublishDTO publishDTO
    ) {
        if (publishDTO.getPublishType() == null) {
            return Result.failed(ErrorCode.REQUEST_PARAM_ERROR.getCode(), "请选择发布类型");
        }
        ZPublishRule publishRule = null;
        try {
            publishRule =  checkAndGetPublishRule(publishDTO.getTaskId(), publishDTO.getPublishId());
        } catch (Exception e) {
            log.error("get publish rule error", e);
            return Result.failed(ErrorCode.REQUEST_PARAM_ERROR.getCode(), "规则id不正确,请刷新后重试");
        }
        // 文件
        if (publishDTO.getPublishType() == 1) {
            FilePublishRuleConfig ruleConfig = new FilePublishRuleConfig();
            // file
            if (StringUtils.isEmpty(publishDTO.getFileLocation())) {
                // 设置当前路径
                ruleConfig.setFileLocation(System.getProperty("user.dir") + "/");
            } else {

                ruleConfig.setFileLocation(
                        StringUtils.endsWith(publishDTO.getFileLocation(), "/")
                                ?publishDTO.getFileLocation()
                                : publishDTO.getFileLocation() + "/");
            }
            ruleConfig.setType(1);
            ruleConfig.setFileType(publishDTO.getFileType());
            publishRule.setRuleConfig(ruleConfig);
            publishRule.setType(1);
        } else if (publishDTO.getPublishType() == 2) {
          // 数据库
            DbPublishRuleConfig dbRuleConfig = null;
            if (publishRule.getRuleConfig() != null && publishRule.getRuleConfig() instanceof DbPublishRuleConfig) {
                dbRuleConfig = (DbPublishRuleConfig) publishRule.getRuleConfig();
            }  else {
                dbRuleConfig = new DbPublishRuleConfig();
            }
           if (publishDTO.getDbStep() == null || publishDTO.getDbStep() == 1) {
               if (StringUtils.isEmpty(publishDTO.getHost())
                       || StringUtils.isEmpty(publishDTO.getPort())
                       || StringUtils.isEmpty(publishDTO.getUser())
                        || StringUtils.isEmpty(publishDTO.getSchema())
               ) {
                   return Result.failed(ErrorCode.REQUEST_PARAM_ERROR.getCode(), "数据库参数错误");
               }
               DbConnectionConfig connectionConfig = new DbConnectionConfig();
               connectionConfig.setDbType(publishDTO.getDbType());
               connectionConfig.setHost(publishDTO.getHost());
               connectionConfig.setPort(publishDTO.getPort());
               connectionConfig.setUser(publishDTO.getUser());
               connectionConfig.setPwd(publishDTO.getPwd());
               connectionConfig.setSchema(publishDTO.getSchema());
               if (StringUtils.isNotEmpty(publishDTO.getCharset())) {
                   connectionConfig.setCharset(publishDTO.getCharset());
               } else {
                   connectionConfig.setCharset("utf8");
               }
               dbRuleConfig.setType(2);
               dbRuleConfig.setConnectionConfig(connectionConfig);
           }

            publishRule.setRuleConfig(dbRuleConfig);
            publishRule.setType(2);
        }
        int rst = zeusPublishRuleManager.addOrUpdateZPublishRule(publishRule);
        if (rst > 0) {
            return Result.success(publishRule.getId());
        } else {
            return Result.failed(ErrorCode.SYSTEM_ERROR.getCode(), "更新数据库错误");
        }
    }

    /**
     * 绑定字段
     * @param paramsDTO
     * @return
     */
    @RequestMapping("/publish/fieldbind.do")
    @ResponseBody
    public String bindField(
            @DbFieldData PublishTableParamsDTO paramsDTO
            ) {
        if (paramsDTO.getTaskId() == null || paramsDTO.getPublishRuleId() == null) {
            return  Result.failed(ErrorCode.REQUEST_PARAM_ERROR.getCode(), "任务id 或者publish配置id为空");
        }
        ZPublishRule publishRule = null;
        try {
            publishRule =  checkAndGetPublishRule(paramsDTO.getTaskId(), paramsDTO.getPublishRuleId());
        } catch (Exception e) {
            log.error("get publish rule error", e);
            return Result.failed(ErrorCode.REQUEST_PARAM_ERROR.getCode(), "规则id不正确,请刷新后重试");
        }
        DbPublishRuleConfig dbRuleConfig = null;
        if (publishRule.getRuleConfig() != null && publishRule.getRuleConfig() instanceof DbPublishRuleConfig) {
            dbRuleConfig = (DbPublishRuleConfig) publishRule.getRuleConfig();
        }  else {
            return Result.failed(ErrorCode.PUBLISH_RULE_ERROR_DB_CONFIG_STATE.getCode(), "发布规则状态不正确,请刷新后重试");
        }
        DbTableConfig tableConfig = new DbTableConfig();
        tableConfig.setTableFields(paramsDTO.getTableParams());
        dbRuleConfig.setTableConfig(tableConfig);

        publishRule.setRuleConfig(dbRuleConfig);
        int rst = zeusPublishRuleManager.updateZPublishRule(publishRule);
        if (rst > 0) {
            return Result.success(publishRule.getId());
        } else {
            return Result.failed(ErrorCode.SYSTEM_ERROR.getCode(), "更新数据库错误");
        }
    }

    /**
     * 绑定表, 返回表的相关信息
     * @param publishBindDTO
     * @return
     */
    @RequestMapping("/publish/db/bind.do")
    @ResponseBody
    public String bindTable(
            PublishBindDTO publishBindDTO
    ) {
        if (StringUtils.isEmpty(publishBindDTO.getTable())
                || publishBindDTO.getPublishRuleId() == null
                || publishBindDTO.getTaskId() == null) {
            return Result.failed(ErrorCode.REQUEST_PARAM_ERROR.getCode(), "参数错误");
        }
        ZPublishRule publishRule = zeusPublishRuleManager.getPublishRulById(publishBindDTO.getPublishRuleId());
        if (publishRule == null
                || publishRule.getRuleConfig() == null
                ||  publishRule.getRuleConfig().getType() != Constants.PUBLISH_RULE_DB) {
            return Result.failed(ErrorCode.PUBLISH_RULE_ERROR_NULL.getCode(), "请先正确配置发布内容配置");
        }
        DbPublishRuleConfig dbPublishRuleConfig = (DbPublishRuleConfig) publishRule.getRuleConfig();
        if (dbPublishRuleConfig.getConnectionConfig() == null) {
            return Result.failed(ErrorCode.PUBLISH_RULE_ERROR_DB_CONFIG_NULL.getCode(), "请先正确配置发布内容配置");
        }

        try {
            return Result.success(
                    mysqlManager.getColumnNames(dbPublishRuleConfig.getConnectionConfig(), publishBindDTO.getTable()));
        } catch (SQLException e) {
            return Result.failed(ErrorCode.DB_ERROR_CONNECTION.getCode(), e.getMessage());
        } catch (Exception e) {
            return Result.failed(ErrorCode.SYSTEM_ERROR.getCode(), "系统发生异常,请刷新后重试");
        }
    }

    @PostMapping("/publish/db/schema.info")
    @ResponseBody
    public String getDBSchema(
            PublishDTO publishDTO
    ) {
        if (StringUtils.isEmpty(publishDTO.getHost())
                || StringUtils.isEmpty(publishDTO.getPort())
                || StringUtils.isEmpty(publishDTO.getUser())
//                || StringUtils.isEmpty(publishDTO.getSchema())
//                || StringUtils.isEmpty(publishDTO.getCharset())
        ) {
            return Result.failed(ErrorCode.REQUEST_PARAM_ERROR.getCode(), "数据库参数错误");
        }
        try {
            List<String> schemas = mysqlManager.getSchemas(publishDTO.getHost(), publishDTO.getPort(),
                    "utf8", publishDTO.getUser(), publishDTO.getPwd());
            return Result.success(schemas);
        } catch (SQLException throwables) {
            return Result.failed(ErrorCode.DB_ERROR_CONNECTION.getCode(), throwables.getMessage());
        }
    }

    @PostMapping("/publish/db/test.do")
    @ResponseBody
    public String testDBConnection(
            PublishDTO publishDTO
            ) {
        log.error(JSONObject.toJSONString(publishDTO));
        if (StringUtils.isEmpty(publishDTO.getHost())
            || StringUtils.isEmpty(publishDTO.getPort())
            || StringUtils.isEmpty(publishDTO.getUser())
            || StringUtils.isEmpty(publishDTO.getSchema())
//            || StringUtils.isEmpty(publishDTO.getCharset())
        ) {
            return Result.failed(ErrorCode.REQUEST_PARAM_ERROR.getCode(), "数据库参数错误");
        }
        try {
            mysqlManager.testConnection(publishDTO.getHost(), publishDTO.getPort()
                    , publishDTO.getSchema(), publishDTO.getCharset()
                    , publishDTO.getUser(), publishDTO.getPwd());
            return Result.success();
        } catch (SQLException throwables) {
            return Result.failed(ErrorCode.DB_ERROR_CONNECTION.getCode(), throwables.getMessage());
        }
    }

    private ZPublishRule checkAndGetPublishRule(Integer taskId, Integer ruleId) throws Exception {
        //TODO 走cache
        if ((taskId == null || taskId == 0) && (ruleId == null || ruleId == 0)) {
            throw new Exception("param error");
        }
        ZPublishRule publishRule = null;
        if (ruleId == null || ruleId == 0) {
            publishRule = zeusPublishRuleManager.getPublishRuleByTaskId(taskId);
            if (publishRule == null) {
                publishRule = new ZPublishRule();
                publishRule.setTaskId(taskId);
            }
        } else {
            publishRule = zeusPublishRuleManager.getPublishRulById(ruleId);
            if (publishRule == null) {
                throw new Exception("rule params error");
            }
            if (taskId != null && taskId > 0) {
                if (!taskId.equals(publishRule.getTaskId())) {
                    throw new Exception("task params error");
                }
            }
        }
        return  publishRule;
    }

}
