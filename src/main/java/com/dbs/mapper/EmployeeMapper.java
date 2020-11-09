package com.dbs.mapper;

import com.dbs.entry.Employee;

public interface EmployeeMapper {
    public Employee getEmp(Integer id);

    public void insertEmp(Employee employee);

    public void updateEmp(Employee employee);

    public void deleteEmp(Integer id);

    public Employee getEmpByLastName(String lastName);
}
