<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pro-body mreport">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li><a style="color: #a0a0a0" href="javascript:void(0);" ng-click="goBack()">报告管理</a></li>
      <li>EGFR报告</li>
    </ol>
    <div class="content">
      <div class="content-left col-xs-10">
        <div class="content-header">
	        <p> 项目名称：
	            {{project.projectName}}
	        </p>
	        <p> 应用名称：
	            {{egfr.appName}}
	        </p>
	        <p> 文件名称：
	            {{egfr.fileName}}({{egfr.dataKey}})
	        </p>
	        <div class="btn-groups">
	            <a class="btn -low" target="_blank" ng-href="${pageContext.request.contextPath }/report/printEGFR?projectId={{egfr.projectId }}&dataKey={{egfr.dataKey }}&appId={{egfr.appId }}">打印报告</a>
	            <a class="btn -middle" ng-if="egfr.pdf != null && egfr.pdf != ''" ng-href="${pageContext.request.contextPath }/report/down?path={{egfr.userId}}/{{egfr.appId}}/{{egfr.dataKey}}/{{egfr.pdf}}">PDF下载</a>
	        </div>
	    </div>
	    <div>
        <section class="m-box">
            <h2>
                <i class="i-edit"></i>一、 已知突变位点（依据已发表文献，该突变位点有明确临床意义）
                <span style="float:right;padding-right: 30px;" title="帮助" ng-click="showHelp()"><div class="clear button button-glow button-circle button-rounded button-primary button-tiny text-center"><span style="line-height:38px;" class="fa fa-thumbs-up"></span></div></span>
            </h2>
            <div class="m-boxCon result">
                <div id="knowResult">
                    <span ng-if="egfr.position != null && egfr.position != ''" ng-bind-html="egfr.position"></span>
                    <span ng-if="egfr.position == null || egfr.position == ''">未检测到突变</span>
                </div>
                <br/>
                <img ng-if="egfr.knowMutationBig != null" ng-repeat="kmb in egfr.knowMutationBig" name="know" ng-src="{{uploadPath}}{{egfr.userId}}/{{egfr.appId}}/{{egfr.dataKey}}/SVG/{{kmb}}" style="width: 100%;">
            </div>
        </section>
        <section class="m-box">
            <h2>
                <i class="i-edit"></i>二、 未知突变位点（该突变位点目前没有已发表文献支持，无明确临床意义）
                <div style="float:right;padding-right: 30px;" title="帮助" ng-click="showHelp()"><div class="clear button button-glow button-circle button-rounded button-primary button-tiny text-center"><span style="line-height:38px;" class="fa fa-thumbs-up"></span></div></div>
            </h2>
            <div class="m-boxCon result">
                <span ng-if="egfr.mutationPosition!=null">
                    <p ng-bind-html="egfr.mutationPosition"></p>
                    <br/>
                    <a ng-repeat="out in egfr.out" ng-click="bigReplace(uploadPath+egfr.userId+'/'+egfr.appId+'/'+egfr.dataKey+'/SVG/'+out.replace('png','10.png'));" style="padding-right: 20px">
                        <img ng-src="{{uploadPath}}{{egfr.userId}}/{{egfr.appId}}/{{egfr.dataKey}}/SVG/{{out}}" style="width: 156px;height: 177px;">
                    </a>
                </span>
                <span ng-if="egfr.mutationPosition==null">
                    数据正常，未找到其他突变。
                </span>
            </div>
         </section>
         <section class="m-box">
             <h2>
                <i class="i-edit"></i>三、 参考结论
            </h2>
            <div ng-bind-html="egfr.conclusion" class="m-boxCon result" id="_result">
            </div>
         </section>
         <section class="m-box">
             <h2><i class="i-edit"></i>四、 测序序列结果</h2>
            <div class="m-boxCon result" id="_seq" style="word-break: break-all;">
                {{egfr.seq}}
            </div>
         </section>
         <section class="m-box">
             <h2><i class="i-dna"></i>五、 测序峰图结果</h2>
                <div class="m-boxCon result" ng-if="egfr.original != null">
                    <a ng-repeat="original in egfr.original" ng-click="bigOrigin(uploadPath+egfr.userId+'/'+egfr.appId+'/'+egfr.dataKey+'/SVG/'+original,'original'+($index+1));" >
                        <br/>
                        <img name="imgSrc" class="originImg" ng-src="{{uploadPath}}{{egfr.userId}}/{{egfr.appId}}/{{egfr.dataKey}}/SVG/{{original}}" id="original{{$index+1}}"><br/>
                    </a>
                </div>
                <div class="m-boxCon result" ng-if="egfr.original == null">
                样本异常，无法检测
                </div>
         </section>
         <section class="m-box">
             <h2><i class="i-celloud"></i>Celloud数据参数同比分析</h2>
            <div class="m-boxCon">
                <div class="row" id="charDiv">
                </div>
            </div>
         </section>
       </div>
      </div>
      <div class="content-right col-xs-2">
        <ng-include src="'pages/partial/_partial_datareportoperate.jsp'"></ng-include>
      </div>
     </div>
 </div>
