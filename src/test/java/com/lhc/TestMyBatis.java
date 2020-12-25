package com.lhc;

import com.lhc.customer.session.SqlSession;
import com.lhc.customer.session.SqlSessionFactory;

public class TestMyBatis {
    public static void main(String[] args) {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSqlSession();
        System.out.println(sqlSession);
    }
}