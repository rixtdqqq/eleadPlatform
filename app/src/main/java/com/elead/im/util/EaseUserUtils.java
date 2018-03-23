package com.elead.im.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.elead.eplatform.R;
import com.elead.im.controller.EaseUI;
import com.elead.im.db.DemoHelper;
import com.elead.im.entry.EMUserForNick;
import com.elead.im.entry.EaseUser;
import com.elead.service.EPlamformServices;
import com.elead.utils.HttpUrlConnectionUtil;
import com.elead.utils.volley.VHttpUrlConnectionUtil;
import com.elead.views.CircularImageView;
import com.hyphenate.chat.EMClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class EaseUserUtils {

    static EaseUI.EaseUserProfileProvider userProvider;

    static {
        userProvider = EaseUI.getInstance().getUserProfileProvider();
    }


    /**
     * get EaseUser according username
     *
     * @param username
     * @return
     */
    public static EaseUser getUserInfo(String username) {
        if (userProvider != null)
            return userProvider.getUser(username);
        return null;
    }

    /**
     * set user avatar
     *
     * @param username
     */
    public static void setUserAvatar(Context context, String username, ImageView imageView) {
        EaseUser user = getUserInfo(username);
        if (user != null && user.getAvatar() != null) {
            try {
                int avatarResId = Integer.parseInt(user.getAvatar());
                Glide.with(context).load(avatarResId).into(imageView);
            } catch (Exception e) {
                //use default avatar
                Glide.with(context).load(user.getAvatar()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.rp_user).into(imageView);
            }
        } else {
            Glide.with(context).load(R.drawable.rp_user).into(imageView);
        }
    }

    public static void setUserAvatar(Context context, String username, CircularImageView imageView) {
        EaseUser user = getUserInfo(username);
        EaseUserUtils.setUserNick(user.getUsername(), imageView);
        if (user != null) {
            if (user.getAvatar() != null) {
                try {
                    int avatarResId = Integer.parseInt(user.getAvatar());
                    Glide.with(context).load(avatarResId).into(imageView);
                } catch (Exception e) {
                    Glide.with(context).load(user.getAvatar()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(null).into(imageView);
                }
            }
        }
    }

    public static void setUserAvatar(Context context, String username, String phone, CircularImageView imageView) {
        EaseUser user = getUserInfo(phone);
        EaseUserUtils.setUserNick(username, imageView);
        if (user != null) {
            if (user.getAvatar() != null) {
                try {
                    int avatarResId = Integer.parseInt(user.getAvatar());
                    Glide.with(context).load(avatarResId).into(imageView);
                } catch (Exception e) {
                    Glide.with(context).load(user.getAvatar()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(null).into(imageView);
                }
            }
        }
    }

    /**
     * 设置用户头像昵称
     *
     * @param username
     * @param textView
     */
    public static void setUserNick(String username, CircularImageView textView) {
        if (textView != null) {
            EaseUser user = getUserInfo(username);
            Log.d(EaseUserUtils.class.getSimpleName(), "user: " + username);
            Log.d(EaseUserUtils.class.getSimpleName(), "user.getNick(): " + user.getNick());
            textView.setBackgroundColor(username);//设置头像背景颜色
            if (user != null && !user.getNick().equals(username)) {
                textView.setText(user.getNick());
            } else {
                //当没有昵称时，从缓存中获取一次，缓存也没有时候，请求接口
                SharedPreferences sp = EaseUser.sp;
                String str = sp.getString(username, "");
                if (!TextUtils.isEmpty(str)) {
                    textView.setText(str);
                } else {
                    getFreindNick(username, textView);
                }
            }
        }
    }

    /**
     * 获取好友昵称
     *
     * @param username
     */
    public static void getFreindNick(final String username, final CircularImageView textView) {
        HashMap<String, String> parameters = new HashMap<>();
        JSONArray a = new JSONArray();
        a.put(username);
        parameters.put("em_usernames", a.toString());
        String url = EPlamformServices.get_epchatuser_info_service;
        VHttpUrlConnectionUtil.postRequest(url, parameters, new HttpUrlConnectionUtil.onConnectionFinishLinstener() {
            @Override
            public void onSuccess(String url, String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String info = jsonObject.getString("datas");
                    Log.i("TAG", info.toString() + "");
                    JSONArray array = new JSONArray(info);
                    EMUserForNick epUser = JSON.parseObject(array.get(0).toString(), EMUserForNick.class);
                    if (!TextUtils.isEmpty(epUser.getEp_name())) {
                        textView.setText(epUser.getEp_name());
                        //TODO 缓存起来，下次就不用再调接口
                        SharedPreferences.Editor edit = EaseUser.sp.edit();
                        edit.putString(username, epUser.getEp_name());
                        edit.commit();
                    } else {

                        textView.setText(username);
                    }
                    //TODO
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String url) {
                Log.i("TAG", "连接失败");
                textView.setText(username);
            }
        });
    }


    /**
     * set user's nickname
     */
    public static void setUserNick(String username, TextView textView) {
        if (textView != null) {
            EaseUser user = getUserInfo(username);
            if (user != null && !user.getNick().equals(username)) {
                textView.setText(user.getNick());
                Log.i("NickName", "" + user.getNickname());
            } else {
                SharedPreferences sp = EaseUser.sp;
                String str = sp.getString(username, username);
                textView.setText(str);
            }
        }
    }

    public static String getNick(String userName) {
        String string = EaseUser.sp.getString(userName, userName);
        if (string.equals(userName)) {
            return getUserInfo(userName).getNick();
        } else return string;
    }

    /**
     * 修改昵称
     *
     * @param nickName
     */
    public static void updateRemoteNick(final String nickName) {
        String currenUser = EMClient.getInstance().getCurrentUser();
        EaseUser user = getUserInfo(currenUser);
        if (user.getNickname().equals(nickName)) {
            return;
        }
        new Thread(new Runnable() {

            @Override
            public void run() {
                DemoHelper.getInstance().getUserProfileManager().updateCurrentUserNickName(nickName);
            }
        }).start();
    }

}
