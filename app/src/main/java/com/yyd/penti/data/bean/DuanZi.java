package com.yyd.penti.data.bean;

import java.util.List;

public class DuanZi {
    private String msg;
    private int error;
    /**
     * title : 【段子】真正用灵魂在画画的人
     * link : http://www.dapenti.com/blog/more.asp?name=xilei&id=114735
     * author : xilei
     * pubDate : 2016-09-16 22:47:00
     * description : <a target="_blank" class="W_fb S_txt1" href="http://weibo.com/xia1205?refer_flag=0000015010_&amp;from=feed&amp;loc=nickname">@夏阿</a>&nbsp;：齐白石忌日，虽然他死去多年，拢共画了一万多张画，但被确定真迹的有四万多幅。市面上流通着四十万张以上的画，且时有新作。真正用灵魂在画画的人~
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
    }
}
