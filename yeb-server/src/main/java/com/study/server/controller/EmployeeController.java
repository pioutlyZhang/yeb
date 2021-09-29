package com.study.server.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.study.server.pojo.*;
import com.study.server.service.*;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
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
@RequestMapping("/employee/basic")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private IPoliticsStatusService politicsStatusService;

    @Autowired
    private IJoblevelService joblevelService;

    @Autowired
    private INationService nationService;

    @Autowired
    private IPositionService positionService;

    @GetMapping("/")
    @ApiOperation("获取所有员工")
    public RespPageBean getEmployee(@RequestParam(defaultValue = "1") Integer currentPage,
                                    @RequestParam(defaultValue = "10") Integer size,
                                    Employee employee,
                                    LocalDate[] beginDateScope){
        return employeeService.getEmployee(currentPage,size,employee,beginDateScope);
    }

    @GetMapping("/politicsstatus")
    @ApiOperation("获取所有政治面貌")
    public List<PoliticsStatus> getAllPoliticsStatus(){
        return politicsStatusService.list();
    }

    @GetMapping("/joblevels")
    @ApiOperation("获取所有职称")
    public List<Joblevel> getAllJoblevels(){
        return joblevelService.list();
    }

    @GetMapping("/nations")
    @ApiOperation("获取所有民族")
    public List<Nation> getAllNations(){
        return nationService.list();
    }

    @GetMapping("/positions")
    @ApiOperation("获取所有职位")
    public List<Position> getAllPositions(){
        return positionService.list();
    }

    @GetMapping("/maxWorkId")
    @ApiOperation("获取最大工号")
    public RespBean maxWorkId(){
        return employeeService.getMaxWorkId();
    }

    @PostMapping("/")
    @ApiOperation("添加员工")
    public RespBean addEmp(@RequestBody Employee emp){
        return employeeService.addEmp(emp);
    }
    @PutMapping("/")
    @ApiOperation("更新员工")
    public RespBean updateEmp(Employee emp){
        if (employeeService.updateById(emp)) {
            return RespBean.success("更新成功");
        }else{
            return RespBean.error("更新失败");
        }
    }
    @DeleteMapping("/{id}")
    @ApiOperation("删除员工")
    public RespBean deleteEmp(@PathVariable("id") Integer id){
        if (employeeService.removeById(id)) {
            return RespBean.success("删除成功");
        }else{
            return RespBean.error("删除失败");
        }
    }

    @GetMapping("/export")
    @ApiOperation(value = "导出员工数据",produces = "application/octet-stream")
    public void exportEmp(HttpServletResponse response){
        List<Employee> emp = employeeService.getEmployeeById(null);
        ExportParams params = new ExportParams("员工表","员工表", ExcelType.HSSF);
        Workbook sheets = ExcelExportUtil.exportExcel(params, Employee.class, emp);
        ServletOutputStream outputStream = null;
        try {
            //以流的方式传输
            response.setHeader("content-type","application/octet-stream");
            //防止中文乱码
            response.setHeader("content-disposition","attachment;filename=" + URLEncoder.encode(
                    "员工表.xls","UTF-8"));
            outputStream = response.getOutputStream();
            sheets.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(outputStream!=null){

                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }




}
