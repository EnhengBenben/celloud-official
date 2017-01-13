<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%-- <link href="<%=request.getContextPath()%>/css/rocky_main.min.css" rel="stylesheet"> --%>
<ng-include src="'pages/partial/_partial_rocky_sidebar.jsp'"></ng-include>
<div class="pro-body rocky">
	<ol class="breadcrumb">
		<li>CelLoud</li>
		<li>我的产品</li>
		<li>华木兰</li>
		<li>上传</li>
	</ol>
	<div class="common-container">
		<div class="step-navigation">
			<div class="step" id="upload-nav-step-one">
				<div class="item">1</div>
			</div>
			<div class="step-line not-reached" id="upload-nav-line-one">
				<hr>
			</div>
			<div class="step not-reached" id="upload-nav-step-two">
				<div class="item">2</div>
			</div>
			<div class="step-line not-reached" id="upload-nav-line-two">
				<hr>
			</div>
			<div class="step not-reached" id="upload-nav-step-three">
				<div class="item">3</div>
			</div>
		</div>
		<div class="content">
			<div class="upload-content">
				<div id="upload-step-one" class="steps">
					<div class="step">
						<div class="info">第一步：请输入文件标签，输入标签可以方便后续的数据检索及体验更加便捷的服务</div>
					</div>
					<div class="form-inline">
					   <form name="batchForm">
							<div class="form-group">
								<label for="batch-info">数据标签</label> <input type="text"
									class="form-control" id="batch-info-input" ng-model="rockyBatch" name="batch" placeholder="标签" required>
								<input type="hidden" id="tag-info-input">
							</div>
							<button id="upload-next" class="btn -confirm" ng-click="stepTwo()" ng-disabled="batchForm.$invalid">下一步</button>
					   </form>
					</div>
				</div>
				<div id="upload-step-two" class="steps hide">
					<div class="step">
						<div class="info">第二步：拖拽文件到下面指定区域或者点击指定位置的选择文件进行选择，后即可自动上传文件</div>
					</div>
					<div id="plupload-content" class="box-body plupload-content">
						<div class="upload-text">
							<i class="celicon -uploadgray"></i> 拖拽文件到此或者点击选择文件上传
						</div>
					</div>
					<table class="table table-main hide" id="rocky-upload-list-table">
						<thead>
							<tr>
								<th>文件名</th>
								<th>上传进度</th>
								<th>剩余时间</th>
								<th>上传速度</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody id="rocky-upload-list-tbody">
						</tbody>
					</table>
				</div>
				<div id="upload-step-three" class="steps hide">
					<div class="step">
						<div class="info">第三步，上传完成</div>
					</div>
					<div class="box-body plupload-content">
						<div class="upload-text">
							<i class="celicon -uploadgray"></i>文件上传成功，继续上传，请点击 <a
								ng-click="continueUpload();">继续添加</a> 或 <a
								ng-click="newUpload();">新上传</a>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="uploadtips">
			<div class="tips" id="upload-tip-one" style="margin-top: 20px;">
				<p>温馨提示：</p>
				<p>1、输入数据标签可以更方便的检索数据哦</p>
			</div>
			<div class="tips hide" id="upload-tip-two" style="margin-top: 20px;">
				<p>温馨提示：</p>
				<p>1、拖拽或者选择文件都可以实现文件上传</p>
				<p>
					2、选择的文件格式应该是类似于test.<font color="#de4c95">R1</font>.fq.gz、test.<font
						color="#de4c95">R2</font>.fq.gz
				</p>
				<p>
					3、程序自动识别含<font color="#de4c95">R1</font>、<font color="#de4c95">R2</font>的成对文件并自动运行
				</p>
			</div>
			<div class="tips hide" id="upload-tip-three" style="margin-top: 20px;">
				<p>温馨提示：</p>
				<p>1、上传完成，如需继续添加，请点击继续添加按钮，继续添加的文件和已完成上传的文件数据标签一样</p>
				<p>2、上传完成，如需新上传文件，请点击新上传按钮，此处需要重新输入新的数据标签</p>
			</div>
		</div>
	</div>
</div>