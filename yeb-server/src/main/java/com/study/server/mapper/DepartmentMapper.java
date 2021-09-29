package com.study.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.server.pojo.Department;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author pioutly
 * @since 2021-09-15
 */
public interface DepartmentMapper extends BaseMapper<Department> {

    List<Department> getAllDepartment(Integer parentId);

    void addDep(Department department);

    void deleteDepartment(Department dep);
}
