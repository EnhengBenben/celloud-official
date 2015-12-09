<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML>
<html>
	<head>
		<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath() %>/bootstrap/css/bootstrap.css">
		<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath() %>/bootstrap/css/bootstrap-responsive.css">
		<link href="<%=request.getContextPath()%>/pages/app/css/style.css" rel="stylesheet" type="text/css" media="screen" />
	</head>
	<body>
	<div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container-fluid">
          <a class="brand" href="javascript:void(0)">16S rDNA未知病原筛查分析</a>
          <ul class="nav pull-right">
            <li><a href="javascript:viewReport()" class="openReport">查看报告</a></li>
          </ul>
        </div>
      </div>
    </div>
	<div class="container-fluid">
		<div class="alert alert-info">
	    	<strong>16S rDNA Sequence matching</strong>
	    	<p>
				The data you have uploaded to Serveris displayed in a tree along
				with the 20 best matches in database.
			</p>
			<p>
				If you hoover over the labels you get the information of the
				highlighted strain as a popup window.
			</p>
			<p>
				You can select a preferred text field to be shown as labels for
				all the nodes by choosing a field in the drop down box at the
				bottom.
			</p>
			<p>
				The gel images of the database patterns are shown next to each
				node in the dendrogram. The red marks are the bands on the
				pattern in the database, the green marks the fragments you have
				uploaded.
			</p>
			<p>
				If you click on a label in the tree, a new view will appear where
				your submitted sequence is aligned to the sequence you have
				clicked on.
			</p>
	    </div>
	    <div class="row-fluid">
	    	<p class="text-info font-big">系统推荐结果:&nbsp;&nbsp;<a href="<%=request.getContextPath()%>/pages/app/16s/home.jsp" class="back"><img src="<%=request.getContextPath() %>/images/publicIcon/backed.png" style="border: 0"/></a></p>
	    	<input type="hidden" id="sequence" value="<%=request.getContextPath()%>/${requestScope.sequence}" />
			<input type="hidden" name="path" value="<s:property value="path" />" />
			<input type="hidden" name="fileName" value="<s:property value="fileName" />" />
			<table class="table table-bordered">
				<tr style="background-color: #d9edf7">
					<th width="10%">Query_id</th>
					<th width="10%">Subject_id</th>
					<th width="10%">Identity</th>
					<th width="10%">Score</th>
					<th width="60%">Strain</th>
				</tr>
				<s:iterator id="sr" status="st" value="sysList">
					<tr>
						<td class="queryId"><a style="color:blue;cursor:pointer"><s:property value="#sr.queryId" /></a></td>
						<td><s:property value="#sr.subjectId" /></td>
						<td><s:property value="#sr.identity" /></td>
						<td><s:property value="#sr.score" /></td>
						<td><s:property value="#sr.subjectAnnotation" /></td>
					</tr>
					<tr>
						<td colspan="5" style="display: none" id="<s:property value="#sr.queryId" />">
							
						</td>
					</tr>
				</s:iterator>
			</table>
	    </div>
	</div>
	<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/jQuery/jquery-1.11.3.min.js"></script>
	<script src="<%=request.getContextPath() %>/pages/app/16s/js/svg.js" data-path="<%=request.getContextPath() %>/pages/app/16s/js"></script>
	<script type="text/javascript">
	var softwareId = window.parent.document.getElementById("softwareId").value;
	var softwareName = window.parent.document.getElementById("softwareName").value;
		$(document).ready(function(){
			$(".queryId").toggle(function(){
				$(".close").css("display","none");
				$(".close").html("");
				var queryId = $(this).text();
				var path = $("input[name='path']").val();
				var fileName = $("input[name='fileName']").val();
				$.post("../../../16s!resultSearch",{"queryId":queryId,"path":path,"fileName":fileName,"t":Math.random()},function(responseText){
					$("#"+queryId).css("display","");
					$("#"+queryId).html(responseText);
				});
			},function(){
				var queryId = $(this).text();
				$("#"+queryId).html("");
				$("#"+queryId).css("display","none");
			});
		});
		function viewReport(){
			
			var appId = $(".leftApp", window.parent.document);
			window.parent.setAPP(softwareId);
			appId[2].click();
		}
	</script>
</body>
</html>
