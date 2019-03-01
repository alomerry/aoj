package mo.configer;

import mo.listener.SessionAttributeListener;
import mo.listener.SessionListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebSessionListenerConfiger {

    @Bean
    public SessionAttributeListener initSessionListenerListener() {
        return new SessionAttributeListener();
    }

    @Bean
    public SessionListener initSessionListener() {
        return new SessionListener();
    }
}
