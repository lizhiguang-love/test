package com.example.demo.lzg.service.impl;

import com.example.demo.lzg.pojo.User;
import com.example.demo.lzg.mapper.UserMapper;
import com.example.demo.lzg.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lizhiguang
 * @since 2023-05-30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
