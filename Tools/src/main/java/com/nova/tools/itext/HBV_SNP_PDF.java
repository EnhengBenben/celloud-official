package com.nova.tools.itext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
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
 * @Description:HBV_SNP 流程生成 pdf 方法
 * @author lin
 * @date 2013-12-24 下午3:05:41
 */
public class HBV_SNP_PDF {
	/**
	 * @param snpPath
	 *            :目录到 SVG/
	 * @param fileName
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static void createPDF(String snpPath, String fileName)
			throws DocumentException, IOException {
		Map<String, String> resultMap = new HashMap<String, String>();

		// 位点图片
		String number[] = { "169", "173", "180", "181", "184", "194", "202",
				"204", "236", "250" };
		String site = null;
		for (String num : number) {
			site = FileTools.fileExist(snpPath, num + ".png", "endsWith");
			if (!"".equals(site)) {
				resultMap.put(num, site);
			}
		}

		// 其他
		List<String> other = FileTools
				.fileSearch(snpPath, "new.png", "endWith");
		// 所有检索结果（大图）
		String big[] = { "1", "2", "3", "4","5" };
		List<String> total = new ArrayList<String>();
		for (String all : big) {
			site = FileTools.fileExist(snpPath, all + "_all.png", "endsWith");
			if (!"".equals(site)) {
				total.add(site);
			}
		}
		// 报告
		String result = FileUtils.readFileToString(new File(snpPath
				+ "/Report.txt"), "GBK");
		result = result.split("Other")[0];
		// type
		String snpType = FileTools.readAppoint(snpPath + "/type.txt");

		// 定义中文
		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light",
				"UniGB-UCS2-H", false);
		// 定义Title字体样式
		Font titleFont = new Font(bfChinese, 16, Font.BOLD, BaseColor.BLACK);
		// 定义正文字体样式
		Font contextFont = new Font(bfChinese, 12, Font.NORMAL);

		// 构造pdf
		Document doc = new Document();
		FileTools.createFile(snpPath.replace("SVG/", "") + "/HBV_SNPS.pdf");
		PdfWriter.getInstance(doc,
				new FileOutputStream(snpPath.replace("SVG/", "")
						+ "/HBV_SNPS.pdf"));
		doc.open();

		// ------开始拼接数据-----
		Image img = null;
		PdfPTable table = null;
		// 标题
		Paragraph title = new Paragraph("HBV_SNP 报告", titleFont);
		title.setAlignment(Element.ALIGN_CENTER); // 居中设置
		title.setLeading(1f);// 设置行间距
		doc.add(title);

		// 文件名
		title = new Paragraph("File Name : " + fileName, contextFont);
		title.setSpacingBefore(25f);// 设置上面空白宽度
		doc.add(title);

		// Type
		title = new Paragraph(snpType.replace("<br />", "").replace("ype",
				"ype  "), contextFont);
		doc.add(title);

		title = new Paragraph("1.替诺福韦酯 TDF 突变检测", contextFont);
		doc.add(title);
		if (resultMap.get("194") != null) {
			addImg(snpPath + resultMap.get("194"), doc);
		}

		title = new Paragraph("2.替比夫定 LDT 突变检测", contextFont);
		doc.add(title);
		if (resultMap.get("204") != null) {
			addImg(snpPath + resultMap.get("204"), doc);
		}

		title = new Paragraph("3.阿德福韦 ADV 突变检测", contextFont);
		doc.add(title);
		String p3[] = { "181", "236" };
		table = getTable();

		int count = 0;
		for (String f : p3) {
			if (resultMap.get(f) != null) {
				count++;
				addImgToTable(snpPath + resultMap.get(f), table);
			}
		}
		if (count % 4 != 0) {
			addBlank(4 - count % 4, table);
		}
		doc.add(table);

		title = new Paragraph("4.拉米夫定 LAM 突变检测", contextFont);
		doc.add(title);
		String p4[] = { "173", "180", "204" };
		table = getTable();

		count = 0;
		for (String f : p4) {
			if (resultMap.get(f) != null) {
				count++;
				addImgToTable(snpPath + resultMap.get(f), table);
			}
		}
		if (count % 4 != 0) {
			addBlank(4 - count % 4, table);
		}
		doc.add(table);
		
		title = new Paragraph("5.恩曲他滨 FTC 突变检测", contextFont);
		doc.add(title);
		String p5[] = { "173", "180", "204" };
		table = getTable();
		count = 0;
		for (String f : p5) {
			if (resultMap.get(f) != null) {
				count++;
				addImgToTable(snpPath + resultMap.get(f), table);
			}
		}
		if (count % 4 != 0) {
			addBlank(4 - count % 4, table);
		}
		doc.add(table);
		doc.newPage();// 强制开启下一页，否则6的前四个图片不显示
		title = new Paragraph("6.恩替卡韦 ETV 突变检测", contextFont);
		doc.add(title);
		String p6[] = { "169", "180", "184", "202", "204", "250" };
		table = getTable();
		count = 0;
		for (String f : p6) {
			if (resultMap.get(f) != null) {
				count++;
				addImgToTable(snpPath + resultMap.get(f), table);
			}
		}
		if (count % 4 != 0) {
			addBlank(4 - count % 4, table);
		}
		doc.add(table);

		title = new Paragraph("7.其他检测结果", contextFont);
		doc.add(title);

		table = getTable();
		count = 0;
		for (String f : other) {
			count++;
			img = Image.getInstance(snpPath + f);
			addImgToTable(snpPath + f, table);
		}
		if (count % 4 != 0) {
			addBlank(4 - count % 4, table);
		}
		doc.add(table);

		title = new Paragraph("结论", contextFont);
		doc.add(title);
		title = new Paragraph(result, contextFont);
		doc.add(title);
		title = new Paragraph("所有检测结果", contextFont);
		doc.add(title);
		for (String all : total) {
			img = Image.getInstance(snpPath + all);
			img.scaleAbsolute(500f, 100f); // 设置图片大小
			img.setAlignment(Image.ALIGN_LEFT);// 设置图片居中显示
			doc.add(img);
		}
		doc.close();
		if (new File(snpPath.replace("SVG/", "") + "/HBV_SNPS.pdf").exists()) {
			try {
				WatermarkUtil.addMark(snpPath.replace("SVG/", "")
						+ "/HBV_SNPS.pdf", snpPath.replace("SVG/", "")
						+ "/HBV_SNP.pdf", PropertiesUtils.img, 1, 220, 800);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static void addBlank(int number, PdfPTable table)
			throws BadElementException, MalformedURLException, IOException {
		String path = "/share/data/webapps/Tools/blank.png";
		for (int i = 0; i < number; i++) {
			Image img = Image.getInstance(path);
			img.scaleAbsolute(100f, 100f); // 设置图片大小
			img.setAlignment(Image.ALIGN_LEFT);// 设置图片居左显示
			PdfPCell cell = new PdfPCell(img);
			cell.setBorder(Rectangle.NO_BORDER);// 设置单元格无边框
			table.addCell(cell);
		}
	}

	private static void addImg(String imgPath, Document doc)
			throws DocumentException, MalformedURLException, IOException {
		Image img = Image.getInstance(imgPath);
		img.scaleAbsolute(100f, 100f); // 设置图片大小
		img.setAlignment(Image.ALIGN_LEFT);// 设置图片居中显示
		doc.add(img);
	}

	private static void addImgToTable(String imgPath, PdfPTable table)
			throws BadElementException, MalformedURLException, IOException {
		Image img = Image.getInstance(imgPath);
		img.scaleAbsolute(100f, 100f); // 设置图片大小
		img.setAlignment(Image.ALIGN_LEFT);// 设置图片居左显示
		PdfPCell cell = new PdfPCell(img);
		cell.setBorder(Rectangle.NO_BORDER);// 设置单元格无边框
		table.addCell(cell);
	}

	private static PdfPTable getTable() {
		PdfPTable table = new PdfPTable(4);// 建立一个pdf表格
		table.setSpacingBefore(20f);// 设置表格上面空白宽度
		table.setTotalWidth(400);// 设置表格的宽度
		table.setWidthPercentage(100);// 设置表格宽度为%100
		table.getDefaultCell().setBorder(0);// 设置表格默认为无边框
		return table;
	}
}