/*
 * Copyright (C) 2014 pengjianbo(pengjianbosoft@gmail.com), Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package cn.finalteam.mygallery;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.finalteam.mygallery.model.PhotoInfo;
import cn.finalteam.mygallery.toolsfinal.ActivityManager;
import cn.finalteam.mygallery.toolsfinal.DateUtils;
import cn.finalteam.mygallery.toolsfinal.DeviceUtils;
import cn.finalteam.mygallery.toolsfinal.StringUtils;
import cn.finalteam.mygallery.toolsfinal.io.FileUtils;

/**
 * Desction:
 * Author:pengjianbo
 * Date:15/10/10 下午5:46
 */
public abstract class PhotoBaseActivity extends FragmentActivity implements cn.finalteam.mygallery.permission.EasyPermissions.PermissionCallbacks {

    protected static String mPhotoTargetFolder;

    private Uri mTakePhotoUri;
    private cn.finalteam.mygallery.utils.MediaScanner mMediaScanner;

    protected int mScreenWidth = 720;
    protected int mScreenHeight = 1280;

    protected boolean mTakePhotoAction;//打开相机动作
    private ActionBar actionBar;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("takePhotoUri", mTakePhotoUri);
        outState.putString("photoTargetFolder", mPhotoTargetFolder);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mTakePhotoUri = savedInstanceState.getParcelable("takePhotoUri");
        mPhotoTargetFolder = savedInstanceState.getString("photoTargetFolder");
    }

    protected Handler mFinishHanlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            finishGalleryFinalPage();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getActivityManager().addActivity(this);
        mMediaScanner = new cn.finalteam.mygallery.utils.MediaScanner(this);
        DisplayMetrics dm = DeviceUtils.getScreenPix(this);
        mScreenWidth = dm.widthPixels;
        mScreenHeight = dm.heightPixels;

    }

    @Override
    public void setTitle(CharSequence title) {
        actionBar = getActionBar();
        if (null == actionBar) {
            return;
        }
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setBackgroundDrawable(null);
        actionBar.removeAllTabs();//
        actionBar.setCustomView(R.layout.my_gf_title);//自定义ActionBar布局
        TextView viewById = (TextView) actionBar.getCustomView().findViewById(R.id.title);
        viewById.setText(title);
        actionBar.getCustomView().setOnClickListener(new View.OnClickListener() {//监听事件
            @Override
            public void onClick(View arg0) {
                int i = arg0.getId();
                if (i == R.id.back) {
                    onBackPressed();

                } else {
                }
            }
        });

    }

    public void setTitleView(View view) {
        actionBar = getActionBar();
//        if (null == actionBar) {
//            return;
//        }
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setBackgroundDrawable(null);
        actionBar.removeAllTabs();//
        actionBar.setCustomView(view);//自定义ActionBar布局
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaScanner != null) {
            mMediaScanner.unScanFile();
        }
        ActivityManager.getActivityManager().finishActivity(this);
    }

    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 拍照
     */
    protected void takePhotoAction() {
        if (!DeviceUtils.existSDCard()) {
            String errormsg = getString(R.string.empty_sdcard);
            toast(errormsg);
            if (mTakePhotoAction) {
                resultFailure(errormsg, true);
            }
            return;
        }

        File takePhotoFolder = null;
        if (StringUtils.isEmpty(mPhotoTargetFolder)) {
            takePhotoFolder = GalleryFinal.getCoreConfig().getTakePhotoFolder();
        } else {
            takePhotoFolder = new File(mPhotoTargetFolder);
        }
        boolean suc = FileUtils.mkdirs(takePhotoFolder);
        File toFile = new File(takePhotoFolder, "IMG" + DateUtils.format(new Date(), "yyyyMMddHHmmss") + ".jpg");

        if (suc) {
            mTakePhotoUri = Uri.fromFile(toFile);
            Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mTakePhotoUri);
            startActivityForResult(captureIntent, GalleryFinal.TAKE_REQUEST_CODE);
        } else {
            takePhotoFailure();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GalleryFinal.TAKE_REQUEST_CODE) {
            if (resultCode == RESULT_OK && mTakePhotoUri != null) {
                final String path = mTakePhotoUri.getPath();
//                    try {
//                        String s = MediaStore.Images.Media.insertImage(getContentResolver(), path, "", "");
//                        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//                        Uri uri = Uri.fromFile(new File(s));
//                        intent.setData(uri);
//                        sendBroadcast(intent);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
                if (new File(path).exists()) {
                    final PhotoInfo info = new PhotoInfo();
                    info.setPhotoId(cn.finalteam.mygallery.utils.Utils.getRandom(10000, 99999));
                    info.setPhotoPath(path);
                    updateGallery(path);
                    takeResult(info);
                } else {
                    takePhotoFailure();
                }
            } else {
                takePhotoFailure();
            }
        }
    }

    private void takePhotoFailure() {
        String errormsg = getString(R.string.take_photo_fail);
        if (mTakePhotoAction) {
            resultFailure(errormsg, true);
        } else {
            toast(errormsg);
        }
    }

    /**
     * 更新相册
     */
    private void updateGallery(String filePath) {
        if (mMediaScanner != null) {
            mMediaScanner.scanFile(filePath, "image/jpeg");
        }
    }

    protected void resultData(ArrayList<cn.finalteam.mygallery.model.PhotoInfo> photoList) {
        GalleryFinal.OnHanlderResultCallback callback = GalleryFinal.getCallback();
        int requestCode = GalleryFinal.getRequestCode();
        if (callback != null) {
            if (photoList != null && photoList.size() > 0) {
                callback.onHanlderSuccess(requestCode, photoList);
            } else {
                callback.onHanlderFailure(requestCode, getString(R.string.photo_list_empty));
            }
        }
        finishGalleryFinalPage();
    }

    protected void resultFailureDelayed(String errormsg, boolean delayFinish) {
        GalleryFinal.OnHanlderResultCallback callback = GalleryFinal.getCallback();
        int requestCode = GalleryFinal.getRequestCode();
        if (callback != null) {
            callback.onHanlderFailure(requestCode, errormsg);
        }
        if (delayFinish) {
            mFinishHanlder.sendEmptyMessageDelayed(0, 500);
        } else {
            finishGalleryFinalPage();
        }
    }

    protected void resultFailure(String errormsg, boolean delayFinish) {
        GalleryFinal.OnHanlderResultCallback callback = GalleryFinal.getCallback();
        int requestCode = GalleryFinal.getRequestCode();
        if (callback != null) {
            callback.onHanlderFailure(requestCode, errormsg);
        }
        if (delayFinish) {
            finishGalleryFinalPage();
        } else {
            finishGalleryFinalPage();
        }
    }

    private void finishGalleryFinalPage() {
        ActivityManager.getActivityManager().finishActivity(PhotoEditActivity.class);
        ActivityManager.getActivityManager().finishActivity(PhotoSelectActivity.class);
        Global.mPhotoSelectActivity = null;
        System.gc();
    }

    protected abstract void takeResult(cn.finalteam.mygallery.model.PhotoInfo info);

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // Forward results to EasyPermissions
        cn.finalteam.mygallery.permission.EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(List<String> list) {
    }

    @Override
    public void onPermissionsDenied(List<String> list) {
    }
}
