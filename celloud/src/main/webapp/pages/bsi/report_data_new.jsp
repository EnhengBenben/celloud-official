<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="content-header">
  <ol class="breadcrumb">
    <li>主页</li>
    <li>应用</li>
    <li>百菌探</li>
    <li><a data-click="report-list" href="javascript:void(0)">报告</a></li>
    <li>
      <c:forEach items="${bsi.data}" var="data">
          ${data.fileName}&nbsp;&nbsp;&nbsp;
      </c:forEach>
    </li>
  </ol>
</div>
<div class="content">
  <div class="pull-left">
	  <ul id="myTabs" class="nav nav-tabs" role="tablist">
	    <li role="presentation" class="active"><a href="#patient-report" id="patient-tab" aria-controls="patient-report" role="tab" data-toggle="tab">患者报告</a></li>
	    <li role="presentation"><a href="#analy-report" id="analy-tab" aria-controls="analy-report" role="tab" data-toggle="tab">分析报告</a></li>
	  </ul>
	  <div id="myTabContent" class="tab-content">
	    <div role="tabpanel" class="tab-pane active in" id="patient-report" aria-labelledby="patient-tab">
	      <h4>检测结果：</h4>
	      <div class="test-info">
	        <c:if test="${not empty bsi.species_20}">
	          <c:forEach items="${bsi.species_20 }" var="species20">
	            <c:choose>
	              <c:when test='${species20.species_zh.equals("未知") || empty species20.species_zh}'>${species20.species }<br></c:when>
	              <c:otherwise>${species20.species_zh }<br>
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
	      </div>
	      <h4>检测范围(20种)：</h4>
	      <table class="table table-main">
	        <tbody>
	        <tr>
	          <td>
	                           人葡萄球菌
	          </td>
	          <td>
	                          肺炎链球菌
	          </td>
	          <td>
	                          粪肠球菌
	          </td>
	          <td>
	                         屎肠球菌
	          </td>
	        </tr>
	        <tr>
	           <td>
	                          艰难梭状芽胞杆菌
	           </td>
	           <td>
	                           表皮葡萄球菌
	           </td>
	           <td>
	                          溶血性葡萄球菌
	           </td>
	           <td>
	                           结核分枝杆菌
	           </td>
	         </tr>
	         <tr>
	           <td>
	                            鲍曼不动杆菌
	           </td>
	           <td>
	                         大肠埃希菌
	           </td>
	           <td>
	                          肺炎克雷伯氏菌
	           </td>
	           <td>
	                           粘质沙雷氏菌
	           </td>
	         </tr>
	         <tr>
	           <td>
	                           阴沟肠杆菌
	           </td>
	           <td>
	                           洋葱伯克霍尔德菌
	           </td>
	           <td>
	                          奇异变形杆菌
	           </td>
	           <td>
	                          铜绿假单胞菌
	           </td>
	         </tr>
	         <tr>
	           <td>
	                           嗜麦芽窄食单胞菌
	           </td>
	           <td>
	                           肠炎沙门氏菌
	           </td>
	           <td>
	                          产气肠杆菌
	           </td>
	           <td>
	                           金黄色酿脓葡萄球菌
	           </td>
	         </tr>
	         </tbody>
	       </table>
	      <p>*此检测结果为阴性仅代表未检测到上述20种细菌，不代表样本中不存在本检测范围之外的其他细菌。</p>
	      <h4>检测方法：<span>基于16S rDNA高通量测序方法</span></h4>
	    </div>
	    <div role="tabpanel" class="tab-pane analy-tab" id="analy-report" aria-labelledby="analy-tab">
	      <h4>1. 检测结果：</h4>
	      <div class="test-info">
	        <c:if test="${not empty bsi.species_20}">
	          <c:forEach items="${bsi.species_20 }" var="species20">
	            <c:choose>
	              <c:when test='${species20.species_zh.equals("未知") || empty species20.species_zh}'>${species20.species }<br></c:when>
	              <c:otherwise>${species20.species_zh }<br>
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
	      </div>
	      <table class="table table-20species">
	        <tbody>
	         <tr>
	           <td class="title" rowspan="2">革兰氏阳性</td>
	           <c:choose>
	             <c:when test="${havestrain.contains('人葡萄球菌') }">
	               <td class="hasit"><i class="fa fa-circle" aria-hidden="true"></i>
	             </c:when>
	             <c:otherwise>
	               <td><i class="fa fa-circle-o" aria-hidden="true"></i>
	             </c:otherwise>
	           </c:choose>
	                          人葡萄球菌
	           </td>
	           <c:choose>
	             <c:when test="${havestrain.contains('肺炎链球菌') }">
	               <td class="hasit"><i class="fa fa-circle" aria-hidden="true"></i>
	             </c:when>
	             <c:otherwise>
	               <td><i class="fa fa-circle-o" aria-hidden="true"></i>
	             </c:otherwise>
	           </c:choose>
	                                 肺炎链球菌
	           </td>
	           <c:choose>
	             <c:when test="${havestrain.contains('粪肠球菌') }">
	               <td class="hasit"><i class="fa fa-circle" aria-hidden="true"></i>
	             </c:when>
	             <c:otherwise>
	               <td><i class="fa fa-circle-o" aria-hidden="true"></i>
	             </c:otherwise>
	           </c:choose>
	                          粪肠球菌
	           </td>
	           <c:choose>
                 <c:when test="${havestrain.contains('艰难梭状芽胞杆菌') }">
                   <td class="hasit"><i class="fa fa-circle" aria-hidden="true"></i>
                 </c:when>
                 <c:otherwise>
                   <td><i class="fa fa-circle-o" aria-hidden="true"></i>
                 </c:otherwise>
               </c:choose>
                               艰难梭状芽胞杆菌
               </td>
	         </tr>
	         <tr>
	           <c:choose>
                 <c:when test="${havestrain.contains('屎肠球菌') }">
                   <td class="hasit"><i class="fa fa-circle" aria-hidden="true"></i>
                 </c:when>
                 <c:otherwise>
                   <td><i class="fa fa-circle-o" aria-hidden="true"></i>
                 </c:otherwise>
               </c:choose>
                               屎肠球菌
               </td>
	           <c:choose>
	             <c:when test="${havestrain.contains('表皮葡萄球菌') }">
	               <td class="hasit"><i class="fa fa-circle" aria-hidden="true"></i>
	             </c:when>
	             <c:otherwise>
	               <td><i class="fa fa-circle-o" aria-hidden="true"></i>
	             </c:otherwise>
	           </c:choose>
	                           表皮葡萄球菌
	           </td>
	           <c:choose>
	             <c:when test="${havestrain.contains('溶血性葡萄球菌') }">
	               <td class="hasit"><i class="fa fa-circle" aria-hidden="true"></i>
	             </c:when>
	             <c:otherwise>
	               <td><i class="fa fa-circle-o" aria-hidden="true"></i>
	             </c:otherwise>
	           </c:choose>
	                          溶血性葡萄球菌
	           </td>
	           <c:choose>
                 <c:when test="${havestrain.contains('结核分枝杆菌') }">
                   <td class="hasit"><i class="fa fa-circle" aria-hidden="true"></i>
                 </c:when>
                 <c:otherwise>
                   <td><i class="fa fa-circle-o" aria-hidden="true"></i>
                 </c:otherwise>
               </c:choose>
                             结核分枝杆菌
               </td>
	         </tr>
	         <tr>
	           <td class="title" rowspan="3">革兰氏阴性</td>
	           <c:choose>
	             <c:when test="${havestrain.contains('鲍曼不动杆菌') }">
	               <td class="hasit"><i class="fa fa-circle" aria-hidden="true"></i>
	             </c:when>
	             <c:otherwise>
	               <td><i class="fa fa-circle-o" aria-hidden="true"></i>
	             </c:otherwise>
	           </c:choose>
	                          鲍曼不动杆菌
	           </td>
	           <c:choose>
	             <c:when test="${havestrain.contains('大肠埃希菌') }">
	               <td class="hasit"><i class="fa fa-circle" aria-hidden="true"></i>
	             </c:when>
	             <c:otherwise>
	               <td><i class="fa fa-circle-o" aria-hidden="true"></i>
	             </c:otherwise>
	           </c:choose>
	                           大肠埃希菌
	           </td>
	           <c:choose>
	             <c:when test="${havestrain.contains(' 肺炎克雷伯氏菌') }">
	               <td class="hasit"><i class="fa fa-circle" aria-hidden="true"></i>
	             </c:when>
	             <c:otherwise>
	               <td><i class="fa fa-circle-o" aria-hidden="true"></i>
	             </c:otherwise>
	           </c:choose>
	                           肺炎克雷伯氏菌
	           </td>
	           <c:choose>
                 <c:when test="${havestrain.contains('金黄色酿脓葡萄球菌') }">
                   <td class="hasit"><i class="fa fa-circle" aria-hidden="true"></i>
                 </c:when>
                 <c:otherwise>
                   <td><i class="fa fa-circle-o" aria-hidden="true"></i>
                 </c:otherwise>
               </c:choose>
                              金黄色酿脓葡萄球菌
               </td>
	         </tr>
	         <tr>
	           <c:choose>
	             <c:when test="${havestrain.contains('粘质沙雷氏菌') }">
	               <td class="hasit"><i class="fa fa-circle" aria-hidden="true"></i>
	             </c:when>
	             <c:otherwise>
	               <td><i class="fa fa-circle-o" aria-hidden="true"></i>
	             </c:otherwise>
	           </c:choose>
	                           粘质沙雷氏菌
	           </td>
	           <c:choose>
	             <c:when test="${havestrain.contains('阴沟肠杆菌') }">
	               <td class="hasit"><i class="fa fa-circle" aria-hidden="true"></i>
	             </c:when>
	             <c:otherwise>
	               <td><i class="fa fa-circle-o" aria-hidden="true"></i>
	             </c:otherwise>
	           </c:choose>
	                          阴沟肠杆菌
	           </td>
	           <c:choose>
                 <c:when test="${havestrain.contains('奇异变形杆菌') }">
                   <td class="hasit"><i class="fa fa-circle" aria-hidden="true"></i>
                 </c:when>
                 <c:otherwise>
                   <td><i class="fa fa-circle-o" aria-hidden="true"></i>
                 </c:otherwise>
               </c:choose>
                               奇异变形杆菌
               </td>
               <c:choose>
                 <c:when test="${havestrain.contains('洋葱伯克霍尔德菌') }">
                   <td class="hasit"><i class="fa fa-circle" aria-hidden="true"></i>
                 </c:when>
                 <c:otherwise>
                   <td><i class="fa fa-circle-o" aria-hidden="true"></i>
                 </c:otherwise>
               </c:choose>
                               洋葱伯克霍尔德菌
               </td>
	         </tr>
	         <tr>
	           <c:choose>
                 <c:when test="${havestrain.contains('产气肠杆菌') }">
                   <td class="hasit"><i class="fa fa-circle" aria-hidden="true"></i>
                 </c:when>
                 <c:otherwise>
                   <td><i class="fa fa-circle-o" aria-hidden="true"></i>
                 </c:otherwise>
               </c:choose>
                              产气肠杆菌
               </td>
	           <c:choose>
	             <c:when test="${havestrain.contains('铜绿假单胞菌') }">
	               <td class="hasit"><i class="fa fa-circle" aria-hidden="true"></i>
	             </c:when>
	             <c:otherwise>
	               <td><i class="fa fa-circle-o" aria-hidden="true"></i>
	             </c:otherwise>
	           </c:choose>
	                           铜绿假单胞菌
	           </td>
	           <c:choose>
	             <c:when test="${havestrain.contains('肠炎沙门氏菌') }">
	               <td class="hasit"><i class="fa fa-circle" aria-hidden="true"></i>
	             </c:when>
	             <c:otherwise>
	               <td><i class="fa fa-circle-o" aria-hidden="true"></i>
	             </c:otherwise>
	           </c:choose>
	                           肠炎沙门氏菌
	           </td>
	            <c:choose>
                 <c:when test="${havestrain.contains('嗜麦芽窄食单胞菌') }">
                   <td class="hasit"><i class="fa fa-circle" aria-hidden="true"></i>
                 </c:when>
                 <c:otherwise>
                   <td><i class="fa fa-circle-o" aria-hidden="true"></i>
                 </c:otherwise>
               </c:choose>
                              嗜麦芽窄食单胞菌
               </td>
	         </tr>
	        </tbody>
	      </table>
	      <table id="test-seq-table" class="table seq-table">
	        <thead>
	          <tr>
	            <th style="max-width: 119px;">菌名</th>
	            <th width="85">序列比重</th>
	            <th width="85">检出区域</th>
	            <th width="380">序列 (5'-3')</th>
	          </tr>
	        </thead>
	        <tbody>
	          <c:set var="seqNum" scope="page" value="0"/>
	          <c:if test="${not empty bsi.species_20}">
	             <c:forEach items="${bsi.species_20 }" var="species20">
	               <c:set var="srowspan" value="1"/>
	               <c:if test="${not empty species20.seq2 }">
	                   <c:set var="srowspan" value="${srowspan+1 }"/>
	               </c:if>
	               <c:if test="${not empty species20.seq3 }">
	                   <c:set var="srowspan" value="${srowspan+1 }"/>
	               </c:if>
	               <c:if test="${not empty species20.seq4 }">
	                  <c:set var="srowspan" value="${srowspan+1 }"/>
	               </c:if>
	               <tr>
	                 <td class="title" rowspan="${srowspan }">
	                   <c:choose>
	                   <c:when test='${species20.species_zh.equals("未知") || empty species20.species_zh}'>${species20.species }</c:when>
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
	                   <svg width="120" height="20">
	                     <g>
	                       <rect x="0" y="0" width="30" height="20" fill="
	                         <c:choose>
	                           <c:when test="${species20.site1!='0,0'}">#dcdcdc</c:when>
	                           <c:otherwise>#fff</c:otherwise>
		                     </c:choose>"
		                     style="stroke:#8c8c8c;fill-opacity:0.4;stroke-opacity:0.9"></rect>
		                   <text x="8" y="15">V1</text>
		                 
		                   <rect x="30" y="0" width="30" height="20" fill="
	                         <c:choose>
	                           <c:when test="${species20.site2!='0,0'}">#dcdcdc</c:when>
	                           <c:otherwise>#fff</c:otherwise>
	                         </c:choose>"
	                         style="stroke:#8c8c8c;fill-opacity:0.4;stroke-opacity:0.9"></rect>
	                       <text x="38" y="15">V2</text>
	                       <rect x="60" y="0" width="30" height="20" fill="
	                         <c:choose>
	                           <c:when test="${species20.site3!='0,0'}">#dcdcdc</c:when>
	                           <c:otherwise>#fff</c:otherwise>
	                         </c:choose>"
	                         style="stroke:#8c8c8c;fill-opacity:0.4;stroke-opacity:0.9"></rect>
		                   <text x="68" y="15">V3</text>
		                   <rect x="90" y="0" width="30" height="20" fill="
	                         <c:choose>
	                           <c:when test="${species20.site4!='0,0'}">#dcdcdc</c:when>
	                           <c:otherwise>#fff</c:otherwise>
	                         </c:choose>"
	                         style="stroke:#8c8c8c;fill-opacity:0.4;stroke-opacity:0.9"></rect>
		                   <text x="98" y="15">V4</text>
	                     </g>
	                   </svg>
	                 </td>
	                 <td class="sub-title" rowspan="${srowspan }">${species20.seq_proportion }</td>
	                 <td>${species20.seq1_name }</td>
	                 <td>
	                   <div class="seq">
	                   ${species20.seq1_no }<br>
	                   ${species20.seq1 }
	                   </div>
	                 </td>
	               </tr>
	               <c:if test="${not empty species20.seq2 }">
	                   <tr>
	                     <td>${species20.seq2_name }</td>
	                     <td>
	                       <div class="seq">${species20.seq2_no }<br>${species20.seq2}</div>
	                     </td>
	                   </tr>
	               </c:if>
	               <c:if test="${not empty species20.seq3 }">
	                   <tr>
	                     <td>${species20.seq3_name }</td>
	                     <td>
	                       <div class="seq">${species20.seq3_no }<br>${species20.seq3}</div>
	                     </td>
	                   </tr>
	               </c:if>
	               <c:if test="${not empty species20.seq4 }">
	                   <tr>
	                     <td>${species20.seq4_name }</td>
	                     <td>
	                       <div class="seq">${species20.seq4_no }<br>${species20.seq4}</div>
	                     </td>
	                   </tr>
	               </c:if>
	             </c:forEach>
	           </c:if>
	        </tbody>
	      </table>
	      <p>序列比重：需要增加一个公式...> 10 reads时为检出<br>
	                        快速序列验证及比对(NCBI Blast): <a target="_blank" href="http://blast.ncbi.nlm.nih.gov/Blast.cgi?PROGRAM=blastn&PAGE_TYPE=BlastSearch&LINK_LOC=blasthome">http://blast.ncbi.nlm.nih.gov/Blast.cgi?PROGRAM=blastn&PAGE_TYPE=BlastSearch&LINK_LOC=blasthome</a></p>
	      <h4>2. 统计信息</h4>
	      <table class="table table-count">
	        <tr>
	          <td>序列总数</td>
	          <td>平均质量</td>
	          <td>平均GC含量</td>
	          <td>human序列总数</td>
	          <td>16S序列总数</td>
	        </tr>
	        <tr>
	          <td>${bsi.totalReads }</td>
	          <td>${bsi.avgQuality }</td>
	          <td>${bsi.avgGCContent }</td>
	          <td>${bsi.human_reads }</td>
	          <td>${bsi.reads_16s }</td>
	        </tr>
	      </table>
	    </div>
	  </div>
	  <div class="data-content-footer">
	    <ul>
	      <li><h4>更多信息： </h4></li>
	      <li>
	        <label>标签：</label>
	        <span id="data-batch">${data.batch }</span>
	      </li>
	      <li>
	        <label>生成时间：</label>
	        <span>
              <fmt:formatDate value="${bsi.createDate }" type="both" dateStyle="long" pattern="yyyyMMdd" />
            </span>
	      </li>
	      <li>
	        <label>原始数据：</label>
            <span>
	            <c:forEach items="${bsi.data}" var="data">
	              ${data.fileName}&nbsp;&nbsp;
	            </c:forEach>
	        </span>
	        <a href="javascript:void(0)">（查看原始数据信息）</a>
	      </li>
	    </ul>
	  </div>
  </div>
  <div class="report-opera pull-right">
    <div class="nav-pub">
      <div >
        <c:if test="${pageList.datas.size()>0}">
          <input id="total-page-hide" value="${pageList.page.totalPage }" type="hidden" >
          <a class="prev-btn" href="javascript:$.report.detail.prev(${pageList.page.currentPage })"><i class="fa fa-chevron-circle-left"></i>上一份</a>
          <a class="next-btn" href="javascript:$.report.detail.next(${pageList.page.currentPage })">下一份<i class="fa fa-chevron-circle-right"></i></a>
        </c:if>
      </div>
    </div>
    <div id="report-pagination" class="report-pagination"></div>
    <div class="report-btn-group pull-right">
       <a class="btn" id="print-patient-a" href="<%=request.getContextPath()%>/report/printBSIReport?projectId=${bsi.projectId }&dataKey=${bsi.dataKey }&appId=${bsi.appId }&templateType=print_patient" target="_blank"><i class="fa fa-print"></i>打印</a>
       <a class="btn hide" id="print-analy-a" href="<%=request.getContextPath()%>/report/printBSIReport?projectId=${bsi.projectId }&dataKey=${bsi.dataKey }&appId=${bsi.appId }&templateType=print_analy" target="_blank"><i class="fa fa-print"></i>打印</a>
       <a class="btn" href="javascript:void(0)"><i class="fa fa-cloud-download"></i>导出</a>
	   <a class="btn" href="javascript:void(0)"><i class="fa fa-group"></i>发布</a>
	   <a class="btn" href="javascript:void(0)"><i class="fa fa-share-square-o"></i>分享</a>
	   <a class="btn" href="javascript:void(0)"><i class="fa fa-inbox"></i>存档</a>
     </div>
  </div>
  <input id="appid-hide" type="hidden" value="${bsi.appId }">
  <input id="datakey-hide" type="hidden" value="${bsi.dataKey }">
</div>
<script src="<%=request.getContextPath()%>/js/bsi_report.js?version=1.2"></script>