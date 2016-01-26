<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link rel="stylesheet" href="./css/xenon-components.css" />
<link rel="stylesheet" href="./css/linecons.css" />
<link rel="stylesheet" href="./css/xenon-core.css" />
<div class="breadcrumbs" id="breadcrumbs">
	<script type="text/jaicon-hospitalvascript">
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
		<li class="active" onclick="getPreDataView()">医院总览</li>
	</ul>
	<!-- .breadcrumb -->
</div>
<div class="page-content">
	<div class="col-xs-12">
		<div class="row">
			<div class="col-sm-3">
				<div class="xe-widget xe-counter-block" data-count=".num" data-from="0" data-to="${resultMap.num }" data-suffix="(个)" data-duration="2">
					<div class="xe-upper">
						<div class="xe-label">
							<strong>医院数量:</strong>
							<strong class="num">${resultMap.num }</strong>
						</div>
					</div>
					<div class="xe-lower">
						<div class="border"></div>
					</div>
				</div>
			</div>
			<div class="col-sm-3">
				<div class="xe-widget xe-counter-block xe-counter-block-blue" data-suffix="(个)" data-count=".num" data-from="0" data-to="${resultMap.userNum }"
					data-duration="2" data-easing="false">
					<div class="xe-upper">
						<div class="xe-label">
							<strong>用户数量:</strong>
							<strong class="num">${resultMap.userNum }</strong>
						</div>
					</div>
					<div class="xe-lower">
						<div class="border"></div>
					</div>
				</div>
			</div>
		</div>
		<h3 class="header smaller lighter green ">大客户新增用户统计</h3>
		<c:if test="${ userRole=='2'}">
			<div id="newCmpBigUser" style="height: 300px;"></div>
		</c:if>
		<div id="main" style="height: 300px"></div>
		<div id="newCompany" style="height: 300px"></div>

		<div class="row">
			<c:if test="${resultMap.mapList!=null && fn:length(resultMap.mapList)>0 }">
				<div class="table-responsive table-div" id="dataDiv">
					<table id="newCompanyList" class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th class="min-w-200">月份</th>
								<th class="min-w-200">医院新增数量</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${resultMap.mapList}" var="item">
								<tr>
									<td>${item.createDate }</td>
									<td>${item.num }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</c:if>
		</div>
		<div class="row">
			<div class="col-xs-12">
				<h3 class="header smaller lighter green">用户地理分布</h3>
				<div class="col-sm-9" style="height: 500px;" id="map"></div>
				<div class="col-sm-2" style="height: 500px;">
					<ul id="listCompany">
					</ul>
				</div>
			</div>
		</div>


	</div>
