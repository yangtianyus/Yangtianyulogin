package com.bawei.yangtianyu.net;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



//网络工具类
public class OkHttpUtils {

    private int HTTP_SUCCESS = 1000;

    private int HTTP_FAIL = 1001;
    private HttpListener mHttpListener;


    public OkHttpUtils get(String url) {
        doHttp(url, 0, null);
        return this;
    }

    public OkHttpUtils post(String url, FormBody.Builder bodyBuilder) {
        doHttp(url, 1, bodyBuilder);
        return this;
    }

    //网络请求
    private void doHttp(String url, int type, FormBody.Builder bodyBuilder) {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Log.i("intercept", request.url().host());
                return chain.proceed(request);
            }
        }).build();
        Request.Builder btilder = new Request.Builder();
        if (type == 0) {
            btilder.get();
        } else {
            btilder.post(bodyBuilder.build());
        }
        btilder.url(url);
        Request request = btilder.build();

        final Message message = Message.obtain();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                message.obj = e.getMessage();
                message.what = HTTP_FAIL;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                message.obj = response.body().string();
                message.what = HTTP_SUCCESS;
                handler.sendMessage(message);
            }
        });

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == HTTP_SUCCESS) {
                String data = (String) msg.obj;
                mHttpListener.success(data);
            } else {
                String error = (String) msg.obj;
                mHttpListener.fail(error);
            }

        }
    };

    //传递接口
    public void result(HttpListener mHttpListener) {
        this.mHttpListener = mHttpListener;
    }

    public interface HttpListener {
        void success(String data);

        void fail(String error);
    }
}
