<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 优先使用最新版本的IE 和 Chrome 内核 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="baidu-site-verification" content="IsldTuHqik" />
<title>CelLoud-血流感染报告</title>
<meta name="keywords" content="上海华点云生物科技有限公司,celloud,生物信息云平台,生物大数据平台,序列数据分析,基因大数据" />
<meta name="description" content="一站式高通量基因检测数据分析系统">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="bookmark" href="<%=request.getContextPath()%>/images/favicon.ico" />
<link rel="shortcut icon" href="<%=request.getContextPath()%>/images/favicon.ico" />
<link href="<%=request.getContextPath() %>/css/print.css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/css/print_bsi.css" rel="stylesheet">
</head>
<body>
  <div class="buttons">
    <a id="save" class="btn btn-default" href="javascript:void(0)">分享</a>
    <a id="reset" class="btn btn-default" href="javascript:void(0)">下载</a>
    <a id="print" class="btn btn-default" href="javascript:void(0)">打印</a>
  </div>
  <div class="title">
    <span class="title-text">细菌性血流感染检测报告</span>
    <img class="title-logo" src="<%=request.getContextPath() %>/images/hospitalIcon/demo-logo.png"></div>
  </div>
  <form id="print-form">
  <div class="base-info">
    <ul>
        <li><lable>姓名：</lable><span><input class="width-80" type="text" name="" value="患者姓名"></span></li>
        <li><lable>性别：</lable><span id="sex-text"></span><span id="sex"><input type="radio" name="sex" value="男">男<input type="radio" name="sex" value="女" >女<input type="radio" name="sex" value="不详" >不详</span></li>
        <li><lable>年龄：</lable><span><input class="width-80" type="text" name="" value="19"></span>岁</li>
        <li></li>
        <li><lable>科室：</lable>
           <span id="department-text"></span>
           <select id="department" class="width-80">
	          <option value="ICU病房">ICU病房</option>
	          <option value="呼吸科">呼吸科</option>
	          <option value="儿科">儿科</option>
	          <option value="感染科">感染科</option>
	          <option value="产科">产科</option>
	        </select>
        </li>
        <li><lable>住院号：</lable><span><input class="width-120" type="text" name="" value="83947536"></span></li>
        <li><lable>床号：</lable><span><input class="width-80" type="text" name="" value="ab0302"></span></li>
        <li><lable>ID号：</lable><span><input class="width-80" type="text" name="" value="az00888"></span></li>
        <li><lable>样本种类：</lable>
            <span id="sample-type-text"></span>
	        <select id="sample-type" class="width-80">
	          <option value="血液">血液</option>
	          <option value="尿">尿</option>
	          <option value="腹水">腹水</option>
	          <option value="阴道拭子">阴道拭子</option>
	          <option value="脑脊液">脑脊液</option>
	          <option value="分泌物">分泌物</option>
	          <option value="大便">大便</option>
	          <option value="鼻咽拭子">鼻咽拭子</option>
	        </select>
        </li>
        <li><lable>样本编号：</lable><span><input class="width-120" type="text" name="" value="88484"></span></li>
        <li><lable>送检医生：</lable><span><input class="width-80" type="text" name="" value="ab0302"></span></li>
        <li><lable>送检日期：</lable><span><input class="width-80" type="text" name=""></span></li>
    </ul>
  </div>
  <div class="tests-content">
    <h4>检测结果：</h4>
    <div class="tests-info">
       <p>检测到金黄色葡萄球菌。</p><br>
       <p>注：从16S序列同源性考虑，该菌与溶血葡萄球菌，表皮葡萄球菌非常相似。</p>
    </div>
  </div>
  <div class="test-description">
    <p>检测范围(20种)：</p>
    <table class="table table-bordered table-condensed">
      <tr>
        <td rowspan="2"><span>G+</span></td>
        <td><span>牛链球菌</span></td>
        <td><span>葡萄球菌</span></td>
        <td><span>肺炎链球菌</span></td>
        <td><span>粪肠球菌</span></td>
      </tr>
      <tr>
        <td><span>屎肠球菌</span></td>
        <td><span>难辨梭状芽孢杆菌</span></td>
        <td><span>溃疡棒状杆菌</span></td>
        <td><span>结膜干燥杆菌</span></td>
      </tr>
      <tr>
        <td rowspan="3"><span>G-</span></td>
        <td><span>大肠埃希菌</span></td>
        <td><span>铜绿假单胞菌</span></td>
        <td><span>鲍曼不动杆菌</span></td>
        <td><span>肺炎克雷伯菌</span></td>
      </tr>
      <tr>
        <td><span>嗜麦芽窄食单胞菌</span></td>
        <td><span>粘质沙雷氏菌</span></td>
        <td><span>阴沟肠杆菌</span></td>
        <td><span>嗜水气单胞菌</span></td>
      </tr>
      <tr>
        <td><span>产气肠杆菌</span></td>
        <td><span>肠炎沙门菌</span></td>
        <td><span>奇异变形杆菌</span></td>
        <td><span>洋葱伯克霍尔德菌</span></td>
      </tr>
    </table>
    <p>检测方法：<span>基于16S rDNA高通量测序方法</span></p>
  </div>
  <div class="tests-footer">
    <ul>
        <li>检测人：<span><input class="width-120" type="text" name="" value=""></span></li>
        <li>复核人：<span><input class="width-120" type="text" name="" value=""></span></li>
        <li>报告时间：<span><input class="width-120" type="text" name="" value=""></span></li>
    </ul>
    <div class="statement"><span>声明：1.报告仅对本次送检样品负责;2.检测结果仅供医师参考</span></div>
  </div>
  </form>
  <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
  <script type="text/javascript">
    $(document).ready(function(){
      $("#save").on("click",function(){
        
      });
      $("#reset").on("click",function(){
        
      });
	  $("#print").on("click",function(){
	    $(".base-info").find("input[type='text']").each(function(){
	       $(this).parent().html("<input type='hidden' value='"+$(this).attr("class")+"'><span name='print-input'>"+$(this).val()+"</span>");
	    });
	    $("#department-text").text($("#department").val());
	    $("#department").addClass("hide");
	    $("#sample-type-text").text($("#sample-type").val());
        $("#sample-type").addClass("hide");
	    $("#sex-text").html($("input[type='radio'][name='sex']:checked").val());
	    $("#sex").addClass("hide");
	    $(".buttons").hide();
	    window.print();
	    $("#department-text").text("");
        $("#department").removeClass("hide");
        $("#sample-type-text").text("");
        $("#sample-type").removeClass("hide");
        $("#sex-text").html("");
        $("#sex").removeClass("hide");
	    $(".buttons").show();
	    $("span[name='print-input']").each(function(){
	        $(this).parent().html("<input class='"+$(this).prev().val()+"' type='text' value='"+$(this).html()+"'>");
	    });
	  });
    });
  </script>
</body>
</html>