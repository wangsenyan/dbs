//初始化类META-INF/spring-factories下
//需要放在
//ApplicationContextInitializer
//SpringApplicationRunListener
//只需放在ioc容器中
//ApplicationRunner @Component
//CommandLineRunner @Component
package com.dbs.initial;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class HelloApplicationContextInitializer implements ApplicationContextInitializer {
    //3
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        System.out.println("HelloApplicationContextInitializer...initialize...");
    }
}
