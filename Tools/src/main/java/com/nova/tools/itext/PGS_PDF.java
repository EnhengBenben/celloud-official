package com.nova.tools.itext;

import java.io.File;
import java.io.FileOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

/**
 * @Description:PGS系列流程生成PDF类
 * @author lin
 * @date 2014-5-18 下午4:33:22
 */
public class PGS_PDF {
	/**
	 * @param path
	 *            ：目录到 dataKey
	 * @param appName
	 *            ：appName
	 * @param barcode
	 *            ：barcode
	 * @param anotherName
	 *            ：别名
	 * @param dataKey
	 *            ：datakey
	 * @param posX
	 *            ：水印位置，X轴
	 * @param posY
	 *            ：水印位置，Y轴
	 * @throws Exception
	 */
	public static void createPDF(String path, String appName, String barcode,
			String anotherName, String dataKey, int posX, int posY)
			throws Exception {
		path = path.endsWith("/") ? path : path + "/";
		String errorFile = path + "no_enough_reads.xls";
		if (!FileTools.checkPath(errorFile)) {
			boolean isBigPic = true;
			String finalPng = FileTools
					.fileExist(path, "final.png", "endsWith");
			if (finalPng.equals("")) {
				isBigPic = false;
				finalPng = FileTools.fileExist(path, "mini.png", "endsWith");
			}

			String fileName = null;
			String tableTitle = null;
			String tableContext = null;
			String xls = FileTools.fileExist(path, dataKey+".xls", "endsWith");
			if (!"".equals(xls)) {
				String xlsContext[] = FileUtils.readFileToString(
						new File(path + xls)).split("\n");
				fileName = xlsContext[0];
				tableTitle = xlsContext[1];
				tableContext = xlsContext[2];
			}

			String report = FileUtils.readFileToString(new File(path
					+ "report.txt"));
			// 定义中文
			BaseFont bfChinese = BaseFont.createFont("STSongStd-Light",
					"UniGB-UCS2-H", false);
			// 定义Title字体样式
			Font titleFont = new Font(bfChinese, 16, Font.BOLD, BaseColor.BLACK);

			// 定义正文字体样式（英语）
			BaseFont context = BaseFont.createFont();
			Font contextFont = new Font(context, 12, Font.NORMAL);

			// 定义正文字体样式（汉语）
			BaseFont contextC = BaseFont.createFont("STSongStd-Light",
					"UniGB-UCS2-H", false);
			Font contextFontC = new Font(contextC, 12, Font.NORMAL);

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

			// 文件名
			title = new Paragraph("Name:    " + fileName, contextFont);
			title.setSpacingBefore(25f);// 设置上面空白宽度
			doc.add(title);

			// barcode
			title = new Paragraph("barcode:    " + barcode, contextFont);
			doc.add(title);

			// 别名
			Pattern p_str = Pattern.compile("[\\u4e00-\\u9fa5]+");
			Matcher m = p_str.matcher(anotherName);
			if (m.find()) {
				title = new Paragraph("s a m p l e_n a m e :    " + anotherName, contextFontC);
			} else {
				title = new Paragraph("sample_name:    " + anotherName, contextFont);
			}
			doc.add(title);

			// 表格
			title = new Paragraph("Data:", contextFont);
			doc.add(title);
			float widths[] = null;// 设置表格的列宽和列数
			if ("Sureplex_HR".equals(appName) || "MDA_HR".equals(appName)
					|| "gDNA_HR".equals(appName) || "MalBac".equals(appName)) {
				widths = new float[] { 100f, 100f, 100f, 100f, 100f };
			} else {
				widths = new float[] { 120f, 110f, 130f, 120f, 130f, 60f };
			}
			PdfPTable table = new PdfPTable(widths);// 建立一个pdf表格
			table.getDefaultCell().setBorder(1);
			PdfPCell cell = null;
			for (String f : tableTitle.split("\t")) {
				cell = new PdfPCell(new Paragraph(f, contextFont));
				cell.setBorder(Rectangle.BOX);
				table.addCell(cell);
			}
			for (String f : tableContext.split("\t")) {
				cell = new PdfPCell(new Paragraph(f, contextFont));
				cell.setBorder(Rectangle.BOX);
				table.addCell(cell);
			}
			doc.add(table);

			title = new Paragraph("Result:\n         "
					+ report.replace("\t", "    "), contextFont);
			doc.add(title);

			img = Image.getInstance(path + finalPng);
			if (isBigPic) {
				img.scaleAbsolute(382f, 500f); // 设置图片大小
			} else {
				img.scaleAbsolute(500f, 104f); // 设置图片大小
			}
			img.setAlignment(Image.ALIGN_LEFT);
			doc.add(img);

			doc.close();
			if (new File(path + "temp.pdf").exists()) {
				WatermarkUtil.addMark(path + "temp.pdf", path + dataKey
						+ ".pdf", PropertiesUtils.img, 1, posX, posY);
			}
		}
	}
}