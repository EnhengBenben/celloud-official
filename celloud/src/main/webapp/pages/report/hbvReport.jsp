<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div>
	<!--文件名称-->
	<div class="m-file">
		文件名称：
		<span class="file-name">
			${hbv.dataKey }(${hbv.fileName })
		</span>
		<div class="toolbar">
			<a href="${down }${hbv.userId }/${hbv.appId }/${hbv.dataKey }/${hbv.pdf }" class="btn btn-default"><i class="i-pdf"></i>PDF下载</a>
			<a href='javascript:toPrintHBV("${hbv.userId }/${hbv.appId }/${hbv.dataKey }",0)' class="btn btn-default"><i class="i-print"></i>详细报告</a>
			<a href='javascript:toPrintHBV("${hbv.userId }/${hbv.appId }/${hbv.dataKey }",1)' class="btn btn-default"><i class="i-print"></i>简要报告</a>
			<a href="${down }${hbv.userId }/${hbv.appId }/${hbv.dataKey }/${hbv.zip }" class="btn btn-default"><i class="i-download"></i>报告下载</a>
		</div>
	</div>
	<div class="m-file" id="cfda">
		<div class="m-box w500">
			<h2>位点突变</h2>
			<h5 id="snpType">${hbv.type.replace('Type','基因型') }</h5>
			<div class="m-boxCon">
				<c:if test="${hbv.known.containsKey('204_png') }">
					<img src="${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.known['204_png'] }" height="476px" width="420px"/>
				</c:if>
				<c:if test="${not hbv.known.containsKey('204_png') }">
					<div class="imgmiss">
						204位点测序失败，建议重测。
					</div>
				</c:if>
			</div>
			<div class="m-tips">
				<i class="i-tips"></i>
					<c:choose>
						<c:when test="${hbv.txt204.contains('LAM') or hbv.txt204.contains('FTC') or hbv.txt204.contains('LDT') or hbv.txt204.contains('ETV') }">
							检测到
							<c:if test="${hbv.txt204.contains('LDT') }">
							替比夫定 LDT
							</c:if>
							<c:if test="${hbv.txt204.contains('LAM') }">
							拉米夫定 LAM
							</c:if>
							<c:if test="${hbv.txt204.contains('FTC') }">
							恩曲他滨 FTC
							</c:if>
							<c:if test="${hbv.txt204.contains('ETV') }">
							恩替卡韦 ETV
							</c:if>
							突变
						</c:when>
						<c:otherwise>
							未检测到突变
						</c:otherwise>
					</c:choose>
			</div>
		</div>
		<div class="center mb10 w500">
			<a href="javascript:void(0)" onclick="change1()" class="btn btn-blue"><i class="i-view"></i>查看详情</a>
		</div>
	</div>
	<div id="nomal" style="display: none;">
		<c:if test="${hbv.type.equals('no match<br />') }">
			<div class="m-box">
			 	<h2>数据异常</h2>
			    <div class="m-boxCon">
					没有比对结果
			    </div>
			</div>
		</c:if>
		<c:if test="${not hbv.type.equals('no match<br />') }">
			<!--位点突变-->
			<div id="printDiv1">
				<div class="m-box">
				 	<h2>
				 		1.替诺福韦酯TDF突变检测
				 		<span style="float:right;padding-right: 30px" title="帮助" onclick="showModal('helpModal',0,1)"><button class="clear button button-glow button-circle button-rounded button-primary button-tiny"><span class="fa fa-thumbs-up"></span></button></span>
				 	</h2>
				    <div class="m-boxCon">
					    <c:if test="${hbv.known.containsKey('194_png') }">
					    	<a href="javascript:showBgOne('${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.known['194_10_png'] }');" >
								<img style="padding-left: 30px;" name="imgSrc" src="${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.known['194_png'] }" height="170px;" width="150px;">
							</a>
						</c:if>
						<c:if test="${not hbv.known.containsKey('194_png') }">
							<div class="imgmiss">
								194位点测序失败，建议重测。
							</div>
						</c:if>
				    </div>
				</div>
				<!--位点突变-->
				<div class="m-box">
				 	<h2>2.替比夫定 LDT 突变检测
				 		<span style="float:right;padding-right: 30px" title="帮助" onclick="showModal('helpModal',0,2)"><button class="clear button button-glow button-circle button-rounded button-primary button-tiny"><span class="fa fa-thumbs-up"></span></button></span>
				 	</h2>
				    <div class="m-boxCon">
				    	<c:if test="${hbv.known.containsKey('204_png') }">
					    	<a href="javascript:showBgOne('${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.known['204_10_png']}');" >
								<img style="padding-left: 30px;" name="imgSrc" src="${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.known['204_png'] }" height="170px;" width="150px;">
							</a>
				    	</c:if>
				    	<c:if test="${not hbv.known.containsKey('204_png') }">
							<div class="imgmiss">
								204位点测序失败，建议重测。
							</div>
						</c:if>
				    </div>
				</div>
				<!--位点突变-->
				<div class="m-box">
				 	<h2>3.阿德福韦 ADV 突变检测
				 		<span style="float:right;padding-right: 30px" title="帮助" onclick="showModal('helpModal',0,3)"><button class="clear button button-glow button-circle button-rounded button-primary button-tiny"><span class="fa fa-thumbs-up"></span></button></span>
				 	</h2>
				    <div class="m-boxCon">
					    <c:if test="${hbv.known.containsKey('181_png') }">
					    	<a href="javascript:showBgOne('${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.known['181_10_png'] }');" >
								<img style="padding-left: 30px;" name="imgSrc" src="${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.known['181_png'] }" height="170px;" width="150px;">
							</a>
						</c:if>
						<c:if test="${not hbv.known.containsKey('181_png') }">
							<div class="imgmiss">
								181位点测序失败，建议重测。
							</div>
						</c:if>
						<c:if test="${hbv.known.containsKey('236_png') }">
							<a href="javascript:showBgOne('${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.known['236_10_png'] }');" >
								<img style="padding-left: 30px;" name="imgSrc" src="${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.known['236_png'] }" height="170px;" width="150px;">
							</a>
						</c:if>
						<c:if test="${not hbv.known.containsKey('236_png') }">
							<div class="imgmiss">
								236位点测序失败，建议重测。
							</div>
						</c:if>
				    </div>
				</div>
				<!--位点突变-->
				<div class="m-box">
				 	<h2>4.拉米夫定 LAM 突变检测
				 		<span style="float:right;padding-right: 30px" title="帮助" onclick="showModal('helpModal',0,4)"><button class="clear button button-glow button-circle button-rounded button-primary button-tiny"><span class="fa fa-thumbs-up"></span></button></span>
				 	</h2>
				    <div class="m-boxCon">
					    <c:if test="${hbv.known.containsKey('173_png') }">
							<a href="javascript:showBgOne('${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.known['173_10_png'] }');" >
								<img style="padding-left: 30px;" name="imgSrc" src="${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.known['173_png'] }" height="170px;" width="150px;">
							</a>
						</c:if>
						<c:if test="${not hbv.known.containsKey('173_png') }">
							<div class="imgmiss">
								173位点测序失败，建议重测。
							</div>
						</c:if>
						<c:if test="${hbv.known.containsKey('180_png') }">
							<a href="javascript:showBgOne('${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.known['180_10_png'] }');" >
								<img style="padding-left: 30px;" name="imgSrc" src="${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.known['180_png'] }" height="170px;" width="150px;">
							</a>
						</c:if>
						<c:if test="${not hbv.known.containsKey('180_png') }">
							<div class="imgmiss">
								180位点测序失败，建议重测。
							</div>
						</c:if>
				    	<c:if test="${hbv.known.containsKey('204_png') }">
					    	<a href="javascript:showBgOne('${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.known['204_10_png']}');" >
								<img style="padding-left: 30px;" src="${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.known['204_png'] }" height="170px;" width="150px;">
							</a>
						</c:if>
						<c:if test="${not hbv.known.containsKey('204_png') }">
							<div class="imgmiss">
								204位点测序失败，建议重测。
							</div>
						</c:if>
				    </div>
				</div>
				<!--位点突变-->
				<div class="m-box">
				 	<h2>5.恩曲他滨 FTC 突变检测
				 		<span style="float:right;padding-right: 30px" title="帮助" onclick="showModal('helpModal',0,5)"><button class="clear button button-glow button-circle button-rounded button-primary button-tiny"><span class="fa fa-thumbs-up"></span></button></span>
				 	</h2>
				    <div class="m-boxCon">
					    <c:if test="${hbv.known.containsKey('173_png') }">
							<a href="javascript:showBgOne('${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.known['173_10_png'] }');" >
								<img style="padding-left: 30px;" src="${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.known['173_png'] }" height="170px;" width="150px;">
							</a>
						</c:if>
						<c:if test="${not hbv.known.containsKey('173_png') }">
							<div class="imgmiss">
								173位点测序失败，建议重测。
							</div>
						</c:if>
						<c:if test="${hbv.known.containsKey('180_png') }">
							<a href="javascript:showBgOne('${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.known['180_10_png'] }');" >
								<img style="padding-left: 30px;" src="${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.known['180_png'] }" height="170px;" width="150px;">
							</a>
						</c:if>
						<c:if test="${not hbv.known.containsKey('180_png') }">
							<div class="imgmiss">
								180位点测序失败，建议重测。
							</div>
						</c:if>
						<c:if test="${hbv.known.containsKey('204_png') }">
					    	<a href="javascript:showBgOne('${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.known['204_10_png']}');" >
								<img style="padding-left: 30px;" src="${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.known['204_png'] }" height="170px;" width="150px;">
							</a>
						</c:if>
						<c:if test="${not hbv.known.containsKey('204_png') }">
							<div class="imgmiss">
								204位点测序失败，建议重测。
							</div>
						</c:if>
				    </div>
				</div>
				<!--位点突变-->
				<div class="m-box">
				 	<h2>6.恩替卡韦 ETV 突变检测
				 		<span style="float:right;padding-right: 30px" title="帮助" onclick="showModal('helpModal',0,6)"><button class="clear button button-glow button-circle button-rounded button-primary button-tiny"><span class="fa fa-thumbs-up"></span></button></span>
				 	</h2>
				    <div class="m-boxCon">
					    <c:if test="${hbv.known.containsKey('169_png') }">
							<a href="javascript:showBgOne('${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.known['169_10_png'] }');" >
								<img class="imgtop" style="padding-left: 30px;" name="imgSrc" src="${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.known['169_png'] }" height="170px;" width="150px;">
							</a>
						</c:if>
						<c:if test="${not hbv.known.containsKey('169_png') }">
							<div class="imgmiss">
								169位点测序失败，建议重测。
							</div>
						</c:if>
						<c:if test="${hbv.known.containsKey('180_png') }">
							<a href="javascript:showBgOne('${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.known['180_10_png'] }');" >
								<img class="imgtop" style="padding-left: 30px;" src="${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.known['180_png'] }" height="170px;" width="150px;">
							</a>
						</c:if>
						<c:if test="${not hbv.known.containsKey('180_png') }">
							<div class="imgmiss">
								180位点测序失败，建议重测。
							</div>
						</c:if>
						<c:if test="${hbv.known.containsKey('184_png') }">
							<a href="javascript:showBgOne('${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.known['184_10_png'] }');" >
								<img class="imgtop" style="padding-left: 30px;" name="imgSrc" src="${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.known['184_png'] }" height="170px;" width="150px;">
							</a>
						</c:if>
						<c:if test="${not hbv.known.containsKey('184_png') }">
							<div class="imgmiss">
								184位点测序失败，建议重测。
							</div>
						</c:if>
						<c:if test="${hbv.known.containsKey('202_png') }">
							<a href="javascript:showBgOne('${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.known['202_10_png'] }');" >
								<img class="imgtop" style="padding-left: 30px;" name="imgSrc" src="${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.known['202_png'] }" height="170px;" width="150px;">
							</a>
						</c:if>
						<c:if test="${not hbv.known.containsKey('202_png') }">
							<div class="imgmiss">
								202位点测序失败，建议重测。
							</div>
						</c:if>
						<c:if test="${hbv.known.containsKey('204_png') }">
					    	<a href="javascript:showBgOne('${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.known['204_10_png']}');" >
								<img class="imgtop" style="padding-left: 30px;" src="${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.known['204_png'] }" height="170px;" width="150px;">
							</a>
						</c:if>
						<c:if test="${not hbv.known.containsKey('204_png') }">
							<div class="imgmiss">
								204位点测序失败，建议重测。
							</div>
						</c:if>
						<c:if test="${hbv.known.containsKey('250_png') }">
					    	<a href="javascript:showBgOne('${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.known['250_10_png']}');" >
								<img class="imgtop" style="padding-left: 30px;" name="imgSrc" src="${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.known['250_png'] }" height="170px;" width="150px;">
							</a>
						</c:if>
						<c:if test="${not hbv.known.containsKey('250_png') }">
							<div class="imgmiss">
								250位点测序失败，建议重测。
							</div>
						</c:if>
				    </div>
				</div>
				<div class="w3cbbs" style="display: none;"></div>
				<div class="container" style="display: none;"></div>
				<!--位点突变-->
				<div class="m-box">
				 	  <h2>7.其他突变位点（该位点目前没有已发表文献支持，无明确临床意义）
				 	  	<span style="float:right;padding-right: 30px" title="帮助" onclick="showModal('helpModal',0,7)"><button class="clear button button-glow button-circle button-rounded button-primary button-tiny"><span class="fa fa-thumbs-up"></span></button></span>
				 	  </h2>
				    <div class="m-boxCon" id="otherPng">
					    <c:if test="${hbv.imgString!='' }">
					    	<c:set value="${fn:split(hbv.imgString, ',') }" var="imgs" />
					    	<c:forEach var="img" items="${imgs }">
					    		<a href="javascript:showBgTwo('${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${img.replace('png','10.png') }');" >
									<img class="imgtop" title="${img }" name="imgSrc" style="padding-left: 30px;" src="${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${img }" height="170px;" width="150px;">
								</a>
					    	</c:forEach>
					    </c:if>
				    </div>
				</div>
			</div>
		</c:if>
		<!--结论-->
		<div class="m-box">
		 	  <h2>参考结论（根据已发表文献得出以下参考结论）
		 	  <span style="float:right;padding-right: 30px" title="帮助" onclick="showModal('helpModal',2)"><button class="clear button button-glow button-circle button-rounded button-primary button-tiny"><span class="fa fa-thumbs-up"></span></button></span>
		 	  </h2>
		    <div class="m-boxCon result" id="resultDiv">
		    	${hbv.reporttxt.split('Other:')[0] }
		    </div>
		</div>
		<!--检测结果-->
		<div class="m-box">
			<h2><i class="i-edit"></i>原始序列</h2>
		    <div class="m-boxCon result" id="printDiv2">
				${hbv.seq }
		    </div>
		</div>
	    <div id="printDiv4" style="display: none;">
			${hbv.clinical }
	    </div>
		<!--染色体序列点图-->
		<div class="m-box" id="printDiv3">
			<h2><i class="i-dna"></i>原始峰图
				<span style="float:right;padding-right: 30px" title="帮助" onclick="showModal('helpModal',1)"><button class="clear button button-glow button-circle button-rounded button-primary button-tiny"><span class="fa fa-thumbs-up"></span></button></span>
			</h2>
			<c:if test="${hbv.original.containsKey('1_all_png') }">
			    <div class="m-boxCon result">
					<a href="javascript:showBg('${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.original['1_all_png'] }','listAll1Img');" >
						<img class="imgtop" src="${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.original['1_all_png'] }" style="width: 750px;height: 150px;" id="listAll1Img">
					</a>
			    </div>
		    </c:if>
		    <c:if test="${hbv.original.containsKey('2_all_png') }">
			    <div class="m-boxCon result">
					<a href="javascript:showBg('${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.original['2_all_png'] }','listAll2Img');" >
						<img class="imgtop" src="${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.original['2_all_png'] }" style="width: 750px;height: 150px;" id="listAll2Img">
					</a>
			    </div>
		    </c:if>
		    <c:if test="${hbv.original.containsKey('3_all_png') }">
			    <div class="m-boxCon result">
					<a href="javascript:showBg('${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.original['3_all_png'] }','listAll3Img');" >
						<img class="imgtop" src="${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.original['3_all_png'] }" style="width: 750px;height: 150px;" id="listAll3Img">
					</a>
			    </div>
		   	</c:if>
		    <c:if test="${hbv.original.containsKey('4_all_png') }">
			     <div class="m-boxCon result">
					<a href="javascript:showBg('${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.original['4_all_png'] }','listAll4Img');" >
						<img class="imgtop" src="${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.original['4_all_png'] }" style="width: 750px;height: 150px;" id="listAll4Img">
					</a>
			    </div>
		    </c:if>
		    <c:if test="${hbv.original.containsKey('5_all_png') }">
			    <div class="m-boxCon result">
					<a href="javascript:showBg('${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.original['5_all_png'] }','listAll5Img');" >
						<img class="imgtop" src="${path }/${hbv.userId }/${hbv.appId }/${hbv.dataKey }/SVG/${hbv.original['5_all_png'] }" style="width: 750px;height: 150px;" id="listAll5Img">
					</a>
			    </div>
		    </c:if>
		</div>
		<!--Celloud数据参数同比分析-->
		<div class="bg-analysis">
		    <div class="m-box">
		        <h2><i class="i-celloud"></i>Celloud数据参数同比分析</h2>
		        <div class="m-boxCon" id="charDiv">
		        </div>
		    </div>
		</div>
		<div class="center mt10 mb10">
			<a href="javascript:void(0)" onclick="change2()" class="btn btn-blue"><i class="i-view"></i>返回</a>
		</div>
	</div>
