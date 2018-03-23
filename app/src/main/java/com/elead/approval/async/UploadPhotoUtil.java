package com.elead.approval.async;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.mygallery.model.PhotoInfo;


public class UploadPhotoUtil {
    private List<PhotoInfo> lists;
    private Context context;
    private List<String> currurls;
    private OnUploadLinstener onUploadLinstener;

    private UploadPhotoUtil(List<PhotoInfo> lists, Context context, OnUploadLinstener onUploadLinstener) {
        this.context = context;
        this.lists = lists;
        this.onUploadLinstener = onUploadLinstener;

    }

    private static class UploadPhotoUtilHolder {
        public static UploadPhotoUtil uploadPhotoUtil;
    }

    public static UploadPhotoUtil getInstance(List<PhotoInfo> lists, Context context, OnUploadLinstener onUploadLinstener) {
        if (UploadPhotoUtilHolder.uploadPhotoUtil == null) {
            UploadPhotoUtilHolder.uploadPhotoUtil = new UploadPhotoUtil(lists, context, onUploadLinstener);
        }
        return UploadPhotoUtilHolder.uploadPhotoUtil;
    }

    public void upLoad() {
        if (lists.size() == 0) {
            onUploadLinstener.onSuccess(new ArrayList<String>());
            return;
        }
        String[] strings = new String[lists.size()];
        for (int i = 0; i < lists.size(); i++) {
            strings[i] = lists.get(i).getPhotoPath();
        }
//        BmobFile.uploadBatch(strings, new UploadBatchListener() {
//
//            @Override
//            public void onSuccess(List<BmobFile> files, List<String> urls) {
//                currurls = urls;
//            }
//
//            @Override
//            public void onError(int statuscode, String errormsg) {
//                onUploadLinstener.onError(currurls, errormsg);
//            }
//
//            @Override
//            public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {
//                Log.d("aa", "curPercent: " + curPercent + "totalPercent: " + totalPercent + "curIndex: " + curIndex + "total: " + total);
//                if (100 == curPercent) {
//                    onUploadLinstener.onProgress(curIndex, total);
//                    if (100 == totalPercent) {
//                        onUploadLinstener.onSuccess(currurls);
//                    }
//                }
//
//            }
//        });
    }


    /**
     * 新增文件下载方法
     *
     *
     * */

//    private void downloadFile(BmobFile file) {
////		if(file==null){
////			file = new BmobFile("haha", "", "http://bmob-cdn-4.b0.upaiyun.com/png/11bf594aad96493e99a3c866eceb8184.png");
////		}
//        File saveFile = new File(Environment.getExternalStorageDirectory(), file.getFilename());
//        file.download(saveFile, new DownloadFileListener() {
//
//            @Override
//            public void onStart() {
//
//            }
//
//            @Override
//            public void done(String s, BmobException e) {
//                if (e == null) {
//                } else {
//                }
//            }
//
//            @Override
//            public void onProgress(Integer value, long newworkSpeed) {
//            }
//
//        });
//    }

    public interface OnUploadLinstener {
        void onProgress(int currIndex, int totalCount);

        void onSuccess(List<String> imgUrls);

        void onError(List<String> imgUrls, String errormsg);
    }


}