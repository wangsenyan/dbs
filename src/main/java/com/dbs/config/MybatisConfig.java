package com.dbs.config;

import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class MybatisConfig {
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        //自定义mybatis的配置规则
        return new ConfigurationCustomizer() {
            @Override
            public void customize(Configuration configuration) {
                //驼峰
                configuration.setMapUnderscoreToCamelCase(true);
            }
        };
    }
}
