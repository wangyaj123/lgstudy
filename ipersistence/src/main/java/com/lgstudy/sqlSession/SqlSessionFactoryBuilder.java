package com.lgstudy.sqlSession;

import com.lgstudy.config.XmlConfigBuilder;
import com.lgstudy.pojo.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

public class SqlSessionFactoryBuilder {
    public SqlSessionFactory build(InputStream in) throws PropertyVetoException, DocumentException {
        //1. 使用dom4j解析配置文件，将解析出来的内容封装到Configuration
        XmlConfigBuilder xmlConfigBuilder = new XmlConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parseConfig(in);

        //2. 创建sqlSessionFactory对象 工厂类 生产sqlSession 会话对象
        DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(configuration);

        return defaultSqlSessionFactory;
    }
}
