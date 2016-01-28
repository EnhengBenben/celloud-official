<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="row" id="reportBody">
	<div style="display: none;" id="detailDiv">
		<div class="m-file">
			<div class="toolbar">
				<a class="btn btn-celloud-success btn-flat" href="javascript:toPrintVSP()"><i class="fa fa-print"></i>打印报告</a>
			</div>
		</div>
		<div class="m-box" style="margin-top: 60px;">
		 	<h2>具有突变的肿瘤相关基因报告</h2>
		    <div class="m-boxCon">
		    	<s:property value="resultMap.geneResult" escape="false"/>
		    </div>
		</div>
	</div>
	<div id="alertDiv">
		<div class="m-box">
			<h2>提示</h2>
			<div class="m-boxCon">
			本报告来自于两个数据：<s:property value="resultMap.source0"/> 和 <s:property value="resultMap.source1"/> 
			</div>
		</div>
		<div class="center mb10 w500">
			<a href="javascript:void(0)" onclick="change1()" class="btn btn-blue"><i class="i-view"></i>查看详情</a>
		</div>
	</div>
</div>
<div class="row" id="printDiv" style="display: none;">
	<div class="w3cbbs">
		<div align="center">
			<img alt="" src="/celloud/images/print/vsp_title.png" height="400px" width="900px">
		</div>
		<h1>肿瘤样本基因检测与个体化治疗分析报告</h1>
		<div>
			<table class="table table-bordered table-condensed" style="width: 300px;">
				<tbody>
					<tr>
						<td>肿瘤类型：</td>
						<td><input name="print" type="text"/></td>
					</tr>
					<tr>
						<td>姓    名： </td>
						<td><input name="print" type="text"/></td>
					</tr>
					<tr>
						<td>取样日期： </td>
						<td><input name="print" type="text"/></td>
					</tr>
					<tr>
						<td>报告日期： </td>
						<td><input name="print" type="text"/></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div align="center">
			<h2>检测机构：上海华点云生物科技有限公司</h2>
		</div>
	</div>
	<div class="m-box">
		<h1>相关检测信息及分析报告</h1>
	 	<h2>一、基本信息</h2>
	 	<h2>1.送检基本信息</h2>
	    <div class="m-boxCon">
	    	<table class="table table-bordered table-condensed">
	    		<tbody>
	    			<tr>
	    				<td>姓名</td>
	    				<td><input name="print" type="text"/></td>
	    				<td>取样日期</td>
	    				<td><input name="print" type="text"/></td>
	    			</tr>
	    			<tr>
	    				<td>性别</td>
	    				<td><input name="print" type="text"/></td>
	    				<td>样本来源</td>
	    				<td><input name="print" type="text"/></td>
	    			</tr>
	    			<tr>
	    				<td>年龄</td>
	    				<td><input name="print" type="text"/></td>
	    				<td>临床诊断</td>
	    				<td><input name="print" type="text"/></td>
	    			</tr>
	    			<tr>
	    				<td>病理诊断</td>
	    				<td><input name="print" type="text"/></td>
	    				<td>分析日期</td>
	    				<td><input name="print" type="text"/></td>
	    			</tr>
	    			<tr>
	    				<td>病理位置</td>
	    				<td><input name="print" type="text"/></td>
	    				<td>分子生物实验操作</td>
	    				<td><input name="print" type="text"/></td>
	    			</tr>
	    			<tr>
	    				<td>病理类型</td>
	    				<td><input name="print" type="text"/></td>
	    				<td>基因分析</td>
	    				<td><input name="print" type="text"/></td>
	    			</tr>
	    		</tbody>
	    	</table>
	    </div>
	</div>
	<div class="m-box">
	 	<h2>2.送检要求</h2>
	    <div class="m-boxCon">
	    	<s:property value="resultMap.geneTable" escape="false"/>
	    </div>
	</div>
	<div class="m-box">
		<h2>二、 基因检测结果简述：</h2>
	    <div class="m-boxCon">
	    	<p>经测序分析,具有突变的肿瘤相关基因报告如下:</p>
	    	<s:property value="resultMap.geneResult" escape="false"/>
	    </div>
	    <div>
		注：<br/>
		1. 该检测结果仅对本次送检样本负责，由于存在异质性的现象，不能反映全部病变的性质。<br/>
		2. 由于检测样本不能长期保存，对检测结果有任何异议，需要检测复核请与24小时内提出。<br/>
		3. 该检测结果仅供科研参考<br/>
	    </div>
	</div>
	<br/>
	<h2>三、 检测基因（主要基因）的详细信息</h2>
	<div class="m-box">
		<h2>1.详细信息</h2>
	    <div class="m-boxCon">
	    	<s:property value="resultMap.des" escape="false"/>
	    </div>
	</div>
	<div class="m-box">
	 	<h2>2.检测结果</h2>
	    <div class="m-boxCon">
	    	<s:property value="resultMap.variant" escape="false"/>
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
function change1(){
	$("#reportBody").scrollTop(0);
	$("#detailDiv").css("display","");
	$("#alertDiv").css("display","none");
}
function change2(){
	$("#reportBody").scrollTop(0);
	$("#detailDiv").css("display","none");
	$("#alertDiv").css("display","");
}
</script>