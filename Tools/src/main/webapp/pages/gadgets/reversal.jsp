<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="en">
<head>
<title>reversal</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resource/bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/pages/gadgets/gadgets.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/bootstrap/js/jquery-1.9.1.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("input[name='submit']").click(function() {
			var seq = $("#sequence").val();
			$.get("Gadgets!reversal", {
				"context" : seq
			}, function(result) {
				$("#result").val(result);
			});
		});
		$("input[name='reset']").click(function() {
			$("#sequence").val("");
			$("#result").val("");
		});
		$("input[name='random']").click(function() {
			$.post("test.fasta", {}, function(responseText) {
				$("#sequence").val(responseText);
			});
		});
	});
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
								序列反转
							</h4>
						</div>
						<div class="span10">
							<p>
								<textarea id="sequence" class="ta" cols="10" rows="10"></textarea>
							</p>
							<div>
								<p>
									<input type="button" name="random" value="随机数据" class="btn">
									<input type="button" name="submit" value="提交" class="btn">
									<input type="button" name="reset" value="重置" class="btn">
								</p>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="span2">
							<h4>
								处理结果
							</h4>
						</div>
						<div class="span10">
							<textarea id="result" class="ta" rows="10" cols="10" readonly="readonly"></textarea>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>