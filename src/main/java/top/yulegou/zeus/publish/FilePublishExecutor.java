package top.yulegou.zeus.publish;/*
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

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.yulegou.zeus.constant.Constants;
import top.yulegou.zeus.dao.domain.ZPublishRule;
import top.yulegou.zeus.dao.domain.ZTask;
import top.yulegou.zeus.dao.domain.ZTaskConfig;
import top.yulegou.zeus.dao.domain.publish.FilePublishRuleConfig;
import top.yulegou.zeus.dao.domain.publish.ZBasePublishRuleConfig;
import top.yulegou.zeus.domain.ContentCollectedDTO;
import top.yulegou.zeus.domain.PublishResult;
import top.yulegou.zeus.manager.ZeusConfigManager;
import top.yulegou.zeus.manager.http.HttpExecutorManager;
import top.yulegou.zeus.util.ExcelUtil;
import top.yulegou.zeus.util.ZeusBeanUtil;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author irisroyalty
 * @date 2020/6/24
 **/
@Slf4j
public class FilePublishExecutor implements BasePublishExecutor {
    @Autowired
    private HttpExecutorManager httpExecutorManager;

    private static FilePublishExecutor filePublishExecutor = new FilePublishExecutor();
    public static FilePublishExecutor getPublish() {
        if (filePublishExecutor.httpExecutorManager == null) {
            filePublishExecutor.httpExecutorManager = ZeusBeanUtil.getBean(HttpExecutorManager.class);
        }
        return filePublishExecutor;
    }
    private FilePublishExecutor() {}

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public int getPublishType() {
        return Constants.PUBLISH_RULE_FILE;
    }

    @Override
    public String getStringPublishType() {
        return "FILE";
    }

