package mo.application;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;

@SpringBootApplication
@ComponentScan(value = {"mo.controller", "mo.service", "mo.configer", "mo.listener"})
@MapperScan("mo.dao")
//@PropertySource(value = {"jdbc.properties"})
public class BetaVersionApp {

    public static void main(String[] args) {
        SpringApplication.run(BetaVersionApp.class);
    }

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> customizer() {
        return factory -> {
            factory.addContextCustomizers(context -> {
                String relativePath = "BetaVersion/src/main/webapp";
                File docBaseFile = new File(relativePath);
                // 路径是否存在
                if (docBaseFile.exists()) {
                    context.setDocBase(docBaseFile.getAbsolutePath());
                }
            });
        };
    }
}