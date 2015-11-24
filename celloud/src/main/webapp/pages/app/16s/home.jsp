<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>16S</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
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
            <li><a href="javascript:void(0)" class="openReport">查看报告</a></li>
          </ul>
        </div>
      </div>
    </div>
    <div class="container-fluid">
    	<div class="alert alert-info">
	    	<strong>Introduction：</strong>
	    	<p>
				Enter a sequence as a string of A,C,T and G characters
				(capitals).
			</p>
			<p>
				The sequence is submitted to the Server where it is matched to
				over 5,200 complete 16S rDNA sequences of bacterial type strains.
			</p>
			<p>
				The 20 best matching database entries are returned and clustered
				in a Neighbor Joining dendrogram with the uploaded sequence.
			</p>
	    </div>
    	<div class="row-fluid">
			<div class="span12">
				<p class="text-info font-big">直接运行:&nbsp;&nbsp;</p>
				<p class="text-info">
							Click&lt; Perform match &gt;to identify your sequence. If you
							have no data readily available, push the button.
				</p>
				<form action="16s!commonMatch" method="post">
				    <input type="hidden" id="fileId" name="fileId"/>
                   	<textarea name="sequence" id="sequence" class="span9" rows="6"></textarea><br />
                   	<div id="btnbox" class="c">
                   <input type="button" value="Perform match" class="btn1" />
					<input type="button" value="Random Sequence" class="btn1" />
				   <input type="button" value="Clear sequence" class="btn1" />
				    <span class="run_success" id="runAlertInput"></span><br />
				    </div>
               	</form>
			</div>
		</div><hr class="hr0">
    	<div class="row-fluid" id="checkDataDiv">
			<div class="span12">
				<p class="text-info font-big">选择运行:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="openUpload" title="上传数据"><img src="<%=request.getContextPath()%>/images/publicIcon/icon-update.png"/></a></p>
				 <div id="dataDiv" class="dataForm"></div> 
				 <div class="pull-right pull_day" id="pageDiv"></div>
				  <div class="sel_data_btn">
					<input type="button" value="运行" id="runCheckBox1" class="btn btn-info"/>
					<input type="button" value="取消" id="resetCheckbox" class="btn"/>
					<span class="run_success" id="runAlert"></span>
				  </div>
             
			</div>
		</div>
	</div>
	<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/run.js?v=0.1"></script>
	<script type="text/javascript">
	var softwareId = window.parent.document.getElementById("softwareId").value;
	var softwareName = window.parent.document.getElementById("softwareName").value;
		$(document).ready(
			function() {
				$("input[value='Perform match']").click(function() {
					
					var seq = $("#sequence").val();
					if(seq == ""){
						$("#runAlertInput").removeClass("run_success");
				        $("#runAlertInput").addClass("run_error");
						$("#runAlertInput").html("请输入序列");
						return;
					}
					$("#runAlertInput").removeClass("run_error");
		            $("#runAlertInput").addClass("run_success");
		            $("#runAlertInput").html("您提交的数据已经开始运行，可进入报告模块查看运行状态...");
			            $.post("../../../data!saveTextToFileAndRun", {"sequence" : seq,"softwareId":softwareId,"appName":softwareName},function(data){
			                    if(data!=1){
			                    	$("#runAlertInput").removeClass("run_success");
			                    	$("#runAlertInput").addClass("run_error");
			                    	$("#runAlertInput").html("数据没有正确运行！");
			                    }
			            });
					$("form").submit();
					
		
				});
				$("input[value='Random Sequence']").click(function() {
					$.post("file/sample.fa",{},function(data){
						$("#sequence").val(data);
					});
				});
				$("input[value='Clear sequence']").click(function() {
					$("#sequence").val("");
				});

				(function() {
					// remove layerX and layerY
					var all = $.event.props, len = all.length, res = [];
					while (len--) {
						var el = all[len];
						if (el != 'layerX' && el != 'layerY')
							res.push(el);
					}
					$.event.props = res;
				}());
			});
		
		function viewReport(){
			var appId = $(".leftApp", window.parent.document);
			window.parent.setAPP(softwareId);
			appId[2].click();
		}
	</script>
</body>
</html>
