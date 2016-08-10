package com.celloud.backstage.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

/**
 * @author MQ:
 * @date 2016年8月4日 上午10:28:49
 * @description 使用POI对2007版以后的word进行操作
 */
/**
 * 
 * XWPFParagraph：代表一个段落。
 *
 * XWPFRun：代表具有相同属性的一段文本。
 *
 * XWPFTable：代表一个表格。
 *
 * XWPFTableRow：表格的一行。
 *
 * XWPFTableCell：表格对应的一个单元格。
 * 
 * CTTblPr: 表格属性
 * 
 * CTTrPr: 行属性
 * 
 * TcPr: 列属性
 *
 */
public class POIWordUtil {

    /**
     * 
     * @author MQ
     * @date 2016年8月4日下午1:44:43
     * @description 将指定路径下的图片插入到文档中, 只支持bmp,jpeg,png
     *
     */
    public static void insertPicture(XWPFDocument doc, String picPath, int width, int height) {
        try {
            // 获取文件扩展名
            String extension = picPath.substring(picPath.lastIndexOf(".") + 1);
            // 获取文件名称
            String imgFile = picPath.substring(picPath.lastIndexOf(File.separatorChar) + 1);
            // 获取文件格式
            int format = 0;
            if("bmp".equalsIgnoreCase(extension)){
                format = XWPFDocument.PICTURE_TYPE_BMP;
            } else if ("jpg".equalsIgnoreCase(extension) || "jpeg".equalsIgnoreCase(extension)) {
                format = XWPFDocument.PICTURE_TYPE_JPEG;
            } else if ("png".equalsIgnoreCase(extension)) {
                format = XWPFDocument.PICTURE_TYPE_PNG;
            }
            XWPFParagraph p = doc.createParagraph();
            p.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun r = p.createRun();
            r.addBreak();
            r.addPicture(new FileInputStream(picPath), format, imgFile, Units.toEMU(width), Units.toEMU(height));// 200x200
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @author MQ
     * @date 2016年8月8日下午3:38:41
     * @description 强制分页
     * @param doc
     *            文档对象
     *
     */
    public static void pageBreak(XWPFDocument doc) {
        XWPFParagraph p = doc.createParagraph();
        p.setPageBreak(true);
    }

    /**
     * 
     * @author MQ
     * @date 2016年8月4日下午1:42:02
     * @description 默认大小插入一段文字(12号)
     *
     */
    public static void insertText(XWPFDocument doc, String text, ParagraphAlignment align) {
        XWPFParagraph p2 = doc.createParagraph();
        p2.setAlignment(align);

        XWPFRun r2 = p2.createRun();
        r2.setText(text);
        r2.setFontSize(12);
    }

    /**
     * 
     * @author MQ
     * @date 2016年8月4日下午1:42:02
     * @description 默认大小居左插入一段文字(12号)
     *
     */
    public static void insertTextLeft(XWPFDocument doc, String text) {
        XWPFParagraph p2 = doc.createParagraph();
        p2.setAlignment(ParagraphAlignment.LEFT);

        XWPFRun r2 = p2.createRun();
        r2.setText(text);
        r2.setFontSize(12);
    }

    /**
     * 
     * @author MQ
     * @date 2016年8月8日下午3:06:56
     * @description 指定大小居左插入一段内本
     * @param doc
     *            文档对象
     * @param text
     *            文本内容
     * @param size
     *            文本大小
     *
     */
    public static void insertTextLeft(XWPFDocument doc, String text, int size) {
        XWPFParagraph p2 = doc.createParagraph();
        p2.setAlignment(ParagraphAlignment.LEFT);

        XWPFRun r2 = p2.createRun();
        r2.setText(text);
        r2.setFontSize(size);
    }

    /**
     * 
     * @author MQ
     * @date 2016年8月4日下午1:42:02
     * @description 默认大小居左插入一段加粗文字(12号)
     *
     */
    public static void insertTextLeftBold(XWPFDocument doc, String text) {
        XWPFParagraph p2 = doc.createParagraph();
        p2.setAlignment(ParagraphAlignment.LEFT);

        XWPFRun r2 = p2.createRun();
        r2.setText(text);
        r2.setFontSize(12);
        r2.setBold(true);
    }

    /**
     * 
     * @author MQ
     * @date 2016年8月8日下午2:46:57
     * @description 默认大小居左插入一段加粗文字(指定大小)
     * @param doc
     *            文档对象
     * @param text
     *            插入文本
     * @param size
     *            文本大小
     *
     */
    public static void insertTextLeftBold(XWPFDocument doc, String text, int size) {
        XWPFParagraph p2 = doc.createParagraph();
        p2.setAlignment(ParagraphAlignment.LEFT);

        XWPFRun r2 = p2.createRun();
        r2.setText(text);
        r2.setFontSize(size);
        r2.setBold(true);
    }

    /**
     * 
     * @author MQ
     * @date 2016年8月4日下午1:41:22
     * @description 指定大小插入一段文字
     *
     */
    public static void insertText(XWPFDocument doc, String text, ParagraphAlignment align, int size) {
        XWPFParagraph p2 = doc.createParagraph();
        p2.setAlignment(align);

        XWPFRun r2 = p2.createRun();
        r2.setText(text);
        r2.setFontSize(size);
    }

    /**
     * 
     * @author MQ
     * @date 2016年8月4日上午10:58:41
     * @description 跨列合并单元格
     * @param table 目标表格
     * @param rowIndex 行索引,从0开始
     * @param fromCell 起始单元格,从0开始
     * @param toCell 结束单元格,从0开始
     *
     */
    public static void mergeCellsHorizontal(XWPFTable table, int rowIndex, int fromCell, int toCell) {
        XWPFTableRow row = table.getRow(rowIndex);
        row.getCell(fromCell).getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
        for (int i = fromCell+1; i <= toCell; i++) {
            row.getCell(i).getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
        }
    }

    /**
     * 
     * @author MQ
     * @date 2016年8月4日上午11:09:45
     * @description 跨行合并单元格
     *
     */
    public static void mergeCellsVertically(XWPFTable table, int collIndex, int fromRow, int toRow) {
        table.getRow(fromRow).getCell(collIndex).getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
        for (int i = fromRow + 1; i <= toRow; i++) {
            table.getRow(fromRow).getCell(collIndex).getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
        }
    }

    /**
     * 
     * @author MQ
     * @date 2016年8月4日上午11:35:23
     * @description 创建一个指定行数,列数的表格
     *
     */
    public static XWPFTable createTable(XWPFDocument doc, int rows, int cols, String width) {
        XWPFTable table = doc.createTable(rows, cols);

        CTTbl ttbl = table.getCTTbl();
        CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();
        CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
        tblWidth.setW(new BigInteger(width));

        return table;
    }

    /**
     * 
     * @author MQ
     * @date 2016年8月4日下午12:15:24
     * @description 从指定行数开始,将Map中的数据填充到单元格中
     *
     */
    public static void fillTableByMap(XWPFTable table, int rows, List<Map<String, Object>> datas) {
        for (int i = rows; i < rows + datas.size(); i++) {
            Map<String, Object> map = datas.get(i - rows);
            Set<String> keySet = map.keySet();
            int j = 0;
            for (String key : keySet) {
                try {
                    Integer.parseInt(String.valueOf(map.get(key)));
                    setCellTextRight(table, i, j++, String.valueOf(map.get(key)));
                } catch (Exception e) {
                    setCellTextLeft(table, i, j++, String.valueOf(map.get(key)));
                }
            }
        }
    }

    /**
     * 
     * @author MQ
     * @date 2016年8月4日下午4:50:23
     * @description 单元格标题栏,12号,加粗,居中
     *
     */
    public static void setCellTextBoldCenter(XWPFTable table, int row, int col, String text) {
        XWPFTableCell cell = table.getRow(row).getCell(col);
        XWPFParagraph p = cell.getParagraphs().get(0);
        p.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun r = p.createRun();
        r.removeBreak();
        r.setBold(true);
        r.setText(text);
        r.setFontSize(11);
    }

    /**
     * 
     * @author MQ
     * @date 2016年8月4日下午1:29:50
     * @description 设置指定单元格的文本内容
     *
     */
    public static void setCellTextCenter(XWPFTable table, int row, int col, String text) {
        XWPFTableCell cell = table.getRow(row).getCell(col);
        CTTc cttc = cell.getCTTc();
        cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
        cell.setText(text);
    }

    public static void setCellTextLeft(XWPFTable table, int row, int col, String text) {
        XWPFTableCell cell = table.getRow(row).getCell(col);
        CTTc cttc = cell.getCTTc();
        cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.LEFT);
        cell.setText(text);
    }

    public static void setCellTextRight(XWPFTable table, int row, int col, String text) {
        XWPFTableCell cell = table.getRow(row).getCell(col);
        CTTc cttc = cell.getCTTc();
        cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.RIGHT);
        cell.setText(text);
    }


    /**
     * 
     * @author MQ
     * @date 2016年8月4日下午1:37:54
     * @description 保存文档
     *
     */
    public static void saveDocument(XWPFDocument doc, String savePath) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(savePath);
            doc.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                doc.close();
                out.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
