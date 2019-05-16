package com.bawei.yangtianyu.mvp.presenter;


import com.bawei.yangtianyu.mvp.model.UserModel;
import com.bawei.yangtianyu.mvp.view.UserView;

public class UserPresenterIml implements UserPresenter, UserModel.CallBackListener {

    private UserView userView;
    private UserModel userModel;

    public UserPresenterIml(UserModel userModel, UserView userView) {
        this.userModel = userModel;
        this.userView = userView;
    }

    //登录
    @Override
    public void doLogin(String phone, String pwd) {
        userModel.doLogin(phone, pwd, this);
    }

    //注册
    @Override
    public void doRegister(String phone, String pwd) {
        userModel.doRegister(phone, pwd, this);
    }

    @Override
    public void success(String data) {
        userView.success(data);
    }

    @Override
    public void fail(String error) {
        userView.fail(error);
    }

    public void destory() {
        if (userModel != null) {
            userModel = null;
        }
        if (userView != null) {
            userView = null;
        }
        System.gc();
    }
}
