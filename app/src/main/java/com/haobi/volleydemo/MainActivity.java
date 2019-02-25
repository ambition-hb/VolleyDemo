package com.haobi.volleydemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * 1、Volley的Get和Post请求方式的使用
 * 2、volley的网络请求队列建立（全局的请求队列）和取消队列请求及Activity周期关联
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        volley_Get();
        volley_Post();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //保证Activity在销毁或关闭的时候，请求可以同时进行销毁。防止后台运行请求，影响用户体验。
        MyApplication.getHttpQueue().cancelAll("abcPost");
        MyApplication.getHttpQueue().cancelAll("abcGet");
    }

    private void volley_Post() {
        String url = "http://apis.juhe.cn/mobile/get?";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, "网络请求失败", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("phone", "18829354567");
                map.put("key", "fef8795fcfa0a1977582d8c31b529112");
                return map;
            }
        };
        request.setTag("abcPost");
        MyApplication.getHttpQueue().add(request);
    }

    private void volley_Get() {
        String url = "http://apis.juhe.cn/mobile/get?phone=18829354567&key=fef8795fcfa0a1977582d8c31b529112";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, "网络请求失败", Toast.LENGTH_LONG).show();
            }
        });
        //设置Tag标签，当想取消请求的时候，可通过该标签对请求进行取消
        request.setTag("abcGet");
        //将请求添加到队列里面
        MyApplication.getHttpQueue().add(request);
    }
}
