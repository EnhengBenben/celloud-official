<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="breadcrumbs" id="breadcrumbs">
	<script type="text/javascript">
		try {
			ace.settings.check('breadcrumbs', 'fixed');
		} catch (e) {
		}
	</script>
	<ul class="breadcrumb">
		<li>
			<i class="icon-hospital"></i>
			<a href="#">医院统计</a>
		</li>
		<li class="active">
			<a onclick="toHospitalList()">医院详细信息</a>
		</li>
		<li>${company.company_name }</li>
	</ul>
	<!-- .breadcrumb -->
</div>
<div class="col-sm-12">
	<div class="row">
		<div class="col-xs-12">
			<div class="widget-box">
				<div class="widget-header widget-header-flat">
					<h4 class="smaller">基本信息</h4>
				</div>
				<div class="widget-body">
					<div class="widget-main">
						<dl id="dt-list-1" class="dl-horizontal" style="word-break: break-all; word-wrap: break-word; line-height: 2em">
							<dt>医院名称：</dt>
							<dd>${company.company_name }</dd>
							<c:if test="${fn:length(company.address)>0}">
								<dt>医院地址：</dt>
								<dd>${company.address }</dd>
							</c:if>
							<c:if test="${fn:length(company.tel)>0}">
								<dt>医院电话：</dt>
								<dd>${company.tel }</dd>
							</c:if>
							<dt>用户数量：</dt>
							<dd>${company.userNum }</dd>
							<dt>用户名称：</dt>
							<dd>${company.userNames }</dd>
							<dt>包含部门：</dt>
							<dd>${company.deptNames }</dd>
							<dt>入驻时间：</dt>
							<dd>
								<fmt:formatDate type="both" value="${company.create_date }" pattern="yyyy-MM-dd" />
							</dd>
							<dt>数据数量：</dt>
							<dd>${company.fileNum }&nbsp;个</dd>
							<dt>数据大小：</dt>
							<dd>
								<fmt:formatNumber pattern="0.00" value="${company.size/ (1024 * 1024 * 1024) }" />
								&nbsp;(GB)
							</dd>
							<dt>报告数量：</dt>
							<dd>${company.reportNum }&nbsp;个</dd>
						</dl>
					</div>
				</div>
				<!-- App -->
				<div class="col-xs-12" style="height: 450px;" id="runTimeDiv"></div>
			</div>
		</div>
	</div>
	<!-- row -->
