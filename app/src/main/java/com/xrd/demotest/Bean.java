package com.xrd.demotest;

/**
 * Created by WJ on 2018/11/28.
 */

public class Bean {
    private String name;
    private int type;

    public Bean(int type, String name) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
