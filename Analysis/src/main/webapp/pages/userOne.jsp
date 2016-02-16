<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
							<dt>用户名称：</dt>
							<dd>${user.username }</dd>
							<dt>Email：</dt>
							<dd>${user.email }</dd>
							<dt>用户电话：</dt>
							<dd>${user.cellphone }</dd>
							<dt>所属医院：</dt>
							<dd>${user.companyName }</dd>
							<dt>所属部门：</dt>
							<dd>${user.deptName }</dd>
							<dt>数据数量：</dt>
							<dd>${user.fileNum }&nbsp;个</dd>
							<dt>数据大小：</dt>
							<dd>
								<fmt:formatNumber pattern="0.00"
									value="${user.fileSize/ (1024 * 1024 * 1024) }" />
								&nbsp;(GB)
							</dd>
							<dt>报告数量：</dt>
							<dd>${user.reportNum }&nbsp;个</dd>
							<dt>注册时间：</dt>
							<dd>
								<fmt:formatDate type="both" value="${user.createDate }" />
							</dd>
						</dl>
					</div>
				</div>
			</div>

			<table id="hospitalList"
				class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th class="min-w-100">用户名称</th>
						<th class="min-w-100">登陆时间</th>
						<th class="min-w-120">IP地址</th>
						<th class="min-w-100">操作系统</th>
						<th class="min-w-100">浏览器</th>
						<th class="min-w-120">地址</th>
					</tr>
				</thead>

				<tbody>
						  <c:forEach items="${loginList }" var="data">
							<tr>
								<td>${data.userName }</td>
								<td>${data.logDate }</td>
								<td>${data.ip}</td>
								<td>${data.os }</td>
								<td>${data.browser }</td>
								<td>${data.address }</td>
							</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="col-xs-12" style="height: 450px;" id="runTimeDiv"></div>
			<!--本月上传文件上传个数 -->
				<div class="col-xs-12 margin:20px btn-group" id="btn_group_file_num"
					data-toggle="buttons">
					<label class="btn btn-primary" onClick="javascript:fileNumMonthOnClick()"> 
					<input type="radio" name="options" id="btn_month_upload_num"> 月统计
					</label>
					 <label class="btn btn-primary"  onClick="javascript:fileNumWeekOnClick()"> 
					 <input type="radio" name="options" id="btn_week_upload_num"> 周统计
					</label>
				</div>
				<div class="col-xs-12" style="height: 450px;" id="upLoadFileNumDiv"></div>
				
				<!-- 按月统计文件大小
				<div class="col-xs-12 margin:20px btn-group" id="btn_group_file_size"
					data-toggle="buttons">
					<label class="btn btn-primary" onClick="javascript:fileSizeMonthOnClick()"> 
					<input type="radio" name="options" id="btn_month_upload_size"> 月统计
					</label> 
					<label class="btn btn-primary"  onClick="javascript:fileSizeWeekOnClick()"> 
					<input type="radio" name="options" id="btn_week_upload_size"> 周统计
					</label>
				</div>
				<div class="col-xs-12" style="height: 450px;" id="upLoadFileSizeDiv"></div>
				 -->
				<!-- 统计用户每周、每月上传文件数量-->
				<div class="col-xs-12 margin:20px btn-group" id="btn_group_app_num" data-toggle="buttons">
					<label class="btn btn-primary"  onClick="javascript:appRunMonthOnClick()"> 
					<input type="radio" name="options" id="btn_month_app_run" > 月统计
					</label>
					 <label class="btn btn-primary" onClick="javascript:appRunWeekOnClick()"> 
					 <input type="radio" name="options" id="btn_week_app_run"  > 周统计
					</label>
				</div>
				<div class="col-xs-12" style="height: 450px;" id="appRunTimeMonthDiv"></div>
				
		</div>
	</div>
	<div class="space-6"></div>
