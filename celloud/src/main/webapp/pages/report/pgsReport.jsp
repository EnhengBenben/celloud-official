<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
	<c:if test="${pgs.noEnoughReads.equals('false') }">
		<div class="m-file">
			<div style="max-width: 500px">
				文件名称：
				<span class="file-name">
					${pgs.dataKey }(<c:if test="${!empty pgs.anotherName }">${pgs.anotherName }</c:if><c:if test="${empty pgs.anotherName }">${pgs.fileName }</c:if> )
				</span>
			</div>
			<div class="toolbar">
				<c:if test="${pgs.finalPng!=null && pgs.finalPng!='' }">
					<a href="${path.replace('upload','') }Procedure!miRNADownload?userId=${pgs.userId }/${pgs.appId }/${pgs.dataKey }/${pgs.finalPng }" class="btn btn-default"><i class="i-download"></i>报告下载</a>
				</c:if>
				<c:if test="${pgs.pdf!=null }">
					<a href="${path.replace('upload','') }Procedure!miRNADownload?userId=${pgs.userId }/${pgs.appId }/${pgs.dataKey }/${pgs.pdf }" class="btn btn-default"><i class="i-pdf"></i>PDF下载</a>
				</c:if>
				<c:if test="${pgs.pdf ==null }">
					<c:if test="${pgs.createDate.time>1440432000000  }">
						<a href="${path.replace('upload','') }Procedure!miRNADownload?userId=${pgs.userId }/${pgs.appId }/${pgs.dataKey }/${pgs.dataKey }.pdf" class="btn btn-default"><i class="i-pdf"></i>PDF下载</a>
					</c:if>
				</c:if>
				<c:choose>
					<c:when test="${pgs.splitPng!=null }">
						<a target="_blank" href="printPGS/${pgs.userId }/${pgs.appId }/${pgs.dataKey }/${pgs.miniPng }/${pgs.report.replace('+','@').replace('	','&nbsp;&nbsp;&nbsp;&nbsp;') }/${pgs.splitPng }" class="btn btn-default"><i class="i-print"></i>打印报告</a>
						<a target="_blank" href="printPGS/${pgs.userId }/${pgs.appId }/${pgs.dataKey }/${pgs.splitPng }/${pgs.report.replace('+','@').replace('	','&nbsp;&nbsp;&nbsp;&nbsp;') }/null" class="btn btn-default"><i class="i-print"></i>点图报告</a>					
					</c:when>
					<c:otherwise>
						<a target="_blank" href="printPGS/${pgs.userId }/${pgs.appId }/${pgs.dataKey }/${pgs.miniPng }/${pgs.report.replace('+','@').replace('	','&nbsp;&nbsp;&nbsp;&nbsp;') }/null" class="btn btn-default"><i class="i-print"></i>打印报告</a>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<!--报告图示一-->
		<div class="m-box">
			<h2>
				<i class="i-report1"></i>数据统计
				<c:if test="${pgs.appId!=85 }">
					<div style="float:right;padding-right: 30px" title="帮助" onclick="showModal('countModal')"><i class="i-tips"></i></div>
				</c:if>
			</h2>
			<div class="m-boxCon" id="_table">
				<table class="table table-bordered table-condensed">
				  <thead>
					<tr>
						<c:if test="${pgs.totalReads!=null}">
							<th>Total_Reads</th>
						</c:if>
						<c:if test="${pgs.mapReads!=null}">
							<td>Map_Reads</td>
						</c:if>
						<c:if test="${pgs.mtRatio!=null}">
							<th>MT_ratio(%)</th>
						</c:if>
						<c:if test="${pgs.mapRatio!=null}">
							<th>Map_Ratio(%)</th>
						</c:if>
						<c:if test="${pgs.duplicate!=null}">
							<th>Duplicate(%)</th>
						</c:if>
						<c:if test="${pgs.gcCount!=null}">
							<th>GC_Count(%)</th>
						</c:if>
						<c:if test="${pgs.sd!=null}">
							<th>*SD</th>
						</c:if>
						<c:if test="${pgs.winSize!=null}">
							<td>Win_size(kb)</td>
						</c:if>
					</tr>
				  </thead>
				  <tbody>
					<tr>
						<c:if test="${pgs.totalReads!=null}">
							<td>${pgs.totalReads }</td>
						</c:if>
						<c:if test="${pgs.mapReads!=null}">
							<td>${pgs.mapReads }</td>
						</c:if>
						<c:if test="${pgs.mtRatio!=null}">
							<td>${pgs.mtRatio }</td>
						</c:if>
						<c:if test="${pgs.mapRatio!=null}">
							<td>${pgs.mapRatio }</td>
						</c:if>
						<c:if test="${pgs.duplicate!=null}">
							<td>${pgs.duplicate }</td>
						</c:if>
						<c:if test="${pgs.gcCount!=null}">
							<td>${pgs.gcCount }</td>
						</c:if>
						<c:if test="${pgs.sd!=null}">
							<td>${pgs.sd }</td>
						</c:if>
						<c:if test="${pgs.winSize!=null}">
							<td>${pgs.winSize }</td>
						</c:if>
					</tr>
				  </tbody>
				</table>
			</div>
			<c:if test="${pgs.note!=null&&!pgs.note.equals('')&&pgs.appId==85 }">
				<div class="m-tips">
					<i class="i-tips"></i>${pgs.note }
				</div>
			</c:if>
		</div>
		<!--检测结果-->
		<div class="m-box m-box-yc">
			<h2><i class="i-edit"></i>报告
				<c:if test="${pgs.appId!=85}">
					<c:if test="${pgs.detail!=null && pgs.detail.size()>0 }">
						<div style="float:right;padding-right: 30px" title="帮助" onclick="showModal('reportModal')"><i class="i-tips"></i></div>
					</c:if>
				</c:if>
			</h2>
			<div class="m-boxCon result" id="reportDiv">
				<c:if test="${pgs.appId==85}">
					${pgs.report }
				</c:if>
				<c:if test="${pgs.appId!=85}">
					<c:if test="${pgs.detail==null || pgs.detail.size() == 0}">
						${pgs.report }
					</c:if>
					<c:if test="${pgs.detail!=null && pgs.detail.size()>0 }">
						<table class='table table-bordered table-condensed'>
							<c:forEach items="${pgs.detail }" var="info">
								<tr>
									<c:forEach var="ss" items="${info}" varStatus="st">  
									    <td>${ss }</td>
									    <c:if test="${st.isLast()&&st.getCount()==1 }">
									    	<td></td>
									    	<td></td>
									    	<td></td>
									    </c:if>
									    <c:if test="${st.isLast()&&st.getCount()==2 }">
									    	<td></td>
									    	<td></td>
									    </c:if>
									    <c:if test="${st.isLast()&&st.getCount()==3 }">
									    	<td></td>
									    </c:if>
									</c:forEach>
								</tr>
							</c:forEach>
						</table>
					</c:if>
				</c:if>
			</div>
		</div>
        <!--染色体图示一-->
        <div class="m-box">
        	<h2><i class="i-dna"></i>染色体</h2>
            <div class="m-boxCon">
            	<c:if test="${pgs.miniPng!=null && pgs.miniPng!='' }">
					<a href="javascript:showBg('${path }/${pgs.userId }/${pgs.appId }/${pgs.dataKey }/${pgs.miniPng }','miniPngImg');" >
						<img src="${path }/${pgs.userId }/${pgs.appId }/${pgs.dataKey }/${pgs.miniPng }" style="width: 700px;" id="miniPngImg">
					</a>
				</c:if>
				<c:if test="${pgs.miniPng==null || pgs.miniPng=='' }">
					<span style="color: red;">运行异常，未产生图片！</span>
				</c:if>
            </div>
        </div>
        <!--染色体图示一-->
        <div class="m-box">
        	<h2><i class="i-dna"></i>染色体点图</h2>
            <div class="m-boxCon">
            	<c:if test="${pgs.testPng!=null && pgs.testPng!='' }">
					<a href="javascript:showBg('${path }/${pgs.userId }/${pgs.appId }/${pgs.dataKey }/${pgs.testPng }','testPngImg');" >
						<img src="${path }/${pgs.userId }/${pgs.appId }/${pgs.dataKey }/${pgs.testPng }" style="width: 700px;height: 220px" id="testPngImg">
					</a>
				</c:if>
				<c:if test="${pgs.testPng==null || pgs.testPng=='' }">
					<span style="color: red;">运行异常，未产生图片！</span>
				</c:if>
            </div>
        </div>
		<!--染色体图示一-->
        <div class="m-box">
        	<h2><i class="i-dna"></i>染色体位置图</h2>
            <div class="m-boxCon">
	            <c:if test="${pgs.finalPng!=null && pgs.finalPng!='' }">
					<a href="javascript:showBg('${path }/${pgs.userId }/${pgs.appId }/${pgs.dataKey }/${pgs.finalPng }','finalPngImg');" >
						<img src="${path }/${pgs.userId }/${pgs.appId }/${pgs.dataKey }/${pgs.finalPng }" style="height: 1000px;" id="finalPngImg">
					</a>
				</c:if>
				<c:if test="${pgs.finalPng==null || pgs.finalPng=='' }">
					<span style="color: red;">运行异常，未产生图片！</span>
				</c:if>
            </div>
        </div>
		<!--Celloud数据参数同比分析-->
		<c:if test="${pgs.appId!=104}">
			<div class="bg-analysis">
			    <div class="m-box">
			        <h2><i class="i-celloud"></i>Celloud数据参数同比分析</h2>
			        <div class="m-boxCon" id="charDiv">
			        </div>
			        <div class="m-tips">
			        	<i class="i-tips"></i>
			        	<span id="charResult"></span>
			        </div>
			    </div>
			</div>
		</c:if>
	</c:if>
	<c:if test="${!pgs.noEnoughReads.equals('false') }">
		<h3>测序量不足，无法分析，建议重测。</h3>
		<p>${pgs.noEnoughReads }</p>
	</c:if>
