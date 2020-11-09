package com.dbs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.dbs.mapper")
@SpringBootApplication
/**
 * 1. 开启基于注解的缓存 @EnableCaching
 * 2. 标注缓存注解即可
 *    @Cacheable
 *    @CacheEvict
 *    @CachePut
 *  3. 原理：CacheManager ===Cache 缓存组件来实际给给缓存中存取数据
 *     1). 引入redis的starter,容器中保存的是RedisCacheManager
 *     2). RedisCacheManager帮我们创建RedisCache来作为缓存组件
 *     3). RedisCache通过操作redis缓存数据
 */
@EnableCaching //开启基于注解的缓存
@EnableRabbit //开启基于注解的RabbitMQ模式
@EnableAsync //允许异步
@EnableScheduling //允许定时任务
//@EnableDubbo垃圾
public class DbconfigApplication {
    public static void main(String[] args) {
        SpringApplication.run(DbconfigApplication.class, args);
    }
}