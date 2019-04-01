package com.bw.sho.bean;

/**
 * @Auther: 不懂
 * @Date: 2019/3/29 13:31:45
 * @Description:
 */
public class OrderPagerinfo {

    private int commodityId;
    private String commodityName;
    private int price;
    private String imageurl;
    private int count;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public OrderPagerinfo(int commodityId, String commodityName, int price, String imageurl, int count) {
        this.commodityId = commodityId;
        this.commodityName = commodityName;
        this.price = price;
        this.imageurl = imageurl;
        this.count = count;
    }
}
