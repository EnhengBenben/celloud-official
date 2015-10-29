package com.nova.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class ExcelUtil {
    private static Logger log = Logger.getLogger(ExcelUtil.class);

    /**
     * 将 txt 转化成 excel 的简单方法
     * 
     * @rule txt 的每一行即对应 excel 的每一行；txt 每一行以 Tab 键间隔作为每一个单元格
     * @param txtPath
     *            ：txt 路径
     * @param excelPath
     *            ：excel 路径
     * @param sheetName
     *            ：sheet 的名字，可为空
     */
    public static void simpleTxtToExcel(String txtPath, String excelPath,
            String sheetName) {
        if (!new File(excelPath).exists()) {
            FileTools.createFile(excelPath);
        }
        log.info(txtPath + "  txtToExcel---start");
        File file = new File(txtPath);
        if (file.exists()) {
            // 声明一个工作薄
            HSSFWorkbook workbook = new HSSFWorkbook();
            // 生成一个表格
            HSSFSheet sheet = workbook.createSheet((sheetName == null || ""
                    .equals(sheetName.trim())) ? "sheet 1" : sheetName);
            // 设置列宽和行高
            sheet.setDefaultColumnWidth(20);
            // 生成正文样式
            HSSFCellStyle context = workbook.createCellStyle();
            // 设置正文样式
            context.setFillForegroundColor(HSSFColor.WHITE.index);// 设置背景色
            context.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            context.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            context.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            context.setBorderRight(HSSFCellStyle.BORDER_THIN);
            context.setBorderTop(HSSFCellStyle.BORDER_THIN);
            context.setAlignment(HSSFCellStyle.ALIGN_LEFT);
            // 生成正文字体
            HSSFFont font = workbook.createFont();
            font.setColor(HSSFColor.BLACK.index);
            font.setFontHeightInPoints((short) 10);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
            // 把字体应用到当前的样式
            context.setFont(font);

            int i = 0;
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException e1) {
                log.error("要转化的txt文件不存在：" + txtPath);
            }
            String line = "";
            try {
                while ((line = br.readLine()) != null) {
                    HSSFRow row = sheet.createRow(i);
                    String[] data = line.split("\t");
                    for (int j = 0; j < data.length; j++) {
                        HSSFCell cell = row.createCell(j);
                        cell.setCellStyle(context);
                        cell.setCellValue(new HSSFRichTextString(data[j]));
                    }
                    i++;
                }
            } catch (IOException e1) {
                log.error("遍历txt失败：" + e1);
            }
            FileTools.createFile(excelPath);
            OutputStream out = null;
            try {
                out = new FileOutputStream(excelPath);
            } catch (FileNotFoundException e1) {
                log.error("要输出的 excel 文件不存在：" + excelPath);
            }
            try {
                workbook.write(out);
            } catch (IOException e) {
                log.error("输出 Excel 出错：" + e);
            }
            try {
                out.close();
            } catch (IOException e) {
                log.error("关闭 Excel 输出流出错：" + e);
            }
            log.info("simpleTxtToExcel-----end");
        } else {
            log.error("要转化的txt文件不存在：" + txtPath);
        }
    }

}