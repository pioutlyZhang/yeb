package com.study.server.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.server.pojo.Employee;
import com.study.server.pojo.MailConstants;
import com.study.server.pojo.MailLog;
import com.study.server.service.IEmployeeService;
import com.study.server.service.IMailLogService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class MailTask {

    @Autowired
    private IMailLogService mailLogService;

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 邮件发送定时任务
     * 每十秒执行一次
     */
    @Scheduled(cron = "*/10 * * * * ?")
    public void mailTask(){
        List<MailLog> list = mailLogService.list(new QueryWrapper<MailLog>().eq("status", 0).lt("tryTime",
                LocalDateTime.now()));
        for(MailLog mailLog : list){
            //如果重试次数大于等于三次，标记为投递失败，不在尝试
            if(mailLog.getCount()>=3){
                mailLog.setStatus(2);
                mailLogService.updateById(mailLog);
            }else{
                mailLog.setCount(mailLog.getCount()+1);
                mailLog.setUpdateTime(LocalDateTime.now());
                mailLog.setTryTime(LocalDateTime.now().plusMinutes(MailConstants.MSG_TIMEOUT));
                mailLogService.updateById(mailLog);
                Employee employee = employeeService.getEmployeeById(mailLog.getEid()).get(0);
//              重新发送消息
                rabbitTemplate.convertAndSend(MailConstants.EXCHANGE_NAME,MailConstants.ROUTING_KEY_NAME
                        ,employee,new CorrelationData(mailLog.getMsgId()));

            }
        }

    }

}
