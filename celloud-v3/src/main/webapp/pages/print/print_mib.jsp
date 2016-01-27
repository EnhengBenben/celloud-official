<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>打印MIB报告</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/font.css">
<link href="<%=request.getContextPath() %>/css/print_gdd.css?version=1.0" rel="stylesheet">
</head>
<body>
<a href="javascript:void(0)" onclick="preview(this)" class="btn btn-default" id="change" style="float:right;margin-top:10px;margin-right:-80px;"><i class="i-print"></i>打印</a>
<section class="section2 border1 w3cbbs">
    <div class="header">
        <h1 style="font-size:32px;padding:0 0 5 0px;">细菌感染检测分析报告</h1>
    </div>
    <h4>1.&nbsp;&nbsp; 基本信息</h4>
    <div class="section1">
    <ul>
        <li>编号：<span><input type="text" name="" value=""></span></li>
        <li>样本类型：<span><input type="text" name="" value=""></span></li>
        <li>姓名：<span><input type="text" name="" value=""></span></li>
        <li>申请日期：<span><input type="text" name=""></span></li>
        <li>性别： <span id="_sex"><input type="radio" name="sex" value="男">男<input type="radio" name="sex" value="女" >女</span></li>
        <li>接收日期：<span><input type="text" name=""></span></li>
        <li>年龄：<span><input type="text" id="patientAge" name="" value=""></span>岁</li>
        <li>样本状态：<span><input type="text" name=""></span></li>
    </ul>
    </div>
    <h4>2.&nbsp;&nbsp;数据统计</h4>
    <div class="info">
        <table class="table table-bordered table-condensed">
            <thead>
                <tr><th>序列总数</th><th>平均质量</th><th>平均GC含量</th></tr>
            </thead>
            <tbody>
                <tr>
                    <td>${mib.totalReads }</td>
                    <td>${mib.avgQuality }</td>
                    <td>${mib.avgGCContent }</td>
                </tr>
            </tbody>
        </table>
    </div>
    <h4>3.&nbsp;&nbsp; 检测结果（${mib.dataKey}）</h4>
    <div class="info">
        ${mib.conclusion }
    </div>
    <h4>4.&nbsp;&nbsp;详细结果</h4>
    <div class="info w3cbbs">
        <table class="table table-bordered table-condensed">
            <thead>
               <tr>
                 <th style="line-height: 14px;vertical-align: middle;text-align: center;">种<br><span style="font-size:12px;color: #9A9999;">Species</span></th>
                 <th style="line-height: 14px;vertical-align: middle;text-align: center;min-width:80px;">属<br><span style="font-size:12px;color: #9A9999;">Genus</span></th>
                 <th style="line-height: 14px;vertical-align: middle;text-align: center;min-width:54px;">GI号<br><span style="font-size:12px;color: #9A9999;">GI</span></th>
                 <th style="line-height: 14px;vertical-align: middle;text-align: center;min-width:45px;">覆盖长度%<br><span style="font-size:12px;color: #9A9999;">%Coverage</span></th>
                 <th style="line-height: 14px;vertical-align: middle;text-align: center;min-width:30px;">种比对上的序列数<br><span style="font-size:12px;color: #9A9999;">Reads_hit</span></th>
                 <th style="line-height: 14px;vertical-align: middle;text-align: center;">属比对上的序列数<br><span style="font-size:12px;color: #9A9999;">Reads_num</span></th>
                 <th style="line-height: 14px;vertical-align: middle;text-align: center;">种序列百分比<br><span style="font-size:12px;color: #9A9999;">%Reads_Ratio</span></th>
                 <th style="line-height: 14px;vertical-align: middle;text-align: center;">平均覆盖深度<br><span style="font-size:12px;color: #9A9999;">Average depth of coverage</span></th>
               </tr>
             </thead>
             <tbody>
               <c:choose>
                 <c:when test="${fn:length(mib.summaryTable)>0}">
                    <c:forEach items="${mib.summaryTable }" var="summary" varStatus="s">
                       <tr>
                         <td>${summary.Species }</td>
                         <td>${summary.Genus }</td>
                         <td>${summary.GI }</td>
                         <td>${summary.Coverage }</td>
                         <td>${summary.Reads_hit }</td>
                         <td>${summary.Reads_num }</td>
                         <td>${summary.Reads_Ratio }</td>
                         <td>${summary.avgCoverage }</td>
                       </tr>
                    </c:forEach>
                 </c:when>
                 <c:otherwise>
                    <tr><td colspan="7">无结果</td></tr>
                 </c:otherwise>
               </c:choose>
             </tbody>
        </table>
    </div>
    <h4>5.&nbsp;&nbsp;测序结果分布图</h4>
    <div class="info">
        <table style="width:90%;">
          <tr>
            <td style="width:49%;">
              <c:choose>
                <c:when test="${empty mib.readsDistributionInfo}">
                  <img src="<c:if test="${!mib.readsDistribution.contains('Tools') }">${uploadPath }/${mib.userId }/${mib.appId }/${mib.dataKey }</c:if>${mib.readsDistribution }" style="width:100%;">
                </c:when>
                <c:otherwise>
                  <div id="reads-distribution-char" style="width:100%;height:330px;">${mibCharList.readsDistributionInfo }</div>
                </c:otherwise>
              </c:choose>
            </td>
            <td>
              <c:choose>
                <c:when test="${empty mib.familyDistributionInfo}">
                  <img src="<c:if test="${!mib.familyDistribution.contains('Tools') }">${uploadPath }/${mib.userId }/${mib.appId }/${mib.dataKey }</c:if>${mib.familyDistribution }" style="width:100%;">
                </c:when>
                <c:otherwise>
                  <div id="family-distribution-char" style="width:100%;height:330px;">${mibCharList.familyDistributionInfo }</div>
                </c:otherwise>
              </c:choose>
            </td>
          </tr>
        </table>
    </div>
    <h4>6.&nbsp;&nbsp;属分布图</h4>
    <div class="info">
        <c:choose>
          <c:when test="${empty mib.genusDistributionInfo}">
            <img src="<c:if test="${!mib.genusDistribution.contains('Tools') }">${uploadPath }/${mib.userId }/${mib.appId }/${mib.dataKey }</c:if>${mib.genusDistribution }" style="width:90%;">
          </c:when>
          <c:otherwise>
            <div id="genus-distribution-char" style="width:100%;height:330px;">${mibCharList.genusDistributionInfo }</div>
          </c:otherwise>
        </c:choose>
    </div>
    <h4>7.&nbsp;&nbsp;各菌16s rRNA序列覆盖分布图</h4>
    <div class="info">
      <c:if test="${mib.pngPath.top1png!=null }">
        <img src="<c:if test="${!mib.pngPath.top1png.contains('Tools') }">${uploadPath }/${mib.userId }/${mib.appId }/${mib.dataKey }</c:if>${mib.pngPath.top1png }" style="width:90%;">
      </c:if>
      <c:if test="${mib.pngPath.top2png!=null }">
        <img src="<c:if test="${!mib.pngPath.top2png.contains('Tools') }">${uploadPath }/${mib.userId }/${mib.appId }/${mib.dataKey }</c:if>${mib.pngPath.top2png }" style="width:90%;">
      </c:if>
      <c:if test="${mib.pngPath.top3png!=null }">
        <img src="<c:if test="${!mib.pngPath.top3png.contains('Tools') }">${uploadPath }/${mib.userId }/${mib.appId }/${mib.dataKey }</c:if>${mib.pngPath.top3png }" style="width:90%;">
      </c:if>
      <c:if test="${mib.pngPath.top4png!=null }">
        <img src="<c:if test="${!mib.pngPath.top4png.contains('Tools') }">${uploadPath }/${mib.userId }/${mib.appId }/${mib.dataKey }</c:if>${mib.pngPath.top4png }" style="width:90%;">
      </c:if>
      <c:if test="${mib.pngPath.top5png!=null }">
        <img src="<c:if test="${!mib.pngPath.top5png.contains('Tools') }">${uploadPath }/${mib.userId }/${mib.appId }/${mib.dataKey }</c:if>${mib.pngPath.top5png }" style="width:90%;">
      </c:if>
      <c:if test="${mib.pngPath.top6png!=null }">
        <img src="<c:if test="${!mib.pngPath.top6png.contains('Tools') }">${uploadPath }/${mib.userId }/${mib.appId }/${mib.dataKey }</c:if>${mib.pngPath.top6png }" style="width:90%;">
      </c:if>
      <c:if test="${mib.pngPath.top7png!=null }">
        <img src="<c:if test="${!mib.pngPath.top7png.contains('Tools') }">${uploadPath }/${mib.userId }/${mib.appId }/${mib.dataKey }</c:if>${mib.pngPath.top7png }" style="width:90%;">
      </c:if>
      <c:if test="${mib.pngPath.top8png!=null }">
        <img src="<c:if test="${!mib.pngPath.top8png.contains('Tools') }">${uploadPath }/${mib.userId }/${mib.appId }/${mib.dataKey }</c:if>${mib.pngPath.top8png }" style="width:90%;">
      </c:if>
      <c:if test="${mib.pngPath.top9png!=null }">
        <img src="<c:if test="${!mib.pngPath.top9png.contains('Tools') }">${uploadPath }/${mib.userId }/${mib.appId }/${mib.dataKey }</c:if>${mib.pngPath.top9png }" style="width:90%;">
      </c:if>
      <c:if test="${mib.pngPath.top10png!=null }">
        <img src="<c:if test="${!mib.pngPath.top10png.contains('Tools') }">${uploadPath }/${mib.userId }/${mib.appId }/${mib.dataKey }</c:if>${mib.pngPath.top10png }" style="width:90%;">
      </c:if>
    </div>
