package com.bawei.yangtianyu.mvp.model;


import com.bawei.yangtianyu.net.OkHttpUtils;

import okhttp3.FormBody;


public class UserModelIml implements UserModel {

    //登录
    @Override
    public void doLogin(String phone, String pwd, final CallBackListener listener) {
        //走注册
        String registerUrl = "http://172.17.8.100/small/user/v1/login";
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("phone", phone);
        builder.add("pwd", pwd);
        new OkHttpUtils().post(registerUrl, builder).result(new OkHttpUtils.HttpListener() {
            @Override
            public void success(String data) {
                listener.success(data);
            }

            @Override
            public void fail(String error) {
                listener.fail(error);
            }
        });
    }

    //注册
    @Override
    public void doRegister(String phone, String pwd, final CallBackListener listener) {
        //走注册
        String registerUrl = "http://172.17.8.100/small/user/v1/register";
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("phone", phone);
        builder.add("pwd", pwd);
        new OkHttpUtils().post(registerUrl, builder).result(new OkHttpUtils.HttpListener() {
            @Override
            public void success(String data) {
                listener.success(data);
            }

            @Override
            public void fail(String error) {
                listener.fail(error);
            }
        });
    }
}
