package com.lhc.customer.session;

import com.lhc.customer.Configuration;

import java.util.List;

public class DefaultSqlSession implements SqlSession {
    private final Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    public <T> T selectOne(String statement, Object parameter) {
        return null;
    }

    public <E> List<E> selectList(String statement, Object parameter) {
        return null;
    }

    public <T> T getMapper(Class<T> type) {
        return null;
    }
}