package com.example.demo.demos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.demos.pojo.UserTest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserTestMapper extends BaseMapper<UserTest> {
    List<UserTest> selectUserTest();
}
