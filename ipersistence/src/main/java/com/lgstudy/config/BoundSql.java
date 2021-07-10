package com.lgstudy.config;

import com.lgstudy.utils.ParameterMapping;

import java.util.ArrayList;
import java.util.List;

public class BoundSql {
    //解析出来的sql
    private String sqlText;
    //#{}里面的解析出来的参数名称
    private List<ParameterMapping> parameterMappingList = new ArrayList<ParameterMapping>();

    public BoundSql(String sqlText, List<ParameterMapping> parameterMappingList) {
        this.sqlText = sqlText;
        this.parameterMappingList = parameterMappingList;
    }

    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }

    public List<ParameterMapping> getParameterMappingList() {
        return parameterMappingList;
    }

    public void setParameterMappingList(List<ParameterMapping> parameterMappingList) {
        this.parameterMappingList = parameterMappingList;
    }
}