</div>
<script>
	//UesrAction function Name    fileGroupInMonth
	var userRunApp = "userRunApp";
	var userRunAppInMonth="userRunAppInMonth";
	var userRunAppInWeek  = "userRunAppInWeek";
	var uploadFileMonth = "uploadFileMonth";
	var uploadFileWeek = "uploadFileWeek";
	
	loadAppRunTime();
	loadAppRunInGroup(userRunAppInMonth); 
	loadUploadFile(uploadFileMonth);
	//Date类扩展
	/*Date.prototype.format = function() {
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
	function fileNumMonthOnClick(){
			loadUploadFile(uploadFileMonth);
	}
	function fileNumWeekOnClick(){
		loadUploadFile(uploadFileWeek);
	}
	
	function appRunMonthOnClick(){
		loadAppRunInGroup(userRunAppInMonth);
	}
	function appRunWeekOnClick(){
		loadAppRunInGroup(userRunAppInWeek);
	}
	
	function loadAppRunTime() {
		$.get("user!" + userRunApp, {
			"user.userId" : '${user.userId }'
		}, function(data) {
			if (data.length <= 0) {
				$("#runTimeDiv").css({"display" : "none"});
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
					axisLabel : {
						rotate : 60
					}
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
	//按月、周统计用户的app运行次数
	function loadAppRunInGroup(groupByTag) {
		$.get("user!"+groupByTag, {
			"user.userId" : '${user.userId }'
		}, function(data) {
			if (data.length <= 0) {
				$("#appRunTimeMonthDiv").css('display', 'none');
				$("#btn_group_app_num").css('display', 'none');
				return;
			}
			var xAxis = new Array(data.length);
			var yAxis = new Array(data.length);
			for (var i = 0; i < data.length; i++) {
				if(groupByTag==userRunAppInMonth){
					xAxis[i] = data[i].skey;
				}else{
					var sun = new Date( data[i].skey);
					xAxis[i] =  data[i].skey+'~'+sun.sunday();
				}
					yAxis[i] = parseInt(data[i].svalue);
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
	}
	function loadUploadFile(groupByTag) {
		//大客户下医院上传文件个数统计
		$.get("user!"+groupByTag, {
			"user.userId" : '${user.userId }'
		}, function(data) {
			if (data.length <= 0) {
				$("#btn_group_file_num").css('display', 'none');
				$("#upLoadFileNumDiv").css('display', 'none');
				return;
			}
			var xAxis = new Array(data.length);
			var fileNum = new Array(data.length);
			var fileSize = new Array(data.length);

			for (var i = 0; i < data.length; i++) {
				if(groupByTag==uploadFileMonth){
					xAxis[i] = data[i].yearMonth;
				}else{
					var sun = new Date( data[i].weekDate);
					xAxis[i] =  data[i].skey+'~'+sun.sunday();
				}
				fileSize[i] =parseFloat((data[i].size/(1024*102481024)).toFixed(2));
				fileNum[i]=data[i].fileNum;
			}
			option = {
				title : {
					text : '文件上传数量统计',
					subtext : ''
				},
				tooltip : {
					trigger : 'axis'
				},
				legend : {
					data : [ '文件大小(GB)','文件数量' ]
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
					type : 'value'
				},
				 {
					type : 'value',
					name : '大小',
					axisLabel : {
						formatter : '{value} (GB)'
					}
				} ],
				series : [ {
					name : '文件大(小GB)',
					type : 'line',
					data : fileSize,
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
				} ,
				 {
					name : '文件数量',
					type : 'line',
					data : fileNum,
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
			var myChart = echarts.init(document
					.getElementById('upLoadFileNumDiv'));
			myChart.setOption(option);
		});
	}
	/*
	function loadUploadFileSize(groupByTag) {
		$.get("user!"+groupByTag, {
			"user.userId" : '${user.userId }'
		}, function(data) {
			if (data.length <= 0) {
				$("#upLoadFileSizeDiv").css('display', 'none');
				$("#btn_group_file_size").css('display', 'none');
				return;
			}
			var xAxis = new Array(data.length);
			var yAxis = new Array(data.length);
			for (var i = 0; i < data.length; i++) {
				if(groupByTag==uploadFileSizeMonth){
					xAxis[i] = data[i].skey;
				}else{
					var sun = new Date( data[i].skey);
					xAxis[i] =  data[i].skey+'~'+sun.sunday();
				}
					yAxis[i] = parseFloat((data[i].svalue / (1024 * 1024 * 1024)).toFixed(2));
			}
			option = {
				title : {
					text : '上传文件大小统计',
					subtext : ''
				},
				tooltip : {
					trigger : 'axis'
				},
				legend : {
					data : [ '文件上传大小' ]
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
					axisLabel : {
						formatter : '{value} (GB)'
					}
				} ],
				series : [ {
					name : '文件上传大小',
					type : 'line',
					data : yAxis,
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
					.getElementById('upLoadFileSizeDiv'));
			myChart.setOption(option);
		});
	}*/
</script>