<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="en">
<head>
<title>Pairwise Align Codons</title>
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath() %>/resource/bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath() %>/resource/bootstrap/css/bootstrap-responsive.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/resource/bootstrap/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/sms_common.js"></script>
<script type="text/javascript" src="js/pairwise_align_codons.js"></script>
<script type="text/javascript" src="js/align_pair_codons_linear.js"></script>
<script type="text/javascript" src="js/align_pair_codons_quad.js"></script>
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
						var result = pairwiseAlignCodons(document);
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
			
			$.get("data/pairwiseAlignCodons.fa","",function(data){
				$("textarea[name='sequence']").val(data);
			});
			$.get("data/pairwiseAlignCodons1.fa","",function(data){
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
          <a class="brand" href="javascript:void(0)">Pairwise Align Codons</a>
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
		    	<strong>Introduction：</strong> Pairwise Align Codons accepts two coding sequences and determines the optimal global alignment. The scoring matrix used to calculate the alignment is described in Schneider et al. (2005). Use Pairwise Align Codons to look for conserved coding sequence regions. Only the bases A,C,G,T and U are used in the alignment.
		    	<br />
		    </div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				Paste sequence one (in raw sequence or FASTA format) into the text area below. Input limit is 6000 characters.
				<p> 
					<form action="" name="main_form" id="main_form">
                    	<textarea name="sequence" id="sequence" class="span9" rows="6"></textarea><br />
                    	Paste sequence two (in raw sequence or FASTA format) into the text area below. Input limit is 6000 characters.<br />
                    	<textarea name="reference" class="span9" rows="6"></textarea><br />
	                    <div class="alert alert-warning" id="warning"></div>
	                    <input type="button" value="submit" id="submit" class="btn"/>
					    <input type="button" value="reset" id="reset" class="btn"/>
					    <input type="button" value="random" id="random" class="btn"/><br /><br />
					    Use the following parameters to specify how alignments are scored.<br />
                    	Value for gaps preceding a sequence <select class="span3">
                        <option value="-6">
                          6
                        </option>

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

                        <option selected="selected" value="0">
                          0
                        </option>

                        <option value="1">
                          -1
                        </option>

                        <option value="2">
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

                        <option value="6">
                          -6
                        </option>
                      </select><br/>
                      Value for internal gaps <select class="span3">
                        <option value="-6">
                          6
                        </option>

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

                        <option value="6">
                          -6
                        </option>
                      </select><br/>
                      Value for gaps following a sequence <select class="span3">
                        <option value="-6">
                          6
                        </option>

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

                        <option selected="selected" value="0">
                          0
                        </option>

                        <option value="1">
                          -1
                        </option>

                        <option value="2">
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

                        <option value="6">
                          -6
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