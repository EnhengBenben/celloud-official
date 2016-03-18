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
	doc = SimpleDocTemplate(os.path.join(path,appName+".pdf"),
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
	table = Table(data, colWidths=[0.9 * cm, 5.5 * cm])
	table.setStyle(TableStyle([('VALIGN',(-1,-1),(-1,-1),'TOP')]))
	total.append(table)
	total.append(Spacer(1, 12))

	# FileName:
	t1 = '<font size=12 name="hei">FileName:</font><font size=10>'+fileName+'</font>'
	total.append(Paragraph(t1, styleTitle))
	total.append(Spacer(1, 10))

	#拼接图片路径
	svgPath = os.path.join(path,"SVG")

	#Report
	t = '<font size=12 name="hei">Report:</font>'
	total.append(Paragraph(t, styleTitle))
	total.append(Spacer(1, 5))
	report = os.path.join(path,'report.txt')
	if os.path.exists(report):
		res = readAllChinese(report)
		res = res.replace('\n','<br/>')
		total.append(Paragraph('<font size=10>'+res+'</font>', styleTitle))
	total.append(Spacer(1, 10))

	#检测结果
	t = '<font size=12 name="hei">检测结果</font>'
	total.append(Paragraph(t, styleTitle))
	total.append(Spacer(1, 10))
	allPng = fileSearch(svgPath,'_all.png','endswith')
	for png in allPng:
		im = Image(os.path.join(svgPath,png), 460, 115,'bound')
		total.append(im)
		total.append(Spacer(1, 10))

	doc.build(total)

if __name__ == '__main__':
	createPDF("/Users/lin/23/84/16022602565641","EGFR","aaaaaaaa.ab1")
