package com.elead.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.elead.application.MyApplication;
import com.elead.card.mode.BaseCardInfo;
import com.elead.entity.DeviceItemEntity;
import com.elead.eplatform.R;
import com.elead.service.EPlamformServices;
import com.elead.utils.volley.VHttpUrlConnectionUtil;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;

/**
 * Created by xieshibin on 2016/12/22.
 */

public class DeviceManageUtil {

    Context  context;
    Handler mHandler;
    public static final int  MutiDevice = 10;
    public static final int  DeviceList = 20;
    public static final int  BindStatus = 30;
    public static final int  BindDevice = 40;
    public static final int  UnBindDevice = 50;



    public DeviceManageUtil(Context  context){
        this.context = context;
    }
    /**
     * 查询绑定设备个数或次数是否超限
     */
    public  void isMutiDeviceUser( Handler handler) {
        if(!NetWorkUtils.isNetworkConnected(context)){
            ToastUtil.showToast(context,context.getResources().getString(R.string.network_not_connected),
                    Toast.LENGTH_LONG).show();
            return;
        }
        mHandler = handler;
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("work_no", MyApplication.user.work_no);
        String url = EPlamformServices.is_muti_devices_service;
        VHttpUrlConnectionUtil.postRequest(url, parameters, new HttpUrlConnectionUtil.onConnectionFinishLinstener() {
            @Override
            public void onSuccess(String url, String result) {
                JSONObject dataJson = null;
                int ret_code = 0;
                String ret_message="";
                int numTotal;
                int times_left;
                try {
                    dataJson = new JSONObject(result);
                    ret_code = dataJson.getInt("ret_code");
                    if(dataJson.has("ret_message")){
                        ret_message = dataJson.getString("ret_message");
                    }
//                    if(ret_code>0){
                    numTotal = dataJson.getInt("num_total");
                    times_left = dataJson.getInt("times_left");
                    Message msg = Message.obtain();
                    msg.what = MutiDevice;
                    msg.arg1 = numTotal;
                    msg.arg2 = times_left;
                    msg.obj = ret_message;
                    mHandler.dispatchMessage(msg);
//                    }
                } catch (JSONException e) {
                    ToastUtil.showToast(context.getResources().getString(R.string.resolve_fail)).show();
                    e.printStackTrace();
                }
            }
            @Override
            public void onFail(String url) {
                ToastUtil.showToast(context.getResources().getString(R.string.request_fail)).show();
            }
        });
    }

