<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML>
<html>
	<head>
  		<script src="<%=request.getContextPath() %>/pages/16s/js/svg.js" data-path="<%=request.getContextPath() %>/pages/16s/js"></script>
		
		<link href="<%=request.getContextPath()%>/pages/16s/css/main.css" type="text/css" rel="stylesheet">

		<script type="text/javascript"
			src="<%=request.getContextPath()%>/pages/16s/js/jquery1.4.js">
		</script>
		<script type="text/javascript">
			$(document).ready(function(){
				$(".queryId").toggle(function(){
					$(".close").css("display","none");
					$(".close").html("");
					var queryId = $(this).text();
					var path = $("input[name='path']").val();
					var fileName = $("input[name='fileName']").val();
					//window.open("<%=request.getContextPath() %>/resultSearch.action?queryId="+queryId+"&path="+path+"&fileName="+fileName);
					$.post("resultSearch.action",{"queryId":queryId,"path":path,"fileName":fileName,"t":Math.random()},function(responseText){
						$("#"+queryId).css("display","");
						$("#"+queryId).html(responseText);
					});
				},function(){
					var queryId = $(this).text();
					$("#"+queryId).html("");
					$("#"+queryId).css("display","none");
				});
			});
		</script>
	</head>
	<body>
		<div id="page">
			<div id="header"></div>
			<div id="main">
				<div class="box">
					<div class="box-tit">
						16S rDNA Sequence matching
					</div>
					<div class="box-txt">
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
				</div>
				<div class="box">
					<div class="box-tit">
						系统推荐结果
					</div>
					<div class="box-txt">
						<input type="hidden" name="path" value="<s:property value="path" />" />
						<input type="hidden" name="fileName" value="<s:property value="fileName" />" />
						<table border="0" cellspacing="1">
							<tr>
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
									<td colspan="5" style="display: none" class="close" id="<s:property value="#sr.queryId" />">
										
									</td>
								</tr>
							</s:iterator>
						</table>
					</div>
				</div>
			</div>
			<a href="javascript:window.history.go(-1)" class="back"><img src="<%=request.getContextPath() %>/pages/16s/images/backed.png" style="border: 0"/></a>
			<div id="footer">
				© 2012 CDC
			</div>
		</div>
	</body>
</html>