</section>
<section class="section8 border1 w3cbbs">
    <h3>序列质量分析（见QC结果）</h3>
    <div class="h2">Basic Statistics</div>
    <table class="table table-green table-striped-blue table-text-center">
        <thead>
            <tr>
                <th>#Measure</th>
                <th colspan="2">Value</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>Filename</td>
                <td>${mib.basicStatistics1.Filename }</td>
                <td>${mib.basicStatistics2.Filename }</td>
            </tr>
            <tr>
                <td>File type</td>
                <td>${mib.basicStatistics1.FileType }</td>
                <td>${mib.basicStatistics2.FileType }</td>
            </tr>
            <tr>
                <td>Encoding</td>
                <td>${mib.basicStatistics1.Encoding }</td>
                <td>${mib.basicStatistics2.Encoding }</td>
            </tr>
            <tr>
                <td>Total Sequences</td>
                <td>${mib.basicStatistics1.TotalSeq }</td>
                <td>${mib.basicStatistics2.TotalSeq }</td>
            </tr>
            <tr>
                <td>Filtered Sequences</td>
                <td>${mib.basicStatistics1.FilteredSeq }</td>
                <td>${mib.basicStatistics2.FilteredSeq }</td>
            </tr>
            <tr>
                <td>Sequence length</td>
                <td>${mib.basicStatistics1.SeqLength }</td>
                <td>${mib.basicStatistics2.SeqLength }</td>
            </tr>
            <tr>
                <td>%GC</td>
                <td>${mib.basicStatistics1.gc }</td>
                <td>${mib.basicStatistics2.gc }</td>
            </tr>
        </tbody>
    </table>
    <table style="width:100%;">
      <tr>
        <td style="width:50%;"><img style="width: 100%;" src="<c:if test="${!mib.qualityPath1.contains('Tools') }">${uploadPath }/${mib.userId }/${mib.appId }/${mib.dataKey }</c:if>${mib.qualityPath1 }"></td>
        <td><img style="width: 100%;" src="<c:if test="${!mib.qualityPath2.contains('Tools') }">${uploadPath }/${mib.userId }/${mib.appId }/${mib.dataKey }/</c:if>${mib.qualityPath2 }"></td>
      </tr>
      <tr>
        <td><img style="width: 100%;" alt="" src="<c:if test="${!mib.seqContentPath1.contains('Tools') }">${uploadPath }/${mib.userId }/${mib.appId }/${mib.dataKey }</c:if>${mib.seqContentPath1 }"></td>
        <td><img style="width: 100%;" alt="" src="<c:if test="${!mib.seqContentPath2.contains('Tools') }">${uploadPath }/${mib.userId }/${mib.appId }/${mib.dataKey }</c:if>${mib.seqContentPath2 }"></td>
      </tr>
    </table>
