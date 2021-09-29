package com.study.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公共返回对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespBean {
    private Integer code;
    private String message;
    private Object data;

    /**
     * 成功后返回信息
     * @param msg
     * @return
     */
    public static RespBean success(String msg){
        return new RespBean(200,msg,null);
    }
    /**
     * 成功后返回信息和数据
     * @param msg
     * @return
     */
    public static RespBean success(String msg,Object data){
        return new RespBean(200,msg,data);
    }
    /**
     * 失败后返回信息
     * @param msg
     * @return
     */
    public static RespBean error(String msg){
        return new RespBean(500,msg,null);
    }
    /**
     * 失败后返回信息和数据
     * @param msg
     * @return
     */
    public static RespBean error(String msg,Object data){
        return new RespBean(500,msg,data);
    }

}
