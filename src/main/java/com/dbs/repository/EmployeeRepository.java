package com.dbs.repository;

import com.dbs.entry.Employee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface EmployeeRepository extends ElasticsearchRepository<Employee, Integer> {
    public List<Employee> findEmployeeByLastNameLike(String lastName);
}
