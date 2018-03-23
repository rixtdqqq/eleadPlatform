package com.elead.upcard.utils;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.elead.application.MyApplication;
import com.elead.service.EPlamformServices;
import com.elead.upcard.modle.SignInfo;
import com.elead.upcard.view.ISignBase;
import com.elead.utils.HttpUrlConnectionUtil;
import com.elead.utils.volley.VHttpUrlConnectionUtil;

import org.json.JSONObject;

import java.util.HashMap;

import static android.content.Context.WIFI_SERVICE;

public class ConnectDao {
    public static final int SIGN_IN_SUCCESS = 10086;
    public static final int ATTENDANCE_SUCCESS = 10087;
    public static final int SIGN_FAIL = 100;
    public static final int ATT_FAIL = 200;
    static HashMap<String, String> PARAMETERS;
    //http://192.168.1.225:8080/eplat-uat/
    static String PUNCH_THE_CARD_URL = EPlamformServices.punchcard_records_service;//   (外网)
    public static boolean isOnSign = false;
    public static boolean isOnAtt = false;

    public static void signIn(final String type, final String work_no, WifiInfo wifimac,
                              final Handler handler) {
        if (isOnSign) return;
        isOnSign = true;
        mHandler = handler;
        WOEK_NO = work_no;
        PARAMETERS = new HashMap<>();
        PARAMETERS.put("work_no", work_no);
        PARAMETERS.put("platform", "android");
        PARAMETERS.put("type", type);
        if (type.equals("GPS")) {
//            initGaodeGPS();
            LocationUtil.getInstance().setOnLocationSuccessLinstener(new LocationUtil.OnLocationSuccessLinstener() {
                @Override
                public void onLocationSuccess() {
                    if (LocationUtil.latitude == 0 || LocationUtil.longitude == 0) {
                        SignFailed(handler);
                        isOnSign = false;
                        return;
                    } else {
                        PARAMETERS.put("latitude", LocationUtil.latitude + "");
                        PARAMETERS.put("longitude", LocationUtil.longitude + "");
                        LocationUtil.latitude = LocationUtil.longitude = 0;
                        requestPubchTheCrad(type, work_no, handler);
                    }
                }

                @Override
                public void onLocationFail() {
                    SignFailed(handler);
                    isOnSign = false;
                }
            });
            LocationUtil.getInstance().enableMyLocation();
        } else {
            PARAMETERS.put("wifimac", wifimac.getBSSID());
            PARAMETERS.put("wifiname", wifimac.getSSID().replace("\"", ""));
            requestPubchTheCrad(type, work_no, handler);
        }
        Log.d("PARAMETERS", PARAMETERS.toString());
    }

    private static void requestPubchTheCrad(final String type, final String work_no, final Handler handler) {
        VHttpUrlConnectionUtil.postRequest(PUNCH_THE_CARD_URL, PARAMETERS, new HttpUrlConnectionUtil.onConnectionFinishLinstener() {
            @Override
            public void onSuccess(String url, String result) {
                isOnSign = false;
                Log.d("mysign", result);
                String time = null;
                String ret_messag = null;
                String type = null;
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    ret_messag = jsonObject.getString("ret_message");
                    time = jsonObject.getString("time");
                    type = jsonObject.getString("type");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Message message = handler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putString("time", time.substring(time.length() - 8, time.length()));
                message.setData(bundle);
                message.obj = type + ret_messag;
                if (ret_messag.contains("成功")) {
                    isGetGaoDeGPS = true;
                    if (type.equals("GPS")) {
                        LocationUtil.getInstance().disableMyLocation();
                        destroyLocation();
                    }
                    message.what = SIGN_IN_SUCCESS;
                    Utils.IS_REQUEST_DATA = true;
                } else {
                    if (type.equals("WiFi")) {
                        ConnectDao.signIn("GPS", work_no, null, handler);
                    } else {
                        if (isGetGaoDeGPS) {
                            isGetGaoDeGPS = false;
                            initGaodeGPS();
                        } else {
                            isGetGaoDeGPS = true;
                            message.what = SIGN_FAIL;
                        }
                        LocationUtil.getInstance().disableMyLocation();
//                        destroyLocation();
                    }
                }
                handler.sendMessageDelayed(message, 500);
            }

            @Override
            public void onFail(String url) {
                isOnSign = false;
                SignFailed(handler);
            }
        });
    }

    private static void SignFailed(Handler handler) {
        Message msg = handler.obtainMessage();
        msg.what = SIGN_FAIL;
        msg.obj = "打卡失败";
        handler.sendMessage(msg);
    }

    /**
     * @param end     work_no=0963
     * @param start   2016-12-11
     * @param end     2016-12-12
     * @param handler
     */
    public static void getAttendanceDate(String work_no,
                                         String start, String end, final Handler handler) {
        if (isOnAtt) return;
        isOnAtt = true;
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("work_no", work_no);
        parameters.put("start", start);
        parameters.put("end", end);
        Log.d("AttendanceDa", parameters.toString());
        String url = EPlamformServices.get_punchcard_records_custom_service;//外网
//        String url = "http://192.168.1.225:8080/eplat-uat/epAttenController.do?getPunchCardRecordsCustom";//内网
        VHttpUrlConnectionUtil.postRequest(url, parameters, new HttpUrlConnectionUtil.onConnectionFinishLinstener() {
            @Override
            public void onSuccess(String url, String result) {
                Log.d("getAttendanceDate: ", result);
                SignInfo attendance = JSON.parseObject(result,
                        SignInfo.class);
                Message msg = handler.obtainMessage();
                msg.what = ATTENDANCE_SUCCESS;
                msg.obj = attendance;
                handler.sendMessage(msg);
                isOnAtt = false;
            }

            @Override
            public void onFail(String url) {
                handler.sendEmptyMessage(ATT_FAIL);
                isOnAtt = false;
            }
        });
    }

