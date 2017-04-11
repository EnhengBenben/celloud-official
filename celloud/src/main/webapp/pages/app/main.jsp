<ng-include src="'pages/partial/_partial_app_sidebar.html'"></ng-include>
<div class="pro-body">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li>应用市场</li>
    </ol>
    <div class="content">
		<div class="app-header">
			<div class="view">
				<div class="swiper-container">
					<div class="swiper-wrapper">
						<div class="swiper-slide" ng-repeat="app in classicApps">
							<a><img ng-src="/app/recommendMax?file={{app.pictureName}}" /></a>
						</div>
					</div>
				</div>
			</div>
			<div class="preview">
				<div class="swiper-container">
					<div class="swiper-wrapper">
						<div class="swiper-slide" ng-repeat="app in classicApps">
							<a><img ng-src="/app/recommendMax?file={{app.pictureName}}" /><span>{{app.appName}}</span></a>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="app-types">
			<div class="type-head">
				<h5>精选产品</h5>
				<a class="show-all">显示全部</a>
			</div>
			<div class="type-content app-list">
				<ul class="row">
					<li class="col-sm-3" ng-repeat="app in recommendApps">
						<div class="inner">
							<div class="app-logo">
								<img src="img/rocky-bg0.png" />
							</div>
							<div class="inner-content">
								<h5>{{app.appName}}-乳腺癌基因检测</h5>
								<p>{{app.classifyName}}</p>
								<div class="score">
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-null.png"/>
									<div>
										<span class="score-count">54</span>
										<span>份测评</span>
									</div>
								</div>
								<div class="footer">
									<button class="button-gray">获取</button>
								</div>
							</div>
						</div>
					</li>
					<li class="col-sm-3">
						<div class="inner">
							<div class="app-logo">
								<img src="img/rocky-bg0.png" />
							</div>
							<div class="inner-content">
								<h5>华木兰-乳腺癌基因检测</h5>
								<p>健康产品</p>
								<div class="score">
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-null.png"/>
									<div>
										<span class="score-count">54</span>
										<span>份测评</span>
									</div>
								</div>
								<div class="footer">
									<button class="button-gray">获取</button>
								</div>
							</div>
						</div>
					</li>
					<li class="col-sm-3">
						<div class="inner">
							<div class="app-logo">
								<img src="img/rocky-bg0.png" />
							</div>
							<div class="inner-content">
								<h5>华木兰-乳腺癌基因检测</h5>
								<p>健康产品</p>
								<div class="score">
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-null.png"/>
									<div>
										<span class="score-count">54</span>
										<span>份测评</span>
									</div>
								</div>
								<div class="footer">
									<button class="button-gray">获取</button>
								</div>
							</div>
						</div>
					</li>
					<li class="col-sm-3">
						<div class="inner">
							<div class="app-logo">
								<img src="img/rocky-bg0.png" />
							</div>
							<div class="inner-content">
								<h5>华木兰-乳腺癌基因检测</h5>
								<p>健康产品</p>
								<div class="score">
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-null.png"/>
									<div>
										<span class="score-count">54</span>
										<span>份测评</span>
									</div>
								</div>
								<div class="footer">
									<button class="button-gray">获取</button>
								</div>
							</div>
						</div>
					</li>
					<li class="col-sm-3">
						<div class="inner">
							<div class="app-logo">
								<img src="img/rocky-bg0.png" />
							</div>
							<div class="inner-content">
								<h5>华木兰-乳腺癌基因检测</h5>
								<p>健康产品</p>
								<div class="score">
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-null.png"/>
									<div>
										<span class="score-count">54</span>
										<span>份测评</span>
									</div>
								</div>
								<div class="footer">
									<button class="button-gray">获取</button>
								</div>
							</div>
						</div>
					</li>
					<li class="col-sm-3">
						<div class="inner">
							<div class="app-logo">
								<img src="img/rocky-bg0.png" />
							</div>
							<div class="inner-content">
								<h5>华木兰-乳腺癌基因检测</h5>
								<p>健康产品</p>
								<div class="score">
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-null.png"/>
									<div>
										<span class="score-count">54</span>
										<span>份测评</span>
									</div>
								</div>
								<div class="footer">
									<button class="button-gray">获取</button>
								</div>
							</div>
						</div>
					</li>
					<li class="col-sm-3">
						<div class="inner">
							<div class="app-logo">
								<img src="img/rocky-bg0.png" />
							</div>
							<div class="inner-content">
								<h5>华木兰-乳腺癌基因检测</h5>
								<p>健康产品</p>
								<div class="score">
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-null.png"/>
									<div>
										<span class="score-count">54</span>
										<span>份测评</span>
									</div>
								</div>
								<div class="footer">
									<button class="button-gray">获取</button>
								</div>
							</div>
						</div>
					</li>
					<li class="col-sm-3">
						<div class="inner">
							<div class="app-logo">
								<img src="img/rocky-bg0.png" />
							</div>
							<div class="inner-content">
								<h5>华木兰-乳腺癌基因检测</h5>
								<p>健康产品</p>
								<div class="score">
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-null.png"/>
									<div>
										<span class="score-count">54</span>
										<span>份测评</span>
									</div>
								</div>
								<div class="footer">
									<button class="button-gray">获取</button>
								</div>
							</div>
						</div>
					</li>
				</ul>
			</div>
			<div class="type-head">
				<h5>临床产品</h5>
				<a class="show-all">显示全部</a>
			</div>
			<div class="type-content app-list">
				<ul class="row">
					<li class="col-sm-3">
						<div class="inner">
							<div class="app-logo">
								<img src="img/rocky-bg0.png" />
							</div>
							<div class="inner-content">
								<h5>华木兰-乳腺癌基因检测</h5>
								<p>健康产品</p>
								<div class="score">
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-null.png"/>
									<div>
										<span class="score-count">54</span>
										<span>份测评</span>
									</div>
								</div>
								<div class="footer">
									<button class="button-gray add">添加APP</button>
								</div>
							</div>
						</div>
					</li>
					<li class="col-sm-3">
						<div class="inner">
							<div class="app-logo">
								<img src="img/rocky-bg0.png" />
							</div>
							<div class="inner-content">
								<h5>华木兰-乳腺癌基因检测</h5>
								<p>健康产品</p>
								<div class="score">
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-null.png"/>
									<div>
										<span class="score-count">54</span>
										<span>份测评</span>
									</div>
								</div>
								<div class="footer">
									<button class="button-gray">获取</button>
								</div>
							</div>
						</div>
					</li>
					<li class="col-sm-3">
						<div class="inner">
							<div class="app-logo">
								<img src="img/rocky-bg0.png" />
							</div>
							<div class="inner-content">
								<h5>华木兰-乳腺癌基因检测</h5>
								<p>健康产品</p>
								<div class="score">
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-null.png"/>
									<div>
										<span class="score-count">54</span>
										<span>份测评</span>
									</div>
								</div>
								<div class="footer">
									<button class="button-gray">获取</button>
								</div>
							</div>
						</div>
					</li>
					<li class="col-sm-3">
						<div class="inner">
							<div class="app-logo">
								<img src="img/rocky-bg0.png" />
							</div>
							<div class="inner-content">
								<h5>华木兰-乳腺癌基因检测</h5>
								<p>健康产品</p>
								<div class="score">
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-null.png"/>
									<div>
										<span class="score-count">54</span>
										<span>份测评</span>
									</div>
								</div>
								<div class="footer">
									<button class="button-gray">获取</button>
								</div>
							</div>
						</div>
					</li>
					<li class="col-sm-3">
						<div class="inner">
							<div class="app-logo">
								<img src="img/rocky-bg0.png" />
							</div>
							<div class="inner-content">
								<h5>华木兰-乳腺癌基因检测</h5>
								<p>健康产品</p>
								<div class="score">
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-null.png"/>
									<div>
										<span class="score-count">54</span>
										<span>份测评</span>
									</div>
								</div>
								<div class="footer">
									<button class="button-gray">获取</button>
								</div>
							</div>
						</div>
					</li>
					<li class="col-sm-3">
						<div class="inner">
							<div class="app-logo">
								<img src="img/rocky-bg0.png" />
							</div>
							<div class="inner-content">
								<h5>华木兰-乳腺癌基因检测</h5>
								<p>健康产品</p>
								<div class="score">
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-null.png"/>
									<div>
										<span class="score-count">54</span>
										<span>份测评</span>
									</div>
								</div>
								<div class="footer">
									<button class="button-gray">获取</button>
								</div>
							</div>
						</div>
					</li>
					<li class="col-sm-3">
						<div class="inner">
							<div class="app-logo">
								<img src="img/rocky-bg0.png" />
							</div>
							<div class="inner-content">
								<h5>华木兰-乳腺癌基因检测</h5>
								<p>健康产品</p>
								<div class="score">
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-null.png"/>
									<div>
										<span class="score-count">54</span>
										<span>份测评</span>
									</div>
								</div>
								<div class="footer">
									<button class="button-gray">获取</button>
								</div>
							</div>
						</div>
					</li>
					<li class="col-sm-3">
						<div class="inner">
							<div class="app-logo">
								<img src="img/rocky-bg0.png" />
							</div>
							<div class="inner-content">
								<h5>华木兰-乳腺癌基因检测</h5>
								<p>健康产品</p>
								<div class="score">
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-null.png"/>
									<div>
										<span class="score-count">54</span>
										<span>份测评</span>
									</div>
								</div>
								<div class="footer">
									<button class="button-gray">获取</button>
								</div>
							</div>
						</div>
					</li>
				</ul>
			</div>
			<div class="type-head">
				<h5>健康产品</h5>
				<a class="show-all">显示全部</a>
			</div>
			<div class="type-content app-list">
				<ul class="row">
					<li class="col-sm-3">
						<div class="inner">
							<div class="app-logo">
								<img src="img/rocky-bg0.png" />
							</div>
							<div class="inner-content">
								<h5>华木兰-乳腺癌基因检测</h5>
								<p>健康产品</p>
								<div class="score">
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-null.png"/>
									<div>
										<span class="score-count">54</span>
										<span>份测评</span>
									</div>
								</div>
								<div class="footer">
									<button class="button-gray add">添加APP</button>
								</div>
							</div>
						</div>
					</li>
					<li class="col-sm-3">
						<div class="inner">
							<div class="app-logo">
								<img src="img/rocky-bg0.png" />
							</div>
							<div class="inner-content">
								<h5>华木兰-乳腺癌基因检测</h5>
								<p>健康产品</p>
								<div class="score">
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-null.png"/>
									<div>
										<span class="score-count">54</span>
										<span>份测评</span>
									</div>
								</div>
								<div class="footer">
									<button class="button-gray">获取</button>
								</div>
							</div>
						</div>
					</li>
					<li class="col-sm-3">
						<div class="inner">
							<div class="app-logo">
								<img src="img/rocky-bg0.png" />
							</div>
							<div class="inner-content">
								<h5>华木兰-乳腺癌基因检测</h5>
								<p>健康产品</p>
								<div class="score">
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-null.png"/>
									<div>
										<span class="score-count">54</span>
										<span>份测评</span>
									</div>
								</div>
								<div class="footer">
									<button class="button-gray">获取</button>
								</div>
							</div>
						</div>
					</li>
					<li class="col-sm-3">
						<div class="inner">
							<div class="app-logo">
								<img src="img/rocky-bg0.png" />
							</div>
							<div class="inner-content">
								<h5>华木兰-乳腺癌基因检测</h5>
								<p>健康产品</p>
								<div class="score">
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-null.png"/>
									<div>
										<span class="score-count">54</span>
										<span>份测评</span>
									</div>
								</div>
								<div class="footer">
									<button class="button-gray">获取</button>
								</div>
							</div>
						</div>
					</li>
					<li class="col-sm-3">
						<div class="inner">
							<div class="app-logo">
								<img src="img/rocky-bg0.png" />
							</div>
							<div class="inner-content">
								<h5>华木兰-乳腺癌基因检测</h5>
								<p>健康产品</p>
								<div class="score">
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-null.png"/>
									<div>
										<span class="score-count">54</span>
										<span>份测评</span>
									</div>
								</div>
								<div class="footer">
									<button class="button-gray">获取</button>
								</div>
							</div>
						</div>
					</li>
					<li class="col-sm-3">
						<div class="inner">
							<div class="app-logo">
								<img src="img/rocky-bg0.png" />
							</div>
							<div class="inner-content">
								<h5>华木兰-乳腺癌基因检测</h5>
								<p>健康产品</p>
								<div class="score">
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-null.png"/>
									<div>
										<span class="score-count">54</span>
										<span>份测评</span>
									</div>
								</div>
								<div class="footer">
									<button class="button-gray">获取</button>
								</div>
							</div>
						</div>
					</li>
					<li class="col-sm-3">
						<div class="inner">
							<div class="app-logo">
								<img src="img/rocky-bg0.png" />
							</div>
							<div class="inner-content">
								<h5>华木兰-乳腺癌基因检测</h5>
								<p>健康产品</p>
								<div class="score">
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-null.png"/>
									<div>
										<span class="score-count">54</span>
										<span>份测评</span>
									</div>
								</div>
								<div class="footer">
									<button class="button-gray">获取</button>
								</div>
							</div>
						</div>
					</li>
					<li class="col-sm-3">
						<div class="inner">
							<div class="app-logo">
								<img src="img/rocky-bg0.png" />
							</div>
							<div class="inner-content">
								<h5>华木兰-乳腺癌基因检测</h5>
								<p>健康产品</p>
								<div class="score">
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-full.png"/>
									<img src="img/score-null.png"/>
									<div>
										<span class="score-count">54</span>
										<span>份测评</span>
									</div>
								</div>
								<div class="footer">
									<button class="button-gray">获取</button>
								</div>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</div>
</div>	
		<script src="plugins/swiper-3.4.2/swiper-3.4.2.min.js"></script>
		<script>
	    var viewSwiper = new Swiper('.view .swiper-container', {
			onSlideChangeStart: function() {
				updateNavPosition()
			}
		})

		var previewSwiper = new Swiper('.preview .swiper-container', {
			direction : 'vertical',
			visibilityFullFit: true,
			slidesPerView: 'auto',
			onlyExternal: true,
			onSlideClick: function() {
				viewSwiper.swipeTo(previewSwiper.clickedSlideIndex)
			}
		})
		function updateNavPosition() {
			$('.preview .active-nav').removeClass('active-nav')
			var activeNav = $('.preview .swiper-slide').eq(viewSwiper.activeIndex).addClass('active-nav')
			if (!activeNav.hasClass('swiper-slide-visible')) {
				if (activeNav.index() > previewSwiper.activeIndex) {
					var thumbsPerNav = Math.floor(previewSwiper.width / activeNav.width()) - 1
					previewSwiper.swipeTo(activeNav.index() - thumbsPerNav)
				} else {
					previewSwiper.swipeTo(activeNav.index())
				}
			}
		}

	    </script>
