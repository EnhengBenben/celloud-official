<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="upload-modal" class="modal upload-modal" ng-controller="fileUpload" novalidate>
  <div class="modal-dialog modal-md">
    <div class="modal-content">
      <div class="modal-header">
        <button id="close-upload-modal" type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
        <h4 class="modal-title">数据上传</h4>
      </div>
      <div class="modal-body row">
        <div class="step">
          <div class="item" ng-class="{active: step=='one'}">1</div><hr id="one-to-two">
          <div class="item" ng-class="{active: step=='two'}">2</div><hr id="two-to-three">
          <div class="item" ng-class="{active: step=='three'}">3</div>
        </div>
        <div class="upload-content" ng-class="{hide: step!='one'}">
          <div class="info">第一步：请输入数据标签，输入标签可以方便后续的数据检索及体验更加便捷的服务</div>
          <form class="info-form" name="first">
	          <div class="info-form-group">
	            <label>数据标签：</label>
	            <div>
	              <input type="text" placeholder="请输入数据标签" name="batch" ng-model="batch" required/>
	            </div>
	          </div>
	          <div class="info-form-group">
	            <label>产品标签:</label>
	            <div>
	              <select ng-model="tagId" name="productTag" required>
	              	<option ng-repeat="tag in tags" value="{{tag.tagId}}">{{tag.tagName}}</option>
                  </select>
	            </div>
	          </div>
	          <button type="button" class="btn" ng-click="nextStep()" ng-disabled="first.$invalid">下一步</button>
	      </form>
        </div>
        <div class="upload-content" ng-class="{hide: step!='two'}">
          <div class="info">第二步：拖拽文件到下面指定区域或者点击指定位置的选择文件，后即可自动上传文件</div>
          <div class="info text-left">数据标签：{{batch}}&nbsp;&nbsp;产品标签：{{tagId}}</div>
          <div id="plupload-content" class="plupload-content">
            <div class="upload-text">
                <i class="celicon -uploadgray"></i> 拖拽文件到此或者<a class="btn-link" id="choseFile">点击选择</a>文件上传
            </div>
          </div>
        </div>
        <div class="upload-content" ng-class="{hide: step!='three'}">
          <div class="info">第三步：上传中...</div>
          <div class="info text-left">数据标签：{{batch}}&nbsp;&nbsp;产品标签：{{tagId}}</div>
          <table class="table table-main" id="upload-list-table">
            <thead>
                <tr>
                    <th>文件名</th>
                    <th>上传进度</th>
                    <th>剩余时间</th>
                    <th>上传速度</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody id="upload-list-tbody">
            </tbody>
          </table>
          <button id="uploadMore" class="btn" href="javascript:void(0)">上传更多</button>
        </div>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->