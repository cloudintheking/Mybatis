package com.fatboa.sqlSession;

/**
 * @author: hl
 * @time: 2018/11/6 15:41
 * @description:
 */
public interface Excutor {
    public <T> T query(String statement, Object parameter);
}
