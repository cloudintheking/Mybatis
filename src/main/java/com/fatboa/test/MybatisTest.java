package com.fatboa.test;

import com.fatboa.bean.User;
import com.fatboa.mapper.UserMapper;
import com.fatboa.sqlSession.MySqlSession;

/**
 * @author: hl
 * @time: 2018/11/6 16:42
 * @description:
 */
public class MybatisTest {
    public static void main(String[] args) {
        synchronized (MybatisTest.class) {
            MySqlSession mySqlSession = new MySqlSession();
            UserMapper userMapper = mySqlSession.getMapper(UserMapper.class);
            User user = userMapper.getUserById("1");
            System.out.println(user);
        }

    }
}
