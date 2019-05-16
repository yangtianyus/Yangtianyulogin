package com.bawei.yangtianyu.mvp.model;

public interface UserModel {

    interface CallBackListener {
        void success(String data);

        void fail(String error);
    }

    //登录
    void doLogin(String phone, String pwd, CallBackListener listener);

    //注册
    void doRegister(String phone, String pwd, CallBackListener listener);

    //修改昵称
    //
}
