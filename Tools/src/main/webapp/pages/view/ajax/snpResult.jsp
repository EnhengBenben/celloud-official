<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
	<style>
	.table-bordered{
		border-left:1px solid #9dcddd;
	}
	#tab4 th, #tab5 th{
 	white-space:normal; 
	word-break : break-all;
	word-wrap : break-word ;
	}
	</style>
<div class="row" style="margin-left: 20px;">
	<h3>文件名&nbsp;：&nbsp;<s:property value="%{resultMap.pagePath.replace('/SVG','').substring(resultMap.pagePath.replace('/SVG','').lastIndexOf('/')+1,resultMap.pagePath.replace('/SVG','').length())}"/>(<s:property value="resultMap.fileName"/>)</h3>
	<font size="4"><s:property value="resultMap.snpType.replace('Type','类型')" escape="false"/></font>
	<br/>
	<div>
		1.替比夫定 LDT 突变检测
	</div>
	<div>
	<br />
			<img style="padding-left: 30px;" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg180"/>" height="150px;" width="150px;">
			<img style="padding-left: 30px;" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg204"/>" height="150px;" width="150px;">
	</div>
	<div>
		<br />
		2.恩替卡韦 ETV 突变检测
	</div>
	<div>
	<br />
			<img style="padding-left: 30px;" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg184"/>" height="150px;" width="150px;">
			<img style="padding-left: 30px;" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg250"/>" height="150px;" width="150px;">
			<img style="padding-left: 30px;" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg202"/>" height="150px;" width="150px;">
			<img style="padding-left: 30px;" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg204"/>" height="150px;" width="150px;">
	</div>
	<div>
		<br/>
		3.拉米夫定 LAM 突变检测
	</div>
	<div>
	<br />
			<img style="padding-left: 30px;" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg173"/>" height="150px;" width="150px;">
			<img style="padding-left: 30px;" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg207"/>" height="150px;" width="150px;">
			<img style="padding-left: 30px;" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg180"/>" height="150px;" width="150px;">
			<img style="padding-left: 30px;" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg213"/>" height="150px;" width="150px;">
			<img style="padding-left: 30px;" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg204"/>" height="150px;" width="150px;">
	</div>
	<div>
		<br />
		4.阿德福韦 ADV 突变检测
	</div>
	<div>
	<br />
			<img style="padding-left: 30px;" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg181"/>" height="150px;" width="150px;">
			<img style="padding-left: 30px;" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg236"/>" height="150px;" width="150px;">
			<img style="padding-left: 30px;" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg214"/>" height="150px;" width="150px;">
			<img style="padding-left: 30px;" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg237"/>" height="150px;" width="150px;">
			<img style="padding-left: 30px;" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg215"/>" height="150px;" width="150px;">
			<img style="padding-left: 30px;" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg238"/>" height="150px;" width="150px;">
	</div>
	<div>
		<br />
			5.其他检测结果
	</div>
	<div>
	<br />
	<s:generator separator=";" val="resultMap.listNew">
		<s:iterator status="st">
			<img style="padding-left: 30px;" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property/>" height="150px;" width="150px;">
		</s:iterator>
	</s:generator>
	</div>
	<div>
		<br />
		结论
	</div>
	<div>
		<s:property value="resultMap.result" escape="false"/>
	</div>
	<div>
		<br />
		所有检测结果
	</div>
		<div>
			<img src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll1"/>" style="width: 750px;height: 150px;" >
		</div>
		<div>
			<img src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll2"/>" style="width: 750px;height: 150px;" >
		</div>
		<div>
			<img src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll3"/>" style="width: 750px;height: 150px;" >
		</div>
		<div>
			<img src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll4"/>" style="width: 750px;height: 150px;" >
		</div>
</div>
<script>
$(function() {
        $(window).manhuatoTop({
                showHeight : 100,
                speed : 1000
        });
});
</script>