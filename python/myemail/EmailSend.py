#!/usr/bin/python
# -*- coding: utf-8 -*-
# python version 2.7.6

import smtplib
import sys
import datetime
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart
from email.mime.image import MIMEImage
from EmailPro import EmailPro

def send(email_to,title,context):
	# start time
	d1 = datetime.datetime.now()
	 # email info
	msg=MIMEMultipart()
	msg['From']=EmailPro.username
	msg['To']=email_to
	msg['Subject']=title
	con = MIMEText(context, 'html', 'utf-8')
	msg.attach(con)
	# send
	server=smtplib.SMTP(EmailPro.smtp)
	server.docmd('ehlo',EmailPro.username)
	server.login(EmailPro.username,EmailPro.password)
	server.sendmail(EmailPro.username,email_to,msg.as_string())
	server.quit()
	# count time
	d2 = datetime.datetime.now()
	print "send email use:"+str(d2-d1)
if __name__=='__main__':
	send("linyongchao@novacloud.com","2246","xx")