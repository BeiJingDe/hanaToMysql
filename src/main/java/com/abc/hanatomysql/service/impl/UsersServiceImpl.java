package com.abc.hanatomysql.service.impl;

import com.abc.hanatomysql.entity.Users;
import com.abc.hanatomysql.mapper.UsersMapper;
import com.abc.hanatomysql.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Z-7
 * @since 2022-08-19
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

}
