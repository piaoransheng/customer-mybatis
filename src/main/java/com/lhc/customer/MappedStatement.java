package com.lhc.customer;

import lombok.Data;

/**
 * 每一个sql的信息
 */
@Data
public class MappedStatement {
    private String nameSpace;
    private String id;
    private String resultType;
    private String sql;
}