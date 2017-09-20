package com.comba.android.combacommon.entity;

import java.io.Serializable;

/**
 * Created by chenhailin on 2017/5/19.
 */

public class ParamsBeans implements Serializable {
    private String key;//
    private String params; //文件时即为文件绝对路径
    private  boolean isFile;

    public boolean isFile() {
        return isFile;
    }

    public void setFile(boolean file) {
        isFile = file;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public ParamsBeans(String key, String params, boolean isFile) {
        this.key = key;
        this.params = params;
        this.isFile = isFile;
    }
}
