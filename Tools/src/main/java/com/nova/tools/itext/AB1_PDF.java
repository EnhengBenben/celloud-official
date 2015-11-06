package com.nova.tools.itext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.nova.tools.itext.utils.WatermarkUtil;
import com.nova.tools.utils.FileTools;
import com.nova.tools.utils.PropertiesUtils;

/**
 * @Description:EGFR 生成 pdf
 * @author lin
 * @date 2014-1-13 上午11:19:45
 */
public class AB1_PDF {
    /**
     * @param path
     *            :目录到 datakey/
     * @param fileName
     * @param appName
     * @param posX
     *            :水印X轴位置
     * @param posY
     *            :水印Y轴位置
     * @throws DocumentException
     * @throws IOException
     */
    public static void createPDF(String path, String fileName, String appName,
	    int posX, int posY) throws DocumentException, IOException {
	// 定义中文
	BaseFont bfChinese = BaseFont.createFont("STSongStd-Light",
		"UniGB-UCS2-H", false);
	// 定义Title字体样式
	Font titleFont = new Font(bfChinese, 16, Font.BOLD, BaseColor.BLACK);
	// 定义正文字体样式
	Font contextFont = new Font(bfChinese, 12, Font.NORMAL);

	// 构造pdf
	Document doc = new Document();
	FileTools.createFile(path + "temp.pdf");
	PdfWriter.getInstance(doc, new FileOutputStream(path + "temp.pdf"));
	doc.open();

	// ------开始拼接数据-----
	Image img = null;
	String transfer = null;
	// 标题
	Paragraph title = new Paragraph(appName + " 报告", titleFont);
	title.setAlignment(Element.ALIGN_CENTER); // 居中设置
	title.setLeading(1f);// 设置行间距
	doc.add(title);

	// 文件名
	title = new Paragraph("File Name:" + fileName, contextFont);
	title.setSpacingBefore(25f);// 设置上面空白宽度
	doc.add(title);

	title = new Paragraph("Report:", contextFont);
	doc.add(title);

	String result = FileUtils.readFileToString(new File(path
		+ "/report.txt"), "GBK");
	title = new Paragraph(result.replace("\t", "    "), contextFont);
	doc.add(title);

	title = new Paragraph("检测结果：", contextFont);
	doc.add(title);

	// 所有检索结果（大图）
	String big[] = { "1", "2", "3", "4", "5" };
	List<String> total = new ArrayList<String>();
	String svpPath = path + "SVG/";
	for (String all : big) {
	    transfer = FileTools.fileExist(svpPath, all + "_all.png",
		    "endsWith");
	    if (!"".equals(transfer)) {
		total.add(transfer);
	    }
	}

	for (String all : total) {
	    img = Image.getInstance(svpPath + all);
	    img.scaleAbsolute(500f, 100f); // 设置图片大小
	    img.setAlignment(Image.ALIGN_LEFT);// 设置图片居中显示
	    doc.add(img);
	}
	doc.close();
	if (new File(path + "temp.pdf").exists()) {
	    try {
		WatermarkUtil.addMark(path + "temp.pdf", path + appName
			+ ".pdf", PropertiesUtils.img, 1, 230, 800);
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
    }
}