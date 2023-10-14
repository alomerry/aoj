package mo.utils;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;

import java.io.File;

public class LocationUtil {
    public static WebServerFactoryCustomizer<TomcatServletWebServerFactory> customizer(String locApp) {
        return factory -> {
            factory.addContextCustomizers(context -> {
//                String relativePath = "PageCodingV1/src/main/webapp";
                File docBaseFile = new File(locApp);
                if (docBaseFile.exists()) {
                    context.setDocBase(docBaseFile.getAbsolutePath());
                }
            });
        };
    }
}
