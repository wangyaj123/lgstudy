package com.lgstudy.pojo;

public class MappedStatement {
    //mapper.xml中的id
    private String id;
    //返回值类型
    private String resultType;
    //参数类型
    private String parameterType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }
}
