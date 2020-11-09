package com.dbs.controller;

import com.dbs.entry.Department;
import com.dbs.entry.Employee;
import com.dbs.entry.Order;
import com.dbs.mapper.EmployeeMapper;
import com.dbs.mapper.OrderMapper;
import com.dbs.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@RunWith(SpringRunner.class)
@SpringBootTest
class DbconfigApplicationTests {
    @Autowired
    DataSource dataSource;

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    //@Autowired
    //RedisTemplate<Object,Employee> employeeRedisTemplate;
    @Test
    void contextLoads() throws SQLException {
        System.out.println(dataSource.getClass());
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        connection.close();
    }

    @Test
    public void testMapper() {
        Employee employee = employeeMapper.getEmp(1);
        System.out.println(employee);
    }

    @Test
    public void test0() {
        Employee employee = employeeMapper.getEmp(1);
        //如果保存对象,默认jdk序列机制,序列化的数据保存到redis中
        redisTemplate.opsForHash().put("emp", "111", employee);
    }

    //默认的成功
    @Test
    public void test1() {
        //Employee employee = employeeMapper.getEmp(1);
        stringRedisTemplate.opsForHash().put("emp", "111", "employee");
    }
//	@Test
//	public void test2(){
//		Employee employee = employeeMapper.getEmp(1);
//		employeeRedisTemplate.opsForHash().put("emp", "111",employee);
//	}

    /**
     * 1. 单播
     */
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void testRabbitMQ() {
        //message需要自己定义，定制消息体内容和消息头
        // rabbitTemplate.send(exchange,routekey,message);
        //object默认当成消息体只需传入要传送的对象,自动序列化转换Object为消息体
        // rabbitTemplate.convertAndSend(exchange,routekey,object);
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "这是一个消息");
        map.put("data", Arrays.asList("helloWorld", 123, true));
        //对象呗默认序列化以后发送出去
        rabbitTemplate.convertAndSend("exchange.direct", "own.news", map);
    }

    /**
     * 如何将数据自动转换为json发送出去
     */
    @Test
    public void receive() {
        // Object o =  rabbitTemplate.receiveAndConvert("own.news");
        //如果o==null,o.getClass()会出错
        //接收完消息后会消息会从队列中弹出
        //System.out.println(o.getClass());
        // System.out.println(o);
    }

    /**
     * 广播
     */
    @Test
    public void sendMsg() {
        //设置延时时间 setExpiration 毫秒
        rabbitTemplate.convertAndSend("exchange.fanout", "", new Department(2, "测试部"), message -> {
            message.getMessageProperties().setExpiration("10000");
            return message;
        });

        //rabbitTemplate.convertAndSend("exchange.fanout","",new Department(1,"测试部"));
    }

    @Autowired
    AmqpAdmin amqpAdmin;

    @Test
    public void createExchange() {
        //amqpAdmin.declareExchange(new DirectExchange("amqpadmin.exchange"));
        //System.out.println("创建成功");
        //创建一个队列
        amqpAdmin.declareQueue(new Queue("amqpadmin.queue", true));
        //System.out.println("创建成功");
        amqpAdmin.declareBinding(new Binding("amqpadmin.queue", Binding.DestinationType.QUEUE,
                "amqpadmin.exchange", "amqp.haha", null));
        //amqpAdmin.initialize();
    }

    /**
     * 失败
     */
//	@Autowired
//	JestClient jestClient;
//	@Test
//	public void testJest()   {
//		//报错索引
//       Employee employee = new Employee();
//       //employee.setdId(3);
//       employee.setEmail("12@qq.com");
//       employee.setGender(0);
//       employee.setdId(3);
//       employee.setLastName("行");
//       //构建一个索引功能
//       Index index =  new Index.Builder(employee).index("own").type("news").build();
//       try{
//       	jestClient.execute(index);
//	   }catch (IOException e){
//       	 e.printStackTrace();
//	   }
//
//	}
    /**
     * 需要在实体类添加 @Document(indexName = "own",indexStoreType ="employee" )
     */
    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    public void testES() {
        Employee employee = new Employee();
        employee.setdId(3);
        employee.setEmail("12@qq.com");
        employee.setGender(0);
        employee.setdId(3);
        employee.setLastName("哥哥");
        employeeRepository.save(employee);
        //有问题
        //List<Employee> employee1 =  employeeRepository.findEmployeeByLastNameLike("哥");
        //System.out.println("获取成功"+ employee1);
    }

    @Autowired
    JavaMailSenderImpl mailSender;

    @Test
    public void testEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("hello");
        message.setText("i love you");
        message.setTo("school_use@163.com");
        message.setFrom("2633600702@qq.com");
        //mailSender.send(message);
    }

    @Test
    public void testEmail2() throws MessagingException {
        MimeMessage mimeMailMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMailMessage, true);
        helper.setSubject("我好看吗");
        helper.setText("<b style='color:green'>好看</b>", true);
        helper.setTo("school_use@163.com");
        helper.setFrom("2633600702@qq.com");
        helper.addAttachment("1.jpg", new File("C:\\Users\\wangsenyan\\Pictures\\th.jpg"));
        helper.addAttachment("1.jpg", new File("C:\\Users\\wangsenyan\\Pictures\\th1.jpg"));
        //mailSender.send(mimeMailMessage);
    }

    Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void testLog() {
        //日志的级别
        //由低到高
        //可以调整输出的日志级别
        logger.trace("这是trace日志");
        logger.debug("这是debug日志");
        logger.info("这是info日志");
        logger.warn("这是warn日志");
        logger.error("这是error日志");
    }

    @Autowired
    OrderMapper orderMapper;

    @Test
    public void testOrder() {
        Random random = new Random();
        for (int i = 1; i < 20; i++) {
            long od = Math.abs(random.nextInt());
            Order order = new Order();
            order.setUserId(i);
            //order.setOrderId(od);
            order.setStatus("status" + i);
            System.out.println("执行前");
            orderMapper.insert(order);
            System.out.println("执行成功");
        }
    }
}
