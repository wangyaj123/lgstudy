package com.lgstudy.config;

import com.lgstudy.pojo.Configuration;
import com.lgstudy.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

public class XmlMapperBuilder {
    private Configuration configuration;

    public XmlMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }
    public void parse(InputStream in) throws DocumentException {
        Document mapperDoc = new SAXReader().read(in);
        //<mapper>
        Element mapperDocRootElement = mapperDoc.getRootElement();
        String namespase = mapperDocRootElement.attributeValue("namespase");
        List<Element> selectNodes = mapperDocRootElement.selectNodes("//select");
        for (Element selectElement : selectNodes) {
            String id = selectElement.attributeValue("id");
            String resultType = selectElement.attributeValue("resultType");
            String parameterType = selectElement.attributeValue("parameterType");
            String sql = selectElement.getTextTrim();
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setParameterType(parameterType);
            mappedStatement.setResultType(resultType);
            mappedStatement.setSql(sql);
            configuration.getMappedStatementMap().put(namespase + "." + id, mappedStatement);
        }
    }
}
