(function(){
  function viewDataReport(userId,dataKey,fileName,appId,appName,proId,proName,obj){
	  var href = "#/reportpro/"+ appName + "/" + appId + "/" + dataKey + "/" + proId;
	  console.log(href);
	  window.location.href = href; 
  }
  
  function getCountValue(key,id){
		var num = 0;
		var val;
		$("#"+id).find("tr").each(function(i){
			if(i==0){
				$(this).find("th").each(function(j){
					var title = $(this).html();
					if(title==key){
						num = j;
					}
				});
			}else{
				$(this).find("td").each(function(k){
					if(num==k){
						val = $(this).html();
						if(val.indexOf(",") > 0){
							var reg=new RegExp(",","g"); //创建正则RegExp对象  
							val=val.replace(reg,"");  
						}
					}
				});
			}
		});
		return val;
	}
  
  celloudApp.directive('loadOver', function ($timeout) {
    return {
        restrict: 'A',
        link: function(scope, element, attr) {
            if (scope.$last === true) {
                $timeout(function() {
                    scope.$emit('reportLoadOver');
                });
            }
        }
    };
  });
  
  /**
   * egfr数据报告controller
   */
  celloudApp.controller("egfrDataReportController", function($scope, $routeParams, dataReportService){
	  dataReportService.getDataReportInfo("report/getEGFRInfo",$routeParams.dataKey,$routeParams.projectId,$routeParams.appId).
	  success(function(egfrInfo){
		  $scope.egfr = egfrInfo.egfr;
		  $scope.project = egfrInfo.project;
		  $scope.uploadPath = egfrInfo.uploadPath;
		  // 数据参数同比
		  var length = $scope.egfr.pos;
		  if(length==0 || isNaN(length)){
			  $("#charDiv").html("<p style=\"color: red;\">数据异常，没有同比结果</p>");
		  }else{  
			  $.get("count/egfrCompare",{"length":length},function(data){
			      var div = $("<div id='char0' class='col-lg-6' style='width: 1000px;height:400px;'></div>");
				  $("#charDiv").append(div);
				  var X = "[";
				  var Y = "[";
				  var value = data.split("\n");
				  if(value.length > 1){
					  for(var k=0;k<value.length-1;k++){
						  var n = value[k].split("\t");
						  X+="'"+n[0]+"',";
						  Y+=n[1]+",";
					  }
				  }else{
					  var n = data.split("\t");
					  X+="'"+n[0]+"',";
					  Y+=n[1]+",";
				  }
				  X = X.substring(0,X.length-1)+"]";
				  Y = Y.substring(0,Y.length-1)+"]";
				  $.reportChar.draw.echartsShowBar("char0", "位点", eval(X), eval(Y), 0, 500, 300);
			  });
		  }
		  $scope.showHelp = function(){
			  $("#helpModal").modal("show");
		  }
	  });
  });
  /**
   * kras数据报告controller
   */
  celloudApp.controller("krasDataReportController", function($scope, $routeParams, dataReportService){
	  dataReportService.getDataReportInfo("report/getKRASInfo",$routeParams.dataKey,$routeParams.projectId,$routeParams.appId).
	  success(function(krasInfo){
		  $scope.kras = krasInfo.kras;
		  $scope.project = krasInfo.project;
		  $scope.uploadPath = krasInfo.uploadPath;
		  // 数据参数同比
		  var length = $scope.kras.pos;
		  if(length==0 || isNaN(length)){
			  $("#charDiv").html("<p style=\"color: red;\">数据异常，没有同比结果</p>");
		  }else{  
			  $.get("count/krasCompare",{"length":length},function(data){
				  var div = $("<div id='char0' class='col-lg-6' style='width: 1000px;height:400px;'></div>");
				  $("#charDiv").append(div);
				  var X = "[";
				  var Y = "[";
				  var value = data.split("\n");
				  if(value.length > 1){
					  for(var k=0;k<value.length-1;k++){
						  var n = value[k].split("\t");
						  X+="'"+n[0]+"',";
						  Y+=n[1]+",";
					  }
				  }else{
					  var n = data.split("\t");
					  X+="'"+n[0]+"',";
					  Y+=n[1]+",";
				  }
					  X = X.substring(0,X.length-1)+"]";
					  Y = Y.substring(0,Y.length-1)+"]";
					  $.reportChar.draw.echartsShowBar("char0", "位点", eval(X), eval(Y), 0, 500, 300);
		    });
		  }
		  $scope.showHelp = function(){
			  $("#helpModal").modal("show");
		  }
	  });
  });
  /**
   * hcv数据报告controller
   */
  celloudApp.controller("hcvDataReportController", function($scope, $routeParams, dataReportService){
	  dataReportService.getDataReportInfo("report/getHCVInfo",$routeParams.dataKey,$routeParams.projectId,$routeParams.appId).
	  success(function(hcvInfo){
		  $scope.hcv = hcvInfo.hcv;
		  $scope.project = hcvInfo.project;
		  $scope.uploadPath = hcvInfo.uploadPath;
		  
		  $scope.change = function(){
			  var val = $("#_change").html();
			  if(val=="显示更多"){
				  $("#nomal").css("display","");
				  $("#cfda").css("display","none");
				  $("#_change").html("返回");
			  }else{
				  $("#nomal").css("display","none");
				  $("#cfda").css("display","");
				  $("#_change").html("显示更多");
			  }
		  }
		  // 数据参数同比
		  $.get("count/hcvCompare",{},function(data){
			  var div = $("<div id='char0' class='col-lg-6' style='width: 1000px;height:400px;'></div>");
			  $("#charDiv").append(div);
			  var one = getCountValue("Subtype","nomal");
			  var X = "[";
			  var Y = "[";
			  var value = data.split(";");
			  for(var k=0;k<value.length-1;k++){
			  	  var n = value[k].split(",");
			  	  X+="'"+n[0]+"',";
			  	  if(n[0]==one){
			  	  	  Y+="{ dataLabels: { enabled: true, y: 0, crop: false, style: { fontWeight: 'bold', fontSize:16 }, }, y:"+ n[1]+"},";
			  	  }else{
			  	  	  Y+=n[1]+",";
			  	  }
			  }
			  X = X.substring(0,X.length-1)+"]";
			  Y = Y.substring(0,Y.length-1)+"]";
			  $.reportChar.draw.echartsShowBar("char0", "Subtype", eval(X), eval(Y), 0, 500, 300);
		});
	  });
  });
  /**
   * braf数据报告controller
   */
  celloudApp.controller("brafDataReportController", function($scope, $routeParams, $compile, dataReportService){
	  dataReportService.getDataReportInfo("report/getBRAFInfo",$routeParams.dataKey,$routeParams.projectId,$routeParams.appId).
	  success(function(brafInfo){
		  $scope.braf = brafInfo.braf;
		  $scope.project = brafInfo.project;
		  $scope.uploadPath = brafInfo.uploadPath;
		  
		  var $table = $($scope.braf.mutationPosition);
		  $scope.searchTable = function(){
			  var result = "";
			  $table.find("td").each(function() {
		      	  var context = $(this).html();
		      	  var len = context.indexOf("-");
		      	  var before = $.trim(context.substring(len - 2, len - 1));
		      	  var after = $.trim(context.substring(len + 1, len + 3));
		      	  var d = context.indexOf(",");
		      	  var k = context.indexOf(")");
		      	  if (k == -1) {
		      	  	  result += after;
		      	  } else if (before != after) {
		      	  	  result += after;
		      	  } else {
		      	  	  var search = $("#_snum").val();
		      	  	  var r = context.substring(d + 1, k);
		      	  	  if (parseFloat(r) > parseFloat(search)) {
		      	  		  result += after;
		      	  	  } else {
		      	  		  var l = context.indexOf("|");
		      	  		  var r = context.indexOf("(");
		      	  		  result += context.substring(l + 1, r);
		      	  	  }
		      	  }
		      });
		      $("#searchResult").html(map[result]);
          }
		  $scope.searchTable();
	  });
  });
  
  /**
   * tbRifampicin数据报告controller
   */
  celloudApp.controller("tbRifampicinDataReportController", function($scope, $routeParams, $compile, dataReportService){
	  dataReportService.getDataReportInfo("report/getTBRifampicinInfo",$routeParams.dataKey,$routeParams.projectId,$routeParams.appId).
	  success(function(tbrifampicinInfo){
		  $scope.tbrifampicin = tbrifampicinInfo.tbrifampicin;
		  $scope.project = tbrifampicinInfo.project;
		  $scope.uploadPath = tbrifampicinInfo.uploadPath;
		  
		  $scope.searchTable = function(){
			  var search = $("#_snum").val();
				$("#_sr").html("");
				$($scope.tbrifampicin.report).find("td").each(function() {
					var context = $(this).html();
					if (!search) {
						$("#_sr").append("<tr><td>"+ context.replace(new RegExp("\t","g"),"&nbsp;&nbsp;&nbsp;&nbsp;")+ "</tr></td>");
					} else {
						var len = context.indexOf("-");
						var d = context.indexOf(",");
						var k = context.indexOf(")");
						if (len == -1) {
							if (d > -1 && k > -1) {
								var result = context.substring(d + 1, k);
								if (parseFloat(result) < parseFloat(search)) {
									$("#_sr").append("<tr><td>"+ context.replace(new RegExp("\t","g"),"&nbsp;&nbsp;&nbsp;&nbsp;")+ "</tr></td>");
								} else {
									var kl = context.indexOf("(");
									context = context.substring(0, kl)+ context.substring(k + 1,context.length);
									$("#_sr").append("<tr><td>"+ context.replace(new RegExp("\t","g"),"&nbsp;&nbsp;&nbsp;&nbsp;") + "</tr></td>");
								}
							} else {
								$("#_sr").append("<tr><td>"+ context.replace(new RegExp("\t","g"),"&nbsp;&nbsp;&nbsp;&nbsp;")+ "</tr></td>");
							}
						} else {
							var before = $.trim(context.substring(len - 2, len - 1));
							var after = $.trim(context.substring(len + 1, len + 3));
							if (before == after) {
								if (d > -1 && k > -1) {
									var result = context.substring(d + 1, k);
									if (parseFloat(result) < parseFloat(search)) {
										$("#_sr").append("<tr><td>"+ context.replace(new RegExp("\t","g"),"&nbsp;&nbsp;&nbsp;&nbsp;")+ "</tr></td>");
									}
								} else {
									$("#_sr").append("<tr><td>"+ context.replace(new RegExp("\t","g"),"&nbsp;&nbsp;&nbsp;&nbsp;")+ "</tr></td>");
								}
							} else {
								if (d > -1 && k > -1) {
									var result = context.substring(d + 1, k);
									if (parseFloat(result) > parseFloat(search)) {
										var c = context.split("\t");
										var c3 = c[3].substring(0, c[3].indexOf("|"));
										var c4 = (c[4].substring(0,c[3].lastIndexOf("|") - 1) + c[4].substring(c[4].lastIndexOf(")") + 1,c[4].length)).replace("(", "");
										var c5;
										if (c[5].indexOf("|") == -1) {
											c5 = c[5];
										} else {
											c5 = c[5].substring(0, c[5].indexOf("|"));
										}
										var last = c[0] + "\t" + c[1] + "\t" + c[2] + "\t" + c3 + "\t" + c4 + "\t" + c5;
										$("#_sr").append("<tr><td>"+ last.replace(new RegExp("\t","g"),"&nbsp;&nbsp;&nbsp;&nbsp;")+ "</tr></td>");
									} else {
										$("#_sr").append("<tr><td>"+ context.replace(new RegExp("\t","g"),"&nbsp;&nbsp;&nbsp;&nbsp;")+ "</tr></td>");
									}
								} else {
									$("#_sr").append("<tr><td>"+ context.replace(new RegExp("\t","g"),"&nbsp;&nbsp;&nbsp;&nbsp;")+ "</tr></td>");
								}
							}
						}
					}
				});
		  }
		  $scope.searchTable();
		  $.get("count/tbCompare",{},function(data){
				var div = $("<div id='char0' class='col-lg-6' style='width: 1000px;height:400px;'></div>");
				$("#charDiv").append(div);
				var X = "[";
				var Y = "[";
				var value = data.split("\n");
				if(value.length > 1){
					for(var k=0;k<value.length-1;k++){
						var n = value[k].split("\t");
						X+="'"+n[0]+"',";
						Y+=n[1]+",";
					}
				}else{
					var n = data.split("\t");
					X+="'"+n[0]+"',";
					Y+=n[1]+",";
				}
				X = X.substring(0,X.length-1)+"]";
				Y = Y.substring(0,Y.length-1)+"]";
				$.reportChar.draw.echartsShowBar("char0", "位点", eval(X), eval(Y), 0, 500, 300);
		});
		  
	  });
  });
  
  
  /**
   * tbinh数据报告controller
   */
  celloudApp.controller("tbinhDataReportController", function($scope, $routeParams, $compile, dataReportService){
	  dataReportService.getDataReportInfo("report/getTBINHInfo",$routeParams.dataKey,$routeParams.projectId,$routeParams.appId).
	  success(function(tbinhInfo){
		  $scope.tbinh = tbinhInfo.tbinh;
		  $scope.project = tbinhInfo.project;
		  $scope.uploadPath = tbinhInfo.uploadPath;
		  
		  // 数据参数同比
		  var mutant = tbinhInfo.mutant;
		  var wild = tbinhInfo.wild;
		  var neither = tbinhInfo.neither;
		  var data = [{name:'Mutant strain',value:mutant},{name:'Wild type',value:wild},{name:'No Result',value:neither}];
		  $.reportChar.draw.echartsShowPie("_showPie","Samples Statistic",data);
		  
		  $scope.searchTable = function(numId, sourceId, resultId) {
			var search = $("#" + numId).val();
			$("#" + resultId).html("");
			var i = 0;
			if(sourceId == "r1"){
				$table = $($scope.tbinh.position);
			}else{
				$table = $($scope.tbinh.mutationPosition);
			}
			$table.find("td").each(function() {
				var context = $(this).html();
				if (search == "") {
					i++;
					$("#" + resultId).append("<tr><td>" + context + "</tr></td>");
				} else {
					var len = context.lastIndexOf("-");
					var before = $.trim(context.substring(len - 2, len - 1));
					var after = $.trim(context.substring(len + 1, len + 3));
					var d = context.indexOf(",");
					var k = context.indexOf(")");
					if (before == after) {
						if (d > -1 && k > -1) {
							var result = context.substring(d + 1, k);
							if (parseFloat(result) < parseFloat(search)) {
								i++;
								$("#" + resultId).append("<tr><td>" + context + "</tr></td>");
							}
						} else {
							i++;
							$("#" + resultId).append("<tr><td>" + context + "</tr></td>");
						}
					} else {
						var sub = context.indexOf("|");
						if (sub > -1) {
							if (d > -1 && k > -1) {
								var result = context.substring(d + 1, k);
								if (parseFloat(result) > parseFloat(search)) {
									var last = context.substring(k + 1, context.length);
									var l = last.indexOf("|");
									if (l == -1) {
										l = last.length;
									}
									i++;
									$("#" + resultId).append("<tr><td>" + context.substring(0, sub) + last.substring(0, l) + "</tr></td>");
								} else {
									i++;
									$("#" + resultId).append("<tr><td>" + context + "</tr></td>");
								}
							} else {
								i++;
								$("#" + resultId).append("<tr><td>" + context + "</tr></td>");
							}
						} else {
							i++;
							$("#" + resultId).append("<tr><td>" + context + "</tr></td>");
						}
					}
				}
			});
			if (i == 0) {
				$("#" + resultId).append("<tr><td style='color:red'>没有符合筛选条件的结果</tr></td>");
			}
		}
	    $scope.searchTable("_snum1", "r1", "_sr1");
	    $scope.searchTable("_snum2", "r2", "_sr2");
	  });
  });
  /**
   * hbv数据报告controller
   */
  celloudApp.controller("hbvDataReportController", function($scope, $routeParams, $compile, dataReportService){
	  dataReportService.getDataReportInfo("report/getHBVInfo",$routeParams.dataKey,$routeParams.projectId,$routeParams.appId).
	  success(function(hbvInfo){
		  $scope.hbv = hbvInfo.hbv;
		  $scope.project = hbvInfo.project;
		  $scope.uploadPath = hbvInfo.uploadPath;
		  
		  $scope.change1 = function(){
			  $("#nomal").css("display","");
			  $("#cfda").css("display","none");
		  }
		  $scope.change2 = function(){
			  $("#nomal").css("display","none");
			  $("#cfda").css("display","");
		  }
		  $scope.showModal = function(id,flag,num){
			  $("#_showOne").css("display","none");
			  $("#_showMore").css("display","none");
			  $("#_showImg").css("display","none");
			  if(flag == 2){
				  $("#_showImg").css("display","");
			  }else if(flag == 1){
				  $("#_showOne").css("display","");
			  }else if(flag == 0){
				  $(".y").css("display","none");
				  if(num == 1){
				  	$(".y1").css("display","");
				  }else if(num == 2){
				  	$(".y2").css("display","");
				  }else if(num == 3){
				  	$(".y3").css("display","");
				  }else if(num == 4){
				  	$(".y4").css("display","");
				  }else if(num == 5){
				  	$(".y5").css("display","");
				  }else if(num == 6){
				  	$(".y6").css("display","");
				  }else if(num == 7){
				  	$(".y7").css("display","");
				  }
				  $("#_showMore").css("display","");
			  }
			  $("#"+id).modal("show");
			  $("#"+id).find(".modal-body").scrollTop(0);
		  }
		  
		  $.get("count/hbvCompare",{"appId":82,"path":DATAPATH},function(data){
				var div0 = $("<div id='char0' class='col-lg-12' style='width: 1000px;height: 450px;'></div>");
				$("#charDiv").append(div0);
				var sType = $("#snpType").html();
				if(!sType){
					sType = "";
				}
				var hbvtype=eval(data.split("@")[1]);
				var aType = "[";
				if(sType.indexOf("A")>=0){
					aType += "['A'," + hbvtype[0]/2 + "]";
				}
				if(sType.indexOf("B")>=0){
					aType += ("'null',['B'," + hbvtype[1]/2 + "]");
				}
				if(sType.indexOf("C")>=0){
					aType += ("'null','null',['C'," + hbvtype[2]/2 + "]");
				}
				if(sType.indexOf("D")>=0){
					for(var i = 0;i < 3;i++){
						aType += 'null';
					}
					aType += ("['D'," + hbvtype[3]/2 + "]");
				}
				if(sType.indexOf("E")>=0){
					for(var i = 0;i < 4;i++){
						aType += 'null';
					}
					aType += ("['E'," + hbvtype[4]/2 + "]");
				}
				if(sType.indexOf("F")>=0){
					for(var i = 0;i < 5;i++){
						aType += 'null';
					}
					aType += ("['F'," + hbvtype[5]/2 + "]");
				}
				if(sType.indexOf("G")>=0){
					for(var i = 0;i < 6;i++){
						aType += 'null';
					}
					aType += ("['G'," + hbvtype[6]/2 + "G");
				}
				if(sType.indexOf("H")>=0){
					for(var i = 0;i < 7;i++){
						aType += 'null';
					}
					aType += ("['H'," + hbvtype[7]/2 + "H");
				}
				if(sType.indexOf("no match")>=0){
					for(var i = 0;i < 8;i++){
						aType += 'null';
					}
					aType += ("['比对失败'," + hbvtype[8]/2 + "]");
				}
				if(sType==""){
					for(var i = 0;i < 9;i++){
						aType += 'null';
					}
					aType += ("['异常数据'," + hbvtype[9]/2 + "]");
				}
				aType += "]";
				$.reportChar.draw.echartsShowHBVType('char0',hbvtype,aType,45);
				
				
				var result = $("#resultDiv").html();
				if(result){
					var temp = result.split("<br>");
					var rType = new Array();
					if(temp[0].indexOf("未检测到")<0){
						rType.push("ADV");
					}
					if(temp.length>1 && temp[1].indexOf("未检测到")<0){
						rType.push("TDF");
					}
					if(temp.length>2 && temp[2].indexOf("未检测到")<0){
						rType.push("LAM");
					}
					if(temp.length>3 && temp[3].indexOf("未检测到")<0){
						rType.push("LDT");
					}
					if(temp.length>4 && temp[4].indexOf("未检测到")<0){
						rType.push("FTC");
					}
					if(temp.length>5 && temp[5].indexOf("未检测到")<0){
						rType.push("ETV");
					}
				}
				
				var div1 = $("<div id='char1' class='col-lg-12' style='width: 1000px;height: 535px;margin-left:-15px;'></div>");
				$("#charDiv").append(div1);
				var one = getCountValue("Subtype","nomal");
				var X = "[";
				var Y = "[";
				var value = data.split("@")[0].split(";");
				var num = value.length;
				for(var k=0;k<value.length-1;k++){
					var n = value[k].split(",");
					X+="'"+n[0]+"',";
					var tag=n[0].split("_");
					if(tag.length==rType.length){
						var isOne = false;
						for(var i=0;i<tag.length;i++){
							if($.inArray(tag[i],rType)>=0){
								isOne=true;
							}else{
								isOne=false;
							}
						}
						if(isOne){
							Y+="{value : "+ n[1] +", itemStyle : {normal : {color : '#00cccc'}}},";
						}else{
							Y+="'" + n[1] + "',";
						}
					}else if(rType.length==0&&n[0]=="none"){
						Y+="{value : "+ n[1] +", itemStyle : {normal : {color : '#00cccc'}}},";
					}else{
						Y+="'" + n[1] + "',";
					}
				}
				X = X.substring(0,X.length-1)+"]";
				Y = Y.substring(0,Y.length-1)+"]";
				$.reportChar.draw.echartsShowBar("char1", "耐药类型", X, Y, -45, 800, 350);
			});
		  
	  });
  });
  /**
   * oncogene数据报告controller
   */
  celloudApp.controller("oncogeneDataReportController", function($scope, $routeParams, $compile, dataReportService){
	  dataReportService.getDataReportInfo("report/getOncogeneInfo",$routeParams.dataKey,$routeParams.projectId,$routeParams.appId).
	  success(function(oncogeneInfo){
		  $scope.oncogene = oncogeneInfo.oncogene;
		  $scope.project = oncogeneInfo.project;
		  $scope.uploadPath = oncogeneInfo.uploadPath;
	  });
  });
  /**
   * dpd数据报告controller
   */
  celloudApp.controller("dpdDataReportController", function($scope, $routeParams, $compile, dataReportService){
	  dataReportService.getDataReportInfo("report/getDpdInfo",$routeParams.dataKey,$routeParams.projectId,$routeParams.appId).
	  success(function(dpdInfo){
		  $scope.dpd = dpdInfo.dpd;
		  $scope.project = dpdInfo.project;
		  $scope.uploadPath = dpdInfo.uploadPath;
		  $scope.searchTable = function(){
			var search = $("#_snum").val();
			$("#_sr").html("");
			$($scope.dpd.mutationPosition).find("td").each(function(){
				var context = $(this).html();
				if(search==""){
					$("#_sr").append("<tr><td>"+context+"</tr></td>");
				}else{
					var len = context.indexOf("-");
					var before = $.trim(context.substring(len-2,len-1));
					var after = $.trim(context.substring(len+1,len+3));
					var d = context.indexOf(",");
					var k = context.indexOf(")");
					if(before==after){
						if(d>-1&&k>-1){
							var result = context.substring(d+1,k);
							if(parseFloat(result)<parseFloat(search)){
								$("#_sr").append("<tr><td>"+context+"</tr></td>");
							}
						}else{
							$("#_sr").append("<tr><td>"+context+"</tr></td>");
						}
					}else{
						var sub = context.indexOf("|");
						if(sub>-1){
							if(d>-1&&k>-1){
								var result = context.substring(d+1,k);
								if(parseFloat(result)>parseFloat(search)){
									var last = context.substring(k+1,context.length);
									var l = last.indexOf("|");
									if(l==-1){
										l = last.length;
									}
									$("#_sr").append("<tr><td>"+context.substring(0,sub)+last.substring(0,l)+"</tr></td>");
								}else{
									$("#_sr").append("<tr><td>"+context+"</tr></td>");
								}
							}else{
								$("#_sr").append("<tr><td>"+context+"</tr></td>");
							}
						}else{
							$("#_sr").append("<tr><td>"+context+"</tr></td>");
						}
					}
				}
			});
		}
	  	$scope.searchTable();
	  });
  });
  
  /**
   * AB_INJ数据报告controller
   */
  celloudApp.controller("abinjDataReportController", function($scope, $routeParams, $compile, dataReportService){
	  dataReportService.getDataReportInfo("report/getABINJInfo",$routeParams.dataKey,$routeParams.projectId,$routeParams.appId).
	  success(function(abinjInfo){
		  $scope.abinj = abinjInfo.abinj;
		  $scope.project = abinjInfo.project;
		  $scope.uploadPath = abinjInfo.uploadPath;
	  });
  });
  /**
   * UGT数据报告controller
   */
  celloudApp.controller("ugtDataReportController", function($scope, $routeParams, $compile, dataReportService){
	  dataReportService.getDataReportInfo("report/getUGTInfo",$routeParams.dataKey,$routeParams.projectId,$routeParams.appId).
	  success(function(ugtInfo){
		  $scope.ugt = ugtInfo.ugt;
		  $scope.project = ugtInfo.project;
		  $scope.uploadPath = ugtInfo.uploadPath;
	  });
  });
  /**
   * 16S数据报告controller
   */
  celloudApp.controller("16sDataReportController", function($scope, $routeParams, $compile, dataReportService){
	  dataReportService.getDataReportInfo("report/get16SInfo",$routeParams.dataKey,$routeParams.projectId,$routeParams.appId).
	  success(function(s16Info){
		  $scope.s16 = s16Info.s16;
		  $scope.project = s16Info.project;
		  $scope.uploadPath = s16Info.uploadPath;
		  var $table = $("<div>" + $scope.s16.resultTable + "</div>");
		  $table.find("tr").each(function(i){
			  if(i==0){
			      length = $(this).find("td").length;
			  }else{
			      tdlength = $(this).find("td").length;
			      if(tdlength<length){
			        $(this).children("td").eq(1).attr("colspan",5);
			      }
			  }
		  });
		  $scope.s16.resultTable = $table.html();
	  });
  });
  /**
   * translate数据报告controller
   */
  celloudApp.controller("translateDataReportController", function($scope, $routeParams, $compile, dataReportService){
	  dataReportService.getDataReportInfo("report/getTranslateInfo",$routeParams.dataKey,$routeParams.projectId,$routeParams.appId).
	  success(function(translateInfo){
		  $scope.translate = translateInfo.translate;
		  $scope.project = translateInfo.project;
		  $scope.uploadPath = translateInfo.uploadPath;
	  });
  });
  /**
   * gdd数据报告controller
   */
  celloudApp.controller("gddDataReportController", function($scope, $routeParams, $compile, dataReportService){
	  dataReportService.getDataReportInfo("report/getGDDInfo",$routeParams.dataKey,$routeParams.projectId,$routeParams.appId).
	  success(function(gddInfo){
		  $scope.gdd = gddInfo.cmp;
		  $scope.project = gddInfo.project;
		  $scope.uploadPath = gddInfo.uploadPath;
	  });
  });
  /**
   * pgs数据报告controller
   */
  celloudApp.controller("pgsDataReportController", function($scope, $routeParams, $compile, dataReportService){
	  dataReportService.getDataReportInfo("report/getPgsInfo",$routeParams.dataKey,$routeParams.projectId,$routeParams.appId).
	  success(function(pgsInfo){
		  $scope.pgs = pgsInfo.pgs;
		  $scope.experiment = pgsInfo.experiment;
		  $scope.project = pgsInfo.project;
		  $scope.uploadPath = pgsInfo.uploadPath;
		  
		  $scope.editShowConclusion = function(){
			  $("#edit-conclusion-error").addClass("hide");
			  $("#editReportConclusion").modal("show");
		  }
		  $scope.saveConclusion = function(){
			  var qualified = $("#reportConclusion").find("input:radio[name='qualified']:checked").val();
			  if(!qualified){
			      showAddError("请选择是否合格！");
			      return;
			  }
			  $.get("experiment/updateExperiment",$("#reportConclusionForm").serialize(),function(flag){
			      if(flag == 1){
			    	  showAddError("保存成功,请重新打开数据报告!");
			    	  setTimeout(function(){
			    		  $("#reportConclusion").modal("hide");
			    	  },2000);
			      }else{
			    	  showAddError("保存失败！");
			      }
			  });
		  }
		  
		  function showEdditError(info){
			  $("#edit-conclusionErrorInfo").html(info);
			  $("#edit-conclusion-error").removeClass("hide");
		  }
		  
		  $scope.editSaveConclusion = function(){
			  var qualified = $("#editReportConclusion").find("input:radio[name='qualified']:checked").val();
			  if(!qualified){
			      showEdditError("请选择是否合格！");
			      return;
			  }
			  var remarks = $("#editReportConclusion").find("textarea[name='remarks']").val();
			  if(remarks.length>125){
				  showEdditError("备注长度不能大于125个字符！！");
				  return;
			  }
			  $.get("experiment/updateExperiment",$("#editReportConclusionForm").serialize(),function(flag){
				  if(flag == 1){
					  showEdditError("保存成功,请重新打开数据报告!");
					  setTimeout(function(){
						  $("#editReportConclusion").modal("hide");
					  },2000);
			      }else{
			    	  showEdditError("保存失败！");
			      }
			  });
		  }
		  
//		  $("#reportDiv").find("th").each(function(){
//			  $(this).css("padding-left","20px");
//			  $(this).css("text-align","left");
//		  });
//		  var num = 0;
//		  $("#reportDiv").find("td").each(function(){
//			  var result = $(this).text();
//			  if(num%4==0){
//				  $(this).css("min-width","90px");
//			  }
//			  if(num%4==1){
//				  $(this).css("min-width","55px");
//			  }
//			  if(num%4==2){
//				  if(result.length>85){
//					  $(this).html(result.substring(0,84)+"...");
//					  $(this).attr("title",result);
//				  }
//			  }
//			  if(num%4==3){
//				  if(result.length>39){
//					  $(this).html(result.substring(0,38)+"...");
//					  $(this).attr("title",result);
//				  }
//				  $(this).css("width","300px");
//			  }
//			  $(this).css("padding-left","20px");
//			  $(this).css("text-align","left");
//			  num ++;
//		  });
//		  var trTotal = 0;//记录总共遍历了多少个 tr
//		  var tr = 0;//记录需要在第几个加rowspan
//		  var count = 1;//记录需要rowspan的数目
//		  var need = false;
//		  $("#reportDiv").find("tr").each(function(){
//			  var tdVal = $(this).children('td').eq(0).html();
//			  if(tdVal.toLowerCase().indexOf("chr") == -1){
//		      $(this).children('td').eq(2).remove();
//		      $(this).children('td').eq(2).remove();
//		      tr = trTotal-count;
//		      count ++;
//		      need = true;
//		  }else if(need){
//		      var rowTr = $("#reportTable tr").eq(tr);
//		      $(rowTr).children("td").eq(0).attr("rowspan",count);
//		      $(rowTr).children("td").eq(0).css("vertical-align","middle");
//		      $(rowTr).children("td").eq(1).attr("rowspan",count);
//		      $(rowTr).children("td").eq(1).css("vertical-align","middle");
//		      count =  1;
//		      need = false;
//		  }
//			  trTotal++;
//		  });
		  $scope.showModal = function(id){
			  $("#"+id).modal("show");
		  }
		  var appId = $scope.pgs.appId;
		  // 数据参数同比
		  $.get("count/pgsCompare",{"appId":appId,"columns":charMap[appId]},function(data){
			  var sp = data.split(";");
			  $("#charResult").html("");
			  for ( var i = 1; i < sp.length; i++) {
				  var big = 0;
				  var div = $("<div id='char"+i+"' class='col-lg-5' style='width: 410px;height:400px;'></div>");
				  $("#charDiv").append(div);
				  var ev = sp[i].split(":");
				  var one = getCountValue(ev[0],"_table");
				  var value = ev[1].split(",");
				  var context="[";
				  for(var k=0;k<value.length-1;k++){
				  	context += "[1,"+value[k]+"],";
				  	if(value[k]>one){
				  		big++;
				  	}
				  }
				  context+="]";
				  var percent = 0;
				  if(ev[0] == "Duplicate" || ev[0] == "Duplicate(%)" || ev[0] == "*SD"){
				  	percent = Math.round(big/value.length*100);
				  }else{
				  	percent = 100 - Math.round(big/value.length*100);
				  }
				  $("#charResult").append("本数据的 "+ev[0]+" 打败了 <span class='green'>"+percent+"%</span> 的数据；");
				  var single = "[[2,"+one+"]]";
				  $.reportChar.draw.echartsShowScatter("char" + i, ev[0], eval(context),eval(single));
			  }
		  });
	  });
  });
  
  
  
  celloudApp.controller("projectReportController", function($scope,$rootScope,$routeParams,$location,projectReportService){
    $scope.companyId = companyId;
    $("#shareProjectSelect").select2({
      tags: true,
      tokenSeparators: [',', ' ']
    });
    //加载产品标签
    $scope.ranAppList = projectReportService.getRanAPP();
    //初始化参数
    var options = {
        page : 1,
        pageSize : 10,
        belongs : 1,
        start : null,
        end : null,
        app : 0,
        condition : null
    };
    //分页检索主方法
    $scope.pageQuery = function(currentPage,pageSize){
      var belongs = options.belongs;
      var start = options.start;
      var end = options.end;
      var app = options.app;
      var condition = options.condition;
      options.pageSize = pageSize;
      projectReportService.getReportListCondition(currentPage,pageSize,belongs,start,end,app,condition).
        success(function(dataList){
          $scope.dataList = dataList;
        });
    }
    $scope.pageQuery(options.page,options.pageSize);
    //切换所属
    $scope.changeBelongs = function(id){
      $(".belongs").removeClass("active");
      $("#belongs"+id).addClass("active");
      options.belongs = id;
      $scope.pageQuery(1,options.pageSize);
    }
    var START = null;
    var END = null;
    //设定时间检索
    $scope.changeDate = function(flag){
      $("#_searchDate").val("");
      $("#_endDate").val("");
      $(".changeDate").removeClass("active");
      $("#changeDate"+flag).addClass("active");
      if(flag==0){
        START = null;
        END = null;
      }else{
        var d = new Date();
        var date = new Date(d.getTime()-1000*60*60*24*flag);
        var year = date.getFullYear();    //获取完整的年份(4位,1970-????)
        var mounth = date.getMonth()+1;       //获取当前月份(0-11,0代表1月)
        var day = date.getDate();        //获取当前日(1-31)
        START = year+"-"+mounth+"-"+day;
        END = d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
      }
      $scope.dateQuery();
    }
    //自定义时间检索
    $scope.chooseDate = function(){
      $(".changeDate").removeClass("active");
      START =$("#_searchDate").val();
      END = $("#_endDate").val();
      if((!START && END)||(START && !END)){
        $("#_alertSpan").css("display","");
        $("#_alertSpan").html("请同时选择起始时间和结束时间");
        $.alert("请同时选择起始时间和结束时间");
        return ;
      }
      if(START>END){
        $("#_alertSpan").css("display","");
        $("#_alertSpan").html("起始日期不能大于结束日期");
        $.alert("起始日期不能大于结束日期");
        return ;
      }
      $scope.dateQuery();
    }
    //时间检索入口
    $scope.dateQuery = function(){
      if(START != null){
        START = START+" 00:00:00";
      }
      if(END != null){
        END = END+" 23:59:59";
      }
      options.start = START;
      options.end = END;
      $scope.pageQuery(1,options.pageSize);
    }
    //根据appId精确检索
    $scope.changeApp = function(appId){
      $(".changeApp").removeClass("active");
      $("#changeApp" + appId).addClass("active");
      options.app = appId;
      $scope.pageQuery(1,options.pageSize);
    }
    //数据检索
    $scope.changeCondition = function(){
      options.condition = $scope.reportCondition;
      $scope.pageQuery(1,options.pageSize);
    }
    //显示项目名称编辑框
    $scope.toChangePname = function(projectId){
      $("#showPname"+projectId).addClass("hide");
      $("#changePname"+projectId).removeClass("hide");
    }
    //修改项目名称
    $scope.changePname = function(projectId){
      var name = $("#updatePname"+projectId).val();
      var oldName = $("#pnameSpan"+projectId).text().trim();
      //名称修改才进行后台提交，未修改则不提交后台
      if(name==oldName){
          $("#showPname"+projectId).removeClass("hide");
          $("#changePname"+projectId).addClass("hide");
          return ;
      }
      projectReportService.changeProjectName(projectId,name).success(function(response){
        if(response.success){
          if(name.length>13){
            name=name.substring(0,12)+"...";
          }
          $("#pnameSpan"+projectId).html(name);
          $("#showPname"+projectId).removeClass("hide");
          $("#changePname"+projectId).addClass("hide");
        }
        $scope.message = response.message;
        $scope.state = true;
      });
    }
    //取消共享来的项目
    $scope.cancelProjectShare = function(projectId){
      $.confirm("确定要删除该项目？","确认框",function(){
        projectReportService.cancelProjectShare(projectId).success(function(response){
          if(response.success){
            $scope.pageQuery(1,options.pageSize);
          }
          $.alert(response.message);
        });
      });
    }
    //下载PDF
    $scope.downPDF = function(userId,appId,projectId){
      var path = userId + "/" + appId + "/" + projectId + "/" + projectId + ".pdf";
      projectReportService.downPDF(path).success(function(flag){
        if(flag==1){
          $.alert("没有可以下载的pdf文件");
        }else{
          var url = window.location.href.split("index")[0];
          window.location.href=url+"report/down?path="+path;
        }
      });
    }
    //打开共享窗口
    $scope.toShareModal = function(projectId,projectName,dataNum){
      $scope.shareProjectId = projectId;
      $scope.shareProjectName = projectName;
      $scope.dataNum = dataNum;
      $scope.updateState = false;
      $("#shareProjectSelect").html("");
    }
    //打开已共享窗口
    $scope.shareModal = function(projectId,projectName,dataNum){
      $scope.shareProjectId = projectId;
      $scope.shareProjectName = projectName;
      $scope.dataNum = dataNum;
      projectReportService.getShareTo(projectId).success(function(data){
        $scope.updateState = false;
        $("#shareProjectSelect").html("");
        $("#shareProjectSelect").select2({
          tags: true,
          data: data,
          tokenSeparators: [',', ' ']
        });
      });
    }
    $scope.saveShareProject = function(){
      var proId = $scope.shareProjectId;
      var data = $("#shareProjectSelect").select2("data");
      var userNames = "";
      $.each(data,function(id,value){
        userNames += value.text + ",";
      });
      //全部转化成小写
      userNames = userNames.toLowerCase();
      userNames = userNames.substring(0, userNames.length-1);
      projectReportService.projectShare(proId,userNames).success(function(response){
        if(response.success){
          $("#project-share-modal").modal("hide");
          $scope.pageQuery(options.page,options.pageSize);
          $scope.updateState = false;
        }
        $scope.updateMessage = response.message;
        $scope.updateState = true;
      });
    }
    //删除
    $scope.removePro = function(projectId){
      $.confirm("确定要删除该项目吗？","确认框",function(){
        projectReportService.deleteProject(projectId).success(function(flag){
          if(flag ==1){
            $scope.pageQuery(1,options.pageSize);
            $.alert("项目报告删除成功");
          }else{
            $.alert("项目报告删除失败");
          }
        });
      });
    }
    
    $scope.$on('reportLoadOver', function(){
      $("#_show").find(".projectContext").each(function(){
        var info = $.trim($(this).prev().html()).split(",");
        var appId = info[0];
        var appName = info[1];
        var proId = info[2];
        var userId = info[3];
        var proName = $("#pnameSpan" + proId).html();
        var length = 0;
        var th_size = 0;
        var td_size = 0;
        var tr_size = $(this).find("tr").length;
        $(this).find("table").addClass("table table-main info-table");
        $(this).find("tr").each(function(j){
          if(j==0){
            th_size = $(this).children().length;
          }else{
            td_size = $(this).children().length;
          }
          var trc = $(this).children().length;
          if(appId=="80"){
            if(j==0){
              length = $(this).children().length;
            }
            if(length>6){
              if(j>1&&j%2==0){
                $(this).addClass("_HCVTR");
                $(this).css("display","none");
                $(this).children().attr("colspan","7");
                $(this).children().css("word-break","break-all");
              }
            }
          }
          $(this).mouseover(function(){
            $(this).find("span").addClass("link_hover");
          });
          $(this).mouseout(function(){
            $(this).find("span").removeClass("link_hover");
          });
          $(this).find("td").each(function(i){
            if(appId=="90"){
              if(i!=1){
                if(trc==4){
                  if(i==0){
                    $(this).addClass("hide");
                  }
                }else{
                  $(this).addClass("hide");
                }
              }else{
                $(this).css("border-left","none");
                if(j>0){
                  $(this).addClass("sub");
                  var fileName = $(this).html();
                  if(fileName.length>30){
                    fileName = fileName.substring(0,30) + "...";
                  }
                  $(this).html("<span id='dataSpan"+proId+$(this).prev().html()+"'>"+$(this).prev().html()+" （"+fileName+"）</span>");
                  $(this).find("span").bind("click",function(){
                    viewDataReport(userId,$.trim($(this).parent().prev().html()),$.trim($(this).html()),appId,appName,proId,proName,$(this));
                  });
                  $(this).find("span").addClass("link");
                }
              }
            }
            if(appId=="128"||appId=="127"||appId=="126"||appId=="118"||appId=="117"||appId=="114"||appId=="113"||appId=="112"||appId=="111"||appId=="110"||appId=="109"||appId=="106"||appId=="107"||appId=="108"||appId=="105"||appId=="82"||appId=="84"||appId=="89"||appId=="73"||appId=="1"){
              if(j>0&&i==1){
                $(this).addClass("sub");
                var fileName = $(this).html();
                if(fileName.length>30&&appId!="113"&&appId!="112"&&appId!="111"&&appId!="110"&&appId!="126"&&appId!="127"&&appId!="128"){
                  fileName = fileName.substring(0,30) + "...";
                }
                if(appId!="114"&&appId!="118"){
                  $(this).html("<span id='dataSpan"+proId+$(this).prev().html()+"'>"+$(this).prev().html()+" （"+fileName+"）</span>");
                }else{
                  $(this).html("<span id='dataSpan"+proId+$(this).prev().html()+"'>"+fileName+"</span>");
                }
                $(this).find("span").bind("click",function(){
                  viewDataReport(userId,$.trim($(this).parent().prev().html()),$.trim($(this).html()),appId,appName,proId,proName,$(this));
                });
                $(this).find("span").addClass("link");
              }
              if(i==0){
                $(this).addClass("hide");
              }
              if(i==1){
                $(this).css("border-left","none");
              }
              if(appId=="82"){
                if($(this).html()=="不敏感"){
                  $(this).addClass("red");
                }
              }
            }
            if(appId=="91"||appId=="95"||appId=="92"||appId=="93"||appId=="94"||appId=="86"||appId=="87"||appId=="88"||appId=="81"||appId=="83"||appId=="85"||appId=="96"||appId=="97"||appId=="98"||appId=="99"||appId=="100"||appId=="101"||appId=="102"||appId=="103"||appId=="104"||appId=="116"||appId=="119"||appId=="120"||appId=="121"||appId=="122"||appId=="124"||appId=="125"){
              if(j>0&&i==0){
                $(this).addClass("sub");
                var fileName = $(this).html();
                if(fileName.length>30){
                  fileName = fileName.substring(0,30) + "...";
                }
                $(this).html("<span id='dataSpan"+proId+$(this).next().html()+"'>"+$(this).next().html()+" （"+fileName+"）</span>");
                $(this).find("span").bind("click",function(){
                  viewDataReport(userId,$.trim($(this).parent().next().html()),$.trim($(this).html()),appId,appName,proId,proName,$(this));
                });
                $(this).find("span").addClass("link");
              }
              if(i==1){
                $(this).addClass("hide");
              }
              if(j>0&&(i==4||i==3)){
                var val = $(this).html();
                if(!isNaN(val)&&val>999){
                  $(this).html($.format(val, 3, ','));
                }
              }
            }
            if(appId=="80"){
              if(length==5){
                if(j>0&&i==1){
                  $(this).addClass("sub");
                  var fileName = $(this).html();
                  if(fileName.length>30){
                    fileName = fileName.substring(0,30) + "...";
                  }
                  $(this).html("<span id='dataSpan"+proId+$(this).prev().html()+"'>"+$(this).prev().html()+" （"+fileName+"）</span>");
                  $(this).find("span").bind("click",function(){
                    viewDataReport(userId,$.trim($(this).parent().prev().html()),$.trim($(this).html()),appId,appName,proId,proName,$(this));
                  });
                  $(this).find("span").addClass("link");
                }
                if(i==0){
                  $(this).addClass("hide");
                }
                if(i==1){
                  $(this).css("border-left","none");
                }
                if($(this).html()=="No_Alignment"||$(this).html()=="Low_Quality"
                  ||$(this).html()=="No Alignment"||$(this).html()=="Low Quality"){
                  $(this).attr("colspan","2");
                }
              }
              if(length==7||length==8){
                if($(this).html()=="No_Alignment"||$(this).html()=="Low_Quality"
                  ||$(this).html()=="No Alignment"||$(this).html()=="Low Quality"){
                  $(this).attr("colspan","5");
                }
              }
              if(length==7){
                if(j>0&&j%2==1&&i==0){
                  $(this).addClass("sub");
                  $(this).html("<span id='dataSpan"+proId+$(this).html()+"'>"+$(this).html()+"</span>");
                  $(this).find("span").bind("click",viewNext);
                  $(this).find("span").addClass("link");
                }
              }else if(length==8){
                if(i==1){
                  $(this).css("border-left","none");
                }
                if((j==0||j%2==1)&&i==0){
                  $(this).addClass("hide");
                }
                if(j>0&&j%2==1&&i==1){
                  $(this).addClass("sub");
                  var fileName = $(this).html();
                  if(fileName.length>30){
                    fileName = fileName.substring(0,30) + "...";
                  }
                  $(this).html("<span id='dataSpan"+proId+$(this).prev().html()+"'>"+$(this).prev().html()+" （"+fileName+"）</span>");
                  $(this).find("span").bind("click",viewNext);
                  $(this).find("span").addClass("link");
                }
              }
            }
            if(appId=="11"){
              if(j>0&&i==1){
                $(this).addClass("sub");
                var fileName = $(this).html();
                if(fileName.length>30){
                  fileName = fileName.substring(0,30) + "...";
                }
                $(this).html("<span id='dataSpan"+proId+$(this).prev().html()+"'>"+$(this).prev().html()+" （"+fileName+"）</span>");
                $(this).find("span").bind("click",function(){
                  viewDataReport(userId,$.trim($(this).parent().prev().html()),$.trim($(this).html()),appId,appName,proId,proName,$(this));
                });
                $(this).find("span").addClass("link");
              }
              if(i==0){
                $(this).addClass("hide");
              }
              if(i==1){
                $(this).css("border-left","none");
              }
            }
            
            if(td_size<th_size&&i==td_size-1){
              $(this).attr("colspan",th_size - td_size+1);
            }
            
          });
        });
        var minTdNum = 5;
        if(appId=="128"||appId=="127"||appId=="126"||appId=="113"||appId=="112"||appId=="111"||appId=="110"){
          minTdNum = 4;
        }
        var rdataNum = $("#rdataNum"+proId).html();
        if((appId=="114"||appId=="118") && tr_size-1<rdataNum){
          var num = Number(rdataNum)-tr_size+1;
          var height = 30*(5-tr_size);
          var adHtml = "<tr><td colspan='"+th_size+"' style='border-left-style: none;vertical-align: middle;height:"+height+"px' align='center'><img src='/celloud/images/report/running.png' title='正在运行...'/><br>"+num+"个数据正在运行...</td></tr>";
          $(this).find("tbody").append(adHtml);
        }else if(tr_size<minTdNum){
          var num = 5-tr_size;
          for(i=0;i<num;i++){
            var adHtml = "<tr>";
            for(j=0;j<th_size-1;j++){
              adHtml+="<td></td>";
            }
            adHtml+="</tr>";
            $(this).find("tbody").append(adHtml);
          }
        }
      });
    });
  });
  
  celloudApp.controller("dataReportController", function($scope,projectReportService,dataReportService){
    $scope.searchInfo = dataReportService.getSearchInfos();
    $scope.reportList = dataReportService.getReports();
    var options = {
        page : 1,
        pageSize : 20,
        beginDate : null,
        endDate : null,
        tagId : null,
        period : null,
        batch : null,
        condition : null,
        sort: 'desc'
    };
    var paramQuqery = function(){
      dataReportService.getReportsByParams(options.page,options.pageSize,options.condition,options.beginDate,options.endDate,options.batch,options.tagId,options.period,options.sort)
      .success(function(dataList){
        $scope.reportList = dataList;
      });
    }
    $scope.pageQuery = function(page,pageSize){
      options.page = page;
      options.pageSize = pageSize;
      paramQuqery();
    }
    $scope.tagsQuery = function(tagId){
      options.tagId = tagId;
      options.page = 1;
      paramQuqery();
    }
    $scope.batchsQuery = function(batch){
      options.batch = batch;
      options.page = 1;
      paramQuqery();
    }
    $scope.periodQuery = function(period){
      options.period = period;
      options.page = 1;
      paramQuqery();
    }
    $scope.fullDateQuery = function(days){
      if(days==0){
        options.beginDate = null;
        options.endDate = null;
      }else{
        var d = new Date();
        var date = new Date(d.getTime()-1000*60*60*24*days);
        var year = date.getFullYear(); 
        var mounth = date.getMonth()+1;
        var day = date.getDate(); 
        options.beginDate = year+"-"+mounth+"-"+day+" 00:00:00";
        options.endDate = d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate()+" 23:59:59";
      }
      options.page = 1;
      paramQuqery();
    }
    $scope.chooseDate = function(){
      var d = new Date();
      var begin = $("#begin-date").val();
      var end = $("#end-date").val();
      end = end ? end : d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
      if(!begin){
        $.tips("请选择开始时间！","时间错误");
        return;
      }
      if(begin > end){
        $.tips("请确认开始时间  < 结束时间！","时间错误");
        return;
      }
      options.beginDate = begin + " 00:00:00";
      options.endDate = end + " 23:59:59";
      options.page = 1;
      paramQuqery();
    }
  });
})();
