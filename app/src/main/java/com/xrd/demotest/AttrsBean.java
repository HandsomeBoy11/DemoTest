package com.xrd.demotest;

import java.io.Serializable;
import java.util.List;

/**
 * Created by WJ on 2018/11/28.
 */

public class AttrsBean implements Serializable{
    private String imgPath;
    private String price;
    private String code;
    private List<AttrsChildBean> list;

    public AttrsBean(String imgPath, String price, String code, List<AttrsChildBean> list) {
        this.imgPath = imgPath;
        this.price = price;
        this.code = code;
        this.list = list;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<AttrsChildBean> getList() {
        return list;
    }

    public void setList(List<AttrsChildBean> list) {
        this.list = list;
    }
}
