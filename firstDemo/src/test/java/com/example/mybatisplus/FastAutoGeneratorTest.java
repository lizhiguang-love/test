package com.example.mybatisplus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;

public class FastAutoGeneratorTest {
    public static void main(String[] args) {
        //需要构建一个 代码自动生成器 对象
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        //配置策略

        //1、全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");  // 获取当前用户下的目录
        gc.setOutputDir(projectPath + "/projectDemo/src/main/java");  // 输出到指定的目录中
        gc.setAuthor("lizhiguang");
        gc.setOpen(false);
        gc.setFileOverride(false);  //是否覆盖之前生成的文件
        gc.setServiceName("%sService"); //%sService用正则表达式去Service的I前缀
        gc.setIdType(IdType.AUTO); // 设置数据库主键类型
        gc.setDateType(DateType.ONLY_DATE); // 设置日期类型
        gc.setSwagger2(true); // 是否配置Swagger文档
        mpg.setGlobalConfig(gc);

        //2、设置数据源
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://192.168.119.128:3306/gulimall?useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        //3、包的配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.example.demo.demos"); // 设置包名
        pc.setEntity("pojo"); // 设置实体类包名
        pc.setMapper("mapper"); // 设置mapper包名
        pc.setService("service"); // 设置service包名
        pc.setController("controller"); // 设置controller包名
        mpg.setPackageInfo(pc);

        //4、策略配置
        StrategyConfig strategy = new StrategyConfig();
         strategy.setInclude("user"); // 设置要映射的表名
//        strategy.setInclude("user","user_info","user_info2","user_info3","user_info4"); // 设置要映射的表名，多表
        strategy.setNaming(NamingStrategy.underline_to_camel); // 设置表名字下划线转驼峰命名规则
        strategy.setColumnNaming(NamingStrategy.underline_to_camel); // 设置表字段名下划线转驼峰命名规则
        strategy.setEntityLombokModel(true);    // 是否开启Lombok注解编程
        strategy.setLogicDeleteFieldName("deleted");  // 设置逻辑删除字段名称
        //自动填充配置
        TableFill gmtCreate = new TableFill("gmt_create", FieldFill.INSERT);
        TableFill gmtModified = new TableFill("gmt_modified", FieldFill.INSERT_UPDATE);
        ArrayList<TableFill> tableFills = new ArrayList<>();
        tableFills.add(gmtCreate);
        tableFills.add(gmtModified);
        strategy.setTableFillList(tableFills);
        // 设置乐观锁字段名称
        strategy.setVersionFieldName("version");
        strategy.setRestControllerStyle(true); // 开启Controller驼峰命名规则
        // strategy.setControllerMappingHyphenStyle(true);  // 开启Controller中请求的驼峰命名规则。多个字段时，例如：http://localhost:8080/hello_id_2
        mpg.setStrategy(strategy);

        mpg.execute();  //执行代码构造器
    }
}
