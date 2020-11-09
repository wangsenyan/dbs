package com.dbs.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.shardingjdbc.api.yaml.YamlShardingDataSourceFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MyShardingConfig {

    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource.druid.ds0")
    public DataSource dataSourceDs0() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid.ds1")
    public DataSource dataSourceDs1() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid.slave0")
    public DataSource dataSourceSlave0() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid.slave1")
    public DataSource dataSourceSlave1() {
        return DruidDataSourceBuilder.create().build();
    }

    private Map<String, DataSource> createShardingDataSourceMap() {
        Map<String, DataSource> result = new HashMap<>(2, 1);
        result.put("ds0", dataSourceDs0());
        result.put("ds1", dataSourceDs1());
        result.put("slave0", dataSourceSlave0());
        result.put("slave1", dataSourceSlave1());
        return result;
    }

    public DataSource ShardingDataSource() throws SQLException, IOException {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext();
        return YamlShardingDataSourceFactory.createDataSource(createShardingDataSourceMap(), ctx.getResource("my.yaml").getFile());
    }


    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(ShardingDataSource());
        return bean.getObject();
    }

    /**
     * @param sqlSessionFactory
     * @author: WSY
     * @description: 创建SqlSessionTemplateBean
     * @method: sqlSessionTemplate
     * @return: org.mybatis.spring.SqlSessionTemplate
     * @date: 2017年10月16日 10:05:40
     */
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
