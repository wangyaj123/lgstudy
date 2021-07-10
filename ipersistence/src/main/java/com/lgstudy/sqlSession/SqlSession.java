package com.lgstudy.sqlSession;

import java.sql.SQLException;
import java.util.List;

public interface SqlSession {
    //查询所有
     <E> List<E> selectList(String statementId, Object... params) throws SQLException;
    //查询一个
    <T> T selectOne(String statementId, Object... params) throws SQLException;
}
