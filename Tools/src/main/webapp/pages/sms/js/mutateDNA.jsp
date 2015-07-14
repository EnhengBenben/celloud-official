<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="en">
<head>
<title>Mutate DNA</title>
<link rel="stylesheet" type="text/css"	href="../bootstrap/css/bootstrap.min.css" media="screen">
<link rel="stylesheet" type="text/css"	href="../bootstrap/css/style.css" media="screen">
<script type="text/javascript" src="../bootstrap/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="js/sms_common.js"></script>
<script type="text/javascript" src="js/mutate_dna.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$("textarea[name='sequence']").next().css("display","none");
		
		//提交
		$("#submit").click(function(){
			var sequence = $("textarea[name='sequence']").val();
			if((!sequence)||($.trim(sequence)=="")){
				$("textarea[name='sequence']").next().html("请输入序列再提交");
				$("textarea[name='sequence']").next().css("display","");
				return ;
			}else{
				$("textarea[name='sequence']").next().html("");
				$("textarea[name='sequence']").next().css("display","none");
				try{
					var result = mutateDna(document);
					$("#result").html(result);
				} catch(e) {
					alert('The following error was encountered: ' + e);
				}
			}
		});
		//生产随机数据
		$("#random").click(function(){
			$.get("data/Mutate DNA.fa","",function(data){
				$("textarea[name='sequence']").val(data);
			});
		});
		//重置
		$("#reset").click(function(){
			$("textarea[name='sequence']").val("");
			$("textarea[name='sequence']").next().css("display","none");
			$("#result").html("");
		});
	});
</script>
</head>
<body>
	<div class="container0">
		<div class="header">
	   		<a href="#">Mutate DNA</a>    	
	    </div>
		<div class="banner">
	    	简介:Mutate DNA introduces base changes into a DNA sequence. You can select the number of mutations to introduce, and whether or not to preserve the first and last three bases in the sequence, to reflect selection acting to maintain start and stop codons. The position of each mutation is chosen randomly, and multiple mutations can occur at a single site. Mutated sequences can be used to evaluate the significance of sequence analysis results.
	    </div>
		<div class="container">
	    	<div class="column-group">
	        	<div class="column-label">
	                <div class="column-sublabel" >
	                    <div class="column-label-header">请输入序列</div>
	                    <div class="column-label-content" style="height:110px;">注意：<br>Paste the raw sequence or one or more FASTA sequences into the text area below. Input limit is 100000 characters.</div>
	                </div>
	            </div>
	        	<div>
					<form action="" name="main_form" id="main_form">
                    	<textarea name="sequence" id="sequence" class="span8" rows="8"></textarea><br />
	                    <div class="warning">
	                    </div>
	                    <input type="button" value="submit" id="submit"/>
					    <input type="button" value="reset" id="reset"/>
					    <input type="button" value="random" id="random"/><br />
					  <ul>
                      <li>Introduce <input type="text" value="10" />mutations into each sequence.</li>

                      <li>The first three bases in each sequence <select>
                        <option value="0">
                          can
                        </option>

                        <option selected="selected" value="3">
                          cannot
                        </option>
                      </select> be mutated.</li>

                      <li>The last three bases in each sequence <select>
                        <option selected="selected" value="0">
                          can
                        </option>

                        <option value="3">
                          cannot
                        </option>
                      </select> be mutated.</li>
                    </ul>
                  </form>
	        	</div>
	        </div>
	        <div class="text-center tip">以下显示结果……</div>
	    	<div class="result-group">
	        	<div class="result-label">
	            	<div class="result-label-header text-right">分析结果：</div>
	            </div>
	        	<div class="result" id="result">
	        		
	        	</div>
	        </div>
		</div>
		<div class="footer">
    		Powered By CelLoud
    	</div>
    </div>
</body>
</html>