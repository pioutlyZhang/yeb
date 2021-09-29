package com.study.server.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.server.mapper.DepartmentMapper;
import com.study.server.pojo.Department;
import com.study.server.pojo.RespBean;
import com.study.server.service.IDepartmentService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pioutly
 * @since 2021-09-15
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;
    /**
     * 获取所有部门信息
     * @return
     *
     */
    @Override
    public List<Department> getAllDepartment() {
        return departmentMapper.getAllDepartment(-1);
    }

    /**
     * 添加部门
     * @param department
     */
    @Override
    public RespBean addDepartment(Department department) {
        department.setEnabled(true);
        departmentMapper.addDep(department);
        if(department.getResult()==1){
            return RespBean.success("添加成功",department);
        }
        return RespBean.error("添加失败");
    }

    @Override
    public RespBean deleteDepartment(Integer id) {
        Department dep = new Department();
        dep.setId(id);
        departmentMapper.deleteDepartment(dep);
        if(dep.getResult()==-2){
            return RespBean.error("该部门下还有子部门，删除失败");
        }
        if(dep.getResult()==-1){
            return RespBean.error("该部门下还有员工，删除失败");
        }
        if(dep.getResult()==1){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");

    }
}
