package com.lhc.customer;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 加载数据库连接信息和所有mapper.xml信息
 *
 * mybatis有两类配置文件：一个Mybatis-config.mxl和N个sql语句配置，都存放在这个配置文件里
 */
@Data
public class Configuration {
    private String jdbcDriver;
    private String jdbcUrl;
    private String jdbcUserName;
    private String jdbcUserPassword;

    //容器：存放所有sql语句配置
    private Map<String, MappedStatement> mappedStatementMap = new HashMap<String, MappedStatement>();
}