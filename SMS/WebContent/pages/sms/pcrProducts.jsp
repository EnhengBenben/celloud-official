<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="en">
<head>
<title>PCR Products</title>
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath() %>/resource/bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath() %>/resource/bootstrap/css/bootstrap-responsive.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/resource/bootstrap/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/sms_common.js"></script>
<script type="text/javascript" src="js/pcr_products.js"></script>
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
					var result = pcrProducts(document);
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
			$.get("data/PCR Products.fa","",function(data){
				$("textarea[name='sequence']").val(data);
			});
		});
		//重置
		$("#reset").click(function(){
			clearWarn();
			$("#show").css("display","none");
			$("textarea[name='sequence']").val("");
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
          <a class="brand" href="javascript:void(0)">PCR Products</a>
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
		    	<strong>Introduction：</strong> PCR Products accepts one or more DNA sequence templates and two primer sequences. The program searches for perfectly matching primer annealing sites that can generate a PCR product. Any resulting products are sorted by size, and they are given a title specifying their length, their position in the original sequence, and the primers that produced them. You can use linear or circular molecules as the template. Use PCR Products to determine the product sizes you can expect to see when you perform PCR in the lab.
		    	<br />
		    </div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				Paste the raw sequence or one or more FASTA sequences into the text area below. Input limit is 200000 characters.
				<p> 
					<form action="" name="main_form" id="main_form">
                    	<textarea name="sequence" id="sequence" class="span9" rows="6"></textarea><br />
                    	Enter the name of the first primer, followed by its sequence in the 5' to 3' direction. Degenerate sites can be represented using IUPAC DNA characters.<br />
                    	<input type="text" value="T7" class="input-xlarge"/> <input type="text" class="input-xlarge" value="aatacgactcactatag" /><br />
                    	Enter the name of the second primer, followed by its sequence in the 5' to 3' direction. Degenerate sites can be represented using IUPAC DNA characters.<br />
                    	<input type="text" value="T3" class="input-xlarge"/> <input type="text" class="input-xlarge" value="attaaccctcactaaag" /><br />
	                    <div class="alert alert-warning" id="warning"></div>
	                    <input type="button" value="submit" id="submit" class="btn"/>
					    <input type="button" value="clear" id="reset" class="btn"/>
					    <input type="button" value="random" id="random" class="btn"/><br /><br />
					    Treat sequences as <select class="span3">
	                        <option selected="selected" value="linear">
	                          linear
	                        </option>
	                        <option value="circular">
	                          circular
	                        </option>
	                      </select> molecules.
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