package mo.configer.db;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource(value = {"jdbc.properties"},ignoreResourceNotFound = true)
public class DatabaseConfiger {

    //TODO 配置多数据源
    @Bean("db_mysql")
    @ConfigurationProperties(prefix = "spring.datasource.oj-mysql-v1")
    public DataSource getMySQL() {
        return DataSourceBuilder.create().build();
    }
}
