<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="breadcrumbs" id="breadcrumbs">
	<script type="text/javascript">
		try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
	</script>
	<ul class="breadcrumb">
		<li>
			<i class="icon-home home-icon"></i>
			<a href="#">首页</a>
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
							<div class="col-sm-7 infobox-container">
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
			<div class="hr hr32 hr-dotted"></div>
			<div class="row">
				<div class="col-sm-7" id="svg"></div>
				
				<div class="col-sm-5">
					<div class="widget-box transparent">
						<div class="widget-header widget-header-flat">
							<h4 class="lighter">
								<i class="icon-star orange"></i>
								热门域名
							</h4>

							<div class="widget-toolbar">
								<a href="#" data-action="collapse">
									<i class="icon-chevron-up"></i>
								</a>
							</div>
						</div>

						<div class="widget-body">
							<div class="widget-main no-padding">
								<table class="table table-bordered table-striped">
									<thead class="thin-border-bottom">
										<tr>
											<th>
												<i class="icon-caret-right blue"></i>
												名称
											</th>

											<th>
												<i class="icon-caret-right blue"></i>
												价格
											</th>

											<th class="hidden-480">
												<i class="icon-caret-right blue"></i>
												状态
											</th>
										</tr>
									</thead>

									<tbody>
										<tr>
											<td>internet.com</td>

											<td>
												<small>
													<s class="red">$29.99</s>
												</small>
												<b class="green">$19.99</b>
											</td>

											<td class="hidden-480">
												<span class="label label-info arrowed-right arrowed-in">销售中</span>
											</td>
										</tr>

										<tr>
											<td>online.com</td>

											<td>
												<small>
													<s class="red"></s>
												</small>
												<b class="green">$16.45</b>
											</td>

											<td class="hidden-480">
												<span class="label label-success arrowed-in arrowed-in-right">可用</span>
											</td>
										</tr>

										<tr>
											<td>newnet.com</td>

											<td>
												<small>
													<s class="red"></s>
												</small>
												<b class="green">$15.00</b>
											</td>

											<td class="hidden-480">
												<span class="label label-danger arrowed">待定</span>
											</td>
										</tr>

										<tr>
											<td>web.com</td>

											<td>
												<small>
													<s class="red">$24.99</s>
												</small>
												<b class="green">$19.95</b>
											</td>

											<td class="hidden-480">
												<span class="label arrowed">
													<s>无货</s>
												</span>
											</td>
										</tr>

										<tr>
											<td>domain.com</td>

											<td>
												<small>
													<s class="red"></s>
												</small>
												<b class="green">$12.00</b>
											</td>

											<td class="hidden-480">
												<span class="label label-warning arrowed arrowed-right">售完</span>
											</td>
										</tr>
									</tbody>
								</table>
							</div><!-- /widget-main -->
						</div><!-- /widget-body -->
					</div><!-- /widget-box -->
				</div>

			</div>
		<!-- PAGE CONTENT ENDS -->
		</div><!-- /.col -->
	</div><!-- /.row -->
</div><!-- /.page-content -->
<script src="./js/jquery.dataTables.min.js"></script>
<script src="./js/jquery.dataTables.bootstrap.js"></script>
<!-- inline scripts related to this page -->
<script src="./js/d3.min.js"></script>
<script>
	var width  = 700;
	var height = 700;
	
	var svg = d3.select("body").select("#svg").append("svg")
	    .attr("width", width)
	    .attr("height", height)
	    .append("g")
	    .attr("transform", "translate(0,0)");
	
	var projection = d3.geo.mercator()
						.center([107, 31])
						.scale(600)
    					.translate([width/2, height/2]);
	
	var path = d3.geo.path()
					.projection(projection);

	var color = d3.scale.category20();
	
	var texts = svg.append("svg:g")  
    	.attr("id", "texts"); 
	
	d3.json("./js/china.json", function(error,root) {
// 		if (error) 
// 			return console.error(error);
// 		console.log(root.features);
		
		svg.selectAll("path")
			.data( root.features )
			.enter()
			.append("path")
			.attr("stroke","#000")
			.attr("stroke-width",1)
// 			.attr("fill", "#fff")
			.attr("fill-opacity", 0.5)
			.attr("fill", function(d,i){
				return color(i);
			})
			.attr("d", path )
			.on("mouseover",function(d,i){
                d3.select(this)
                    .attr("fill","yellow");
            })
            .on("mouseout",function(d,i){
                d3.select(this)
                    .attr("fill", color(i));
            });
           
		
		texts.selectAll("text")  
	        .data(root.features)  
	        .enter().append("svg:text")  
	        .text(function(d){return d.properties.name;})  
	        .attr("x", function(d){  
	            var local=projection(d.geometry.coordinates[0][0]);  
// 	            if(d.lon=='113.5575191')//处理澳门  
// 	            return (local[0]-30);  
// 	            else return local[0];})
	            return local[0];})
// 	        .attr("y",function(d){  
// 	        	alert(d.geometry.coordinates[0][10]);
// 	            var local=projection(d.geometry.coordinates[0][0]);  
// 	            if(d.lat=='39.1439299') return (local[1]+10);//处理天津  
// 	            else return local[1];  
// 	            })  
// 	            return local[1];   })  
	        .attr('fill','#000')  
	        .attr('font-size','14px')  
	        ;  
		
	});

</script>
