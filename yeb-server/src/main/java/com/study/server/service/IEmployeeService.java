package com.study.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.server.pojo.Employee;
import com.study.server.pojo.RespBean;
import com.study.server.pojo.RespPageBean;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pioutly
 * @since 2021-09-15
 */
public interface IEmployeeService extends IService<Employee> {

    RespPageBean getEmployee(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope);

    RespBean getMaxWorkId();

    RespBean addEmp(Employee emp);

    List<Employee> getEmployeeById(Integer id);

    RespPageBean getEmpSalaries(Integer currentPage, Integer size);
}
