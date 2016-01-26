<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="col-xs-12" style="height: 350px;" id="monthechartView"></div>
<table id="userMonthDataList"
	class="table table-striped table-bordered table-hover">
	<thead>
		<tr>
			<th>用户名</th>
			<th>所在医院</th>
			<th>数据个数(个)</th>
		</tr>
	</thead>

	<tbody>
		<s:if test="%{list.size()>0}">
			<s:iterator id="data" value="list">
				<tr>
					<td>${data.username }</td>
					<td>${data.company_name }</td>
					<td>${data.num }</td>
				</tr>
			</s:iterator>
		</s:if>
	</tbody>
</table>
<script type="text/javascript">
	var getUserDataInMonthJson = "getUserDataInMonthJson";
	var month = $("#_month").html();
	loadMonthAllCompany(month);
	function loadMonthAllCompany(month) {
		console.log(month);
		$.get("data!"+getUserDataInMonthJson, {"month":month}, function(data) {
			console.log(data);
			var xAxis = new Array(data.length);
			var yAxis = new Array(data.length);
			for (var i = 0; i < data.length; i++) {
				xAxis[data.length - 1 - i] = data[i].company_name;
				yAxis[data.length - 1 - i] = data[i].num;
			}
			option = {
				title : {
					text : '',
					subtext : ''
				},
				tooltip : {
					trigger : 'axis'
				},
				legend : {
					data : [ '文件个数' ]
				},
				toolbox : {	
					show : true,
					feature : {
						// mark : {show: true},
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
				dataZoom : {
					show : true,
					realtime : true,
					start : 50,
					end : 100
				},
				xAxis : [ {
					type : 'category',
					data : xAxis
				} ],
				yAxis : [ {
					type : 'value'
				} ],
				series : [ {
					name : '文件个数',
					type : 'bar',
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
			var myChart = echarts.init(document.getElementById('monthechartView'));
			myChart.setOption(option);
		});
	}
	jQuery(function($) {
		var oTable1 = $('#userMonthDataList').dataTable({
			"aoColumns" : [ null, null, null ],
			iDisplayLength : 100
		});
	})
	function monthDataList(userId, month) {
		$("#thirdTitle").removeClass("hide");
		$("#_month").html(month);
		$.get("data!getUserMonthDetail", {
			"userId" : userId,
			"month" : month
		}, function(responseText) {
			$("#dataDiv").html(responseText);
			
		})
	}

	
</script>