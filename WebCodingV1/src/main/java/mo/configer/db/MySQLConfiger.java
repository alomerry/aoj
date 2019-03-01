package mo.configer.db;


import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"mo.dao"}, sqlSessionFactoryRef = "getMySQLSessionFactory")
public class MySQLConfiger {

    @Resource(name = "db_mysql")
    private DataSource mysql;

    @Bean
    public SqlSessionFactory getMySQLSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(mysql);
        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate getMySQLSessionTemplate() throws Exception {
        return new SqlSessionTemplate(getMySQLSessionFactory());
    }
}
