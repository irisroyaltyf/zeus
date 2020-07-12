package top.yulegou.zeus.util;/*
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

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

/**
 * @author irisroyalty
 * @date 2020/7/12
 **/
public class ExcelUtil {
    public static final String EXCEL_2003 = "excel_2003";
    public static final String EXCEL_2007 = "excel_2007";

    public static Workbook createExcelWorkbook(String type) {
        if(StringUtils.equalsIgnoreCase(type, EXCEL_2003)) {
            return new HSSFWorkbook();
        } else if (StringUtils.equalsIgnoreCase(type, EXCEL_2007)) {
            return new XSSFWorkbook();
        }
        return null;
    }

    /**
     * 生成sheet表，并写入第一行数据（列头）
     * @param workbook 工作簿对象
     * @return 已经写入列头的Sheet
     */
    public static Sheet buildDataSheet(Workbook workbook, List<String> headers, String name) {
        Sheet sheet = workbook.createSheet(name);
        if (headers == null || headers.isEmpty()) {
            return null;
        }
        // 设置列头宽度
        for (int i = 0; i < headers.size(); i++) {
            sheet.setColumnWidth(i, 4000);
        }
        // 设置默认行高
        sheet.setDefaultRowHeight((short) 400);
        // 构建头单元格样式
        CellStyle cellStyle = buildHeadCellStyle(sheet.getWorkbook());
        // 写入第一行各列的数据
        Row head = sheet.createRow(0);
        for (int i = 0; i < headers.size(); i++) {
            Cell cell = head.createCell(i);
            cell.setCellValue(headers.get(i));
            cell.setCellStyle(cellStyle);
        }
        return sheet;
    }

    /**
     * 将数据转换成行
     * @param data 源数据
     * @param row 行对象
     * @return
     */
    public static void convertDataToRow(List<String> data, Row row){
        int cellNum = 0;
        Cell cell;
        for (String d : data) {
            cell = row.createCell(cellNum++);
            cell.setCellValue(d);
        }
    }

    /**
     * 设置第一行列头的样式
     * @param workbook 工作簿对象
     * @return 单元格样式对象
     */
    private static CellStyle buildHeadCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        //对齐方式设置
        style.setAlignment(HorizontalAlignment.CENTER);
        //边框颜色和宽度设置
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex()); // 下边框
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex()); // 左边框
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex()); // 右边框
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex()); // 上边框
        //设置背景颜色
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //粗体字设置
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }
}