</div>

<div class="modal fade" id="helpModal" tabindex="-1" style="display: none;">
  <div class="modal-dialog">
   <div class="modal-content">
	<div class="modal-header well">
		<a class="close" data-dismiss="modal">×</a>
		<h4>注释</h4>
	</div>
	<div class="modal-body well">
		<div id="_showMore">
			<div class="lineheight y y1">	1. 耐药相关的位点突变(替诺福韦酯TDF突变检测规则)：
				<div style="line-height:1em;padding-top:10px" class="_leftShort">
					<span style="color:#9966FF">A194T</span>
				</div>
			</div>
			<div class="lineheight y y2">	1. 耐药相关的位点突变(替比夫定LDT突变检测规则)：
				<div style="line-height:1em;padding-top:10px" class="_leftShort">
					<span style="color:#9966FF">M204I</span>
				</div>
			</div>
			<div class="lineheight y y3">	1. 耐药相关的位点突变(阿德福韦ADV突变检测规则)：
				<div style="line-height:1em;padding-top:10px" class="_leftShort">
					<span style="color:#9966FF">A181V/T、N236T</span>
				</div>
			</div>
			<div class="lineheight y y4">	1. 耐药相关的位点突变(拉米夫定LAM突变检测规则)：
				<div style="line-height:1em;padding-top:10px" class="_leftShort">
					<span style="color:#9966FF">V173L、L180M、M204I/V</span>
				</div>
			</div>
			<div class="lineheight y y5">	1. 耐药相关的位点突变(恩曲他滨FTC突变检测规则)：
				<div style="line-height:1em;padding-top:10px" class="_leftShort">
					<span style="color:#9966FF">V173L、L180M、M204I/V</span>
				</div>
			</div>
			<div class="lineheight y y6">	1. 耐药相关的位点突变(恩替卡韦ETV突变检测规则)：
				<div style="line-height:1em;padding-top:10px" class="_leftShort">
					<span style="color:#9966FF">I169T、L180M、T184A/G/S/I/L/F、S202G/I、M204V、M250V/L/I</span>
				</div>
			</div>
			<div class="lineheight y y7">	1. 耐药相关的位点突变：
				<div style="line-height:1em;padding-top:10px" class="_leftShort">
					<span style="color:#9966FF">该位点目前没有已发表文献支持，无明确临床意义</span>
				</div>
			</div>
			<div class="lineheight">
				2. 突变结果举例解释：“M204M|V {A-A|G(67|33,2.1);G-G|T(72|28,2.5)}” ：
				<div class="_leftShort">（1）“M”表示野生型编码氨基酸为M；“204”表示氨基酸位点为204；“M|V”表示由原来的野生型M变为V；</div>
				<div class="_leftShort">（2）“{A-A|G(67|33,2.1);G-G|T(72|28,2.5)} ” 表示碱基的变化，其中一个位点由原来的A变为A|G杂合，比例为67比33；另一个碱基由原来的G变为G|T杂合，比例为72比28。</div>
	    		<div class="_leftShort">（3）比例值说明：A-A|G(67|33,2.1)，2.1为67和33的比值，该比例并不代表样本中该位点A和G的真实数量比例，只是代表该位点是A的可能性为67%，是G的可能性为33%。如果没有“(67|33,2.1）”出现，认为该位点100%发生突变。如果是A-A|G，说明该位点为A的可能性大；如果为A-G|A，说明该位点为G的 可能性大。当野生型的碱基（即A）的可能性大于突变碱基（即G）的可能性时，如果比值小于5我们认为该位点是突变；如果突变碱基（即G）的可能性大于野生型的碱基（即A）的可能性时，不论比值多少都认为发生了突变。</div>
	        </div>
			<div class="lineheight">3. “ *Wild Type: GCT ” 表示该位点的野生型为GCT</div>
			<div class="lineheight">4. 峰图中的 “ * ” 表示该位置的碱基发生了突变</div>
			<div class="lineheight">5. “ *Low quality ” 表示该区域的碱基测序质量值太低</div>
		</div>
		<div id="_showOne">
			<div class="lineheight">峰图中的 “ * ” 表示该位置的碱基发生了突变</div>
		</div>
		<div id="_showImg">
			<img class="imgtop" src="${path.replace('upload','') }resource/img/hbv.png" width="550px">
		</div>
	</div>
	<div class="modal-footer">
		<a class="btn close" href="javascript:void(0)" data-dismiss="modal"><i class="icon-ban-circle"></i> 取消</a>
	</div>
  </div>
 </div>
</div>
<script>
$(function() {
        $(window).manhuatoTop({
                showHeight : 100,
                speed : 1000
        });
});
function showBgTwo(src){
	$(window.parent.document).find("img[id='imageFullScreen']").css("width",1260);
	$(window.parent.document).find("img[id='imageFullScreen']").css("height",156);
	window.parent.showZoom(src);
}
function showBgOne(src){
	$(window.parent.document).find("img[id='imageFullScreen']").css("width",1260);
	$(window.parent.document).find("img[id='imageFullScreen']").css("height",156);
	window.parent.showZoom(src);
}
function showBg(src,id) { 
	var width = $("#" + id).width();
	var height = $("#" + id).height();
	$(window.parent.document).find("img[id='imageFullScreen']").css("width",width*1.5);
	$(window.parent.document).find("img[id='imageFullScreen']").css("height",height*1.5);
	window.parent.showZoom(src);
}
function change1(){
	$("#nomal").css("display","");
	$("#cfda").css("display","none");
}
function change2(){
	$("#nomal").css("display","none");
	$("#cfda").css("display","");
}
function showModal(id,flag,num){
	$("#_showOne").css("display","none");
	$("#_showMore").css("display","none");
	$("#_showImg").css("display","none");
	if(flag == 2){
		$("#_showImg").css("display","");
	}else if(flag == 1){
		$("#_showOne").css("display","");
	}else if(flag == 0){
		$(".y").css("display","none");
		if(num == 1){
			$(".y1").css("display","");
		}else if(num == 2){
			$(".y2").css("display","");
		}else if(num == 3){
			$(".y3").css("display","");
		}else if(num == 4){
			$(".y4").css("display","");
		}else if(num == 5){
			$(".y5").css("display","");
		}else if(num == 6){
			$(".y6").css("display","");
		}else if(num == 7){
			$(".y7").css("display","");
		}
		$("#_showMore").css("display","");
	}
	$("#"+id).modal("show");
	$("#"+id).find(".modal-body").scrollTop(0);
}
</script>
<style>
.lineheight{
	padding-top: 10px;
}
._leftShort{
	line-height:2;
	padding-left: 14px;
	text-align:justify;
}
.imgmiss{
	font-size: 12px;
    width: 130px;
    border: 1px solid;
    padding: 30px 10px 10px 10px;
    margin-top: 19px;
    margin-left: 30px;
    height: 90px;
    display:inline-block;
    border-color: black;
    color: #CC0000;
    vertical-align: top;
}
</style>