    /**
     * 绑定设备
     */
    public  void bindDevice(DeviceItemEntity entity, Handler handler) {
        if(!NetWorkUtils.isNetworkConnected(context)){
            ToastUtil.showToast(context,context.getResources().getString(R.string.network_not_connected),
                    Toast.LENGTH_LONG).show();
            return;
        }
        mHandler = handler;
        final String udid = entity.udid;
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("work_no", MyApplication.user.work_no);
        parameters.put("device_name",entity.name);
        parameters.put("udid", udid);
        parameters.put("platform", "android");

        String url = EPlamformServices.bind_device_service;
        VHttpUrlConnectionUtil.postRequest(url, parameters, new HttpUrlConnectionUtil.onConnectionFinishLinstener() {
            @Override
            public void onSuccess(String url, String result) {
                int ret_code = 0;
                String ret_message="";
                try {
                    JSONObject dataJson = new JSONObject(result);
                    ret_code = dataJson.getInt("ret_code");
                    if(dataJson.has("ret_message")){
                        ret_message = dataJson.getString("ret_message");
                    }
                    ToastUtil.showToast(ret_message).show();

                    Message msg = Message.obtain();
                    msg.what = BindDevice;
                    msg.arg1 = ret_code;
                    msg.obj = udid;
                    mHandler.dispatchMessage(msg);

                } catch (JSONException e) {
                    ToastUtil.showToast(context.getResources().getString(R.string.resolve_fail)).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String url) {
                ToastUtil.showToast(context.getResources().getString(R.string.request_fail)).show();
            }
        });
    }

    /**
     * 解除绑定设备
     */
    public  void unbindDevice(DeviceItemEntity entity,Handler handler) {
        if(!NetWorkUtils.isNetworkConnected(context)){
            ToastUtil.showToast(context,context.getResources().getString(R.string.network_not_connected),
                    Toast.LENGTH_LONG).show();
            return;
        }
        mHandler = handler;
        final String udid = entity.udid;
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("work_no", MyApplication.user.work_no);
        parameters.put("udid", entity.udid);
        String url = EPlamformServices.unbind_device_service;
        VHttpUrlConnectionUtil.postRequest(url, parameters, new HttpUrlConnectionUtil.onConnectionFinishLinstener() {
            @Override
            public void onSuccess(String url, String result) {
                int ret_code = 0;
                String ret_message="";
                try {
                    JSONObject dataJson = new JSONObject(result);
                    ret_code = dataJson.getInt("ret_code");
                    if(dataJson.has("ret_message")){
                        ret_message = dataJson.getString("ret_message");
                    }
                    ToastUtil.showToast(ret_message).show();
                    Message msg = Message.obtain();
                    msg.what = UnBindDevice;
                    msg.arg1 = ret_code;
                    msg.obj = udid;
                    mHandler.dispatchMessage(msg);

                } catch (JSONException e) {
                    ToastUtil.showToast(context.getResources().getString(R.string.resolve_fail)).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String url) {
                ToastUtil.showToast(context.getResources().getString(R.string.request_fail)).show();
            }
        });
    }

    /**
     * 获取绑定设备列表
     */
    public  void getBindDevices(Handler handler) {
        if(!NetWorkUtils.isNetworkConnected(context)){
            ToastUtil.showToast(context,context.getResources().getString(R.string.network_not_connected),
                    Toast.LENGTH_LONG).show();
            return;
        }
        mHandler = handler;
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("work_no", MyApplication.user.work_no);
        String url = EPlamformServices.bind_device_list_service;
        VHttpUrlConnectionUtil.postRequest(url, parameters, new HttpUrlConnectionUtil.onConnectionFinishLinstener() {
            @Override
            public void onSuccess(String url, String result) {
                int ret_code;
                String ret_message = "";
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    ret_code = jsonObject.getInt("ret_code");
                    String devices_list = jsonObject.getString("devices_list");
                    if(jsonObject.has("ret_message")){
                        ret_message = jsonObject.getString("ret_message");
                    }
                    if(ret_code<0){
                        ToastUtil.showToast(ret_message).show();
                    }
                    if(ret_code>0){
                        Message msg = Message.obtain();
                        msg.what = DeviceList;
                        msg.obj = devices_list;
                        mHandler.dispatchMessage(msg);
                    }

                } catch (JSONException e) {
                    ToastUtil.showToast(context.getResources().getString(R.string.resolve_fail)).show();
                    e.printStackTrace();
                }
            }
            @Override
            public void onFail(String url) {
                ToastUtil.showToast(context.getResources().getString(R.string.request_fail)).show();
            }
        });
    }

    /**
     * 设备是否绑定,绑定状态
     */
    public  void isDeviceBindedStatus(Handler handler) {
        if(!NetWorkUtils.isNetworkConnected(context)){
            ToastUtil.showToast(context,context.getResources().getString(R.string.network_not_connected),
                    Toast.LENGTH_LONG).show();
            return;
        }
        mHandler = handler;
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("work_no", MyApplication.user.work_no);
        parameters.put("udid", MyApplication.serial_device);
        String url = EPlamformServices.device_bind_statu_service;
        VHttpUrlConnectionUtil.postRequest(url, parameters, new HttpUrlConnectionUtil.onConnectionFinishLinstener() {
            @Override
            public void onSuccess(String url, String result) {
                int ret_code;
                String ret_message="";
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    ret_code = jsonObject.getInt("ret_code");
                    if(jsonObject.has("ret_message")){
                        ret_message = jsonObject.getString("ret_message");
                    }
                    if(ret_code<0){
                        ToastUtil.showToast(ret_message).show();
                    }

                    Message msg = Message.obtain();
                    msg.what = BindStatus;
                    msg.arg1 = ret_code;
                    mHandler.dispatchMessage(msg);

                } catch (JSONException e) {
                    ToastUtil.showToast(context.getResources().getString(R.string.resolve_fail)).show();
                    e.printStackTrace();
                }
            }
            @Override
            public void onFail(String url) {
                ToastUtil.showToast(context.getResources().getString(R.string.request_fail)).show();
            }
        });
    }

}
