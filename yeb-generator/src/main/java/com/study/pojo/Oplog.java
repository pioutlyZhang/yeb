package com.study.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author pioutly
 * @since 2021-09-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_oplog")
@ApiModel(value="Oplog对象", description="")
public class Oplog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "添加日期")
    private LocalDate addDate;

    @ApiModelProperty(value = "操作内容")
    private String operate;

    @ApiModelProperty(value = "操作员ID")
    private Integer adminid;


}
