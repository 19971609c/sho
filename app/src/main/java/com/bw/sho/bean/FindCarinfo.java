package com.bw.sho.bean;

import org.greenrobot.greendao.annotation.Entity;

import java.util.List;

/**
 * @Auther: 不懂
 * @Date: 2019/3/21 10:26:07
 * @Description:
 */
public class FindCarinfo {

    /**
     * result : [{"commodityId":6,"commodityName":"轻柔系自然裸妆假睫毛","count":1,"pic":"http://mobile.bwstudent.com/images/small/commodity/mzhf/cz/4/1.jpg","price":39}]
     * message : 查询成功
     * status : 0000
     */

    private String message;
    private String status;
    private List<FindCarResclt> result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<FindCarResclt> getResult() {
        return result;
    }

    public void setResult(List<FindCarResclt> result) {
        this.result = result;
    }


/*    @Entity
    public static class ResultBean {
        *//**
         * commodityId : 6
         * commodityName : 轻柔系自然裸妆假睫毛
         * count : 1
         * pic : http://mobile.bwstudent.com/images/small/commodity/mzhf/cz/4/1.jpg
         * price : 39
         *//*

        private int commodityId;
        private String commodityName;
        private int count;
        private String pic;
        private int price;
        private boolean isCheck;


        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public int getCommodityId() {
            return commodityId;
        }

        public void setCommodityId(int commodityId) {
            this.commodityId = commodityId;
        }

        public String getCommodityName() {
            return commodityName;
        }

        public void setCommodityName(String commodityName) {
            this.commodityName = commodityName;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }*/
}
