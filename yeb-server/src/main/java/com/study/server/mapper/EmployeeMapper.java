package com.study.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.server.pojo.Employee;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author pioutly
 * @since 2021-09-15
 */
public interface EmployeeMapper extends BaseMapper<Employee> {

    IPage getEmployeeByPage(Page<Employee> page
            , @Param("employee") Employee employee
            , @Param("beginDateScope") LocalDate[] beginDateScope);

    /**
     * 查询员工根据id
     * @param id
     * @return
     */
    List<Employee> getEmployeeById(Integer id);

    /**
     * 获取员工账套
     * @param page
     * @return
     */
    IPage<Employee> getEmployeeWithSalary(Page<Employee> page);
}
