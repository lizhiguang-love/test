package com.example.demo.lzg.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "userLzg")
public class UserTest {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private String name;
    private Integer age;
    private String email;
}
