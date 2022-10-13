package com.myExercise.FileSystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FileSystemApplication {

	private static Logger logger = LoggerFactory.getLogger(FileSystemApplication.class);

	@Value("${tomcat.port:8080}")
	private int port;
	@Value("${tomcat.contextPath:/file-system}")
	private String contextPath;

	public static void main(String[] args) {
		SpringApplication.run(FileSystemApplication.class, args);
		logger.info(" application started ");
	}

	/**
	 * enables AJP in embedded Tomcat.
	 *
	 * @return customizer
	 */
	@Bean
	public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer() {
		return server -> {
			server.setPort(port);
			server.setContextPath(contextPath);
		};
	}

}