</div>
<script>
	//	 var appRunGroupInMonth = "getHospitaAppRunGroupByMonth";
	//	 var appRunGroupInWeek = "getHospitaAppRunGroupByWeek";
	//	 var fileGroupInMonth = "getHospitaMonthUpload";
	//	 var fileGroupInWeek = "getHospitsWeekUpload";
	
	getHospitaAppRunNum();
	
	//大客户下医院各App运行次数
	function getHospitaAppRunNum() {
		$.get("company!getHospitaAppRunNum", {
			"company.company_id" : '${company.company_id }'
		}, function(data) {
			console.log(data);
			if (data.length <= 0) {
				$("#runTimeDiv").css({
					"display" : "none"
				});
				return;
			}
			var xAxis = new Array(data.length);
			var yAxis = new Array(data.length);
			for (var i = 0; i < data.length; i++) {
				xAxis[i] = data[i].app_name;
				yAxis[i] = data[i].runNum;
			}
			var option = makeOptionScrollUnit(xAxis, yAxis, "各App运行", barType, 0, 10);
			var myChart = echarts.init(document.getElementById('runTimeDiv'));
			myChart.setOption(option);
		});
	}
	/*
	function getHospitaMonthUpload(groupByTag) {
	//大客户下医院上传文件个数统计
	$.get("company!" + groupByTag, {
	"company.company_id" : '${company.company_id }'
	}, function(data) {
	if (data.length <= 0) {
	$("#btn_group_file_num").css('display', 'none');
	$("#upLoadFileNumDiv").css('display', 'none');
	return;
	}
	console.log(data);
	var xAxis = new Array(data.length);
	var yAxisNum = new Array(data.length);
	var yAxisSize = new Array(data.length);
	
	for (var i = 0; i < data.length; i++) {
	if (groupByTag == fileGroupInMonth)
	xAxis[i] = data[i].yearMonth;
	else {
	var sun = new Date(data[i].weekDate);
	xAxis[i] = data[i].weekDate + '~' + sun.sunday();
	
	}
	yAxisNum[i] = data[i].fileNum;
	yAxisSize[i] = parseFloat((data[i].size / (1024 * 1024 * 1024)).toFixed(2));
	
	}
	option = {
	title : {
	text : '文件数量、大小统计',
	subtext : ''
	},
	tooltip : {
	trigger : 'axis',
	axisPointer : { // 坐标轴指示器，坐标轴触发有效
	type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
	}
	},
	legend : {
	data : [ '文件大小(GB)', '文件数量' ]
	},
	dataZoom : {
	show : true,
	realtime : true,
	start : 50,
	end : 100
	},
	toolbox : {
	show : true,
	feature : {
	dataView : {
	show : true,
	readOnly : false
	},
	magicType : {
	show : true,
	type : [ 'line', 'bar' ]
	},
	restore : {
	show : true
	},
	saveAsImage : {
	show : true
	}
	}
	},
	calculable : true,
	xAxis : [ {
	type : 'category',
	data : xAxis,
	boundaryGap : false,
	} ],
	yAxis : [ {
	name : '数量',
	type : 'value'
	}, {
	type : 'value',
	name : '大小',
	axisLabel : {
	formatter : '{value} (GB)'
	}
	} ],
	series : [ {
	name : '文件大小(GB)',
	type : 'line',
	data : yAxisSize,
	itemStyle : {
	normal : {
	label : {
	show : true
	}
	}
	},
	markPoint : {
	data : [ {
	type : 'max',
	name : '最大值'
	}, {
	type : 'min',
	name : '最小值'
	} ]
	},
	markLine : {
	data : [ {
	type : 'average',
	name : '平均值'
	} ]
	}
	}, {
	name : '文件数量',
	type : 'line',
	data : yAxisNum,
	markPoint : {
	data : [ {
	type : 'max',
	name : '最大值'
	}, {
	type : 'min',
	name : '最小值'
	} ]
	},
	markLine : {
	data : [ {
	type : 'average',
	name : '平均值'
	} ]
	}
	} ]
	};
	var myChart = echarts.init(document.getElementById('upLoadFileNumDiv'));
	myChart.setOption(option);
	});
	}

	function getAppRunInGroup(groupByTag) {
	$.get("company!" + groupByTag, {
	"company.company_id" : '${company.company_id }'
	}, function(data) {
	if (data.length <= 0) {
	$("#appRunTimeMonthDiv").css('display', 'none');
	$("#btn_group_app_run_num").css('display', 'none');
	return;
	}
	var xAxis = new Array(data.length);
	var yAxis = new Array(data.length);
	for (var i = 0; i < data.length; i++) {
	if (groupByTag == appRunGroupInMonth)
	xAxis[i] = data[i].yearMonth;
	else {
	var sun = new Date(data[i].weekDate);
	xAxis[i] = data[i].weekDate + '~' + sun.sunday();
	}
	yAxis[i] = data[i].runNum;
	}
	option = {
	title : {
	text : 'App运行统计',
	subtext : ''
	},
	tooltip : {
	trigger : 'axis'
	},
	legend : {
	data : [ 'App运行次数' ]
	},
	dataZoom : {
	show : true,
	realtime : true,
	start : 50,
	end : 100
	},
	toolbox : {
	show : true,
	feature : {
	dataView : {
	show : true,
	readOnly : false
	},
	magicType : {
	show : true,
	type : [ 'line', 'bar' ]
	},
	restore : {
	show : true
	},
	saveAsImage : {
	show : true
	}
	}
	},
	calculable : true,
	xAxis : [ {
	type : 'category',
	data : xAxis,
	boundaryGap : false,
	} ],
	yAxis : [ {
	type : 'value',
	} ],
	series : [ {
	name : 'App运行次数',
	type : 'line',
	data : yAxis,
	itemStyle : {
	normal : {
	label : {
	show : true
	}
	}
	},
	markPoint : {
	data : [ {
	type : 'max',
	name : '最大值'
	}, {
	type : 'min',
	name : '最小值'
	} ]
	},
	markLine : {
	data : [ {
	type : 'average',
	name : '平均值'
	} ]
	}
	} ]
	};
	var myChart = echarts.init(document.getElementById('appRunTimeMonthDiv'));
	myChart.setOption(option);
	});
	}*/
</script>