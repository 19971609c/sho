package com.bw.sho.bean;

import java.util.List;

/**
 * @Auther: 不懂
 * @Date: 2019/3/1 14:00:38
 * @Description:
 */
public class TwoListinfo {

    /**
     * result : [{"id":"1001008001","name":"手机"},{"id":"1001008002","name":"手机配件"},{"id":"1001008003","name":"照相机"},{"id":"1001008004","name":"影音娱乐"},{"id":"1001008005","name":"智能设备"}]
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
         * id : 1001008001
         * name : 手机
         */

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
