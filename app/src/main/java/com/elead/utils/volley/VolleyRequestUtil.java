package com.elead.utils.volley;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

public class VolleyRequestUtil {
    private static final int MY_SOCKET_TIMEOUT_MS = 5 * 1000;
    private Context mContext;
    /* Volley配置 */
    // 建立Volley的Http请求队列
    // 开放Volley的HTTP请求队列接口
    private static RequestQueue volleyQueue;
    public static StringRequest stringRequest;

    public VolleyRequestUtil(Context mContext) {
        this.mContext = mContext;
        volleyQueue = Volley.newRequestQueue(mContext);
    }

    public static void cancleAll(String s) {
        if (null != volleyQueue)
            volleyQueue.cancelAll(s);
    }


    public static class VolleyRequestHolder {
        public static VolleyRequestUtil volleyRequestUtil;
    }

    public static VolleyRequestUtil getInstance(Context context) {
        if (null == VolleyRequestHolder.volleyRequestUtil) {
            VolleyRequestHolder.volleyRequestUtil = new VolleyRequestUtil(context);
        }
        return VolleyRequestHolder.volleyRequestUtil;
    }


    /*
    * 获取GET请求内容
    * 参数：
    * context：当前上下文；
    * url：请求的url地址；
    * tag：当前请求的标签；
    * volleyListenerInterface：VolleyListenerInterface接口；
    * */
    public static void RequestGet(String url, String tag, VolleyListenerInterface volleyListenerInterface) {
        Log.d("url: ", url + "---tag " + tag);
        // 清除请求队列中的tag标记请求
        volleyQueue.cancelAll(tag);
        // 创建当前的请求，获取字符串内容
        stringRequest = new StringRequest(Request.Method.GET, url, volleyListenerInterface.mListener, volleyListenerInterface.mErrorListener);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                1,
                1.0f));
        // 为当前请求添加标记
        stringRequest.setTag(tag);
        // 将当前请求添加到请求队列中
        volleyQueue.add(stringRequest);
        // 重启当前请求队列
        volleyQueue.start();

    }

    /*
    * 获取POST请求内容（请求的代码为Map）
    * 参数：
    * context：当前上下文；
    * url：请求的url地址；
    * tag：当前请求的标签；
    * params：POST请求内容；
    * volleyListenerInterface：VolleyListenerInterface接口；
    * */
    public static void RequestPost(String url, String tag, final Map<String, String> params, VolleyListenerInterface volleyListenerInterface) {
        // 清除请求队列中的tag标记请求
        Log.d("eleadLogin", "3");
        Log.d("url: ", url + "---tag " + tag);
        try {
            volleyQueue.cancelAll(tag);
        } catch (IllegalArgumentException e) {
            Log.d("eleadLogin", "4");
        }
        Log.d("eleadLogin", "5");
//        SsX509TrustManager.allowAllSSL();//信任所有证书
        // 创建当前的POST请求，并将请求内容写入Map中
        stringRequest = new StringRequest(Request.Method.POST, url, volleyListenerInterface.mListener, volleyListenerInterface.mErrorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Log.d("eleadLogin", "6");
                return params;
            }
        };
        Log.d("eleadLogin", "7");
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                1,
                1.0f));
        Log.d("eleadLogin", "8");
        // 为当前请求添加标记
        stringRequest.setTag(tag);
        Log.d("eleadLogin", "9");
        // 将当前请求添加到请求队列中
        volleyQueue.add(stringRequest);
        Log.d("eleadLogin", "10");
        // 重启当前请求队列
        volleyQueue.start();
        Log.d("eleadLogin", "11");
    }


}