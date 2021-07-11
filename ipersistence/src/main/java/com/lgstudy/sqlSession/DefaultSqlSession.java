package com.lgstudy.sqlSession;


import com.lgstudy.pojo.Configuration;

import java.lang.reflect.*;
import java.sql.SQLException;
import java.util.List;

public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    public <E> List<E> selectList(String statementId, Object... params) throws Exception {
        //将要区完成对simpleExecutor里的query方法的调用
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        return simpleExecutor.query(configuration, configuration.getMappedStatementMap().get(statementId), params);
    }

    public <T> T selectOne(String statementId, Object... params) throws Exception {
        List<Object> objects = selectList(statementId, params);
        if (objects.size() == 1){
            return (T) objects.get(0);
        }else {
            throw new RuntimeException("查询结果为空或返回结果过多");
        }
    }

    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        //使用JDK动态代理为Dao接口生成代理对象，并返回
        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //底层还是执行JDBC代码 根据不同的情况 来调用不同的方法
                //参数：statementId= namespace.id = 类的全限定名.方法名
                //方法名
                String methodName = method.getName();
                //类的全限定名
                String className = method.getDeclaringClass().getName();
                //方法的返回值类型
                Type genericReturnType = method.getGenericReturnType();
                //判断是否进行了泛型类型参数化
                if(genericReturnType instanceof ParameterizedType){
                    return selectList(className + "." + methodName, args);
                }
                return selectOne(className + "." + methodName, args);
            }
        });
        return (T) proxyInstance;
    }

}
