package com.elead.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.elead.application.MyApplication;
import com.elead.entity.DeviceItemEntity;
import com.elead.eplatform.R;
import com.elead.utils.AppManager;
import com.elead.utils.DeviceManageUtil;
import com.elead.utils.EloadingDialog;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieshibin on 2016/12/23.
 */

public class BindListActivity extends Activity {

    DeviceManageUtil deviceManageUtil = new DeviceManageUtil(BindListActivity.this);
    List<DeviceItemEntity> bindedEntities = new ArrayList<DeviceItemEntity>();
    List<DeviceItemEntity> unbindedEntities = new ArrayList<DeviceItemEntity>();
    TextView cancel_btn,sure_btn;

    public  class ListHandler extends android.os.Handler {
        private WeakReference<Context> reference;
        public ListHandler(Context context) {
            reference = new WeakReference<>(context);
        }
        @Override
        public void handleMessage(Message msg) {
            BindListActivity activity = (BindListActivity) reference.get();
            if( null == activity){
                return;
            }
            if(msg.what == DeviceManageUtil.DeviceList){
                String deviceList = (String) msg.obj;
                bindedEntities = JSON.parseArray(deviceList, DeviceItemEntity.class);
                fillLayout();

            }else if(msg.what == DeviceManageUtil.UnBindDevice){//解绑
                int res_code = msg.arg1;
                if(res_code>0){//解绑成功
                    String imei = (String)msg.obj;
                    repackDatas(false,imei);
                    fillLayout();
                }
            }else if(msg.what == DeviceManageUtil.BindDevice){//绑定
                if(msg.arg1>0){
                    String imei = (String)msg.obj;
                    repackDatas(true,imei);
                    fillLayout();
                }
            }
            EloadingDialog.cancle();
        }
    }

    public ListHandler mHandler = new ListHandler(this);
    LinearLayout  bind_layout,unbind_layout;
    LayoutInflater inflater;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.bind_device_layout);

        inflater = LayoutInflater.from(this);
        AppManager.getAppManager().addActivity(this);
        initView();
        initData();
        getBindDevices();

    }


    /**
     * 绑定和解绑后重新组装数据
     * @param isbind  true表示绑定
     * @param udid  设备唯一码在android上是imei号
     */
    private  void  repackDatas(boolean isbind,String udid){

         if(isbind){
//             for(DeviceItemEntity entity : unbindedEntities){//删除、增加时这种写法会报异常
             for(int i=0;i<unbindedEntities.size();i++){
                 DeviceItemEntity entity = unbindedEntities.get(i);
                 if(udid.equals(entity.udid)){
                     unbindedEntities.remove(entity);
                     bindedEntities.add(entity);
                 }
             }
         }else{
//             for(DeviceItemEntity entity : bindedEntities){
             for(int i=0;i<bindedEntities.size();i++){
                 DeviceItemEntity entity = bindedEntities.get(i);
                 if(udid.equals(entity.udid)){
                     bindedEntities.remove(entity);
                     unbindedEntities.add(entity);
                 }
             }
         }
    }

    /**
     * 把相应数据填充到设备绑定区和未绑定区
     */
    private  void  fillLayout(){

        bind_layout.removeAllViews();
        unbind_layout.removeAllViews();
        //绑定设备
        for(DeviceItemEntity itemEntity:bindedEntities){
            final  DeviceItemEntity item = itemEntity;
            View view = inflater.inflate(R.layout.bind_device_item,null);
            TextView device_name = (TextView) view.findViewById(R.id.device_name);
            TextView device_id = (TextView) view.findViewById(R.id.device_id);
            Button unbind_btn = (Button) view.findViewById(R.id.bind_type_btn);
            device_name.setText(itemEntity.name);
            device_id.setText(itemEntity.udid);
            unbind_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //解绑
                    EloadingDialog.ShowDialog(BindListActivity.this);
                    deviceManageUtil.unbindDevice(item,mHandler);
                }
            });
            bind_layout.addView(view);
            if(itemEntity.udid.equals(MyApplication.serial_device)){
                MyApplication.isBind = true;
                sure_btn.setBackground(getResources().getDrawable(R.drawable.grey_btn_selector));
            }
        }
        //未绑定设备
        for(DeviceItemEntity itemEntity:unbindedEntities){
            final  DeviceItemEntity item = itemEntity;
            View view = inflater.inflate(R.layout.bind_device_item,null);
            TextView device_name = (TextView) view.findViewById(R.id.device_name);
            TextView device_id = (TextView) view.findViewById(R.id.device_id);
            Button  bind_btn = (Button) view.findViewById(R.id.bind_type_btn);
            bind_btn.setText(getResources().getString(R.string.bind));
            bind_btn.setBackground(getResources().getDrawable(R.drawable.green_btn_selector));
            device_name.setText(itemEntity.name);
            device_id.setText(itemEntity.udid);
            bind_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //解绑
                    EloadingDialog.ShowDialog(BindListActivity.this);
                    deviceManageUtil.bindDevice(item,mHandler);
                }
            });
            unbind_layout.addView(view);
            if(itemEntity.udid.equals(MyApplication.serial_device)){
                MyApplication.isBind = false;
                sure_btn.setBackground(getResources().getDrawable(R.drawable.null_btn_selector));
            }
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitToLogin();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * 退出到登录页面
     */
    private void  exitToLogin(){
        // 重新退到登录页面
        Intent intent = new Intent(BindListActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("logout", true);
        startActivity(intent);
        AppManager.getAppManager().finishAllActivity();
    }

    /**
     * 初始化
     */
    private  void  initView(){
        back = (ImageButton) findViewById(R.id.back);
        bind_layout = (LinearLayout)findViewById(R.id.bind_layout);
        unbind_layout = (LinearLayout)findViewById(R.id.unbind_layout);
        cancel_btn = (TextView) findViewById(R.id.cancel_btn);
        sure_btn = (TextView) findViewById(R.id.sure_btn);
        sure_btn.setBackground(getResources().getDrawable(R.drawable.null_btn_selector));

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitToLogin();
            }
        });

        sure_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyApplication.isBind){
                    finish();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 把正在使用的这台设备放到未绑定数据列表中
     */
    private void initData(){
        DeviceItemEntity entity = new DeviceItemEntity();
        entity.name = ""+android.os.Build.MODEL;
        entity.udid = MyApplication.serial_device+"a";
        unbindedEntities.add(entity);

        DeviceItemEntity entitys = new DeviceItemEntity();
        entitys.name = ""+android.os.Build.MODEL;
        entitys.udid = MyApplication.serial_device+"ab";
        unbindedEntities.add(entitys);

    }

    /**
     * 获取绑定设备
     */
    private void getBindDevices(){
        EloadingDialog.ShowDialog(BindListActivity.this);
        deviceManageUtil.getBindDevices(mHandler);
    }


}
