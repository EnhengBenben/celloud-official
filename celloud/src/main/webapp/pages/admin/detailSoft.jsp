<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<a style="float:right" class="btn btn-primary" onclick="javascript:toSoftList();">返回</a>
<table  class="table table-bordered" style="width:100%">
  <tr>
  	<td width="180" rowspan="3">软件图片:</td>
  	<td rowspan="3"><img alt="" style="width: 100px;height: 100px;" src="<%=request.getContextPath()%>/images/app/${soft.pictureName}"></td>
  	<td width="180">软件名称:</td>
  	<td>${soft.softwareName}</td>
  	<td width="180">英文名称:</td>
  	<td>${soft.englishName}</td>
  </tr>
  <tr>
  	<td>分类:</td>
  	<td>${soft.classify}</td>
  	<td>添加时间:</td>
  	<td>${soft.createDate}</td>
  </tr>
  <tr>
  	<td>软件入口:</td>
  	<td>${soft.softwareEntry}</td>
  	<td>进程名:</td>
  	<td>${soft.processName}</td>
  </tr>
  <tr>
  	<td>软件部署地址:</td>
  	<td>${soft.host}</td>
  	<td>人气指数:</td>
  	<td>${soft.bhri}</td>
  	<td>关键词:</td>
  	<td>${soft.description}</td>
  </tr>
  <tr>
  	<td>管理员邮箱:</td>
  	<td>${soft.email}</td>
  	<td>应用类型标志位:</td>
  	<td>
    	<s:if test="%{soft.type == 1}">C/S软件</s:if>
		<s:elseif test="%{soft.type == 2}">数据库</s:elseif>
		<s:elseif test="%{soft.type == 3}">B/S软件</s:elseif>
		<s:elseif test="%{soft.type == 4}">JS软件</s:elseif>
    </td>
    <td>简介:</td>
  	<td>${soft.intro}</td>
  </tr>
  <tr>
  	<td>是否推荐:</td>
  	<td>
    	<s:if test="%{soft.flag == 0}">否</s:if>
		<s:elseif test="%{soft.flag == 1}">是</s:elseif>
    </td>
    <td>能否直接运行数据:</td>
  	<td>
	  	<s:if test="%{soft.runData == 0}">是</s:if>
		<s:elseif test="%{soft.runData == 1}">否</s:elseif>
	</td>
	<td>是否需要设置参数:</td>
  	<td>
	  	<s:if test="%{soft.param == 0}">否</s:if>
		<s:elseif test="%{soft.param == 1}">是</s:elseif>
	</td>
  </tr>
  <tr>
  	<td>运行至少需要数据条数:</td>
  	<td>
  		${soft.dataNum}
	</td>
	<td>运行文件格式:</td>
  	<td>${soft.formatDesc}</td>
	<td>数据类型:</td>
  	<td>
	  	<s:if test="%{soft.proDataType == 1}">EST</s:if>
		<s:elseif test="%{soft.proDataType == 2}">Genome</s:elseif>
		<s:elseif test="%{soft.proDataType == 3}">Transcriptome</s:elseif>
		<s:elseif test="%{soft.proDataType == 4}">miRNA</s:elseif>
		<s:elseif test="%{soft.proDataType == 5}">Exome</s:elseif>
	</td>
  </tr>
  <tr>
  	<td>软件截图:</td>
  	<td colspan="5">
    	<s:if test="%{screenList.size>0}">
             <div id="myCarousel" class="carousel slide">
				<div class="carousel-inner">
					<s:iterator value="screenList" id="li" status="st">
						<div class="<s:if test="%{#st.index==0}">active</s:if> item">
							<img style="width: 400px;height: 200px;" src="<%=request.getContextPath()%>/images/screenshot/<s:property value="#li.screenName" />" alt="">
						</div>
					</s:iterator>
				</div>
				<s:if test="%{screenList.size>1}">
					<a class="left carousel-control" href="#myCarousel" data-slide="prev">&lsaquo;</a>
					<a class="right carousel-control" href="#myCarousel" data-slide="next">&rsaquo;</a>
				</s:if>
			</div>
		</s:if>
    </td>
  </tr>
</table>