</div>
<script type="text/javascript">
	jQuery(function($) {
		chars();
		$.get("company!getProvince", function(result) {
			var data = "[";
			for ( var i in result) {
				var map = result[i];
				data += "{name: '" + map['province'] + "', value: " + map['num'] + "},";
			}
			data += "]";
			showView(eval(data));
		});
		
		var oTable1 = $('#newCompanyList').dataTable({
			"aoColumns" : [ null, null ],
			iDisplayLength : 10,
			"aaSorting" : [ [ 0, "desc" ] ],
		});
	});
	
	var getBigUserMonthNewCmpURL = "home!getPreDataViewBigUesrNewCmp";
	$.get(getBigUserMonthNewCmpURL, {}, function(data) {
		var listCmp = data["companyNames"];
		var xAxis = data["xAxis"];
		var opt
		for (var i = 0; i < listCmp.length; i++) {
			var temp = data[listCmp[i]];
			var yAxis = new Array(temp.length);
			for (var j = 0; j < temp.length; j++) {
				yAxis[j] = temp[j].num;
			}
			if (i == 0) {
				opt = makeOptionScrollUnit(xAxis, yAxis, listCmp[0], lineType, 100, xAxis.length, null, null, "test");
			} else {
				opt = makeOptionAdd(opt, yAxis, listCmp[i], lineType);
			}
		}
		var bigUserView = echarts.init(document.getElementById('newCmpBigUser'));
		bigUserView.setOption(opt);
	});
	
	function showView(data) {
		option = {
			title : {
				text : '',
				x : 'center'
			},
			tooltip : {
				trigger : 'item'
			},
			dataRange : {
				x : 'right',
				y : 'bottom',
				min : 0,
				max : 10,
				calculable : true,
				color : [ 'maroon', 'purple', 'red', 'orange', 'yellow', 'lightgreen' ]
			},
			toolbox : {
				show : true,
				orient : 'vertical',
				x : 'right',
				y : 'center',
				feature : {
					restore : {
						show : true
					},
					saveAsImage : {
						show : true
					}
				}
			},
			series : [ {
				name : 'pm2.5',
				type : 'map',
				mapType : 'china',
				mapLocation : {
					x : 60,
					width : "600",
					height : "500"
				},
				hoverable : false,
				roam : true,
				data : [],
				scaleLimit : {
					min : 0.9,
					max : 1.1
				},
				selectedMode : 'single',
				itemStyle : {
					normal : {
						label : {
							show : true
						}
					},
					emphasis : {
						label : {
							show : true
						}
					}
				},
				geoCoord : {
					"北京" : [ 116.4, 39.9 ],
					"天津" : [ 117.2, 39.12 ],
					"上海" : [ 121.47, 31.23 ],
					"重庆" : [ 106.55, 29.57 ],
					"河北" : [ 114.52, 38.05 ],
					"山西" : [ 112.55, 37.87 ],
					"辽宁" : [ 123.43, 41.8 ],
					"吉林" : [ 125.32, 43.9 ],
					"黑龙江" : [ 126.53, 45.8 ],
					"江苏" : [ 118.78, 32.07 ],
					"浙江" : [ 120.15, 30.28 ],
					"安徽" : [ 117.25, 30.83 ],
					"福建" : [ 119.3, 26.08 ],
					"江西" : [ 115.85, 28.68 ],
					"山东" : [ 116.98, 36.67 ],
					"河南" : [ 113.62, 34.75 ],
					"湖北" : [ 114.3, 30.6 ],
					"湖南" : [ 112.93, 28.23 ],
					"广东" : [ 113.27, 23.13 ],
					"海南" : [ 110.32, 20.03 ],
					"四川" : [ 104.07, 30.67 ],
					"贵州" : [ 106.63, 26.65 ],
					"云南" : [ 102.72, 25.05 ],
					"陕西" : [ 108.93, 34.27 ],
					"甘肃" : [ 103.82, 36.07 ],
					"青海" : [ 101.78, 36.62 ],
					"西藏自治区" : [ 91.13, 29.65 ],
					"宁夏回族自治区" : [ 106.28, 38.47 ],
					"广西壮族自治区" : [ 108.37, 22.82 ],
					"新疆维吾尔" : [ 87.62, 43.82 ],
					"内蒙古自治区" : [ 111.73, 40.83 ],
					"香港" : [ 115.08, 22.2 ],
					"澳门" : [ 114.33, 21.33 ],
					"台北市" : [ 121.5, 25.03 ]
				}
			}, {
				name : '医院',
				type : 'map',
				mapType : 'china',
				data : [],
				markPoint : {
					symbol : 'emptyCircle',
					symbolSize : function(v) {
						return 10 + v / 100
					},
					effect : {
						show : true,
						shadowBlur : 0
					},
					data : data
				}
			} ]
		};
		var myChart = echarts.init(document.getElementById('map'));
		myChart.on(echarts.config.EVENT.MAP_SELECTED, function(param) {
			var selected = param.selected;
			var str = '';
			for ( var p in selected) {
				if (selected[p]) {
					str += p + ' ';
				}
			}
			$.post("company!getCompanyProvince", {
				"company.province" : str.replace(/(^\s*)|(\s*$)/g, '')
			}, function(result) {
				$('#listCompany').children().remove();
				for (item in result) {
					var li = $("<li>" + result[item].company_name + "</li>");
					$("#listCompany").append(li);
				}
			});
		});
		myChart.setOption(option);
	}
	function chars() {
		$.get("company!getCompanyNumEveryMonth", {}, function(result) {
			var xAxis = new Array(result.length);
			var yAxis = new Array(result.length);
			var yAxisAdd = new Array(result.length);
			for (var i = 0; i < result.length; i++) {
				xAxis[i] = result[i].createDate;
				yAxis[i] = result[i].num;
				if (i == 0) {
					yAxisAdd[0] = result[0].num;
				} else {
					yAxisAdd[i] = result[i].num + yAxisAdd[i - 1];
					
				}
			}
			
			var option = makeOptionScrollUnit(xAxis, yAxis, "月新增医院数量", lineType, 100, xAxis.length);
			
			var newoption = makeOptionScrollUnit(xAxis, yAxisAdd, "新增医院累积曲线图", lineType, 100, xAxis.length, "阴影");
			
			//	option = makeOptionAdd(option, yAxisAdd, "新增医院累积曲线图", lineType);
			
			// 基于准备好的dom，初始化echarts图表
			var myChart = echarts.init(document.getElementById('main'));
			var newCompany = echarts.init(document.getElementById('newCompany'));
			newCompany.setOption(newoption);
			myChart.setOption(option);
		})
	}
</script>
<script type="text/javascript" src="./js/joinable.js"></script>
<script type="text/javascript" src="./js/xenon-custom.js"></script>
<script type="text/javascript" src="./js/xenon-widgets.js"></script>