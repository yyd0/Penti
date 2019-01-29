package com.yyd.penti.data.bean;

public class YiTu {
    private String imgUrl;
    private String desc;

    public YiTu(String imgUrl, String desc) {
        this.imgUrl = imgUrl;
        this.desc = desc;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
