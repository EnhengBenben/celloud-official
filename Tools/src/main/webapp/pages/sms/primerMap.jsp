<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="en">
<head>
<title>Primer Map</title> 
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath() %>/resource/bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath() %>/resource/bootstrap/css/bootstrap-responsive.css">
<style type="text/css">
body.main {font-size: medium; font-family: arial, sans-serif; color: #000000; background-color: #FFFFFF}
div.pre {font-size: medium; color: #000000; font-family: courier, sans-serif; white-space: pre}
div.title {font-size: x-large; color: #000000; text-align: left; background-color: #FFFFFF}
div.info {font-weight: bold}
span.none, td.none {color: #000000; background-color: #FFFFFF}
span.one, td.one {color: #000000; background-color: #66FF00}
span.two, td.two {color: #000000; background-color: #FFFF66}
span.three, td.three {color: #000000; background-color: #FFFFFF}
span.forward_primer, td.forward_primer {color: #000000; background-color: #FF66FF}
span.reverse_primer, td.reverse_primer {color: #000000; background-color: #FF9933}
span.current_sequence {color: #000000; background-color: #FFFFFF}
span.mutated_sequence {color: #990066; background-color: #FFFFFF}
td.many {color: #000000}
td.title {font-weight: bold; color: #000000; background-color: #FFFFFF}
.navbar-fixed-top{
	position:static;
}
.navbar {
	margin-bottom: 20px
}
</style>
<script type="text/javascript" src="<%=request.getContextPath() %>/resource/bootstrap/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/sms_common.js"></script>
<script type="text/javascript" src="js/sms_restriction_sites.js"></script>
<script type="text/javascript" src="js/sms_genetic_codes.js"></script>
<script type="text/javascript" src="js/primer_map.js"></script>
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
					var result = primerMap(document);
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
			$.get("data/Primer Map.fa","",function(data){
				$("textarea[name='sequence']").val(data);
			});
			$.get("data/Primer Map1.fa","",function(data){
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
          <a class="brand" href="javascript:void(0)">Primer Map</a>
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
		    	<strong>Introduction：</strong> Primer Map accepts a DNA sequence and returns a textual map showing the annealing positions of PCR primers. Restriction endonuclease cut sites, and the protein translations of the DNA sequence can also be shown. Use this program to produce a useful reference figure, particularly when you have designed a large number of primers for a particular template. Primer Map supports the entire IUPAC alphabet and several genetic codes.
		    	<br />
		    </div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				Paste a raw sequence or one or more FASTA sequences into the text area below. Input limit is 200000 characters.
				<p> 
					<form action="" name="main_form" id="main_form">
                    	<textarea name="sequence" id="sequence" class="span9" rows="6"></textarea><br />
                    	Enter the primers you want to display. An example primer entry is: <b>(T7) aatacgactcactatag</b>. The round brackets surround the name of the primer, and the sequence is written in the 5' to 3' direction. Multiple entries are separated by commas.<br />
                    	<textarea name="reference" class="span9" rows="6"></textarea><br />
	                    <div class="alert alert-warning" id="warning"></div>
	                    <input type="button" value="submit" id="submit" class="btn"/>
					    <input type="button" value="clear" id="reset" class="btn"/>
					    <input type="button" value="random" id="random" class="btn"/><br /><br />
					    Show <select class="span3">
                        <option value="105">
                          105
                        </option>

                        <option value="90">
                          90
                        </option>

                        <option value="75">
                          75
                        </option>

                        <option selected="selected" value="60">
                          60
                        </option>

                        <option value="45">
                          45
                        </option>

                        <option value="30">
                          30
                        </option>
                      </select> bases per line.<br/>
                      Show the translation for reading frame <select class="span3">
                        <option value="1">
                          1
                        </option>

                        <option value="2">
                          2
                        </option>

                        <option value="3">
                          3
                        </option>

                        <option selected="selected" value="all_three">
                          1, 2, and 3
                        </option>

                        <option value="uppercase">
                          uppercase text
                        </option>

                        <option value="none">
                          none
                        </option>
                      </select><br/>
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
                      </select> genetic code.<br/>
                      Restriction sites should <select class="span3">
                        <option selected="selected" value="shown">
                          be shown
                        </option>

                        <option value="not_shown">
                          not be shown
                        </option>
                      </select><br/>
                      Treat sequences as <select class="span3">
                        <option selected="selected" value="linear">
                          linear
                        </option>

                        <option value="circular">
                          circular
                        </option>
                      </select> molecules.<br/>
                      Generate <select class="span3">
                        <option selected="selected" value="color">
                          color
                        </option>

                        <option value="bw">
                          black and white
                        </option>
                      </select> output.
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