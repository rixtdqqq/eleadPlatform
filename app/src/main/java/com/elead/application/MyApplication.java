
package com.elead.application;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.elead.eplatform.R;
import com.elead.im.db.DemoHelper;
import com.elead.module.EpUser;
import com.elead.utils.HttpsUtil;
import com.elead.utils.SharedPreferencesUtil;
import com.elead.utils.Util;
import com.elead.utils.volley.VolleyRequestUtil;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.Iterator;
import java.util.List;


public class MyApplication extends MultiDexApplication implements Application.ActivityLifecycleCallbacks {

    public static final String TAG = MyApplication.class.getSimpleName();
    public static Context mContext;
    public static Activity showingActivity;
    private static MyApplication instance;
    public static int[] size;
    public static EpUser user;
    //是否找回密码
    public static boolean isSetPsw = false;
    //获取手机imei号
    public static String serial_device = "";
    //设备是否绑定成功
    public static boolean isBind = false;

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
        HttpsUtil.initHttpsUrlConnection(this);
        SharedPreferencesUtil.putInt(this, Util.LOGIN_COUNT, SharedPreferencesUtil.getInt(this, Util.LOGIN_COUNT)+1);
        MultiDex.install(this);
        VolleyRequestUtil.getInstance(this);
        instance = this;
        mContext = getApplicationContext();
//        CrashHandler.getInstance().init(mContext);
        String myUser = SharedPreferencesUtil.getString(mContext, EpUser.class.getSimpleName());
        isSetPsw = false;
        isBind = false;
        DemoHelper.getInstance().init(mContext);
        if (!TextUtils.isEmpty(myUser)) {
            user = JSON.parseObject(myUser, EpUser.class);
        } else {
            user = new EpUser();
        }
        initEaseMob();
//        Bmob.initialize(mContext, "41758958c13d7678dd05c867fb3caf2d");
//        BmobInstallation.getCurrentInstallation().save();
        // 启动推送服务
        MultiDex.install(mContext);
        //init demo helper
//        DemoHelper.getInstance().init(mContext);

        initImageLoader();
        serial_device = android.os.Build.SERIAL;

    }



    private void initEaseMob(){
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);

        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回
        if (processAppName == null ||!processAppName.equalsIgnoreCase(mContext.getPackageName())) {
            Log.e(TAG,"enter the service process!");
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
        //初始化
        EMClient.getInstance().init(mContext, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
        //初始化EaseUI
        EMClient.getInstance().setDebugMode(true);
    }

    public static MyApplication getInstance() {
        return instance;
    }

    /**
     * 设置imageloader
     */
    private void initImageLoader() {

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.default_image) // resource or drawable
                .showImageForEmptyUri(R.drawable.default_image) // resource or drawable
                .showImageOnFail(R.drawable.default_image) // resource or drawable
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT) // default
                .bitmapConfig(Bitmap.Config.RGB_565) // default
                .build();

        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this).
                defaultDisplayImageOptions(options).
                tasksProcessingOrder(QueueProcessingType.LIFO).build();
        //Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);

//        RedPacket.getInstance().initContext(mContext);
//        RedPacket.getInstance().setDebugMode(true);
//        EMOptions options = new EMOptions();
//// 默认添加好友时，是不需要验证的，改成需要验证
//        options.setAcceptInvitationAlways(false);
////初始化
//        int pid = android.os.Process.myPid();
//        String processAppName = getAppName(pid);
//// 如果APP启用了远程的service，此application:onCreate会被调用2次
//// 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
//// 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回
//
//        if (processAppName == null || !processAppName.equalsIgnoreCase(mContext.getPackageName())) {
//            Log.e(TAG, "enter the service process!");
//
//            // 则此application::onCreate 是被service 调用的，直接返回
//            return;
//        }
//        EMClient.getInstance().init(mContext, options);
////在做打包混淆时，关闭debug模式，避免消耗不必要的资源
//        EMClient.getInstance().setDebugMode(false);
    }

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        Log.e("APP--onActivityCreated", activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
