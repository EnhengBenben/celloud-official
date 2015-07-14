<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="en">
<head>
<title>Fuzzy Search DNA</title>
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath() %>/resource/bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath() %>/resource/bootstrap/css/bootstrap-responsive.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/resource/bootstrap/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/sms_common.js"></script>
<script type="text/javascript" src="js/fuzzy_search_dna.js"></script>
<script type="text/javascript" src="js/fuzzy_search.js"></script>
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
					var result = fuzzySearchDna(document);
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
			$.get("data/fuzzySearchDNA.fa","",function(data){
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
          <a class="brand" href="javascript:void(0)">Fuzzy Search DNA</a>
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
		    	<strong>Introduction：</strong> Fuzzy Search DNA accepts a DNA sequence along with a query sequence and returns sites that are identical or similar to the query. You can use this program, for example, to find sequences that can be easily mutated into a useful restriction site.
		    	<br />
		    </div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				Enter the sequence to search (in raw sequence or FASTA format) into the text area below. Input limit is 2000 characters.
				<p> 
					<form action="" name="main_form" id="main_form">
                    	<textarea name="sequence" id="sequence" class="span9" rows="6"></textarea><br /><br />
	                    Enter the query sequence into the text area below. Input limit is 30 characters.<br />
                    	<input type="text" class="input-xlarge" value="cccggg" /><br /><br />
	                    <div class="alert alert-warning" id="warning"></div>
	                    <input type="button" value="submit" id="submit" class="btn"/>
					    <input type="button" value="clear" id="reset" class="btn"/>
					    <input type="button" value="random" id="random" class="btn"/><br /><br />
					     Use the following parameters to specify how hits are scored, and to control which hits are returned.<br/>
                    Match value <select class="span3">
                        <option value="5">
                          5
                        </option>
                        <option value="4">
                          4
                        </option>
                        <option value="3">
                          3
                        </option>
                        <option value="2">
                          2
                        </option>
                        <option selected="selected" value="1">
                          1
                        </option>
                        <option value="0">
                          0
                        </option>
                        <option value="-1">
                          -1
                        </option>
                        <option value="-2">
                          -2
                        </option>
                        <option value="-3">
                          -3
                        </option>
                        <option value="-4">
                          -4
                        </option>
                        <option value="-5">
                          -5
                        </option>
                      </select><br/>
                      Mismatch value <select class="span3">
                        <option value="5">
                          5
                        </option>
                        <option value="4">
                          4
                        </option>
                        <option value="3">
                          3
                        </option>
                        <option value="2">
                          2
                        </option>
                        <option value="1">
                          1
                        </option>
                        <option selected="selected" value="0">
                          0
                        </option>
                        <option value="-1">
                          -1
                        </option>
                        <option value="-2">
                          -2
                        </option>
                        <option value="-3">
                          -3
                        </option>
                        <option value="-4">
                          -4
                        </option>
                        <option value="-5">
                          -5
                        </option>
                      </select><br/>
                      Gap value <select class="span3">
                        <option value="-5">
                          5
                        </option>
                        <option value="-4">
                          4
                        </option>
                        <option value="-3">
                          3
                        </option>
                        <option value="-2">
                          2
                        </option>
                        <option value="-1">
                          1
                        </option>
                        <option value="0">
                          0
                        </option>
                        <option value="1">
                          -1
                        </option>
                        <option selected="selected" value="2">
                          -2
                        </option>
                        <option value="3">
                          -3
                        </option>
                        <option value="4">
                          -4
                        </option>
                        <option value="5">
                          -5
                        </option>
                      </select><br/>
                      Report the the top <select class="span3">
                        <option value="40">
                          40
                        </option>
                        <option value="30">
                          30
                        </option>
                        <option value="20">
                          20
                        </option>
                        <option value="10">
                          10
                        </option>
                        <option value="9">
                          9
                        </option>
                        <option value="8">
                          8
                        </option>
                        <option value="7">
                          7
                        </option>
                        <option value="6">
                          6
                        </option>
                        <option selected="selected" value="5">
                          5
                        </option>
                        <option value="4">
                          4
                        </option>
                        <option value="3">
                          3
                        </option>
                        <option value="2">
                          2
                        </option>
                        <option value="1">
                          1
                        </option>
                      </select> hits.
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