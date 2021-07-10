package com.lgstudy.sqlSession;

import com.lgstudy.config.BoundSql;
import com.lgstudy.pojo.Configuration;
import com.lgstudy.pojo.MappedStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SimpleExecutor implements Executor {
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException {
       //1. 注册驱动，获取连接
        Connection connection = configuration.getDataSource().getConnection();
        //2. 获取sql语句 转换sql语句 需要对#{}里面的值进行解析存储
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);
        PreparedStatement preparedStatement = connection.prepareStatement(mappedStatement.getSql());
        return null;
    }

    /**
     * 完成对#{}的解析工作，将#{}使用？进行代替 2. 解析出#{}里面的值进行存储
     * @param sql
     * @return
     */
    private BoundSql getBoundSql(String sql) {

        return null;
    }
}
