package com.dbs.repository;

import com.dbs.entry.User;
import org.springframework.data.jpa.repository.JpaRepository;

//实体类型和主键类型
//jpa连接数据库
public interface UserRepository extends JpaRepository<User, Integer> {
}