    public static void onSign(ISignBase ripAnimView, Handler handler) {
        if (ripAnimView.isMove() && Utils.checkWifiAndGpsStatus(MyApplication.showingActivity)) {
            ripAnimView.setConnectFinish(false);
        } else if (!ripAnimView.isMove()) {
            ripAnimView.setConnectFinish(false);
            WifiManager wifiManager = (WifiManager) MyApplication.mContext.getSystemService(WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            Log.d("wifi", wifiInfo.toString());
            if (null == wifiInfo.getBSSID()) {
                ConnectDao.signIn("GPS", MyApplication.user.work_no, null, handler);
            } else {
                ConnectDao.signIn("WiFi", MyApplication.user.work_no, wifiInfo, handler);
            }

        }
    }

        //声明AMapLocationClient类对象
    private static AMapLocationClient mLocationClient = null;
    private static AMapLocationClientOption mOption;
    private static String mLatitude;
    private static String mLongitude;
    private static Handler mHandler;
    private static String WOEK_NO;
    private static boolean isGetGaoDeGPS = true;

    private static void initGaodeGPS() {
        if (null == mLocationClient) {
            //初始化定位
            mLocationClient = new AMapLocationClient(MyApplication.mContext);
            //设置定位参数
            mLocationClient.setLocationOption(getDefaultOption());
            //设置定位回调监听
            mLocationClient.setLocationListener(mLocationListener);
        }
        // 启动定位
        mLocationClient.startLocation();
    }

    /**
     * 定位监听
     */
    static AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (null != aMapLocation) {
                //解析定位结果
                getLocationStr(aMapLocation);
            } else {
                isGetGaoDeGPS = true;
                isOnSign = false;
                SignFailed(mHandler);
            }
        }
    };

    public synchronized static void getLocationStr(AMapLocation location) {
        if (null != location) {
            Log.d("sign", "location:" + location.toString());
//            StringBuffer sb = new StringBuffer();
            if (location.getErrorCode() == 0) {
                mLongitude = location.getLongitude() + "";//经度
                mLatitude = location.getLatitude() + "";//纬度
                PARAMETERS = new HashMap<>();
                PARAMETERS.put("work_no", WOEK_NO);
                PARAMETERS.put("platform", "android");
                PARAMETERS.put("type", "GPS");
                PARAMETERS.put("latitude", mLatitude);
                PARAMETERS.put("longitude", mLongitude);
                requestPubchTheCrad("GPS", WOEK_NO, mHandler);
                /*sb.append("定位成功" + "\n");
                sb.append("定位类型: " + location.getLocationType() + "\n");
                sb.append("经    度    : " + location.getLongitude() + "\n");
                sb.append("纬    度    : " + location.getLatitude() + "\n");
                sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
                sb.append("提供者    : " + location.getProvider() + "\n");

                if (location.getProvider().equalsIgnoreCase(
                        android.location.LocationManager.GPS_PROVIDER)) {
                    // 以下信息只有提供者是GPS时才会有
                    sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
                    sb.append("角    度    : " + location.getBearing() + "\n");
                    // 获取当前提供定位服务的卫星个数
                    sb.append("星    数    : "
                            + location.getSatellites() + "\n");
                } else {
                    // 提供者是GPS时是没有以下信息的
                    sb.append("国    家    : " + location.getCountry() + "\n");
                    sb.append("省            : " + location.getProvince() + "\n");
                    sb.append("市            : " + location.getCity() + "\n");
                    sb.append("城市编码 : " + location.getCityCode() + "\n");
                    sb.append("区            : " + location.getDistrict() + "\n");
                    sb.append("区域 码   : " + location.getAdCode() + "\n");
                    sb.append("地    址    : " + location.getAddress() + "\n");
                    sb.append("兴趣点    : " + location.getPoiName() + "\n");
                    //定位完成的时间
                    sb.append("定位时间: " + formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss") + "\n");
                }*/
            } else {
                isGetGaoDeGPS = true;
                isOnSign = false;
                SignFailed(mHandler);
            }
        }
    }

    /**
     * 默认的定位参数
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private static AMapLocationClientOption getDefaultOption() {
        mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(true);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Device_Sensors);//设置定位模式为AMapLocationMode.Device_Sensors，仅设备模式。
        mOption.setHttpTimeOut(20000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(true);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(true);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        mOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(false);// 设置是否开启缓存
        return mOption;
    }

    /**
     * 销毁定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private static void destroyLocation() {
        if (null != mLocationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            mLocationClient.onDestroy();
            mLocationClient = null;
            mOption = null;
        }
    }

}