</div>

<div class="modal modal-green-header in" id="countModal">
  <div class="modal-dialog">
   <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal">×</a>
		<h4>数据统计说明</h4>
	</div>
	<div class="modal-body">
		<div class="lineheight">Total_Reads：样本测序序列总数据量，分析要求数据量大于15万。</div>
		<div class="lineheight">Map_Reads：样本序列比对到人类基因组的数据量。</div>
		<div class="lineheight">Map_Ratio：样本序列比对到人类基因组的数据量比例。</div>
		<div class="lineheight">Duplicate：样本测序过程中冗余序列比例，冗余序列产生于同一序列片段，为消除冗余的对分析染色体拷贝数的影响，分析过程中去除冗余序列。</div>
		<div class="lineheight">GC_Count：样本测序序列的GC含量，围绕人类基因组GC平均含量41%波动。</div>
		<div class="lineheight">SD：样本拷贝数分析中染色体的平均偏差，SD越小假阳率越低，SD小于3.5可检测4Mb以上染色体异常。</div>
		<c:if test="${pgs.appId==81||pgs.appId==88||pgs.appId==91||pgs.appId==93 }">
			<div class="lineheight">MT_Ratio：样本测序序列中线粒体序列百分比。数据统计表明染色体拷贝数异常胚胎线粒体比例高。</div>
			<div>
				<img alt="" src="${path.replace('upload','') }/resource/img/pgs.png" width="100%">
			</div>
		</c:if>
	</div>
	<div class="modal-footer">
		<a class="btn btn-celloud-success btn-flat" href="javascript:void(0)" data-dismiss="modal"><i class="icon-ban-circle"></i> 取消</a>
	</div>
  </div>
 </div>
