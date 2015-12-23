<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="col-sm-12">
	<div class="row">
		<div class="col-xs-12">
			<div class="widget-box">
				<div class="widget-header widget-header-flat">
					<h4 class="smaller">基本信息</h4>
				</div>
				<div class="widget-body">
					<div class="widget-main">
						<dl id="dt-list-1" class="dl-horizontal"
							style="word-break: break-all; word-wrap: break-word; line-height: 2em">
							<dt>医院名称：</dt>
							<dd>${company.company_name }</dd>
							<dt>医院地址：</dt>
							<dd>${company.address }</dd>
							<dt>医院电话：</dt>
							<dd>${company.tel }</dd>
							<dt>用户数量：</dt>
							<dd>${company.userNum }</dd>
							<dt>用户名称：</dt>
							<dd>${company.userNames }</dd>
							<dt>用户数量：</dt>
							<dd>${company.userNum }&nbsp;个</dd>
							<dt>包含部门：</dt>
							<dd>${company.deptNames }</dd>
							<dt>数据数量：</dt>
							<dd>${company.fileNum }&nbsp;个</dd>
							<dt>数据大小：</dt>
							<dd>
								<fmt:formatNumber pattern="0.00"
									value="${company.fileSize/ (1024 * 1024 * 1024) }" />
								&nbsp;(GB)
							</dd>
							<dt>报告数量：</dt>
							<dd>${company.reportNum }&nbsp;个</dd>
							<dt>入驻时间：</dt>
							<dd>
								<fmt:formatDate type="both" value="${company.create_date }" />
							</dd>
						</dl>
					</div>
				</div>
				<!-- App -->
				<div class="col-xs-12" style="height: 450px;" id="runTimeDiv"></div>

				<!--医院本月上传文件上传个数 -->
				<div class="col-xs-12 margin:20px btn-group" id="btn_group_file_num"
					data-toggle="buttons">
					<label class="btn btn-primary"
						onClick="javascript:fileNumMonthOnClick()"> <input
						type="radio" name="options" id="btn_month_upload_num"> 月统计
					</label> <label class="btn btn-primary"
						onClick="javascript:fileNumWeekOnClick()"> <input
						type="radio" name="options" id="btn_week_upload_num"> 周统计
					</label>
				</div>
				<div class="col-xs-12" style="height: 450px;" id="upLoadFileNumDiv"></div>

				<!-- 统计医院上传文件大小
				<div class="col-xs-12 margin:20px btn-group"
					id="btn_group_file_size" data-toggle="buttons">
					<label class="btn btn-primary"
						onClick="javascript:fileSizeMonthOnClick()"> <input
						type="radio" name="options" id="btn_month_upload_size">
						月统计
					</label> <label class="btn btn-primary"
						onClick="javascript:fileSizeWeekOnClick()"> <input
						type="radio" name="options" id="btn_week_upload_size"> 周统计
					</label>
				</div>
				<div class="col-xs-12" style="height: 450px;" id="upLoadFileSizeDiv"></div>
				 -->
				<!-- 统计医院上传文件数量-->
				<div class="col-xs-12 margin:20px btn-group"
					id="btn_group_app_run_num" data-toggle="buttons">
					<label class="btn btn-primary"
						onClick="javascript:appRunMonthOnClick()"> <input
						type="radio" name="options" id="btn_month_app_run"> 月统计
					</label> <label class="btn btn-primary"
						onClick="javascript:appRunWeekOnClick()"> <input
						type="radio" name="options" id="btn_week_app_run"> 周统计
					</label>
				</div>
				<div class="col-xs-12" style="height: 450px;"
					id="appRunTimeMonthDiv"></div>
			</div>
		</div>
	</div>
	<!-- row -->
	<div class="space-6"></div>
</div>
<script>
	var appRunGroupInMonth = "getHospitaAppRunGroupByMonth";
	var appRunGroupInWeek = "getHospitaAppRunGroupByWeek";
	var fileGroupInMonth = "getHospitaMonthUpload";
	var fileGroupInWeek = "getHospitsWeekUpload";

	getHospitaAppRunNum();
	getAppRunInGroup(appRunGroupInMonth);
	getHospitaMonthUpload(fileGroupInMonth);
	/*
	Date.prototype.format = function() {
		var m = this.getMonth() + 1;
		return this.getFullYear() + '-' + m + '-' + this.getDate();
	}
	//周日
	Date.prototype.sunday = function() {
		var l = this.getTime();
		//周日是每一天
		var d = 7 - this.getDay();
		return new Date(this.getTime() + d * 24 * 60 * 60 * 1000).format();
	}
*/
	function appRunMonthOnClick() {
		getAppRunInGroup(appRunGroupInMonth);
	}
	function appRunWeekOnClick() {
		getAppRunInGroup(appRunGroupInWeek);

	}
	function fileNumWeekOnClick() {
		getHospitaMonthUpload(fileGroupInWeek);
	}
	function fileNumMonthOnClick() {
		getHospitaMonthUpload(fileGroupInMonth);
	}

	//大客户下医院各App运行次数
	function getHospitaAppRunNum() {
		$.get("company!getHospitaAppRunNum", {
			"company.company_id" : '${company.company_id }'
		}, function(data) {
			if (data.length <= 0) {
				$("#runTimeDiv").css({
					"display" : "none"
				});
				$("#btn_group_runtime").css({
					"display" : "none"
				});
				return;
			}
			var xAxis = new Array(data.length);
			var yAxis = new Array(data.length);
			for (var i = 0; i < data.length; i++) {
				xAxis[i] = data[i].softwareName;
				yAxis[i] = data[i].runNum;
			}
			option = {
				title : {
					text : '各App运行统计',
					subtext : ''
				},
				tooltip : {
					trigger : 'axis'
				},
				legend : {
					data : [ 'App运行次数' ]
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
				xAxis : [ {
					type : 'category',
					data : xAxis,
				} ],
				yAxis : [ {
					type : 'value'
				} ],
				series : [ {
					name : 'App运行次数',
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
			var myChart = echarts.init(document.getElementById('runTimeDiv'));
			myChart.setOption(option);
		});
	}
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
				else{
					var sun = new Date(data[i].weekDate);
					xAxis[i] = data[i].weekDate+'~'+sun.sunday();

				}
				yAxisNum[i] = data[i].fileNum;
				yAxisSize[i] =parseFloat( (data[i].size/(1024*1024*1024)).toFixed(2));

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
					data : [ '文件大小(GB)' ,'文件数量']
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
					}},
					 {
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
							}
						]
					}
			}
				]
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
				else{
					var sun = new Date( data[i].weekDate);
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
			var myChart = echarts.init(document
					.getElementById('appRunTimeMonthDiv'));
			myChart.setOption(option);
		});
	}
</script>