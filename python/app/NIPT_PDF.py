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

pdfmetrics.registerFont(TTFont('hei', 'app/ttc/simhei.ttf'))

# create pdf
def createPDF(path):
	if(not path.endswith(os.sep)):
		path = path+os.sep
	datakey = str(os.path.split(path[:-1])[1])
	rdir = os.path.join(path,"Result")
	doc = SimpleDocTemplate(os.path.join(path,datakey+".pdf"),
		pagesize=A4,rightMargin=10,
		leftMargin=10,topMargin=10,bottomMargin=10)
	# define style
	styles = getSampleStyleSheet()
	# title style
	styleTitle = styles["Normal"]
	styleTitle.alignment = TA_LEFT
	styleTitle.leftIndent = 55
	# context style
	styleContext = styles["BodyText"]
	styleContext.alignment = TA_LEFT

	# total context
	tatal = []

	# logo and pdf title
	logo = '/home/lin/work/png/portrait.png'
	im = Image(logo, 15, 17)
	pdftitle = '<font size=14 name="hei">NIPT 报告</font>'
	data= [[im, Paragraph(pdftitle, styleContext)]]
	table = Table(data, colWidths=[0.9 * cm, 3.5 * cm])
	table.setStyle(TableStyle([('VALIGN',(-1,-1),(-1,-1),'TOP')]))
	tatal.append(table)
	tatal.append(Spacer(1, 12))

	# first table
	t1 = '<font size=10 name="hei">数据统计：</font>'
	tatal.append(Paragraph(t1, styleTitle))
	tatal.append(Spacer(1, 12))
	firstData = simpleTable(os.path.join(rdir,"Data_report.txt"),styleContext,styleContext)
	table = Table(firstData,colWidths=[4.0 * cm, 4.0 * cm, 2.0 * cm, 4.0 * cm, 2.0 * cm])
	table.setStyle(TableStyle([
                       ('INNERGRID', (0,0), (-1,-1), 0.25, colors.black),
                       ('BOX', (0,0), (-1,-1), 0.25, colors.black),
                       ]))
	tatal.append(table)
	tatal.append(Spacer(1, 12))

	# second table
	t2 = '<font size=10 name="hei">报告：</font>'
	tatal.append(Paragraph(t2, styleTitle))
	tatal.append(Spacer(1, 12))
	secondData = simpleTable(os.path.join(rdir,"Zscore.txt"),styleContext,styleContext)
	table = Table(secondData,colWidths=[4.0 * cm, 4.0 * cm, 4 * cm, 4* cm])
	table.setStyle(TableStyle([
                       ('INNERGRID', (0,0), (-1,-1), 0.25, colors.black),
                       ('BOX', (0,0), (-1,-1), 0.25, colors.black),
                       ]))
	tatal.append(table)
	tatal.append(Spacer(1, 12))

	# Pic
	ptext = '<font size=10 name="hei">染色体：</font>'
	tatal.append(Paragraph(ptext, styleTitle))
	tatal.append(Spacer(1, 12))
	im = Image(os.path.join(rdir,"Pic.txt.mini.png"), 465, 108)
	tatal.append(im)

	doc.build(tatal)