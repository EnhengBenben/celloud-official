<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
        <title>CelLoud华木兰报告</title>
        <link href="//cdn.bootcss.com/Swiper/3.4.1/css/swiper.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/wechat/rocky_report.css?v=3.4" />
    </head>

    <body>
        <!----------report container-------------->
		<div id="container" class="swiper-container">
			<div class="swiper-wrapper">
				<!--------page1--------->
				<div class="main swiper-slide">
					<div class="p1">

					</div>
				</div>
				<!--------page2--------->
				<div class="main  swiper-slide">
					<h2 class="test_title">BRCA基因检测结果报告</h2>
					<span class="test_title_en">BRCA Genetic Testing Report</span>
					<div class="p2">
					<!--------name开始------------->
					<div class="p2_main p2_">
						<div class="p2_name">
							<img src="<%=request.getContextPath()%>/images/wechat/rocky_report/02.Page 1.png" /> 姓名：
						</div>
						<div class="p2_age">
							 <img src="<%=request.getContextPath()%>/images/wechat/rocky_report/02.Page 2.png" /> 出生年月：
						</div>
					</div>
					<!--------name结束------------->

					<!--------type开始------------->
					<div class="p2_main type">
						<!--<span>样本类型：</span>
					<span>样本编号：</span><br>
					<span>送检日期：</span>
					<span>报告日期：</span>-->

					</div>
					<!--------type结束------------->
					
					<!--------result开始------------->
					<div class="p2_main result">
					</div>
					<!--------resul结束------------->

					<!-------report开始------------->
					<div class=" rep">
						<div class="report">
							<table class="tab_data_p2" border="0" cellspacing="" cellpadding="">
								<thead>
									<tr class="tab_header_p2">
										<th>编号</th>
										<th>基因</th>
										<th>核苷酸变异</th>
										<th>氨基酸变异</th>
										<th>临床意义</th>
									</tr>
								</thead>
								<tbody class="p2_tbody">

								</tbody>
							</table>
						</div>
					</div>
					<!--------report结束------------->

					<!--------date开始------------->
					<div class="p2_main  date">
						<span>检测人：</span>
						<span>复核人：</span>
						<span>日&emsp;期：</span>
						<span>日&emsp;期：</span>
					</div>
					<!--------date结束------------->
					</div>
				</div>
				<!--------page3--------->
				<div class="main p3_pos swiper-slide">
					<div class="p3">
						<img  src="<%=request.getContextPath()%>/images/wechat/rocky_report/1－@3x.png" />
					</div>
				</div>
				<!--------page4--------->
				<div class="main swiper-slide p4 ">
					<img class="backImg1" src="<%=request.getContextPath()%>/images/wechat/rocky_report/04.Page 1.png" />
					<div class="p4_content">
						<p>我们向您致敬！</p>
						<p>在健康管理上态度积极并作出明智选择，不仅是勇气，更是责任。我们愿陪您一起迈出健康管理的第一步。</p>
						<p>作为您个人健康管理的重要参考，BRCA基因检测报告能协助您评估自身罹患遗传性乳腺癌的风险。报告基于您提供的检测样品，经过精密的基因测序和专业的数据分析，同时结合最新的科学研究成果，专业、客观地解释您的基因与已知遗传性乳腺癌突变之间的关系，用以协助您与您的健康管理顾问作出准确的健康评估和管理规划。</p>
						<p id="p5_bot">送检样本的处理及数据分析、出具报告的整个过程，都有专业的监管与流程记录，以确保您的个人隐私与相应数据的安全。我们遵从相应的服务协议，在未获得本人授权情况下，数据不作他用。同时也请您妥善保管报告以及相应的数据信息，以免泄漏。</p>
						<p class="myZhu">祝，健康！</p>
					</div>
				</div>
				<!--------page5--------->
				<!--<div class="main swiper-slide">
					 <img class="backImg1" src="<%=request.getContextPath()%>/images/wechat/rocky_report/04.Page 1.png" />
					<div class="p5_content">
						<p>风险提示：</p>
						
						<p><b>·</b><i>报告所给出的信息，是作为健康管理的参考信息，不能成为疾病诊断与治疗依据。疾病诊断与治疗需由临床医生给出结论与方案。</i></p>
						<p><b>·</b><i>健康的身体由基因、良好的生活习惯及生活环境共同影响，基因突变本身不是疾病形成的唯一因素。</i></p>
						
						<p><b>·</b><i>健康，是长期管理的结果，此报告所揭示的突变及相关的信息会因为研究的进展而发生变化。</i></p>
						
						<p id="p5_bot">送检样本的处理及数据分析、出具报告的整个过程，都有专业的监管与流程记录，以确保您的个人隐私与相应数据的安全。我们遵从相应的服务协议，在未获得本人授权情况下，数据不作他用。同时也请您妥善保管报告以及相应的数据信息，以免泄漏。</p>
						<p>祝，健康！</p>
					</div>
				</div>-->
				<!--------page6--------->
				<div class="main swiper-slide">
					 <img class="backImg6" src="<%=request.getContextPath()%>/images/wechat/rocky_report/06.Page 1.png" />
					<div class="p6_content_top">
						<img src="<%=request.getContextPath()%>/images/wechat/rocky_report/未标题-1_06.png" />
					</div>
					<div class="p6_content_bottom">
						<p><span style="font-style: italic;font-family: 'times new roman';">BRCA1/BRCA2</span>基因是抑癌基因，它们表达的蛋白在细胞DNA精密修复所需的酶促通路中起作用，调节细胞复制、DNA损伤修复、细胞正常生长，保护细胞不会生长失控和癌变。一些危害突变会导致这些基因或它们所表达的蛋白失去正常功能，无法修复DNA损伤，使得DNA中的异常不断累积，最终导致癌症的形成。</p>
						<p>目前研究者们已发现了<span style="font-style: italic;font-family: 'times new roman';">BRCA1/2</span>基因中多达上万种的突变， 参考国际癌症研究机构（International Agency for Research on Cancer, IARC）的分类标准，基因突变的临床意义分为如下5类 ：</p>
					</div>
				</div>
				<!--------page7--------->
				<div class="main swiper-slide">
					<div class="p7-content">
						<div>
							<p>
								<b>·</b>&nbsp;&nbsp;Class 5 – 致病相关的变异   Pathogenic 
							</p>
							<p>
								<b>·</b>&nbsp;&nbsp;Class 4 – 可能致病的变异   Likely pathogenic
							</p>
							<p>
								<b>·</b>&nbsp;&nbsp;Class 3 – 意义不明确的变异   Uncertain Significance
							</p>
							<p>
								<b>·</b>&nbsp;&nbsp;Class 2 – 可能良性的变异   Likely benign
							</p>
							<p>
								<b>·</b>&nbsp;&nbsp;Class 1 – 良性的变异   Benign
							</p>
						</div>
						<div class="p7">
							<div class="countIn">

							</div>
							<table class="tab_data"  align='center'  border="0" cellspacing="1" cellpadding="">
								<thead>
									<tr class="tab_header">
										<th>编号</th>
										<th>基因</th>
										<th>核苷酸变异</th>
										<th>氨基酸变异</th>
										<th>临床意义</th>
									</tr>
								</thead>
								<tbody class="tab_body_p6">

								</tbody>
							</table>
						</div>
					</div>
				</div>
				<!--------page8--------->
				<div class="main swiper-slide">
					 <img class="backImg8" src="<%=request.getContextPath()%>/images/wechat/rocky_report/08.Page 1.png" />
					<div class="p8_content">
						<div>
							根据遗传学“中心法则”，遗传信息由DNA开始最终传向蛋白质，蛋白分子共同协作使我们的身体正常运转。发生有害突变的DNA产生异常的蛋白（蛋白分子大小或分子结构发生变化），不能行使其正常功能，最终可能导致机体发生病变。
						</div>
						<div class="p8_img"><img src="<%=request.getContextPath()%>/images/wechat/rocky_report/未标题-1_16.png" /></div>
						<div class="p8_m">参考国际癌症研究机构（International Agency for Research on Cancer,IARC）的分类标准，基因变异的临床意义分类及标准为：</div>
						<div class="p8_text"><b>·</b><i>致病相关变异：已证实能使癌症风险显著提高的突变。这种变异往往是移码突变，导致蛋白质不能正常表达。</i></div>
						<div class="p8_text"><b>·</b><i>可能致病变异：没有明确证据，当前认为该变异有害。</i></div>
						
						<div class="p8_text"><b>·</b><i>意义不明确变异：不确定该变异是有害还是无影响。这是一种常见的检测结果，很多变异一开始都被分为这一类。随着证据的不断补充，这些变异会被重新分类。</i></div>
						<div class="p8_text"><b>·</b><i>可能良性变异：没有明确证据，当前认为该变异对人体无害。</i></div>
						<div class="p8_text"><b>·</b><i>良性变异：被证实为对人体无害的变异，可以被认为是“未发生突变”。</i></div>
						<div class="p8_last">注：致病相关变异有很高但不完全的遗传外显率，这意味着有害突变携带者有较高的癌变风险，但有的人可能最终并不患癌。</div>
					</div>
				</div>
				<!--------page9--------->
				<div class="main swiper-slide">
					<div class="p9-content">
						<div class="p9_decription">在您的<span id="BCRA">BRCA1/2</span>基因中，BRCA变异影响（临床意义分类）描述如下：</div>
						<table class="p9-tab" border="" cellspacing="" cellpadding="">
							<thead>
								<tr class="p2_tab_header">
									<th>编号</th>
									<th>编译名称</th>
									<th>影响描述</th>
								</tr>
							</thead>
							<tbody class="tab_significance">

							</tbody>
						</table>
					</div>
				</div>
				<!--------page10--------->
				<div class="main swiper-slide">
					<div class="p10-content">
						<img class="backImg10" src="<%=request.getContextPath()%>/images/wechat/rocky_report/10.Page 1.png" />
						<p>乳腺癌的病因尚未完全清楚，其中因相关基因突变引起的乳腺癌称为遗传性乳腺癌，这些基因包括：</p>
						<table class="p10_tab" border="0" cellspacing="cellspacing" cellpadding="cellpadding">
							<tr class="p10_tab_body">
								<td>高风险基因</td>
								<td class="borNone">BRCA1,BRCA2,TP53,PTEN,STK11,CDH1</td>
							</tr>
							<tr class="p10_tab_body ">
								<td>中风险基因</td>
								<td class="borNone">CHEK2,ATM,PALB2,BRIP1,MRE11A,<br>RAD50, RAD51C,NBN</td>
							</tr>
							<tr class="p10_tab_body">
								<td>低风险基因</td>
								<td>CASP8,FGFR2,MAP3K,TOX3,LSP1</td>
							</tr>
						</table>
						<p id="p10_artical2">遗传性乳腺癌占全部乳腺癌的5%~10%。<span style="font-style: italic;font-family: 'times new roman';">BRCA1</span>和<span id="BCRA">BRCA2</span>基因是最重要的两个遗传性乳腺癌易感基因，它们引起的乳腺癌占遗传性乳腺癌的40%~45%</p>
						<div><img id="p10_img" src="<%=request.getContextPath()%>/images/wechat/rocky_report/未标题-1_22.png" /></div>
						<p id="p10_artical3">平均来看，普通人在50岁之前患乳腺癌的风险为1.9%，70岁之前患乳腺癌的风险为7.3%。（注:患病风险数据来源于欧美人群,仅供亚洲人群参考。）</p>
						<p id="p10_artical4">本次检测在您的乳腺癌关键基因<span style="font-style: italic;font-family: 'times new roman';">BRCA1</span>和<span id="BCRA">BRCA2</span>上未发现致病变异，因而该因素没有提高您的乳腺癌患病风险。建议您结合本检测结果，保持良好心态，基于家族史及自身身体状况，积极管理自身健康。</p>
					</div>
				</div>
				<!--------page11--------->
				<div class="main swiper-slide">
					 <img class="backImg11" src="<%=request.getContextPath()%>/images/wechat/rocky_report/11.Page 1.png" />
					<div class="p11-content">
						<div>人是二倍体基因组生物，每个细胞中有两套基因（一份来自父亲一份来自母亲）。一般来说，子代携带者只有其中一套基因上会遗传来自父辈的突变，被称为杂合子突变携带者。<span style="font-style: italic;font-family: 'times new roman';">BRCA1/BRCA2</span>突变升高癌症风险是显性遗传作用，只要等位基因中的一个基因位点发生突变就能表现出来。突变可以从父母任何一方遗传而来，也可以遗传给儿子或女儿。子代不论性别，从突变携带者父/母亲那里遗传到同一突变的概率为50%，因此男性也可能会携带有突变。</div>
						<div><img src="<%=request.getContextPath()%>/images/wechat/rocky_report/未标题-1_26.png" /></div>
						<div>本次检测在您的乳腺癌关键基因<span style="font-style: italic;font-family: 'times new roman';">BRCA1</span>和<span id="BCRA">BRCA2</span>中未检测到致病突变，在大多数情况下，您的亲属不需要进行这两个基因的突变筛查。在有乳腺癌家族史的情况下，为了分析家族遗传风险信息，建议确诊为乳腺癌患者的直系亲属接受此基因的突变检测。</div>
					</div>
				</div>
				<!--------page12--------->
				<div class="main swiper-slide">
					  <img class="backImg12" src="<%=request.getContextPath()%>/images/wechat/rocky_report/12.Page 1.png" />
					<div class="p12-content">
						<span>性别和年龄</span>
						<div>女性和年龄增长是发生乳腺癌的主要危险因素。女性一生都有发生乳腺癌的风险，风险大约是男性的100倍。70岁女性患乳腺癌的短期风险大约是30岁女性的10倍。</div>
						<span>乳腺癌家族史</span>
						<div class="p12_img">
							<img src="<%=request.getContextPath()%>/images/wechat/rocky_report/未标题-1_03.png" />
						</div>
						<div>确凿证据表明，有乳腺癌家族史的女性，尤其是一级亲属患乳腺癌的女性，发生乳腺癌的风险升高。如果只有一名一级亲属患病，则风险翻倍；如果有两名一级亲属诊断患病，则风险增加4倍。</div>
					</div>
				</div>
				<!--------page13--------->
				<div class="main swiper-slide">
					  <img class="backImg13" src="<%=request.getContextPath()%>/images/wechat/rocky_report/12.Page 1.png" />
					<div class="p13-content">
						<span>主要遗传易感性</span>
						<div>确凿证据表明，有乳腺癌相关遗传基因突变的女性患病风险升高，取决于基因突变、家族史和其他影响基因表达的危险因素。</div>
						<span>乳腺密度</span>
						<div>乳腺密度大的女性发生乳腺癌的风险增加。这通常是一种固有的特征，但在一定程度上可因生育、药物和酒精而改变。</div>
						<span>其他可能的危险因素</span>
						<div class="p13-content-img">
							<img src="<%=request.getContextPath()%>/images/wechat/rocky_report/未标题-1_20.png" />
						</div>
					</div>
				</div>
				<!--------page14--------->
				 <div class="main swiper-slide">
                    <img class="backImg14" src="<%=request.getContextPath()%>/images/wechat/rocky_report/14.Page 1.png" />
                    <div class="p14-content">
                        <span><img src="<%=request.getContextPath()%>/images/wechat/rocky_report/14.wine.png">避免饮酒</span>
                        <div>饮酒女性患乳腺癌的风险较很少饮酒者高，这种风险在绝经前女性中最为显著。目前认为，酒精可刺激体内雌激素水平上升，与乳腺癌发生有关。因此,女性尤其是绝经前的女性，应戒酒或少饮酒。</div>
                        <span><img src="<%=request.getContextPath()%>/images/wechat/rocky_report/14.food.png">合理膳食</span>
                        <div>目前认为，膳食中的碳水化合物、脂肪会增加乳腺癌的发生风险,而水果蔬菜、叶酸、维生素D和膳食纤维能降低乳腺癌的发生风险。因此，平时应多吃一些蔬菜水果等含有维生素、膳食纤维的食品，预防乳腺癌的发生。</div>
                        <span><img src="<%=request.getContextPath()%>/images/wechat/rocky_report/14.exercise.png">积极参加体育锻炼，保持合适的体重</span>
                        <div>高体重是绝经后乳腺癌的危险因素，此时体内多余的脂肪会转变为雌激素，成为雌激素的主要来源，促进乳腺癌细胞的生长。要坚持体育锻炼，保持适当的体重，避免肥胖导致的乳腺癌风险增加。</div>
                        <span><img src="<%=request.getContextPath()%>/images/wechat/rocky_report/14.Smile.png	">保持心情舒畅，避免压力过大</span>
                        <div>长期心情不畅的女性是乳腺癌的高危人群，建议大家在日常生活中保持情绪稳定,避免精神刺激,适当给自己减压,保持心情舒畅及乐观的生活态度,避免和减少精神、心理紧张因素,可以减少乳腺癌的发生。</div>
						<span><img src="<%=request.getContextPath()%>/images/wechat/rocky_report/14.inspect.png">定期去正规医疗机构进行体检</span>
						<div>建议长期饮酒、心情不畅及乳腺增生、有乳腺癌家族史、携带相关基因有害突变的人群定期进行乳腺自检，同时去正规体检机构进行相关的检查，包括 X 线检查、糖类抗原 CA153、CEA、乳腺B超等，早监测、早发现，早诊断、早治疗。</div>
					</div>
				</div>
				<!--------page15--------->
				<div class="main swiper-slide">
					 <img class="backImg15" src="<%=request.getContextPath()%>/images/wechat/rocky_report/15.Page 1.png" />
					<div class="p15-content">
						<p><b>【1】</b><i>Holly Yan (2013-05-14). "What's the gene that led to Angelina Jolie's double mastectomy?". Health. CNN.</i></p>
						<p><b>【2】</b><i> "BRCA1 and BRCA2: Cancer Risk and Genetic Testing". National Cancer Institute. 29 May 2009.</i></p>
						<p><b>【3】</b><i>effrey N. Weitzel, Veronica I. Lagos, Carey A. Cullinane, Patricia J. Gambol, Julie O. Culver, Kathleen R. Blazer, Melanie R. Palomares, Katrina J. Lowstuter, Deborah J. MacDonald (2007). "Limited Family Structure and BRCA Gene Mutation Status in Single Cases of Breast Cancer". Journal of the American Medical Association 297 (23): 2587–2595. doi:10.1001/jama.297.23.2587</i></p>
						<p><b>【4】</b><i>Cui, J; Antoniou, AC; Dite, GS; Southey, MC; Venter, DJ; Easton, DF; Giles, GG; McCredie, MR; Hopper, JL (Feb 2001)."After BRCA1 and BRCA2-what next? Multifactorial segregation analyses of three-generation, population-based Australian families affected by female breast cancer". Am J Hum Genet 68 (2): 420–31. doi:10.1086/318187.PMC 1235275. PMID 11133358</i></p>
						<p><b>【5】</b><i>Morris, Joi L.; Gordon, Ora K. (2010). Positive Results: Making the Best Decisions When You're at High Risk for Breast or Ovarian Cancer. Amherst, N.Y.: Prometheus Books. ISBN 978-1-59102-776-8.</i></p>
						<p><b>【6】</b><i>Campeau PM, Foulkes WD, Tischkowitz MD. Hereditary breast cancer: New genetic developments, new therapeutic avenues. Human Genetics 2008; 124(1):31–42.</i></p>
						<p><b>【7】</b><i>Easton DF. How many more breast cancer predisposition genes are there? Breast Cancer Research 1999; 1(1):14–17.  [PubMed Abstract]</i></p>
						<p><b>【8】</b><i>Pal T, Permuth-Wey J, Betts JA, et al. BRCA1 and BRCA2 mutations account for a large proportion of ovarian carcinoma cases. Cancer 2005; 104(12):2807–16.</i></p>
						<p><b>【9】</b><i>Howlader N, Noone AM, Krapcho M, et al. (eds). SEER Cancer Statistics Review, 1975-2011, National Cancer Institute. Bethesda, MD, http://seer.cancer.gov/csr/1975_2011/, based on November 2013 SEER data submission, posted to the SEER web site, April 2014.</i></p>
						<p><b>【11】</b><i>Antoniou A, Pharoah PD, Narod S, et al. Average risks of breast and ovarian cancer associated with BRCA1 or BRCA2 mutations detected in case series unselected for family history: A combined analysis of 22 studies. American Journal of Human Genetics2003; 72(5):1117–1130.</i></p>
						<p><b>【12】</b><i>Chen S, Parmigiani G. Meta-analysis of BRCA1 and BRCA2 penetrance. Journal of Clinical Oncology 2007; 25(11):1329–1333.</i></p>
					</div>
				</div>
				<!--------page16--------->
				<div class="main swiper-slide">
					<img class="backImg16" src="<%=request.getContextPath()%>/images/wechat/rocky_report/16.Page 1.png" />
					<div class="p16-content">
						<p>本检测的意义是通过基因测序了解遗传背景下患有某癌症的风险情况，如能在疾病未发生之前改变生活方式，积极地干预、预防，可以延缓或者阻止该疾病的发生，建议在疾病未发生前按照健康建议养成良好的生活习惯。</p>
						<p>癌症遗传风险评估不等于临床诊断，其检测结果不能作为判断是否患有癌症的标准。即某癌种易感基因检测结果阳性或阴性，只代表受检者患病的风险较高或较低，而不代表其已经患有该癌症或不会患有该癌症。癌症遗传风险评估结果可作为临床医生诊断疾病时的参考资料。</p>
						<p>检测结果为高风险或较高风险时，提示您较他人更易罹患此类癌症，但并不代表必定患此癌症。受检测者依据检测结果所做出的民亊行为，由受检者自行承担一切法律后果。</p>
						<p>由于科技不断发展，世界范围内数以万计的科学家正在夜以继日地致力于基因与健康之间的研究，我们会保持跟进相关的研究进展，根据最新科研成果调整和丰富基因检测内容。本检测只对检测结果的当前正确性负责并承诺检测服务的准确率将保持在国际先进水平上。</p>
						<p>提供样本者应对受检者与样本的一致性负责。本结果报告仅对此次送检样品负责。</p>
					</div>
				</div>
				<!---------华点云page1------->
				<div class="main swiper-slide h_lastpage_hide">
					<div class="h_lastpage">
						<img src="<%=request.getContextPath()%>/images/wechat/rocky_report/last.png">

					</div>
				</div>
			</div>
			<!-- 如果需要分页器 -->
   			 <div class="swiper-pagination pages"></div>
		</div>
        <script src="//cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
        <script src="//cdn.bootcss.com/Swiper/3.4.1/js/swiper.min.js"></script>
        <script type="text/javascript">
		     window.CONTEXT_PATH = '<%=request.getContextPath()%>';
		</script>
        <script src="<%=request.getContextPath()%>/js/rote.js?v=4.1" type="text/javascript" charset="utf-8"></script>
    </body>
</html>