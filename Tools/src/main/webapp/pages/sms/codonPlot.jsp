<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="en">
<head>
<title>Codon Plot</title>
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath() %>/resource/bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath() %>/resource/bootstrap/css/bootstrap-responsive.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/resource/bootstrap/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/sms_common.js"></script>
<script type="text/javascript" src="js/codon_plot.js"></script>
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
				var reference = $("textarea[name='reference']").val();
				if((!reference)||($.trim(reference)=="")){
					$("#warning").html("请输入序列再提交");
					$("#warning").css("display","");
					return ;
				}else{
					clearWarn();
					try{
						var result = codonPlot(document);
						$("#result").html(result);
						$("#show").css("display","");
					} catch(e) {
						alert('The following error was encountered: ' + e);
					}
				}
			}
		});
		//生产随机数据
		$("#random").click(function(){
			clearWarn();
			$("#show").css("display","none");
			$.get("data/codonPlot1.fa","",function(data){
				$("textarea[name='sequence']").val(data);
			});
			$.get("data/codonPlot2.fa","",function(data){
				$("textarea[name='reference']").val(data);
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
          <a id="brand" class="brand" href="javascript:void(0)">Codon Plot</a>
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
		    	<strong>Introduction：</strong> Codon Plot accepts a DNA sequence and generates a graphical plot consisting of a horizontal bar for each codon. The length of the bar is proportional to the frequency of the codon in the codon frequency table you enter. Use Codon Plot to find portions of DNA sequence that may be poorly expressed, or to view a graphic representation of a codon usage table (by using a DNA sequence consisting of one of each codon type).
		    	<br />
		    </div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				Paste the raw or FASTA sequence into the text area below. Input limit is 50000 characters.
				<form action="" name="main_form" id="main_form">
					<textarea name="sequence" id="sequence" class="span9" rows="6"></textarea><br/>
                    <div class="alert alert-warning" id="warning"></div>
                    <input type="button" class="btn" value="submit" id="submit"/>
				    <input type="button" class="btn" value="clear" id="reset"/>
				    <input type="button" class="btn" value="random" id="random"/><br /><br />
				    Enter the codon table you wish to use for the genetic code (in GCG format). The default codon usage table was generated using all the E. coli coding sequences in GenBank. It was obtained from the Codon Usage Database.<br />
                   	<textarea name="reference" id="reference" class="span9" rows="6"></textarea>
                 </form>
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