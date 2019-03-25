package com.bw.sho.bean;

/**
 * @Auther: 不懂
 * @Date: 2019/3/24 14:37:38
 * @Description:
 */
public class CreateOrder {
    private int commodityId;
    private int amount;

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public CreateOrder(int commodityId, int amount) {
        this.commodityId = commodityId;
        this.amount = amount;
    }
}
