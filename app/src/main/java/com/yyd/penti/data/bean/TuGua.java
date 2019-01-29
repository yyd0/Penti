package com.yyd.penti.data.bean;

import java.util.List;

public class TuGua {
    private String msg;
    private int error;
    /**
     * title : 【喷嚏图卦20160916】很多时候一个导演找到编剧说，你给我对付个剧本出来吧
     * link : http://www.dapenti.com/blog/more.asp?name=xilei&id=114724
     * author : xilei
     * pubDate : 2016-09-16 14:29:00
     * description : https://dapenti.com/blog/readapp2.asp?name=xilei&id=114724
     * imgurl : http://pic.yupoo.com/dapenti/FR4wlxaR/medium.jpg
     * top_pic : null
     */

    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String title;
        private String description;
        private String imgurl;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }
    }
}
