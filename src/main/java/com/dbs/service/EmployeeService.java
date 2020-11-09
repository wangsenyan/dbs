package com.dbs.service;

import com.dbs.entry.Employee;
import com.dbs.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

/**
 * @CacheConfig 抽取缓存的公共配置
 */
@CacheConfig(cacheNames = "emp", cacheManager = "RedisCacheManager")
@Service
public class EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;

    /**
     * @param id
     * @return
     * @Cacheable //如果有相同的数据，直接从缓存中获取
     * cacheNames/value: 指定缓存的名字 数组的方式
     * key: 缓存数据使用的key,可用它来指定方法参数的值 1-方法的返回值
     * 编写SpEl: #id:参数id的值 #a0 #p0 #root.args[0]
     * getEmp[0].. = key = "#root.methodName+'['+#id+']'"
     * keyGenerator: key生成器,可以自己指定key的生成器的组件id
     * 例子:com.dbs.config.MyCacheConfig
     * cacheManager: 缓存管理器
     * CacheResolver: 与cacheManager二选一
     * condition: 指定符合条件的情况下才缓存 condition = '#id>0'
     * unless: 否定缓存 unless = '#result==null'
     * sync: 是否使用异步模式
     * 原理：
     * 1. 自动配置类
     * 2. 缓存的配置类 CacheConfigurationImportSelector
     * org.springframework.boot.autoconfigure.cache.GenericCacheConfiguration
     * org.springframework.boot.autoconfigure.cache.JCacheCacheConfiguration
     * org.springframework.boot.autoconfigure.cache.EhCacheCacheConfiguration
     * org.springframework.boot.autoconfigure.cache.HazelcastCacheConfiguration
     * org.springframework.boot.autoconfigure.cache.InfinispanCacheConfiguration
     * org.springframework.boot.autoconfigure.cache.CouchbaseCacheConfiguration
     * org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration
     * org.springframework.boot.autoconfigure.cache.CaffeineCacheConfiguration
     * org.springframework.boot.autoconfigure.cache.SimpleCacheConfiguration
     * org.springframework.boot.autoconfigure.cache.NoOpCacheConfiguration
     * 3. 哪个默认生效 SimpleCacheConfiguration
     * 4. 给容器中注册了一个CacheManager:ConcurrentMapCacheManager
     * 5. 可以获取和创建ConcurrentMapCache类型的缓存组件,他的作用将数据源保存在ConcurrentMap中
     * 6. 调用流程
     * 不能使用#result,因为先搜索再运行
     */
    @Cacheable(cacheNames = {"emp"} /*,keyGenerator = "myKeyGenerator"*/)
    public Employee getEmp(Integer id) {
        System.out.println("查询" + id + "号员工");
        Employee emp = employeeMapper.getEmp(id);
        return emp;
    }

    /**
     * @CachePut: 既调用方法又更新缓存数据
     * 运行时机:先调用目标方法，然后将目标方法的结果缓存起来
     * key: #employee.id   #result.id
     * @CachePut 与 @Cacheable 的key一致
     */
    @CachePut(/*value = "emp",*/key = "#result.id")
    public Employee updateEmp(Employee employee) {
        System.out.println("更新" + employee + "员工");
        employeeMapper.updateEmp(employee);
        return employee;
    }

    /**
     * @CacheEvict 缓存清除
     * key:指定清除的数据
     * allEntries = true 清除这个缓存中的所有数据
     * beforeInvocation = true 清除缓存在方法之前执行，默认在方法之后执行
     */
    @CacheEvict(/*value = "emp",*/key = "#id")
    public void deleteEmp(Integer id) {
        System.out.println("deleteEmp: " + id);
    }

    /**
     * @param lastName
     * @return
     * @Caching定义复杂的缓存
     */
    @Caching(
            cacheable = {
                    //ctrl+shift+/
                    @Cacheable(/*value = "emp",*/key = "#lastName")
            },
            put = {
                    @CachePut(/*value = "emp",*/key = "#result.id"),
                    @CachePut(/*value = "emp",*/key = "#result.email")
            }
    )
    public Employee getEmpByLastName(String lastName) {
        return employeeMapper.getEmpByLastName(lastName);
    }
}
