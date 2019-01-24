package com.xrd.demotest.selectAttrs;

import java.util.List;

/**
 * Created by WJ on 2019/1/2.
 */

public class Data {
    private String name;
    private List<DataChild> list;
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DataChild> getList() {
        return list;
    }

    public void setList(List<DataChild> list) {
        this.list = list;
    }
}
