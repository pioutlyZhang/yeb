package com.study.server.controller;


import com.study.server.pojo.Position;
import com.study.server.pojo.RespBean;
import com.study.server.service.IPositionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
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
@RequestMapping("/system/basic/pos")
public class PositionController {
    @Autowired
    private IPositionService positionService;

    @ApiOperation("获取职位信息")
    @GetMapping("/")
    public List<Position> getAllPosition(){
        return positionService.list();
    }

    @ApiOperation("添加职位信息")
    @PostMapping("/")
    public RespBean addPosition(@RequestBody Position position){
        position.setCreateDate(LocalDateTime.now());
        if (positionService.save(position)) {
            return RespBean.success("保存成功");
        }else{
            return RespBean.error("保存失败");
        }
    }
    @ApiOperation("更新职位信息")
    @PutMapping("/")
    public RespBean updatePosition(@RequestBody Position position){
        if (positionService.updateById(position)) {
            return RespBean.success("更新成功");
        }else{
            return RespBean.error("更新失败");
        }
    }
    @ApiOperation("删除职位信息")
    @DeleteMapping("/{id}")
    public RespBean deletePosition(@PathVariable("id") Integer id){
        if (positionService.removeById(id)) {
            return RespBean.success("删除成功");
        }else{
            return RespBean.error("删除失败");
        }
    }
    @ApiOperation("批量删除职位信息")
    @DeleteMapping("/")
    public RespBean deletePosition( Integer[] ids){
        if (positionService.removeByIds(Arrays.asList(ids))) {
            return RespBean.success("删除成功");
        }else{
            return RespBean.error("删除失败");
        }
    }
}
