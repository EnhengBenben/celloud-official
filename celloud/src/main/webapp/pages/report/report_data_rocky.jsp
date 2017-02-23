<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="pro-body">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li><a style="color: #a0a0a0" href="javascript:void(0);" ng-click="goBack()">报告管理</a></li>
      <li>华木兰报告</li>
    </ol>
	<div class="common-container">
	  <div class="content rocky ">
		<div class="report-content">
			<div class="row">
				<div class="col-xs-6">
					<h4>检测结果：</h4>
				</div>
				<div class="col-xs-6 text-right">
					<a class="btn -low" style="line-height: 28px;" target="_blank"
						ng-href="<%=request.getContextPath()%>/report/printRockyReport?projectId={{rocky.projectId}}&dataKey={{rocky.dataKey}}&appId={{rocky.appId}}">
						<i class="fa fa-print"></i> 打印报告
					</a>
				</div>
			</div>
			<section>
				<div class="result-div">
					<p class="main">
						{{rocky.pathogenic?'':'未'}}发现<strong>致病相关</strong>突变！
					</p>
					<p class="notice">注： 本报告中&nbsp;“致病相关”&nbsp;是指本系统采集到的已知研究结论中，受检人样本中存在的突变与疾病有相关性。</p>
				</div>
			</section>
			<h4>结果说明：</h4>
			<div class="report-result">
				<section>
					<p>
						<strong>变异列表</strong>
					</p>
					<table class="table table-main -report">
						<thead>
							<tr>
								<th>编号</th>
								<th>基因</th>
								<th>染色体位置</th>
								<th>核苷酸变异</th>
								<th>氨基酸变异</th>
								<th>临床意义</th>
							</tr>
						</thead>
						<tbody>
							<tr ng-repeat="record in rocky.records">
								<td>{{$index + 1}}</td>
								<td><i>{{record.gene}}</i></td>
								<td>{{record.chromosome}}</td>
								<td>{{record.acids}}</td>
								<td>{{record.nucleotides}}</td>
								<td>{{significances[record.significance]}}</td>
							</tr>
						</tbody>
					</table>
				</section>
				<section>
					<p>
						<strong>变异描述</strong>
					</p>
					<table class="table table-main -report">
						<tbody>
							<tr ng-repeat="record in rocky.records">
								<td style="width: 170px;"><i>{{record.gene}}</i>:&nbsp;{{record.acids}}<br>{{significances[record.significance]}}</td>
								<td>{{record.description}}</td>
							</tr>
						</tbody>
					</table>
				</section>
			</div>
			<section>
				<h4 style="text-align: center; margin-top: 20px;">
					<a target="_blank" ng-href="<%=request.getContextPath()%>/report/printRockyReport?projectId={{rocky.projectId}}&dataKey={{rocky.dataKey}}&appId={{rocky.appId}}">
						<i class="fa fa-search"></i> 点击查看详细报告
					</a>
				</h4>
			</section>
		</div>
	  </div>
	</div>
</div>