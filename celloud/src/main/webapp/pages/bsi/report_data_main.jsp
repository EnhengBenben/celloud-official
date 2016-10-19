<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="content-header">
  <ol class="breadcrumb">
    <li>主页</li>
    <li>应用</li>
    <li>百菌探</li>
    <li>报告</li>
    <li>
      <c:forEach items="${bsi.data}" var="data">
          ${data.fileName}&nbsp;&nbsp;&nbsp;
      </c:forEach>
    </li>
  </ol>
</div>
<div class="content">
  <ul id="myTabs" class="nav nav-tabs" role="tablist">
    <li role="presentation" class="active"><a href="javascript:void(0)" id="patient-tab">患者报告</a></li>
    <li role="presentation"><a href="javascript:void(0)" id="analy-tab">分析报告</a></li>
    <li class="nav-pub">
      <div class="pull-right">
        <c:if test="${pageList.datas.size()>0}">
          <input id="total-page-hide" value="${pageList.page.totalPage }" type="hidden" >
          <a class="prev-btn" href="javascript:$.report.detail.prev(${pageList.page.currentPage })"><i class="fa fa-chevron-circle-left"></i>上一份</a>
          <a class="next-btn" href="javascript:$.report.detail.next(${pageList.page.currentPage })">下一份<i class="fa fa-chevron-circle-right"></i></a>
        </c:if>
      </div>
    </li>
  </ul>
  <div class="report-btn-group pull-right">
    <a class="btn" id="print-patient-a" href="<%=request.getContextPath()%>/report/printBSIReport?projectId=${bsi.projectId }&dataKey=${bsi.dataKey }&appId=${bsi.appId }&templateType=print_patient" target="_blank"><i class="fa fa-print"></i>打印</a>
    <a class="btn hide" id="print-analy-a" href="<%=request.getContextPath()%>/report/printBSIReport?projectId=${bsi.projectId }&dataKey=${bsi.dataKey }&appId=${bsi.appId }&templateType=print_analy" target="_blank"><i class="fa fa-print"></i>打印</a>
  </div>
  
  <div id="myTabContent" class="tab-content pull-left">
    <div id="tab-temp" style="" class="tab-temp hide"></div>
    <div role="tabpanel" class="tab-pane active in tab-absolute" id="patient-report" aria-labelledby="patient-tab">
      <h4>检测结果：</h4>
	  <table class="table table-main">
	    <thead>
		    <tr>
		      <th colspan="5">
		        <c:if test="${not empty bsi.species_20}">检测到
		          <c:forEach items="${bsi.species_20 }" var="species20">
		            <c:choose>
		              <c:when test='${species20.species_zh.equals("未知") || empty species20.species_zh}'>species20.species&nbsp;</c:when>
		              <c:otherwise>${species20.species_zh }
		                <c:choose>
		                  <c:when test="${empty havestrain}">
		                    <c:set var="havestrain" scope="page" value="${species20.species_zh }"/>
		                  </c:when>
		                  <c:otherwise>
		                    <c:set var="havestrain" scope="page" value="${havestrain },${species20.species_zh }"/>
		                  </c:otherwise>
		                </c:choose>
		              </c:otherwise>
		            </c:choose>
		          </c:forEach>
		        </c:if>
		      </td>
		    </th>
	    </thead>
	    <tbody>
	    <tr>
	      <td>G+</td>
	      <td>
	        <i class="fa <c:choose><c:when test="${havestrain.contains('牛链球菌') }">fa-circle</c:when><c:otherwise>fa-circle-o</c:otherwise></c:choose>" aria-hidden="true"></i>
	                       牛链球菌
	      </td>
	      <td>
	        <i class="fa <c:choose><c:when test="${havestrain.contains('葡萄球菌') }">fa-circle</c:when><c:otherwise>fa-circle-o</c:otherwise></c:choose>" aria-hidden="true"></i>
	                      葡萄球菌
	      </td>
	      <td>
	        <i class="fa <c:choose><c:when test="${havestrain.contains('肺炎链球菌') }">fa-circle</c:when><c:otherwise>fa-circle-o</c:otherwise></c:choose>" aria-hidden="true"></i>
	                      肺炎链球菌
	      </td>
	      <td>
			<i class="fa <c:choose><c:when test="${havestrain.contains('粪肠球菌') }">fa-circle</c:when><c:otherwise>fa-circle-o</c:otherwise></c:choose>" aria-hidden="true"></i>
			          粪肠球菌
		  </td>
	    </tr>
	    <tr>
	      <td></td>
	       <td>
	         <i class="fa <c:choose><c:when test="${havestrain.contains('屎肠球菌') }">fa-circle</c:when><c:otherwise>fa-circle-o</c:otherwise></c:choose>" aria-hidden="true"></i>
	                       屎肠球菌
	       </td>
	       <td>
	         <i class="fa <c:choose><c:when test="${havestrain.contains('难辨梭状芽孢杆菌') }">fa-circle</c:when><c:otherwise>fa-circle-o</c:otherwise></c:choose>" aria-hidden="true"></i>
	                       难辨梭状芽孢杆菌
	       </td>
	       <td>
	         <i class="fa <c:choose><c:when test="${havestrain.contains('溃疡棒状杆菌') }">fa-circle</c:when><c:otherwise>fa-circle-o</c:otherwise></c:choose>" aria-hidden="true"></i>
	                      溃疡棒状杆菌
	       </td>
	       <td>
	         <i class="fa <c:choose><c:when test="${havestrain.contains('结膜干燥杆菌') }">fa-circle</c:when><c:otherwise>fa-circle-o</c:otherwise></c:choose>" aria-hidden="true"></i>
	                       结膜干燥杆菌
	       </td>
	     </tr>
	     <tr>
	       <td>G-</td>
	       <td>
	         <i class="fa <c:choose><c:when test="${havestrain.contains('大肠埃希菌') }">fa-circle</c:when><c:otherwise>fa-circle-o</c:otherwise></c:choose>" aria-hidden="true"></i>
	                       大肠埃希菌
	       </td>
	       <td>
	         <i class="fa <c:choose><c:when test="${havestrain.contains('铜绿假单胞菌') }">fa-circle</c:when><c:otherwise>fa-circle-o</c:otherwise></c:choose>" aria-hidden="true"></i>
	                      铜绿假单胞菌
	       </td>
	       <td>
	         <i class="fa <c:choose><c:when test="${havestrain.contains('鲍曼不动杆菌') }">fa-circle</c:when><c:otherwise>fa-circle-o</c:otherwise></c:choose>" aria-hidden="true"></i>
	                       鲍曼不动杆菌
	       </td>
	       <td>
	         <i class="fa <c:choose><c:when test="${havestrain.contains('肺炎克雷伯氏菌') }">fa-circle</c:when><c:otherwise>fa-circle-o</c:otherwise></c:choose>" aria-hidden="true"></i>
	                       肺炎克雷伯氏菌
	       </td>
	     </tr>
	     <tr>
	       <td></td>
	       <td>
	         <i class="fa <c:choose><c:when test="${havestrain.contains('嗜麦芽窄食单胞菌') }">fa-circle</c:when><c:otherwise>fa-circle-o</c:otherwise></c:choose>" aria-hidden="true"></i>
	                       嗜麦芽窄食单胞菌
	       </td>
	       <td>
	         <i class="fa <c:choose><c:when test="${havestrain.contains('粘质沙雷氏菌') }">fa-circle</c:when><c:otherwise>fa-circle-o</c:otherwise></c:choose>" aria-hidden="true"></i>
	                       粘质沙雷氏菌
	       </td>
	       <td>
	         <i class="fa <c:choose><c:when test="${havestrain.contains('阴沟肠杆菌') }">fa-circle</c:when><c:otherwise>fa-circle-o</c:otherwise></c:choose>" aria-hidden="true"></i>
	                      阴沟肠杆菌
	       </td>
	       <td>
	         <i class="fa <c:choose><c:when test="${havestrain.contains('嗜水气单胞菌') }">fa-circle</c:when><c:otherwise>fa-circle-o</c:otherwise></c:choose>" aria-hidden="true"></i>
	                       嗜水气单胞菌
	       </td>
	     </tr>
	     <tr>
	       <td></td>
	       <td>
	         <i class="fa <c:choose><c:when test="${havestrain.contains('产气肠杆菌') }">fa-circle</c:when><c:otherwise>fa-circle-o</c:otherwise></c:choose>" aria-hidden="true"></i>
	                       产气肠杆菌
	       </td>
	       <td>
	         <i class="fa <c:choose><c:when test="${havestrain.contains('肠炎沙门菌') }">fa-circle</c:when><c:otherwise>fa-circle-o</c:otherwise></c:choose>" aria-hidden="true"></i>
	                       肠炎沙门菌
	       </td>
	       <td>
	         <i class="fa <c:choose><c:when test="${havestrain.contains('奇异变形杆菌') }">fa-circle</c:when><c:otherwise>fa-circle-o</c:otherwise></c:choose>" aria-hidden="true"></i>
	                      奇异变形杆菌
	       </td>
	       <td>
	         <i class="fa <c:choose><c:when test="${havestrain.contains('洋葱伯克霍尔德菌') }">fa-circle</c:when><c:otherwise>fa-circle-o</c:otherwise></c:choose>" aria-hidden="true"></i>
	                       洋葱伯克霍尔德菌
	       </td>
	     </tr>
	     </tbody>
	   </table>
	  <h5>检测范围(20种)：</h5>
      <p>牛链球菌、葡萄球菌、肺炎链球菌、粪肠球菌、屎肠球菌、难辨梭状芽孢杆菌、溃疡棒状杆菌、结膜干燥杆菌、大肠埃希菌、铜绿假单胞菌、
    鲍曼不动杆菌、肺炎克雷伯氏菌、嗜麦芽窄食单胞菌、粘质沙雷氏菌、阴沟肠杆菌、嗜水气单胞菌、产气肠杆菌、肠炎沙门菌、奇异变形杆菌、洋葱伯克霍尔德菌。
         <br>此20种以外检测为阴性不代表没有
      </p>
      <h5>检测方法：<span>基于16S rDNA高通量测序方法</span></h5>
	</div>
    <div role="tabpanel" class="tab-pane" id="analy-report" aria-labelledby="analy-tab">
	  <h4>1. 检测结果：</h4>
	  <table class="table table-main">
	    <thead>
	     <tr>
	       <th colspan="5">
	         <c:if test="${not empty bsi.species_20}">
	           检测到
	           <c:forEach items="${bsi.species_20 }" var="species20">
	             <c:choose>
	               <c:when test='${species20.species_zh.equals("未知") || species20.species_zh.equals("") || empty species20.species_zh}'>species20.species&nbsp;</c:when>
	               <c:otherwise>${species20.species_zh }
	                 <c:choose>
	                    <c:when test="${empty havestrain}">
	                      <c:set var="havestrain" scope="page" value="${species20.species_zh }"/>
	                    </c:when>
	                    <c:otherwise>
	                      <c:set var="havestrain" scope="page" value="${havestrain },${species20.species_zh }"/>
	                    </c:otherwise>
	                  </c:choose>
	               </c:otherwise>
	             </c:choose>
	           </c:forEach>
	         </c:if>
	       </th>
	     </tr>
	    </thead>
	    <tbody>
	     <tr>
	       <td>G+</td>
	       <td>
	         <i class="fa <c:choose><c:when test="${havestrain.contains('牛链球菌') }">fa-circle</c:when><c:otherwise>fa-circle-o</c:otherwise></c:choose>" aria-hidden="true"></i>
	                       牛链球菌
	       </td>
	       <td>
	         <i class="fa <c:choose><c:when test="${havestrain.contains('葡萄球菌') }">fa-circle</c:when><c:otherwise>fa-circle-o</c:otherwise></c:choose>" aria-hidden="true"></i>
	                       葡萄球菌
	       </td>
	       <td>
	         <i class="fa <c:choose><c:when test="${havestrain.contains('肺炎链球菌') }">fa-circle</c:when><c:otherwise>fa-circle-o</c:otherwise></c:choose>" aria-hidden="true"></i>
	                       肺炎链球菌
	       </td>
	       <td>
	         <i class="fa <c:choose><c:when test="${havestrain.contains('粪肠球菌') }">fa-circle</c:when><c:otherwise>fa-circle-o</c:otherwise></c:choose>" aria-hidden="true"></i>
	                       粪肠球菌
	       </td>
	     </tr>
	     <tr>
	       <td></td>
	       <td>
	         <i class="fa <c:choose><c:when test="${havestrain.contains('屎肠球菌') }">fa-circle</c:when><c:otherwise>fa-circle-o</c:otherwise></c:choose>" aria-hidden="true"></i>
	                       屎肠球菌
	       </td>
	       <td>
	         <i class="fa <c:choose><c:when test="${havestrain.contains('难辨梭状芽孢杆菌') }">fa-circle</c:when><c:otherwise>fa-circle-o</c:otherwise></c:choose>" aria-hidden="true"></i>
	                       难辨梭状芽孢杆菌
	       </td>
	       <td>
	         <i class="fa <c:choose><c:when test="${havestrain.contains('溃疡棒状杆菌') }">fa-circle</c:when><c:otherwise>fa-circle-o</c:otherwise></c:choose>" aria-hidden="true"></i>
	                      溃疡棒状杆菌
	       </td>
	       <td>
	         <i class="fa <c:choose><c:when test="${havestrain.contains('结膜干燥杆菌') }">fa-circle</c:when><c:otherwise>fa-circle-o</c:otherwise></c:choose>" aria-hidden="true"></i>
	                       结膜干燥杆菌
	       </td>
	     </tr>
	     <tr>
	       <td>G-</td>
	       <td>
	         <i class="fa <c:choose><c:when test="${havestrain.contains('大肠埃希菌') }">fa-circle</c:when><c:otherwise>fa-circle-o</c:otherwise></c:choose>" aria-hidden="true"></i>
	                       大肠埃希菌
	       </td>
	       <td>
	         <i class="fa <c:choose><c:when test="${havestrain.contains('铜绿假单胞菌') }">fa-circle</c:when><c:otherwise>fa-circle-o</c:otherwise></c:choose>" aria-hidden="true"></i>
	                      铜绿假单胞菌
	       </td>
	       <td>
	         <i class="fa <c:choose><c:when test="${havestrain.contains('鲍曼不动杆菌') }">fa-circle</c:when><c:otherwise>fa-circle-o</c:otherwise></c:choose>" aria-hidden="true"></i>
	                       鲍曼不动杆菌
	       </td>
	       <td>
	         <i class="fa <c:choose><c:when test="${havestrain.contains('肺炎克雷伯氏菌') }">fa-circle</c:when><c:otherwise>fa-circle-o</c:otherwise></c:choose>" aria-hidden="true"></i>
	                       肺炎克雷伯氏菌
	       </td>
	     </tr>
	     <tr>
	       <td></td>
	       <td>
	         <i class="fa <c:choose><c:when test="${havestrain.contains('嗜麦芽窄食单胞菌') }">fa-circle</c:when><c:otherwise>fa-circle-o</c:otherwise></c:choose>" aria-hidden="true"></i>
	                       嗜麦芽窄食单胞菌
	       </td>
	       <td>
	         <i class="fa <c:choose><c:when test="${havestrain.contains('粘质沙雷氏菌') }">fa-circle</c:when><c:otherwise>fa-circle-o</c:otherwise></c:choose>" aria-hidden="true"></i>
	                       粘质沙雷氏菌
	       </td>
	       <td>
	         <i class="fa <c:choose><c:when test="${havestrain.contains('阴沟肠杆菌') }">fa-circle</c:when><c:otherwise>fa-circle-o</c:otherwise></c:choose>" aria-hidden="true"></i>
	                      阴沟肠杆菌
	       </td>
	       <td>
	         <i class="fa <c:choose><c:when test="${havestrain.contains('嗜水气单胞菌') }">fa-circle</c:when><c:otherwise>fa-circle-o</c:otherwise></c:choose>" aria-hidden="true"></i>
	                       嗜水气单胞菌
	       </td>
	     </tr>
	     <tr>
	       <td></td>
	       <td>
	         <i class="fa <c:choose><c:when test="${havestrain.contains('产气肠杆菌') }">fa-circle</c:when><c:otherwise>fa-circle-o</c:otherwise></c:choose>" aria-hidden="true"></i>
	                       产气肠杆菌
	       </td>
	       <td>
	         <i class="fa <c:choose><c:when test="${havestrain.contains('肠炎沙门菌') }">fa-circle</c:when><c:otherwise>fa-circle-o</c:otherwise></c:choose>" aria-hidden="true"></i>
	                       肠炎沙门菌
	       </td>
	       <td>
	         <i class="fa <c:choose><c:when test="${havestrain.contains('奇异变形杆菌') }">fa-circle</c:when><c:otherwise>fa-circle-o</c:otherwise></c:choose>" aria-hidden="true"></i>
	                      奇异变形杆菌
	       </td>
	       <td>
	         <i class="fa <c:choose><c:when test="${havestrain.contains('洋葱伯克霍尔德菌') }">fa-circle</c:when><c:otherwise>fa-circle-o</c:otherwise></c:choose>" aria-hidden="true"></i>
	                       洋葱伯克霍尔德菌
	       </td>
	     </tr>
	    </tbody>
	  </table>
	  <table id="test-seq-table" class="table table-main seq-table">
	    <thead>
	      <tr>
	        <th style="max-width: 119px;">菌名(属-种)</th>
	        <th width="85">唯一识别<br>序列数</th>
	        <th class="no">#</th>
	        <th width="380">序列 (5'-3')</th>
	        <th name="opera-seq" width="53">操作</th>
	        <th>覆盖交叉菌</th>
	      </tr>
	    </thead>
	    <tbody>
	      <c:set var="seqNum" scope="page" value="0"/>
	      <c:if test="${not empty bsi.species_20}">
	         <c:forEach items="${bsi.species_20 }" var="species20">
	           <tr>
	             <td style="text-align:center;">
	               <c:choose>
	               <c:when test='${species20.species_zh.equals("未知") || empty species20.species_zh}'>species20.species&nbsp;</c:when>
	               <c:otherwise>${species20.species_zh }
	                 <c:choose>
	                    <c:when test="${empty havestrain}">
	                      <c:set var="havestrain" scope="page" value="${species20.species_zh }"/>
	                    </c:when>
	                    <c:otherwise>
	                      <c:set var="havestrain" scope="page" value="${havestrain },${species20.species_zh }"/>
	                    </c:otherwise>
	                  </c:choose>
	               </c:otherwise>
	               </c:choose>
	               <br>
	               <svg width="139.9" height="8">
	                 <rect x="0" y="0" width="139.9" height="8" fill="#fff" style="stroke:#B0DF2B;fill-opacity:0.1;stroke-opacity:0.9"/>
	                 <c:if test="${species20.site1!='0,0'}">
	                 <rect x="0" y="0" width="34.9" height="8" fill="#B0DF2B" style="fill-opacity:0.4;stroke-opacity:0.9"/>
	                 </c:if>
	                 <c:if test="${species20.site2!='0,0'}">
	                 <rect x="33.1" y="0" width="46.3" height="8" fill="#17BABF" style="fill-opacity:0.4;stroke-opacity:0.9"/>
	                 </c:if>
	                 <c:if test="${species20.site3!='0,0'}">
	                 <rect x="77.6" y="0" width="31.5" height="8" fill="#FEAA20" style="fill-opacity:0.4;stroke-opacity:0.9"/>
	                 </c:if>
	                 <c:if test="${species20.site4!='0,0'}">
	                 <rect x="105.6" y="0" width="34.3" height="8" fill="#AE98DA" style="fill-opacity:0.4;stroke-opacity:0.9"/>
	                 </c:if>
	               </svg>
	             </td>
	             <td style="text-align:center;">${species20.unique_reads_num }</td>
	             <td class="no">1</td>
	             <td>
	               <div class="seq">${species20.seq1 }</div>
	             </td>
	             <td name="opera-seq"><span id="copy-seq${seqNum }<c:set var="seqNum" scope="page" value="${seqNum+1 }"/>"></span></td>
	             <td style="text-align:center;">
	               <c:choose>
	                 <c:when test="${empty species20.cross_species }">无</c:when>
	                 <c:otherwise>${species20.cross_species }</c:otherwise>
	               </c:choose>
	             </td>
	           </tr>
	           <c:if test="${not empty species20.seq2 }">
		           <tr>
		             <td></td>
		             <td></td>
		             <td class="no">2</td>
		             <td>
		               <div class="seq">${species20.seq2}</div>
		             </td>
		             <td name="opera-seq"><span id="copy-seq${seqNum }<c:set var="seqNum" scope="page" value="${seqNum+1 }"/>"></span></td>
		             <td></td>
		           </tr>
	           </c:if>
               <c:if test="${not empty species20.seq3 }">
                   <tr>
                     <td></td>
                     <td></td>
                     <td class="no">3</td>
                     <td>
                       <div class="seq">${species20.seq3}</div>
                     </td>
                     <td name="opera-seq"><span id="copy-seq${seqNum }<c:set var="seqNum" scope="page" value="${seqNum+1 }"/>"></span></td>
                     <td></td>
                   </tr>
               </c:if>
               <c:if test="${not empty species20.seq4 }">
                   <tr>
                     <td></td>
                     <td></td>
                     <td class="no">4</td>
                     <td>
                       <div class="seq">${species20.seq4}</div>
                     </td>
                     <td name="opera-seq"><span id="copy-seq${seqNum }<c:set var="seqNum" scope="page" value="${seqNum+1 }"/>"></span></td>
                     <td></td>
                   </tr>
               </c:if>
	         </c:forEach>
	       </c:if>
	    </tbody>
	  </table>
	  <div class="test-description"> 快速序列验证及比对(NCBI Blast): <a href="http://blast.ncbi.nlm.nih.gov/Blast.cgi?PROGRAM=blastn&PAGE_TYPE=BlastSearch&LINK_LOC=blasthome">http://blast.ncbi.nlm.nih.gov/Blast.cgi?PROGRAM=blastn&PAGE_TYPE=BlastSearch&LINK_LOC=blasthome</a></div>
	  <h4>2. 其它可能病原菌</h4>
	  <table id="other-seq-table" class="table  seq-table">
	      <thead>
	         <tr>
	           <th style="max-width: 119px;">菌名(属-种)</th>
	           <th width="85">唯一识别<br>序列数</th>
	           <th class="no">#</th>
	           <th width="380">序列 (5'-3')</th>
	           <th name="opera-seq" width="53">操作</th>
	         </tr>
	       </thead>
	       <tbody>
	         <c:if test="${not empty bsi.species_other}">
	           <c:forEach items="${bsi.species_other }" var="species20">
	           <tr>
	             <td style="text-align:center;">
	               ${species20.species }
	               <br>
	               <svg width="139.9" height="8">
	                 <rect x="0" y="0" width="139.9" height="8" fill="#fff" style="stroke:#B0DF2B;fill-opacity:0.1;stroke-opacity:0.9"/>
	                 <c:if test="${species20.site1!='0,0'}">
	                 <rect x="0" y="0" width="34.9" height="8" fill="#B0DF2B" style="fill-opacity:0.4;stroke-opacity:0.9"/>
	                 </c:if>
	                 <c:if test="${species20.site2!='0,0'}">
	                 <rect x="33.1" y="0" width="46.3" height="8" fill="#17BABF" style="fill-opacity:0.4;stroke-opacity:0.9"/>
	                 </c:if>
	                 <c:if test="${species20.site3!='0,0'}">
	                 <rect x="77.6" y="0" width="31.5" height="8" fill="#FEAA20" style="fill-opacity:0.4;stroke-opacity:0.9"/>
	                 </c:if>
	                 <c:if test="${species20.site4!='0,0'}">
	                 <rect x="105.6" y="0" width="34.3" height="8" fill="#AE98DA" style="fill-opacity:0.4;stroke-opacity:0.9"/>
	                 </c:if>
	               </svg>
	             </td>
	             <td style="text-align:center;">${species20.unique_reads_num }</td>
	             <td class="no">1</td>
	             <td>
	               <div class="seq">${species20.seq1 }</div>
	             </td>
	             <td name="opera-seq"><span id="copy-seq${seqNum }<c:set var="seqNum" scope="page" value="${seqNum+1 }"/>"></span></td>
	           </tr>
               <c:if test="${not empty species20.seq2 }">
                   <tr>
                     <td></td>
                     <td></td>
                     <td class="no">2</td>
                     <td>
                       <div class="seq">${species20.seq2}</div>
                     </td>
                     <td name="opera-seq"><span id="copy-seq${seqNum }<c:set var="seqNum" scope="page" value="${seqNum+1 }"/>"></span></td>
                   </tr>
               </c:if>
               <c:if test="${not empty species20.seq3 }">
                   <tr>
                     <td></td>
                     <td></td>
                     <td class="no">3</td>
                     <td>
                       <div class="seq">${species20.seq3}</div>
                     </td>
                     <td name="opera-seq"><span id="copy-seq${seqNum }<c:set var="seqNum" scope="page" value="${seqNum+1 }"/>"></span></td>
                   </tr>
               </c:if>
               <c:if test="${not empty species20.seq4 }">
                   <tr>
                     <td></td>
                     <td></td>
                     <td class="no">4</td>
                     <td>
                       <div class="seq">${species20.seq4}</div>
                     </td>
                     <td name="opera-seq"><span id="copy-seq${seqNum }<c:set var="seqNum" scope="page" value="${seqNum+1 }"/>"></span></td>
                   </tr>
               </c:if>
	         </c:forEach>
	       </c:if>
	     </tbody>
	   </table>
	  <h4>3. 统计信息</h4>
	  <table class="table">
        <tr>
          <td>序列总数</td>
          <td>平均质量</td>
          <td>平均GC含量</td>
        </tr>
        <tr>
          <td>${bsi.totalReads }</td>
          <td>${bsi.avgQuality }</td>
          <td>${bsi.avgGCContent }</td>
        </tr>
      </table>
	  <table class="char-table">
        <tr>
          <td style="width:50%;" class="char-td">
            <c:choose>
              <c:when test="${empty bsi.readsDistributionInfo}">
                <c:if test="${not empty bsi.readsDistribution}">
                  <img src="${uploadPath }/${bsi.userId }/${bsi.appId }/${bsi.dataKey }${bsi.readsDistribution }" style="width:100%;">
                </c:if>
              </c:when>
              <c:otherwise>
                 <div id="reads-distribution-char" style="width:400px;height:330px;">${bsiCharList.readsDistributionInfo }</div>
              </c:otherwise>
            </c:choose>
          </td>
          <td style="width:50%;" class="char-td">
            <c:choose>
              <c:when test="${empty bsi.familyDistributionInfo}">
                <c:if test="${not empty bsi.familyDistribution}">
                  <img src="${uploadPath }/${bsi.userId }/${bsi.appId }/${bsi.dataKey }${bsi.familyDistribution }" style="width:100%;">
                </c:if>
              </c:when>
              <c:otherwise>
                 <div id="family-distribution-char" style="width:400px;height:330px;">${bsiCharList.familyDistributionInfo }</div>
              </c:otherwise>
            </c:choose>
          </td>
        </tr>
      </table>
	  <table>
	      <tr>
            <td class="char-td">
              <c:choose>
                <c:when test="${empty bsi.genusDistributionInfo}">
                  <c:if test="${not empty bsi.genusDistribution}">
                    <img src="${uploadPath }/${bsi.userId }/${bsi.appId }/${bsi.dataKey }${bsi.genusDistribution }" style="width:100%;">
                  </c:if>
                </c:when>
                <c:otherwise>
                  <div id="genus-distribution-char" style="width:850px;height:330px;background-color:#fff;">${bsiCharList.genusDistributionInfo }</div>
                </c:otherwise>
              </c:choose>
          </td>
	     </tr>
	    </table>
	    <h4>4. 附件</h4>
	    <h4 class="second-title">4.1 生物信息数据分析</h4>
	    <table class="table data-analy-table" style="font-size:12px;">
	      <thead>
	        <tr>
	          <th>种</th>
	          <th>属</th>
	          <th>GI号</th>
	          <th>覆盖长度%</th>
	          <th>种比对上<br>的序列数</th>
	          <th>属比对上<br>的序列数</th>
	          <th>种序列<br>百分比</th>
	          <th>平均覆盖<br>深度</th>
	        </tr>
	      </thead>
	      <tbody>
	        <c:if test="${fn:length(bsi.summaryTable)>0}">
	          <c:forEach items="${bsi.summaryTable }" var="summary" varStatus="s">
	            <tr>
	              <td style="text-align: left;line-height:1em">${summary.Species }</td>
	              <td>${summary.Genus }</td>
	              <td>${summary.GI }</td>
	              <td>${summary.Coverage }</td>
	              <td>${summary.Reads_hit }</td>
	              <td>${summary.Reads_num }</td>
	              <td>${summary.Reads_Ratio }</td>
	              <td>${summary.avgCoverage }</td>
	            </tr>
	          </c:forEach>
	        </c:if>
	      </tbody>
	    </table>
	    <h4 class="second-title">4.2 16S rRNA序列覆盖分布图</h4>
	    <table class="img-table">
	        <tr>
	          <td style="width:50%">
	            <c:if test="${not empty bsi.pngPath.top1png }">
	              <img src="${uploadPath }/${bsi.userId }/${bsi.appId }/${bsi.dataKey }${bsi.pngPath.top1png }" style="width:100%;">
	            </c:if>
	          </td>
	          <td style="width:50%">
	            <c:if test="${not empty bsi.pngPath.top2png }">
	              <img src="${uploadPath }/${bsi.userId }/${bsi.appId }/${bsi.dataKey }${bsi.pngPath.top2png }" style="width:100%;">
	            </c:if>
	          </td>
	        </tr>
	        <tr>
	          <td style="width:50%">
	            <c:if test="${not empty bsi.pngPath.top3png }">
	              <img src="${uploadPath }/${bsi.userId }/${bsi.appId }/${bsi.dataKey }${bsi.pngPath.top3png }" style="width:100%;">
	            </c:if>
	          </td>
	          <td style="width:50%">
	            <c:if test="${not empty bsi.pngPath.top4png }">
	              <img src="${uploadPath }/${bsi.userId }/${bsi.appId }/${bsi.dataKey }${bsi.pngPath.top4png }" style="width:100%;">
	            </c:if>
	          </td>
	        </tr>
	        <tr>
	          <td style="width:50%">
	            <c:if test="${not empty bsi.pngPath.top5png }">
	              <img src="${uploadPath }/${bsi.userId }/${bsi.appId }/${bsi.dataKey }${bsi.pngPath.top5png }" style="width:100%;">
	            </c:if>
	          </td>
	          <td style="width:50%">
	            <c:if test="${not empty bsi.pngPath.top6png }">
	              <img src="${uploadPath }/${bsi.userId }/${bsi.appId }/${bsi.dataKey }${bsi.pngPath.top6png }" style="width:100%;">
	            </c:if>
	          </td>
	        </tr>
	        <tr>
	          <td style="width:50%">
	            <c:if test="${not empty bsi.pngPath.top7png }">
	              <img src="${uploadPath }/${bsi.userId }/${bsi.appId }/${bsi.dataKey }${bsi.pngPath.top7png }" style="width:100%;">
	            </c:if>
	          </td>
	          <td style="width:50%">
	            <c:if test="${not empty bsi.pngPath.top8png }">
	              <img src="${uploadPath }/${bsi.userId }/${bsi.appId }/${bsi.dataKey }${bsi.pngPath.top8png }" style="width:100%;">
	            </c:if>
	          </td>
	        </tr>
	        <tr>
	          <td style="width:50%">
	            <c:if test="${not empty bsi.pngPath.top9png }">
	              <img src="${uploadPath }/${bsi.userId }/${bsi.appId }/${bsi.dataKey }${bsi.pngPath.top9png }" style="width:100%;">
	            </c:if>
	          </td>
	          <td style="width:50%">
	            <c:if test="${not empty bsi.pngPath.top10png }">
	              <img src="${uploadPath }/${bsi.userId }/${bsi.appId }/${bsi.dataKey }${bsi.pngPath.top10png }" style="width:100%;">
	            </c:if>
	          </td>
	        </tr>
	    </table>
	    <h4 class="second-title">4.3 序列质量分析（QC）</h4>
	    <table class="table">
	      <thead>
	        <tr>
	            <th>#Measure</th>
	            <th colspan="2">Value</th>
	        </tr>
	      </thead>
	      <tbody>
	          <tr>
	              <td>Filename</td>
	              <td>${bsi.basicStatistics1.Filename }</td>
	              <td>${bsi.basicStatistics2.Filename }</td>
	          </tr>
	          <tr>
	              <td>File type</td>
	              <td>${bsi.basicStatistics1.FileType }</td>
	              <td>${bsi.basicStatistics2.FileType }</td>
	          </tr>
	          <tr>
	              <td>Encoding</td>
	              <td>${bsi.basicStatistics1.Encoding }</td>
	              <td>${bsi.basicStatistics2.Encoding }</td>
	          </tr>
	          <tr>
	              <td>Total Sequences</td>
	              <td>${bsi.basicStatistics1.TotalSeq }</td>
	              <td>${bsi.basicStatistics2.TotalSeq }</td>
	          </tr>
	          <tr>
	              <td>Filtered Sequences</td>
	              <td>${bsi.basicStatistics1.FilteredSeq }</td>
	              <td>${bsi.basicStatistics2.FilteredSeq }</td>
	          </tr>
	          <tr>
	              <td>Sequence length</td>
	              <td>${bsi.basicStatistics1.SeqLength }</td>
	              <td>${bsi.basicStatistics2.SeqLength }</td>
	          </tr>
	          <tr>
	              <td>%GC</td>
	              <td>${bsi.basicStatistics1.gc }</td>
	              <td>${bsi.basicStatistics2.gc }</td>
	          </tr>
	      </tbody>
	    </table>
	    <table>
	      <tr>
	        <td style="width:50%;">
	          <c:if test="${not empty bsi.qualityPath1 }">
	            <img style="width:100%;" src="${uploadPath }/${bsi.userId }/${bsi.appId }/${bsi.dataKey }${bsi.qualityPath1 }">
	          </c:if>
	        </td>
	        <td>
	          <c:if test="${not empty bsi.qualityPath2 }">
	            <img style="width:100%;" src="${uploadPath }/${bsi.userId }/${bsi.appId }/${bsi.dataKey }${bsi.qualityPath2 }">
	          </c:if>
	        </td>
	      </tr>
	      <tr>
	        <td>
	          <c:if test="${not empty bsi.seqContentPath1 }">
	            <img style="width:100%;" alt="" src="${uploadPath }/${bsi.userId }/${bsi.appId }/${bsi.dataKey }${bsi.seqContentPath1 }">
	          </c:if>
	        </td>
	        <td>
	          <c:if test="${not empty bsi.seqContentPath2 }">
	            <img style="width:100%;" alt="" src="${uploadPath }/${bsi.userId }/${bsi.appId }/${bsi.dataKey }${bsi.seqContentPath2 }">
	          </c:if>
	        </td>
	      </tr>
	    </table>
	  </div>
    </div>
  </div>
