package com.bw.sho.bean;

import java.util.List;

/**
 * @Auther: 不懂
 * @Date: 2019/3/1 14:00:38
 * @Description:
 */
public class ThreeListinfo {


    /**
     * result : [{"commodityId":36,"commodityName":"女款 小密点布料休闲帆布鞋","masterPic":"http://172.17.8.100/images/small/commodity/nx/fbx/5/1.jpg","price":69,"saleNum":0},{"commodityId":33,"commodityName":"柔软舒适 女鞋 时尚百搭经典高帮休闲女帆布鞋","masterPic":"http://172.17.8.100/images/small/commodity/nx/fbx/2/1.jpg","price":119,"saleNum":0},{"commodityId":38,"commodityName":"明星同款舒适一脚蹬厚底增高休闲小白鞋 拼色圆头深口套脚帆布鞋女鞋","masterPic":"http://172.17.8.100/images/small/commodity/nx/fbx/7/1.jpg","price":480,"saleNum":0},{"commodityId":35,"commodityName":"唐狮女款小白鞋百搭秋季新款女鞋女款板鞋休闲鞋子帆布鞋女","masterPic":"http://172.17.8.100/images/small/commodity/nx/fbx/4/1.jpg","price":78,"saleNum":0},{"commodityId":32,"commodityName":"唐狮女鞋冬季女鞋休闲鞋子女士女鞋百搭帆布鞋女士休闲鞋子女款板鞋休闲女鞋帆布鞋","masterPic":"http://172.17.8.100/images/small/commodity/nx/fbx/1/1.jpg","price":88,"saleNum":0},{"commodityId":37,"commodityName":"轻便舒适 女鞋 简约百搭一脚蹬套脚女款板鞋休闲帆布鞋","masterPic":"http://172.17.8.100/images/small/commodity/nx/fbx/6/1.jpg","price":99,"saleNum":0},{"commodityId":34,"commodityName":"防滑耐磨 女款 时尚高帮帆布鞋","masterPic":"http://172.17.8.100/images/small/commodity/nx/fbx/3/1.jpg","price":109,"saleNum":0}]
     * message : 查询成功
     * status : 0000
     */

    private String message;
    private String status;
    private List<ResultBean> result;

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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * commodityId : 36
         * commodityName : 女款 小密点布料休闲帆布鞋
         * masterPic : http://172.17.8.100/images/small/commodity/nx/fbx/5/1.jpg
         * price : 69
         * saleNum : 0
         */

        private int commodityId;
        private String commodityName;
        private String masterPic;
        private int price;
        private int saleNum;

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

        public String getMasterPic() {
            return masterPic;
        }

        public void setMasterPic(String masterPic) {
            this.masterPic = masterPic;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getSaleNum() {
            return saleNum;
        }

        public void setSaleNum(int saleNum) {
            this.saleNum = saleNum;
        }
    }
}
