package com.bw.sho.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Auther: 不懂
 * @Date: 2019/3/22 13:50:11
 * @Description:
 */

@Entity
public class FindCarResclt {
    /**
     * commodityId : 6
     * commodityName : 轻柔系自然裸妆假睫毛
     * count : 1
     * pic : http://mobile.bwstudent.com/images/small/commodity/mzhf/cz/4/1.jpg
     * price : 39
     */

    private int commodityId;
    private String commodityName;
    private int count;
    private String pic;
    private int price;
    private boolean isCheck;

    public boolean getIsCheck() {
        return this.isCheck;
    }
    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }
    public int getPrice() {
        return this.price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public String getPic() {
        return this.pic;
    }
    public void setPic(String pic) {
        this.pic = pic;
    }
    public int getCount() {
        return this.count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public String getCommodityName() {
        return this.commodityName;
    }
    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }
    public int getCommodityId() {
        return this.commodityId;
    }
    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }
    @Generated(hash = 738083791)
    public FindCarResclt(int commodityId, String commodityName, int count,
            String pic, int price, boolean isCheck) {
        this.commodityId = commodityId;
        this.commodityName = commodityName;
        this.count = count;
        this.pic = pic;
        this.price = price;
        this.isCheck = isCheck;
    }
    @Generated(hash = 1398606850)
    public FindCarResclt() {
    }

}
