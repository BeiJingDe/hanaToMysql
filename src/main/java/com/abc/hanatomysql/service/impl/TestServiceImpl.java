package com.abc.hanatomysql.service.impl;

import com.abc.hanatomysql.entity.Test;
import com.abc.hanatomysql.mapper.TestMapper;
import com.abc.hanatomysql.service.ITestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Z-7
 * @since 2022-08-18
 */
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements ITestService {

}
