package com.fatboa.sqlSession;

import java.lang.reflect.Proxy;

/**
 * @author: hl
 * @time: 2018/11/6 15:41
 * @description:
 */
public class MySqlSession {
    private Excutor excutor = new MyExcutor();
    private MyConfiguration myConfiguration = new MyConfiguration();

    public <T> T selectOne(String sql, Object parameter) {
        return excutor.query(sql, parameter);
    }

    public <T> T getMapper(Class<T> clas) {
        return (T) Proxy.newProxyInstance(clas.getClassLoader(), new Class[]{clas}, new MyMapperProxy(this, myConfiguration));
    }
}
