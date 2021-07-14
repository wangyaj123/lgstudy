package com.lgstudy.test;

import com.lgstudy.dao.IUserDao;
import com.lgstudy.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class CacheTest {

    private IUserDao userMapper;
    private SqlSession sqlSession;
    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void before() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
         sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        sqlSession = sqlSessionFactory.openSession();
        userMapper = sqlSession.getMapper(IUserDao.class);

    }

    /**
     * 一级缓存 sqlsession级别的
     */
    @Test
    public void firstLevelCache(){
        // 第一次查询id为1的用户
        User user1 = userMapper.findUserById(1);

        //更新用户
        User user = new User();
        user.setId(1);
        user.setUsername("tom");
        userMapper.updateUser(user);
        sqlSession.commit();
        sqlSession.clearCache();

        // 第二次查询id为1的用户
        User user2 = userMapper.findUserById(1);


        System.out.println(user1==user2);
    }

    /**
     * 二级缓存：跨sqlsession级别的
     */
    @Test
    public void SecondLevelCache(){
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        SqlSession sqlSession3 = sqlSessionFactory.openSession();

        IUserDao mapper1 = sqlSession1.getMapper(IUserDao.class);
        IUserDao mapper2 = sqlSession2.getMapper(IUserDao.class);
        IUserDao mapper3 = sqlSession3.getMapper(IUserDao.class);

        User user1 = mapper1.findUserById(1);
        sqlSession1.close(); //清空一级缓存


        User user = new User();
        user.setId(1);
        user.setUsername("lisi");
        mapper3.updateUser(user);
        sqlSession3.commit();

        User user2 = mapper2.findUserById(1);
        //二级缓存 缓存的是数据而不是对象， 所以user1地址何user2地址不一样
        System.out.println(user1==user2);


    }


}
