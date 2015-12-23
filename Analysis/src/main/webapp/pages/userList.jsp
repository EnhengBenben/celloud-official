<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="breadcrumbs" id="breadcrumbs">
	<script type="text/javascript">
		try {
			ace.settings.check('breadcrumbs', 'fixed');
		} catch (e) {
		}
	</script>
	<ul class="breadcrumb">
		<li><i class="icon-user-md"></i> <a href="#">用户统计</a></li>
		<li class="active">用户详细信息</li>
	</ul>
	<!-- .breadcrumb -->
</div>
<div class="page-content">
	<div class="row">
		<h3 class="header smaller lighter blue">
			<span onclick="toUserList()">用户详细信息</span> <small id="secondTitle"
				class="hide"> <i class="icon-double-angle-right"></i> <span
				id="_oneUser"></span>
			</small>
		</h3>
		<div class="col-xs-12" style="height: 450px;" id="echartView"></div>
		<div class="table-header hide" id="_userName" ></div>
		<div class="col-xs-11 table-responsive" id="userListDiv" style=" margin-left:60px;margin-top:15px">
			<table id="userList"
				class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th>用户名</th>
						<th>Email</th>
						<th class="min-w-120">电话</th>
						<th class="min-w-120">所属医院</th>
						<th class="min-w-80">所属部门</th>
						<th class="min-w-80">数据个数</th>
						<th class="min-w-110">数据大小(GB)</th>
						<th class="min-w-80">报告个数</th>
						<th class="w160">注册时间</th>
					</tr>
				</thead>

				<tbody>
					<s:if test="%{userList.size()>0}">
						<s:iterator id="data" value="userList">
							<tr>
								<td><a
									href="javascript:getUserDetail(${data.userId },'${data.username }')">${data.username }</a></td>
								<td>${data.email }</td>
								<td>${data.cellphone }</td>
								<td>${data.companyName }</td>
								<td>${data.deptName }</td>
								<td>${data.fileNum }</td>
								<td><fmt:formatNumber pattern="0.00"
										value="${data.fileSize/ (1024 * 1024 * 1024) }" /></td>
								<td>${data.reportNum }</td>
								<td><fmt:formatDate type="both" value="${data.createDate }" /></td>
							</tr>
						</s:iterator>
					</s:if>
				</tbody>
			</table>
		</div>
		<!-- PAGE CONTENT ENDS -->
	</div>
	<!-- /.row -->
</div>
<!-- /.page-content -->
<script type="text/javascript">
	$.get("user!getUserListByBigUserJson", {}, function(res) {
		var xAxis = new Array(res.length);
		var dataNum = new Array(res.length);
		var dataSize = new Array(res.length);
		var reportNum = new Array(res.length);

		for (var i = 0; i < res.length; i++) {
			xAxis[i] = res[i].username;
			dataNum[i] = res[i].fileNum;
			dataSize[i] = parseFloat( (res[i].fileSize/ (1024 * 1024 * 1024) ).toFixed(2));
			reportNum[i] = res[i].reportNum;
		}
		option = {
			tooltip : {
				trigger : 'axis',
				axisPointer : { // 坐标轴指示器，坐标轴触发有效
					type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
				}
			},
			legend : {
				data : [  '数据个数', '数据大小（GB）', '报告个数' ]
			},
			toolbox : {
				show : true,
				feature : {
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
			xAxis : [ {
				type : 'category',
				data : xAxis,
				axisLabel : {
					rotate : 60
				}
			} ],
			yAxis : [ {
				type : 'value',
				name : '数量'
			}, {
				type : 'value',
				name : '数据大小(GB)',
				axisLabel : {
					formatter : '{value} (GB)'
				}
			} ],
			series : [ 
			 {
				name : '数据个数',
				type : 'bar',
				data : dataNum,
				markLine : {
					data : [ {
						type : 'average',
						name : '平均值'
					} ]
				},
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
			}, {
				name : '数据大小（GB）',
				type : 'bar',
				yAxisIndex : 1,
				data : dataSize,
				itemStyle : {
					normal : {
						label : {
							show : true
						}
					}
				},
				markLine : {
					data : [ {
						type : 'average',
						name : '平均值'
					} ]
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
			}, {
				name : '报告个数',
				type : 'bar',
				data : reportNum,
				itemStyle : {
					normal : {
						label : {
							show : true
						}
					}
				},
				markLine : {
					data : [ {
						type : 'average',
						name : '平均值'
					} ]
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
			} ]
		};
		var myChart = echarts.init(document.getElementById('echartView'));
		myChart.setOption(option);
	});
	jQuery(function($) {
		var oTable1 = $('#userList').dataTable(
				{
					"aoColumns" : [ null, null, null, null, null, null, null,
							null, null ],
					iDisplayLength : 100
				});
	})
	function getUserDetail(id, name) {
		$("#_oneUser").html(name);
		$("#secondTitle").removeClass("hide");
		$.get("user!getUserById", {
			"user.userId" : id
		}, function(responseText) {
			$("#userListDiv").html(responseText);
		});
	}

	
</script>