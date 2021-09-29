package com.study.server.config;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.study.server.mapper.MailLogMapper;
import com.study.server.pojo.MailConstants;
import com.study.server.pojo.MailLog;
import com.study.server.service.IMailLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ配置类
 */
@Slf4j
@Configuration
public class RabbitMQConfig {

    @Autowired
    public CachingConnectionFactory cachingConnectionFactory;

    @Autowired
    public IMailLogService mailLogService;

    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        /**
         *  消息确认回调
         *  data :消息的唯一标识
         *  ack  :确认结果
         *  cause:回调原因
         */
        rabbitTemplate.setConfirmCallback((data,ack,cause) -> {
            String id = data.getId();
            if(ack){
                log.info("消息发送成功------>:{}",id);
                mailLogService.update(new UpdateWrapper<MailLog>().set("status",1).eq("msgId",1));
            }else{
                log.info("消息发送失败------>:{}",id);
            }
        });
        /**
         * 消息发送失败时的回调函数
         * msg：消息主键
         * repCode：响应码
         * repText：相应描述
         */
        rabbitTemplate.setReturnCallback((msg,repCode,repText,exchange,routingKey)->{
            log.info("消息发送queue时失败");
        });
        return rabbitTemplate;
    }

    @Bean("mailQueue")
    public Queue mailQueue(){
        return new Queue(MailConstants.QUEUE_NAME);
    }

    @Bean("mailExchange")
    public DirectExchange mailExchange(){
        return new DirectExchange(MailConstants.EXCHANGE_NAME);
    }

    @Bean
    public Binding queueBinding(@Qualifier("mailQueue") Queue queue,
                                @Qualifier("mailExchange") DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(MailConstants.ROUTING_KEY_NAME);
    }
}
