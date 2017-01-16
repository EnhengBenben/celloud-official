<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="<%=request.getContextPath()%>/plugins/sockjs-modified-1.0.0.js"></script>
<script src="<%=request.getContextPath()%>/plugins/waveLoading.min.js"></script>
<div class="topbar-menu bsi" ng-controller="bsiCommon">
    <header class="common-menu">
        <div class="common-menu-logo">
            <img alt="百菌探" src="<%=request.getContextPath()%>/images/app/bsi.png">
        </div>
        <hr class="-left">
        <div id="common-menu-center" class="info">
            <div id="common-menu" class="common-menu-btn pull-left">
               <a class="item-btn upload-btn" id="to-upload-a" href="javascript:void(0)" ng-click="bsiStepOne()">
                   <i class="celicon my-upload-icon"></i><br>上传
                   <canvas id="upload-progress" class="upload-progress" width="64" height="64"></canvas>
               </a>
               <a class="item-btn" ng-class="{active: isActive('product/bactive/d')}" id="to-data-a" di-href="${pageContext.request.contextPath }/index#/product/bactive/d/{{appId}}">
                   <i class="celicon my-data-icon"></i><br>数据
               </a>
               <a class="item-btn" ng-class="{active: isActive('product/bactive/r')}" id="to-report-a" di-href="${pageContext.request.contextPath }/index#/product/bactive/report/{{appId}}">
                   <i class="celicon my-report-icon"></i><br>报告
               </a>
            </div>
        </div>
        <div id="common-menu-right" class="searchs common-menu-btn">
            <div class="data-search">
                <input id="condition-input" class="input-sm" type="text" placeholder="搜索" ng-keypress="conditionSearch($event)" />
                <a id="condition-find" class="input-group-btn" ng-click="conditionFind()">
                    <i class="fa fa-search"></i>
                </a>
            </div>
        </div>
        <hr class="-right">
    </header>
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