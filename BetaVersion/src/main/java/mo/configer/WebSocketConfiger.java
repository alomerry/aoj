package mo.configer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfiger {
    @Bean
    public ServerEndpointExporter webSocket() {
        return new ServerEndpointExporter();
    }
}
