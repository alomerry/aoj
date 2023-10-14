package mo.application;

import mo.utils.LocationUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
//import utils.LocationUtil;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(value = {"mo.controller", "mo.service", "mo.configer",/* "mo.listener",*/ "mo.aop"})
@PropertySource(value = {"classpath:configer.properties"}, ignoreResourceNotFound = false, encoding = "UTF-8")
//@EnableAspectJAutoProxy(exposeProxy = true)
public class WebAppV1 extends SpringBootServletInitializer {

    @Value("${LOCAPP.LOACTION}")
    private String LOCAPP_LOACTION;

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
