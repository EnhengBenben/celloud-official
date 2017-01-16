#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

__des__ = 'Rocky create pdf'
__author__ = 'miaoqi'

import os
import codecs
import json
import threading
import sys
reload(sys)
sys.setdefaultencoding('utf8')
from reportlab.lib import colors
from reportlab.lib.enums import TA_LEFT, TA_RIGHT, TA_CENTER, TA_JUSTIFY
from reportlab.lib.pagesizes import A4,cm,letter
from reportlab.lib.units import inch
from reportlab.lib.styles import getSampleStyleSheet, ParagraphStyle
from reportlab.pdfbase import pdfmetrics
from reportlab.pdfbase.ttfonts import TTFont
from reportlab.platypus import Image, Paragraph, SimpleDocTemplate, Spacer, Table, TableStyle
from reportlab.pdfgen import canvas
from reportlab.pdfgen.canvas import Canvas
from mongo.mongoOperate import mongo
from PDFPro import PDFPro

significances = {
    'Pathogenic':'致病相关变异',
    'Likely pathogenic':'可能致病变异',
    'Uncertain significance':'意义不明确变异',
    'Likely benign':'可能良性变异',
    'Benign':'良性变异'
}
px = {
    'a':5,
    'b':5,
    'c':4,
    'd':5,
    'e':4,
    'f':4,
    'g':4,
    'h':5,
    'i':2,
    'j':2,
    'k':4,
    'l':2,
    'm':7,
    'n':5,
    'o':5,
    'p':5,
    'q':5,
    'r':3,
    's':3,
    't':3,
    'u':5,
    'v':4,
    'w':6,
    'x':4,
    'y':4,
    'z':4,
    'A':6,
    'B':6,
    'C':6,
    'D':7,
    'E':6,
    'F':5,
    'G':7,
    'H':7,
    'I':3,
    'J':4,
    'K':7,
    'L':5,
    'M':8,
    'N':7,
    'O':7,
    'P':6,
    'Q':7,
    'R':6,
    'S':4,
    'T':6,
    'U':7,
    'V':6,
    'W':9,
    'X':7,
    'Y':6,
    'Z':6,
    '1':5,
    '2':5,
    '3':5,
    '4':5,
    '5':5,
    '6':5,
    '7':5,
    '8':5,
    '9':5,
    '0':5,
    '.':1,
    ' ':2,
    '“':3,
    '”':4,
    ',':2,
    ';':2,
    ':':2,
    '"':3,
    '(':3,
    ')':3,
    '-':2,
    '<':6,
    '>':6,
    '%':8,
    '%':10
}


