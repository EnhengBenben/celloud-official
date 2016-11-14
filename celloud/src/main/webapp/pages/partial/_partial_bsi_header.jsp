<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="topbar-menu" ng-controller="bsiCommon">
    <header class="common-menu">
        <div class="common-menu-logo">
            <img alt="百菌探" src="<%=request.getContextPath()%>/images/app/bsi.png">
        </div>
        <hr class="-left">
        <div id="common-menu-center" class="info">
            <div id="common-menu" class="common-menu-btn pull-left">
               <a class="item-btn upload-btn" id="to-upload-a" href="javascript:void(0)" ng-click="toUpload()">
                   <i class="celicon my-upload-icon"></i><br>上传
                   <canvas id="upload-progress" class="upload-progress" width="64" height="64"></canvas>
               </a>
               <a class="item-btn" ng-class="{active: isActive('bsidata')}" id="to-data-a" href="${pageContext.request.contextPath }/index#/product/bsi/bsidata">
                   <i class="celicon my-data-icon"></i><br>数据
               </a>
               <a class="item-btn" ng-class="{active: isActive('bsireport')}" id="to-report-a" href="${pageContext.request.contextPath }/index#/product/bsi/bsireport">
                   <i class="celicon my-report-icon"></i><br>报告
               </a>
            </div>
        </div>
        <div id="common-menu-right" class="searchs common-menu-btn">
            <div class="data-search">
                <input id="condition-input" class="input-sm" type="text" placeholder="搜索" />
                <a id="condition-find" class="input-group-btn">
                    <i class="fa fa-search"></i>
                </a>
            </div>
        </div>
        <hr class="-right">
    </header>
    <div id="upload-modal" class="modal upload-modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button id="close-upload-modal" type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">
                            <i class="fa fa-times-circle"></i>
                        </span>
                    </button>
                    <h4 class="modal-title">数据上传</h4>
                </div>
                <div class="modal-body row">
                    <div class="step-item">
                        <div class="step-one active">1</div>
                        <hr id="one-to-two">
                        <div class="step-two">2</div>
                        <hr id="two-to-three">
                        <div class="step-three">3</div>
                    </div>
                    <div class="step-one-content">
                        <input id="batch-info" type="text" placeholder="请输入标签" value="" ng-keypress="batchInfo($event)" />
                        <br>
                        <p>
                            <span>
                                为每次上传数据按输入加入标签，提升后续报告查询、数据管理、分类汇总提供快捷服务。
                                <span>
                        </p>
                        <p>
                            <span>
                                示例：外科5月；ICU-9床；急诊发热
                                <span>
                        </p>
                        <a id="next-step" class="btn" href="javascript:void(0)" ng-click="nextStep()">下一步</a>
                    </div>
                    <div class="step-two-content hide">
                        <div class="upload-container">
                            <div id="plupload-content" class="box-body plupload-content">
                                <ul id="upload-filelist" class="plupload-filelist"></ul>
                                <div class="upload-text">
                                    <i class="celicon grey-upload-icon"></i>拖拽文件到此或者点击选择文件上传
                                </div>
                            </div>
                        </div>
                        <p>
                            <span>
                                <input id="need-split" type="checkbox" value="1" checked="checked">
                                需要数据拆分 (run: Split)
                            </span>
                        </p>
                        <p>
                            <span>&gt;目前支持 .tar.gz; .zip; 压缩过的fasta 格式的序列文件及index 文件</span>
                        </p>
                        <p>
                            <span>&gt;文件命名规则请点击查看 "百菌探_下机文件命名规则"</span>
                        </p>
                        <p>
                            <span>&gt;数据拆分(Split) 选中时系统先按index文件对数据拆分并重命名.</span>
                        </p>
                        <input id="tag-info" type="hidden" value="1" />
                        <a id="begin-upload" class="btn" href="javascript:void(0)">开始上传</a>
                    </div>
                    <div class="step-three-content hide">
                        <div class="upload-status">
                            数据上传中...数据标签 ：
                            <span id="tags-review"></span>
                        </div>
                        <div class="upload-container">
                            <div id="plupload-content" class="box-body plupload-content">
                                <ul id="uploading-filelist" class="plupload-filelist">
                                    <li class="plupload-filelist-header">
                                        <div class="plupload-file-name">
                                            <span>文件名</span>
                                        </div>
                                        <div class="plupload-file-size">大小</div>
                                        <div class="plupload-file-surplus">剩余时间</div>
                                        <div class="plupload-file-status">进度</div>
                                        <div class="plupload-file-action">操作</div>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <p>
                            <span>文件上传中结束会自动开始分析任务，可在报告页面查看最新报告.</span>
                        </p>
                        <a id="upload-more" class="btn" href="javascript:void(0)">上传更多</a>
                    </div>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
    <!-- /.modal -->
    <div id="report-uploading-modal" class="modal running-modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">
                            <i class="fa fa-times-circle"></i>
                        </span>
                    </button>
                    <h4 class="modal-title">数据上传中</h4>
                </div>
                <div class="modal-body row">
                    <h4>数据不完整，来检查您缺哪个数据吧！</h4>
                    <div>
                        数据运行所需文件如下：<br>
                        <span class="highlight-text">public_name_</span>
                        R1.fastq<br>
                        <span class="highlight-text">public_name_</span>
                        R2.fastq<br>
                        <span class="highlight-text">public_name_</span>
                        .txt
                    </div>
                    <a class="btn" href="javascript:void(0)" data-dismiss="modal" aria-label="Close">知道了</a>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
    <!-- /.modal -->
    <div id="running-modal" class="modal running-modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">
                            <i class="fa fa-times-circle"></i>
                        </span>
                    </button>
                    <h4 class="modal-title">数据分析中</h4>
                </div>
                <div class="modal-body row">
                    <img alt="" src="<%=request.getContextPath()%>/images/icon/in-analysis.gif">
                    <h4>报告生成中</h4>
                    <div>
                        请
                        <span class="highlight-text">稍后</span>
                        刷新查看
                    </div>
                    <a class="btn" href="javascript:void(0)" data-dismiss="modal" aria-label="Close">OK,再等等</a>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
    <!-- /.modal -->
    <div id="running-error-modal" class="modal running-error-modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">
                            <i class="fa fa-times-circle"></i>
                        </span>
                    </button>
                    <h4 class="modal-title">异常终止</h4>
                </div>
                <div class="modal-body row">
                    <i class="celicon run-error-icon"></i>
                    <h4>
                        我们抱歉的通知，数据：
                        <span id="run-error-data" class="highlight-text"></span>
                    </h4>
                    <h4>上传（分析）过程中出错</h4>
                    <div>请重传（联系service@celloud.cn获取帮助）</div>
                    <a class="btn" href="javascript:void(0)" data-dismiss="modal" aria-label="Close">知道了</a>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
</div>