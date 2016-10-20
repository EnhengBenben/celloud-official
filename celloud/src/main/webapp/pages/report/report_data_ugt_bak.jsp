<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div>
	<div class="m-file">
	    <dl class="dl-horizontal datareport-title">
          <dt>项目名称：</dt>
          <dd>${project.projectName}</dd>
          <dt>应用名称：</dt>
          <dd>${ugt.appName}</dd>
          <dt>文件名称：</dt>
          <dd class="force-break">${ugt.fileName}(${ugt.dataKey})</dd>
        </dl>
	</div>
	<div class="m-box">
		<h2><i class="i-edit"></i>突变类型</h2>
	    <div class="m-boxCon result">
			<c:if test="${ugt.position!=null && ugt.position!='' }">
	    		${ugt.position }
    		</c:if>
    		<c:if test="${ugt.position==null || ugt.position=='' }">
    			未检测到突变
    		</c:if>
	    </div>
	</div>
	<div class="m-box m-box-yc">
		<h2>
			<i class="i-edit"></i>SNP
		</h2>
	    <div class="m-boxCon result" id="report_tb">
			<c:if test="${ugt.mutationPosition!=null }">
		    	${ugt.mutationPosition }
	    	</c:if>
	    	<c:if test="${ugt.mutationPosition==null }">
	    		数据正常，未找到其他突变。
	    	</c:if>
	    </div>
	</div>
	<div class="m-box">
		<h2><i class="i-edit"></i>原始序列</h2>
	    <div class="m-boxCon result" id="_seq">
			${ugt.seq }
	    </div>
	</div>
	<div class="m-box">
		<h2><i class="i-dna"></i>原始峰图</h2>
		<c:if test="${ugt.original!=null }">
	    	<c:forEach var="original" items="${ugt.original }" varStatus="st">
				<div class="m-boxCon result">
						<a href="javascript:bigOrigin('${uploadPath}${ugt.userId }/${ugt.appId }/${ugt.dataKey }/SVG/${original }','original${st.count }');" >
							<img src="${uploadPath}${ugt.userId }/${ugt.appId }/${ugt.dataKey }/SVG/${original }" class="originImg" id="original${st.count }">
						</a>
			    </div>
	    	</c:forEach>
		</c:if>
     	<c:if test="${ugt.original==null }">
		    <div class="m-boxCon result">
		    样本异常，无法检测
		    </div>
		</c:if>
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
