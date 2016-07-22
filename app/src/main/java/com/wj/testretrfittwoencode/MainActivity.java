package com.wj.testretrfittwoencode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
//        initRequest();
        initRetrofit();
        String data = "12321312312";
        String encrypt = CryptoUtils.EncryptWithPadding(data);
        String decrypt = CryptoUtils.DecryptWithPadding(encrypt);

        ((TextView) findViewById(R.id.hello)).setText(
                data + "\n"
                + "加密后:" + encrypt + "\n"
                + "解密后:" + decrypt);
    }

//    private void initRequest() {
//        //请求
//        SelectPlotBizApi selectPlotBizApi= HttpHelper.createApi(SelectPlotBizApi.class);
//        selectPlotBizApi.getXiaoQuLiBiaoResulttwo(HttpHelper.getUrlPostFix(), object.toString()) //后缀，json
//                .subscribeOn(Schedulers.io())//执行在io线程
//                .observeOn(AndroidSchedulers.mainThread())//主线程显示
//                .subscribe(new Subscriber<PageBean>() {
//                    @Override
//                    public void onCompleted() {
//                        Log.i("xiaozhang", "执行完成：");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.i("xiaozhang", "for执行失败：" + e);
//                    }
//
//                    @Override
//                    public void onNext(PageBean pageBean) {
//                        Log.i("xiaozhang", "线程中执行的,数据为" + pageBean.ResultJson);
//                    }
//                });
//    }

    JSONObject object;

    private void initData() {
        //传递的json数据
        object = new JSONObject();
        try {
            object.put("opencode", "JNPKL3M438O");
            object.put("name", "");
            object.put("areaid", "");
            object.put("lng", 106.561581);
            object.put("lat", 29.564743);
            object.put("pageindex", "1");
            object.put("pagesize", "10");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initRetrofit() {
        //配置okhttp3客户端
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build();

        //构建Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.baidu.com/")  //.baseUrl  是设置连接的地址  https://www.baidu.com/?name=wj&pwd=123456
                .client(okHttpClient)
                .addConverterFactory(JsonConverterFactory.create())//添加自定义转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 添加Rxjava适配器
                .build();

        //请求
        String username = "sarahjean";
        Call<User> call = retrofit.create(SelectPlotBizApi.class).getUser(username);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                int statusCode = response.code();
                User user = response.body();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Log error here since request failed
            }

        });
    }
}
