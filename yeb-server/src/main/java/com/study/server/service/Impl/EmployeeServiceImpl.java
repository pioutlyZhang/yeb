package com.study.server.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.server.mapper.EmployeeMapper;
import com.study.server.mapper.MailLogMapper;
import com.study.server.pojo.*;
import com.study.server.service.IEmployeeService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pioutly
 * @since 2021-09-15
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MailLogMapper mailLogMapper;
    /**
     * 分页获取员工
     * @param currentPage
     * @param size
     * @param employee
     * @param beginDateScope
     * @return
     */
    @Override
    public RespPageBean getEmployee(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope) {
        Page<Employee> page = new Page<>(currentPage,size);
        IPage employeeByPage = employeeMapper.getEmployeeByPage(page, employee, beginDateScope);

        RespPageBean respPageBean = new RespPageBean(employeeByPage.getTotal(),employeeByPage.getRecords());
        return respPageBean;
    }

        /**
     * 获取工号
     * @return
     */
    @Override
    public RespBean getMaxWorkId() {
        List<Map<String, Object>> maps = employeeMapper.selectMaps(new QueryWrapper<Employee>()
                .select("max(workID)"));
        String s = maps.get(0).get("max(workID)").toString();
        return RespBean.success("获取成功",String.format("%08d",Integer.parseInt(s) + 1));

    }

    /**
     * 添加员工
     * @param emp
     * @return
     */
    @Override
    public RespBean addEmp(Employee emp) {
        LocalDate beginContract = emp.getBeginContract();
        LocalDate endContract = emp.getEndContract();
        long days = beginContract.until(endContract, ChronoUnit.DAYS);
        DecimalFormat decimalFormat = new DecimalFormat("##.00");
        emp.setContractTerm(Double.parseDouble(decimalFormat.format(days/365.00)));
        if(employeeMapper.insert(emp)==1){
            Employee employee = employeeMapper.getEmployeeById(emp.getId()).get(0);
            String mailId = UUID.randomUUID().toString() + emp.getId();
            MailLog mailLog = new MailLog();
            //数据库记录发送的消息
            mailLog.setMsgId(mailId);
            mailLog.setEid(emp.getId());
            mailLog.setStatus(MailConstants.DELIVERING);
            mailLog.setRouteKey(MailConstants.ROUTING_KEY_NAME);
            mailLog.setExchange(MailConstants.EXCHANGE_NAME);
            mailLog.setCount(0);
            mailLog.setTryTime(LocalDateTime.now().plusMinutes(MailConstants.MSG_TIMEOUT));
            mailLog.setCreateTime(LocalDateTime.now());
            mailLog.setUpdateTime(LocalDateTime.now());
            mailLogMapper.insert(mailLog);


            rabbitTemplate.convertAndSend(MailConstants.EXCHANGE_NAME
                    ,MailConstants.ROUTING_KEY_NAME,employee,new CorrelationData(mailId));
            return RespBean.success("插入成功");
        }else{
            return RespBean.error("插入失败");
        }
    }

    /**
     * 根据id获取员工
     * @param id
     * @return
     */
    @Override
    public List<Employee> getEmployeeById(Integer id) {
        return employeeMapper.getEmployeeById(id);
    }

    @Override
    public RespPageBean getEmpSalaries(Integer currentPage, Integer size) {
        Page<Employee> page = new Page<>(currentPage,size);
        IPage<Employee> employeePage = employeeMapper.getEmployeeWithSalary(page);
        RespPageBean respPageBean = new RespPageBean(employeePage.getTotal(),employeePage.getRecords());
        return respPageBean;
    }


}
