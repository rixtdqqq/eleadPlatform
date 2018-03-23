package com.elead.utils.volley;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.elead.utils.HttpUrlConnectionUtil;

import java.util.HashMap;

public class VHttpUrlConnectionUtil {

    public static void getRequest(final String url,
                                  final HttpUrlConnectionUtil.onConnectionFinishLinstener connectionFinishLinstener) {
        VolleyRequestUtil.RequestGet(url, url2Tag(url), new VolleyListenerInterface(new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                connectionFinishLinstener.onSuccess(url, s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                connectionFinishLinstener.onFail(url);
            }
        }));
    }


    public static void postRequest(final String url,
                                   final HashMap<String, String> parameters,
                                   final HttpUrlConnectionUtil.onConnectionFinishLinstener connectionFinishLinstener) {
        Log.d("eleadLogin", "2");
        VolleyRequestUtil.RequestPost(url, url2Tag(url), parameters, new VolleyListenerInterface(new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d(VHttpUrlConnectionUtil.class.getSimpleName(), "onResponse" + s);
                connectionFinishLinstener.onSuccess(url, s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("eleadLogin", "eeeeeee");
                Log.d(VHttpUrlConnectionUtil.class.getSimpleName(), "VolleyError" + volleyError);
                connectionFinishLinstener.onFail(url);
            }
        }));
    }

    private static String url2Tag(String url) {
        Log.d("eleadLogin", "aaa");
        return url.substring(url.indexOf("?") + 1, url.length());
    }

    public static void cancleRequest(String url) {
        VolleyRequestUtil.cancleAll(url2Tag(url));
    }

}