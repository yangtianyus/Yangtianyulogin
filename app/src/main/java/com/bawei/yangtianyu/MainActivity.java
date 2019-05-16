package com.bawei.yangtianyu;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.bawei.yangtianyu.mvp.presenter.UserPresenterIml;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences sp;
    private EditText mUserName, mUserPass;
    private UserPresenterIml userPresenterIml;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.tv_register).setOnClickListener(this);

        mUserName = (EditText) findViewById(R.id.user_name);
        mUserPass = (EditText) findViewById(R.id.user_pass);;



    }

    @Override
    public void onClick(View v) {

    }


}


