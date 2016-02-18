package com.channelsoft.sample.model.user;

import cn.bmob.v3.BmobObject;

/**
 * Created by 王宗贤 on 2015/12/21.
 */
public class UserInfo extends BmobObject {
    private String path;
    private String name;
    private String sex;
    private String age;
    private String job;
    private String tel;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public String getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
