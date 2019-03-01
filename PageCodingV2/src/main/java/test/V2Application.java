package test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import utils.LocationUtil;


@SpringBootApplication
public class V2Application {
    public static void main(String[] args) {
        SpringApplication.run(V2Application.class);
    }

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> customizer() {
        return LocationUtil.customizer("PageCodingV2/src/main/webapp");
    }
}