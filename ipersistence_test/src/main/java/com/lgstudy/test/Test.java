package com.lgstudy.test;

import com.lgstudy.io.Resources;

import java.io.InputStream;

public class Test {
    public void test(){
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapperConfig.xml");
    }
}
