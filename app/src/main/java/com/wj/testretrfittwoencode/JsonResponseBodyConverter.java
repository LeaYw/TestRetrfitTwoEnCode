package com.wj.testretrfittwoencode;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by wangjing on 2016/7/4.
 * 自定义响应ResponseBody
 */
public class JsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson mGson;//gson对象
    private final TypeAdapter<T> adapter;

    /**
     * 构造器
     */
    public JsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.mGson = gson;
        this.adapter = adapter;
    }

    /**
     * 转换
     *
     * @param responseBody
     * @return
     * @throws IOException
     */
    @Override
    public T convert(ResponseBody responseBody) throws IOException {

        String response = responseBody.string();

        String strResult = response.substring(1, response.length() - 1);
        String result = CryptoUtils.DecryptWithNoPadding(strResult);//解密
        Log.e("xiaozhang", "解密的服务器数据：" + result);
        User pageBean = mGson.fromJson(result, User.class);
        return (T) pageBean;


    }

}