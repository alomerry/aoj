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
@MapperScan(basePackages = {"mo.dao.mini"}, sqlSessionFactoryRef = "getMiniMySQLSessionFactory",sqlSessionTemplateRef = "getMiniMySQLSessionTemplate")
public class MiniMySQLConsumerConfiger {
    @Resource(name = "db_mysql_secondary")
    private DataSource mysql_1;

    @Bean
    public SqlSessionFactory getMiniMySQLSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(mysql_1);
        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate getMiniMySQLSessionTemplate() throws Exception {
        return new SqlSessionTemplate(getMiniMySQLSessionFactory());
    }
}
