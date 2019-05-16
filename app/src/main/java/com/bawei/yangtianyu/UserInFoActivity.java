package com.bawei.yangtianyu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class UserInFoActivity extends AppCompatActivity {

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_in_fo);
        sp = getSharedPreferences("user", MODE_PRIVATE);
        String userName = sp.getString("user_name", null);
        String userNickName = sp.getString("user_nickName", null);
        String userPictrue = sp.getString("user_pictrue", null);

        ImageView imageView = (ImageView) findViewById(R.id.image_pictrue);
        TextView textView = (TextView) findViewById(R.id.tv_nickname);
        Glide.with(this).load(userPictrue).into(imageView);
        textView.setText(userNickName);

    }
}
