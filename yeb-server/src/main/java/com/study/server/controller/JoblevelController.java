package com.study.server.controller;


import com.study.server.pojo.Joblevel;
import com.study.server.pojo.Position;
import com.study.server.pojo.RespBean;
import com.study.server.service.IJoblevelService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
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
@RequestMapping("/system/basic/joblevel")
public class JoblevelController {

    @Autowired
    private IJoblevelService joblevelService;

    @ApiOperation("获取职位信息")
    @GetMapping("/")
    public List<Joblevel> getAllJoblevel(){
        return joblevelService.list();
    }

    @ApiOperation("添加职位信息")
    @PostMapping("/")
    public RespBean addJoblevel(@RequestBody Joblevel joblevel){
        joblevel.setCreateDate(LocalDateTime.now());
        if (joblevelService.save(joblevel)) {
            return RespBean.success("保存成功");
        }else{
            return RespBean.error("保存失败");
        }
    }
    @ApiOperation("更新职位信息")
    @PutMapping("/")
    public RespBean updateJoblevel(@RequestBody Joblevel joblevel){
        if (joblevelService.updateById(joblevel)) {
            return RespBean.success("更新成功");
        }else{
            return RespBean.error("更新失败");
        }
    }
    @ApiOperation("删除职位信息")
    @DeleteMapping("/{id}")
    public RespBean deleteJoblevel(@PathVariable("id") Integer id){
        if (joblevelService.removeById(id)) {
            return RespBean.success("删除成功");
        }else{
            return RespBean.error("删除失败");
        }
    }
    @ApiOperation("批量删除职位信息")
    @DeleteMapping("/")
    public RespBean deleteJoblevel( Integer[] ids){
        if (joblevelService.removeByIds(Arrays.asList(ids))) {
            return RespBean.success("删除成功");
        }else{
            return RespBean.error("删除失败");
        }
    }
}
