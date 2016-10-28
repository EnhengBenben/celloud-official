<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="en">
<head>
<title>Color Align Properties</title>
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath() %>/resource/bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath() %>/resource/bootstrap/css/bootstrap-responsive.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/resource/bootstrap/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/sms_common.js"></script>
<script type="text/javascript" src="js/color_align_prop.js"></script>
<style type="text/css">
span.g, span.a, span.v, span.l, span.i {
color: #000000;
background-color: #C0C0C0;
}
span.f, span.y, span.w {
color: #000000;
background-color: #FF6600;
}
span.c, span.m {
color: #000000;
background-color: #FFFF00;
}
span.s, span.t {
color: #000000;
background-color: #66FF00;
}
span.k, span.r, span.h {
color: #000000;
background-color: #FF0000;
}
span.d, span.e {
color: #000000;
background-color: #0066FF;
}
span.n, span.q {
color: #000000;
background-color: #996633;
}
span.p {
color: #000000;
background-color: #FF99FF;
}
.navbar-fixed-top{
	position:static;
}
.navbar {
	margin-bottom: 20px
}
</style>
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
					var result = colorAlignProp(document);
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
			$.get("data/Color Align Properties.fa","",function(data){
				$("textarea[name='sequence']").val(data);
			});
		});
		//重置
		$("#reset").click(function(){
			clearWarn();
			$("#show").css("display","none");
			$("textarea[name='sequence']").val("");
			$("textarea[name='sequence2']").val("");
			$("#result").html("");
		});
	});
</script>
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
          <a class="brand" href="javascript:void(0)">Color Align Properties</a>
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
		    	<strong>Introduction：</strong> Color Align Properties accepts a group of aligned sequences (in FASTA or GDE format) and colors the alignment. The program examines each residue and compares it to the other residues in the same column. Residues that are identical or similar among the sequences are given a colored background. The color is chosen according to the biochemical properties of the residue. You can specify the percentage of residues that must be identical and similar for the coloring to be applied. Use Color Align Properties to highlight protein regions with conserved biochemical properties.
		    	<br />
		    </div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				Paste the aligned sequences in FASTA or GDE format into the text area below. Input limit is 20000 characters.
				<p>
					<form action="" name="main_form" id="main_form">
                    	<textarea name="sequence" id="sequence" class="span9" rows="6"></textarea><br />
	                    <div class="alert alert-warning" id="warning"></div>
	                    <input type="button" value="submit" id="submit" class="btn"/>
					    <input type="button" value="clear" id="reset" class="btn"/>
					    <input type="button" value="random" id="random" class="btn"/><br /><br />
					     Show <select class="span3">
                        <option value="30">
                          30
                        </option>
                        <option value="40">
                          40
                        </option>
                        <option value="50">
                          50
                        </option>
                        <option value="60">
                          60
                        </option>
                        <option value="70">
                          70
                        </option>
                        <option selected="selected" value="80">
                          80
                        </option>
                        <option value="90">
                          90
                        </option>
                        <option value="100">
                          100
                        </option>
                        <option value="200">
                          200
                        </option>
                        <option value="300">
                          300
                        </option>
                      </select> residues per line.<br/>
                      Percentage of sequences that must agree for identity or similarity coloring to be added: 
                      <select class="span3">
                        <option value="10">
                          10
                        </option>
                        <option value="20">
                          20
                        </option>
                        <option value="30">
                          30
                        </option>
                        <option value="40">
                          40
                        </option>
                        <option value="50">
                          50
                        </option>
                        <option value="60">
                          60
                        </option>
                        <option value="70">
                          70
                        </option>
                        <option value="80">
                          80
                        </option>
                        <option value="90">
                          90
                        </option>
                        <option selected="selected" value="100">
                          100
                        </option>
                      </select><br/>
                      Used colored <select class="span3">
                        <option selected="selected" value="background">
                          backgrounds
                        </option>
                        <option value="text">
                          text
                        </option>
                      </select> .<br/>
                      Enter the starting positions of the sequences separated by commas (to alter residue numbering). An example entry is: <b>0, 200, 0, -1</b>. If no numbers are given, the default starting position of 0 is used for each sequence.<br />
                    <textarea name="sequence2" id="sequence2" class="span9" rows="6"></textarea><br />
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