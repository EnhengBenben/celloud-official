#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__des__ = 'NIPT create pdf'
__author__ = 'lin'

import os
from reportlab.lib import colors
from reportlab.lib.enums import TA_LEFT
from reportlab.lib.pagesizes import A4,cm
from reportlab.lib.styles import getSampleStyleSheet
from reportlab.pdfbase import pdfmetrics
from reportlab.pdfbase.ttfonts import TTFont
from reportlab.platypus import Image , Paragraph , SimpleDocTemplate , Spacer , Table , TableStyle
from utils.TableUtils import *
from utils.StringUtils import *
from PDFPro import PDFPro

pdfmetrics.registerFont(TTFont('hei', os.path.join(PDFPro.ttc,'simhei.ttf')))

# create pdf
def createPDF(path,appName,fileName,anotherName):
	if(not path.endswith(os.sep)):
		path = path+os.sep
	datakey = str(os.path.split(path[:-1])[1])
	doc = SimpleDocTemplate(os.path.join(path,datakey+".pdf"),
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

	# Name:
	t1 = '<font size=10 name="hei">Name:</font><font size=8 name="hei">'+fileName+'</font>'
	total.append(Paragraph(t1, styleTitle))
	total.append(Spacer(1, 5))

	# Barcode
	t1 = '<font size=10 name="hei">barcode:</font><font size=8 name="hei">'+getBarcode(fileName)+'</font>'
	total.append(Paragraph(t1, styleTitle))
	total.append(Spacer(1, 5))

	# anotherName
	t1 = '<font size=10 name="hei">sample_name:</font><font size=8 name="hei">'+anotherName+'</font>'
	total.append(Paragraph(t1, styleTitle))
	total.append(Spacer(1, 5))

	# first table
	t1 = '<font size=10 name="hei">Data:</font>'
	total.append(Paragraph(t1, styleTitle))
	total.append(Spacer(1, 5))
	firstData = lineTable(os.path.join(path,datakey+".xls"),styleContext,[1,2])
	table = Table(firstData,colWidths=[3.0 * cm , 3.0 * cm, 3.0 * cm, 3.0 * cm, 3.0 * cm, 1.5 * cm])
	table.setStyle(TableStyle([
                       ('INNERGRID', (0,0), (-1,-1), 0.25, colors.black),
                       ('BOX', (0,0), (-1,-1), 0.25, colors.black),
                       ]))
	total.append(table)
	total.append(Spacer(1, 5))

	# Result
	t2 = '<font size=10 name="hei">Result:</font>'
	total.append(Paragraph(t2, styleTitle))
	total.append(Spacer(1, 5))
	with open(os.path.join(path,"report.txt"), 'r') as f:
		t2 = '<font size=8 name="hei">'+f.read()+'</font>'
		total.append(Paragraph(t2, styleTitle))
		total.append(Spacer(1, 5))

	# Pic
	finalPng1 = os.path.join(path,datakey+".gc.cnt.each.txt.hmm.final.png")
	finalPng2 = os.path.join(path,datakey+".gc.cnt.avg.txt.final.png")
	finalPng3 = os.path.join(path,datakey+".gc.cnt.each.txt.kde.gcheck.final.png")
	if os.path.exists(finalPng1):
		im = Image(finalPng1, 440, 586)
		total.append(im)
	elif os.path.exists(finalPng2):
		im = Image(finalPng2, 440, 586)
		total.append(im)
	elif os.path.exists(finalPng3):
		im = Image(finalPng3, 440, 586)
		total.append(im)

	doc.build(total)