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

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import cn.finalteam.mygallery.model.PhotoInfo;
import cn.finalteam.mygallery.toolsfinal.DeviceUtils;
import cn.finalteam.mygallery.toolsfinal.StringUtils;
import cn.finalteam.mygallery.toolsfinal.io.FileUtils;


/**
 * Desction:
 * Author:pengjianbo
 * Date:15/12/2 上午11:05
 */
public class GalleryFinal {
    static final int TAKE_REQUEST_CODE = 1001;

    static final int PERMISSIONS_CODE_GALLERY = 2001;

    private static FunctionConfig mCurrentFunctionConfig;
    private static FunctionConfig mGlobalFunctionConfig;
    private static CoreConfig mCoreConfig;

    private static OnHanlderResultCallback mCallback;
    private static int mRequestCode;

    public static void init(CoreConfig coreConfig) {
        mCoreConfig = coreConfig;
        mGlobalFunctionConfig = coreConfig.getFunctionConfig();
    }

    public static FunctionConfig copyGlobalFuncationConfig() {
        if (mGlobalFunctionConfig != null) {
            return mGlobalFunctionConfig.clone();
        }
        return null;
    }

    public static CoreConfig getCoreConfig() {
        return mCoreConfig;
    }

    public static FunctionConfig getFunctionConfig() {
        return mCurrentFunctionConfig;
    }

    /**
     * 打开Gallery-单选
     *
     * @param requestCode
     * @param callback
     */
    public static void openGallerySingle(int requestCode, OnHanlderResultCallback callback) {
        FunctionConfig config = copyGlobalFuncationConfig();
        if (config != null) {
            openGallerySingle(requestCode, config, callback);
        } else {
            if (callback != null) {
                callback.onHanlderFailure(requestCode, mCoreConfig.getContext().getString(R.string.open_gallery_fail));
            }
        }
    }

