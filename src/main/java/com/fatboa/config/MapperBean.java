package com.fatboa.config;

import java.util.List;

/**
 * @author: hl
 * @time: 2018/11/6 15:18
 * @description:
 */
public class MapperBean {
    /**
     * 接口名
     */
    private String interfaceName;
    /**
     * 接口下所以方法
     */
    private List<Function> list;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public List<Function> getList() {
        return list;
    }

    public void setList(List<Function> list) {
        this.list = list;
    }
}
