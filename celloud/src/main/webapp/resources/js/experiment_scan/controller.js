(function(){
  celloudApp.controller("samplingController", function($scope, samplingService){
    $scope.productTags = samplingService.getProductTags();
    $scope.typeList = null;
    
    $scope.changeSampleType = function(){
    	var tagId = 0;
    	if($scope.selTags != undefined){
    		tagId = $scope.selTags.tagId;
    	}
    	var param = {'tagId' : tagId, 'flag' : 3};
    	samplingService.listMetadata(param).
    	success(function(data){
    		$scope.typeList = data;
    	});
    }
    var refreshList = function(){
      $scope.sampleList = samplingService.sampleList();
    }
    refreshList();
    $scope.addSample = function(){
      samplingService.sampling($scope.sampleName,$scope.selTags.tagId,$scope.type.text).success(function(data){
        if(data == 2){
          $.alert("此样品信息已经收集过，请核查或者采集下一管样品信息！");
        }else {
          refreshList();
        }
        $scope.sampleName = "";
      })
    }
    $scope.commitSample = function(){
      samplingService.commitSample($scope.sampleList).success(function(data){
        if(data > 0){
          window.open(window.CONTEXT_PATH+"/sample_order.html#/sampling/order/"+data);
          refreshList();
        }else {
          $.alert("样本已提交");
        }
      })
    }
    $scope.deleteSample = function(id){
      samplingService.deleteSample(id).success(function(data){
        if(data > 0){
          refreshList();
          $.alert("删除样本成功!");
        }else {
          $.alert("删除样本失败!");
        }
      })
    }
  });
  
  celloudApp.controller("samplingInfoController", function($scope, sampleInfoService, $timeout){
	  $scope.sampleTypes = sampleInfoService.typeList();
	  var productTagObj1 = null;
      var productTagObj2 = null;
      var sampleTypeObj1 = null;
      var sampleTypeObj2 = null;
	  $scope.sample = {};
	  $scope.patient = {};
	  
	  $scope.checkIdCard = function(){
		  $scope.vaidIdCard = $scope.idCardUtil.checkIdCardNo($scope.patient.idCard);
	  }
	  
	  // 身份证校验
	  $scope.idCardUtil = {
		  /*省,直辖市代码表*/
		  provinceAndCitys: {11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",
			  31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",
			  45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",
			  65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"},
		
		  /*每位加权因子*/
		  powers: ["7","9","10","5","8","4","2","1","6","3","7","9","10","5","8","4","2"],
		  
		  /*第18位校检码*/
		  parityBit: ["1","0","X","9","8","7","6","5","4","3","2"],
		
		  /*性别*/
		  genders: {male:"男",female:"女"},
		
		  /*校验地址码*/
		  checkAddressCode: function(addressCode){
		     var check = /^[1-9]\d{5}$/.test(addressCode);
		     if(!check) {
		    	 return false;
		     }
		     if($scope.idCardUtil.provinceAndCitys[parseInt(addressCode.substring(0,2))]){
		    	 return true;
		     }else{
		    	 return false;
		     }
		  },
	
		  /*校验日期码*/
		  checkBirthDayCode: function(birDayCode){
		      var check = /^[1-9]\d{3}((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))$/.test(birDayCode);
		      if(!check) return false;    
		      var yyyy = parseInt(birDayCode.substring(0,4),10);
		      var mm = parseInt(birDayCode.substring(4,6),10);
		      var dd = parseInt(birDayCode.substring(6),10);
		      var xdata = new Date(yyyy,mm-1,dd);
		      if(xdata > new Date()){
		    	  return false;//生日不能大于当前日期
		      }else if ( ( xdata.getFullYear() == yyyy ) && ( xdata.getMonth () == mm - 1 ) && ( xdata.getDate() == dd ) ){
		    	  return true;
		      }else{
		    	  return false;
		      }
		  },
	  
		  /*计算校检码*/
		  getParityBit: function(idCardNo){
			  var id17 = idCardNo.substring(0,17);    
		      /*加权 */
			  var power = 0;
			  for(var i=0;i<17;i++){
				  power += parseInt(id17.charAt(i),10) * parseInt($scope.idCardUtil.powers[i]);
			  }              
			  /*取模*/ 
		      var mod = power % 11;
		      return $scope.idCardUtil.parityBit[mod];
		  },
	  
		  /*验证校检码*/
		  checkParityBit: function(idCardNo){
		     var parityBit = idCardNo.charAt(17).toUpperCase();
		     if($scope.idCardUtil.getParityBit(idCardNo) == parityBit){
		         return true;
		     }else{
		         return false;
		     }
		  },
	
		  /*校验15位或18位的身份证号码*/
		  checkIdCardNo: function(idCardNo){
			  //15位和18位身份证号码的基本校验
			  var check = /^\d{15}|(\d{17}(\d|x|X))$/.test(idCardNo);
			  if(!check){
				  return false;
			  }
			  var flag = false;
			  //判断长度为15位或18位  
			  if(idCardNo.length==15){
				  flag = $scope.idCardUtil.check15IdCardNo(idCardNo);
			  }else if(idCardNo.length==18){
				  flag = $scope.idCardUtil.check18IdCardNo(idCardNo);
			  }
			  if(flag){
				  // 获取年龄, 性别
				  var idCardInfo = $scope.idCardUtil.getIdCardInfo(idCardNo);
				  $scope.patient.gender = idCardInfo.gender == "男" ? "1" : "0";
				  var birthday = idCardInfo.birthday;
				  var birthY = birthday.split("-")[0];
				  var now = new Date();
				  y = now.getFullYear();
  				  $scope.patient.age = parseInt(y) - parseInt(birthY);
			  }
			  return flag;
		  },
	
		  //校验15位的身份证号码
		  check15IdCardNo: function(idCardNo){
			  //15位身份证号码的基本校验
			  var check = /^[1-9]\d{7}((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))\d{3}$/.test(idCardNo);   
			  if(!check) {
				  return false;
			  }
			  //校验地址码
			  var addressCode = idCardNo.substring(0,6);
			  check = $scope.idCardUtil.checkAddressCode(addressCode);
			  if(!check) {
				  return false;
			  }
		  	  var birDayCode = '19' + idCardNo.substring(6,12);
		  	  //校验日期码
		  	  return $scope.idCardUtil.checkBirthDayCode(birDayCode);
	  	  },
	
		  //校验18位的身份证号码
	  	  check18IdCardNo: function(idCardNo){
		      //18位身份证号码的基本格式校验
	  		  var check = /^[1-9]\d{5}[1-9]\d{3}((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))\d{3}(\d|x|X)$/.test(idCardNo);
	  		  if(!check) {
	  			  return false;
	  		  }
	  		  //校验地址码
	  		  var addressCode = idCardNo.substring(0,6);
	  		  check = $scope.idCardUtil.checkAddressCode(addressCode);
	  		  if(!check) {
	  			  return false;
	  		  }
	  		  //校验日期码
	  		  var birDayCode = idCardNo.substring(6,14);
	  		  check = $scope.idCardUtil.checkBirthDayCode(birDayCode);
	  		  if(!check){
	  			  return false;
	  		  }
	  		  //验证校检码   
		      return $scope.idCardUtil.checkParityBit(idCardNo);   
		  },
	
		  formateDateCN: function(day){
			  var yyyy =day.substring(0,4);
			  var mm = day.substring(4,6);
			  var dd = day.substring(6);
			  return yyyy + '-' + mm +'-' + dd;
		  },
	
		  //获取信息
		  getIdCardInfo: function(idCardNo){
			  var idCardInfo = {
					  gender:"",   //性别
					  birthday:"" // 出生日期(yyyy-mm-dd)
			  };
			  if(idCardNo.length==15){
				  var aday = '19' + idCardNo.substring(6,12);
				  idCardInfo.birthday = $scope.idCardUtil.formateDateCN(aday);
				  if(parseInt(idCardNo.charAt(14))%2==0){
					  idCardInfo.gender = $scope.idCardUtil.genders.female;
				  }else{
					  idCardInfo.gender = $scope.idCardUtil.genders.male;
				  }
			  }else if(idCardNo.length == 18){
				  var aday = idCardNo.substring(6,14);
				  idCardInfo.birthday = $scope.idCardUtil.formateDateCN(aday);
				  if(parseInt(idCardNo.charAt(16))%2==0){
					  idCardInfo.gender = $scope.idCardUtil.genders.female;
				  }else{
					  idCardInfo.gender = $scope.idCardUtil.genders.male;
				  } 
			  }
			  return idCardInfo;
		  },
	  
		  /*18位转15位*/
		  getId15: function(idCardNo){
			  if(idCardNo.length==15){
				  return idCardNo;
			  }else if(idCardNo.length==18){
				  return idCardNo.substring(0,6) + idCardNo.substring(8,17); 
			  }else{
				  return null;
			  }
		  },
	  
		  /*15位转18位*/
		  getId18: function(idCardNo){
			  if(idCardNo.length==15){
				  var id17 = idCardNo.substring(0,6) + '19' + idCardNo.substring(6);
				  var parityBit = $scope.idCardUtil.getParityBit(id17);
		          return id17 + parityBit;
   			  }else if(idCardNo.length==18){
		          return idCardNo;
		      }else{
			      return null;
		      }
		  }
	};
	  
	  $scope.addReset = function(){
		  $timeout(function(){
			  productTagObj1.val(null).trigger("change");
	    	  sampleTypeObj1.val(null).trigger("change");
	    	  $scope.sample.sampleName = "";
	    	  $scope.patient.name = "";
	    	  $scope.patient.tel = "";
	    	  $scope.patient.age = "";
	    	  $scope.patient.gender = 1;
	    	  $scope.patient.idCard = "";
	    	  $scope.patient.smoke = 1;
	    	  $scope.addSampleInfoForm.$setPristine();
		  });
	  }
	  $scope.changeSampleTypeByTag = function(flag){
		  $scope.sampleType = null;
		  var oldWidth = $("#sampleTypes"+flag+" .select2-search__field").attr("style");
		  $("#sampleTypes"+flag+" li.select2-selection__choice").remove();
	      $("#sampleTypes"+flag+" span.select2-selection__clear").remove();
		  $("#sampleTypes"+flag+" .select2-search__field").attr("placeholder","请选择样本类型");
		  $("#sampleTypes"+flag+" .select2-search__field").attr("style","oldWidth");
		  $("#sampleType"+flag+" option").remove();
	      var tagId = null;
	      var optionVal = $("#productTag"+flag).val();
		  if(optionVal != null){
			  if(!isNaN(optionVal)){
		          tagId = parseInt(optionVal);
		      }
			  $.ajax({
			      url : "metadata/listMetadataToSelectByTagIdAndFlag",
			      type : 'POST',
			      data:{
			          'tagId' : tagId,
			          'flag' : 3
			      },
			      success : function(data) {
			          // 从后台获取json数据
			          var json = eval(data);
			          $("#sampleType"+flag).select2({
			              data : json,
			              placeholder : '请选择样本类型',
			              language : 'zh-CN',
			              allowClear : true,
			              maximumSelectionLength: 1
			          });
			          $(".select2-search__field").css("height","20px");
			      }
			  });
	      }
		  $(".select2-search__field").css("height","20px");
	  }
      $scope.listSampleInfo = function(){
    	  sampleInfoService.listSampleInfo()
    	  .success(function(data){
    		  $scope.sampleInfoList = data;
    	  });
      }
      $scope.toAddSample = function(){
    	  $timeout(function(){
    		  $scope.sample = {};
    		  $scope.patient = {};
    		  $scope.patient.gender = 1;
    		  $scope.patient.smoke = 1;
	    	  productTagObj1.val(null).trigger("change");
			  sampleTypeObj1.val(null).trigger("change");
	    	  $("#addSampleInfoModal").modal("show");
	    	  $scope.addSampleInfoForm.$setPristine();
    	  });
      }
	  $scope.saveSample = function(){
		  $scope.patient.gender = $("input[name='gender']:checked").val();
		  $scope.patient.smoke = $("input[name='smoke']:checked").val();
		  sampleInfoService.sampleInfos($scope.patient, $scope.sample, $scope.productTag, $scope.sampleType)
		  	.success(function(data, status){
		  		if (status == 201){
		  			$("#addSampleInfoModal").modal("hide");
		  			$.alert("新增样本成功");
		  			$scope.listSampleInfo();
		  		}
		  	    $scope.sampleName = "";
		  	})
		  	.error(function(data, status){
		  		if(status == 400){
		  			$scope.repeat = true;
		  		}else if(status == 500){
		  			$.alert("服务器发生内部错误");
		  		}
		  	});
	  }
	  $scope.bakPatient = {};
	  $scope.bakSample = {};
	  $scope.editReset = function(){
		  $timeout(function(){
			  $scope.patient = angular.copy($scope.bakPatient);
			  $scope.sample = angular.copy($scope.bakSample);
			  $scope.sample.sampleName = $scope.bakSample.sampleName
	    	  $scope.patient.name = $scope.bakPatient.name
	    	  $scope.patient.tel = $scope.bakPatient.tel
	    	  $scope.patient.age = $scope.bakPatient.age
	    	  $scope.patient.gender = $scope.bakPatient.gender
	    	  $scope.patient.idCard = $scope.bakPatient.idCard
	    	  $scope.patient.smoke = $scope.bakPatient.smoke
	    	  $scope.updateSampleInfoForm.$setPristine();
			  
			  productTagObj2.val([$scope.bakSample['tagId'],$scope.bakSample['tagName']]).trigger("change");
			  $.ajax({
			      url : "metadata/listMetadataToSelectByTagIdAndFlag",
			      type : 'POST',
			      data:{
			          'tagId' : $scope.bakSample['tagId'],
			          'flag' : 3
			      },
			      success : function(data) {
			          // 从后台获取json数据
			          var json = eval(data);
			          $("#sampleType2").select2({
			              data : json,
			              placeholder : '请选择样本类型',
			              language : 'zh-CN',
			              allowClear : true,
			              maximumSelectionLength: 1
			          });
	    			  sampleTypeObj2.val([$scope.bakSample['type'],$scope.bakSample['type']]).trigger("change");
			          $(".select2-search__field").css("height","20px");
			      }
			  });
			  $scope.updateSampleInfoForm.$setPristine();
		  });
	  }
      $scope.toEditSampleInfo = function(id){
    	  sampleInfoService.getSampleInfo(id)
    	  .success(function(sampleData){
    		  $scope.sample.sampleId = sampleData['sampleId'];
    		  $scope.sample.sampleName = sampleData['sampleName'];
    		  $scope.sample.type = sampleData['type'];
    		  $scope.sample.tagId = sampleData['tagId'];
    		  $scope.sample.oldTagId = sampleData['tagId'];
    		  $scope.sample.tagName = sampleData['tagName'];
    		  $scope.patient.id = sampleData['id'];
    		  $scope.patient.name = sampleData['name'];
    		  $scope.patient.gender = sampleData['gender'];
    		  $scope.patient.age = sampleData['age'];
    		  $scope.patient.tel = sampleData['tel'];
    		  $scope.patient.idCard = sampleData['idCard'];
    		  $scope.patient.weight = sampleData['weight'];
    		  $scope.patient.height = sampleData['height'];
    		  $scope.patient.email = sampleData['email'];
    		  $scope.patient.smoke = sampleData['smoke'];
    		  $scope.patient.personalHistory = sampleData['personalHistory'];
    		  $scope.patient.familyHistory = sampleData['familyHistory'];
    		  $scope.bakPatient = angular.copy($scope.patient);
    		  $scope.bakSample = angular.copy($scope.sample);
    		  $("#editSampleInfoModal").modal("show");
    		  $timeout(function(){
    			  productTagObj2.val([sampleData['tagId'],sampleData['tagName']]).trigger("change");
    			  $.ajax({
    			      url : "metadata/listMetadataToSelectByTagIdAndFlag",
    			      type : 'POST',
    			      data:{
    			          'tagId' : sampleData['tagId'],
    			          'flag' : 3
    			      },
    			      success : function(data) {
    			          // 从后台获取json数据
    			          var json = eval(data);
    			          $("#sampleType2").select2({
    			              data : json,
    			              placeholder : '请选择样本类型',
    			              language : 'zh-CN',
    			              allowClear : true,
    			              maximumSelectionLength: 1
    			          });
    	    			  sampleTypeObj2.val([sampleData['type'],sampleData['type']]).trigger("change");
    			          $(".select2-search__field").css("height","20px");
    			      }
    			  });
    		  });
    	  });
      }
      $scope.updateSample = function(){
    	  $scope.patient.gender = $("input[name='gender']:checked").val();
		  $scope.patient.smoke = $("input[name='smoke']:checked").val();
		  sampleInfoService.updateSampleInfos($scope.patient, $scope.sample, $scope.productTag, $scope.sampleType)
		  .success(function(data, status){
			  if (status == 201){
				  $("#editSampleInfoModal").modal("hide");
		  		  $.alert("更新样本成功");
		  		  $scope.listSampleInfo();
		  	  }
		  	  $scope.sampleName = "";
		  })
		  .error(function(data, status){
		  	  if(status == 400){
		  		  $scope.repeat = true;
		  	  }else if(status == 500){
		  		  $.alert("服务器发生内部错误");
		  	  }
		  });
      }
      
      $("#addSampleInfoModal").on("hidden.bs.modal",function(e){
    	  $scope.sample = {};
    	  $scope.patient = {};
    	  $scope.patient.gender = 1;
    	  $scope.patient.smoke = 1;
    	  $scope.productTag = "";
    	  $scope.sampleType = "";
    	  productTagObj1.val(null).trigger("change");
    	  sampleTypeObj1.val(null).trigger("change");
	  });
      $("#editSampleInfoModal").on("hidden.bs.modal",function(e){
    	  $scope.sample = {};
    	  $scope.patient = {};
    	  $scope.patient.gender = 1;
    	  $scope.patient.smoke = 1;
    	  $scope.productTag = "";
    	  $scope.sampleType = "";
    	  productTagObj2.val(null).trigger("change");
    	  sampleTypeObj2.val(null).trigger("change");
	  });
      $.ajax({
          url : CONTEXT_PATH + "/uploadFile/listProductTagToSelect",
          type : 'post',
          dataType : 'text',
          success : function(data) {
              // 从后台获取json数据
              var json = eval('(' + data + ')');
              productTagObj1 = $("#productTag1").select2({
                  data : json,
                  placeholder : '检测产品编号',
                  language : 'zh-CN',
                  allowClear : true,
                  maximumSelectionLength: 1
              })
              productTagObj2 = $("#productTag2").select2({
                  data : json,
                  placeholder : '检测产品编号',
                  language : 'zh-CN',
                  allowClear : true,
                  maximumSelectionLength: 1
              })
              $(".select2-search__field").css("height","20px");
          },
          error : function(data) {

          }
      });
      sampleTypeObj1 = $("#sampleType1").select2({
          placeholder : '请选择样本类型',
          language : 'zh-CN',
          allowClear : true,
          maximumSelectionLength: 1
      });
      sampleTypeObj2 = $("#sampleType2").select2({
          placeholder : '请选择样本类型',
          language : 'zh-CN',
          allowClear : true,
          maximumSelectionLength: 1
      });
      $scope.commitSampleInfo = function(){
    	  sampleInfoService.commitSample()
    	  .success(function(data, status){
    		  if(status == 200){
    			  var url = window.CONTEXT_PATH+"/sampleInfoOrder#/samplinginfo/order/"+data;
    			  window.open(url);
    			  $scope.listSampleInfo();
    		  }
    	  })
    	  .error(function(data, status){
    		  if(status == 500){
    			  $.alert("服务器发生错误")
    		  }
    	  });
      }
    $scope.removeSampleInfo = function(sampleId){
      sampleInfoService.removeSampleInfo(sampleId)
      .success(function(data, status){
        if(status == 204){
        	$scope.listSampleInfo();
            $.alert("删除样本成功!");
        }
      })
      .error(function(data, status){
    	  if(status == 500){
    		  $.alert("删除样本失败!");
		  }
      })
    }
    $(".select2-search__field").css("height","20px");
    $scope.listSampleInfo();
  });
  
  celloudApp.controller("sampleTrackingController", function($scope, $routeParams, sampleTrackingService){
    $scope.pages = {
        page : 1,
        pageSize : 20
    };
    $scope.pageQuery = function(page,pageSize){
      $scope.pages = {
        page : page,
        pageSize : pageSize
      };
      sampleTrackingService.pageList($scope.pages.page,$scope.pages.pageSize,$scope.sampleName).success(function(data){
        $scope.sampleList = data;
      });
    }
    $scope.sampleNameQuery = function(){
      $scope.pageQuery(1,$scope.pages.pageSize);
    }
    $scope.doOnKeyPress= function($event){
      if($event.keyCode == 13){
          $scope.sampleNameQuery();
      }
    }
    $scope.sampleList = $scope.pageQuery($scope.pages.page,$scope.pages.pageSize);
  });
  
  celloudApp.controller("sampleInfoTrackingController", function($scope, $routeParams, sampleTrackingService){
	    $scope.pages = {
	        page : 1,
	        pageSize : 20
	    };
	    $scope.pageQuery = function(page,pageSize){
	      $scope.pages = {
	        page : page,
	        pageSize : pageSize
	      };
	      sampleTrackingService.pageList($scope.pages.page,$scope.pages.pageSize,$scope.sampleName).success(function(data){
	        $scope.sampleList = data;
	      });
	    }
	    $scope.sampleNameQuery = function(){
	      $scope.pageQuery(1,$scope.pages.pageSize);
	    }
	    $scope.doOnKeyPress= function($event){
	      if($event.keyCode == 13){
	          $scope.sampleNameQuery();
	      }
	    }
	    $scope.sampleList = $scope.pageQuery($scope.pages.page,$scope.pages.pageSize);
	  });
  
  celloudApp.controller("sampleOrderController", function($scope, commonService, $routeParams, sampleOrderService){
    sampleOrderService.sampleOrderInfo($routeParams.orderId).success(function(data){
      if(data != null){
    	$scope.userProduct = commonService.getProduct().get();
        $scope.sampleOrderInfo = data;
      }else {
        $.alert("样本已提交");
      }
    });
    $scope.print = function(){
      window.print();
    };
  });
  
  celloudApp.controller("sampleInfoOrderController", function($scope, $routeParams, sampleInfoOrderService){
	  sampleInfoOrderService.sampleInfoOrderInfo($routeParams.orderId)
	    .success(function(data){
	      if(data != null){
	        $scope.sampleOrderInfo = data;
	      }else {
	        $.alert("样本已提交");
	      }
	    });
	    $scope.print = function(){
	      window.print();
	    };
	    $scope.send = function(){
	    	sampleInfoOrderService.sendSampleInfoOrderInfo($routeParams.orderId)
	    	.success(function(data, status){
	    		if(status == 200){
	    			alert("发送成功!");
	    		}
	    	})
	    	.error(function(data, status){
	    		if(sttus == 500){
	    			$.alert("服务器内部错误");
	    		}
	    	});
	    }
	  });
  
  celloudApp.controller("scanStorageController", function($scope, scanStorageService){
    $scope.pages = {
      page : 1,
      pageSize : 20
    };
    
    $scope.checkedLength = 0;
    
    $scope.pageQuery = function(page,pageSize){
      $scope.pages = {
        page : page,
        pageSize : pageSize
      };
      scanStorageService.pageList($scope.pages.page,$scope.pages.pageSize).success(function(data){
        $scope.sampleList = data;
      });
    }
    
    $scope.doOnKeyPress= function($event){
    	if($event.keyCode == 13){
    		if($scope.sampleName==''||$scope.sampleName==undefined){
    		}else{
    			$scope.scanStorage();
    		}
    	}
    }
    $scope.checkAll = function(){
    	var flag = $("input[name='checkAll']").prop("checked");
    	if(flag){
    		$scope.checkedLength = $("input[name='checkOne']").length;
    	}else{
    		$scope.checkedLength = 0;
    	}
    	$("input[name='checkOne']").each(function(){
    		$(this).prop("checked", flag);
    	})
    }
    $scope.checkOne = function(){
    	// 获取选中的输入框的数量
    	var length1 = $("input[name='checkOne']:checked").length;
    	$scope.checkedLength = length1;
    	// 获取全部的输入框的数量
    	var length2 = $("input[name='checkOne']").length;
    	if(length1 == length2){
    		$("input[name='checkAll']").prop("checked",true);
    	}else{
    		$("input[name='checkAll']").prop("checked",false);
    	}
    }
    $scope.printQRCode = function(){
    	// 获取选中的checkbox, 封装数组进行打印
    	var sampleNames = new Array();
    	var dates = new Array();
    	$("input[name='checkOne']:checked").each(function(){
    		sampleNames.unshift($(this).parent().parent().parent().children(".experSampleName").html().trim());
    		dates.unshift($(this).parent().parent().parent().children(".createDate").html().trim());
    	});
    	printQRCodeBatch(sampleNames, dates);
    }
    $scope.scanStorage = function(){
      if($scope.orderNo == "" || $scope.orderNo == undefined){
        $.alert("请输入订单编号！");
        return false;
      }
    	if($scope.sampleName == "" || $scope.sampleName == undefined){
    		$.alert("请输入样本信息！");
    		return false;
    	}
      scanStorageService.scanStorage($scope.orderNo,$scope.sampleName).success(function(data){
        if(data.error != undefined){
          $.alert(data.error);
        }else{
          //打印二维码
//          printQRCode(data.experName,data.date);
          $scope.sampleList = $scope.pageQuery($scope.page,$scope.pageSize);
        }
        $scope.sampleName = "";
      });
    }
    
    $scope.remove = function(id){
      scanStorageService.remove(id).success(function(data){
        if(data > 0){
          $scope.pageQuery($scope.pages.page,$scope.pages.pageSize);
          $.alert("样本删除成功");
        }else{
          $.alert("样本删除失败");
        }
      });
    }
    
    $scope.sampleList = $scope.pageQuery($scope.page,$scope.pageSize);
  });
  
  celloudApp.controller("tokenDNAController",function($scope,scanStorageService, tokenDNAService){
      $scope.pages = {
        page : 1,
        pageSize : 20
      };
      $scope.checkedLength = 0;
      $scope.pageQuery = function(page,pageSize){
        $scope.pages = {
          page : page,
          pageSize : pageSize
        };
        tokenDNAService.pageList($scope.pages.page,$scope.pages.pageSize).success(function(data){
          $scope.sampleList = data
        });
      }
      
      $scope.doOnKeyPress= function($event){
      	if($event.keyCode == 13){
      		if($scope.sampleName==''||$scope.sampleName==undefined){
      		}else{
      			$scope.tokenDNA();
      		}
      	}
      }
      $scope.tokenDNA = function(){
      	if($scope.sampleName == "" || $scope.sampleName == undefined){
      		$.alert("请输入实验样本编号！");
      		return false;
      	}
        tokenDNAService.tokenDNA($scope.sampleName).success(function(data){
          if(data.error != undefined){
            $.alert(data.error);
          }else{
            //打印二维码
//            printQRCode($scope.sampleName,data.date);
            $scope.sampleList = $scope.pageQuery($scope.pages.page,$scope.pages.pageSize);
          }
          $scope.sampleName = "";
        });
      }
      $scope.checkAll = function(){
      	var flag = $("input[name='checkAll']").prop("checked");
      	if(flag){
    		$scope.checkedLength = $("input[name='checkOne']").length;
    	}else{
    		$scope.checkedLength = 0;
    	}
      	$("input[name='checkOne']").each(function(){
      		$(this).prop("checked", flag);
      	})
      }
      $scope.checkOne = function(){
      	// 获取选中的输入框的数量
      	var length1 = $("input[name='checkOne']:checked").length;
      	$scope.checkedLength = length1;
      	// 获取全部的输入框的数量
      	var length2 = $("input[name='checkOne']").length;
      	if(length1 == length2){
      		$("input[name='checkAll']").prop("checked",true);
      	}else{
      		$("input[name='checkAll']").prop("checked",false);
      	}
      }
      $scope.printQRCode = function(){
      	// 获取选中的checkbox, 封装数组进行打印
      	var sampleNames = new Array();
      	var dates = new Array();
      	$("input[name='checkOne']:checked").each(function(){
      		sampleNames.unshift($(this).parent().parent().parent().children(".experSampleName").html().trim());
      		dates.unshift($(this).parent().parent().parent().children(".createDate").html().trim());
      	});
      	printQRCodeBatch(sampleNames, dates);
      }
      $scope.remove = function(id){
        scanStorageService.remove(id).success(function(data){
          if(data > 0){
            $scope.sampleList = $scope.pageQuery($scope.pages.page,$scope.pages.pageSize);
            $.alert("样本删除成功");
          }else{
            $.alert("样本删除失败");
          }
        });
      }
      $scope.sampleList = $scope.pageQuery($scope.pages.page,$scope.pages.pageSize);
  });
  
  celloudApp.controller("buidLibraryController",function($scope,scanStorageService, buidLibraryService){
    $scope.infos = buidLibraryService.infos();
    
    $scope.doOnKeyPress= function($event){
    	if($event.keyCode == 13){
    		if($scope.sampleName==''||$scope.sampleName==undefined){
    			$.alert("请输入样本信息");
    		}else{
    			$scope.addSample();
    		}
    	}
    }
    $scope.addSample = function(){
      var sampleList = $scope.infos.pageList.datas;
      var samplelength = $scope.infos.sampleIndex.length;
      if(sampleList.length>=samplelength){
        $.tips("每个文库最多"+samplelength+"个样本！")
      }else if($scope.sampleName == '' || $scope.sampleName == undefined){
    	  $.alert("请输入实验样本编号");
      }else{
        buidLibraryService.addSample($scope.sampleName,sampleList).success(function(data){
          if(data > 0){
            $scope.infos = buidLibraryService.infos();
          }else if(data == 0){
            $.alert("此样本未提取DNA");
          }else {
            $.alert("此样品已在等待建库，请核查或者扫描下一管样品信息！");
          }
        });
      }
      $scope.sampleName = "";
    }
    $scope.remove = function(id){
      scanStorageService.remove(id).success(function(data){
        if(data > 0){
          $scope.infos = buidLibraryService.infos();
          $.alert("样本删除成功");
        }else{
          $.alert("样本删除失败");
        }
      });
    }
    $scope.addLibrary = function(){
      var select = $scope.sindex.name+":"+$scope.sindex.seq;
      buidLibraryService.addLibrary($scope.infos.libraryName,select,$scope.infos.pageList.datas).success(function(data){
        if(data != null && data != undefined){
          //打印二维码
          printQRCode(data.storageName,data.sindex);
          $scope.infos = buidLibraryService.infos();
          $.alert("建库成功！");
        }else {
          $scope.repeatError = true;
          $.alert("建库失败！");
        }
      });
    }
    $scope.addAndDownLibrary = function(){
      var select = $scope.sindex.name+":"+$scope.sindex.seq;
      buidLibraryService.addLibrary($scope.infos.libraryName,select,$scope.infos.pageList.datas).success(function(data){
        if(data == null){
          $scope.notPrevError = true;
        }else if(data != null){
          var storageName = $scope.infos.libraryName;
          $scope.infos = buidLibraryService.infos();
          buidLibraryService.downloadExcel(data.id,data.storageName).success(function(flag){
            if(flag==1){
              $.alert("没有正确生成Excel文件");
            }else{
              var url = window.location.href.split("index")[0];
              window.location.href=url+"sample/downExperExcel?ssId="+data.id+"&storageName="+data.storageName;
              $.alert("建库并下载成功！");
            }
          });
        }else {
          $scope.repeatError = true;
        }
      });
    }
  });
  
  celloudApp.controller("storagesController",function($scope, storagesService,buidLibraryService){
    $scope.storages = storagesService.storages();
    storagesService.sampleList().success(function(data){
      $scope.sampleList = data;
    });
    $scope.pages = {
      page : 1,
      pageSize : 20
    };
    $scope.pageQuery = function(page,pageSize){
      $scope.pages = {
        page : page,
        pageSize : pageSize
      };
      storagesService.pageList($scope.pages.page,$scope.pages.pageSize).success(function(data){
        $scope.storages = data;
      });
    }
    $scope.download = function(id,storageName){
      buidLibraryService.downloadExcel(id,storageName).success(function(flag){
        if(flag==1){
          $.alert("没有正确生成Excel文件");
        }else{
          var url = window.location.href.split("index")[0];
          window.location.href=url+"sample/downExperExcel?ssId="+id+"&storageName="+storageName;
          $.alert("下载Excel文件成功！");
        }
      });
    }
    
    $scope.updateInMachine = function(id){
      storagesService.changeInMachine(id).success(function(flag){
        if(flag>0){
          $.alert("修改上机状态成功");
          $scope.storages = $scope.pageQuery($scope.pages.page,$scope.pages.pageSize);
        }else{
          $.alert("修改上机状态失败");
        }
      })
    }
  });
  
  celloudApp.controller("editSampleController",function($scope, scanStorageService, tokenDNAService){
    $scope.toEditRemark = function(id,remark){
      $scope.sampleId = id;
      $scope.remark = remark;
      $scope.remark_bak = remark;
    }
    $scope.resetSampleRemark = function() {
      $scope.remark = angular.copy($scope.remark_bak);
    };
    
    $scope.editRemark = function(){
      scanStorageService.editRemark($scope.sampleId,$scope.remark).success(function(data){
        if(data > 0){
          if($scope.exper_state == 1){
        	  scanStorageService.pageList($scope.pages.page,$scope.pages.pageSize).success(function(data){
        	    $scope.sampleList = data;
            });
          }else{
        	  tokenDNAService.pageList($scope.pages.page,$scope.pages.pageSize).success(function(data){
        	    $scope.sampleList = data
            });
          }
          $.alert("样本修改成功");
        }else{
          $.alert("样本修改失败");
        }
        $("#sample-remark-modal").modal("hide");
      });
    }
  });
})()