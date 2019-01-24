package com.xrd.demotest;

import java.util.List;

/**
 * Created by WJ on 2018/11/28.
 */

public class AttrsChildBean {
    private int type;
    private String typeName;
    private List<Bean> childList;

    public AttrsChildBean(int type, String typeName, List<Bean> childList) {
        this.type = type;
        this.typeName = typeName;
        this.childList = childList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<Bean> getChildList() {
        return childList;
    }

    public void setChildList(List<Bean> childList) {
        this.childList = childList;
    }
}
