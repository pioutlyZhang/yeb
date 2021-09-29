package com.study.server.controller;


import com.study.server.pojo.RespBean;
import com.study.server.pojo.Salary;
import com.study.server.service.ISalaryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pioutly
 * @since 2021-09-15
 */
@RestController
@RequestMapping("/salary/sob")
public class SalaryController {

    @Autowired
    private ISalaryService salaryService;

    @ApiOperation("获取所有工资信息")
    @GetMapping("/")
    public List<Salary> getAllSalary(){
        return salaryService.list();
    }

    @ApiOperation("添加工资账套")
    @PostMapping("/")
    public RespBean addSalary(@RequestBody Salary salary){
        if(salaryService.save(salary)){
            return RespBean.success("添加成功");
        }else{
            return RespBean.error("添加失败");
        }
    }

    @ApiOperation("更新工资账套信息")
    @PutMapping("/")
    public RespBean updateSalary(@RequestBody Salary salary){
        if(salaryService.updateById(salary)){
            return RespBean.success("更新成功");
        }else{
            return RespBean.error("更新失败");
        }
    }

    @ApiOperation("删除工资账套信息")
    @DeleteMapping("/{id}")
    public RespBean updateSalary(@PathVariable Integer id){
        if(salaryService.removeById(id)){
            return RespBean.success("删除成功");
        }else{
            return RespBean.error("删除失败");
        }
    }

}
