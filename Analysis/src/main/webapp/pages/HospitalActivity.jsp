<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="breadcrumbs" id="breadcrumbs">
	<script type="text/javascript">
		try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
	</script>
	<ul class="breadcrumb">
		<li>
			<i class="icon-home home-icon"></i>
			<a href="#">医院统计</a>
		</li>
		<li class="active">活跃度统计</li>
	</ul><!-- .breadcrumb -->
</div>

<div class="page-content">
	<div class="row">
		<div class="col-xs-12">
			<div class="row">
				<div class="col-xs-12">
					<h3 class="header smaller lighter green">
						<i class="icon-signal"></i>
						月新增医院统计
					</h3>
					<div class="col-sm-10" style="height:350px;" id="newHospitalEvyMonth">
					</div>
				</div>
			</div><!-- /row -->
			<div class="hr hr32 hr-dotted"></div>
			<div class="row">
				
			</div>
		<!-- PAGE CONTENT ENDS -->
		</div><!-- /.col -->
	</div><!-- /.row -->
</div><!-- /.page-content -->
<script type="text/javascript">
    function chars() {
// 	    var theme=require('./plugin/echarts-2.2.3/src/theme/infographic.js')
   		$.get("company!getCompanyNumEveryMonth",{},function(result){
       		var xAxis = eval("["+result.timeLine+"]");
       		var yAxis = eval("["+result.data+"]");
	        // 基于准备好的dom，初始化echarts图表
	        var myChart = echarts.init(document.getElementById('newHospitalEvyMonth')); 
	        option = {
	       	    tooltip : {
	       	        trigger: 'axis'
	       	    },
	       	    legend: {
	       	        data:['月新增医院数量']
	       	    },
	       	    toolbox: {
	       	        show : true,
	       	        feature : {
	       	            mark : {show: true},
	       	            dataZoom : {show: true},
	       	            dataView : {show: true},
	       	            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
	       	            restore : {show: true},
	       	            saveAsImage : {show: true}
	       	        }
	       	    },
	       	    calculable : true,
	       	    dataZoom : {
	       	        show : true,
	       	        realtime : true,
	       	        start : 0,
	       	        end : 80
	       	    },
	       	    xAxis : [
	       	        {
	       	            type : 'category',
	       	            boundaryGap : false,
	       	            data : xAxis
	       	        }
	       	    ],
	       	    yAxis : [
	       	        {
	       	            type : 'value'
	       	        }
	       	    ],
	       	    series : [
	       	        {
	       	            name:'月新增医院数量',
	       	            type:'line',
	       	            data:yAxis
	       	        }
	       	    ]
	       	};
	        // 为echarts对象加载数据 
	        myChart.setOption(option); 
    	})
    }
    chars();
</script>