</section>
<script language="javascript" src="<%=request.getContextPath()%>/plugins/jQuery/jquery-1.11.3.min.js"></script>
<script src="//cdn.bootcss.com/echarts/2.2.7/echarts.js"></script>
<script src="<%=request.getContextPath() %>/js/charts.js"></script>
<script type="text/javascript">
$(function(){
    var mib_readsDisInfo = $("#reads-distribution-char").text();
    var mib_familyDisInfo = $("#family-distribution-char").text();
    var min_genusDisInfo = $("#genus-distribution-char").text();
    if(mib_readsDisInfo != ""){
        $.reportChar.draw.circularGraph("reads-distribution-char","Reads","Distribution",eval("("+mib_readsDisInfo+")"));
    }
    if(mib_familyDisInfo != ""){
        $.reportChar.draw.circularGraph("family-distribution-char","Family","Distribution",eval("("+mib_familyDisInfo+")"));
    }
    if(min_genusDisInfo != ""){
        $.reportChar.draw.singleBar("genus-distribution-char","Top 10 genus distribution","",eval("("+min_genusDisInfo+")"),"Depth","Depth");
    }
});
function preview(obj){
    var inputVal;
    $("body").find("section").each(function(){
        $(this).removeClass("border1");
    });
    $("body").find("input[type='text']").each(function(){
        inputVal = $(this).val();
        classname = $(this).attr("class");
        $(this).parent().html("<input type='hidden' value='"+classname+"'><span name='print'>"+inputVal+"</span>");
    });
    var sex = $("input[type='radio']:checked").val();
    $("#_sex").html(sex);
    $("#change").hide();
    window.print();
    $("#change").show();
    $("body").find("section").each(function(){
        $(this).addClass("border1");
    });
    $("body").find("span[name='print']").each(function(){
        inputVal = $(this).html();
        classname = $(this).prev().val();
        $(this).parent().html("<input type='text' class='"+classname+"' value='"+inputVal+"'>");
    });
    $("#_sex").html("<input type='radio' name='sex' value='男'>男<input type='radio' name='sex' value='女'>女");
    $("input[type='radio'][value="+sex+"]").prop("checked",true); 
}
</script>
</body>
</html>