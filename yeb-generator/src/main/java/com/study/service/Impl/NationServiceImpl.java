package com.study.service.Impl;

import com.study.pojo.Nation;
import com.study.mapper.NationMapper;
import com.study.service.INationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pioutly
 * @since 2021-09-15
 */
@Service
public class NationServiceImpl extends ServiceImpl<NationMapper, Nation> implements INationService {

}
