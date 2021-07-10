package com.lgstudy.sqlSession;

import com.lgstudy.config.BoundSql;
import com.lgstudy.pojo.Configuration;
import com.lgstudy.pojo.MappedStatement;
import com.lgstudy.utils.GenericTokenParser;
import com.lgstudy.utils.ParameterMapping;
import com.lgstudy.utils.ParameterMappingTokenHandler;
import com.lgstudy.utils.TokenHandler;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleExecutor implements Executor {
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
       //1. 注册驱动，获取连接
        Connection connection = configuration.getDataSource().getConnection();
        //2. 获取sql语句 转换sql语句 需要对#{}里面的值进行解析存储
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);
        //3. 获取预处理对象 preparedStatement
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());
        //4.设置参数
            //获取参数的全路径
        String parameterType = mappedStatement.getParameterType();
        Class<?> parameterClassType = getClassType(parameterType);
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String content = parameterMapping.getContent();
            //反射
            Field declaredField = parameterClassType.getDeclaredField(content);
            //暴力访问
            declaredField.setAccessible(true);
            Object value = declaredField.get(params[0]);
            preparedStatement.setObject(i + 1, value);
        }
        //5. 执行sql
        ResultSet resultSet = preparedStatement.executeQuery();
        //6. 封装返回结果集
        String resultType = mappedStatement.getResultType();
        Class<?> classTypeClass = getClassType(resultType);

        ArrayList<Object> objectArrayList = new ArrayList<Object>();

        while (resultSet.next()){
            Object o = classTypeClass.newInstance();
            //元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                //字段名称
                String columnName = metaData.getColumnName(i);
                //字段值
                Object value = resultSet.getObject(columnName);
                //使用反射或内省，根据数据库表和实体的对应关系完成封装
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, classTypeClass);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(o, value);

            }
            objectArrayList.add(o);
        }
        return (List<E>) objectArrayList;
    }

    private Class<?> getClassType(String parameterType) throws ClassNotFoundException {
        if (parameterType != null){
            Class<?> aClass = Class.forName(parameterType);
            return aClass;
        }
        return null;
    }

    /**
     * 完成对#{}的解析工作，将#{}使用？进行代替 2. 解析出#{}里面的值进行存储
     * @param sql
     * @return
     */
    private BoundSql getBoundSql(String sql) {
        //标记处理类：配置标记解析器来完成对占位符的解析处理工作
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        String parseSql = genericTokenParser.parse(sql);
        //#{}里面解析出来的参数名和曾
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();
        return new BoundSql(parseSql, parameterMappings);
    }
}