</div>
<div class="modal modal-green-header in" id="reportModal">
  <div class="modal-dialog">
   <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal">×</a>
		<h4>报告说明</h4>
	</div>
	<div class="modal-body">
		<div class="lineheight">Aneuploidy：染色体异倍性及异常区域。</div>
		<div class="lineheight">Position：染色体异常位置。</div>
		<div class="lineheight">Average：染色体拷贝数分析中每个染色体的平均值。该值为染色体拷贝数据的参考值。数据统计表明大部分正常染色体平均值为15-23，染色体三体的平均值大于25，染色体单体的平均值小于13。</div>
	</div>
	<div class="modal-footer">
		<a class="btn btn-celloud-success btn-flat" href="javascript:void(0)" data-dismiss="modal"><i class="icon-ban-circle"></i> 取消</a>
	</div>
  </div>
 </div>
</div>
<style>
.lineheight{
	line-height: 30px;
}
</style>
<script>
$(function() {
	$(window).manhuatoTop({
		showHeight : 100,
		speed : 1000
	});
	$("#reportDiv").find("th").each(function(){
		$(this).css("padding-left","20px");
		$(this).css("text-align","left");
	});
	var num = 0;
	$("#reportDiv").find("td").each(function(){
		var result = $(this).text();
		if(num%4==0){
			$(this).css("min-width","90px");
		}
		if(num%4==1){
			$(this).css("min-width","55px");
		}
		if(num%4==2){
			if(result.length>85){
				$(this).html(result.substring(0,84)+"...");
				$(this).attr("title",result);
			}
		}
		if(num%4==3){
			if(result.length>39){
				$(this).html(result.substring(0,38)+"...");
				$(this).attr("title",result);
			}
			$(this).css("width","300px");
		}
		$(this).css("padding-left","20px");
		$(this).css("text-align","left");
		num ++;
	});
});
function showBg(src,id) {
	var width = $("#" + id).width();
	var height = $("#" + id).height();
	$(window.parent.document).find("img[id='imageFullScreen']").css("width",width*1.5);
	$(window.parent.document).find("img[id='imageFullScreen']").css("height",height*1.5);
	window.parent.showZoom(src);
}
function showModal(id){
	$("#"+id).modal("show");
}
</script>