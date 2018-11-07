package com.fatboa.sqlSession;

import com.fatboa.config.Function;
import com.fatboa.config.MapperBean;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author: hl
 * @time: 2018/11/6 14:38
 * @description: 读取与解析配置信息，并返回处理好的Environment
 */
public class MyConfiguration {
    private static ClassLoader loader = ClassLoader.getSystemClassLoader();

    public Connection build(String resource) throws Exception {
        try {
            InputStream stream = loader.getResourceAsStream(resource);
            SAXReader reader = new SAXReader();
            Document document = reader.read(stream);
            Element root = document.getRootElement();
            return evalDataSource(root);

        } catch (Exception e) {
            throw new RuntimeException("error occured evaling xml");
        }
    }

    public Connection evalDataSource(Element node) throws ClassNotFoundException {
        if (!node.getName().equals("database")) {
            throw new RuntimeException("root should be database");
        }
        String driverClassName = null;
        String url = null;
        String username = null;
        String password = null;

        //获取属性节点
        for (Object item : node.elements("property")) {
            Element i = (Element) item;
            String value = getValue(i);
            String name = i.attributeValue("name");
            switch (name) {
                case "url":
                    url = value;
                    break;
                case "username":
                    username = value;
                    break;
                case "password":
                    password = value;
                    break;
                case "driverClassName":
                    driverClassName = value;
                    break;
                default:
                    throw new RuntimeException("[database]:<property> unknown name");
            }
        }
        Class.forName(driverClassName);
        Connection connection = null;
        try {
            //建立数据库连接
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    //获取property属性的值，如果有value值，则读取，没有则读取内容
    private String getValue(Element node) {
        return node.hasContent() ? node.getText() : node.attributeValue("value");
    }

    //读取mapper
    public MapperBean readMapper(String path) {
        MapperBean mapper = new MapperBean();
        InputStream stream = loader.getResourceAsStream(path);
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(stream);
            Element root = document.getRootElement();
            mapper.setInterfaceName(root.attributeValue("nameSpace"));
            List<Function> list = new ArrayList<>();
            for (Iterator rootIter = root.elementIterator(); rootIter.hasNext(); ) {
                Function func = new Function();
                Element e = (Element) rootIter.next();
                String sqltype = e.getName().trim();
                String funcName = e.attributeValue("id").trim();
                String sql = e.getText().trim();
                String resultType = e.attributeValue("resultType").trim();
                func.setSql(sql);
                func.setFuncname(funcName);
                Object newInstance = null;
                try {
                    newInstance = Class.forName(resultType).newInstance();
                } catch (InstantiationException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
                func.setResultType(newInstance);
                func.setSqltype(sqltype);
                list.add(func);
            }
            mapper.setList(list);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return mapper;
    }

}
