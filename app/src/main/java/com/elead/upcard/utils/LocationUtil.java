package com.elead.upcard.utils;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.amap.mapapi.location.LocationManagerProxy;


public class LocationUtil implements LocationListener {
    private static final int START_LOCAT = 100;
    private static final int STOP_LOCAT = 200;
    private static final String TAG = LocationUtil.class.getSimpleName();
    private static final int LOCAT_FAIL = 500;
    private static final int TIME_OUT = 8 * 1000;

    private LocationManagerProxy locationManager = null;
    private OnLocationSuccessLinstener onLocationSuccessLinstener;
    public static double latitude;
    public static double longitude;
    private Context context;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case START_LOCAT:
                    Log.d(TAG, "startlocation");
                    if (null == locationManager) {
                        locationManager = LocationManagerProxy.getInstance(context);
                    }
                    Criteria cri = new Criteria();
                    cri.setAccuracy(Criteria.ACCURACY_FINE);//位置解析的精度，高或低，参数： Criteria. ACCURACY_FINE，精确模式； Criteria. ACCURACY_COARSE，模糊模式
                    cri.setAltitudeRequired(false);//是否提供海拔高度信息
                    cri.setBearingRequired(false);//是否提供方向信息
                    cri.setCostAllowed(false);//是否允许运营商计费
                    cri.setPowerRequirement(Criteria.POWER_HIGH);//电池消耗，无、低、中、高，参数 Criteria. NO_REQUIREMENT, Criteria. POWER_LOW, Criteria. POWER_MEDIUM, or Criteria. POWER_HIGH,
                    cri.setSpeedRequired(false);//是否提供速度信息
                    String bestProvider = locationManager.getBestProvider(cri, true);
                    locationManager.requestLocationUpdates(bestProvider, 0, 0, LocationUtil.this);
                    break;
                case STOP_LOCAT:
                    disableMyLocation();
                    if (null != onLocationSuccessLinstener)
                        onLocationSuccessLinstener.onLocationSuccess();
                    break;
                case LOCAT_FAIL:
                    if (0 == latitude && 0 == longitude) {
                        disableMyLocation();
                        if (null != onLocationSuccessLinstener)
                            onLocationSuccessLinstener.onLocationFail();
                    }
                    break;
            }

        }
    };

    public LocationUtil(Context context) {
        this.context = context;
    }

    public static class LocationHolder {
        static LocationUtil locationUtil;
    }

    public static LocationUtil getInstance() {
        return LocationHolder.locationUtil;
    }

    public static void inIt(Context context) {
        LocationHolder.locationUtil = new LocationUtil(context);
    }

    public void enableMyLocation() {
        LocationManager lManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean providerEnabled = lManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (providerEnabled) {
            handler.removeMessages(START_LOCAT);
            handler.sendEmptyMessageDelayed(START_LOCAT, 300);
//            handler.removeMessages(LOCAT_FAIL);
//            handler.sendEmptyMessageDelayed(LOCAT_FAIL, TIME_OUT);
        } else {
            latitude = longitude = 0;
            handler.sendEmptyMessage(LOCAT_FAIL);
        }
    }

    public void disableMyLocation() {
        if (null != locationManager) {
            locationManager.removeUpdates(this);
            locationManager.destory();
            locationManager = null;
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            String str = ("定位成功:(" + latitude + "," + longitude + ")");
            Log.d(TAG, "---" + str);
            handler.removeMessages(STOP_LOCAT);
            handler.sendEmptyMessageDelayed(STOP_LOCAT, 100);
        } else {
            latitude = longitude = 0;
            handler.sendEmptyMessage(LOCAT_FAIL);
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d(TAG + "onProviderDisabled", provider);

    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d(TAG + "---onProviderEnabled", provider);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
        Log.d(TAG + "---onStatusChanged", provider);
    }


    public interface OnLocationSuccessLinstener {
        void onLocationSuccess();

        void onLocationFail();
    }

    public void setOnLocationSuccessLinstener(OnLocationSuccessLinstener onLocationSuccessLinstener) {
        this.onLocationSuccessLinstener = onLocationSuccessLinstener;
    }
}
