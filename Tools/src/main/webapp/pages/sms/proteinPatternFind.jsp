<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="en">
<head>
<title>Protein Pattern Find</title>
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath() %>/resource/bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath() %>/resource/bootstrap/css/bootstrap-responsive.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/resource/bootstrap/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/sms_common.js"></script>
<script type="text/javascript" src="js/protein_pattern.js"></script>
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
					var result = proteinPattern(document);
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
			$.get("data/Protein Pattern Find.fa","",function(data){
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
          <a class="brand" href="javascript:void(0)">Protein Pattern Find</a>
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
		    	<strong>Introduction：</strong> Protein Pattern Find accepts one or more sequences along with a search pattern and returns the number and positions of sites that match the pattern. Use Protein Pattern Find to locate sequence regions that match a consensus sequence of interest.
		    	<br />
		    </div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				Paste a raw sequence or one or more FASTA sequences into the text area below. White space and digits are removed before the pattern matching is performed. Input limit is 500000 characters.
				<p> 
					<form action="" name="main_form" id="main_form">
                    	<textarea name="sequence" id="sequence" class="span9" rows="6"></textarea><br />
                    	Enter the search pattern to be used. The default pattern "S[^S]{0,5}S" searches for occurrences of two serine residues less than or equal to 5 residues apart.<br />
                    	<input type="text" class="input-xlarge" value="S[^S]{0,5}S" /><br />
	                    <div class="alert alert-warning" id="warning"></div>
	                    <input type="button" value="submit" id="submit" class="btn"/>
					    <input type="button" value="reset" id="reset" class="btn"/>
					    <input type="button" value="random" id="random" class="btn"/><br /><br />
					    Add <select class="span3">
                        <option selected="selected" value="1">
                          1
                        </option>

                        <option value="2">
                          2
                        </option>

                        <option value="3">
                          3
                        </option>

                        <option value="4">
                          4
                        </option>

                        <option value="5">
                          5
                        </option>
                      </select> copies of <select class="span3">
                        <option selected="selected" value="">
                          Nothing
                        </option>

                        <option value="DTYRYI">
                          AU1 (DTYRYI)
                        </option>

                        <option value="TDFYLK">
                          AU5 (TDFYLK)
                        </option>

                        <option value="QYPALT">
                          BTag (QYPALT)
                        </option>

                        <option value="EQKLISEEDL">
                          c-myc (EQKLISEEDL)
                        </option>

                        <option value="DYKDDDDK">
                          FLAG (DYKDDDDK)
                        </option>

                        <option value="MSKGEELFTGVVPILVELDGDVNGHKFSVSGEGEGDATYGKLTLKFICTTGKLPVPWPTLVTTFSYGVQCFSRYPDHMKRHDFFKSAMPEGYVQERTIFFKDDGNYKTRAEVKFEGDTLVNRIELKGIDFKEDGNILGHKLEYNYNSHNVYIMADKQKNGIKVNFKIRHNIEDGSVQLADHYQQNTPIGDGPVLLPDNHYLSTQSALSKDPNEKRDHMVLLEFVTAAGITHGMDELYK">
                          GFP (MSKGEELFTG...)
                        </option>

                        <option value="EYMPME">
                          Glu-Glu (EYMPME)
                        </option>

                        <option value="EFMPME">
                          Glu-Glu (EFMPME)
                        </option>

                        <option value="MSPILGYWKIKGLVQPTRLLLEYLEEKYEEHLYERDEGDKWRNKKFELGLEFPNLPYYIDGDVKLTQSMAIIRYIADKHNMLGGCPKERAEISMLEGAVLDIRYGVSRIAYSKDFETLKVDFLSKLPEMLKMFEDRLCHKTYLNGDHVTHPDFMLYDALDVVLYMDPMCLDAFPKLVCFKKRIEAIPQIDKYLKSSKYIAWPLQGWQATFGGGDHPPKSDLVPRGSPGIHRD">
                          GST (MSPILGYWKI...)
                        </option>

                        <option value="YPYDVPDYA">
                          HA (YPYDVPDYA)
                        </option>

                        <option value="HHHHHH">
                          His6 (HHHHHH)
                        </option>

                        <option value="QPELAPEDPED">
                          HSV (QPELAPEDPE...)
                        </option>

                        <option value="HTTPHH">
                          HTTPHH (HTTPHH)
                        </option>

                        <option value="RYIRS">
                          IRS (RYIRS)
                        </option>

                        <option value="PPEPET">
                          KT3 (PPEPET)
                        </option>

                        <option value="EDQVDPRLIDGK">
                          Protein C (EDQVDPRLID...)
                        </option>

                        <option value="KETAAAKFERQHMDS">
                          S-Tag (KETAAAKFER...)
                        </option>

                        <option value="MDEKTTGWRGGHVVEGLAGELEQLRARLEHHPQGQREP">
                          SBP (MDEKTTGWRG...)
                        </option>

                        <option value="MASMTGGQQMG">
                          T7 (MASMTGGQQM...)
                        </option>

                        <option value="GKPIPNPLLGLDST">
                          V5 (GKPIPNPLLG...)
                        </option>

                        <option value="MNRLGK">
                          VSV-G (MNRLGK)
                        </option>
                      </select> to the above sequence.
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