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
@TableName("t_salary_adjust")
@ApiModel(value="SalaryAdjust对象", description="")
public class SalaryAdjust implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "员工ID")
    private Integer eid;

    @ApiModelProperty(value = "调薪日期")
    private LocalDate asDate;

    @ApiModelProperty(value = "调前薪资")
    private Integer beforeSalary;

    @ApiModelProperty(value = "调后薪资")
    private Integer afterSalary;

    @ApiModelProperty(value = "调薪原因")
    private String reason;

    @ApiModelProperty(value = "备注")
    private String remark;


}
