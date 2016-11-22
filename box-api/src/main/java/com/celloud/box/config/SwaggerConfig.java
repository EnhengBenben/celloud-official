package com.celloud.box.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.Api;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
	@Bean
	public Docket testApi() {
		return new Docket(DocumentationType.SWAGGER_2).select() // 选择那些路径和api会生成document
				.apis(RequestHandlerSelectors.withClassAnnotation(Api.class)) // 对所有api进行监控
				.build().apiInfo(testApiInfo());
	}

	private ApiInfo testApiInfo() {
		ApiInfo apiInfo = new ApiInfo("盒子的接口", // 大标题
				"盒子上传文件的接口", // 小标题
				"0.1", // 版本
				"孙文栋", "sunwendong@celloud.cn", // 作者
				"The Apache License, Version 2.0", // 链接显示文字
				"http://www.apache.org/licenses/LICENSE-2.0.html"// 网站链接
		);

		return apiInfo;
	}
}
