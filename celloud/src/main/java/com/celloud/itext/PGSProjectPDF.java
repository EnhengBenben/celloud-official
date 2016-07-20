package com.celloud.itext;

import java.io.File;
import java.io.FileOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.celloud.itext.utils.WatermarkUtil;
import com.celloud.utils.FileTools;
import com.celloud.utils.PropertiesUtil;
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

public class PGSProjectPDF {

	/**
	 * @param path
	 *            ：目录到 app
	 * @param appName
	 *            ：appName
	 * @param posX
	 *            ：水印位置，X轴
	 * @param posY
	 *            ：水印位置，Y轴
	 * @throws Exception
	 */
	public static void createPDF(String path, String appName, int posX, int posY, String keys, String projectId)
			throws Exception {
		path = path.endsWith("/") ? path : path + "/";
		// 定义中文
		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
		// 定义Title字体样式
		Font titleFont = new Font(bfChinese, 16, Font.BOLD, BaseColor.BLACK);

		// 定义正文字体样式（英语）
		BaseFont context = BaseFont.createFont();
		Font contextFont = new Font(context, 12, Font.NORMAL);

		// 定义正文字体样式（汉语）
		BaseFont contextC = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
		Font contextFontC = new Font(contextC, 12, Font.NORMAL);

		Document doc = new Document();
		PdfWriter.getInstance(doc, new FileOutputStream(path + projectId + "/temp.pdf"));
		doc.open();

		// ------开始拼接数据-----
		// 标题
		Paragraph title = new Paragraph(appName + " 报告", titleFont);
		title.setAlignment(Element.ALIGN_CENTER); // 居中设置
		title.setLeading(1f);// 设置行间距
		doc.add(title);

		String key[] = keys.split(";");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < key.length; i++) {
			String info[] = key[i].split(",");
			String result = path + info[0] + "/";
			String errorFile = result + "no_enough_reads.xls";
			if (!FileTools.checkPath(errorFile)) {
				boolean isBigPic = true;
				String finalPng = FileTools.fileExist(result, "final.png", "endsWith");
				if (finalPng.equals("")) {
					isBigPic = false;
					finalPng = FileTools.fileExist(path, "mini.png", "endsWith");
				}
				String fileName = "";
				String tableTitle = "";
				String tableContext = "";
				String xls = FileTools.fileExist(result, info[0] + ".xls", "endsWith");
				if (!"".equals(xls)) {
					String xlsContext[] = FileUtils.readFileToString(new File(result + xls)).split("\n");
					fileName = xlsContext[0];
					tableTitle = xlsContext[1];
					tableContext = xlsContext[2];
				}
				File r = new File(result + "report.txt");
				String report = "";
				if(r.exists()){
					report = FileUtils.readFileToString(r);
				}

				// 文件名
				title = new Paragraph("Name:    " + fileName, contextFont);
				title.setSpacingBefore(25f);// 设置上面空白宽度
				doc.add(title);

				// barcode
				title = new Paragraph("barcode:    " + info[1], contextFont);
				doc.add(title);

				// 别名
				Pattern p_str = Pattern.compile("[\\u4e00-\\u9fa5]+");
				String an = FileTools.getArray(info, 2);
				if (an == null)
					an = "";
				Matcher m = p_str.matcher(an);
				if (m.find()) {
					title = new Paragraph("s a m p l e_n a m e :    " + an, contextFontC);
				} else {
					title = new Paragraph("sample_name:    " + an, contextFont);
				}
				sb.append(result).append(",").append(finalPng).append(",").append(an).append(",").append(isBigPic)
						.append(";");
				doc.add(title);

				title = new Paragraph("Data:", contextFont);
				doc.add(title);
				float widths[] = null;// 设置表格的列宽和列数
				if ("SurePlex".equals(appName) || "MDA_HR".equals(appName) || "gDNA_HR".equals(appName)
						|| "MalBac".equals(appName)) {
					widths = new float[] { 100f, 100f, 100f, 100f, 100f };
				} else {
					widths = new float[] { 120f, 125f, 130f, 120f, 130f, 65f };
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
				title = new Paragraph("Result:\n         " + report.replace("\t", "    "), contextFont);
				doc.add(title);
			}
		}
		if(StringUtils.isNotEmpty(sb.toString())){
			doc.newPage();
			String[] imgString = sb.toString().split(";");
			int imgs = imgString.length;
			boolean isOld = false;
			if (imgs % 2 == 1) {
				isOld = true;
				imgs = imgs - 1;
			}
			Pattern p_str = Pattern.compile("[\\u4e00-\\u9fa5]+");
			for (int i = 0; i < imgs; i = i + 2) {
				String detail1[] = imgString[i].split(",");
				String result1 = detail1[0];
				String png1 = FileTools.getArray(detail1, 1);
				String sm1 = FileTools.getArray(detail1, 2);
				String isBigPic1 = FileTools.getArray(detail1, 3);
				Matcher m1 = p_str.matcher(sm1);
				Paragraph title1 = null;
				Image img1 = null;
				if (m1.find()) {
					title1 = new Paragraph("s a m p l e_n a m e :    " + sm1, contextFontC);
				} else {
					title1 = new Paragraph("sample_name:    " + sm1, contextFont);
				}
				if (!result1.equals("") && StringUtils.isNotEmpty(png1)) {
					img1 = Image.getInstance(result1 + png1);
					if (isBigPic1.equals("true")) {
						img1.scaleAbsolute(208, 272); // 设置图片大小
					} else {
						img1.scaleAbsolute(500f, 104f); // 设置图片大小
					}
					img1.setAlignment(Image.ALIGN_LEFT);
				}
				
				String detail2[] = imgString[i + 1].split(",");
				String result2 = FileTools.getArray(detail2, 0);
				String png2 = FileTools.getArray(detail2, 1);
				String sm2 = FileTools.getArray(detail2, 2);
				String isBigPic2 = FileTools.getArray(detail2, 3);
				Matcher m2 = p_str.matcher(sm2);
				Paragraph title2 = null;
				Image img2 = null;
				if (m2.find()) {
					title2 = new Paragraph("s a m p l e_n a m e :    " + sm2, contextFontC);
				} else {
					title2 = new Paragraph("sample_name:    " + sm2, contextFont);
				}
				if (!result2.equals("") && StringUtils.isNotEmpty(png2)) {
					img2 = Image.getInstance(result2 + png2);
					if (isBigPic2.equals("true")) {
						img2.scaleAbsolute(208, 272); // 设置图片大小
					} else {
						img2.scaleAbsolute(500f, 104f); // 设置图片大小
					}
					img2.setAlignment(Image.ALIGN_LEFT);
				}
				
				float widths[] = new float[] { 500f, 500f };
				PdfPTable table = new PdfPTable(widths);// 建立一个pdf表格
				PdfPCell cell = null;
				cell = new PdfPCell(title1);
				cell.setBorderWidth(0);
				table.addCell(cell);
				cell = new PdfPCell(title2);
				cell.setBorderWidth(0);
				table.addCell(cell);
				if(img1!=null){
					cell = new PdfPCell(img1);
					cell.setBorderWidth(0);
					table.addCell(cell);
				}
				if(img2!=null){
					cell = new PdfPCell(img2);
					cell.setBorderWidth(0);
					table.addCell(cell);
					doc.add(table);
				}
				if (i % 4 == 2) {
					doc.newPage();
				}
			}
			if (isOld) {
				String detail1[] = imgString[imgs].split(",");
				String result1 = FileTools.getArray(detail1, 0);
				String png1 = FileTools.getArray(detail1, 1);
				String sm1 = FileTools.getArray(detail1, 2);
				String isBigPic1 = FileTools.getArray(detail1, 3);
				Matcher m1 = p_str.matcher(sm1);
				Paragraph title1 = null;
				Image img1 = null;
				if (m1.find()) {
					title1 = new Paragraph("s a m p l e_n a m e :    " + sm1, contextFontC);
				} else {
					title1 = new Paragraph("sample_name:    " + sm1, contextFont);
				}
				if (!result1.equals("") && StringUtils.isNotEmpty(png1)) {
					img1 = Image.getInstance(result1 + png1);
					if (isBigPic1.equals("true")) {
						img1.scaleAbsolute(208, 272); // 设置图片大小
					} else {
						img1.scaleAbsolute(500f, 104f); // 设置图片大小
					}
					img1.setAlignment(Image.ALIGN_LEFT);
				}
				float widths[] = new float[] { 500f };
				PdfPTable table = new PdfPTable(widths);// 建立一个pdf表格
				PdfPCell cell = null;
				cell = new PdfPCell(title1);
				cell.setBorderWidth(0);
				table.addCell(cell);
				if(img1!=null){
					cell = new PdfPCell(img1);
					cell.setBorderWidth(0);
					table.addCell(cell);
				}
				doc.add(table);
			}
		}
		doc.close();
		if (new File(path + projectId + "/temp.pdf").exists()) {
			WatermarkUtil.addMark(path + projectId + "/temp.pdf", path + projectId + "/" + projectId + ".pdf",
					PropertiesUtil.img, 1, posX, posY);
		}
	}
}