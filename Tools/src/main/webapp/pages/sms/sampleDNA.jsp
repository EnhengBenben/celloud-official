<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="en">
<head>
<title>Sample DNA</title>
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath() %>/resource/bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath() %>/resource/bootstrap/css/bootstrap-responsive.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/resource/bootstrap/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/sms_common.js"></script>
<script type="text/javascript" src="js/sample_dna.js"></script>
<script type="text/javascript">
function clearWarn(){
	$("#warning").html("");
	$("#warning").css("display","none");
}
	$(document).ready(function(){
		clearWarn();
		$("#show").css("display","none");
		
		//提交
		$("#submit").click(function(){
			var sequence = $("textarea[name='sequence']").val();
			if((!sequence)||($.trim(sequence)=="")){
				$("#warning").html("请输入序列再提交");
				$("#warning").css("display","");
				return ;
			}else{
				clearWarn();
				try{
					var result = sampleDna(document);
					$("#result").html(result);
					$("#show").css("display","");
				} catch(e) {
					alert('The following error was encountered: ' + e);
				}
			}
		});
		//生产随机数据
		$("#random").click(function(){
			clearWarn();
			$("#show").css("display","none");
			$.get("data/Sample DNA.fa","",function(data){
				$("textarea[name='sequence']").val(data);
			});
		});
		//重置
		$("#reset").click(function(){
			clearWarn();
			$("#show").css("display","none");
			$("textarea[name='sequence']").val("");
			$("textarea[name='reference']").val("");
			$("#result").html("");
		});
	});
</script>
<style type="text/css">
.navbar-fixed-top{
	position:static;
}
.navbar {
	margin-bottom: 20px
}
</style>
</head>
<body>
	<div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container-fluid">
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <a class="brand" href="javascript:void(0)">Sample DNA</a>
          <div class="nav-collapse collapse">
            <ul class="nav">
            </ul>
          </div>
        </div>
      </div>
    </div>
    <div class="container-fluid">
    	<div class="row-fluid">
			<div class="alert alert-info">
		    	<strong>Introduction：</strong> Sample DNA randomly selects bases from the guide sequence until a sequence of the length you specify is constructed. Each selected base is replaced so that it can be selected again.
		    	<br />
		    </div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				Enter the length of the sequence you wish to construct. Maximum accepted value is 10000.
				<p> 
					<form action="" name="main_form" id="main_form">
	                    <input type="text" class="input-xlarge" value="100" /><br />
                    	Paste the raw or FASTA guide sequence into the text area below. Input limit is 10000 characters.<br />
                    	<textarea name="sequence" id="sequence" class="span9" rows="6"></textarea><br />
	                    <div class="alert alert-warning" id="warning"></div>
	                    <input type="button" value="submit" id="submit" class="btn"/>
					    <input type="button" value="reset" id="reset" class="btn"/>
					    <input type="button" value="random" id="random" class="btn"/><br /><br />
	                     Number of sequences to generate: <select class="span3">
                        <option selected="selected" value="1">
                          1
                        </option>

                        <option value="10">
                          10
                        </option>

                        <option value="50">
                          50
                        </option>

                        <option value="100">
                          100
                        </option>
                      </select>
                  </form>
				</p>
			</div>
		</div>
	</div>
	<div class="container-fluid">
		<div class="row-fluid" id="show">
			<div class="alert alert-info"><strong>Analysis Result：</strong></div>
			<div>
				<pre class="prettyprint linenums" id="result"></pre>
			</div>
		</div>
    </div>
</body>
</html>