    @Override
    public int publish(List<ContentCollectedDTO> fieldList, ZTask task, ZPublishRule publishRule) {
        ZBasePublishRuleConfig ruleConfig = publishRule.getRuleConfig();
        if (ruleConfig == null || !(ruleConfig instanceof FilePublishRuleConfig)) {
            log.error("publish rule is null or error");
            return 0;
        }
        FilePublishRuleConfig fileConfig = (FilePublishRuleConfig) ruleConfig;
        String fileLocation = fileConfig.getFileLocation();
        if (StringUtils.isEmpty(fileLocation)) {
            log.error("文件目录为空");
            return 0;
        }
        fileLocation = fileLocation + task.gettName();
        //TODO 配置的时候确保最后是一个/  先是GROUPname 然后再是taskName
//        if (StringUtils.endsWithIgnoreCase(fileLocation, "/"))
        File dir = new File(fileLocation) ;
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                log.error("创建文件夹错误,请确认目录权限和磁盘空间");
                return 0;
            }
        }
        File f = null;
        FileOutputStream outputStream = null;
        Workbook workbook = null;
        Sheet sheet = null;
        if (StringUtils.equalsIgnoreCase(((FilePublishRuleConfig) ruleConfig).getFileType(), "txt" )) {
            f = new File(fileLocation + "/" + sdf.format(new Date()) + ".txt");
        } else if (StringUtils.equalsIgnoreCase(((FilePublishRuleConfig) ruleConfig).getFileType(), "xls" )) {
            f = new File(fileLocation + "/" + sdf.format(new Date()) + ".xls");
            workbook = ExcelUtil.createExcelWorkbook(ExcelUtil.EXCEL_2003);
            if (fieldList != null && !fieldList.isEmpty()) {
                sheet = ExcelUtil.buildDataSheet(workbook, new ArrayList<>(fieldList.get(0).getFieldsRst().keySet()), task.gettName());
            }
        } else if (StringUtils.equalsIgnoreCase(((FilePublishRuleConfig) ruleConfig).getFileType(), "xlsx" )) {
            f = new File(fileLocation + "/" + sdf.format(new Date()) + ".xlsx");
            workbook = ExcelUtil.createExcelWorkbook(ExcelUtil.EXCEL_2007);
            if (fieldList != null && !fieldList.isEmpty()) {
                sheet = ExcelUtil.buildDataSheet(workbook, new ArrayList<>(fieldList.get(0).getFieldsRst().keySet()), task.gettName());
            }
        }
        try {
            outputStream = new FileOutputStream(f, true);
            Integer transfer = ZeusConfigManager.getConfigDetail(Constants.ZCONFIG_IMAGE_CONFIG, Constants.ZCONFIG_IMAGE_TRANSFER);
            String ImageDir = ZeusConfigManager.getConfigDetail(Constants.ZCONFIG_IMAGE_CONFIG, Constants.ZCONFIG_IMAGE_DIR);

            int rstCount = 0;
            for (ContentCollectedDTO collectedDTO : fieldList) {
                if (collectedDTO.getFieldsRst() == null || collectedDTO.getFieldsRst().isEmpty()) {
                    continue;
                }
                List<String> contentList = new ArrayList<>();
                for (Iterator<Map.Entry<String, String>> fit = collectedDTO.getFieldsRst().entrySet().iterator();
                     fit.hasNext();) {
                    Map.Entry<String, String> entryField = fit.next();
                    String fieldContent = entryField.getValue();
                    if (StringUtils.isNotEmpty(fieldContent)) {
                        if (transfer != null && transfer == 1) {
                            for (Iterator<String> it = collectedDTO.getFieldImages(entryField.getKey()).iterator();
                                 it.hasNext(); ) {
                                String imageUrl = it.next();
                                String newImage = collectedDTO.getDownload(imageUrl);
                                if (StringUtils.isNotEmpty(newImage)) {
                                    fieldContent = RegExUtils.replaceAll(fieldContent, imageUrl, newImage);
                                } else {
                                    newImage = httpExecutorManager.download(imageUrl, ImageDir + "/" + task.gettName());
                                    if (StringUtils.isNotEmpty(newImage)) {
                                        fieldContent = RegExUtils.replaceAll(fieldContent, imageUrl, newImage);
                                        collectedDTO.addDownLoad(imageUrl, newImage);
                                    } else {
                                        log.error("图片下载失败，没有替换 " + imageUrl);
                                    }
                                }
                            }
                        }
                        contentList.add(fieldContent);
                    } else {
                        contentList.add("");
                    }
                }
                if (StringUtils.equalsIgnoreCase(((FilePublishRuleConfig) ruleConfig).getFileType(), "txt" )) {
                    outputStream.write(StringUtils.join(contentList, "\t").getBytes());
                    outputStream.write("\r\n".getBytes());
                    outputStream.flush();
                } else if (StringUtils.equalsIgnoreCase(((FilePublishRuleConfig) ruleConfig).getFileType(), "xls" )) {
                    Row row = sheet.createRow(rstCount);
                    ExcelUtil.convertDataToRow(contentList, row);
                } else if (StringUtils.equalsIgnoreCase(((FilePublishRuleConfig) ruleConfig).getFileType(), "xlsx" )) {
                    Row row = sheet.createRow(rstCount);
                    ExcelUtil.convertDataToRow(contentList, row);
                }
                rstCount ++;
            }

           if (StringUtils.equalsIgnoreCase(((FilePublishRuleConfig) ruleConfig).getFileType(), "xls" )) {
               workbook.write(outputStream);
               outputStream.flush();
            } else if (StringUtils.equalsIgnoreCase(((FilePublishRuleConfig) ruleConfig).getFileType(), "xlsx" )) {
               workbook.write(outputStream);
               outputStream.flush();
            }
            return rstCount;
        } catch (Exception e) {
            log.error("write file error ", e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }

    @Override
    public PublishResult publish(ContentCollectedDTO collectedDTO, ZTask task, ZPublishRule publishRule) {
        ZBasePublishRuleConfig ruleConfig = publishRule.getRuleConfig();
        if (ruleConfig == null || !(ruleConfig instanceof FilePublishRuleConfig)) {
            log.error("publish rule is null or error");
            return PublishResult.failed();
        }
        FilePublishRuleConfig fileConfig = (FilePublishRuleConfig) ruleConfig;
        String fileLocation = fileConfig.getFileLocation();
        if (StringUtils.isEmpty(fileLocation)) {
            log.error("文件目录为空");
            return PublishResult.failed();
        }
        fileLocation = fileLocation + task.gettName() ;
        //TODO 配置的时候确保最后是一个/  先是GROUPname 然后再是taskName
//        if (StringUtils.endsWithIgnoreCase(fileLocation, "/"))
        File dir = new File(fileLocation) ;
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                log.error("创建文件夹错误,请确认目录权限和磁盘空间");
                return PublishResult.failed("创建文件夹错误,请确认目录权限和磁盘空间");
            }
        }
        File f = null;
        FileOutputStream outputStream = null;
        Workbook workbook = null;
        Sheet sheet = null;
        int rowNum = 0;
        try {

            if (StringUtils.equalsIgnoreCase(((FilePublishRuleConfig) ruleConfig).getFileType(), "txt" )) {
                f = new File(fileLocation + "/" + sdf.format(new Date()) + ".txt");
            } else if (StringUtils.equalsIgnoreCase(((FilePublishRuleConfig) ruleConfig).getFileType(), "xls" )) {
                f = new File(fileLocation + "/" + sdf.format(new Date()) + ".xls");
                if (f.exists()) {
                    FileInputStream inputStream = new FileInputStream(f);
                    try {
                        workbook = new HSSFWorkbook(inputStream);
                    } catch (Exception e1) {
                        log.error("read excel error", e1);
                        workbook = ExcelUtil.createExcelWorkbook(ExcelUtil.EXCEL_2003);
                    }
                } else {
                    workbook = ExcelUtil.createExcelWorkbook(ExcelUtil.EXCEL_2003);
                }
                //TODO sheetname 为tskname + 配置version 可能的字段变更会导致excel的字段不一致
                sheet = workbook.getSheet(task.gettName());
                if (sheet == null) {
                    sheet = ExcelUtil.buildDataSheet(workbook, new ArrayList<>(collectedDTO.getFieldsRst().keySet()), task.gettName());
                }
                rowNum = sheet.getLastRowNum();
            } else if (StringUtils.equalsIgnoreCase(((FilePublishRuleConfig) ruleConfig).getFileType(), "xlsx" )) {
                f = new File(fileLocation + "/" + sdf.format(new Date()) + ".xlsx");
                if (f.exists()) {
                    FileInputStream inputStream = new FileInputStream(f);
                    try {
                        workbook = new XSSFWorkbook(inputStream);
                    } catch (Exception e1) {
                        log.error("read excel error", e1);
                        workbook = ExcelUtil.createExcelWorkbook(ExcelUtil.EXCEL_2007);
                    }                } else {
                    workbook = ExcelUtil.createExcelWorkbook(ExcelUtil.EXCEL_2007);
                }
                sheet = workbook.getSheet(task.gettName());
                if (sheet == null) {
                    sheet = ExcelUtil.buildDataSheet(workbook, new ArrayList<>(collectedDTO.getFieldsRst().keySet()), task.gettName());
                }
                rowNum = sheet.getLastRowNum();
            }
            outputStream = new FileOutputStream(f);
            Integer transfer = ZeusConfigManager.getConfigDetail(Constants.ZCONFIG_IMAGE_CONFIG, Constants.ZCONFIG_IMAGE_TRANSFER);
            String ImageDir = ZeusConfigManager.getConfigDetail(Constants.ZCONFIG_IMAGE_CONFIG, Constants.ZCONFIG_IMAGE_DIR);

            if (collectedDTO.getFieldsRst() == null || collectedDTO.getFieldsRst().isEmpty()) {
                return PublishResult.failed();
            }
            List<String> contentList = new ArrayList<>();
            for (Iterator<Map.Entry<String, String>> fit = collectedDTO.getFieldsRst().entrySet().iterator();
                 fit.hasNext();) {
                Map.Entry<String, String> entryField = fit.next();
                String fieldContent = entryField.getValue();
                if (StringUtils.isNotEmpty(fieldContent)) {
                    if (transfer != null && transfer == 1) {
                        for (Iterator<String> it = collectedDTO.getFieldImages(entryField.getKey()).iterator();
                             it.hasNext(); ) {
                            String imageUrl = it.next();
                            String newImage = collectedDTO.getDownload(imageUrl);
                            if (StringUtils.isNotEmpty(newImage)) {
                                fieldContent = RegExUtils.replaceAll(fieldContent, imageUrl, newImage);
                            } else {
                                newImage = httpExecutorManager.download(imageUrl, ImageDir + "/" + task.gettName());
                                if (StringUtils.isNotEmpty(newImage)) {
                                    fieldContent = RegExUtils.replaceAll(fieldContent, imageUrl, newImage);
                                    collectedDTO.addDownLoad(imageUrl, newImage);
                                } else {
                                    log.error("图片下载失败，没有替换 " + imageUrl);
                                }
                            }
                        }
                    }
                    contentList.add(fieldContent);
                } else {
                    contentList.add("");
                }
            }
            if (StringUtils.equalsIgnoreCase(((FilePublishRuleConfig) ruleConfig).getFileType(), "txt" )) {
                outputStream.write(StringUtils.join(contentList, "\t").getBytes());
                outputStream.write("\r\n".getBytes());
                outputStream.flush();
            } else if (StringUtils.equalsIgnoreCase(((FilePublishRuleConfig) ruleConfig).getFileType(), "xls" )) {
                Row row = sheet.createRow(rowNum + 1);
                ExcelUtil.convertDataToRow(contentList, row);
                workbook.write(outputStream);
                outputStream.flush();
            } else if (StringUtils.equalsIgnoreCase(((FilePublishRuleConfig) ruleConfig).getFileType(), "xlsx" )) {
                Row row = sheet.createRow(rowNum + 1);
                ExcelUtil.convertDataToRow(contentList, row);
                workbook.write(outputStream);
                outputStream.flush();
            }
            return PublishResult.successWithMsg(f.getAbsolutePath());
        } catch (Exception e) {
            log.error("write file error ", e);
            return PublishResult.failed(e.getMessage());
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
