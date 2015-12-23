<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main"
	style="height: 400px; width: 80%; border: 1px solid #ddd; margin: 20px"></div>
<script type="text/javascript">
	function chars() {
		$.get("company!getCompanyNumEveryMonth", {}, function(result) {
			var xAxis = eval("[" + result.timeLine + "]");
			var yAxis = eval("[" + result.data + "]");
			// 基于准备好的dom，初始化echarts图表
			var myChart = echarts.init(document.getElementById('main'));
			option = {
				tooltip : {
					trigger : 'axis'
				},
				legend : {
					data : [ '月新增医院数量' ]
				},
				toolbox : {
					show : true,
					feature : {
						mark : {
							show : true
						},
						dataZoom : {
							show : true
						},
						dataView : {
							show : true
						},
						magicType : {
							show : true,
							type : [ 'line', 'bar', 'stack', 'tiled' ]
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
				dataZoom : {
					show : true,
					realtime : true,
					start : 20,
					end : 80
				},
				xAxis : [ {
					type : 'category',
					boundaryGap : false,
					data : xAxis
				} ],
				yAxis : [ {
					type : 'value'
				} ],
				series : [ {
					name : '月新增医院数量',
					type : 'line',
					data : yAxis,
					itemStyle : {
						normal : {
							label : {
								show : true
							}
						}
					},
				} ]
			};
			// 为echarts对象加载数据 
			myChart.setOption(option);
		})
	}
	chars();
</script>