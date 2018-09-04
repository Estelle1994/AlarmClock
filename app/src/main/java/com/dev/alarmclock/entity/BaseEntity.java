package com.dev.alarmclock.entity;

import java.util.List;

/**
 * Created by ${Estelle} on 2018/6/22.
 */

public class BaseEntity {

    /**
     * code : 200
     * msg : OK
     * muser : [{"name":"zhangsan","age":"10","phone":"11111","email":"11111@11.com"},{"name":"lisi","age":"20","phone":"22222","email":"22222@22.com"},"..."]
     */

    private int code;
    private String msg;
    private List<MuserBean> muser;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<MuserBean> getMuser() {
        return muser;
    }

    public void setMuser(List<MuserBean> muser) {
        this.muser = muser;
    }

    public static class MuserBean {
        /**
         * name : zhangsan
         * age : 10
         * phone : 11111
         * email : 11111@11.com
         */

        private String name;
        private String age;
        private String phone;
        private String email;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
