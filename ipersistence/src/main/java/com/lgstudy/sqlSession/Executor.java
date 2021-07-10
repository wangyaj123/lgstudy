package com.lgstudy.sqlSession;

import com.lgstudy.pojo.Configuration;
import com.lgstudy.pojo.MappedStatement;

import java.beans.IntrospectionException;
import java.sql.SQLException;
import java.util.List;

public interface Executor {
    <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException, InstantiationException, IntrospectionException, Exception;
}