    /**
     * 打开Gallery-单选
     *
     * @param requestCode
     * @param config
     * @param callback
     */
    public static void openGallerySingle(int requestCode, FunctionConfig config, OnHanlderResultCallback callback) {
        if (mCoreConfig.getImageLoader() == null) {
           
            if (callback != null) {
                callback.onHanlderFailure(requestCode, mCoreConfig.getContext().getString(R.string.open_gallery_fail));
            }
            return;
        }

        if (config == null && mGlobalFunctionConfig == null) {
            if (callback != null) {
                callback.onHanlderFailure(requestCode, mCoreConfig.getContext().getString(R.string.open_gallery_fail));
            }
            return;
        }

        if (!DeviceUtils.existSDCard()) {
            Toast.makeText(mCoreConfig.getContext(), R.string.empty_sdcard, Toast.LENGTH_SHORT).show();
            return;
        }
        mRequestCode = requestCode;
        mCallback = callback;
        mCurrentFunctionConfig = config;

        Intent intent = new Intent(mCoreConfig.getContext(), PhotoSelectActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mCoreConfig.getContext().startActivity(intent);
    }

    /**
     * 打开Gallery-
     *
     * @param requestCode
     * @param maxSize
     * @param callback
     */
    public static void openGalleryMuti(int requestCode, int maxSize, OnHanlderResultCallback callback) {
        FunctionConfig config = copyGlobalFuncationConfig();
        if (config != null) {
            config.maxSize = maxSize;
            openGalleryMuti(requestCode, config, callback);
        } else {
            if (callback != null) {
                callback.onHanlderFailure(requestCode, mCoreConfig.getContext().getString(R.string.open_gallery_fail));
            }
           
        }
    }

    /**
     * 打开Gallery-多选
     *
     * @param requestCode
     * @param config
     * @param callback
     */
    public static void openGalleryMuti(int requestCode, FunctionConfig config, OnHanlderResultCallback callback) {
        if (mCoreConfig.getImageLoader() == null) {
           
            if (callback != null) {
                callback.onHanlderFailure(requestCode, mCoreConfig.getContext().getString(R.string.open_gallery_fail));
            }
            return;
        }

        if (config == null && mGlobalFunctionConfig == null) {
            if (callback != null) {
                callback.onHanlderFailure(requestCode, mCoreConfig.getContext().getString(R.string.open_gallery_fail));
            }
            return;
        }

        if (config.getMaxSize() <= 0) {
            if (callback != null) {
                callback.onHanlderFailure(requestCode, mCoreConfig.getContext().getString(R.string.maxsize_zero_tip));
            }
            return;
        }

//        if (config.getSelectedList() != null && config.getSelectedList().size() > config.getMaxSize()) {
//            if (callback != null) {
//                callback.onHanlderFailure(requestCode, mCoreConfig.getContext().getString(R.string.select_max_tips));
//            }
//            return;
//        }

        if (!DeviceUtils.existSDCard()) {
            Toast.makeText(mCoreConfig.getContext(), R.string.empty_sdcard, Toast.LENGTH_SHORT).show();
            return;
        }

        mRequestCode = requestCode;
        mCallback = callback;
        mCurrentFunctionConfig = config;


        Intent intent = new Intent(mCoreConfig.getContext(), PhotoSelectActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mCoreConfig.getContext().startActivity(intent);
    }


    /**
     * 打开相机
     *
     * @param requestCode
     * @param callback
     */
    public static void openCamera(int requestCode, OnHanlderResultCallback callback) {
        FunctionConfig config = copyGlobalFuncationConfig();
        if (config != null) {
            openCamera(requestCode, config, callback);
        } else {
            if (callback != null) {
                callback.onHanlderFailure(requestCode, mCoreConfig.getContext().getString(R.string.open_gallery_fail));
            }
           
        }
    }

    /**
     * 打开相机
     *
     * @param config
     * @param callback
     */
    public static void openCamera(int requestCode, FunctionConfig config, OnHanlderResultCallback callback) {
        if (mCoreConfig.getImageLoader() == null) {
           
            if (callback != null) {
                callback.onHanlderFailure(requestCode, mCoreConfig.getContext().getString(R.string.open_gallery_fail));
            }
            return;
        }

        if (config == null && mGlobalFunctionConfig == null) {
            if (callback != null) {
                callback.onHanlderFailure(requestCode, mCoreConfig.getContext().getString(R.string.open_gallery_fail));
            }
            return;
        }

        if (!DeviceUtils.existSDCard()) {
            Toast.makeText(mCoreConfig.getContext(), R.string.empty_sdcard, Toast.LENGTH_SHORT).show();
            return;
        }

        mRequestCode = requestCode;
        mCallback = callback;
        mCurrentFunctionConfig = config;

        Intent intent = new Intent(mCoreConfig.getContext(), PhotoEditActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(PhotoEditActivity.TAKE_PHOTO_ACTION, true);
        mCoreConfig.getContext().startActivity(intent);
    }

    /**
     * 打开裁剪
     *
     * @param requestCode
     * @param photoPath
     * @param callback
     */
    public static void openCrop(int requestCode, String photoPath, OnHanlderResultCallback callback) {
        FunctionConfig config = copyGlobalFuncationConfig();
        if (config != null) {
            openCrop(requestCode, config, photoPath, callback);
        } else {
            if (callback != null) {
                callback.onHanlderFailure(requestCode, mCoreConfig.getContext().getString(R.string.open_gallery_fail));
            }
           
        }
    }

    /**
     * 打开裁剪
     *
     * @param requestCode
     * @param config
     * @param photoPath
     * @param callback
     */
    public static void openCrop(int requestCode, FunctionConfig config, String photoPath, OnHanlderResultCallback callback) {
        if (mCoreConfig.getImageLoader() == null) {
           
            if (callback != null) {
                callback.onHanlderFailure(requestCode, mCoreConfig.getContext().getString(R.string.open_gallery_fail));
            }
            return;
        }

        if (config == null && mGlobalFunctionConfig == null) {
            if (callback != null) {
                callback.onHanlderFailure(requestCode, mCoreConfig.getContext().getString(R.string.open_gallery_fail));
            }
            return;
        }

        if (!DeviceUtils.existSDCard()) {
            Toast.makeText(mCoreConfig.getContext(), R.string.empty_sdcard, Toast.LENGTH_SHORT).show();
            return;
        }

        if (config == null || StringUtils.isEmpty(photoPath) || !new File(photoPath).exists()) {
            Log.d("galleryfinal","config为空或文件不存在");
            return;
        }
        mRequestCode = requestCode;
        mCallback = callback;

        //必须设置这个三个选项
        config.editPhoto = true;
        config.crop = true;

        mCurrentFunctionConfig = config;
        ArrayList<PhotoInfo> map = new ArrayList<>();
        PhotoInfo photoInfo = new PhotoInfo();
        photoInfo.setPhotoPath(photoPath);
        photoInfo.setPhotoId(cn.finalteam.mygallery.utils.Utils.getRandom(10000, 99999));
        map.add(photoInfo);
        Intent intent = new Intent(mCoreConfig.getContext(), PhotoEditActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(PhotoEditActivity.CROP_PHOTO_ACTION, true);
        intent.putExtra(PhotoEditActivity.SELECT_MAP, map);
        mCoreConfig.getContext().startActivity(intent);
    }

    /**
     * 打开编辑
     *
     * @param requestCode
     * @param photoPath
     * @param callback
     */
    public static void openEdit(int requestCode, String photoPath, OnHanlderResultCallback callback) {
        FunctionConfig config = copyGlobalFuncationConfig();
        if (config != null) {
            openEdit(requestCode, config, photoPath, callback);
        } else {
            if (callback != null) {
                callback.onHanlderFailure(requestCode, mCoreConfig.getContext().getString(R.string.open_gallery_fail));
            }
           
        }
    }

    /**
     * 打开编辑
     *
     * @param requestCode
     * @param config
     * @param photoPath
     * @param callback
     */
    public static void openEdit(int requestCode, FunctionConfig config, String photoPath, OnHanlderResultCallback callback) {
        if (mCoreConfig.getImageLoader() == null) {
           
            if (callback != null) {
                callback.onHanlderFailure(requestCode, mCoreConfig.getContext().getString(R.string.open_gallery_fail));
            }
            return;
        }

        if (config == null && mGlobalFunctionConfig == null) {
            if (callback != null) {
                callback.onHanlderFailure(requestCode, mCoreConfig.getContext().getString(R.string.open_gallery_fail));
            }
            return;
        }

        if (!DeviceUtils.existSDCard()) {
            Toast.makeText(mCoreConfig.getContext(), R.string.empty_sdcard, Toast.LENGTH_SHORT).show();
            return;
        }

        if (config == null || StringUtils.isEmpty(photoPath) || !new File(photoPath).exists()) {
            Log.d("galleryfinal","config为空或文件不存在");
            return;
        }
        mRequestCode = requestCode;
        mCallback = callback;

        mCurrentFunctionConfig = config;
        ArrayList<PhotoInfo> map = new ArrayList<>();
        PhotoInfo photoInfo = new PhotoInfo();
        photoInfo.setPhotoPath(photoPath);
        photoInfo.setPhotoId(cn.finalteam.mygallery.utils.Utils.getRandom(10000, 99999));
        map.add(photoInfo);
        Intent intent = new Intent(mCoreConfig.getContext(), PhotoEditActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(PhotoEditActivity.EDIT_PHOTO_ACTION, true);
        intent.putExtra(PhotoEditActivity.SELECT_MAP, map);
        mCoreConfig.getContext().startActivity(intent);
    }

    /**
     * 清楚缓存文件
     */
    public static void cleanCacheFile() {
        if (mCurrentFunctionConfig != null && mCoreConfig.getEditPhotoCacheFolder() != null) {
            //清楚裁剪冗余图片
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        FileUtils.deleteDirectory(mCoreConfig.getEditPhotoCacheFolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }

    public static int getRequestCode() {
        return mRequestCode;
    }

    public static OnHanlderResultCallback getCallback() {
        return mCallback;
    }

    /**
     * 处理结果
     */
    public static interface OnHanlderResultCallback {
        /**
         * 处理成功
         *
         * @param reqeustCode
         * @param resultList
         */
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList);

        /**
         * 处理失败或异常
         *
         * @param requestCode
         * @param errorMsg
         */
        public void onHanlderFailure(int requestCode, String errorMsg);
    }
}
