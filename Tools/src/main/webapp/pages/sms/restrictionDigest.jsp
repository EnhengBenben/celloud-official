<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="en">
<head>
<title>Restriction Digest</title>
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath() %>/resource/bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath() %>/resource/bootstrap/css/bootstrap-responsive.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/resource/bootstrap/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/sms_common.js"></script>
<script type="text/javascript" src="js/rest_digest.js"></script>
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
					var result = restDigest(document);
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
			$.get("data/Restriction Digest.fa","",function(data){
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
          <a class="brand" href="javascript:void(0)">Restriction Digest</a>
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
		    	<strong>Introduction：</strong> Restriction Digest cleaves a DNA sequence in a virtual restriction digest, with one, two, or three restriction enzymes. The resulting fragments are sorted by size, and they are given a title specifying their length, their position in the original sequence, and the enzyme sites that produced them. You can digest linear or circular molecules, and even a mixture of molecules (by entering more than one sequence in FASTA format). Use Restriction Digest to determine the fragment sizes you will see when you perform a digest in the lab.
		    	<br />
		    </div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				Paste the raw sequence or one or more FASTA sequences into the text area below. Input limit is 100000 characters.
				<p> 
					<form action="" name="main_form" id="main_form">
                    	<textarea name="sequence" id="sequence" class="span9" rows="6"></textarea><br />
	                    <div class="alert alert-warning" id="warning"></div>
	                    <input type="button" value="submit" id="submit" class="btn"/>
					    <input type="button" value="reset" id="reset" class="btn"/>
					    <input type="button" value="random" id="random" class="btn"/><br /><br />
					   Treat sequences as <select class="span3">
                        <option selected="selected" value="linear">
                          linear
                        </option>

                        <option value="circular">
                          circular
                        </option>
                      </select> molecules.<br/>
                      Digest with <select class="span3">
                        <option value="/aggcct/ (AatI agg|cct)3">
                          AatI agg|cct
                        </option>

                        <option value="/gacgtc/ (AatII gacgt|c)1">
                          AatII gacgt|c
                        </option>

                        <option value="/tgcgca/ (Acc16I tgc|gca)3">
                          Acc16I tgc|gca
                        </option>

                        <option value="/cgcg/ (AccII cg|cg)2">
                          AccII cg|cg
                        </option>

                        <option value="/tccgga/ (AccIII t|ccgga)5">
                          AccIII t|ccgga
                        </option>

                        <option value="/aacgtt/ (AclI aa|cgtt)4">
                          AclI aa|cgtt
                        </option>

                        <option value="/cacgtg/ (AcvI cac|gtg)3">
                          AcvI cac|gtg
                        </option>

                        <option value="/gtac/ (AfaI gt|ac)2">
                          AfaI gt|ac
                        </option>

                        <option value="/agcgct/ (AfeI agc|gct)3">
                          AfeI agc|gct
                        </option>

                        <option value="/cttaag/ (AflII c|ttaag)5">
                          AflII c|ttaag
                        </option>

                        <option value="/accggt/ (AgeI a|ccggt)5">
                          AgeI a|ccggt
                        </option>

                        <option value="/actagt/ (AhlI a|ctagt)5">
                          AhlI a|ctagt
                        </option>

                        <option value="/gtgcac/ (Alw441 g|tgcac)5">
                          Alw441 g|tgcac
                        </option>

                        <option selected="selected" value="/agct/ (AluI ag|ct)2">
                          AluI ag|ct
                        </option>

                        <option value="/agcgct/ (Aor51HI agc|gct)3">
                          Aor51HI agc|gct
                        </option>

                        <option value="/gggccc/ (ApaI gggcc|c)1">
                          ApaI gggcc|c
                        </option>

                        <option value="/gtgcac/ (ApaLI g|tgcac)5">
                          ApaLI g|tgcac
                        </option>

                        <option value="/ggcgcgcc/ (AscI gg|cgcgcc)6">
                          AscI gg|cgcgcc
                        </option>

                        <option value="/attaat/ (AseI at|taat)4">
                          AseI at|taat
                        </option>

                        <option value="/ggtacc/ (Asp718I g|gtacc)5">
                          Asp718I g|gtacc
                        </option>

                        <option value="/ttcgaa/ (AsuII tt|cgaa)4">
                          AsuII tt|cgaa
                        </option>

                        <option value="/c[cty]cg[agr]g/ (AvaI c|ycgrg)5">
                          AvaI c|ycgrg
                        </option>

                        <option value="/tgcgca/ (AviII tgc|gca)3">
                          AviII tgc|gca
                        </option>

                        <option value="/cctagg/ (AvrII c|ctagg)5">
                          AvrII c|ctagg
                        </option>

                        <option value="/tggcca/ (BalI tgg|cca)3">
                          BalI tgg|cca
                        </option>

                        <option value="/ggatcc/ (BamHI g|gatcc)5">
                          BamHI g|gatcc
                        </option>

                        <option value="/atcgat/ (BanIII at|cgat)4">
                          BanIII at|cgat
                        </option>

                        <option value="/ggcgcc/ (BbeI ggcgc|c)1">
                          BbeI ggcgc|c
                        </option>

                        <option value="/cacgtg/ (BbrPI cac|gtg)3">
                          BbrPI cac|gtg
                        </option>

                        <option value="/gcatgc/ (BbuI gcatg|c)1">
                          BbuI gcatg|c
                        </option>

                        <option value="/actagt/ (BcuI a|ctagt)5">
                          BcuI a|ctagt
                        </option>

                        <option value="/tgatca/ (BclI t|gatca)5">
                          BclI t|gatca
                        </option>

                        <option value="/ctag/ (BfaI c|tag)3">
                          BfaI c|tag
                        </option>

                        <option value="/cttaag/ (BfrI c|ttaag)5">
                          BfrI c|ttaag
                        </option>

                        <option value="/atgcat/ (BfrBI atg|cat)3">
                          BfrBI atg|cat
                        </option>

                        <option value="/agatct/ (BglII a|gatct)5">
                          BglII a|gatct
                        </option>

                        <option value="/cctagg/ (BlnI c|ctagg)5">
                          BlnI c|ctagg
                        </option>

                        <option value="/atcgat/ (BseCI at|cgat)4">
                          BseCI at|cgat
                        </option>

                        <option value="/gcgcgc/ (BsePI g|cgcgc)5">
                          BsePI g|cgcgc
                        </option>

                        <option value="/cggccg/ (BseX3I c|ggccg)5">
                          BseX3I c|ggccg
                        </option>

                        <option value="/accggt/ (BshTI a|ccggt)5">
                          BshTI a|ccggt
                        </option>

                        <option value="/tgtaca/ (Bsp1407I t|gtaca)5">
                          Bsp1407I t|gtaca
                        </option>

                        <option value="/ccatgg/ (Bsp19I c|catgg)5">
                          Bsp19I c|catgg
                        </option>

                        <option value="/atcgat/ (BspDI at|cgat)4">
                          BspDI at|cgat
                        </option>

                        <option value="/tccgga/ (BspEI t|ccgga)5">
                          BspEI t|ccgga
                        </option>

                        <option value="/tgtaca/ (BsrGI t|gtaca)5">
                          BsrGI t|gtaca
                        </option>

                        <option value="/gcgcgc/ (BssHII g|cgcgc)5">
                          BssHII g|cgcgc
                        </option>

                        <option value="/cgcg/ (BstUI cg|cg)2">
                          BstUI cg|cg
                        </option>

                        <option value="/atcgat/ (ClaI at|cgat)4">
                          ClaI at|cgat
                        </option>

                        <option value="/tttaaa/ (DraI ttt|aaa)3">
                          DraI ttt|aaa
                        </option>

                        <option value="/cggccg/ (EagI c|ggccg)5">
                          EagI c|ggccg
                        </option>

                        <option value="/gaattc/ (EcoRI g|aattc)5">
                          EcoRI g|aattc
                        </option>

                        <option value="/gatatc/ (EcoRV gat|atc)3">
                          EcoRV gat|atc
                        </option>

                        <option value="/ggcgcc/ (EgeI ggc|gcc)3">
                          EgeI ggc|gcc
                        </option>

                        <option value="/ggccggcc/ (FseI ggccgg|cc)2">
                          FseI ggccgg|cc
                        </option>

                        <option value="/tgcgca/ (FspI tgc|gca)3">
                          FspI tgc|gca
                        </option>

                        <option value="/ggcc/ (HaeIII gg|cc)2">
                          HaeIII gg|cc
                        </option>

                        <option value="/gt[cty][agr]ac/ (HincII gty|rac)3">
                          HincII gty|rac
                        </option>

                        <option value="/aagctt/ (HindIII a|agctt)5">
                          HindIII a|agctt
                        </option>

                        <option value="/ga[acgturyswkmbdhvn]tc/ (HinfI g|antc)4">
                          HinfI g|antc
                        </option>

                        <option value="/gttaac/ (HpaI gtt|aac)3">
                          HpaI gtt|aac
                        </option>

                        <option value="/ccgg/ (HpaII c|cgg)3">
                          HpaII c|cgg
                        </option>

                        <option value="/ggcgcc/ (KasI g|gcgcc)5">
                          KasI g|gcgcc
                        </option>

                        <option value="/ggtacc/ (KpnI ggtac|c)1">
                          KpnI ggtac|c
                        </option>

                        <option value="/[acgturyswkmbdhvn]gatc[acgturyswkmbdhvn]/ (MboI |gatc)5">
                          MboI |gatc
                        </option>

                        <option value="/caattg/ (MfeI c|aattg)5">
                          MfeI c|aattg
                        </option>

                        <option value="/acgcgt/ (MluI a|cgcgt)5">
                          MluI a|cgcgt
                        </option>

                        <option value="/tggcca/ (MscI tgg|cca)3">
                          MscI tgg|cca
                        </option>

                        <option value="/ttaa/ (MseI t|taa)3">
                          MseI t|taa
                        </option>

                        <option value="/ccgg/ (MspI c|cgg)3">
                          MspI c|cgg
                        </option>

                        <option value="/gccggc/ (NaeI gcc|ggc)3">
                          NaeI gcc|ggc
                        </option>

                        <option value="/ggcgcc/ (NarI gg|cgcc)4">
                          NarI gg|cgcc
                        </option>

                        <option value="/ccatgg/ (NcoI c|catgg)5">
                          NcoI c|catgg
                        </option>

                        <option value="/catatg/ (NdeI ca|tatg)4">
                          NdeI ca|tatg
                        </option>

                        <option value="/gatc/ (NdeII |gatc)4">
                          NdeII |gatc
                        </option>

                        <option value="/gccggc/ (NgoMIV g|ccggc)5">
                          NgoMIV g|ccggc
                        </option>

                        <option value="/gctagc/ (NheI g|ctagc)5">
                          NheI g|ctagc
                        </option>

                        <option value="/gcggccgc/ (NotI gc|ggccgc)6">
                          NotI gc|ggccgc
                        </option>

                        <option value="/tcgcga/ (NruI tcg|cga)3">
                          NruI tcg|cga
                        </option>

                        <option value="/atgcat/ (NsiI atgca|t)1">
                          NsiI atgca|t
                        </option>

                        <option value="/ttaattaa/ (PacI ttaat|taa)3">
                          PacI ttaat|taa
                        </option>

                        <option value="/acatgt/ (PciI a|catgt)5">
                          PciI a|catgt
                        </option>

                        <option value="/ggcc/ (PhoI gg|cc)2">
                          PhoI gg|cc
                        </option>

                        <option value="/gtttaaac/ (PmeI gttt|aaac)4">
                          PmeI gttt|aaac
                        </option>

                        <option value="/cacgtg/ (PmlI cac|gtg)3">
                          PmlI cac|gtg
                        </option>

                        <option value="/ttataa/ (PsiI tta|taa)3">
                          PsiI tta|taa
                        </option>

                        <option value="/ctgcag/ (PstI ctgca|g)1">
                          PstI ctgca|g
                        </option>

                        <option value="/cgatcg/ (PvuI cgat|cg)2">
                          PvuI cgat|cg
                        </option>

                        <option value="/cagctg/ (PvuII cag|ctg)3">
                          PvuII cag|ctg
                        </option>

                        <option value="/gtac/ (RsaI gt|ac)2">
                          RsaI gt|ac
                        </option>

                        <option value="/gagctc/ (SacI gagct|c)1">
                          SacI gagct|c
                        </option>

                        <option value="/ccgcgg/ (SacII ccgc|gg)2">
                          SacII ccgc|gg
                        </option>

                        <option value="/gtcgac/ (SalI g|tcgac)5">
                          SalI g|tcgac
                        </option>

                        <option value="/cctgcagg/ (SbfI cctgca|gg)2">
                          SbfI cctgca|gg
                        </option>

                        <option value="/agtact/ (ScaI agt|act)3">
                          ScaI agt|act
                        </option>

                        <option value="/ggcgcc/ (SfoI ggc|gcc)3">
                          SfoI ggc|gcc
                        </option>

                        <option value="/cccggg/ (SmaI ccc|ggg)3">
                          SmaI ccc|ggg
                        </option>

                        <option value="/tacgta/ (SnaBI tac|gta)3">
                          SnaBI tac|gta
                        </option>

                        <option value="/actagt/ (SpeI a|ctagt)5">
                          SpeI a|ctagt
                        </option>

                        <option value="/gcatgc/ (SphI gcatg|c)1">
                          SphI gcatg|c
                        </option>

                        <option value="/aatatt/ (SspI aat|att)3">
                          SspI aat|att
                        </option>

                        <option value="/gagctc/ (SstI gagct|c)1">
                          SstI gagct|c
                        </option>

                        <option value="/ccgcgg/ (SstII ccgc|gg)2">
                          SstII ccgc|gg
                        </option>

                        <option value="/aggcct/ (StuI agg|cct)3">
                          StuI agg|cct
                        </option>

                        <option value="/atttaaat/ (SwaI attt|aaat)4">
                          SwaI attt|aaat
                        </option>

                        <option value="/tcga/ (TaqI t|cga)3">
                          TaqI t|cga
                        </option>

                        <option value="/ctcgag/ (TliI c|tcgag)5">
                          TliI c|tcgag
                        </option>

                        <option value="/attaat/ (VspI at|taat)4">
                          VspI at|taat
                        </option>

                        <option value="/tctaga/ (XbaI t|ctaga)5">
                          XbaI t|ctaga
                        </option>

                        <option value="/ctcgag/ (XhoI c|tcgag)5">
                          XhoI c|tcgag
                        </option>

                        <option value="/cccggg/ (XmaI c|ccggg)5">
                          XmaI c|ccggg
                        </option>
                      </select> and <select class="span3">
                        <option selected="selected" value="">
                          nothing
                        </option>

                        <option value="/aggcct/ (AatI agg|cct)3">
                          AatI agg|cct
                        </option>

                        <option value="/gacgtc/ (AatII gacgt|c)1">
                          AatII gacgt|c
                        </option>

                        <option value="/tgcgca/ (Acc16I tgc|gca)3">
                          Acc16I tgc|gca
                        </option>

                        <option value="/cgcg/ (AccII cg|cg)2">
                          AccII cg|cg
                        </option>

                        <option value="/tccgga/ (AccIII t|ccgga)5">
                          AccIII t|ccgga
                        </option>

                        <option value="/aacgtt/ (AclI aa|cgtt)4">
                          AclI aa|cgtt
                        </option>

                        <option value="/cacgtg/ (AcvI cac|gtg)3">
                          AcvI cac|gtg
                        </option>

                        <option value="/gtac/ (AfaI gt|ac)2">
                          AfaI gt|ac
                        </option>

                        <option value="/agcgct/ (AfeI agc|gct)3">
                          AfeI agc|gct
                        </option>

                        <option value="/cttaag/ (AflII c|ttaag)5">
                          AflII c|ttaag
                        </option>

                        <option value="/accggt/ (AgeI a|ccggt)5">
                          AgeI a|ccggt
                        </option>

                        <option value="/actagt/ (AhlI a|ctagt)5">
                          AhlI a|ctagt
                        </option>

                        <option value="/gtgcac/ (Alw441 g|tgcac)5">
                          Alw441 g|tgcac
                        </option>

                        <option value="/agct/ (AluI ag|ct)2">
                          AluI ag|ct
                        </option>

                        <option value="/agcgct/ (Aor51HI agc|gct)3">
                          Aor51HI agc|gct
                        </option>

                        <option value="/gggccc/ (ApaI gggcc|c)1">
                          ApaI gggcc|c
                        </option>

                        <option value="/gtgcac/ (ApaLI g|tgcac)5">
                          ApaLI g|tgcac
                        </option>

                        <option value="/ggcgcgcc/ (AscI gg|cgcgcc)6">
                          AscI gg|cgcgcc
                        </option>

                        <option value="/attaat/ (AseI at|taat)4">
                          AseI at|taat
                        </option>

                        <option value="/ggtacc/ (Asp718I g|gtacc)5">
                          Asp718I g|gtacc
                        </option>

                        <option value="/ttcgaa/ (AsuII tt|cgaa)4">
                          AsuII tt|cgaa
                        </option>

                        <option value="/c[cty]cg[agr]g/ (AvaI c|ycgrg)5">
                          AvaI c|ycgrg
                        </option>

                        <option value="/tgcgca/ (AviII tgc|gca)3">
                          AviII tgc|gca
                        </option>

                        <option value="/cctagg/ (AvrII c|ctagg)5">
                          AvrII c|ctagg
                        </option>

                        <option value="/tggcca/ (BalI tgg|cca)3">
                          BalI tgg|cca
                        </option>

                        <option value="/ggatcc/ (BamHI g|gatcc)5">
                          BamHI g|gatcc
                        </option>

                        <option value="/atcgat/ (BanIII at|cgat)4">
                          BanIII at|cgat
                        </option>

                        <option value="/ggcgcc/ (BbeI ggcgc|c)1">
                          BbeI ggcgc|c
                        </option>

                        <option value="/cacgtg/ (BbrPI cac|gtg)3">
                          BbrPI cac|gtg
                        </option>

                        <option value="/gcatgc/ (BbuI gcatg|c)1">
                          BbuI gcatg|c
                        </option>

                        <option value="/actagt/ (BcuI a|ctagt)5">
                          BcuI a|ctagt
                        </option>

                        <option value="/tgatca/ (BclI t|gatca)5">
                          BclI t|gatca
                        </option>

                        <option value="/ctag/ (BfaI c|tag)3">
                          BfaI c|tag
                        </option>

                        <option value="/cttaag/ (BfrI c|ttaag)5">
                          BfrI c|ttaag
                        </option>

                        <option value="/atgcat/ (BfrBI atg|cat)3">
                          BfrBI atg|cat
                        </option>

                        <option value="/agatct/ (BglII a|gatct)5">
                          BglII a|gatct
                        </option>

                        <option value="/cctagg/ (BlnI c|ctagg)5">
                          BlnI c|ctagg
                        </option>

                        <option value="/atcgat/ (BseCI at|cgat)4">
                          BseCI at|cgat
                        </option>

                        <option value="/gcgcgc/ (BsePI g|cgcgc)5">
                          BsePI g|cgcgc
                        </option>

                        <option value="/cggccg/ (BseX3I c|ggccg)5">
                          BseX3I c|ggccg
                        </option>

                        <option value="/accggt/ (BshTI a|ccggt)5">
                          BshTI a|ccggt
                        </option>

                        <option value="/tgtaca/ (Bsp1407I t|gtaca)5">
                          Bsp1407I t|gtaca
                        </option>

                        <option value="/ccatgg/ (Bsp19I c|catgg)5">
                          Bsp19I c|catgg
                        </option>

                        <option value="/atcgat/ (BspDI at|cgat)4">
                          BspDI at|cgat
                        </option>

                        <option value="/tccgga/ (BspEI t|ccgga)5">
                          BspEI t|ccgga
                        </option>

                        <option value="/tgtaca/ (BsrGI t|gtaca)5">
                          BsrGI t|gtaca
                        </option>

                        <option value="/gcgcgc/ (BssHII g|cgcgc)5">
                          BssHII g|cgcgc
                        </option>

                        <option value="/cgcg/ (BstUI cg|cg)2">
                          BstUI cg|cg
                        </option>

                        <option value="/atcgat/ (ClaI at|cgat)4">
                          ClaI at|cgat
                        </option>

                        <option value="/tttaaa/ (DraI ttt|aaa)3">
                          DraI ttt|aaa
                        </option>

                        <option value="/cggccg/ (EagI c|ggccg)5">
                          EagI c|ggccg
                        </option>

                        <option value="/gaattc/ (EcoRI g|aattc)5">
                          EcoRI g|aattc
                        </option>

                        <option value="/gatatc/ (EcoRV gat|atc)3">
                          EcoRV gat|atc
                        </option>

                        <option value="/ggcgcc/ (EgeI ggc|gcc)3">
                          EgeI ggc|gcc
                        </option>

                        <option value="/ggccggcc/ (FseI ggccgg|cc)2">
                          FseI ggccgg|cc
                        </option>

                        <option value="/tgcgca/ (FspI tgc|gca)3">
                          FspI tgc|gca
                        </option>

                        <option value="/ggcc/ (HaeIII gg|cc)2">
                          HaeIII gg|cc
                        </option>

                        <option value="/gt[cty][agr]ac/ (HincII gty|rac)3">
                          HincII gty|rac
                        </option>

                        <option value="/aagctt/ (HindIII a|agctt)5">
                          HindIII a|agctt
                        </option>

                        <option value="/ga[acgturyswkmbdhvn]tc/ (HinfI g|antc)4">
                          HinfI g|antc
                        </option>

                        <option value="/gttaac/ (HpaI gtt|aac)3">
                          HpaI gtt|aac
                        </option>

                        <option value="/ccgg/ (HpaII c|cgg)3">
                          HpaII c|cgg
                        </option>

                        <option value="/ggcgcc/ (KasI g|gcgcc)5">
                          KasI g|gcgcc
                        </option>

                        <option value="/ggtacc/ (KpnI ggtac|c)1">
                          KpnI ggtac|c
                        </option>

                        <option value="/[acgturyswkmbdhvn]gatc[acgturyswkmbdhvn]/ (MboI |gatc)5">
                          MboI |gatc
                        </option>

                        <option value="/caattg/ (MfeI c|aattg)5">
                          MfeI c|aattg
                        </option>

                        <option value="/acgcgt/ (MluI a|cgcgt)5">
                          MluI a|cgcgt
                        </option>

                        <option value="/tggcca/ (MscI tgg|cca)3">
                          MscI tgg|cca
                        </option>

                        <option value="/ttaa/ (MseI t|taa)3">
                          MseI t|taa
                        </option>

                        <option value="/ccgg/ (MspI c|cgg)3">
                          MspI c|cgg
                        </option>

                        <option value="/gccggc/ (NaeI gcc|ggc)3">
                          NaeI gcc|ggc
                        </option>

                        <option value="/ggcgcc/ (NarI gg|cgcc)4">
                          NarI gg|cgcc
                        </option>

                        <option value="/ccatgg/ (NcoI c|catgg)5">
                          NcoI c|catgg
                        </option>

                        <option value="/catatg/ (NdeI ca|tatg)4">
                          NdeI ca|tatg
                        </option>

                        <option value="/gatc/ (NdeII |gatc)4">
                          NdeII |gatc
                        </option>

                        <option value="/gccggc/ (NgoMIV g|ccggc)5">
                          NgoMIV g|ccggc
                        </option>

                        <option value="/gctagc/ (NheI g|ctagc)5">
                          NheI g|ctagc
                        </option>

                        <option value="/gcggccgc/ (NotI gc|ggccgc)6">
                          NotI gc|ggccgc
                        </option>

                        <option value="/tcgcga/ (NruI tcg|cga)3">
                          NruI tcg|cga
                        </option>

                        <option value="/atgcat/ (NsiI atgca|t)1">
                          NsiI atgca|t
                        </option>

                        <option value="/ttaattaa/ (PacI ttaat|taa)3">
                          PacI ttaat|taa
                        </option>

                        <option value="/acatgt/ (PciI a|catgt)5">
                          PciI a|catgt
                        </option>

                        <option value="/ggcc/ (PhoI gg|cc)2">
                          PhoI gg|cc
                        </option>

                        <option value="/gtttaaac/ (PmeI gttt|aaac)4">
                          PmeI gttt|aaac
                        </option>

                        <option value="/cacgtg/ (PmlI cac|gtg)3">
                          PmlI cac|gtg
                        </option>

                        <option value="/ttataa/ (PsiI tta|taa)3">
                          PsiI tta|taa
                        </option>

                        <option value="/ctgcag/ (PstI ctgca|g)1">
                          PstI ctgca|g
                        </option>

                        <option value="/cgatcg/ (PvuI cgat|cg)2">
                          PvuI cgat|cg
                        </option>

                        <option value="/cagctg/ (PvuII cag|ctg)3">
                          PvuII cag|ctg
                        </option>

                        <option value="/gtac/ (RsaI gt|ac)2">
                          RsaI gt|ac
                        </option>

                        <option value="/gagctc/ (SacI gagct|c)1">
                          SacI gagct|c
                        </option>

                        <option value="/ccgcgg/ (SacII ccgc|gg)2">
                          SacII ccgc|gg
                        </option>

                        <option value="/gtcgac/ (SalI g|tcgac)5">
                          SalI g|tcgac
                        </option>

                        <option value="/cctgcagg/ (SbfI cctgca|gg)2">
                          SbfI cctgca|gg
                        </option>

                        <option value="/agtact/ (ScaI agt|act)3">
                          ScaI agt|act
                        </option>

                        <option value="/ggcgcc/ (SfoI ggc|gcc)3">
                          SfoI ggc|gcc
                        </option>

                        <option value="/cccggg/ (SmaI ccc|ggg)3">
                          SmaI ccc|ggg
                        </option>

                        <option value="/tacgta/ (SnaBI tac|gta)3">
                          SnaBI tac|gta
                        </option>

                        <option value="/actagt/ (SpeI a|ctagt)5">
                          SpeI a|ctagt
                        </option>

                        <option value="/gcatgc/ (SphI gcatg|c)1">
                          SphI gcatg|c
                        </option>

                        <option value="/aatatt/ (SspI aat|att)3">
                          SspI aat|att
                        </option>

                        <option value="/gagctc/ (SstI gagct|c)1">
                          SstI gagct|c
                        </option>

                        <option value="/ccgcgg/ (SstII ccgc|gg)2">
                          SstII ccgc|gg
                        </option>

                        <option value="/aggcct/ (StuI agg|cct)3">
                          StuI agg|cct
                        </option>

                        <option value="/atttaaat/ (SwaI attt|aaat)4">
                          SwaI attt|aaat
                        </option>

                        <option value="/tcga/ (TaqI t|cga)3">
                          TaqI t|cga
                        </option>

                        <option value="/ctcgag/ (TliI c|tcgag)5">
                          TliI c|tcgag
                        </option>

                        <option value="/attaat/ (VspI at|taat)4">
                          VspI at|taat
                        </option>

                        <option value="/tctaga/ (XbaI t|ctaga)5">
                          XbaI t|ctaga
                        </option>

                        <option value="/ctcgag/ (XhoI c|tcgag)5">
                          XhoI c|tcgag
                        </option>

                        <option value="/cccggg/ (XmaI c|ccggg)5">
                          XmaI c|ccggg
                        </option>
                      </select> and <select class="span3">
                        <option selected="selected" value="">
                          nothing
                        </option>

                        <option value="/aggcct/ (AatI agg|cct)3">
                          AatI agg|cct
                        </option>

                        <option value="/gacgtc/ (AatII gacgt|c)1">
                          AatII gacgt|c
                        </option>

                        <option value="/tgcgca/ (Acc16I tgc|gca)3">
                          Acc16I tgc|gca
                        </option>

                        <option value="/cgcg/ (AccII cg|cg)2">
                          AccII cg|cg
                        </option>

                        <option value="/tccgga/ (AccIII t|ccgga)5">
                          AccIII t|ccgga
                        </option>

                        <option value="/aacgtt/ (AclI aa|cgtt)4">
                          AclI aa|cgtt
                        </option>

                        <option value="/cacgtg/ (AcvI cac|gtg)3">
                          AcvI cac|gtg
                        </option>

                        <option value="/gtac/ (AfaI gt|ac)2">
                          AfaI gt|ac
                        </option>

                        <option value="/agcgct/ (AfeI agc|gct)3">
                          AfeI agc|gct
                        </option>

                        <option value="/cttaag/ (AflII c|ttaag)5">
                          AflII c|ttaag
                        </option>

                        <option value="/accggt/ (AgeI a|ccggt)5">
                          AgeI a|ccggt
                        </option>

                        <option value="/actagt/ (AhlI a|ctagt)5">
                          AhlI a|ctagt
                        </option>

                        <option value="/gtgcac/ (Alw441 g|tgcac)5">
                          Alw441 g|tgcac
                        </option>

                        <option value="/agct/ (AluI ag|ct)2">
                          AluI ag|ct
                        </option>

                        <option value="/agcgct/ (Aor51HI agc|gct)3">
                          Aor51HI agc|gct
                        </option>

                        <option value="/gggccc/ (ApaI gggcc|c)1">
                          ApaI gggcc|c
                        </option>

                        <option value="/gtgcac/ (ApaLI g|tgcac)5">
                          ApaLI g|tgcac
                        </option>

                        <option value="/ggcgcgcc/ (AscI gg|cgcgcc)6">
                          AscI gg|cgcgcc
                        </option>

                        <option value="/attaat/ (AseI at|taat)4">
                          AseI at|taat
                        </option>

                        <option value="/ggtacc/ (Asp718I g|gtacc)5">
                          Asp718I g|gtacc
                        </option>

                        <option value="/ttcgaa/ (AsuII tt|cgaa)4">
                          AsuII tt|cgaa
                        </option>

                        <option value="/c[cty]cg[agr]g/ (AvaI c|ycgrg)5">
                          AvaI c|ycgrg
                        </option>

                        <option value="/tgcgca/ (AviII tgc|gca)3">
                          AviII tgc|gca
                        </option>

                        <option value="/cctagg/ (AvrII c|ctagg)5">
                          AvrII c|ctagg
                        </option>

                        <option value="/tggcca/ (BalI tgg|cca)3">
                          BalI tgg|cca
                        </option>

                        <option value="/ggatcc/ (BamHI g|gatcc)5">
                          BamHI g|gatcc
                        </option>

                        <option value="/atcgat/ (BanIII at|cgat)4">
                          BanIII at|cgat
                        </option>

                        <option value="/ggcgcc/ (BbeI ggcgc|c)1">
                          BbeI ggcgc|c
                        </option>

                        <option value="/cacgtg/ (BbrPI cac|gtg)3">
                          BbrPI cac|gtg
                        </option>

                        <option value="/gcatgc/ (BbuI gcatg|c)1">
                          BbuI gcatg|c
                        </option>

                        <option value="/actagt/ (BcuI a|ctagt)5">
                          BcuI a|ctagt
                        </option>

                        <option value="/tgatca/ (BclI t|gatca)5">
                          BclI t|gatca
                        </option>

                        <option value="/ctag/ (BfaI c|tag)3">
                          BfaI c|tag
                        </option>

                        <option value="/cttaag/ (BfrI c|ttaag)5">
                          BfrI c|ttaag
                        </option>

                        <option value="/atgcat/ (BfrBI atg|cat)3">
                          BfrBI atg|cat
                        </option>

                        <option value="/agatct/ (BglII a|gatct)5">
                          BglII a|gatct
                        </option>

                        <option value="/cctagg/ (BlnI c|ctagg)5">
                          BlnI c|ctagg
                        </option>

                        <option value="/atcgat/ (BseCI at|cgat)4">
                          BseCI at|cgat
                        </option>

                        <option value="/gcgcgc/ (BsePI g|cgcgc)5">
                          BsePI g|cgcgc
                        </option>

                        <option value="/cggccg/ (BseX3I c|ggccg)5">
                          BseX3I c|ggccg
                        </option>

                        <option value="/accggt/ (BshTI a|ccggt)5">
                          BshTI a|ccggt
                        </option>

                        <option value="/tgtaca/ (Bsp1407I t|gtaca)5">
                          Bsp1407I t|gtaca
                        </option>

                        <option value="/ccatgg/ (Bsp19I c|catgg)5">
                          Bsp19I c|catgg
                        </option>

                        <option value="/atcgat/ (BspDI at|cgat)4">
                          BspDI at|cgat
                        </option>

                        <option value="/tccgga/ (BspEI t|ccgga)5">
                          BspEI t|ccgga
                        </option>

                        <option value="/tgtaca/ (BsrGI t|gtaca)5">
                          BsrGI t|gtaca
                        </option>

                        <option value="/gcgcgc/ (BssHII g|cgcgc)5">
                          BssHII g|cgcgc
                        </option>

                        <option value="/cgcg/ (BstUI cg|cg)2">
                          BstUI cg|cg
                        </option>

                        <option value="/atcgat/ (ClaI at|cgat)4">
                          ClaI at|cgat
                        </option>

                        <option value="/tttaaa/ (DraI ttt|aaa)3">
                          DraI ttt|aaa
                        </option>

                        <option value="/cggccg/ (EagI c|ggccg)5">
                          EagI c|ggccg
                        </option>

                        <option value="/gaattc/ (EcoRI g|aattc)5">
                          EcoRI g|aattc
                        </option>

                        <option value="/gatatc/ (EcoRV gat|atc)3">
                          EcoRV gat|atc
                        </option>

                        <option value="/ggcgcc/ (EgeI ggc|gcc)3">
                          EgeI ggc|gcc
                        </option>

                        <option value="/ggccggcc/ (FseI ggccgg|cc)2">
                          FseI ggccgg|cc
                        </option>

                        <option value="/tgcgca/ (FspI tgc|gca)3">
                          FspI tgc|gca
                        </option>

                        <option value="/ggcc/ (HaeIII gg|cc)2">
                          HaeIII gg|cc
                        </option>

                        <option value="/gt[cty][agr]ac/ (HincII gty|rac)3">
                          HincII gty|rac
                        </option>

                        <option value="/aagctt/ (HindIII a|agctt)5">
                          HindIII a|agctt
                        </option>

                        <option value="/ga[acgturyswkmbdhvn]tc/ (HinfI g|antc)4">
                          HinfI g|antc
                        </option>

                        <option value="/gttaac/ (HpaI gtt|aac)3">
                          HpaI gtt|aac
                        </option>

                        <option value="/ccgg/ (HpaII c|cgg)3">
                          HpaII c|cgg
                        </option>

                        <option value="/ggcgcc/ (KasI g|gcgcc)5">
                          KasI g|gcgcc
                        </option>

                        <option value="/ggtacc/ (KpnI ggtac|c)1">
                          KpnI ggtac|c
                        </option>

                        <option value="/[acgturyswkmbdhvn]gatc[acgturyswkmbdhvn]/ (MboI |gatc)5">
                          MboI |gatc
                        </option>

                        <option value="/caattg/ (MfeI c|aattg)5">
                          MfeI c|aattg
                        </option>

                        <option value="/acgcgt/ (MluI a|cgcgt)5">
                          MluI a|cgcgt
                        </option>

                        <option value="/tggcca/ (MscI tgg|cca)3">
                          MscI tgg|cca
                        </option>

                        <option value="/ttaa/ (MseI t|taa)3">
                          MseI t|taa
                        </option>

                        <option value="/ccgg/ (MspI c|cgg)3">
                          MspI c|cgg
                        </option>

                        <option value="/gccggc/ (NaeI gcc|ggc)3">
                          NaeI gcc|ggc
                        </option>

                        <option value="/ggcgcc/ (NarI gg|cgcc)4">
                          NarI gg|cgcc
                        </option>

                        <option value="/ccatgg/ (NcoI c|catgg)5">
                          NcoI c|catgg
                        </option>

                        <option value="/catatg/ (NdeI ca|tatg)4">
                          NdeI ca|tatg
                        </option>

                        <option value="/gatc/ (NdeII |gatc)4">
                          NdeII |gatc
                        </option>

                        <option value="/gccggc/ (NgoMIV g|ccggc)5">
                          NgoMIV g|ccggc
                        </option>

                        <option value="/gctagc/ (NheI g|ctagc)5">
                          NheI g|ctagc
                        </option>

                        <option value="/gcggccgc/ (NotI gc|ggccgc)6">
                          NotI gc|ggccgc
                        </option>

                        <option value="/tcgcga/ (NruI tcg|cga)3">
                          NruI tcg|cga
                        </option>

                        <option value="/atgcat/ (NsiI atgca|t)1">
                          NsiI atgca|t
                        </option>

                        <option value="/ttaattaa/ (PacI ttaat|taa)3">
                          PacI ttaat|taa
                        </option>

                        <option value="/acatgt/ (PciI a|catgt)5">
                          PciI a|catgt
                        </option>

                        <option value="/ggcc/ (PhoI gg|cc)2">
                          PhoI gg|cc
                        </option>

                        <option value="/gtttaaac/ (PmeI gttt|aaac)4">
                          PmeI gttt|aaac
                        </option>

                        <option value="/cacgtg/ (PmlI cac|gtg)3">
                          PmlI cac|gtg
                        </option>

                        <option value="/ttataa/ (PsiI tta|taa)3">
                          PsiI tta|taa
                        </option>

                        <option value="/ctgcag/ (PstI ctgca|g)1">
                          PstI ctgca|g
                        </option>

                        <option value="/cgatcg/ (PvuI cgat|cg)2">
                          PvuI cgat|cg
                        </option>

                        <option value="/cagctg/ (PvuII cag|ctg)3">
                          PvuII cag|ctg
                        </option>

                        <option value="/gtac/ (RsaI gt|ac)2">
                          RsaI gt|ac
                        </option>

                        <option value="/gagctc/ (SacI gagct|c)1">
                          SacI gagct|c
                        </option>

                        <option value="/ccgcgg/ (SacII ccgc|gg)2">
                          SacII ccgc|gg
                        </option>

                        <option value="/gtcgac/ (SalI g|tcgac)5">
                          SalI g|tcgac
                        </option>

                        <option value="/cctgcagg/ (SbfI cctgca|gg)2">
                          SbfI cctgca|gg
                        </option>

                        <option value="/agtact/ (ScaI agt|act)3">
                          ScaI agt|act
                        </option>

                        <option value="/ggcgcc/ (SfoI ggc|gcc)3">
                          SfoI ggc|gcc
                        </option>

                        <option value="/cccggg/ (SmaI ccc|ggg)3">
                          SmaI ccc|ggg
                        </option>

                        <option value="/tacgta/ (SnaBI tac|gta)3">
                          SnaBI tac|gta
                        </option>

                        <option value="/actagt/ (SpeI a|ctagt)5">
                          SpeI a|ctagt
                        </option>

                        <option value="/gcatgc/ (SphI gcatg|c)1">
                          SphI gcatg|c
                        </option>

                        <option value="/aatatt/ (SspI aat|att)3">
                          SspI aat|att
                        </option>

                        <option value="/gagctc/ (SstI gagct|c)1">
                          SstI gagct|c
                        </option>

                        <option value="/ccgcgg/ (SstII ccgc|gg)2">
                          SstII ccgc|gg
                        </option>

                        <option value="/aggcct/ (StuI agg|cct)3">
                          StuI agg|cct
                        </option>

                        <option value="/atttaaat/ (SwaI attt|aaat)4">
                          SwaI attt|aaat
                        </option>

                        <option value="/tcga/ (TaqI t|cga)3">
                          TaqI t|cga
                        </option>

                        <option value="/ctcgag/ (TliI c|tcgag)5">
                          TliI c|tcgag
                        </option>

                        <option value="/attaat/ (VspI at|taat)4">
                          VspI at|taat
                        </option>

                        <option value="/tctaga/ (XbaI t|ctaga)5">
                          XbaI t|ctaga
                        </option>

                        <option value="/ctcgag/ (XhoI c|tcgag)5">
                          XhoI c|tcgag
                        </option>

                        <option value="/cccggg/ (XmaI c|ccggg)5">
                          XmaI c|ccggg
                        </option>
                      </select> .
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