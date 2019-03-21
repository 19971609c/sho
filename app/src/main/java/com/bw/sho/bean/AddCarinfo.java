package com.bw.sho.bean;

/**
 * @Auther: 不懂
 * @Date: 2019/3/20 19:46:21
 * @Description:
 */
public class AddCarinfo {

    private int commodityId;
    private int count;

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public AddCarinfo(int commodityId, int count) {
        this.commodityId = commodityId;
        this.count = count;
    }
}
