<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="en">
<head>
<title>Codon Usage</title>
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath() %>/resource/bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath() %>/resource/bootstrap/css/bootstrap-responsive.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/resource/bootstrap/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/sms_common.js"></script>
<script type="text/javascript" src="js/sms_genetic_codes.js"></script>
<script type="text/javascript" src="js/codon_usage.js"></script>
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
				$("#show").css("display","none");
				try{
					var result = codonUsage(document);
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
			$.get("data/codonUsage.fa","",function(data){
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
          <a class="brand" href="javascript:void(0)">Codon Usage</a>
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
		    	<strong>Introduction：</strong>Codon Usage accepts one or more DNA sequences and returns the number and frequency of each codon type. Since the program also compares the frequencies of codons that code for the same amino acid (synonymous codons), you can use it to assess whether a sequence shows a preference for particular synonymous codons.
		    	<br />
		    </div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				Paste the raw sequence or one or more FASTA sequences into the text area. Input limit is 500000 characters.
				<p>
					<form action="" name="main_form" id="main_form">
                    	<textarea name="sequence" id="sequence" class="span8" rows="8"></textarea><br />
	                    <div class="alert alert-warning" id="warning"></div>
	                    <input type="button" value="submit" id="submit" class="btn"/>
					    <input type="button" value="clear" id="reset" class="btn"/>
					    <input type="button" value="random" id="random" class="btn"/><br /><br />
	                    Use the <select class="span3">
	                        <option selected="selected" value="transl_table=1">
	                          standard (1)
	                        </option>
	                        <option value="transl_table=2">
	                          vertebrate mitochondrial (2)
	                        </option>
	                        <option value="transl_table=3">
	                          yeast mitochondrial (3)
	                        </option>
	                        <option value="transl_table=4">
	                          mold mitochondrial (4)
	                        </option>
	                        <option value="transl_table=5">
	                          invertebrate mitochondrial (5)
	                        </option>
	                        <option value="transl_table=6">
	                          ciliate nuclear (6)
	                        </option>
	                        <option value="transl_table=9">
	                          echinoderm mitochondrial (9)
	                        </option>
	                        <option value="transl_table=10">
	                          euplotid nuclear (10)
	                        </option>
	                        <option value="transl_table=11">
	                          bacterial (11)
	                        </option>
	                        <option value="transl_table=12">
	                          alternative yeast nuclear (12)
	                        </option>
	                        <option value="transl_table=13">
	                          ascidian mitochondrial (13)
	                        </option>
	                        <option value="transl_table=14">
	                          flatworm mitochondrial (14)
	                        </option>
	                        <option value="transl_table=15">
	                          Blepharisma macronuclear (15)
	                        </option>
	                        <option value="transl_table=16">
	                          chlorophycean mitochondrial (16)
	                        </option>
	                        <option value="transl_table=21">
	                          trematode mitochondrial (21)
	                        </option>
	                        <option value="transl_table=22">
	                          Scenedesmus obliquus mitochondrial (22)
	                        </option>
	                        <option value="transl_table=23">
	                          Thraustochytrium mitochondrial (23)
	                        </option>
	                      </select>genetic code.
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