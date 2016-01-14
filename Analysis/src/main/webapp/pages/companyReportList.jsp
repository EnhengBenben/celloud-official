<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="breadcrumbs" id="breadcrumbs">
	<script type="text/javascript">
		try {
			ace.settings.check('breadcrumbs', 'fixed')
		} catch (e) {
		}
	</script>
	<ul class="breadcrumb">
		<li>
			<i class="icon-hospital"></i>
			<a href="#">医院统计</a>
		</li>
		<li class="active"><a onclick = "hospitalBigUserCount()">医院报告统计</a></li>
	</ul>
</div>
<div class="page-content">
	<div class="row">
	<span>${appList }</span>
	 <span>${mapList }</span>
	
	<c:if test="${fn:length(appList) > 0&&fn:length(mapList) > 0 }">
                <div class="table-responsive" id="dataDiv">
                    <table id="allUserDataList" class="table table-striped table-bordered table-hover">
                        <thead>
                            <tr>
                                <th>医院编码</th>
                                <th>医院名称</th>
                                <c:forEach items="${appList}" var="item">
                                <th>${item.app_name}</th>
                                </c:forEach>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${mapList}" var="item">
                                <tr>
                                    <td>
                                        ${item.company_id }
                                    </td>
                                    <td>${item.app_id }</td>
                                    <td>${item.runNum}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>
	</div>
</div>