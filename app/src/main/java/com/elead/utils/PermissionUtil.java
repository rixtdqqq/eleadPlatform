package com.elead.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * 请求权限工具类
 * Created by lm806 on 2016/12/13.
 */

public class PermissionUtil {

    /**
     * 权限集合
     */
    private static List<String> permissionList = new ArrayList<>();

    /**
     * 请求所有权限
     *
     * @param requestCode
     */
    public static Boolean needAllPermission(Activity act, int requestCode) {
            return requestAllPermissions(act, requestCode);
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//            return true;
//        } else {
//        }
    }
    public static Boolean needSignPermission(Activity act, int requestCode) {
            return requestSignPermissions(act, requestCode);
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//            return true;
//        } else {
//        }
    }

    /**
     * 请求打卡权限
     * @param act
     * @param requestCode
     * @return
     */
    private static Boolean requestSignPermissions(Activity act, int requestCode) {
        permissionList.clear();
        if (ContextCompat.checkSelfPermission(act,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(act,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        String[] permissions = permissionList.toArray(new String[permissionList.size()]);
        if (permissions.length > 0) {
            ActivityCompat.requestPermissions(act,
                    permissions,
                    requestCode);
            ToastUtil.showToast(act, "允许获取位置权限后才能打卡!", 1).show();
            return false;
        } else {
            return true;
        }

    }

    /**
     * 请求所有权限
     * @param act
     * @param requestCode
     * @return
     */
    private static Boolean requestAllPermissions(Activity act, int requestCode) {
        permissionList.clear();
        if (ContextCompat.checkSelfPermission(act,
                Manifest.permission.MODIFY_AUDIO_SETTINGS)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.MODIFY_AUDIO_SETTINGS);
        }

        if (ContextCompat.checkSelfPermission(act,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(act,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.CAMERA);
        }
        if (ContextCompat.checkSelfPermission(act,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_CONTACTS);
        }
        if (ContextCompat.checkSelfPermission(act,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(act,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        String[] permissions = permissionList.toArray(new String[permissionList.size()]);
        if (permissions.length > 0) {
            ActivityCompat.requestPermissions(act,
                    permissions,
                    requestCode);
            return false;
        } else {
            return true;
        }

    }

    /**
     * 请求电话权限
     *
     * @param requestCode
     * @return
     */
    public static boolean requesCallPhonePermissions(Activity act, int requestCode) {
        if (ContextCompat.checkSelfPermission(act,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {//没有权限
            ActivityCompat.requestPermissions(act,
                    new String[]{Manifest.permission.CALL_PHONE},
                    requestCode);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 请求读sdCard
     *
     * @param act
     * @param requestCode
     * @return
     */
    public static boolean requestReadSDCardPermissions(Activity act, int requestCode) {
        if (ContextCompat.checkSelfPermission(act,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {//没有权限
            ActivityCompat.requestPermissions(act,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    requestCode);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 请求读手机状态
     *
     * @param act
     * @param requestCode
     * @return
     */
    public static boolean requestReadPhoneStatePermissions(Activity act, int requestCode) {
        if (ContextCompat.checkSelfPermission(act,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {//没有权限
            ActivityCompat.requestPermissions(act,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    requestCode);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 请求照相机
     *
     * @param act
     * @param requestCode
     * @return
     */
    public static boolean requestCameraPermissions(Activity act, int requestCode) {
        if (ContextCompat.checkSelfPermission(act,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {//没有权限
            ActivityCompat.requestPermissions(act,
                    new String[]{Manifest.permission.CAMERA},
                    requestCode);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 读取通讯录
     *
     * @param act
     * @param requestCode
     * @return
     */
    public static boolean requestReadConstantPermissions(Activity act, int requestCode) {
        if (ContextCompat.checkSelfPermission(act,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {//没有权限
            ActivityCompat.requestPermissions(act,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    requestCode);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 访问账户Gmail列表
     *
     * @param act
     * @param requestCode
     * @return
     */
    public static boolean requestGET_ACCOUNTSPermissions(Activity act, int requestCode) {
        if (ContextCompat.checkSelfPermission(act,
                Manifest.permission.GET_ACCOUNTS)
                != PackageManager.PERMISSION_GRANTED) {//没有权限
            ActivityCompat.requestPermissions(act,
                    new String[]{Manifest.permission.GET_ACCOUNTS},
                    requestCode);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 获取精确位置
     *
     * @param act
     * @param requestCode
     * @return
     */
    public static boolean requestLocationPermissions(Activity act, int requestCode) {
        if (ContextCompat.checkSelfPermission(act,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {//没有权限
            ActivityCompat.requestPermissions(act,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    requestCode);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 请求录音权限
     * @param act
     * @param requestCode
     * @return
     */
    public static boolean requestRecordAudioPermissions(Activity act, int requestCode) {
        if (ContextCompat.checkSelfPermission(act,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {//没有权限
            ActivityCompat.requestPermissions(act,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    requestCode);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 请求录音权限
     * @param act
     * @param requestCode
     * @return
     */
    public static boolean requestWriteExternalStoragePermissions(Activity act, int requestCode) {
        if (ContextCompat.checkSelfPermission(act,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {//没有权限
            ActivityCompat.requestPermissions(act,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    requestCode);
            return false;
        } else {
            return true;
        }
    }


}


