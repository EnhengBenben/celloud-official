$(document).ready(function() {
	var swiper = new Swiper(".swiper-container", {
			direction: 'vertical',
			// 如果需要分页器
			pagination: '.swiper-pagination',
			paginationType: 'fraction',
			autoHeight: true,
			observer: true, //修改swiper自己或子元素时，自动初始化swiper
			observeParents: true //修改swiper的父元素时，自动初始化swiper
		})
	//返回顶部
	/*$('.pages').bind('touchend',function(){
		swiper.slideTo(0, 1000, false);//切换到第一个slide，速度为1秒
	})*/
	//控制页面滚动
	var startScroll, touchStart, touchCurrent;
	swiper.slides.on('touchstart', function(e) {
		startScroll = this.scrollTop;
		touchStart = e.targetTouches[0].pageY;
	}, true);
	swiper.slides.on('touchmove', function(e) {
		touchCurrent = e.targetTouches[0].pageY;
		var touchesDiff = touchCurrent - touchStart;
		var slide = this;
		var onlyScrolling =
			(slide.scrollHeight > slide.offsetHeight) && //allow only when slide is scrollable
			(
				(touchesDiff < 0 && startScroll === 0) || //start from top edge to scroll bottom
				(touchesDiff > 0 && startScroll === (slide.scrollHeight - slide.offsetHeight)) || //start from bottom edge to scroll top
				(startScroll > 0 && startScroll < (slide.scrollHeight - slide.offsetHeight)) //start from the middle
			);
		if (onlyScrolling) {
			e.stopPropagation();
		}
	}, true);
	//控制rem
	document.documentElement.style.fontSize = document.documentElement.clientWidth / 7.5 + 'px';
	window.addEventListener('resize', function() {
		document.documentElement.style.fontSize = document.documentElement.clientWidth / 7.5 + 'px';
	})
 //获取url参数
  function request(strParame) {   
      var args = new Object( );   
      var query = location.search.substring(1);   
        
      var pairs = query.split("&"); // Break at ampersand   
      for(var i = 0; i < pairs.length; i++) {   
      var pos = pairs[i].indexOf('=');   
      if (pos == -1) continue;   
      var argname = pairs[i].substring(0,pos);   
      var value = pairs[i].substring(pos+1);   
      value = decodeURIComponent(value);   
      args[argname] = value;   
      }   
      return args[strParame];   
  }
	 var protocol = window.location.protocol;
	 var hostname = window.location.hostname;
	 var port = window.location.port ? ":" + window.location.port : "";
	 var context = window.CONTEXT_PATH;
	 if(hostname=='127.0.0.1'||hostname=='localhost'){
		 hostname='192.168.22.253';
		 port=':8080';
		 context = '/celloud';
	 }
	//峰图服务器地址base
	var fengtuBase = 'https://www.celloud.cn';
	 var webService=protocol+"//"+hostname+port+"/celloud/api/report/getRockyReport?projectId="+request('projectId')+"&dataKey="+request("dataKey")+"&appId="+request('appId');

	$.ajax({
		type: "get",
		url: webService,
		async: true,
		dataType: 'json',
		jsonpCallback: "callback",
		success: function(data) {
			console.log(data)
			var report = data
			var name = report.rocky.baseInfo.examineeName;
			var sampleType = report.rocky.baseInfo.sampleType;
			var sampleDeliveryTime = report.rocky.baseInfo.sampleDeliveryTime;
			var createTime = report.rocky.baseInfo.createTime;
			if(sampleType==undefined ){
				var ct = '<span>样本类型：</span>' +
				'<span>样本编号：</span>' +
				'<span>送检日期：' + sampleDeliveryTime + '</span>' +
				'<span>报告日期：</span>'
			}
			if(sampleDeliveryTime==undefined){
				var ct = '<span>样本类型： '+sampleType+'</span>' +
				'<span>样本编号：</span>' +
				'<span>送检日期：</span>' +
				'<span>报告日期：</span>'
			}
			if(sampleType==undefined && sampleDeliveryTime==undefined ){
				var ct = '<span>样本类型:</span>' +
				'<span>样本编号：</span>' +
				'<span>送检日期：</span>' +
				'<span>报告日期：</span>'
			}
			$('.type').html(ct)
			var record = report.rocky.records
			console.log(record)
			var tab = '';
			var res = '';
			var res1 = '';
			var tab1 = '';
			var tab2 = '';
			var p1 = '';
			//判断有无数据
			if (!record) {
				res = '<p class="reslut1">' +
					'本次<span id="BCRA">BRCA<span>基因没有检测到突变' +
					'</p>';
				$('.rep').hide();
				$('.p7').html(res)
			}

			//添加检测数据
			for (var i in record) {
				switch(record[i].significance){
					case 'Pathogenic':
					record[i].significance='致病相关的变异'
					break;
					case 'Likely pathogenic':
					record[i].significance='可能致病的变异'
					break;
					case 'Uncertain Significance':
					record[i].significance='意义不明确的变异'
					break;
					case 'Likely benign':
					record[i].significance='可能良性的变异'
					break;
					case 'Benign':
					record[i].significance='良性的变异'
					break;
					default:
					break;
				}
				tab = '<tr class="tab_body_p2">' +
					'<td>' + (parseInt(i) + 1) + '</td>' +
					'<td><span id="BCRA">' + record[i].gene + '</span></td>' +
					'<td>' + record[i].acids + '</td>' +
					'<td>' + record[i].nucleotides + '</td>' +
					'<td>' + record[i].significance + '</td>' +
					'</tr>';
				$('.p2_tbody').append(tab);
				if (report.rocky.pathogenic) {
					res = '<p class="reslut1">' +
						'本次BRCA基因共检测到<u>' + record.length + '</u>个突变，其中致病相关突变' +
						'为<u>' + report.rocky.pathogenicNum + '</u>个。' +
						'</p>';
					$('.result').html(res);
				} else {
					res = '<p class="reslut1">' +
						'本次BRCA基因共检测到<u>' + record.length + '</u>个突变，其中致病相关突变' +
						'为<u>' + report.rocky.pathogenicNum + '</u>个。' +
						'</p>'+
					'<p class="reslut2">' +
						'本次检测在您的乳腺癌关键基因<span id="BCRA">' + 'BRCA1</span>和<span 						id="BCRA">BRCA2</span>上未发现致病变异，因而该因素没有提高您的乳腺癌风险。</p>'
					$('.result').html(res);
				}

				res1 = '本次检测，在您的<span id="BCRA">' + 'BRCA1/2</span>基因中共发现了<u>' + record.length + 						'</u>个突变。它们是：'
				$('.countIn').html(res1);
			}
			for (var i in record) {
				tab1 = '<tr class="tab_body">' +
					'<td>' + (parseInt(i) + 1) + '</td>' +
					'<td><span id="BCRA">' + record[i].gene + '</span></td>' +
					'<td>' + record[i].acids + 'T</td>' +
					'<td>' + record[i].nucleotides + '</td>' +
					'<td>' + record[i].significance + '</td>' +
					'</tr>'
				$('.tab_body_p6').append(tab1);
			}
			for (var i in record) {
				if (report.rocky.companyId == 33) {
					tab2 = '<tr class="p2_tab_body">' +
						'<td>' + (parseInt(i) + 1) + '</td>' +
						'<td><span id="BCRA">' + record[i].gene + '</span>:<br>' + record[i].acids + '<br>临床意义:<br>' + record[i].significance + '</td>' +
						'<td>' + record[i].description + '</td>' +
						'</tr>' +
						'<tr class="p2_tab_body_fengtu">' +
						'<td colspan="3">' +
						'<img id="fengtu"' + 'src="'+ fengtuBase+ '/output/' + report.rocky.userId + '/' + report.rocky.appId + '/' + report.rocky.dataKey + '/' + record[i].peakPic + '"/>' +
						'</td>' +
						'</tr>';
					p1 = '<div class="report_title">' +
						'<img src="'+window.CONTEXT_PATH+'/images/wechat/rocky_report/logo 3.png"/>' +
						'<img src="'+window.CONTEXT_PATH+'/images/wechat/rocky_report/logo 3-1.png" alt="" title="" />' +
						'<img style="display:block;width:3.3rem;margin-bottom:0.61rem;" src="'+window.CONTEXT_PATH+'/images/wechat/rocky_report/logo 2.png" alt="" title="" />' +
						'</div>';
					$('.h_lastpage_hide').hide()
				} else {
					tab2 = '<tr class="p2_tab_body">' +
						'<td>' + (parseInt(i) + 1) + '</td>' +
						'<td><span id="BCRA">' + record[i].gene + '</span>:<br> ' + record[i].acids + '<br>临床意义:<br>' + record[i].significance + '</td>' +
						'<td>' + record[i].description + '</td>' +
						'</tr>';
					p1 = '<div class="report_title">' +
						'<img src="'+window.CONTEXT_PATH+'/images/wechat/rocky_report/logo 3.png"/>' +
						'<img src="'+window.CONTEXT_PATH+'/images/wechat/rocky_report/logo 3-1.png" alt="" title="" />' +
						'<img src="'+window.CONTEXT_PATH+'/images/wechat/rocky_report/1－@4x.png"/>' +
						'</div>';

				}
				$('.tab_significance').append(tab2);
				$('.p1').html(p1);
			}

		}
	});
})