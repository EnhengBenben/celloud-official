#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__des__ = 'HBV create pdf'
__author__ = 'lin'

import os
from reportlab.lib import colors
from reportlab.lib.enums import TA_LEFT
from reportlab.lib.pagesizes import A4,cm
from reportlab.lib.styles import getSampleStyleSheet
from reportlab.pdfbase import pdfmetrics
from reportlab.pdfbase.ttfonts import TTFont
from reportlab.platypus import Image , Paragraph , SimpleDocTemplate , Spacer , Table , TableStyle
from utils.FileUtils import *
from utils.StringUtils import *
from utils.TableUtils import *
from PDFPro import PDFPro

pdfmetrics.registerFont(TTFont('hei', os.path.join(PDFPro.ttc,'simhei.ttf')))

# create pdf
def createPDF(path,appName,fileName):
	if(not path.endswith(os.sep)):
		path = path+os.sep
	doc = SimpleDocTemplate(os.path.join(path,"HBV_SNP.pdf"),
		pagesize=A4,rightMargin=30,
		leftMargin=10,topMargin=10,bottomMargin=10)
	# define style
	styles = getSampleStyleSheet()
	# title style
	styleTitle = styles["Normal"]
	styleTitle.alignment = TA_LEFT
	styleTitle.leftIndent = 40
	# context style
	styleContext = styles["BodyText"]
	styleContext.alignment = TA_LEFT

	# total context
	total = []

	# logo and pdf title
	im = Image(PDFPro.logo, 15, 17)
	pdftitle = '<font size=14 name="hei">'+appName+' 报告</font>'
	data= [[im, Paragraph(pdftitle, styleContext)]]
	table = Table(data, colWidths=[0.9 * cm, 8.5 * cm])
	table.setStyle(TableStyle([('VALIGN',(-1,-1),(-1,-1),'TOP')]))
	total.append(table)
	total.append(Spacer(1, 12))

	# FileName:
	t1 = '<font size=10 name="hei">FileName:</font><font size=8 name="hei">'+fileName+'</font>'
	total.append(Paragraph(t1, styleTitle))
	total.append(Spacer(1, 5))

	#拼接图片路径
	svgPath = os.path.join(path,"SVG")

	# Type
	typeTxt = os.path.join(svgPath,"type.txt")
	if os.path.exists(typeTxt):
		t1 = '<font size=10 name="hei">'+readAll(typeTxt)+'</font>'
		total.append(Paragraph(t1, styleTitle))
		total.append(Spacer(1, 5))

	#检索其他检测结果
	other = fileSearch(svgPath,'_new.png','endswith')
	#定义每种药物的点图位点
	pos = [['194.png'],['204.png'],['181.png','236.png'],['173.png','180.png','204.png'],['173.png','180.png','204.png'],['169.png','180.png','184.png','202.png','204.png','250.png']]
	pos.append(other)
	#定义每种药物的标题
	title = {0:"1.替诺福韦酯 TDF 突变检测",
	1:"2.替比夫定 LDT 突变检测",
	2:"3.阿德福韦 ADV 突变检测",
	3:"4.拉米夫定 LAM 突变检测",
	4:"5.恩曲他滨 FTC 突变检测",
	5:"6.恩替卡韦 ETV 突变检测",
	6:"7.其他检测结果"
	}
	#定义表格列宽
	colLen = {1:[16 * cm],2:[4 * cm,12 * cm],3:[4 * cm,4 * cm,8 * cm],4:[4 * cm,4 * cm,4 * cm,4 * cm]}

	for i in range(len(pos)):
		t = '<font size=10 name="hei">' + title[i] + '</font>'
		total.append(Paragraph(t, styleTitle))
		p = pos[i]
		images = []
		r = []
		hasPNG = False
		for j in range(len(p)):
			png = os.path.join(svgPath,p[j])
			if os.path.exists(png):
				hasPNG = True
				im = Image(png, 100, 100,'bound')
				images.append(im)
				if j%4==3:
					r.append(images)
					images = []
		r.append(images)
		if hasPNG:
			if len(p)>3:
				col = colLen[4]
			else:
				col = colLen[len(p)]
			table = Table(r, colWidths=col)
			total.append(table)
	#结论
	t = '<font size=12 name="hei">结论</font>'
	total.append(Paragraph(t, styleTitle))
	total.append(Spacer(1, 10))
	report = os.path.join(svgPath,'Report.txt')
	if os.path.exists(report):
		res = readAllChinese(report).replace('Other:	Y','')
		res = res.replace('\n','<br/>')
		total.append(Paragraph('<font size=12 name="hei">'+res+'</font>', styleTitle))
	#所有检测结果
	t = '<font size=12 name="hei">所有检测结果</font>'
	total.append(Paragraph(t, styleTitle))
	total.append(Spacer(1, 10))
	allPng = fileSearch(svgPath,'_all.png','endswith')
	for png in allPng:
		im = Image(os.path.join(svgPath,png), 460, 115,'bound')
		total.append(im)
		total.append(Spacer(1, 10))

	doc.build(total)

if __name__ == '__main__':
	createPDF("/Users/lin/23/82/20150902235788","HBV_SNP","a.ab1")
