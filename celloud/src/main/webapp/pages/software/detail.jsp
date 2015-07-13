<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<a href="javascript:goToSoftListPage();" class="close"></a>
<div class="appGroup">
	<div class="app-icon">
		<div class="center"><img src="<%=request.getContextPath()%>/images/app/<s:property value="soft.pictureName"/>"></div>
	    <div class="center">
	    	<s:if test='soft.isAdd=="yes"'>
            		<img src="<%=request.getContextPath()%>/images/software/added.png" />
           	</s:if>
           	<s:elseif test='soft.isAdd=="no"'>
           		<s:if test='soft.softwareId==6||soft.softwareId==9||soft.softwareId==10||soft.softwareId==12||soft.softwareId==74||soft.softwareId==75||soft.softwareId==76||soft.softwareId==77||soft.softwareId==78||soft.softwareId==79||soft.softwareId==81||soft.softwareId==83||soft.softwareId==105||soft.softwareId==106||soft.softwareId==107||soft.softwareId==108||soft.softwareId==109||soft.softwareId==110'>
            		<img src="<%=request.getContextPath()%>/images/software/developing.png" />
            	</s:if>
            	<s:else>
            		<a href="javascript:addBhri('addAppInDetail','<s:property value="soft.softwareId"/>','<s:property value="soft.softwareName"/>','<s:property value="soft.pictureName"/>','<s:property value="soft.host"/>','<s:property value="soft.isAdd"/>',0)" id="addAppInDetail" class="add"><img src="<%=request.getContextPath()%>/images/software/icon_add.png" />添加</a>
            	</s:else>
           	</s:elseif>
	    </div>
	</div>
	<div class="app-item">
       	<div class="appTitle"><s:property value="soft.softwareName"/></div>
       	<div class="appContent">
       		<s:property value="soft.description"/>
       	</div>
       	<div class="appStar sa<s:property value="soft.bhri"/>"></div>
       	<div class="appCount"><s:property value="soft.bhri"/></div>
    </div>
</div>

<h3>简介</h3>
<p>
	<s:property value="soft.intro" /><br/>
	<s:if test="%{soft.appDoc!=null&&!soft.appDoc.equals('')}">
		<a id="goToAppInfoBtn<s:property value="soft.softwareId"/>" href="javascript:goToAppInfo(<s:property value="soft.softwareId"/>);" class="appDetailToInfo" _appId='AppInfo' _appName='AppInfo' _appImg='images/app/appInfo.png' _iframeSrc='getAppInfo.action?appName=<s:property value="soft.softwareName"/>&appId=<s:property value="soft.softwareId"/>'>&gt;&gt;查看更多</a>
	</s:if>
</p>

<!-- HBV_Tree、Exome_SNV、HCV_Genotype、16S -->
<h3>截图</h3>
<s:if test="%{screenList.size>0}">
    <div id="myCarousel" class="carousel slide">
		<div class="carousel-inner">
			<s:iterator value="screenList" id="li" status="st">
				<div class="<s:if test="%{#st.index==0}">active</s:if> item">
					<img style="width: 600px;height: 300px;" src="<%=request.getContextPath()%>/images/screenshot/<s:property value="#li.screenName" />" alt="">
					<div class="carousel-caption" style="height: 20px;">
                      <h4 style="margin-top: 0px;"><s:property value="#st.index+1"/></h4>
                    </div>
				</div>
			</s:iterator>
		</div>
		<s:if test="%{screenList.size>1}">
			<a class="left carousel-control" href="#myCarousel" data-slide="prev">&lsaquo;</a>
			<a class="right carousel-control" href="#myCarousel" data-slide="next">&rsaquo;</a>
		</s:if>
	</div>
</s:if>
<s:elseif test='%{screenList.size==0}'>
	暂时没有截图
</s:elseif>

<!-- 提交评论 -->
<h3>评论</h3>
<s:if test='soft.isAdd=="yes"'>
	<textarea id="inputComment"></textarea>
	<a href="javascript:submitComment('<s:property value="soft.softwareId"/>');" class="btn btn-green">提交评论</a>
	<span id="commentInfoDiv"></span>
</s:if>
<s:elseif test='soft.isAdd=="no"'>
	添加软件发表评论<br/>
</s:elseif>

<!-- 评论列表 -->

<h3>全部评论</h3>
<s:if test="%{commentList.size>0}">
		<div id="commentDiv">
		<s:iterator value="commentList" id="comment" status="st">
			<div class="comment">
			<!-- 评论内容 -->
			<p>
				<a href="javascript:void(0);"><s:property value="#comment.commentUserName" /></a>：<s:property value="#comment.comment" />
				<s:if test='soft.isAdd=="yes"'>
					<a href="javascript:replyComment('<s:property value="#comment.id" />');">回复</a>
					<s:if test="#comment.commentUserName==#session.userName">
	           			<a href="javascript:deleteComment('<s:property value="soft.softwareId"/>','<s:property value="#comment.id" />',0);">删除</a>
	           		</s:if>
				</s:if>
				<span><s:date name="#comment.commentDate" format="yyyy-MM-dd HH:mm:ss" /></span>
			</p>
			<!-- 回复内容 -->
			<s:iterator value="#comment.replyList" id="reply" status="st">
				<p>
					<a href="javascript:void(0);"><s:property value="#reply.replyUserName" /></a>回复
					<a href="javascript:void(0);"><s:property value="#comment.commentUserName" /></a>：
					<s:property value="#reply.comment" />
					<s:if test='soft.isAdd=="yes"'>
						<s:if test="#reply.replyUserName==#session.userName">
		         			<a href="javascript:deleteComment('<s:property value="soft.softwareId"/>','<s:property value="#reply.id" />',1);">删除</a>
		         		</s:if>
					</s:if>
					<span><s:date name="#reply.commentDate" format="yyyy-MM-dd HH:mm:ss" /></span>
				</p>
       		</s:iterator>
       		<!-- 回复输入框 -->
       		<p id='<s:property value="#comment.id" />Textarea' style="display: none;">回复：<textarea></textarea></p>
            <p>
            	<a id='<s:property value="#comment.id" />Asubmit' href="javascript:submitReply('<s:property value="#comment.id" />','<s:property value="soft.softwareId"/>');" class="btn btn-blue" style="display: none;">确认回复</a>
            	<a id='<s:property value="#comment.id" />Acancel' href="javascript:cancelReply('<s:property value="#comment.id" />');" class="btn" style="display: none;">取消回复</a>
            	<span id="<s:property value="#comment.id" />SpanInfo"></span>
            </p>
			</div>
		</s:iterator>
		</div>
</s:if>
<s:else>
	暂时没有评论<br/>
</s:else>
<script type="text/javascript">
$(document).ready(function(){
	$("#commentInfoDiv").html("");
});
</script>