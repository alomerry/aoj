package mo.configer;

import mo.interceptor.AdminIntercepter;
import mo.interceptor.UserIntercepter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebAppConfiger implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new UserIntercepter());
        registry.addInterceptor(new AdminIntercepter());
    }

}
