package com.bw.sho.bean;

import java.util.List;

/**
 * @Auther: 不懂
 * @Date: 2019/3/28 11:54:59
 * @Description:
 */
public class Wallerinfo {

    /**
     * result : {"balance":99955889,"detailList":[{"amount":278,"consumerTime":1553824455000,"orderId":"20190329095409513471","userId":471},{"amount":79,"consumerTime":1553781760000,"orderId":"20190328220237254471","userId":471},{"amount":37194,"consumerTime":1553781676000,"orderId":"20190328220112421471","userId":471},{"amount":6199,"consumerTime":1553781638000,"orderId":"20190328220034614471","userId":471},{"amount":47,"consumerTime":1553780526000,"orderId":"20190328214203238471","userId":471},{"amount":78,"consumerTime":1553780477000,"orderId":"20190328211631953471","userId":471},{"amount":39,"consumerTime":1553780460000,"orderId":"20190328211802428471","userId":471},{"amount":19,"consumerTime":1553780420000,"orderId":"20190328213804585471","userId":471},{"amount":19,"consumerTime":1553780273000,"orderId":"20190328213741827471","userId":471},{"amount":158,"consumerTime":1553779184000,"orderId":"20190328211941269471","userId":471}]}
     * message : 查询成功
     * status : 0000
     */

    private ResultBean result;
    private String message;
    private String status;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

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

    public static class ResultBean {
        /**
         * balance : 99955889
         * detailList : [{"amount":278,"consumerTime":1553824455000,"orderId":"20190329095409513471","userId":471},{"amount":79,"consumerTime":1553781760000,"orderId":"20190328220237254471","userId":471},{"amount":37194,"consumerTime":1553781676000,"orderId":"20190328220112421471","userId":471},{"amount":6199,"consumerTime":1553781638000,"orderId":"20190328220034614471","userId":471},{"amount":47,"consumerTime":1553780526000,"orderId":"20190328214203238471","userId":471},{"amount":78,"consumerTime":1553780477000,"orderId":"20190328211631953471","userId":471},{"amount":39,"consumerTime":1553780460000,"orderId":"20190328211802428471","userId":471},{"amount":19,"consumerTime":1553780420000,"orderId":"20190328213804585471","userId":471},{"amount":19,"consumerTime":1553780273000,"orderId":"20190328213741827471","userId":471},{"amount":158,"consumerTime":1553779184000,"orderId":"20190328211941269471","userId":471}]
         */

        private int balance;
        private List<DetailListBean> detailList;

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

        public List<DetailListBean> getDetailList() {
            return detailList;
        }

        public void setDetailList(List<DetailListBean> detailList) {
            this.detailList = detailList;
        }

        public static class DetailListBean {
            /**
             * amount : 278
             * consumerTime : 1553824455000
             * orderId : 20190329095409513471
             * userId : 471
             */

            private int amount;
            private long consumerTime;
            private String orderId;
            private int userId;

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public long getConsumerTime() {
                return consumerTime;
            }

            public void setConsumerTime(long consumerTime) {
                this.consumerTime = consumerTime;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }
        }
    }
}
