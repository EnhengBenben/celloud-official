<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="en">
<head>
<title>seqsub</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resource/bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/pages/gadgets/gadgets.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/bootstrap/js/jquery-1.9.1.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("input[name='random']").click(function() {
			$.post("test.fasta", {}, function(responseText) {
				$("#sequence").val(responseText);
				$("#start").val("30");
				$("#end").val("300");
			});
		});
		$("input[name='reset']").click(function() {
			$("#sequence").val("");
			$("#start").val("");
			$("#end").val("");
			$("#result").val("");
		});
	});
	function submit(){
		var seq = $("#sequence").val();
		var start = $("#start").val();
		var end = $("#end").val();
		$.get("<%=request.getContextPath()%>/RunServlet", {
			"context" : seq,
			"start" : start,
			"end" : end,
			"type" : 'seqsub'
		}, function(result) {
			$("#result").val(result);
		});
	}
</script>
</head>
<body>
	<div class="container">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span1"></div>
				<div class="span10 well">
					<div class="row">
						<div class="span2">
							<h4>
								序列截取
							</h4>
						</div>
						<div class="span10">
							<p>
								<textarea id="sequence" class="textarea" cols="10" rows="8"></textarea>
							</p>
							<div>
								<p>
									要截取序列的起始和结束位置： From <input id="start" class="inp" type="text" />
									To <input id="end" class="inp" type="text" />
								</p>
								<p>
									<input type="button" name="random" value="随机数据" class="btn">
									<input type="button" name="submit" onclick="submit()" value="提交" class="btn">
									<input type="button" name="reset" value="重置" class="btn">
								</p>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="span2">
							<h4>
								截取结果
							</h4>
						</div>
						<div class="span10">
							<textarea id="result" class="textarea" rows="8" cols="10" readonly="readonly"></textarea>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>