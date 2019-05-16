package com.bawei.yangtianyu.mvp.presenter;


public interface UserPresenter {

    //登录
    void doLogin(String phone, String pwd);

    //注册
    void doRegister(String phone, String pwd);
}
