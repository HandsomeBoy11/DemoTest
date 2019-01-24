package com.xrd.demotest;

import com.xrd.demotest.selectAttrs.Data;

import java.util.List;

/**
 * Created by WJ on 2019/1/10.
 */

public class ResponsBean {
   private String errorCode;
   private String state;
   private List<Data> dataList;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<Data> getDataList() {
        return dataList;
    }

    public void setDataList(List<Data> dataList) {
        this.dataList = dataList;
    }
}