<div id="helpModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog">
    	<div class="modal-content">
	        <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
		        <h4 class="modal-title">注释</h4>
	        </div>
	        <div class="modal-body form-modal">
		        <div class="_help">1. 如果为Homozygous纯合突变，发生突变的位置在看峰图序列时以最高峰代表的碱基为突变碱基；如果为Heterozygous杂合突变，发生突变的位置在看峰图序列时以第二高峰代表的碱基为突变碱基；</div>
				<div class="_help">2. V769-D770insASV 为突变型名称。</div>
				<div class="_help">3. 比例值说明：A-A|G(67|33,2.1)，2.1为67和33的比值，该比例并不代表样本中该位点A和G的真实数量比例，只是代表该位点是A的可能性为67%，是G的可能性为33%。如果没有“(67|33,2.1）”出现，认为该位点100%发生突变。如果是A-A|G，说明该位点为A的可能性大；如果为A-G|A，说明该位点为G的可能性大。当野生型的碱基（即A）的可能性大于突变碱基（即G）的可能性时，如果比值小于5我们认为该位点是突变；如果突变碱基（即G）的可能性大于野生型的碱基（即A）的可能性时，不论比值多少都认为发生了突变。</div>
				<div class="_help">4. Homozygous 为纯合突变，Heterozygous 为杂合突变。</div>
				<div class="_help">5. 图中的“*”表示在该位置发生突变。</div>
				<div class="_help">6. “Recombination: ttaagagaagcaa- &gt;c”:表示ttaagagaagcaa变为c；<br/>
				“Deletion: gaattaagagaagcaaca ：”表示gaattaagagaagcaaca缺失；<br/>
				“Insertion: gccagcgtg” ：表示gccagcgtg插入。</div>
				<div class="_help">7. “*Wild type: ACAACCCCCACGTGTGCCGCCTGCTGGGC ”：表示野生型序列为ACAACCCCCACGTGTGCCGCCTGCTGGGC</div>
				<div class="_help">8. “M204M|V {A-A|G(67|33,2.1);G-G|T(72|28,2.5)}”中：<br/>
				（1）“M”表示野生型编码氨基酸为M；“204”表示氨基酸位点为204；“M|V”表示由原来的野生型M变为V。<br/>
				（2）“A-A|G(67|33,2.1) ;G-G|T(72|28,2.5)”表示碱基的变化，一个碱基位点由原来的A变为A|G杂合，比例为67比33，比值为2.1；另一个碱基位点由原来的G变为G|T杂合，比例为72比28，比值为2.5。</div>
			</div>
			<div class="modal-footer">
		    	<button type="button" class="btn btn-celloud-close btn-flat pull-right" data-dismiss="modal">关闭</button>
			</div>
    	</div>
    </div><!-- /.modal-content -->
</div><!-- /.modal-dialog -->