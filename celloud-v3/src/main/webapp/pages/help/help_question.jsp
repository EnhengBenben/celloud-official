<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link href="<%=request.getContextPath()%>/css/help.css" rel="stylesheet" type="text/css" />
<section class="content-header">
  <h1>
    <small></small>
  </h1>
  <ol class="breadcrumb">
    <li><a href="javascript:showHelp()"><i class="fa fa-dashboard"></i>帮助</a></li>
    <li class="active">常见问题</li>
  </ol>
</section>
<!-- Main content -->
<section class="content">
  <div class="row">
	  <div class="col-sm-2">
	      <div class="btn-group-vertical" style="width:100%;padding-right:0px;padding-left:0px;">
	         <a href="javascript:userHelp.openPage('pages/help/help_main.jsp')" class="btn btn-success">
	            用户指南
	        </a>
	        <a href="javascript:userHelp.openPage('pages/help/help_usefast.jsp')" class="btn btn-success">
	            快速上手
	        </a>
	        <a href="javascript:userHelp.openPage('pages/help/help_question.jsp')" class="btn btn-success bg-green-active">
	            常见问题
	        </a>
	      </div>
	      <!-- <div class="input-group" style="margin-top:5px;">
	        <input type="text" class="form-control">
	        <span class="input-group-btn">
	          <button class="btn btn-success btn-flat" type="button">搜索</button>
	        </span>
	      </div> -->
	  </div>
	  <div class="col-xs-10 pull-right" style="padding-left:0px;font-size:12px; font-color:#8A8A8A;">
	    <div class="box box-success">
	        <div class="box-header with-border" style="font-size:12px; font-color:#8A8A8A;">
	          
	        </div><!-- /.box-header -->
	        <div class="box-body">
		        <ul class="nav nav-tabs">
				   <li class="active" ><a href="#question-platform" data-toggle="tab">平台</a></li>
				   <li><a href="#question-client" data-toggle="tab">客户端</a></li>
				   <li><a href="#question-newideas" data-toggle="tab">我有新想法</a></li>
				</ul>
				<div class="tab-content">
		        <ul id="question-platform" class="help-list tab-pane active question-list" >
		          <li>平台从哪里进行注册呢？
		              <div>
		                              答：CelLoud生物信息云平台目前暂不会对外开放注册，所有账号的申请都是内部申请 获得的。
		              </div>
		          </li>
		          <li>为什么使用IE浏览器看到的页面是错乱的呢？
		              <div>
		                              答：CelLoud生物信息云平台最佳用户体验是在Chrome、Firefox、IE9+、360或者搜狗 浏览器的极速模式，对于比较古老的浏览器 目前暂时不支持，所以需要用户升级 到现代浏览器上使用。
		              </div>
		          </li>
		          <li>数据上传为什么会中断？
		              <div>
		                              答：数据传输和您所在的网络稳定性有密切的关系，目前平台部分的数据上传已经尽可 能的进行优化，实现分块上传。如果网络不稳定，建议您使用客户端上 传，web页 面上传失败只能重新上传，不能断点续传。
		              </div>
		          </li>
		          <li>为什么流程运行了好久都没有出结果？
		              <div>
		                              答：流程运行不出现结果由多方面原因造成的。如果不确定原因，请及时通过邮件告诉 我们（service@celloud.cn），邮件中最好告诉我们 您的电话，我们工作人员会 尽快和您取得联系。
		              </div>
		          </li>
		          <li>上传的数据可以下载吗 ？
		              <div>
		                              答：平台提供数据的实时备份和存储服务，因为下载对服务器造成压力，目前我们没有 开放下载功能。保存在平台的数据是安全的，后续我们会根据实际的需要 开发并 且开放这部分功能。
		              </div>
		          </li>
		          <li>个人用户可以体验平台的哪些服务？
		              <div>
		                              答：平台的定制App是从临床需求出发为医院、生物科技公司、健康体检中心等单位用 户定制的。个人用户可以使用平台开放的工具型分析软件，体验平台灵 活、方 便、快捷的服务。
		              </div>
		          </li>
		          <li>平台用户如何修改密码和邮箱信息？
		              <div>
		                              答：首先通过原使用者账号登录，之后在平台页面右上角的用户资料内可自行修改密码 和邮箱信息，不需要向平台申请。
		              </div>
		          </li>
		          <li>分析报告中与病人相关的信息会保存在平台吗？
		              <div>
		                              答：平台不会收集与病人相关的任何信息，所以用户在检测报告打印完成并将报告下载 到本地后，只需要在病人信息填写界面点击“重置”按钮，所有相关信息 都会清 空，不会在平台留下痕迹。
		              </div>
		          </li>
		          <li>平台可以同时运行几个不同App吗？
		              <div>
		                              答：可以，这也是平台独具的分析优势。用户可以同时并行运行多个App，分析速度不 会因此而减慢。
		              </div>
		          </li>
		          <li>平台的App有升级服务吗 ？
		              <div>
		                              答：平台的每一个App都会不定期的升级其服务能力，包括数据库的扩充、算法的更优 化、运行速度的提升等等。用户不需要做任何申请即可免费实时使用最 新版本的 App服务。
		              </div>
		          </li>
		          <li>平台App对数据有测序平台限定要求吗？
		              <div>
		                              答：平台App是定制开发的，因此不同服务商提供的测序平台产出的数据有不同的分析 App。用户如果在使用中更换了测序平台，需提前与我们联系，重新 定制App，以 免耽误您的工作。
		              </div>
		          </li>
		          <li>平台除了能提供测序数据分析服务外，还能提供哪些其他服务？
		              <div>
		                              答：Celloud是一个开放平台，平台面对医院、测序平台服务商、检测产品开发的科技 企业、体检中心、保险公司等与基因健康相关的领域均开放，因此 平台上汇集了 多方的资源。无论您所处于那个行业，只要有基因检测分析的相关需求，无论你缺 乏哪个环节的专业支持，均可以通过云平台找到最佳助力及 解决方案。
		              </div>
		          </li>
		        </ul>
		        <ul id="question-client" class="help-list tab-pane question-list">
		           <li>客户端工具怎么安装？
	                  <div>
	                                  答：目前客户端工具不需要直接安装，直接从官网上下载解压缩到非中文目录下面，点 击里面的celloud.exe程序即可执行。
	                  </div>
	              </li>
	              <li>客户端工具无法打开？
	                  <div>
	                                  答：在打开软件过程中，如果杀毒软件提示是否信任，请把客户端加入信任列表，否则 软件会被杀毒软件杀掉造成无法启动。
	                  </div>
	              </li>
	              <li>客户端工具上传数据中断了怎么办？
	                  <div>
	                                  答：客户端工具上传数据，支持数据的断点续传，支持数据的压缩上传，整个过程用户 无需参与，客户端会自动化的判断 进行处理。
	                  </div>
	              </li>
	              <li>我是mac系统，能使用客户端工具吗？
	                  <div>
	                                  答：暂时不能。目前客户端工具仅支持windows系统，工具有x86和x64两个版本。用户 根据需要进行下载，后续我们会推出mac版本。
	                  </div>
	              </li>
	              <li>利用客户端最多可以一次上传多少个数据文件？
	                  <div>
	                                  答：客户端推荐一次上传的文件不要过多，建议不要超过20个为宜。
	                  </div>
	              </li>
	              <li>客户端具备无人值守，上传进度通报的功能吗？
	                  <div>
	                                  答：客户端上传的数据在上传成功后会自动给用户发送成功邮件。后续我们还会增加支 持手机短信提醒的功能。
	                  </div>
	              </li>
		        </ul>
		        <ul id="question-newideas" class="help-list tab-pane question-list">
	               <li>我们想进行报告或者流程的定制化怎么办？
	                  <div>
	                                  答：CelLoud生物信息云平台接受客户的自定义需求，如果您有这方面的想法或者业 务，可以通过邮件（service@celloud.cn）和我 们联系。
	                  </div>
	              </li>
	              <li>我们自己开发的流程可以部署在云平台上运行吗？
	                  <div>
	                                  答：可以。我们欢迎第三方公司的流程接入平台。
	                  </div>
	              </li>
	              <li>我们只有检测需求，但不具备检测技术也没有测序平台，celloud能解决这样的 问题吗？
	                  <div>
	                                  答：可以，celloud平台汇集了研发、测序、分析相关的丰富资源。我们可以为您提供 资源配置方案，经过您的评估确认后，快速形成检测方案——数据 产出——专业生物 信息分析——检测报告的服务链，满足您的检测需求。
	                  </div>
	              </li>
	            </ul>
	            </div>
	        </div>
	      </div>
	  </div>
  </div>
</section><!-- /.content -->
<script type="text/javascript">
$(".question-list li").click(function(){
    $(this).children("div").toggle();
});
</script>