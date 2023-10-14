package mo.configer.db;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
public class DataBaseConfiger {

    //配置多数据源
    @Bean("db_mysql_main")
    @ConfigurationProperties(prefix = "spring.datasource.oj-mysql-v1")
    public DataSource getMainMySQL() {
        return DataSourceBuilder.create().build();
    }

    @Bean("db_mysql_secondary")
    @ConfigurationProperties(prefix = "spring.datasource.oj-mysql-v2")
    public DataSource getSecondMySQL() {
        return DataSourceBuilder.create().build();
    }
}
