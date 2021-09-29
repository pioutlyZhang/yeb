package com.study.server.exception;

import com.study.server.pojo.RespBean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice   //统一异常处理
public class GlobalException {
    @ExceptionHandler(SQLException.class)
    public RespBean mysqlException(SQLException e){
        if(e instanceof SQLIntegrityConstraintViolationException){
            return RespBean.error("该数据有外键关联");
        }
        return RespBean.error("数据库发生异常");
    }
}
