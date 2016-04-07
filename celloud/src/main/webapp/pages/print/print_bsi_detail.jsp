<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 优先使用最新版本的IE 和 Chrome 内核 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="baidu-site-verification" content="IsldTuHqik" />
<title>CelLoud-血流感染报告</title>
<meta name="keywords" content="上海华点云生物科技有限公司,celloud,生物信息云平台,生物大数据平台,序列数据分析,基因大数据" />
<meta name="description" content="一站式高通量基因检测数据分析系统">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="bookmark" href="<%=request.getContextPath()%>/images/favicon.ico" />
<link rel="shortcut icon" href="<%=request.getContextPath()%>/images/favicon.ico" />
<link href="<%=request.getContextPath() %>/css/print.css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/css/print_bsi.css" rel="stylesheet">
</head>
<body>
  <div class="buttons">
    <a id="save" class="btn btn-default" href="javascript:void(0)">分享</a>
    <a id="reset" class="btn btn-default" href="javascript:void(0)">下载</a>
    <a id="print" class="btn btn-default" href="javascript:void(0)">打印</a>
  </div>
  <div id="toc" class="tocify fixed"></div>
  <div class="title">
    <span class="title-text">细菌性血流感染检测报告--分析报告</span>
    <img class="title-logo" src="<%=request.getContextPath() %>/images/hospitalIcon/demo-logo.png"></div>
  </div>
  <form id="print-form">
  <div id="tocify-content">
  <section class="base-info">
    <h2>1. 样本信息</h2>
    <ul>
        <li><lable>姓名：</lable><span><input class="width-80" type="text" name="" value="患者姓名"></span></li>
        <li><lable>性别：</lable><span id="sex-text"></span><span id="sex"><input type="radio" name="sex" value="男">男<input type="radio" name="sex" value="女" >女<input type="radio" name="sex" value="不详" >不详</span></li>
        <li><lable>年龄：</lable><span><input class="width-80" type="text" name="" value="19"></span>岁</li>
        <li></li>
        <li><lable>科室：</lable>
           <span id="department-text"></span>
           <select id="department" class="width-80">
	          <option value="ICU病房">ICU病房</option>
	          <option value="呼吸科">呼吸科</option>
	          <option value="儿科">儿科</option>
	          <option value="感染科">感染科</option>
	          <option value="产科">产科</option>
	        </select>
        </li>
        <li><lable>住院号：</lable><span><input class="width-120" type="text" name="" value="83947536"></span></li>
        <li><lable>床号：</lable><span><input class="width-80" type="text" name="" value="ab0302"></span></li>
        <li><lable>ID号：</lable><span><input class="width-80" type="text" name="" value="az00888"></span></li>
        <li><lable>样本种类：</lable>
            <span id="sample-type-text"></span>
	        <select id="sample-type" class="width-80">
	          <option value="血液">血液</option>
	          <option value="尿">尿</option>
	          <option value="腹水">腹水</option>
	          <option value="阴道拭子">阴道拭子</option>
	          <option value="脑脊液">脑脊液</option>
	          <option value="分泌物">分泌物</option>
	          <option value="大便">大便</option>
	          <option value="鼻咽拭子">鼻咽拭子</option>
	        </select>
        </li>
        <li><lable>样本编号：</lable><span><input class="width-120" type="text" name="" value="88484"></span></li>
        <li><lable>送检医生：</lable><span><input class="width-80" type="text" name="" value="ab0302"></span></li>
        <li><lable>送检日期：</lable><span><input class="width-80" type="text" name=""></span></li>
    </ul>
  </section>
  <section class="break-page">
    <h2>2 检测结果</h2>
    <div class="tests-info">
       <p>牛链球菌</p>
       <p>葡萄球菌</p>
       <table class="table table-bordered table-condensed gray-table">
         <tr>
           <td rowspan="2">G+</td>
           <td class="positive"><span>[✔]</span>牛链球菌</td>
           <td class="positive"><span>[✔]</span>葡萄球菌</td>
           <td><span>[✘]</span>肺炎链球菌</td>
           <td><span>[✘]</span>粪肠球菌</td>
         </tr>
         <tr>
           <td><span>[✘]</span>屎肠球菌</td>
           <td><span>[✘]</span>难辨梭状芽孢杆菌</td>
           <td><span>[✘]</span>溃疡棒状杆菌</td>
           <td><span>[✘]</span>结膜干燥杆菌</td>
         </tr>
         <tr>
           <td rowspan="3">G-</td>
           <td><span>[✘]</span>大肠埃希菌</td>
           <td><span>[✘]</span>铜绿假单胞菌</td>
           <td><span>[✘]</span>鲍曼不动杆菌</td>
           <td><span>[✘]</span>肺炎克雷伯菌</td>
         </tr>
         <tr>
           <td><span>[✘]</span>嗜麦芽窄食单胞菌</td>
           <td><span>[✘]</span>粘质沙雷氏菌</td>
           <td><span>[✘]</span>阴沟肠杆菌</td>
           <td><span>[✘]</span>嗜水气单胞菌</td>
         </tr>
         <tr>
           <td><span>[✘]</span>产气肠杆菌</td>
           <td><span>[✘]</span>肠炎沙门菌</td>
           <td><span>[✘]</span>奇异变形杆菌</td>
           <td><span>[✘]</span>洋葱伯克霍尔德菌</td>
         </tr>
       </table>
       <table id="test-seq-table" class="table table-bordered table-condensed">
         <thead>
           <tr>
             <th style="max-width: 119px;">菌名(属-种)</th>
             <th width="10">#</th>
             <th width="380">序列 (5'-3')</th>
             <th name="opera-seq" width="53">操作</th>
             <th>其它信息</th>
           </tr>
	     </thead>
         <tbody>
	         <tr>
	           <td rowspan="4" style="text-align:center;">葡萄球菌</td>
	           <td>1</td>
	           <td>
	             <div id="seq1" class="seq">TCATGGAGAG&nbsp;&nbsp;TTCGATCCTG&nbsp;&nbsp;GCTCAGGATG&nbsp;&nbsp;AACGCTGGCG&nbsp;&nbsp;GCATGCTTAA&nbsp;&nbsp;CACATGCAAG&nbsp;&nbsp;TCGGACGGGG<br>AGTGGTGTTT&nbsp;&nbsp;CCAGTGGCGG&nbsp;&nbsp;ACGGGTGAGT&nbsp;&nbsp;ATACGCGTAA&nbsp;&nbsp;GAACCTGCCC&nbsp;&nbsp;TTGGGAGGGG&nbsp;&nbsp;AACAACAGCT&nbsp;&nbsp;<br>GGAAACGGCT&nbsp;&nbsp;GCTAATACCC&nbsp;&nbsp;CGTAGGCTGA&nbsp;&nbsp;GGAGCAAAAG&nbsp;&nbsp;GAGGAATCCG&nbsp;&nbsp;CCTGAGGAGG&nbsp;&nbsp;GGCTTGCGTC</div>
	           </td>
	           <td name="opera-seq"><span id="copy-seq1"></span></td>
	           <td></td>
	         </tr>
	         <tr>
	           <td>2</td>
	           <td>
                 <div id="seq2" class="seq">TCATGGAGAG&nbsp;&nbsp;TTCGATCCTG&nbsp;&nbsp;GCTCAGGATG&nbsp;&nbsp;AACGCTGGCG&nbsp;&nbsp;GCATGCTTAA&nbsp;&nbsp;CACATGCAAG&nbsp;&nbsp;TCGGACGGGG<br>AGTGGTGTTT&nbsp;&nbsp;CCAGTGGCGG&nbsp;&nbsp;ACGGGTGAGT&nbsp;&nbsp;ATACGCGTAA&nbsp;&nbsp;GAACCTGCCC&nbsp;&nbsp;TTGGGAGGGG&nbsp;&nbsp;AACAACAGCT&nbsp;&nbsp;<br>GGAAACGGCT&nbsp;&nbsp;GCTAATACCC&nbsp;&nbsp;CGTAGGCTGA&nbsp;&nbsp;GGAGCAAAAG&nbsp;&nbsp;GAGGAATCCG&nbsp;&nbsp;CCTGAGGAGG&nbsp;&nbsp;GGCTTGCGTC</div>
               </td>
               <td name="opera-seq"><span id="copy-seq2"></span></td>
               <td></td>
	         </tr>
	         <tr>
	           <td>3</td>
	           <td>
                 <div id="seq3" class="seq">TCATGGAGAG&nbsp;&nbsp;TTCGATCCTG&nbsp;&nbsp;GCTCAGGATG&nbsp;&nbsp;AACGCTGGCG&nbsp;&nbsp;GCATGCTTAA&nbsp;&nbsp;CACATGCAAG&nbsp;&nbsp;TCGGACGGGG<br>AGTGGTGTTT&nbsp;&nbsp;CCAGTGGCGG&nbsp;&nbsp;ACGGGTGAGT&nbsp;&nbsp;ATACGCGTAA&nbsp;&nbsp;GAACCTGCCC&nbsp;&nbsp;TTGGGAGGGG&nbsp;&nbsp;AACAACAGCT&nbsp;&nbsp;<br>GGAAACGGCT&nbsp;&nbsp;GCTAATACCC&nbsp;&nbsp;CGTAGGCTGA&nbsp;&nbsp;GGAGCAAAAG&nbsp;&nbsp;GAGGAATCCG&nbsp;&nbsp;CCTGAGGAGG&nbsp;&nbsp;GGCTTGCGTC</div>
               </td>
               <td name="opera-seq"><span id="copy-seq3"></span></td>
               <td></td>
	         </tr>
	         <tr>
	           <td>4</td>
	           <td>
                 <div id="seq4" class="seq">TCATGGAGAG&nbsp;&nbsp;TTCGATCCTG&nbsp;&nbsp;GCTCAGGATG&nbsp;&nbsp;AACGCTGGCG&nbsp;&nbsp;GCATGCTTAA&nbsp;&nbsp;CACATGCAAG&nbsp;&nbsp;TCGGACGGGG<br>AGTGGTGTTT&nbsp;&nbsp;CCAGTGGCGG&nbsp;&nbsp;ACGGGTGAGT&nbsp;&nbsp;ATACGCGTAA&nbsp;&nbsp;GAACCTGCCC&nbsp;&nbsp;TTGGGAGGGG&nbsp;&nbsp;AACAACAGCT&nbsp;&nbsp;<br>GGAAACGGCT&nbsp;&nbsp;GCTAATACCC&nbsp;&nbsp;CGTAGGCTGA&nbsp;&nbsp;GGAGCAAAAG&nbsp;&nbsp;GAGGAATCCG&nbsp;&nbsp;CCTGAGGAGG&nbsp;&nbsp;GGCTTGCGTC</div>
               </td>
               <td name="opera-seq"><span id="copy-seq4"></span></td>
	           <td></td>
	         </tr>
	         <tr>
	           <td rowspan="4" style="text-align:center;">牛链杆菌</td>
	           <td>1</td>
	           <td>
                 <div id="seq5" class="seq">TCATGGAGAG&nbsp;&nbsp;TTCGATCCTG&nbsp;&nbsp;GCTCAGGATG&nbsp;&nbsp;AACGCTGGCG&nbsp;&nbsp;GCATGCTTAA&nbsp;&nbsp;CACATGCAAG&nbsp;&nbsp;TCGGACGGGG<br>AGTGGTGTTT&nbsp;&nbsp;CCAGTGGCGG&nbsp;&nbsp;ACGGGTGAGT&nbsp;&nbsp;ATACGCGTAA&nbsp;&nbsp;GAACCTGCCC&nbsp;&nbsp;TTGGGAGGGG&nbsp;&nbsp;AACAACAGCT&nbsp;&nbsp;<br>GGAAACGGCT&nbsp;&nbsp;GCTAATACCC&nbsp;&nbsp;CGTAGGCTGA&nbsp;&nbsp;GGAGCAAAAG&nbsp;&nbsp;GAGGAATCCG&nbsp;&nbsp;CCTGAGGAGG&nbsp;&nbsp;GGCTTGCGTC</div>
               </td>
               <td name="opera-seq"><span id="copy-seq5"></span></td>
	           <td></td>
	         </tr>
	         <tr>
	           <td>2</td>
	           <td>
                 <div id="seq6" class="seq">TCATGGAGAG&nbsp;&nbsp;TTCGATCCTG&nbsp;&nbsp;GCTCAGGATG&nbsp;&nbsp;AACGCTGGCG&nbsp;&nbsp;GCATGCTTAA&nbsp;&nbsp;CACATGCAAG&nbsp;&nbsp;TCGGACGGGG<br>AGTGGTGTTT&nbsp;&nbsp;CCAGTGGCGG&nbsp;&nbsp;ACGGGTGAGT&nbsp;&nbsp;ATACGCGTAA&nbsp;&nbsp;GAACCTGCCC&nbsp;&nbsp;TTGGGAGGGG&nbsp;&nbsp;AACAACAGCT&nbsp;&nbsp;<br>GGAAACGGCT&nbsp;&nbsp;GCTAATACCC&nbsp;&nbsp;CGTAGGCTGA&nbsp;&nbsp;GGAGCAAAAG&nbsp;&nbsp;GAGGAATCCG&nbsp;&nbsp;CCTGAGGAGG&nbsp;&nbsp;GGCTTGCGTC</div>
               </td>
               <td name="opera-seq"><span id="copy-seq6"></span></td>
	           <td></td>
	         </tr>
	         <tr>
	           <td>3</td>
	           <td>
                 <div id="seq7" class="seq">TCATGGAGAG&nbsp;&nbsp;TTCGATCCTG&nbsp;&nbsp;GCTCAGGATG&nbsp;&nbsp;AACGCTGGCG&nbsp;&nbsp;GCATGCTTAA&nbsp;&nbsp;CACATGCAAG&nbsp;&nbsp;TCGGACGGGG<br>AGTGGTGTTT&nbsp;&nbsp;CCAGTGGCGG&nbsp;&nbsp;ACGGGTGAGT&nbsp;&nbsp;ATACGCGTAA&nbsp;&nbsp;GAACCTGCCC&nbsp;&nbsp;TTGGGAGGGG&nbsp;&nbsp;AACAACAGCT&nbsp;&nbsp;<br>GGAAACGGCT&nbsp;&nbsp;GCTAATACCC&nbsp;&nbsp;CGTAGGCTGA&nbsp;&nbsp;GGAGCAAAAG&nbsp;&nbsp;GAGGAATCCG&nbsp;&nbsp;CCTGAGGAGG&nbsp;&nbsp;GGCTTGCGTC</div>
               </td>
               <td name="opera-seq"><span id="copy-seq7"></span></td>
	           <td></td>
	         </tr>
	         <tr>
	           <td>4</td>
	           <td>
                 <div id="seq8" class="seq">TCATGGAGAG&nbsp;&nbsp;TTCGATCCTG&nbsp;&nbsp;GCTCAGGATG&nbsp;&nbsp;AACGCTGGCG&nbsp;&nbsp;GCATGCTTAA&nbsp;&nbsp;CACATGCAAG&nbsp;&nbsp;TCGGACGGGG<br>AGTGGTGTTT&nbsp;&nbsp;CCAGTGGCGG&nbsp;&nbsp;ACGGGTGAGT&nbsp;&nbsp;ATACGCGTAA&nbsp;&nbsp;GAACCTGCCC&nbsp;&nbsp;TTGGGAGGGG&nbsp;&nbsp;AACAACAGCT&nbsp;&nbsp;<br>GGAAACGGCT&nbsp;&nbsp;GCTAATACCC&nbsp;&nbsp;CGTAGGCTGA&nbsp;&nbsp;GGAGCAAAAG&nbsp;&nbsp;GAGGAATCCG&nbsp;&nbsp;CCTGAGGAGG&nbsp;&nbsp;GGCTTGCGTC</div>
               </td>
               <td name="opera-seq"><span id="copy-seq8"></span></td>
	           <td></td>
	         </tr>
	     </tbody>
       </table>
       <div class="test-description"> 快速序列验证及比对(NCBI Blast): <a href="http://www.ncbi.nlm.nih.gov/genomes/16S/search16S.html">http://www.ncbi.nlm.nih.gov/genomes/16S/search16S.html</a></div>
    </div>
  </section>
  <section>
    <h2>3. 其它可能病原菌</h2>
    <div class="tests-info other-info">
      <table id="other-seq-table" class="table table-bordered table-condensed seq-table">
        <thead>
	      <tr>
            <th style="min-width: 90px;">菌名(属-种)</th>
            <th>#</th>
            <th width="380">序列 (5'-3')</th>
            <th name="opera-seq" width="53">操作</th>
            <th>参考信息</th>
          </tr>
         </thead>
         <tbody>
	         <tr>
	           <td rowspan="2" style="text-align:center;">Aeribacillus<br>pallidus</td>
	           <td>1</td>
	           <td>
                 <div id="seq9" class="seq">TCATGGAGAG&nbsp;&nbsp;TTCGATCCTG&nbsp;&nbsp;GCTCAGGATG&nbsp;&nbsp;AACGCTGGCG&nbsp;&nbsp;GCATGCTTAA&nbsp;&nbsp;CACATGCAAG&nbsp;&nbsp;TCGGACGGGG<br>AGTGGTGTTT&nbsp;&nbsp;CCAGTGGCGG&nbsp;&nbsp;ACGGGTGAGT&nbsp;&nbsp;ATACGCGTAA&nbsp;&nbsp;GAACCTGCCC&nbsp;&nbsp;TTGGGAGGGG&nbsp;&nbsp;AACAACAGCT&nbsp;&nbsp;<br>GGAAACGGCT&nbsp;&nbsp;GCTAATACCC&nbsp;&nbsp;CGTAGGCTGA&nbsp;&nbsp;GGAGCAAAAG&nbsp;&nbsp;GAGGAATCCG&nbsp;&nbsp;CCTGAGGAGG&nbsp;&nbsp;GGCTTGCGTC</div>
               </td>
               <td name="opera-seq"><span id="copy-seq9"></span></td>
	           <td rowspan="2">[1] 夏鲁惠.医院感染微生物16S分型与分析[J].临床医学教育，2004(1):46-52.</td>
	         </tr>
	         <tr>
	           <td>2</td>
	           <td>
                 <div id="seq10" class="seq">TCATGGAGAG&nbsp;&nbsp;TTCGATCCTG&nbsp;&nbsp;GCTCAGGATG&nbsp;&nbsp;AACGCTGGCG&nbsp;&nbsp;GCATGCTTAA&nbsp;&nbsp;CACATGCAAG&nbsp;&nbsp;TCGGACGGGG<br>AGTGGTGTTT&nbsp;&nbsp;CCAGTGGCGG&nbsp;&nbsp;ACGGGTGAGT&nbsp;&nbsp;ATACGCGTAA&nbsp;&nbsp;GAACCTGCCC&nbsp;&nbsp;TTGGGAGGGG&nbsp;&nbsp;AACAACAGCT&nbsp;&nbsp;<br>GGAAACGGCT&nbsp;&nbsp;GCTAATACCC&nbsp;&nbsp;CGTAGGCTGA&nbsp;&nbsp;GGAGCAAAAG&nbsp;&nbsp;GAGGAATCCG&nbsp;&nbsp;CCTGAGGAGG&nbsp;&nbsp;GGCTTGCGTC</div>
               </td>
               <td name="opera-seq"><span id="copy-seq10"></span></td>
	         </tr>
	         <tr>
	           <td rowspan="2" style="text-align:center;">Bacillus<br>alveayuensis</td>
	           <td>1</td>
	           <td>
                 <div id="seq11" class="seq">TCATGGAGAG&nbsp;&nbsp;TTCGATCCTG&nbsp;&nbsp;GCTCAGGATG&nbsp;&nbsp;AACGCTGGCG&nbsp;&nbsp;GCATGCTTAA&nbsp;&nbsp;CACATGCAAG&nbsp;&nbsp;TCGGACGGGG<br>AGTGGTGTTT&nbsp;&nbsp;CCAGTGGCGG&nbsp;&nbsp;ACGGGTGAGT&nbsp;&nbsp;ATACGCGTAA&nbsp;&nbsp;GAACCTGCCC&nbsp;&nbsp;TTGGGAGGGG&nbsp;&nbsp;AACAACAGCT&nbsp;&nbsp;<br>GGAAACGGCT&nbsp;&nbsp;GCTAATACCC&nbsp;&nbsp;CGTAGGCTGA&nbsp;&nbsp;GGAGCAAAAG&nbsp;&nbsp;GAGGAATCCG&nbsp;&nbsp;CCTGAGGAGG&nbsp;&nbsp;GGCTTGCGTC</div>
               </td>
               <td name="opera-seq"><span id="copy-seq11"></span></td>
	           <td rowspan="2">[2]辛希孟.传染病学论文集:A集[C].北京:中国社会科学出版社，2014.</td>
	         </tr>
	         <tr>
	           <td>2</td>
	           <td>
                 <div id="seq12" class="seq">TCATGGAGAG&nbsp;&nbsp;TTCGATCCTG&nbsp;&nbsp;GCTCAGGATG&nbsp;&nbsp;AACGCTGGCG&nbsp;&nbsp;GCATGCTTAA&nbsp;&nbsp;CACATGCAAG&nbsp;&nbsp;TCGGACGGGG<br>AGTGGTGTTT&nbsp;&nbsp;CCAGTGGCGG&nbsp;&nbsp;ACGGGTGAGT&nbsp;&nbsp;ATACGCGTAA&nbsp;&nbsp;GAACCTGCCC&nbsp;&nbsp;TTGGGAGGGG&nbsp;&nbsp;AACAACAGCT&nbsp;&nbsp;<br>GGAAACGGCT&nbsp;&nbsp;GCTAATACCC&nbsp;&nbsp;CGTAGGCTGA&nbsp;&nbsp;GGAGCAAAAG&nbsp;&nbsp;GAGGAATCCG&nbsp;&nbsp;CCTGAGGAGG&nbsp;&nbsp;GGCTTGCGTC</div>
               </td>
               <td name="opera-seq"><span id="copy-seq12"></span></td>
	         </tr>
	     </tbody>
       </table>
    </div>
  </section>
  <section class="break-page">
    <h2>4. 统计信息</h2>
    <table class="table table-bordered table-condensed table-text-center">
        <tr>
          <td>序列总数</td>
          <td>平均质量</td>
          <td>平均GC含量</td>
        </tr>
        <tr>
          <td>596678</td>
          <td>33.1 </td>
          <td>55%</td>
        </tr>
    </table>
    <table>
        <tr>
          <td style="width:33%"><img style="width:100%" src="<%=request.getContextPath() %>/images/print/bsi_1.jpg"></td>
          <td style="width:33%"><img style="width:100%" src="<%=request.getContextPath() %>/images/print/bsi_1.jpg"></td>
          <td style="width:33%"><img style="width:100%" src="<%=request.getContextPath() %>/images/print/bsi_1.jpg"></td>
        </tr>
        <tr>
           <td colspan="3"><img style="width:100%" src="<%=request.getContextPath() %>/images/print/bsi_2.jpg"></td>
        </tr>
    </table>
  </section>
  <section>
    <h2>5. 附件</h2>
    <h3>5.1 生物信息数据分析</h3>
    <table class="table table-bordered table-condensed table-text-center">
        <tr>
          <td>种</td>
          <td>属</td>
          <td>GI号</td>
          <td>覆盖长度%</td>
          <td>种比对上的序列数</td>
          <td>属比对上的序列数</td>
          <td>种序列百分比%</td>
          <td>平均覆盖深度</td>
        </tr>
        <tr>
          <td>Halomonas mongoliensis</td>
          <td>Halomonas </td>
          <td>343198694</td>
          <td>42.00</td>
          <td>23825</td>
          <td>46170</td>
          <td>26.00</td>
          <td>3744.51</td>
        </tr>
        <tr>
          <td>Genus</td>
          <td>Bacillus </td>
          <td>343202629</td>
          <td>23.00</td>
          <td>8760</td>
          <td>9566</td>
          <td>5.00</td>
          <td>708.29</td>
        </tr>
    </table>
    <h3>5.2 16S rRNA序列覆盖分布图</h3>
    <table>
        <tr>
          <td style="width:49%"><img style="width:100%" src="<%=request.getContextPath() %>/images/print/bsi_3.jpg"></td>
          <td style="width:49%"><img style="width:100%" src="<%=request.getContextPath() %>/images/print/bsi_3.jpg"></td>
        </tr>
        <tr>
          <td style="width:49%"><img style="width:100%" src="<%=request.getContextPath() %>/images/print/bsi_3.jpg"></td>
          <td style="width:49%"><img style="width:100%" src="<%=request.getContextPath() %>/images/print/bsi_3.jpg"></td>
        </tr>
        <tr>
          <td style="width:49%"><img style="width:100%" src="<%=request.getContextPath() %>/images/print/bsi_3.jpg"></td>
          <td style="width:49%"><img style="width:100%" src="<%=request.getContextPath() %>/images/print/bsi_3.jpg"></td>
        </tr>
        <tr>
          <td style="width:49%"><img style="width:100%" src="<%=request.getContextPath() %>/images/print/bsi_3.jpg"></td>
          <td style="width:49%"><img style="width:100%" src="<%=request.getContextPath() %>/images/print/bsi_3.jpg"></td>
        </tr>
        <tr>
          <td style="width:49%"><img style="width:100%" src="<%=request.getContextPath() %>/images/print/bsi_3.jpg"></td>
          <td style="width:49%"><img style="width:100%" src="<%=request.getContextPath() %>/images/print/bsi_3.jpg"></td>
        </tr>
    </table>
    <h3>5.3 序列质量分析（QC）</h3>
    <table class="table table-bordered table-condensed table-text-center">
        <tr>
          <td>Column 1</td>
          <td>Column 2</td>
          <td>Column 3</td>
        </tr>
        <tr>
          <td>&nbsp;&nbsp;</td>
          <td></td>
          <td></td>
        </tr>
        <tr>
          <td>&nbsp;&nbsp;</td>
          <td></td>
          <td></td>
        </tr>
    </table>
    <table>
        <tr>
          <td><img style="width:100%" src="<%=request.getContextPath() %>/images/print/bsi_4.jpg"></td>
        </tr>
    </table>
  </section>
  </div>
  </form>
  <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
  <script src="//cdn.bootcss.com/jqueryui/1.11.4/jquery-ui.min.js"></script>
  <script src="//cdn.bootcss.com/scrollmonitor/1.0.12/scrollMonitor.min.js"></script>
  <script src="//cdn.bootcss.com/jquery.tocify/1.9.0/javascripts/jquery.tocify.min.js"></script>
  <script src="//cdn.bootcss.com/swfobject/2.2/swfobject.min.js"></script>
  <script type="text/javascript">
    $(document).ready(function(){
      var params = {
          wmode: "transparent",
          allowScriptAccess: "always"
      };
      var flashBtnUri = '<%=request.getContextPath() %>/images/icon/copy_seq_btn.png';
      for(var i = 1;i<=12; i++){
	      var flashvars1 = {
	          content: encodeURIComponent($("#seq"+i).text()),
	          uri: flashBtnUri
	      };
	      swfobject.embedSWF("<%=request.getContextPath() %>/plugins/clipboard.swf", 'copy-seq'+i, "52", "25", "9.0.0", null, flashvars1, params);
      }
      
      $("#print").on("click",function(){
        $(".base-info").find("input[type='text']").each(function(){
           $(this).parent().html("<input type='hidden' value='"+$(this).attr("class")+"'><span name='print-input'>"+$(this).val()+"</span>");
        });
        $("#department-text").text($("#department").val());
        $("#department").addClass("hide");
        $("#sample-type-text").text($("#sample-type").val());
        $("#sample-type").addClass("hide");
        $("#sex-text").html($("input[type='radio'][name='sex']:checked").val());
        $("#sex").addClass("hide");
        $(".buttons").hide();
        $("#toc").hide();
        $("th[name='opera-seq']").addClass("hide");
        $("td[name='opera-seq']").addClass("hide");
        window.print();
        $("th[name='opera-seq']").removeClass("hide");
        $("td[name='opera-seq']").removeClass("hide");
        $("#department-text").text("");
        $("#department").removeClass("hide");
        $("#sample-type-text").text("");
        $("#sample-type").removeClass("hide");
        $("#sex-text").html("");
        $("#sex").removeClass("hide");
        $(".buttons").show();
        $("#toc").show();
        $("span[name='print-input']").each(function(){
            $(this).parent().html("<input class='"+$(this).prev().val()+"' type='text' value='"+$(this).html()+"'>");
        });
      });
      // Tocify Table
      if($.isFunction($.fn.tocify) && $("#toc").length){
          $("#toc").tocify({ 
              context: '#tocify-content', 
              selectors: "h2,h3" 
          });
          var $this = $(".tocify"),
              watcher = scrollMonitor.create($this.get(0));
          watcher.lock();
      }
    });
  </script>
</body>
</html>