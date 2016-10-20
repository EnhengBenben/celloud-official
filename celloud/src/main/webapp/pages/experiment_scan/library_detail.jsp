<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<ng-include src="'pages/partial/_partial_experiment_sidebar.jsp'"></ng-include>
<div class="pro-body">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li>实验管理</li>
      <li>文库列表</li>
    </ol>
    <div class="content library">
        <table class="table table-main">
            <thead>
                <tr>
                    <th>文库编号</th>
                    <th>文库index</th>
                    <th>样品编号</th>
                    <th>样品类型</th>
                    <th>建库时间</th>
                    <th>样本index</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <tr ng-repeat="s in sampleList">
                    <td>{{s.storageName }}</td>
                    <td>{{s.storageSindex }}</td>
                    <td>{{s.sampleName }}</td>
                    <td>{{s.type }}</td>
                    <td>{{s.createDate | date:'yyyy-MM-dd HH:mm:ss' }}</td>
                    <td>{{s.sampleSindex }}</td>
                    <td>
                        <a class="btn-link" ng-click="remove(sample.sampleLogId)">上传</a>
                        |
                        <a class="btn-link" ng-click="download(s.id,s.storageName)">下载</a>
                    </td>
                </tr>
            </tbody>
        </table>
	</div>
</div>