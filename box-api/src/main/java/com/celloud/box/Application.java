package com.celloud.box;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * App启动类<br>
 * 
 * 如果继承org.springframework.boot.web.support.SpringBootServletInitializer，
 * 并覆盖configure方法，修改pom.xml的package为war后，可以使用maven打包成war包。
 *
 */

@SpringBootApplication
@EnableAsync
public class Application extends AsyncConfigurerSupport{
	private static Logger logger = LoggerFactory.getLogger(Application.class);

	/**
	 * 修改pom.xml的package为war后，可以打包成war包
	 * 
	 * @param args
	 */
	// @Override
	// protected SpringApplicationBuilder configure(SpringApplicationBuilder
	// application) {
	// return application.sources(Application.class);
	// }

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		logger.info("Application is Running ...");
	}
}
