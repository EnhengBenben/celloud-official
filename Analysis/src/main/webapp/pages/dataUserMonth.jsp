<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="col-xs-12" style="height:350px;" id="userechartView">
</div>
<table id="userMonthDataList" class="table table-striped table-bordered table-hover">
	<thead>
		<tr>
			<th>上传月份</th>
			<th>数据个数(个)</th>
		</tr>
	</thead>
	<tbody>
		<s:if test="%{dataList.size()>0}">
			<s:iterator id="data" value="dataList">
				<tr>
					<td><a href="javascript:monthDataList(${data.user_id },'${data.yearMonth }');">${data.yearMonth }</a></td>
					<td>${data.fileNum }</td>
				</tr>
			</s:iterator>
		</s:if>
	</tbody>
</table>
<script type="text/javascript">
var getUserMonthDataJson= "getUserMonthDataJson";
var userId = $("#hideUserId").val();
	loadＵserEachMonth(	userId);
	//加载当前用户每月的数据图表
	function loadＵserEachMonth(userId){
		$.get("data!"+getUserMonthDataJson,{'userId':userId},function (data){
			if(data==null||data.length<1){
			$("#userechartView").css({"display":"none"});
				return;
			}
			var xAxis = new Array(data.length);
			var yAxis = new Array(data.length);
			for(var i=0;i<data.length;i++)
			{
				xAxis[i]=data[i].yearMonth;
				yAxis[i]=data[i].fileNum;
			}
			option = {
				    title : {
				        text: '',
				        subtext: ''
				    },
				    tooltip : {
				        trigger: 'axis'
				    },
				    legend: {
				        data:['文件数量',]
				    },
				    toolbox: {
				        show : true,
				        feature : {
				            mark : {show: true},
				            dataView : {show: true, readOnly: false},
				            magicType : {show: true, type: ['line', 'bar']},
				            restore : {show: true},
				            saveAsImage : {show: true}
				        }
				    },
				    calculable : true,
				    dataZoom : {
						show : true,
						realtime : true,
						start : 50,
						end : 100
					},
				    xAxis : [
				        {
				            type : 'category',
				            boundaryGap : false,
				            data :xAxis
				        }
				    ],
				    yAxis : [
				        {
				            type : 'value',
				        }
				    ],
				    series : [
				        {
				            name:'文件数量',
				            type:'line',
				            data:yAxis,
				        	itemStyle : {
								normal : {
									label : {
										show : true
									}
								}
							},
				            markPoint : {
				                data : [
				                    {type : 'max', name: '最大值'},
				                    {type : 'min', name: '最小值'}
				                ]
				            },
				            markLine : {
				                data : [
				                    {type : 'average', name: '平均值'}
				                ]
				            }
				        }
				    ]
				};
	        var myChart = echarts.init(document.getElementById('userechartView')); 
	        myChart.setOption(option);   
	});
	}
	
	jQuery(function($) {
		var oTable1 = $('#userMonthDataList').dataTable( {
		"aoColumns": [
	      null,null,null
		],
		iDisplayLength: 12
		} );
	})
	function monthDataList(userId,month){
		$("#thirdTitle").removeClass("hide");
		$("#_month").html(month);
		$.get("data!getUserMonthDetail",{"userId":userId,"month":month},function(responseText){
			$("#dataDiv").html(responseText);
		})
	}
</script>