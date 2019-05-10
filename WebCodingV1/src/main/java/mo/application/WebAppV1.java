package mo.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import utils.LocationUtil;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(value = {"mo.controller", "mo.service", "mo.configer",/* "mo.listener",*/ "mo.aop"})
//@EnableAspectJAutoProxy(exposeProxy = true)
public class WebAppV1 extends SpringBootServletInitializer {
    public static void main(String[] a) {
        SpringApplication.run(WebAppV1.class);
    }

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> setDocPath() {
        return LocationUtil.customizer("WebCodingV1/src/main/webapp");
    }

    @Override//为了打包springboot项目
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }
}
