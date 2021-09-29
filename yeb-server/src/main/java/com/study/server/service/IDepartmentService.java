package com.study.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.server.pojo.Department;
import com.study.server.pojo.RespBean;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pioutly
 * @since 2021-09-15
 */
public interface IDepartmentService extends IService<Department> {
    /**
     * 获取所有部门信息
     * @return
     */
    List<Department> getAllDepartment();

    RespBean addDepartment(Department department);

    RespBean deleteDepartment(Integer id);
}