<script src="//cdn.bootcss.com/echarts/2.2.7/echarts.js"></script>
<script src="<%=request.getContextPath()%>/js/charts.js"></script>
<script type="text/javascript">
  $(document).ready(function(){
      var params = {
          wmode: "transparent",
          allowScriptAccess: "always"
      };
      var flashBtnUri = "<%=request.getContextPath() %>/images/icon/copy_seq_btn.png";
      $(".seq").each(function(){
        var seqText = $(this).text();
        var btnId = $(this).parent().next().find("span").attr("id");
        var flashvars1 = {
          content: encodeURIComponent(seqText),
          uri: flashBtnUri
        };
        swfobject.embedSWF("<%=request.getContextPath() %>/plugins/clipboard.swf", btnId, "52", "25", "9.0.0", null, flashvars1, params);
      });
      var mib_readsDisInfo = $("#reads-distribution-char").text();
      var mib_familyDisInfo = $("#family-distribution-char").text();
      var min_genusDisInfo = $("#genus-distribution-char").text();
      if(mib_readsDisInfo != ""){
          $.reportChar.draw.circularGraph("reads-distribution-char","Reads","Distribution",eval("("+mib_readsDisInfo+")"));
      }
      if(mib_familyDisInfo != ""){
          $.reportChar.draw.circularGraph("family-distribution-char","Family","Distribution",eval("("+mib_familyDisInfo+")"));
      }
      if(min_genusDisInfo != ""){
          $.reportChar.draw.singleBar("genus-distribution-char","Top 10 genus distribution","",eval("("+min_genusDisInfo+")"),"Depth","Depth");
      }
      $("#analy-tab").on("click",function(){
        $("#analy-tab").parent("li").addClass("active");
        $("#patient-tab").parent("li").removeClass("active");
        $("#tab-temp").removeClass("hide");
        $("#analy-report").css("z-index","99999");
        $("#analy-report").addClass("active").addClass("in");
        $("#patient-report").css("z-index","");
        $("#patient-report").removeClass("active").removeClass("in");
        $("#print-analy-a").removeClass("hide");
        $("#print-patient-a").addClass("hide");
        $("#tab-temp").addClass("hide");
      });
      $("#patient-tab").on("mousedown",function(){
        $("#patient-tab").parent("li").addClass("active");
        $("#analy-tab").parent("li").removeClass("active");
        $("#tab-temp").removeClass("hide");
        $("#analy-report").css("z-index","");
        $("#patient-report").css("z-index","99999");
        $("#patient-report").addClass("active").addClass("in");
        $("#print-patient-a").removeClass("hide");
        $("#print-analy-a").addClass("hide");
      });
      $("#patient-tab").on("click",function(){
        $("#analy-report").removeClass("active");
        $("#tab-temp").addClass("hide");
      });
      $("#to-my-report").on("click",function(){
        $.report.find.condition();
      });
  });
</script>