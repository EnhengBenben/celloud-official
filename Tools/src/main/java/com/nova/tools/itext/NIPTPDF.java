package com.nova.tools.itext;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.io.FileUtils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.nova.tools.itext.utils.WatermarkUtil;
import com.nova.tools.utils.FileTools;
import com.nova.tools.utils.PropertiesUtils;

public class NIPTPDF {

    /**
     * @param path
     *            ：目录到 dataKey
     * @param appName
     *            ：appName
     * @param posX
     *            ：水印位置，X轴
     * @param posY
     *            ：水印位置，Y轴
     * @throws Exception
     */
    public static void createPDF(String path, String appName, int posX, int posY)
	    throws Exception {
	path = path.endsWith("/") ? path : path + "/";
	String png = FileTools.fileExist(path, "mini.png", "endsWith");
	String report = FileTools
		.fileExist(path, "Data_report.txt", "endsWith");
	String zscore = FileTools.fileExist(path, "Zscore.txt", "endsWith");
	// 定义中文
	BaseFont bfChinese = BaseFont.createFont("STSongStd-Light",
		"UniGB-UCS2-H", false);
	// 定义Title字体样式
	Font titleFont = new Font(bfChinese, 16, Font.BOLD, BaseColor.BLACK);
	// 定义正文字体样式
	Font contextFont = new Font(bfChinese, 12, Font.NORMAL);

	Document doc = new Document();
	FileTools.createFile(path + "temp.pdf");
	PdfWriter.getInstance(doc, new FileOutputStream(path + "temp.pdf"));
	doc.open();

	// ------开始拼接数据-----
	Image img = null;
	// 标题
	Paragraph title = new Paragraph(appName + " 报告", titleFont);
	title.setAlignment(Element.ALIGN_CENTER); // 居中设置
	title.setLeading(1f);// 设置行间距
	doc.add(title);

	title = new Paragraph("数据统计:", contextFont);
	doc.add(title);
	float widths[] = new float[] { 100f, 200f, 80f, 140f, 80f };
	PdfPTable table = new PdfPTable(widths);// 建立一个pdf表格
	table.setTotalWidth(600);// 设置表格的宽度
	table.getDefaultCell().setBorder(1);
	PdfPCell cell = null;
	String context = FileUtils.readFileToString(new File(path + report));
	String c[] = context.split("\n");
	for (int i = 0; i < c.length; i++) {
	    for (String f : c[i].split("\t")) {
		cell = new PdfPCell(new Paragraph(f, contextFont));
		cell.setBorder(Rectangle.BOX);
		table.addCell(cell);
	    }
	}
	doc.add(table);

	title = new Paragraph("报告:", contextFont);
	doc.add(title);
	float width[] = new float[] { 150f, 150f, 150f, 150f };
	table = new PdfPTable(width);// 建立一个pdf表格
	table.setTotalWidth(600);// 设置表格的宽度
	table.getDefaultCell().setBorder(1);
	cell = null;
	context = FileUtils.readFileToString(new File(path + zscore));
	String c2[] = context.split("\n");
	for (int i = 0; i < c2.length; i++) {
	    for (String f : c2[i].split("\t")) {
		cell = new PdfPCell(new Paragraph(f, contextFont));
		cell.setBorder(Rectangle.BOX);
		table.addCell(cell);
	    }
	}
	doc.add(table);

	title = new Paragraph("染色体:", contextFont);
	doc.add(title);
	img = Image.getInstance(path + png);
	img.scaleAbsolute(510f, 160f); // 设置图片大小
	img.setAlignment(Image.ALIGN_LEFT);
	doc.add(img);

	doc.close();
	if (new File(path + "temp.pdf").exists()) {
	    WatermarkUtil.addMark(path + "temp.pdf", path + appName + ".pdf",
		    PropertiesUtils.img, 1, posX, posY);
	}
    }
}