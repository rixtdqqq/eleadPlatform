package com.elead.im.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.elead.im.db.DemoHelper;
import com.elead.im.entry.EaseUser;
import com.elead.module.EpUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/13 0013.
 */
public class RegistUsersMessageSpUtil {
    private static SharedPreferences sp;
    private List<EaseUser> contactList;
    private static RegistUsersMessageSpUtil registUsersMessageSpUtil;
    private static Context mContext;
    private static String Tag = RegistUsersMessageSpUtil.class.getSimpleName();

    public RegistUsersMessageSpUtil(Context context) {
        sp = context.getSharedPreferences("registPhones", Context.MODE_PRIVATE);
        setContactList(DemoHelper.getInstance().getArrayContactList());
    }


    public static RegistUsersMessageSpUtil getInstance(Context context) {
        if (null == sp) {
            mContext = context;
            registUsersMessageSpUtil = new RegistUsersMessageSpUtil(context);

        }
        return registUsersMessageSpUtil;
    }


    public boolean saveInstallationId(String phone, String installationId) {
        return sp.edit().putString(phone, installationId).commit();
    }

    public String getInstallationIdByPhone(String phone) {
        return sp.getString(phone, "");
    }

    public boolean isRegist(String phone) {
        String string = sp.getString(phone, "");
        Log.d("22222", "phone:" + phone + "---" + "string: " + string);
        return string.length() > 2 ? true : false;
    }

    public boolean isAddFriend(String phone) {
        boolean isAdd = false;
        Log.d(Tag, "isAddFriend.user" + contactList.size());
        for (EaseUser user : contactList) {
            Log.d(Tag, "isAddFriend.user" + user.getUsername() + "---" + phone);
            if (user.getUsername().equals(phone)) {
                isAdd = true;
                break;
            }
        }
        return isAdd;
    }

    public List<EpUser> getMyUserList() {
        return Euser2Myuser(contactList);
    }

    public boolean isContain(String phone) {
        String string = sp.getString(phone, null);
        return null == string ? false : true;
    }

    public List<EpUser> Euser2Myuser(List<EaseUser> user) {
        List<EpUser> epUsers = new ArrayList<>();
        for (EaseUser easeUser : user) {
            epUsers.add(new EpUser(easeUser.getNick(), easeUser.getUsername(), easeUser.getInitialLetter()));
        }
        return epUsers;
    }

    public void setContactList(List<EaseUser> contactList) {
        this.contactList = contactList;
    }

    public List<EaseUser> getContactList() {
        return contactList;
    }


}
