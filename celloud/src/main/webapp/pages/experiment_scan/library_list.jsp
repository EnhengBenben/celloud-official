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
                    <th>样本数量</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <tr ng-repeat="storage in storages.datas">
                    <td><a class="btn-link" ng-href="#/experiment/libraryList/{{storage.id}}">{{storage.storageName }}</a></td>
                    <td>{{storage.sindex }}</td>
                    <td>{{storage.sampleNum }}</td>
                    <td>
                        <a class="btn-link">上传</a>
                        |
                        <a class="btn-link" ng-click="download(storage.id,storage.storageName)">下载</a>
                    </td>
                </tr>
            </tbody>
        </table>
	</div>
</div>