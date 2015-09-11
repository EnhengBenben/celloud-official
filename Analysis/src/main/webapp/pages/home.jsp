<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="breadcrumbs" id="breadcrumbs">
	<script type="text/javascript">
		try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
	</script>
	<ul class="breadcrumb">
		<li>
			<i class="icon-dashboard"></i>
			<a href="#">控制台</a>
		</li>
	</ul><!-- .breadcrumb -->
</div>

<div class="page-content">
	<div class="row">
		<div class="col-xs-12">
			<div class="row">
				<div class="widget-box transparent">
					<div class="widget-header widget-header-flat">
						<h4 class="lighter">
							<i class="icon-signal"></i>
							总量统计
						</h4>

						<div class="widget-toolbar">
							<a href="#" data-action="collapse">
								<i class="icon-chevron-up"></i>
							</a>
						</div>
					</div>

					<div class="widget-body">
						<div class="widget-main padding-4">
						  <div class="col-sm-7 infobox-container" style="min-width:600px">
							<div class="infobox infobox-green  ">
								<div class="infobox-icon">
									医院总量：
								</div>
								<div class="infobox-data">
									<span class="infobox-data-number">${resultMap.companyNum }</span>
									<span class="infobox-content">（个）</span>
								</div>
							</div>
							<div class="infobox infobox-blue  ">
								<div class="infobox-icon">
									用户总量：
								</div>
								<div class="infobox-data">
									<span class="infobox-data-number">${resultMap.userNum }</span>
									<span class="infobox-content">（个）</span>
								</div>
							</div>
							<div class="infobox infobox-pink  ">
								<div class="infobox-icon">
									报告总量：
								</div>
								<div class="infobox-data">
									<span class="infobox-data-number">${resultMap.reportNum }</span>
									<span class="infobox-content">（个）</span>
								</div>
							</div>
							<div class="infobox infobox-pink  ">
								<div class="infobox-icon">
									APP总量：
								</div>
								<div class="infobox-data">
									<span class="infobox-data-number">${resultMap.appNum }</span>
									<span class="infobox-content">（个）</span>
								</div>
							</div>
							<div class="infobox infobox-red  ">
								<div class="infobox-icon">
									数据总量：
								</div>
								<div class="infobox-data">
									<span class="infobox-data-number">${resultMap.dataNum }</span>
									<span class="infobox-content">(个)</span>
								</div>
							</div>
		
							<div class="infobox infobox-orange2  ">
								<div class="infobox-icon">
									数据总大小：
								</div>
								<div class="infobox-data">
									<span class="infobox-data-number"><fmt:formatNumber pattern="0.00" value="${resultMap.dataSize }"></fmt:formatNumber></span>
									<span class="infobox-content">(GB)</span>
								</div>
							</div>
		
							<div class="space-6"></div>
						</div>
						</div><!-- /widget-main -->
					</div><!-- /widget-body -->
				</div><!-- /widget-box -->
				<div class="space-6"></div>
			</div><!-- /row -->
			<div class="row">
				<div class="col-xs-12">
					<h3 class="header smaller lighter green">医院地理分布</h3>
					<div class="col-sm-10" style="height:350px;" id="map">
					</div>
				</div>
			</div><!-- /row -->
			<div class="space"></div>
			<div class="hr hr32 hr-dotted"></div>
		<!-- PAGE CONTENT ENDS -->
		</div><!-- /.col -->
	</div><!-- /.row -->
</div><!-- /.page-content -->
<script>
function chars() {
	var myChart = echarts.init(document.getElementById('map')); 
	option = {
	    title : {
	        text: '医院用户所分布区域',
	        subtext: 'data from PM25.in',
	        x:'center'
	    },
	    tooltip : {
	        trigger: 'item'
	    },
	    dataRange: {
	        min : 0,
	        max : 500,
	        calculable : true,
	        color: ['maroon','purple','red','orange','yellow','lightgreen']
	    },
	    toolbox: {
	        show : true,
	        orient : 'vertical',
	        x: 'right',
	        y: 'center',
	        feature : {
// 	            mark : {show: true},
// 	            dataView : {show: true, readOnly: false},
	            restore : {show: true},
	            saveAsImage : {show: true}
	        }
	    },
	    series : [
	        {
	            name: 'pm2.5',
	            type: 'map',
	            mapType: 'china',
	            hoverable: false,
	            roam:true,
	            data : [],
	            geoCoord: {
	            	"北京":[116.4,39.9],
	            	"天津":[117.2,39.12],
	            	"上海":[121.47,31.23],
	            	"重庆":[106.55,29.57],
	            	"河北":[114.52,38.05],
	            	"山西":[112.55,37.87],
	            	"辽宁":[123.43,41.8],
	            	"吉林":[125.32,43.9],
	            	"黑龙江":[126.53,45.8],
	            	"江苏":[118.78,32.07],
	            	"浙江":[120.15,30.28],
	            	"安徽":[117.25,31.83],
	            	"福建":[119.3,26.08],
	            	"江西":[115.85,28.68],
	            	"山东":[116.98,36.67],
	            	"河南":[113.62,34.75],
	            	"湖北":[114.3,30.6],
	            	"湖南":[112.93,28.23],
	            	"广东":[113.27,23.13],
	            	"海南":[110.32,20.03],
	            	"四川":[104.07,30.67],
	            	"贵州":[106.63,26.65],
	            	"云南":[102.72,25.05],
	            	"陕西":[108.93,34.27],
	            	"甘肃":[103.82,36.07],
	            	"青海":[101.78,36.62],
	            	"西藏自治区":[91.13,29.65],
	            	"宁夏回族自治区":[106.28,38.47],
	            	"广西壮族自治区":[108.37,22.82],
	            	"新疆维吾尔":[87.62,43.82],
	            	"内蒙古自治区":[111.73,40.83],
	            	"香港":[114.08,22.2],
	            	"澳门":[113.33,22.13],
	            	"台北市":[121.5,25.03]
	            }
	        },
	        {
	            name: '医院',
	            type: 'map',
	            mapType: 'china',
	            data:[],
	            markPoint : {
	                symbol:'emptyCircle',
	                symbolSize : function (v){
	                    return 10 + v/100
	                },
	                effect : {
	                    show: true,
	                    shadowBlur : 0
	                },
	                itemStyle:{
	                    normal:{
	                        label:{show:false}
	                    }
	                },
	                data : [
	                    {name: "河北", value: 13},
	                    {name: "河南", value: 194},
	                    {name: "江苏", value: 229},
	                ]
	            }
	        }
	    ]
	};
	myChart.setOption(option); 
}
chars();

</script>
