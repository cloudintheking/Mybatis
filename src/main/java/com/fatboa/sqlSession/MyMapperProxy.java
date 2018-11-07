package com.fatboa.sqlSession;

import com.fatboa.config.Function;
import com.fatboa.config.MapperBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author: hl
 * @time: 2018/11/6 16:04
 * @description:
 */
public class MyMapperProxy implements InvocationHandler {
    private MySqlSession mySqlSession;
    private MyConfiguration myConfiguration;

    public MyMapperProxy(MySqlSession mySqlSession, MyConfiguration myConfiguration) {
        this.mySqlSession = mySqlSession;
        this.myConfiguration = myConfiguration;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MapperBean mapper = myConfiguration.readMapper("userMapper.xml");
        if (!method.getDeclaringClass().getName().equals(mapper.getInterfaceName())) {
            return null;
        }
        List<Function> list = mapper.getList();
        if (list != null && list.size() != 0) {
            for (Function fun : list) {
                if (method.getName().equals(fun.getFuncname())) {
                    return mySqlSession.selectOne(fun.getSql(), String.valueOf(args[0]));
                }
            }
        }
        return null;
    }
}
