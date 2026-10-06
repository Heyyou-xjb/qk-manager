package com.itheima;

import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import org.apache.ibatis.type.JdbcType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

public class MpGenTest {

    public static void main(String[] args) {
        //代码生成脚手架：数据库连接与输出目录都从环境变量读取，避免把真实配置写进代码
        String url = System.getenv().getOrDefault("QK_DB_URL", "jdbc:mysql://localhost:3306/qk?serverTimezone=GMT%2B8");
        String username = System.getenv().getOrDefault("QK_DB_USERNAME", "root");
        String password = System.getenv().getOrDefault("QK_DB_PASSWORD", "");
        String outputDir = System.getenv().getOrDefault("QK_GEN_OUTPUT_DIR", "target/generated-code");

        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("niubi") // 设置作者
                            .outputDir(outputDir); // 输出目录
                })
                .dataSourceConfig(builder ->
                        builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                            // 兼容旧版本转换成Integer
                            if (JdbcType.TINYINT == metaInfo.getJdbcType()) {
                                return DbColumnType.INTEGER;
                            }
                            return typeRegistry.getColumnType(metaInfo);
                        })
                )
                .packageConfig(builder -> {
                    builder.parent("com.itheima") // 设置父包名
                            .entity("model.dataobject") // 设置实体类包名
                            .mapper("mapper") // 设置 Mapper 接口包名
                            .service("service") // 设置 Service 接口包名
                            .serviceImpl("service.impl") // 设置 Service 实现类包名
                            .xml("mappers"); // 设置 Mapper XML 文件包名
                })
                .strategyConfig(builder -> {
                    builder.addInclude("operate_log") // 设置需要生成的表名
                            .entityBuilder()
                            .formatFileName("%sDO")
                            .enableLombok() // 启用 Lombok
                            .serviceBuilder()
                            .formatServiceFileName("%sService")       // 接口：UserService
                            .formatServiceImplFileName("%sServiceImpl") // 实现类：UserServiceImpl
                            .controllerBuilder()
                            .enableRestStyle();
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用 Freemarker 模板引擎
                .execute(); // 执行生成
    }

}
