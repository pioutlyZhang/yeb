package com.study.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.study.server.pojo.Employee;
import com.study.server.pojo.RespBean;
import com.study.server.pojo.RespPageBean;
import com.study.server.pojo.Salary;
import com.study.server.service.IEmployeeService;
import com.study.server.service.ISalaryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salary/sobcfg")
public class SalarySobCfgController {

    @Autowired
    private ISalaryService salaryService;

    @Autowired
    private IEmployeeService employeeService;
    @ApiOperation("获取所有工资账套")
    @GetMapping("/salaries")
    public List<Salary> getAllSalaries(){
        return salaryService.list();

    }
    @ApiOperation("获取所有员工账套")
    @GetMapping("/")
    public RespPageBean getEmpSalaries(@RequestParam(defaultValue = "1") Integer currentPage,
                                       @RequestParam(defaultValue = "10") Integer size){

        return employeeService.getEmpSalaries(currentPage,size);
    }

    @ApiOperation("更新员工账套")
    @PutMapping("/")
    public RespBean updateEmpSalaries(Integer id, Integer sid){
        if(employeeService.update(new UpdateWrapper<Employee>().set("salaryId",sid).eq("id",id))){
            return RespBean.success("更新成功");
        }else{
            return RespBean.error("更新失败");
        }
    }
}
