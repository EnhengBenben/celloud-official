(function(){
	celloudApp.controller("companyUserController", function($scope, companyService){
		
		$scope.pageInfo = {"currentPage" : 1, "pageSize" : 20};
		
		$scope.pageQuery = function(currentPage, pageSize){
			$scope.pageInfo.currentPage = currentPage || $scope.pageInfo.currentPage;
			$scope.pageInfo.pageSize = pageSize || $scope.pageInfo.pageSize;
			companyService.pageQueryUser($scope.pageInfo.currentPage, $scope.pageInfo.pageSize).
			success(function(userList){
				$scope.userList = userList;
			});
		}
		$scope.updateUserState = function(userId, state){
			companyService.updateUserState(userId, state).
			success(function(data, status){
				if(status == 204){
					if(state == 0){
						$.alert("禁用成功");
					}else{
						$.alert("启用成功");
					}
					$scope.pageQuery($scope.pageInfo.currnetPage, $scope.pageInfo.pageSize);
				}
				if(status == 500){
					if(state == 0){
						$.alert("禁用失败");
					}else{
						$.alert("启用失败");
					}
				}
			})
		}
		$scope.sendEmail = function(){
		  var apps = new Array();
		  $("#userAddForm").find("input[name='app']:checked").each(function(){
		    apps.push($(this).val());
		  });
		  var roles = new Array();
		  $("#userAddForm").find("input[name='role']:checked").each(function(){
		    roles.push($(this).val());
		  });
			$("#submit").prop("disabled",true);
			companyService.sendRegisterEmail($scope.email,$scope.kaptcha,apps,roles).success(function(data, status){
				 if(status == 204){
					$("#company-addUser-modal").modal("hide");
					$.alert("发送成功!");
				}
			}).error(function(data, status){
				if(status == 400){
					$scope.emailError = data.emailError;
					$scope.kaptchaError = data.kaptchaError;
//					$('#kaptchaImage').click();
					$("img[name=kaptchaImage]").click();
				}else if(status == 500){
					
				}
			});
		}
		$scope.sendCellphoneCaptcha = function(){
      var apps = new Array();
      $("#userCellphoneAddForm").find("input[name='cellphone-app']:checked").each(function(){
        apps.push($(this).val());
      });
      var roles = new Array();
      $("#userCellphoneAddForm").find("input[name='cellphone-role']:checked").each(function(){
        roles.push($(this).val());
      });
      $("#submit").prop("disabled",true);
      companyService.sendCellphoneCaptcha($scope.email,$scope.captcha,apps,roles,$scope.truename).success(function(data, status){
         if(status == 204){
           $("#company-cellphone-addUser-modal").modal("hide");
           $.alert("发送成功!");
         }
      }).error(function(data, status){
        if(status == 400){
          $scope.cellphoneError = data.cellphoneError;
          $scope.kaptchaError = data.kaptchaError;
          $("img[name=kaptchaImage]").click();
        }else if(status == 500){
          
        }
      });
    }
		
		$scope.toAddApp = function(userId){
		  $scope.addApp.isShow = false;
		  $scope.userId = userId;
		  companyService.toAddApp(userId)
      .success(function(data){
        $scope.appList = data;
      });
		}
		$scope.addApp = function(){
		  var apps = new Array();
		  $("#appAddForm").find("input[name='app']:checked").each(function(){
		    apps.push($(this).val());
		  });
		  if(apps.length==0){
		    $scope.addApp.isShow = true;
		    return;
		  }
		  $scope.addApp.isShow = false;
		  $("#submit").prop("disabled",true);
		  companyService.addApp($scope.userId,apps).
		  success(function(data, status){
		    if(status == 200){
		      $("#company-addApp-modal").modal("hide");
		      $.alert("追加成功!");
		    }
		  }).
		  error(function(data, status){
		    $("#company-addApp-modal").modal("hide");
		    $.alert("追加失败!");
		  });
		}
		
		$scope.toRemoveApp = function(userId){
		  $scope.removeApp.isShow = false;
		  $scope.userId = userId;
		  companyService.toRemoveApp(userId)
		  .success(function(data){
		    $scope.appList = data;
		  });
		}
		$scope.removeApp = function(){
		  var apps = new Array();
		  $("#removeAppForm").find("input[name='app']:checked").each(function(){
		    apps.push($(this).val());
		  });
		  if(apps.length==0){
        $scope.removeApp.isShow = true;
        return;
      }
      $scope.removeApp.isShow = false;
		  $("#submit").prop("disabled",true);
		  companyService.removeApp($scope.userId,apps).
		  success(function(data, status){
		    if(status == 200){
		      $("#company-removeApp-modal").modal("hide");
		      $.alert("删除成功!");
		    }
		  }).
		  error(function(data, status){
		    $("#company-removeApp-modal").modal("hide");
        $.alert("删除失败!");
		  });
		}
		
		$scope.toAddRole = function(userId){
		  $scope.addRole.isShow = false;
		  $scope.userId = userId;
		  companyService.toAddRole(userId)
		  .success(function(data){
		    $scope.roleList = data;
		  });
		}
		$scope.addRole = function(){
		  var roles = new Array();
		  $("#roleAddForm").find("input[name='role']:checked").each(function(){
		    roles.push($(this).val());
		  });
		  if(roles.length==0){
        $scope.addRole.isShow = true;
        return;
      }
      $scope.addRole.isShow = false;
		  $("#submit").prop("disabled",true);
		  companyService.addRole($scope.userId,roles).
		  success(function(data, status){
		    if(status == 200){
		      $("#company-addRole-modal").modal("hide");
		      $.alert("追加成功!");
		    }
		  }).
		  error(function(data, status){
		    $("#company-addRole-modal").modal("hide");
        $.alert("追加失败!");
		  });
		}
		$scope.toRemoveRole = function(userId){
		  $scope.removeRole.isShow = false;
		  $scope.userId = userId;
		  companyService.toRemoveRole(userId)
		  .success(function(data){
		    $scope.roleList = data;
		  });
		}
		$scope.removeRole = function(){
		  var roles = new Array();
		  $("#roleRemoveForm").find("input[name='role']:checked").each(function(){
		    roles.push($(this).val());
		  });
		  if(roles.length==0){
        $scope.removeRole.isShow = true;
        return;
      }
      $scope.removeRole.isShow = false;
		  $("#submit").prop("disabled",true);
		  companyService.removeRole($scope.userId,roles).
		  success(function(data, status){
		    if(status == 200){
		      $("#company-removeRole-modal").modal("hide");
          $.alert("删除成功!");
		    }
		  }).
		  error(function(data, status){
		    $("#company-removeRole-modal").modal("hide");
        $.alert("删除失败!");
		  });
		}
		$scope.clearState = function(){
			$scope.emailError = null;
			$scope.kaptchaError = null;
			$scope.cellphoneError = null;
		}
		$scope.showAddUserForm = function(){
		  companyService.getAppList()
		  .success(function(data){
		    $scope.appList = data;
		  });
		  companyService.getRoleList()
		  .success(function(data){
		    $scope.roleList = data;
		  });
			$scope.userAddForm.$setPristine();
			$scope.email = '';
			$scope.kaptcha = '';
			$scope.truename = '';
			$scope.captcha = '';
			$scope.clearState();
		}
		$("img[name=kaptchaImage]").click(function() {
			$(this).hide().attr('src','kaptcha?' + Math.floor(Math.random() * 100)).fadeIn();
		});
		$scope.pageQuery($scope.pageInfo.currnetPage, $scope.pageInfo.pageSize);
	});
	celloudApp.controller("companyBaseController", function($scope, companyService){
		companyService.getCompanyInfo().
		success(function(data, status){
			if(status == 200){
				$scope.company = data;
				$scope.company_bak = angular.copy(data);
				// 用于reset
				$scope.province_bak = data.province;
				$scope.city_bak = data.city;
				$scope.district_bak = data.district;
				$scope.companyIcon_bak = data.companyIcon;
				
				$("#view").css("background-color","rgb(102, 102, 102)");
				$("#view").css("background-repeat","no-repeat");
				$("#view").css("background-position","center center");
				$("#view").css("background-size","contain");
				$("#view").css("background-image","url(" + $scope.company.companyIcon + ")");
				
				
				_init_area($scope.province_bak, $scope.city_bak, $scope.district_bak);
				
				var clipArea = new bjj.PhotoClip("#clipArea", {
			        size: [120, 50],
			        outputSize: [120, 50],
			        file: "#file",
			        view: "#view",
			        ok: "#clipBtn",
			        loadStart: function() {
			            console.log("照片读取中");
			        },
			        loadComplete: function() {
			            console.log("照片读取完成");
			        },
			        clipFinish: function(dataURL) {
			        	$scope.company.companyIcon = dataURL;
			        }
			    });
				
			}
		}).
		error(function(data, status){
			
		})
		$scope.reset = function(){
			$scope.company = angular.copy($scope.company_bak);
			$("#view").css("background-image","url(" + $scope.company.companyIcon + ")");
			_init_area($scope.province_bak, $scope.city_bak, $scope.district_bak);
		}
		$scope.updateCompanyInfo = function(){
			$scope.company.province = $("#s_province").val();
			$scope.company.city = $("#s_city").val();
			$scope.company.district = $("#s_county").val();
			companyService.updateCompanyInfo($scope.company).
			success(function(data, status){
				if(204 == status){
					$.alert("修改成功");
				}
			}).
			error(function(data, status){
				if(400 == status){
					$.alert("您填写的数据有误, 请核查后再提交");
				}
				if(500 == status){
					$.alert("提交失败, 请联系管理员");
				}
			})
		}
	});
	
celloudApp.controller("companyKeyController", function($scope, $rootScope, companyService, userService){
		$scope.pageInfo = {"currentPage" : 1, "pageSize" : 20};
		$scope.pageQuery = function(currentPage, pageSize){
			$scope.pageInfo.currentPage = currentPage || $scope.pageInfo.currentPage;
			$scope.pageInfo.pageSize = pageSize || $scope.pageInfo.pageSize;
			companyService.pageQueryKey($scope.pageInfo.currentPage, $scope.pageInfo.pageSize).
			success(function(keyList, status){
				if(status == 200){
					$scope.keyList = keyList;
				}
			});
		}
		$scope.add = function(){
			companyService.saveKey().
			success(function(data, status){
				if(status == 201){
					$.alert("创建成功");
					$scope.pageQuery();
				}
			}).
			error(function(data, status){
				if(status == 500){
					$.alert("创建失败");
				}
			});
		}
		$scope.remove = function(id){
			companyService.removeKey(id).
			success(function(data, status){
				if(status == 204){
					$.alert("删除成功");
					$scope.pageQuery();
				}
			}).
			error(function(data ,status){
				console.log("success" + status);
				if(status == 500){
					$.alert("删除失败");
				}
			});
		}
		$scope.update = function(id, state){
			state = state == 0 ? 1 : 0;
			companyService.updateKey(id, state).
			success(function(data, status){
				$.alert("更改状态成功");
				$scope.pageQuery();
			}).
			error(function(data, status){
				if(status == 500){
					$.alert("更改状态失败");				}
			});
		}
		$scope.showSecret = function(id){
			if($rootScope.authenFlag){
				// 已认证
				companyService.getKey(id).
				success(function(data, status){
					$scope.getSecretJson();
					$scope.pageQuery($scope.pageInfo.currnetPage, $scope.pageInfo.pageSize);
				}).
				error(function(data, status){
					$.alert("服务器错误")
				});
			}else{
				$rootScope.authenId = id;
				// 未认证
				companyService.checkCellphone().
				success(function(data, status){
					if(status == 200){
						$scope.cellphone = data;
						$("#company-showKey").modal("show");
					}
				})
			}
		}
		$scope.hideModal = function(){
			$("#company-showKey").modal("hide");
			$scope.btnFlag = true;
		}
		$("#company-showKey").on("hidden.bs.modal",function(e){
			if($scope.btnFlag){
				$scope.btnFlag = false;
				window.location.href = CONTEXT_PATH + "/index#/user/base";
			}
		});
		$scope.sendCaptcha = function(){
			companyService.sendCaptcha().
			success(function(data, status){
				if(status == 200){
					// 倒计时60秒
			        time = 60;
			        $("#captchaButton").prop("disabled",true);
			        $("#captchaButton").html("重新发送(<span id='times'>60</span>)");
			        var setinterval = setInterval(function(){
			            time--;    
			            if(time==0){
			                $("#captchaButton").prop("disabled",false);
			                clearInterval(setinterval);
			                $("#captchaButton").html("重新发送");
			            } else {
			                $("#times").html(time);
			            }
			        }, 1000);
				}
			}).
			error(function(data, status){
				if(status == 500){
					// 请勿频繁发送
					$.alert("请勿频繁获取验证码");
				}
			})
		};
		$scope.authenticationCellphone = function(){
			companyService.authenticationCellphone($scope.captcha).
			success(function(data, status){
				if(status == 200){
					$rootScope.authenFlag = true;
					$("#company-showKey").modal("hide");
					$scope.showSecret($rootScope.authenId);
				}
			})
		}
		$scope.getAuthenFlag = function(){
			companyService.getAuthenFlag().
			success(function(data, status){
				if(data == 1){
					$rootScope.authenFlag = true;
				}else{
					$rootScope.authenFlag = false;
				}
			})
		}
		$scope.getSecretJson = function(){
			companyService.getSecretJson().
			success(function(data, status){
				$rootScope.secretJson = data;
			})
		}
		$scope.hideSecret = function(id){
			companyService.removeSecret(id).
			success(function(data, status){
				$rootScope.secretJson = data;
				$scope.pageQuery();
			})
		}
		$scope.getAuthenFlag();
		$scope.getSecretJson();
		$scope.pageQuery($scope.pageInfo.currnetPage, $scope.pageInfo.pageSize);
	});
	
})();
