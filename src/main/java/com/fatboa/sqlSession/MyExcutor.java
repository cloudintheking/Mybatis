package com.fatboa.sqlSession;

import com.fatboa.bean.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author: hl
 * @time: 2018/11/6 15:43
 * @description:
 */
public class MyExcutor implements Excutor {
    private MyConfiguration xmlConfiguration = new MyConfiguration();

    @Override
    public <T> T query(String sql, Object parameter) {
        Connection connection = null;
        PreparedStatement pre = null;
        ResultSet set = null;
        try {
            connection = getConnection();
            pre = connection.prepareStatement(sql);
            //设置参数
            pre.setString(1, parameter.toString());
            set = pre.executeQuery();
            // set = pre.executeQuery();
            User u = new User();
            while (set.next()) {
                u.setId(set.getString(1));
                u.setUsername(set.getString(2));
                u.setPassword(set.getString(3));
            }
            return (T) u;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (set != null) {
                    set.close();
                }
                if (pre != null) {
                    pre.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    private Connection getConnection() {
        Connection connection = null;
        try {
            connection = xmlConfiguration.build("config.xml");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
