package com.lgstudy.test;

import com.lgstudy.io.Resources;
import com.lgstudy.pojo.User;
import com.lgstudy.sqlSession.SqlSession;
import com.lgstudy.sqlSession.SqlSessionFactory;
import com.lgstudy.sqlSession.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

public class Test {
    public void test() throws Exception {
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapperConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<User> userList = sqlSession.selectList("user.selectList", new User(1, "zhangsan"));    }
}
