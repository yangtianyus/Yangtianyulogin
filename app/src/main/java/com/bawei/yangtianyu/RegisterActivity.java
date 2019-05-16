package com.bawei.yangtianyu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import android.widget.EditText;
import android.widget.Toast;

import com.bawei.yangtianyu.mvp.model.UserModelIml;
import com.bawei.yangtianyu.mvp.presenter.UserPresenterIml;
import com.bawei.yangtianyu.mvp.view.UserView;
import com.google.gson.Gson;



//注册页面
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, UserView {

    private EditText mUserName, mUserPass, mUserPass2;
    private UserPresenterIml mUserPresenterIml;
    private SharedPreferences sp;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViewById(R.id.btn_register).setOnClickListener(this);
        findViewById(R.id.tv_login).setOnClickListener(this);

        mUserName = (EditText) findViewById(R.id.user_name);
        mUserPass = (EditText) findViewById(R.id.user_pass);
        mUserPass2 = (EditText) findViewById(R.id.user_pass2);

        mUserPresenterIml = new UserPresenterIml(new UserModelIml(), this);

        sp = getSharedPreferences("user", MODE_PRIVATE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register://点击注册
                doRegister();
                break;
            case R.id.tv_login://已有账号，立即登录
                finish();
                break;
        }
    }

    //注册
    private void doRegister() {
        userName = mUserName.getText().toString().trim();
        String userPass = mUserPass.getText().toString().trim();
        String userPass2 = mUserPass2.getText().toString().trim();

        if (TextUtils.isEmpty(userName)) {
            toast("请输入您的账号");
            return;
        }
        if (TextUtils.isEmpty(userPass)) {
            toast("请输入您的密码");
            return;
        }
        if (TextUtils.isEmpty(userPass2)) {
            toast("请确认您的密码");
            return;
        }

        if (!userPass.equals(userPass2)) {
            toast("请检查您两次密码是否一致");
            return;
        }

        //走注册
        mUserPresenterIml.doRegister(userName, userPass);


    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void success(String data) {
        UserBean bean = new Gson().fromJson(data, UserBean.class);
        toast(bean.getMessage());

        sp.edit().putString("user_name", userName).commit();
        if (bean.getStatus().equals("0000")) {
            finish();
        }

    }

    @Override
    public void fail(String error) {
        toast(error);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUserPresenterIml.destory();
    }
}
