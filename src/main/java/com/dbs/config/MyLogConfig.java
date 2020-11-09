package com.dbs.config;

/**
 * 1. springboot SLF4f - logback
 * 2. 应该调用日志抽象层的接口
 * 3. 使用slf4j,配置文件做成日志实现框架的配置文件
 * 4. 遗留问题
 * 1) 将系统中其他框架先排除出去
 * 2）用中间包来替换原有的日志框架
 * 3）我们导入slf4j其他的实现
 * <p>
 * 4）如果我们要引入其他框架,一定要把这个框架的默认日志依赖移除掉
 * <p>
 * 5. logging.file/logging.path 不指定,打印在控制台
 * 6. logging.file 指定日志文件
 */
public class MyLogConfig {
}
