package com.lgstudy.test;

import com.lgstudy.io.Resources;
import com.lgstudy.pojo.User;
import com.lgstudy.sqlSession.SqlSession;
import com.lgstudy.sqlSession.SqlSessionFactory;
import com.lgstudy.sqlSession.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;


public class IPersistenceTest {
    @Test
    public void test() throws Exception {
        InputStream resourceAsSteam = Resources.getResourceAsSteam("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsSteam);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<User> userList = sqlSession.selectList("user.selectList", new User(1, "zhangsan"));
        for (User user: userList){
            System.out.println(user.toString());
        }
    }
}
