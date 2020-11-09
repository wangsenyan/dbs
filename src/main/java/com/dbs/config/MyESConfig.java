package com.dbs.config;
//C:\Users\wangsenyan\.m2\repository

import org.springframework.context.annotation.Configuration;

/**
 * 安装:
 * docker run -p 9200:9200 -p 9300:9300 --name elasticsearch -e "discovery.type=single-node" \
 * -e "cluster.name=elasticsearch" -v /mydata/elasticsearch/plugins:/usr/share/elasticsearch/plugins \
 * -v /mydata/elasticsearch/data:/usr/share/elasticsearch/data -d elasticsearch:7.6.2
 * <p>
 * -e ES_JAVA_OPTS="-Xms256m -Xms256m" 占用虚拟内存256m
 * -p 9200:9200 网页端口
 * -p 9300:9300 分布式端口
 * <p>
 * 添加数据:[](https://www.elastic.co/guide/cn/elasticsearch/guide/current/_indexing_employee_documents.html)
 * 查询表达式 POST代替GET
 * <p>
 * SpringBoot默认支持两种技术来和ES交互
 * 1. Jest(默认不生效,需要导入jest工具包 io.searchbox.client.JestClient)
 * 2. SpringData ElasticSearch
 * 1) client节点信息 clusterNodes clusterName
 * 2) ElasticsearchTemplate 操作es
 * 3) 编写ElasticsearchRepository的子接口来操作ES
 */
@Configuration
public class MyESConfig {
//    @Bean
//    public JestClient jestClient(){
//       return new JestClient() {
//           @Override
//           public <T extends JestResult> T execute(Action<T> clientRequest) throws IOException {
//               return null;
//           }
//
//           @Override
//           public <T extends JestResult> void executeAsync(Action<T> clientRequest, JestResultHandler<? super T> jestResultHandler) {
//
//           }
//
//           @Override
//           public void shutdownClient() {
//
//           }
//
//           @Override
//           public void setServers(Set<String> servers) {
//
//           }
//
//           @Override
//           public void close() throws IOException {
//
//           }
//       };
//    }
}
