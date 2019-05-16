package com.bawei.yangtianyu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.bawei.yangtianyu.mvp.model.UserModelIml;
import com.bawei.yangtianyu.mvp.presenter.UserPresenterIml;
import com.bawei.yangtianyu.mvp.view.UserView;
import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, UserView {

    private SharedPreferences sp;
    private EditText mUserName, mUserPass;
    private UserPresenterIml userPresenterIml;
    private CheckBox mCheckBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.tv_register).setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);

        mUserName = (EditText) findViewById(R.id.user_name);
        mUserPass = (EditText) findViewById(R.id.user_pass);
        mCheckBox = (CheckBox) findViewById(R.id.checkbox);

        sp = getSharedPreferences("user", MODE_PRIVATE);


        userPresenterIml = new UserPresenterIml(new UserModelIml(), this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String userName = sp.getString("user_name", null);
        String userPass = sp.getString("user_pass", null);
        if (!TextUtils.isEmpty(userName)) {
            mUserName.setText(userName);
        }

        if (!TextUtils.isEmpty(userPass)) {
            mUserPass.setText(userPass);
            mCheckBox.setChecked(true);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register://跳转到注册
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.btn_login://点击登录
                doLogin();
                break;
        }
    }

    //登录
    private String userName,userPass;
    private void doLogin() {
         userName = mUserName.getText().toString().trim();
         userPass = mUserPass.getText().toString().trim();

        if (TextUtils.isEmpty(userName)) {
            toast("请输入您的账号");
            return;
        }
        if (TextUtils.isEmpty(userPass)) {
            toast("请输入您的密码");
            return;
        }


        //走登录
        userPresenterIml.doLogin(userName, userPass);


    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void success(String data) {
        //toast(data);

        UserInFoBean bean = new Gson().fromJson(data, UserInFoBean.class);
        sp.edit().putString("user_name", bean.getResult().getPhone())
                .putString("user_nickName", bean.getResult().getNickName())
                .putString("user_pictrue", bean.getResult().getHeadPic())
                .commit();

        boolean isCheckBox = mCheckBox.isChecked();
        if (isCheckBox) {//保存
            sp.edit().putString("user_name", userName)
                    .putString("user_pass", userPass).commit();

        } else {//清空
            sp.edit().putString("user_name", "")
                    .putString("user_pass", "").commit();
        }
        //跳到用户信息展示页面
        startActivity(new Intent(LoginActivity.this, UserInFoActivity.class));
    }

    @Override
    public void fail(String error) {
        toast(error);
    }
}
