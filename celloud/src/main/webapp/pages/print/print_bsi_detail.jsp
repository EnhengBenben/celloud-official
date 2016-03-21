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
  <div id="toc" class="tocify fixed"></div>
  <div class="title">
    <span class="title-text">细菌性血流感染检测报告</span>
    <img class="title-logo" src="<%=request.getContextPath() %>/images/hospitalIcon/demo-logo.png"></div>
  </div>
  <form id="print-form">
  <div id="tocify-content">
  <section class="base-info">
    <h2>1. 样本信息</h2>
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
  </section>
  <section>
    <h2>2.1 检测结果</h2>
    <div class="tests-info">
       <p>金黄色葡萄球菌。</p>
       <p class="statement"><span>说明：从16S序列同源性考虑，该菌与溶血葡萄球菌，表皮葡萄球菌非常相似。</span></p>
	   <p>牛链杆菌。</p>
    </div>
    <h2>2.2 检测结果详述</h2>
    <table class="table table-bordered table-condensed table-text-center">
        <tr>
          <td>菌信息（属、种）</td>
          <td>生化特性</td>
          <td>用药参考</td>
        </tr>
        <tr>
          <td><span class="strain-name">Halomonas mongoliensis</span></td>
          <td>光滑型： 菌落边缘整齐，表面有光泽 </td>
	      <td>最常用的是阿莫西林，再就是甲硝唑</td>
        </tr>
        <tr>
          <td><span class="strain-name">Staphylococcus aureus</span></td>
          <td>湿润、光滑、呈灰色，革兰氏染色阳性 </td>
          <td>大环内酯类：红霉素、白霉素</td>
        </tr>
    </table>
  </section>
  <section>
    <h2>3. 其它可能病原菌</h2>
    <table class="table table-bordered table-condensed table-text-center">
        <tr>
          <td>菌信息（属、种）</td>
          <td>生化特性</td>
          <td>用药参考</td>
        </tr>
        <tr>
          <td><span class="strain-name">Aeribacillus pallidus</span></td>
          <td>培养管理光合细菌的培养 </td>
          <td>氨基糖苷类：包括链霉素、庆大霉素、卡那霉素</td>
        </tr>
        <tr>
          <td><span class="strain-name">Bacillus alveayuensis</span></td>
          <td>培养基配制根据所培养种类 </td>
          <td>大环内酯类：红霉素、白霉素</td>
        </tr>
    </table>
  </section>
  <section>
    <h2>4. 统计信息</h2>
    <table class="table table-bordered table-condensed table-text-center">
        <tr>
          <td>序列总数</td>
          <td>平均质量</td>
          <td>平均GC含量</td>
        </tr>
        <tr>
          <td>596678</td>
          <td>33.1 </td>
          <td>55%</td>
        </tr>
    </table>
    <table>
        <tr>
          <td style="width:33%"><img style="width:100%" src="<%=request.getContextPath() %>/images/print/bsi_1.jpg"></td>
          <td style="width:33%"><img style="width:100%" src="<%=request.getContextPath() %>/images/print/bsi_1.jpg"></td>
          <td style="width:33%"><img style="width:100%" src="<%=request.getContextPath() %>/images/print/bsi_1.jpg"></td>
        </tr>
        <tr>
           <td colspan="3"><img style="width:100%" src="<%=request.getContextPath() %>/images/print/bsi_2.jpg"></td>
        </tr>
    </table>
  </section>
  <section>
    <h2>5. 附件</h2>
    <h3>5.1 生物信息数据分析</h3>
    <table class="table table-bordered table-condensed table-text-center">
        <tr>
          <td>种</td>
          <td>属</td>
          <td>GI号</td>
          <td>覆盖长度%</td>
          <td>种比对上的序列数</td>
          <td>属比对上的序列数</td>
          <td>种序列百分比%</td>
          <td>平均覆盖深度</td>
        </tr>
        <tr>
          <td>Halomonas mongoliensis</td>
          <td>Halomonas </td>
          <td>343198694</td>
          <td>42.00</td>
          <td>23825</td>
          <td>46170</td>
          <td>26.00</td>
          <td>3744.51</td>
        </tr>
        <tr>
          <td>Genus</td>
          <td>Bacillus </td>
          <td>343202629</td>
          <td>23.00</td>
          <td>8760</td>
          <td>9566</td>
          <td>5.00</td>
          <td>708.29</td>
        </tr>
    </table>
    <h3>5.2 16S rRNA序列覆盖分布图</h3>
    <table>
        <tr>
          <td style="width:49%"><img style="width:100%" src="<%=request.getContextPath() %>/images/print/bsi_3.jpg"></td>
          <td style="width:49%"><img style="width:100%" src="<%=request.getContextPath() %>/images/print/bsi_3.jpg"></td>
        </tr>
        <tr>
          <td style="width:49%"><img style="width:100%" src="<%=request.getContextPath() %>/images/print/bsi_3.jpg"></td>
          <td style="width:49%"><img style="width:100%" src="<%=request.getContextPath() %>/images/print/bsi_3.jpg"></td>
        </tr>
        <tr>
          <td style="width:49%"><img style="width:100%" src="<%=request.getContextPath() %>/images/print/bsi_3.jpg"></td>
          <td style="width:49%"><img style="width:100%" src="<%=request.getContextPath() %>/images/print/bsi_3.jpg"></td>
        </tr>
        <tr>
          <td style="width:49%"><img style="width:100%" src="<%=request.getContextPath() %>/images/print/bsi_3.jpg"></td>
          <td style="width:49%"><img style="width:100%" src="<%=request.getContextPath() %>/images/print/bsi_3.jpg"></td>
        </tr>
        <tr>
          <td style="width:49%"><img style="width:100%" src="<%=request.getContextPath() %>/images/print/bsi_3.jpg"></td>
          <td style="width:49%"><img style="width:100%" src="<%=request.getContextPath() %>/images/print/bsi_3.jpg"></td>
        </tr>
    </table>
    <h3>5.3 序列质量分析（QC）</h3>
    <table class="table table-bordered table-condensed table-text-center">
        <tr>
          <td>Column 1</td>
          <td>Column 2</td>
          <td>Column 3</td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td></td>
          <td></td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td></td>
          <td></td>
        </tr>
    </table>
    <table>
        <tr>
          <td><img style="width:100%" src="<%=request.getContextPath() %>/images/print/bsi_4.jpg"></td>
        </tr>
    </table>
  </section>
  </div>
  </form>
  <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
  <script src="//cdn.bootcss.com/jqueryui/1.11.4/jquery-ui.min.js"></script>
  <script src="//cdn.bootcss.com/scrollmonitor/1.0.12/scrollMonitor.min.js"></script>
  <script src="//cdn.bootcss.com/jquery.tocify/1.9.0/javascripts/jquery.tocify.min.js"></script>
  <script type="text/javascript">
    $(document).ready(function(){
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
        $("#toc").hide();
        window.print();
        $("#department-text").text("");
        $("#department").removeClass("hide");
        $("#sample-type-text").text("");
        $("#sample-type").removeClass("hide");
        $("#sex-text").html("");
        $("#sex").removeClass("hide");
        $(".buttons").show();
        $("#toc").show();
        $("span[name='print-input']").each(function(){
            $(this).parent().html("<input class='"+$(this).prev().val()+"' type='text' value='"+$(this).html()+"'>");
        });
      });
      // Tocify Table
      if($.isFunction($.fn.tocify) && $("#toc").length){
          $("#toc").tocify({ 
              context: '#tocify-content', 
              selectors: "h2,h3" 
          });
          var $this = $(".tocify"),
              watcher = scrollMonitor.create($this.get(0));
          watcher.lock();
      }
    });
  </script>
</body>
</html>