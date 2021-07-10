package com.lgstudy.sqlSession;


import com.lgstudy.pojo.Configuration;

import java.sql.SQLException;
import java.util.List;

public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    public <E> List<E> selectList(String statementId, Object... params) throws SQLException {
        //将要区完成对simpleExecutor里的query方法的调用
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        return simpleExecutor.query(configuration, configuration.getMappedStatementMap().get(statementId), params);
    }

    public <T> T selectOne(String statementId, Object... params) throws SQLException {
        List<Object> objects = selectList(statementId, params);
        if (objects.size() == 1){
            return (T) objects.get(0);
        }else {
            throw new RuntimeException("查询结果为空或返回结果过多");
        }
    }

}
