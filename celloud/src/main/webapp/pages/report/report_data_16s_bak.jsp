<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div>
	<div class="m-file">
		<dl class="dl-horizontal datareport-title">
          <dt>项目名称：</dt>
          <dd>${project.projectName}</dd>
          <dt>应用名称：</dt>
          <dd>${s16.appName}</dd>
          <dt>文件名称：</dt>
          <dd class="force-break">${s16.fileName}(${s16.dataKey})</dd>
        </dl>
	</div>
	<div class="m-box">
		<h2><i class="i-report1"></i>数据统计</h2>
		<div class="m-boxCon" id="16sResult">
			${s16.resultTable }
		</div>
	</div>
</div>
<script type="text/javascript">
$("#16sResult").find("tr").each(function(i){
  if(i==0){
    length = $(this).find("td").length;
  }else{
    tdlength = $(this).find("td").length;
    if(tdlength<length){
      $(this).children("td").eq(1).attr("colspan",5);
    }
  }
});
</script>