# 新罗马斜体
pdfmetrics.registerFont(TTFont('tnri', os.path.join(PDFPro.ttc,'TimesNewRomanItalic.ttf')))
# 平方细1
pdfmetrics.registerFont(TTFont('pf1', os.path.join(PDFPro.ttc,'pingfang1.ttf')))
# 平方细2
pdfmetrics.registerFont(TTFont('pf2', os.path.join(PDFPro.ttc,'pingfang2.ttf')))
# Helvetica.dfont
pdfmetrics.registerFont(TTFont('he', os.path.join(PDFPro.ttc,'Helvetica.ttf')))
# 宋体
pdfmetrics.registerFont(TTFont('song', os.path.join(PDFPro.ttc,'Songti.ttf')))
class RockyPdf:
    instance = None
    locker = threading.Lock()
    #初始化
    def __init__(self):
        pass
    @staticmethod
    def getInstance():
        RockyPdf.locker.acquire()
        try:
            if not RockyPdf.instance:
                RockyPdf.instance = RockyPdf()
            return RockyPdf.instance
        finally:
            RockyPdf.locker.release()
    # 写页码
    def drawPage(self,c,page):
        c.setFillColorRGB(0.62,0.48,0.68)
        c.setFont('pf2',12)
        # 偶数页
        if(page%2==0):
            if(page<10):
                c.drawString(528,50,'0' + str(page))
            else:
                c.drawString(528,50,str(page))
        else:
            if(page<10):
                c.drawString(50,50,'0' + str(page))
            else:
                c.drawString(50,50,str(page))
    # 写段落
    def drawPara(self,text,ps,c,awidth,aheight,x,y):        
        p = Paragraph(text,ps)
        p.wrap(awidth,aheight)
        p.drawOn(c,x,y)
    def drawParaWithBullet(self,text,bullet,ps,c,awidth,aheight,x,y):
        p = Paragraph(text,ps,bullet)
        p.wrap(awidth,aheight)
        p.drawOn(c,x,y)
    def getLastWidth(self,description,fromIndex):
        lastWidth = 0
        for i,ch in enumerate(description.decode('utf-8')):
            if i >= fromIndex:
                lastWidth += 10 if px.get(ch) == None else px.get(ch)
        return lastWidth
    def drawOneTable(self,c,ps,page,resultPath,count,aHeight,topHeight,tableHeight,bottomHeight,onePage):
        c.setStrokeColorRGB(0.62,0.48,0.68)
        dotted = Image(os.path.join(PDFPro.rockyPicPath,"dotted.png"),51,841)
        if page % 2 == 0:
            dotted.drawOn(c,494,0)
        else:
            dotted.drawOn(c,0,0)
        page+=1
        # 临时行数, 用于输出表格
        tempCount = 0
        # 起始行数
        start = count
        # 结束行数
        end = 0
        if os.path.exists(resultPath):
            f = open(resultPath,'r')
            # 读取文件行数, 每行高30px
            for line in f.readlines():
                end += 1
                if(end > start):
                    tableHeight += 26
                    # A4高度-顶部高度-底部高度-表格高度 < 60
                    if aHeight-topHeight-bottomHeight-tableHeight < 60:
                        bottomHeight = 0
                        tableHeight = 26
                        onePage = False
                        break;
            end = 0
            tableHeight = 26
            f.close()
            f = open(resultPath,'r')
            # 重新遍历文件, 获取实际输出行数
            for line in f.readlines():
                end += 1
                if(end > start):
                    tableHeight += 26
                    if aHeight-topHeight-bottomHeight-tableHeight < 60:
                        end -= 1
                        tableHeight -=26
                        bottomHeight = 0
                        onePage = False
                        break;
            f.close()
            # 表格title
            data = [['编号','基因','核苷酸变异','氨基酸变异','临床意义']]
            f = open(resultPath,'r')
            tableHeight = 26
            for line in f.readlines():
                tempCount += 1
                if(tempCount > start and tempCount <= end):
                    tableHeight += 26
                    st = line.strip().split("\t")
                    # 第2列
                    gene = st[0]
                    # 第3列
                    acids = st[2]
                    # 第4列
                    nucleotides = st[3]
                    # 第5列
                    sign = significances[st[4]]               
                    ps.fontSize = 12
                    ps.alignment = TA_CENTER
                    ps.leading = 14
                    ps.firstLineIndent = 0
                    p = Paragraph('<para><font name="tnri"><i>' + gene + '</i></font></para>',ps)
                    data.append([tempCount,p,acids,nucleotides,sign])
            t = Table(data,[50,70,120,110,100],[26] * (end-start+1))
            t.setStyle(TableStyle([('TEXTCOLOR',(1,1),(-1,-1),'#333333'),
                                   ('SIZE',(0,0),(-1,0),14),
                                   ('SIZE',(1,1),(-1,-1),12),
                                   ('FONTNAME',(0,0),(-1,-1),'pf1'),
                                   ('ALIGN',(0,0),(-1,-1),'CENTER'),
                                   ('VALIGN',(0,0),(-1,-1),'MIDDLE')]))
            t.wrap(455,(end-start+1)*26)
            # +8是因为没有背景颜色 所以需要向上提8
            line4 = aHeight - topHeight - tableHeight - 30 - 1 + 12
            if page % 2 == 0:
                t.drawOn(c,98,aHeight - topHeight - tableHeight + 6)
                c.line(95,line4,550,line4)
                ps.fontSize = 14
                ps.firstLineIndent = 0
            else:
                t.drawOn(c,48,aHeight - topHeight - tableHeight + 6)
                c.line(45,line4,500,line4)
                ps.fontSize = 14
                ps.firstLineIndent = 0
            ps.alignment = 4
            ps.leading = 24
            if(onePage):
                if page % 2 == 0:
                    self.drawPara('检测人：',ps,c,64,14,270,line4 - 35 - 14 - 10)
                    self.drawPara('复核人：',ps,c,64,14,410,line4 - 35 - 14 - 10)
                    self.drawPara('日&nbsp;&nbsp;&nbsp;期：',ps,c,64,14,270,line4 - 35 - 14 - 25 - 14 - 10)
                    self.drawPara('日&nbsp;&nbsp;&nbsp;期：',ps,c,64,14,410,line4 - 35 - 14 - 25 - 14 - 10)
                    celloud_qrcode = Image(os.path.join(PDFPro.rockyPicPath,"celloud_qrcode.png"),50,56)
                    celloud_qrcode.drawOn(c,117,line4 - 35 - 14 - 25 - 14 -7)
                else:
                    self.drawPara('检测人：',ps,c,64,14,220,line4 - 35 - 14 - 10)
                    self.drawPara('复核人：',ps,c,64,14,360,line4 - 35 - 14 - 10)
                    self.drawPara('日&nbsp;&nbsp;&nbsp;期：',ps,c,64,14,220,line4 - 35 - 14 - 25 - 14 - 10)
                    self.drawPara('日&nbsp;&nbsp;&nbsp;期：',ps,c,64,14,360,line4 - 35 - 14 - 25 - 14 - 10)
                    celloud_qrcode = Image(os.path.join(PDFPro.rockyPicPath,"celloud_qrcode.png"),50,56)
                    celloud_qrcode.drawOn(c,67,line4 - 35 - 14 - 25 - 14 -7)
                c.showPage()
                #if(page % 2 == 0):
                    #c.showPage()
            else:
               c.showPage()
               self.drawOneTable(c,ps,page,resultPath,end,aHeight,50,26,88,True)
    def drawTwoTable(self,c,ps,page,resultPath,count,aHeight,topHeight,tableHeight,bottomHeight,onePage):
        page += 1
        self.drawPage(c,page)
        # 临时行数, 用于输出表格
        tempCount = 0
        # 起始行数
        start = count
        # 结束行数
        end = 0
        if os.path.exists(resultPath):
            f = open(resultPath,'r')
            # 读取文件行数, 每行高30px
            for line in f.readlines():
                end += 1
                if(end > start):
                    tableHeight += 30
                    # A4高度-顶部高度-底部高度-表格高度 < 90
                    if aHeight-topHeight-bottomHeight-tableHeight < 90:
                        bottomHeight = 0
                        tableHeight = 30
                        onePage = False
                        break;
            end = 0
            tableHeight = 30
            f.close()
            f = open(resultPath,'r')
            # 重新遍历文件, 获取实际输出行数
            for line in f.readlines():
                end += 1
                if(end > start):
                    tableHeight += 30
                    if aHeight-topHeight-bottomHeight-tableHeight < 90:
                        end -= 1
                        tableHeight -=30
                        bottomHeight = 0
                        onePage = False
                        break;
            f.close()
            # 表格title
            data = [['编号','基因','核苷酸变异','氨基酸变异','临床意义']]
            f = open(resultPath,'r')
            tableHeight = 30
            for line in f.readlines():
                tempCount += 1
                if(tempCount > start and tempCount <= end):
                    tableHeight += 30
                    st = line.strip().split("\t")
                    # 第2列
                    gene = st[0]
                    # 第3列
                    acids = st[2]
                    # 第4列
                    nucleotides = st[3]
                    # 第5列
                    sign = significances[st[4]]               
                    ps.fontSize = 10
                    ps.alignment = TA_CENTER
                    ps.leading = 12
                    p = Paragraph('<para><font name="tnri"><i>' + gene + '</i></font></para>',ps)
                    data.append([tempCount,p,acids,nucleotides,sign])
            t = Table(data,[50,70,120,110,100],[30] * (end - start + 1))
            t.setStyle(TableStyle([('BACKGROUND',(0,0),(-1,0),'#9F7BAF'),
                                   ('TEXTCOLOR',(0,0),(-1,0),'#FFFFFF'),
                                   ('TEXTCOLOR',(1,1),(-1,-1),'#333333'),
                                   ('SIZE',(0,0),(-1,0),12),
                                   ('SIZE',(1,1),(-1,-1),10),
                                   ('FONTNAME',(0,0),(-1,-1),'pf1'),
                                   ('ALIGN',(0,0),(-1,-1),'CENTER'),
                                   ('VALIGN',(0,0),(-1,-1),'MIDDLE'),
                                   ('GRID',(0,0),(-1,-1),0.25,'#9F7BAF')]))
            t.wrap(450,(end - start + 1)*30)
            if(page % 2 == 0):
                t.drawOn(c,74,aHeight - topHeight - tableHeight)
            else:
                t.drawOn(c,70,aHeight - topHeight - tableHeight)
            ps.fontSize = 12
            ps.alignment = 4

            ps.leading = 24
            if(onePage):
                self.drawPara('检测人：',ps,c,60,12,312,aHeight - topHeight -  tableHeight - 84)
                self.drawPara('复核人：',ps,c,60,12,412,aHeight - topHeight -  tableHeight - 84)
                self.drawPara('日&nbsp;&nbsp;&nbsp;期：',ps,c,60,12,312,aHeight - topHeight - tableHeight - 32 - 84)
                self.drawPara('日&nbsp;&nbsp;&nbsp;期：',ps,c,60,12,412,aHeight - topHeight - tableHeight - 32 - 84)
                c.showPage()
                if(page % 2 != 0):
                    page += 1
                    self.drawPage(c,page)
                    space1 = Image(os.path.join(PDFPro.rockyPicPath,"space1.png"),595,556)
                    space1.drawOn(c,0,285)
                    c.showPage()
                return page
            else:
                c.showPage()
                return self.drawTwoTable(c,ps,page,resultPath,end,aHeight,175,30,74,True)
    def drawThreeTable(self,c,ps,page,resultPath,fromCount,fromIndex,aHeight,topHeight,tableHeight,onePage,descriptions,companyId,peakPath):
        page+=1
        self.drawPage(c,page)
        # 临时行数, 用于输出表格
        tempCount = 0
        # 起始行数
        start = fromCount
        # 结束行数
        end = 0
        # 表格title
        data = [['编号','变异名称','影响描述']]
        # 表格每行的高度
        tableHeights = [30]
        if os.path.exists(resultPath):
            # 判断名称是否够高
            f = open(resultPath,'r')
            for line in f.readlines():
                if onePage == False:
                    break
                oneRow = True
                tab = True
                if fromIndex > 0:
                    oneRow = False
                    tab = False
                rowHeight = 0
                end += 1
                tempCount += 1
                if end > start:
                    if aHeight - topHeight - tableHeight - 70 < 90:
                        # 这一页剩余的高度连70个像素都放不下 需要翻页
                        tempCount -= 1
                        onePage = False
                        fromIndex = 0
                        break
                    else:
                        # 判断描述可以放下的高度
                        # 1. 获取这一行的描述
                        st = line.strip().split("\t")
                        # 第2列
                        gene = st[0]
                        # 第3列
                        acids = st[2]
                        # 第4列
                        nucleotides = st[3]
                        # 第5列
                        sign = significances[st[4]]
                        variationId = st[5]
                        # 第6列
                        peak = st[6]
                        
                        description = '目前暂无描述说明。我们将保持关注，根据最新科研成果及时更新该状态。'
                        if gene+'-'+variationId in descriptions.keys():
                            description = descriptions[gene+'-'+variationId]
                            if description == '-':
                                description = "There is no description at this moment."
                        descContent = ""
                        descWidth = 0
                        # 遍历描述信息, 判断描述可以显示到哪里
                        for i, ch in enumerate(description.decode('utf-8')):
                            # 从第几个字符开始
                            if i >= fromIndex:
                                descWidth += 10 if px.get(ch) == None else px.get(ch)
                                fromIndex += 1
                                if (oneRow == True and descWidth > 208) or (oneRow == False and descWidth > 228):
                                    rowHeight += 20 
                                    oneRow = False
                                    if companyId != 33 and aHeight - topHeight - tableHeight - rowHeight - 40 - 10 < 90:
                                        # 需要分页
                                        fromIndex -= 1
                                        onePage = False
                                        lastWidth = self.getLastWidth(description,fromIndex)
                                        descWidth -= 10 if px.get(ch) == None else px.get(ch)
                                        if lastWidth < 70:
                                            tempDescWidth = descWidth
                                            for i,ch in enumerate(descContent.decode('utf-8')):
                                                tempDescWidth -= 10 if px.get(descContent[len(descContent) - i - 1]) == None else px.get(descContent[len(descContent) - i - 1])
                                                fromIndex -= 1
                                                if tempDescWidth <= 0:
                                                    descContent = descContent[0:fromIndex]
                                                    break
                                        break
                                    elif companyId == 33 and aHeight - topHeight - tableHeight - rowHeight - 60 - 10 < 90:
                                        # 需要分页
                                        fromIndex -= 1
                                        onePage = False
                                        lastWidth = self.getLastWidth(description,fromIndex)
                                        descWidth -= 10 if px.get(ch) == None else px.get(ch)
                                        if lastWidth < 100:
                                            tempDescWidth = descWidth
                                            for i,ch in enumerate(descContent.decode('utf-8')):
                                                tempDescWidth -= 10 if px.get(descContent[len(descContent) - i - 1]) == None else px.get(descContent[len(descContent) - i - 1])
                                                fromIndex -= 1
                                                if tempDescWidth <= 0:
                                                    descContent = descContent[0:fromIndex]
                                                    break
                                        break
                                    else:
                                        descContent += ch
                                        descWidth = 10 if px.get(ch) == None else px.get(ch)
                                else:
                                    descContent += ch
                            if i == len(description.decode('utf-8')) - 1:
                                rowHeight += 20
                                fromIndex = 0

                        # +30是因为实际上下应该+20 但是Para后有10个像素空白 所以+30
                        if companyId == 33 and fromIndex == 0:
                            # 峰图高40
                            if(rowHeight + 50 <= 90):
                                rowHeight = 90
                            else:
                                rowHeight += 50
                        else:
                            if(rowHeight + 30 <= 70):
                                rowHeight = 70
                            else:
                                rowHeight += 30
                        tableHeight += rowHeight
                        tableHeights.append(rowHeight)
                        threepsone = ParagraphStyle({})
                        threepsone.fontName = 'song' # 字体
                        threepsone.fontSize = 10 # 字号
                        threepsone.alignment = TA_CENTER # 位置
                        threepsone.leading = 10
                        threepsone.firstLineIndent = 0
                        threepsone.spaceAfter = 5
                        threeps = ParagraphStyle({})
                        threeps.fontName = 'song' # 字体
                        threeps.fontSize = 10 # 字号
                        threeps.alignment = TA_CENTER # 位置
                        threeps.leading = 10
                        threeps.firstLineIndent = 0
                        threeps.spaceBefore = 5
                        p00 = Paragraph("<para><font name='tnri'><i>" + gene + "</i></font>：" + acids + "</para>",threepsone)
                        p01 = Paragraph("临床意义：" + sign,threeps)

                        ps = ParagraphStyle({})
                        ps.fontName = "song"
                        ps.fontSize = 10
                        ps.alignment = TA_JUSTIFY
                        ps.leading = 20
                        ps.wordWrap = "CJK"
                        if tab == True:
                            ps.firstLineIndent = 20
                        else:
                            ps.firsLineIndent = 0
                        descContentTnri = ""
                        tnriFlag = False

                        descContent = descContent.replace('BRCA1','<font name="tnri"><i>BRCA1</i></font>')
                        descContent = descContent.replace('BRCA2','<font name="tnri"><i>BRCA2</i></font>')
                        descContent = descContent.replace('<font name="tnri"><i>BRCA1</i></font>蛋白','BRCA1蛋白')
                        descContent = descContent.replace('<font name="tnri"><i>BRCA2</i></font>蛋白','BRCA2蛋白')

                        ps.spaceBefore = -10
                        p1 = Paragraph("<para>" + descContent + "</para>",ps)
                        
                        #peakPs = ParagraphStyle({})
                        #peakP = Paragraph("<para><img src='"+peakPath+peak+"' /></para>",peakPs)
                        #peakP.wrap(270,40)
                        if companyId == 33 and fromIndex == 0:
                            Im = Image(peakPath + peak,270,40)
                            data.append([tempCount,[p00,p01],[p1,Im]])
                        else:
                            data.append([tempCount,[p00,p01],[p1]])
            f.close()
            t = Table(data,[40,140,270],tableHeights)
            if companyId == 33:
                if fromIndex > 0:
                    t.setStyle(TableStyle([('BACKGROUND',(0,0),(-1,0),'#9F7BAF'),
                                   ('TEXTCOLOR',(0,0),(-1,0),'#FFFFFF'),
                                   ('TEXTCOLOR',(1,1),(-1,-1),'#333333'),
                                   ('SIZE',(0,0),(0,-1),12),
                                   ('SIZE',(0,0),(-1,0),12),
                                   ('FONTNAME',(0,0),(-1,0),'pf1'),
                                   ('FONTNAME',(0,1),(0,-1),'song'),
                                   ('LEFTPADDING',(-1,1),(-1,-1),20),
                                   ('RIGHTPADDING',(-1,1),(-1,-1),20),
                                   ('ALIGN',(0,0),(-1,-1),'CENTER'),
                                   ('VALIGN',(0,0),(-1,-1),'MIDDLE'),
                                   ('VALIGN',(-1,1),(-1,-1),'BOTTOM'),
                                   ('VALIGN',(-1,-1),(-1,-1),'MIDDLE'),
                                   ('BOTTOMPADDING',(-1,1),(-1,-1),-7),
                                   ('BOTTOMPADDING',(-1,-1),(-1,-1),10),
                                   ('TOPPADDING',(-1,1),(-1,-1),0),
                                   ('TOPPADDING',(-1,-1),(-1,-1),20),
                                   ('GRID',(0,0),(-1,-1),0.25,'#9F7BAF')]))
                else:
                    t.setStyle(TableStyle([('BACKGROUND',(0,0),(-1,0),'#9F7BAF'),
                                   ('TEXTCOLOR',(0,0),(-1,0),'#FFFFFF'),
                                   ('TEXTCOLOR',(1,1),(-1,-1),'#333333'),
                                   ('SIZE',(0,0),(0,-1),12),
                                   ('SIZE',(0,0),(-1,0),12),
                                   ('FONTNAME',(0,0),(-1,0),'pf1'),
                                   ('FONTNAME',(0,1),(0,-1),'song'),
                                   ('LEFTPADDING',(-1,1),(-1,-1),20),
                                   ('RIGHTPADDING',(-1,1),(-1,-1),20),
                                   ('ALIGN',(0,0),(-1,-1),'CENTER'),
                                   ('VALIGN',(0,0),(-1,-1),'MIDDLE'),
                                   ('VALIGN',(-1,1),(-1,-1),'BOTTOM'),
                                   ('BOTTOMPADDING',(-1,1),(-1,-1),-7),
                                   ('TOPPADDING',(-1,1),(-1,-1),0),
                                   ('GRID',(0,0),(-1,-1),0.25,'#9F7BAF')]))
            else:
                t.setStyle(TableStyle([('BACKGROUND',(0,0),(-1,0),'#9F7BAF'),
                                   ('TEXTCOLOR',(0,0),(-1,0),'#FFFFFF'),
                                   ('TEXTCOLOR',(1,1),(-1,-1),'#333333'),
                                   ('SIZE',(0,0),(0,-1),12),
                                   ('SIZE',(0,0),(-1,0),12),
                                   ('FONTNAME',(0,0),(-1,0),'pf1'),
                                   ('FONTNAME',(0,1),(0,-1),'song'),
                                   ('LEFTPADDING',(-1,1),(-1,-1),20),
                                   ('RIGHTPADDING',(-1,1),(-1,-1),20),
                                   ('ALIGN',(0,0),(-1,-1),'CENTER'),
                                   ('VALIGN',(0,0),(-1,-1),'MIDDLE'),
                                   ('BOTTOMPADDING',(-1,1),(-1,-1),10),
                                   ('TOPPADDING',(-1,1),(-1,-1),20),
                                   ('GRID',(0,0),(-1,-1),0.25,'#9F7BAF')]))
            t.wrap(450,tableHeight)
            if page % 2 == 0:
                t.drawOn(c,74,aHeight - topHeight - tableHeight)
            else:
                t.drawOn(c,70,aHeight - topHeight - tableHeight)
            c.showPage()
            if(onePage):
                if(page % 2 != 0):
                    page += 1
                    self.drawPage(c,page)
                    space2 = Image(os.path.join(PDFPro.rockyPicPath,"space2.png"),595,476)
                    space2.drawOn(c,0,365)
                    c.showPage()
                return page
            else:
                if(fromIndex <=0):
                    return self.drawThreeTable(c,ps,page,resultPath,tempCount,fromIndex,aHeight,175,30,True,descriptions,companyId,peakPath)
                else:
                    return self.drawThreeTable(c,ps,page,resultPath,tempCount - 1,fromIndex,aHeight,175,30,True,descriptions,companyId,peakPath)
    def getDescription(self):
        path = os.path.split(os.path.realpath(__file__))[0]
        path = os.path.join(path,'Rocky_pdf.txt')
        with open(path, 'r') as f:
            data = json.load(f)
        f.close()
        return data
    # create pdf
    #def createPDF(self,path,dataKey,userId,companyId):
    def createPDF(self, objId):
        mo = mongo.getInstance()
        dataReport = mo.get({"_id" : ObjectId(objId)})
        print dataReport
        peakPath = os.path.join(path,dataKey+"/")
        px = os.path.join(PDFPro.rockyPicPath,"px.png")
        end = Image(os.path.join(PDFPro.rockyPicPath,"end.png"),5,4)
        end2 = Image(os.path.join(PDFPro.rockyPicPath,"end2.png"),11,12)
        page1_logo = Image(os.path.join(PDFPro.rockyPicPath,"page1_logo.png"),347,52)
        page1_celloud = Image(os.path.join(PDFPro.rockyPicPath,"page1_celloud.png"),181,14)
        page1_yanda = Image(os.path.join(PDFPro.rockyPicPath,"page1_yanda.png"),122,29)
        page2_title = Image(os.path.join(PDFPro.rockyPicPath,"page2_title.png"),238,38)
        page2_icon1 = Image(os.path.join(PDFPro.rockyPicPath,"page2_icon1.png"),13,12)
        page2_icon2 = Image(os.path.join(PDFPro.rockyPicPath,"page2_icon2.png"),12,12)
        page3 = Image(os.path.join(PDFPro.rockyPicPath,"page3.png"),595,802)
        page4_title = Image(os.path.join(PDFPro.rockyPicPath,"page4_title.png"),120,40)
        page6_title = Image(os.path.join(PDFPro.rockyPicPath,"page6_title.png"),104,40)
        page6_content = Image(os.path.join(PDFPro.rockyPicPath,"page6_content.png"),208,220)
        page8_title = Image(os.path.join(PDFPro.rockyPicPath,"page8_title.png"),150,53)
        page8_content = Image(os.path.join(PDFPro.rockyPicPath,"page8_content.png"),400,79)
        page9_title = Image(os.path.join(PDFPro.rockyPicPath,"page9_title.png"),146,55)
        page9_content2 = Image(os.path.join(PDFPro.rockyPicPath,"page9_content2.png"),337,149)
        page10_title = Image(os.path.join(PDFPro.rockyPicPath,"page10_title.png"),132,53)
        page10_content = Image(os.path.join(PDFPro.rockyPicPath,"page10_content.png"),318,261)
        page11_title = Image(os.path.join(PDFPro.rockyPicPath,"page11_title.png"),226,53)
        page11_content = Image(os.path.join(PDFPro.rockyPicPath,"page11_content.png"),250,252)
        page12_content = Image(os.path.join(PDFPro.rockyPicPath,"page12_content.png"),300,300)
        page13_title = Image(os.path.join(PDFPro.rockyPicPath,"page13_title.png"),225,52)
        page13_icon1 = Image(os.path.join(PDFPro.rockyPicPath,"page13_icon1.png"),13,15)
        page13_icon2 = Image(os.path.join(PDFPro.rockyPicPath,"page13_icon2.png"),14,14)
        page13_icon3 = Image(os.path.join(PDFPro.rockyPicPath,"page13_icon3.png"),13,15)
        page13_icon4 = Image(os.path.join(PDFPro.rockyPicPath,"page13_icon4.png"),14,13)
        page14_icon1 = Image(os.path.join(PDFPro.rockyPicPath,"page14_icon1.png"),12,14)
        page15_title = Image(os.path.join(PDFPro.rockyPicPath,"page15_title.png"),97,40)
        page16_title = Image(os.path.join(PDFPro.rockyPicPath,"page16_title.png"),116,40)
        celloud = Image(os.path.join(PDFPro.rockyPicPath,"celloud.png"),222,82)
        # 读取结果文件
        resultPath = os.path.join(path,dataKey,'result','all.snp')

        # 读取总的突变基因个数, 致病基因个数
        total = 0
        pathogenicNum = 0
        if(os.path.exists(resultPath)):
            f = open(resultPath,'r')
            for line in f.readlines():
                total += 1
                st = line.strip().split('\t')
                if(st[4] == 'Pathogenic'):
                    pathogenicNum += 1
            f.close()
        # A4宽高
        aWidth, aHeight = A4

        stylesheet=getSampleStyleSheet()

        ps = ParagraphStyle({})
        ps.fontName = 'pf1' # 字体
        ps.fontSize = 12 # 字号
        ps.firstLineIndent = 0 # 首行左边距
        ps.alignment = TA_JUSTIFY # 位置
        ps.leading = 24 # 行间距
        ps.textColor = '#333333'

        tbps = ParagraphStyle({})
        tbps.fontName = 'pf1'
        tbps.fontSize = 12
        tbps.firstLineIndent = 0
        if not os.path.exists(os.path.join(PDFPro.rockyPdfPath,str(userId),str(dataKey))):
            os.makedirs(os.path.join(PDFPro.rockyPdfPath,str(userId),str(dataKey)))
        c = canvas.Canvas(os.path.join(PDFPro.rockyPdfPath,str(userId),str(dataKey),str(dataKey)+'.pdf'),pagesize=A4)
        c.setFont('pf1',14)
        # 第一页
        page1_logo.drawOn(c,124,450)
        if companyId == 33:
            page1_yanda.drawOn(c,237,80)
        else:
            page1_celloud.drawOn(c,215,90)
        c.showPage()
        # 第二页(表格)
        c.setStrokeColorRGB(0.62,0.48,0.68)
        ps.fontSize = 14
        page2_title.drawOn(c,203,753)
        page2_icon1.drawOn(c,95,679)
        self.drawPara('姓名：',ps,c,42,14,119,669)
        page2_icon2.drawOn(c,323,679)
        self.drawPara('出生年月：',ps,c,70,14,343,669)
        c.line(95,668,550,668)
        self.drawPara('样本类型：',ps,c,70,14,95,614)
        self.drawPara('样本编号：',ps,c,70,14,323,614)
        self.drawPara('送检日期：',ps,c,70,14,95,580)
        self.drawPara('报告日期：',ps,c,70,14,323,580)
        c.line(95,559,550,559)
        ps.firstLineIndent = 24
        ps.fontSize = 12
        ps.wordWrap = "CJK"
        self.drawPara('<para>本次BRCA基因检测共检测到<font color="#9F7BAF" name="pf2">'+str(total)+'</font>个突变，其中致病相关突变为<font color="#9F7BAF" name="pf2">'+str(pathogenicNum)+'</font>个。</para>',ps,c,455,12,95,507)
        if pathogenicNum <= 0:
            self.drawPara('<para>本次检测在您的乳腺癌关键基因<font name="tnri"><i>BRCA1</i></font>和<font name="tnri"><i>BRCA2</i></font>上未发现致病变异，因而该因素没有提高您的乳腺癌患病风险。</para>',ps,c,455,36,95,441)
        else:
            self.drawPara('<para>本次检测在您的乳腺癌关键基因<font name="tnri"><i>BRCA1</i></font>和<font name="tnri"><i>BRCA2</i></font>上发现致病相关变异，建议您结合自己的身体状况、家族史信息，找临床医生进行进一步的检测和咨询。</para>',ps,c,455,36,95,441)
        c.line(95,420,550,420)
        self.drawOneTable(c,ps,1,resultPath,0,aHeight,451,26,88,True)
        # 第三页
        page3.drawOn(c,0,0)
        c.showPage()
        # 第四页
        # 记录页面下标
        ps.fontSize = 12
        page = 1
        page4_title.drawOn(c,60,731)
        self.drawPara('我们向您致敬!',ps,c,84,12,70,559)
        ps.firstLineIndent = 24
        self.drawPara('在健康管理上态度积极并作出明智选择，不仅是勇气，更是责任。我们愿陪您一起迈出健康管理的第一步。',ps,c,410,36,70,493)
        self.drawPara('作为您个人健康管理的重要参考，BRCA&nbsp;基因检测报告能协助您评估自身罹患遗传性乳腺癌的风险。报告基于您提供的检测样品，经过精密的基因测序和专业的数据分析，同时结合最新的科学研究成果，专业、客观地解释您的基因与已知遗传性乳腺癌突变之间的关系，用以协助您与您的健康管理顾问作出准确的健康评估和管理规划。',ps,c,410,108,70,355)
        self.drawPage(c,page)
        c.showPage()
        # 第五页
        page+=1
        ps.firstLineIndent = 0
        self.drawPara('风险揭示：',ps,c,410,12,115,559)
        ps.leftIndent = 12
        self.drawParaWithBullet('报告所给出的信息，是作为健康管理的参考信息，不能成为疾病诊断与治疗依据。疾病诊断与治疗需由临床医生给出结论与方案。','\xe2\x80\xa2',ps,c,384,36,139,493)
        self.drawParaWithBullet('健康的身体由基因、良好的生活习惯及生活环境共同影响，基因突变本身不是疾病形成的唯一因素。','\xe2\x80\xa2',ps,c,384,36,139,445)
        self.drawParaWithBullet('健康，是长期管理的结果，此报告所揭示的突变及相关的信息会因为研究的进展而发生变化。','\xe2\x80\xa2',ps,c,384,36,139,397)
        ps.firstLineIndent = 24
        ps.leftIndent = 0
        self.drawPara('送检样本的处理及数据分析、出具报告的整个过程，都有专业的监管与流程记录，以确保您的个人隐私与相应数据的安全。我们遵从相应的服务协议，在未获得本人授权情况下，数据不作他用。同时也请您妥善保管报告以及相应的数据信息，以免泄漏。',ps,c,410,84,115,283)
        ps.firstLineIndent = 0
        self.drawPara('祝，健康！',ps,c,72,12,463,213)
        self.drawPage(c,page)
        c.showPage()
        # 第六页       
        page+=1
        self.drawPage(c,page)
        page6_title.drawOn(c,60,731)
        page6_content.drawOn(c,171,446)
        ps.firstLineIndent = 24
        self.drawPara('<font name="tnri"><i>BRCA1/BRCA2</i></font>基因是抑癌基因，它们表达的蛋白在细胞&nbsp;DNA&nbsp;精密修复所需的酶促通路中起作用，调节细胞复制、&nbsp;DNA&nbsp;损伤修复、细胞正常生长，保护细胞不会生长失控和癌变。一些危害突变会导致这些基因或它们所表达的蛋白失去正常功能，无法修复&nbsp;DNA&nbsp;损伤，使得&nbsp;DNA&nbsp;中的异常不断累积，&nbsp;最终导致癌症的形成。',ps,c,410,120,70,278)
        self.drawPara('目前研究者们已发现了<font name="tnri"><i>BRCA1/2&nbsp;</i></font>基因中多达上万种的突变，参考国际癌症研究机构（International Agency for Research on Cancer，IARC）的分类标准，基因突变的临床意义分为如下5类 ：',ps,c,410,60,70,188)
        c.showPage()
        # 第二个表格
        ps.firstLineIndent = 0
        ps.leftIndent = 12
        self.drawParaWithBullet('Class 5 – 致病相关的变异&nbsp;Pathogenic','\xe2\x80\xa2',ps,c,386,12,185,644)
        self.drawParaWithBullet('Class 4 – 可能致病的变异&nbsp;Likely pathogenic','\xe2\x80\xa2',ps,c,386,12,185,620)
        self.drawParaWithBullet('Class 3 – 意义不明确的变异&nbsp;Uncertain Significance','\xe2\x80\xa2',ps,c,386,12,185,596)
        self.drawParaWithBullet('Class 2 – 可能良性的变异&nbsp;Likely benign','\xe2\x80\xa2',ps,c,386,12,185,572)
        self.drawParaWithBullet('Class 1 – 良性的变异&nbsp;Benign','\xe2\x80\xa2',ps,c,386,12,185,548)
        ps.leftIndent = 0
        self.drawPara('<para>本次检测，在您的<font name="tnri"><i>BRCA1/2</i></font>基因中共发现了17个突变。它们是：</para>',ps,c,410,12,186,496)
        # print resultPath
        page = self.drawTwoTable(c,ps,page,resultPath,0,841,365,30,74,True)
        # 第七页
        page+=1
        self.drawPage(c,page)
        page8_title.drawOn(c,60,717)
        ps.firstLineIndent = 24
        self.drawPara('根据遗传学“中心法则”，遗传信息由&nbsp;DNA&nbsp;开始最终传向蛋白质，&nbsp;蛋白分子共同协作使我们的身体正常运转。&nbsp;发生有害突变的&nbsp;DNA&nbsp;产生异常的蛋白（蛋白分子大小或分子结构发生变化），不能行使其正常功能，最终可能导致机体发生病变。',ps,c,410,60,70,572)
        page8_content.drawOn(c,75,463)
        self.drawPara('参考国际癌症研究机构（International Agency for Research on Cancer,<br/>IARC）的分类标准，基因变异的临床意义分类及标准为：',ps,c,410,36,70,377)
        ps.firstLineIndent = 0
        ps.leftIndent = 12
        self.drawParaWithBullet('致病相关变异：已证实能使癌症风险显著提高的突变。这种变异往往是移码突变，导致蛋白质不能正常表达。','\xe2\x80\xa2',ps,c,386,36,96,311)
        self.drawParaWithBullet('可能致病变异：没有明确证据，当前认为该变异有害。','\xe2\x80\xa2',ps,c,386,12,96,287)
        self.drawParaWithBullet('意义不明确变异：不确定该变异是有害还是无影响。这是一种常见的检测结果，很多变异一开始都被分为这一类。随着证据的不断补充，这些变异会被重新分类。','\xe2\x80\xa2',ps,c,386,60,96,215)
        self.drawParaWithBullet('可能良性变异：没有明确证据，当前认为该变异对人体无害。','\xe2\x80\xa2',ps,c,386,12,96,191)
        self.drawParaWithBullet('良性变异：被证实为对人体无害的变异，可以被认为是“未发生突变”。','\xe2\x80\xa2',ps,c,398,24,96,167)
        ps.firstLineIndent = 24
        ps.leftIndent = 0
        self.drawPara('注：致病相关变异有很高但不完全的遗传外显率，这意味着有害突变携带者有较高的癌变风险，但有的人可能最终并不患癌。',ps,c,410,36,70,101)
        c.showPage()
        # 第三个表格
        descriptions = self.getDescription()
        ps.firstLineIndent = 0
        self.drawPara('在您的<font name="tnri"><i>BRCA1/2</i></font>基因中，BRCA变异影响（临床意义分类）描述如下：',ps,c,450,12,164,656)
        page = self.drawThreeTable(c,ps,page,resultPath,0,0,841,217,30,True,descriptions,companyId,peakPath)
        ps.leading = 24
        ps.firstLineIndent = 24
        ps.fontName = "pf1"
        ps.fotnSize = 12
        # 第八页
        page+=1
        self.drawPage(c,page)
        page9_title.drawOn(c,60,716)
        self.drawPara('乳腺癌的病因尚未完全清楚，其中因相关基因突变引起的乳腺癌称为遗传性乳腺癌，这些基因包括：',ps,c,410,36,70,620)
        ps.firseLineIndent = 0
        tbps.textColor = '#FFFFFF'
        p00 = Paragraph('<para>高风险基因</para>',tbps)
        tbps.textColor = '#333333'
        p01 = Paragraph('<para><font name="tnri"><i>BRCA1</i></font>，<font name="tnri"><i>BRCA2</i></font>，<font name="tnri"><i>TP53</i></font>，<font name="tnri"><i>PTEN</i></font>，<font name="tnri"><i>STK11</i></font>，<font name="tnri"><i>CDH1</i></font>',tbps)
        tbps.textColor = '#FFFFFF'
        p10 = Paragraph('<para>中风险基因</para>',tbps)
        tbps.textColor = '#333333'
        p11 = Paragraph('<para leading=24><font name="tnri"><i>CHEK2</i></font>，<font name="tnri"><i>ATM</i></font>，<font name="tnri"><i>PALB2</i></font>，<font name="tnri"><i>BRIP1</i></font>，<font name="tnri"><i>MRE11A</i></font>，<font name="tnri"><i>RAD50</i></font>，<br/><font name="tnri"><i>RAD51C</i></font>，<font name="tnri"><i>NBN</i></font></para>',tbps)
        tbps.textColor = '#FFFFFF'
        p20 = Paragraph('<para>低风险基因</para>',tbps)
        tbps.textColor = '#333333'
        p21 = Paragraph('<para><font name="tnri"><i>CASP8</i></font>，<font name="tnri"><i>FGFR2</i></font>，<font name="tnri"><i>MAP3K</i></font>，<font name="tnri"><i>TOX3</i></font>，<font name="tnri"><i>LSP1</i></font></para>',tbps)
        p00.wrap(60,12)
        data = [[p00,p01],
                [p10,p11],
                [p20,p21]]
        t = Table(data,[100,310],[32,56,32])
        t.setStyle(TableStyle([('BACKGROUND',(0,0),(-2,-1),'#9F7BAF'),
                               ('TEXTCOLOR',(0,0),(-2,-1),'#FFFFFF'),
                               ('BOTTOMPADDING',(0,0),(0,-1),10),
                               ('BOTTOMPADDING',(0,1),(0,1),22),
                               ('LEFTPADDING',(0,0),(-1,-1),20),
                               ('LEFTPADDING',(1,0),(1,-1),10),
                               ('BOTTOMPADDING',(1,0),(1,-1),10),
                               ('BOTTOMPADDING',(1,1),(1,1),0),
                               ('GRID',(0,0),(-1,-1),0.25,'#9F7BAF')]))
        t.wrap(410,120)
        t.drawOn(c,70,490)        
        self.drawPara('遗传性乳腺癌占全部乳腺癌的5%~10%。<font name="tnri"><i>BRCA1</i></font>和<font name="tnri"><i>BRCA2</i></font>基因是最重要的两个遗传性乳腺癌易感基因，它们引起的乳腺癌占遗传性乳腺癌的40%~45%。',ps,c,422,36,70,424)
        page9_content2.drawOn(c,107,265)
        self.drawPara('平均来看，普通人在50岁之前患乳腺癌的风险为1.9%，70岁之前患乳腺癌的风险为7.3%。',ps,c,410,36,70,199)
        self.drawPara('（注:患病风险数据来源于欧美人群,仅供亚洲人群参考。）',ps,c,410,36,70,175)
        if(pathogenicNum <= 0):
            self.drawPara('<para textColor="#9F7BAF">本次检测在您的乳腺癌关键基因<font name="tnri"><i>BRCA1</i></font>和<font name="tnri"><i>BRCA2</i></font>上未发现致病变异，因而该因素没有提高您的乳腺癌患病风险。建议您结合本检测结果，保持良好心态，基于家族史及自身身体状况，积极管理自身健康。</para>',ps,c,410,36,70,85)
        else:
            self.drawPara('<para textColor="#9F7BAF">本次检测在您的乳腺癌关键基因<font name="tnri"><i>BRCA1</i></font>和<font name="tnri"><i>BRCA2</i></font>上发现致病相关变异，该因素可能提高您的乳腺癌患病风险。建议您结合本检测结果，基于家族史及自身身体状况，找临床医生进行进一步的检测和咨询，积极管理自身健康。</para>',ps,c,410,36,70,85)
        c.showPage()
        # 第九页
        page+=1
        self.drawPage(c,page)
        page10_title.drawOn(c,403,718)
        self.drawPara('人是二倍体基因组生物，每个细胞中有两套基因（一份来自父亲一份来自母亲）。一般来说，子代携带者只有其中一套基因上会遗传来自父辈的突变，被称为杂合子突变携带者。<font name="tnri"><i>BRCA1/BRCA2</i></font>&nbsp;&nbsp;突变升高癌症风险是显性遗传作用，只要等位基因中的一个基因位点发生突变就能表现出来。突变可以从父母任何一方遗传而来，也可以遗传给儿子或女儿。子代不论性别，从突变携带者父/母亲那里遗传到同一突变的概率为50%，因此男性也可能会携带有突变。',ps,c,410,132,115,524)
        page10_content.drawOn(c,161,243)
        if(pathogenicNum <= 0):
            self.drawPara('<para textColor="#9F7BAF">本次检测在您的乳腺癌关键基因<font name="tnri"><i>BRCA1</i></font>和<font name="tnri"><i>BRCA2</i></font>中未检测到致病突变，在大多数情况下，您的亲属不需要进行这两个基因的突变筛查。在有乳腺癌家族史的情况下，为了分析家族遗传风险信息，建议确诊为乳腺癌患者的直系亲属接受此基因的突变检测。</para>',ps,c,410,84,115,119)
        else:
            self.drawPara('<para textColor="#9F7BAF">本次检测在您的乳腺癌关键基因<font name="tnri"><i>BRCA1</i></font>和<font name="tnri"><i>BRCA2</i></font>中检测到致病相关突变，为了分析家族遗传风险信息，建议您的直系亲属检测基因中是否存在相同的有害突变。建议您的直系亲属提高预防意识，积极进行健康管理。强烈建议已经确诊为乳腺癌患者的直系亲属接受此基因的突变检测。</para>',ps,c,410,84,115,119)
        c.showPage()
        # 第十页
        page+=1
        self.drawPage(c,page)
        page11_title.drawOn(c,60,718)
        c.setFont('pf2',12)
        c.drawString(70,654,'性别和年龄')
        self.drawPara('女性和年龄增长是发生乳腺癌的主要危险因素。女性一生都有发生乳腺癌的风险，风险大约是男性的100倍。70岁女性患乳腺癌的短期风险大约是30岁女性的10倍。',ps,c,410,36,70,554)
        c.drawString(70,522,'乳腺癌家族史')
        page11_content.drawOn(c,150,240)
        self.drawPara('确凿证据表明，有乳腺癌家族史的女性，尤其是一级亲属患乳腺癌的女性，发生乳腺癌的风险升高。如果只有一名一级亲属患病，则风险翻倍；如果有两名一级亲属诊断患病，则风险增加4倍。',ps,c,410,60,70,140)
        c.showPage()
        # 第十一页
        c.setFont('pf1',14)
        page+=1
        self.drawPage(c,page)
        c.setFont('pf2',12)
        c.drawString(441,654,'主要遗传易感性')
        self.drawPara('确凿证据表明，有乳腺癌相关遗传基因突变的女性患病风险升高，取决于基因突变、家族史和其他影响基因表达的危险因素。',ps,c,410,36,115,578)
        c.drawString(477,546,'乳腺密度')
        self.drawPara('乳腺密度大的女性发生乳腺癌的风险增加。这通常是一种固有的特征，但在一定程度上可因生育、药物和酒精而改变。',ps,c,410,36,115,470)
        c.drawString(417,438,'其他可能的危险因素')
        page12_content.drawOn(c,170,108)
        c.showPage()
        # 第十二页
        c.setFont('pf1',14)
        page+=1
        self.drawPage(c,page)
        c.setFont('pf2',12)
        page13_title.drawOn(c,60,719)
        page13_icon1.drawOn(c,70,652)
        c.drawString(96,654,'避免饮酒')
        self.drawPara('饮酒女性患乳腺癌的风险较很少饮酒者高，这种风险在绝经前女性中最为显著。目前认为，酒精可刺激体内雌激素水平上升，与乳腺癌发生有关。因此,女性尤其是绝经前的女性，应戒酒或少饮酒。',ps,c,410,60,70,554)
        page13_icon2.drawOn(c,70,500)
        c.drawString(96,502,'合理膳食')
        self.drawPara('目前认为，膳食中的碳水化合物、脂肪会增加乳腺癌的发生风险,而水果蔬菜、叶酸、维生素D和膳食纤维能降低乳腺癌的发生风险。因此，平时应多吃一些蔬菜水果等含有维生素、膳食纤维的食品，预防乳腺癌的发生。',ps,c,410,60,70,402)
        page13_icon3.drawOn(c,70,348)
        c.drawString(96,350,'积极参加体育锻炼，保持合适的体重')
        self.drawPara('高体重是绝经后乳腺癌的危险因素，此时体内多余的脂肪会转变为雌激素，成为雌激素的主要来源，促进乳腺癌细胞的生长。要坚持体育锻炼，保持适当的体重，避免肥胖导致的乳腺癌风险增加。',ps,c,410,60,70,250)
        page13_icon4.drawOn(c,70,196)
        c.drawString(96,198,'保持心情舒畅，避免压力过大')
        self.drawPara('长期心情不畅的女性是乳腺癌的高危人群，建议大家在日常生活中保持情绪稳定,避免精神刺激,适当给自己减压,保持心情舒畅及乐观的生活态度,避免和减少精神、心理紧张因素,可以减少乳腺癌的发生。',ps,c,410,60,70,98)
        c.showPage()
        # 第十三页
        c.setFont('pf1',14)
        page+=1
        self.drawPage(c,page)
        c.setFont('pf2',12)
        page14_icon1.drawOn(c,343,651)
        c.drawString(369,654,'定期去正规医疗机构进行体检')
        self.drawPara('建议长期饮酒、心情不畅及乳腺增生、有乳腺癌家族史、携带相关基因有害突变的人群定期进行乳腺自检，同时去正规体检机构进行相关的检查，包括X线检查、糖类抗原CA153、CEA、乳腺B超等，早监测、早发现，早诊断、早治疗。',ps,c,410,60,115,530)
        c.showPage()
        # 第十四页
        c.setFont('pf1',14)
        page+=1
        self.drawPage(c,page)
        page15_title.drawOn(c,60,731)
        ps.fotnSize = 10
        ps.leading = 20
        ps.firstLineIndent = 0
        self.drawPara('【1】',ps,c,40,10,62,646)
        self.drawPara('Holly Yan (2013-05-14). "What\'s the gene that led to Angelina Jolie\'s double mastectomy?". Health. CNN.',ps,c,370,30,104,626)
        self.drawPara('【2】',ps,c,40,10,62,586)
        self.drawPara('"BRCA1 and BRCA2: Cancer Risk and Genetic Testing". National Cancer Institute. 29 May 2009.',ps,c,370,30,104,566)
        self.drawPara('【3】',ps,c,40,10,62,526)
        self.drawPara('effrey N. Weitzel, Veronica I. Lagos, Carey A. Cullinane, Patricia J. Gambol, Julie O. Culver, Kathleen R. Blazer, Melanie R. Palomares, Katrina J. Lowstuter, Deborah J. MacDonald (2007). "Limited Family Structure and BRCA Gene Mutation Status in Single Cases of Breast Cancer". Journal of the American Medical Association 297 (23): 2587–2595. doi:10.1001/jama.297.23.2587',ps,c,370,110,104,426)
        self.drawPara('【4】',ps,c,40,10,62,386)
        self.drawPara('Cui, J; Antoniou, AC; Dite, GS; Southey, MC; Venter, DJ; Easton, DF; Giles, GG; McCredie, MR; Hopper, JL (Feb 2001)."After BRCA1 and BRCA2-what next? Multifactorial segregation analyses of three-generation, population-based Australian families affected by female breast cancer". Am J Hum Genet 68 (2): 420–31. doi:10.1086/318187.PMC 1235275. PMID 11133358',ps,c,370,110,104,286)
        self.drawPara('【5】',ps,c,40,10,62,246)
        self.drawPara('Morris, Joi L.; Gordon, Ora K. (2010). Positive Results: Making the Best Decisions When You\'re at High Risk for Breast or Ovarian Cancer. Amherst, N.Y.: PrometheusBooks. ISBN 978-1-59102-776-8.',ps,c,370,70,104,186)
        self.drawPara('【6】',ps,c,40,10,62,146)
        self.drawPara('Campeau PM, Foulkes WD, Tischkowitz MD. Hereditary breast cancer: New genetic developments, new therapeutic avenues. Human Genetics 2008; 124(1):31–42.',ps,c,370,50,104,106)
        c.showPage()
        # 第十五页
        page+=1
        self.drawPage(c,page)
        self.drawPara('【7】',ps,c,40,10,107,646)
        self.drawPara('Easton DF. How many more breast cancer predisposition genes are there? Breast Cancer Research 1999; 1(1):14–17. [PubMed Abstract]',ps,c,370,30,149,606)
        self.drawPara('【8】',ps,c,40,10,107,566)
        self.drawPara('Pal T, Permuth-Wey J, Betts JA, et al. BRCA1 and BRCA2 mutations account for a large proportion of ovarian carcinoma cases. Cancer 2005; 104(12):2807–16.',ps,c,370,30,149,526)
        self.drawPara('【9】',ps,c,40,10,107,486)
        self.drawPara('Howlader N, Noone AM, Krapcho M, et al. (eds). SEER Cancer Statistics Review, 1975-2011, National Cancer Institute. Bethesda, MD, http://seer.cancer.gov/csr/1975_2011/, based on November 2013 SEER data submission, posted to the SEER web site, April 2014.',ps,c,370,30,149,406)
        self.drawPara('【10】',ps,c,40,10,107,366)
        self.drawPara('Antoniou A, Pharoah PD, Narod S, et al. Average risks of breast and ovarian cancer associated with BRCA1 or BRCA2 mutations detected in case series unselected for family history: A combined analysis of 22 studies. American Journal of Human Genetics2003; 72(5):1117–1130.',ps,c,370,30,149,286)
        self.drawPara('【11】',ps,c,40,10,107,246)
        self.drawPara('Chen S, Parmigiani G. Meta-analysis of BRCA1 and BRCA2 penetrance. Journal of Clinical Oncology 2007; 25(11):1329–1333.',ps,c,370,30,149,226)
        ps.fontName = 'pf1'
        ps.fontSize = 12
        ps.firstLineIndent = 24
        ps.leading = 24
        c.showPage()
        # 第十六页
        page+=1
        self.drawPage(c,page)
        page16_title.drawOn(c,60,731)
        self.drawPara('本检测的意义是通过基因测序了解遗传背景下患有某癌症的风险情况，如能在疾病未发生之前改变生活方式，积极地干预、预防，可以延缓或者阻止该疾病的发生，建议在疾病未发生前按照健康建议养成良好的生活习惯。',ps,c,410,60,70,596)
        self.drawPara('癌症遗传风险评估不等于临床诊断，其检测结果不能作为判断是否患有癌症的标准。即某癌种易感基因检测结果阳性或阴性，只代表受检者患病的风险较高或较低，而不代表其已经患有该癌症或不会患有该癌症。癌症遗传风险评估结果可作为临床医生诊断疾病时的参考资料。',ps,c,410,84,70,482)
        self.drawPara('检测结果为高风险或较高风险时，提示您较他人更易罹患此类癌症，但并不代表必定患此癌症。受检测者依据检测结果所做出的民亊行为，由受检者自行承担一切法律后果。',ps,c,410,60,70,392)
        self.drawPara('由于科技不断发展，世界范围内数以万计的科学家正在夜以继日地致力于基因与健康之间的研究，我们会保持跟进相关的研究进展，根据最新科研成果调整和丰富基因检测内容。本检测只对检测结果的当前正确性负责并承诺检测服务的准确率将保持在国际先进水平上。',ps,c,410,84,70,278)
        self.drawPara('提供样本者应对受检者与样本的一致性负责。本结果报告仅对此次送检样品负责。',ps,c,410,12,70,212)
        c.showPage()
        # 第十七页
        if companyId != 33:
            celloud.drawOn(c,75,75)
        # 保存
        c.save()
if __name__ == '__main__':
    RockyPdf = RockyPdf.getInstance()
    RockyPdf.createPDF('/ossfs/output/12/123','16122605441911',33)
