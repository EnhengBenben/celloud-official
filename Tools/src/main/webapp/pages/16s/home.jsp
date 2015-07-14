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
		<link href="<%=request.getContextPath()%>/pages/16s/css/main.css" type="text/css" rel="stylesheet">
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/pages/16s/js/jquery1.4.js">
		</script>
		<script type="text/javascript">
			$(document).ready(
				function() {
					$("input[value='Perform match']").click(function() {
						$('body').animate( {
							opacity : '0.5'
						}, 700);
						$("form").submit();
					});
					$("input[value='Random Sequence']").click(function() {
						$.post("file/sample.fa",{},function(data){
							$("#intxt").val(data);
						});
					});
					$("input[value='Clear sequence']").click(function() {
						$("#intxt").val("");
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
						<p>
							Click&lt; Perform match &gt;to identify your sequence. If you
							have no data readily available, push the button.
						</p>
						<p>
							<a href="<%=request.getContextPath()%>/pages/16s/file/16s.xlsx" target="_blank" style="color:blue;cursor:pointer">Download</a> the database of 16S rDNA
						</p>
					</div>
				</div>
				<form action="commonMatch.action" method="post">
					<div class="box">
						<div class="box-tit">
							Enter sequence below in FASTA format：
						</div>
						<div>
							<textarea id="intxt" name="sequence" cols="750" rows="200"></textarea>
						</div>
					</div>
					<div id="btnbox" class="c">
						<input type="button" value="Perform match" class="btn1" />
						<input type="button" value="Random Sequence" class="btn1" />
						<input type="button" value="Clear sequence" class="btn1" />
					</div>
				</form>
			</div>
			<div id="footer">
				
			</div>
		</div>
	</body>
</html>
