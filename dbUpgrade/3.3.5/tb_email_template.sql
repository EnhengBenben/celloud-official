/*!40101 SET NAMES utf8 */;

update `tb_email_template` set context = '<p style="color: rgb(0, 0, 0); font-family: STHeiti; font-size: medium; line-height: normal;">尊敬的用户${userName}您好：</p>

<p style="color: rgb(0, 0, 0); font-family: STHeiti; font-size: medium; line-height: normal;"> </p>

<p style="color: rgb(0, 0, 0); font-family: STHeiti; font-size: medium; line-height: normal;">        您的 ${projectName} 项目下数据${data}运行 ${app} 已经完成，您可以进入 CelLoud 查看结果。</p>

<p style="color: rgb(0, 0, 0); font-family: STHeiti; font-size: medium; line-height: normal;"> </p>

<p style="color: rgb(0, 0, 0); font-family: STHeiti; font-size: medium; line-height: normal;"><span style="line-height: 20.8px;">        </span>启动时间: <span style="color: rgb(0, 0, 0); font-family: STHeiti; font-size: medium; line-height: normal;"></span>${start}<span style="color: rgb(0, 0, 0); font-family: STHeiti; font-size: medium; line-height: normal;"></span></p>

<p style="color: rgb(0, 0, 0); font-family: STHeiti; font-size: medium; line-height: normal;"><span style="color: rgb(0, 0, 0); font-family: STHeiti; font-size: medium; line-height: 20.8px;">        </span><span style="color: rgb(0, 0, 0); font-family: STHeiti; font-size: medium; line-height: normal;">结束时间: </span><span style="color: rgb(0, 0, 0); font-family: STHeiti; font-size: medium; line-height: normal;"></span><span style="color: rgb(0, 0, 0); font-family: STHeiti; font-size: medium; line-height: normal;">${end}</span><span style="color: rgb(0, 0, 0); font-family: STHeiti; font-size: medium; line-height: normal;"></span></p>

<p style="color: rgb(0, 0, 0); font-family: STHeiti; font-size: medium; line-height: normal;"> </p>

<p style="color: rgb(0, 0, 0); font-family: STHeiti; font-size: medium; line-height: normal;"><span style="line-height: 20.8px;">        </span>本邮件来自 CelLoud 系统，请勿回复。</p>' where id = 1;
