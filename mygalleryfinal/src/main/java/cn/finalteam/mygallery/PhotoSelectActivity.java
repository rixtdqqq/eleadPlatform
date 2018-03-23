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

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.finalteam.mygallery.adapter.FolderListAdapter;
import cn.finalteam.mygallery.adapter.PhotoListAdapter;
import cn.finalteam.mygallery.model.PhotoFolderInfo;
import cn.finalteam.mygallery.model.PhotoInfo;
import cn.finalteam.mygallery.permission.AfterPermissionGranted;
import cn.finalteam.mygallery.permission.EasyPermissions;
import cn.finalteam.mygallery.toolsfinal.DeviceUtils;
import cn.finalteam.mygallery.toolsfinal.StringUtils;
import cn.finalteam.mygallery.utils.PhotoTools;
import cn.finalteam.mygallery.widget.CusCheckBox;

import static android.view.animation.AnimationUtils.loadAnimation;
import static cn.finalteam.mygallery.PhotoSelectActivity.ShowState.allphoto;
import static cn.finalteam.mygallery.PhotoSelectActivity.ShowState.folderphoto;
import static cn.finalteam.mygallery.PhotoSelectActivity.ShowState.prewview;
import static cn.finalteam.mygallery.R.id.back;

/**
 * Desction:图片选择器
 * Author:pengjianbo
 * Date:15/10/10 下午3:54
 */
public class PhotoSelectActivity extends PhotoBaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, PhotoListAdapter.onChangeLinstener {

    private final int HANLDER_TAKE_PHOTO_EVENT = 1000;
    private final int HANDLER_REFRESH_LIST_EVENT = 1002;
    private static ShowState CURR_STATE = allphoto;

    public enum ShowState {
        allphoto, folder, folderphoto, prewview
    }

    private GridView mGvPhotoList;
    private ListView mLvFolderList;
    private LinearLayout mLlFolderPanel;
    private Button btn_show_llfloder;
    private Button btn_preview;
    private TextView mTvEmptyView;

    private List<PhotoFolderInfo> mAllPhotoFolderList;
    private FolderListAdapter mFolderListAdapter;

    private ArrayList<PhotoInfo> mCurPhotoList;
    private PhotoListAdapter mPhotoListAdapter;

    //是否需要刷新相册
    private boolean mHasRefreshGallery = false;
    private ArrayList<PhotoInfo> mSelectPhotoList = new ArrayList<>();
    private LinearLayout linback;
    private Button btn_sub;
    private PhotoPreviewFragment photoPreviewFragment;
    private View bottomral, bottom_preview;
    private View bottom_right_lin, bottom_left_lin;
    public CusCheckBox leftCheckbox, rightCheckbox;


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("selectPhotoMap", mSelectPhotoList);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mSelectPhotoList = (ArrayList<PhotoInfo>) getIntent().getSerializableExtra("selectPhotoMap");
    }

    private Handler mHanlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == HANLDER_TAKE_PHOTO_EVENT) {
                PhotoInfo photoInfo = (PhotoInfo) msg.obj;
                takeRefreshGallery(photoInfo);
                refreshSelectCount();
            } else if (msg.what == HANDLER_REFRESH_LIST_EVENT) {
                refreshSelectCount();
                mPhotoListAdapter.notifyDataSetChanged();
                mFolderListAdapter.notifyDataSetChanged();
                if (mAllPhotoFolderList.get(0).getPhotoList() == null ||
                        mAllPhotoFolderList.get(0).getPhotoList().size() == 0) {
                    mTvEmptyView.setText(R.string.no_photo);
                }

                mGvPhotoList.setEnabled(true);
                btn_show_llfloder.setEnabled(true);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (GalleryFinal.getFunctionConfig() == null) {
            resultFailureDelayed(getString(R.string.please_reopen_gf), true);
        } else {
            setContentView(R.layout.my_gf_activity_photo_select);
            mPhotoTargetFolder = null;
            setWindowStatusBarColor(this, Color.parseColor("#2ec7c9"));
            findViews();
            setListener();

            mAllPhotoFolderList = new ArrayList<>();
            mFolderListAdapter = new FolderListAdapter(this, mAllPhotoFolderList, GalleryFinal.getFunctionConfig());
            mLvFolderList.setAdapter(mFolderListAdapter);

            mCurPhotoList = new ArrayList<>();
            mPhotoListAdapter = new PhotoListAdapter(this, mCurPhotoList, mScreenWidth, this);
            mGvPhotoList.setAdapter(mPhotoListAdapter);
            mGvPhotoList.setEmptyView(mTvEmptyView);

            refreshSelectCount();
            requestGalleryPermission();

            mGvPhotoList.setOnScrollListener(GalleryFinal.getCoreConfig().getPauseOnScrollListener());
        }

        Global.mPhotoSelectActivity = this;
    }


    private void findViews() {
        mGvPhotoList = (GridView) findViewById(R.id.gv_photo_list);
        mLvFolderList = (ListView) findViewById(R.id.lv_folder_list);
        mLlFolderPanel = (LinearLayout) findViewById(R.id.ll_folder_panel);


        mTvEmptyView = (TextView) findViewById(R.id.tv_empty_view);
        btn_show_llfloder = (Button) findViewById(R.id.btn_show_llfloder);
        btn_preview = (Button) findViewById(R.id.btn_preview);

        bottomral = findViewById(R.id.bottom_ral);
        bottom_preview = findViewById(R.id.bottom_preview);
        View inflate = LinearLayout.inflate(this, R.layout.my_gf_title, null);
        setTitleView(inflate);
        btn_sub = (Button) getActionBar().getCustomView().findViewById(R.id.btn_sub);
        linback = (LinearLayout) getActionBar().getCustomView().findViewById(back);


        bottom_right_lin = findViewById(R.id.bottom_right_lin);
        bottom_left_lin = findViewById(R.id.bottom_left_lin);
        leftCheckbox = (CusCheckBox) findViewById(R.id.check_left);
        rightCheckbox = (CusCheckBox) findViewById(R.id.check_right);
    }

    public void settitleText(String str) {
        TextView tv_title = (TextView) getActionBar().getCustomView().findViewById(R.id.title);
        tv_title.setText(str);
    }

    private void setListener() {
        linback.setOnClickListener(this);
        btn_sub.setOnClickListener(this);
        btn_preview.setOnClickListener(this);
        btn_show_llfloder.setOnClickListener(this);
        mLvFolderList.setOnItemClickListener(this);
        mGvPhotoList.setOnItemClickListener(this);
        bottom_left_lin.setOnClickListener(this);
        bottom_right_lin.setOnClickListener(this);
        leftCheckbox.setOnClickListener(this);
        rightCheckbox.setOnClickListener(this);
    }

    protected void deleteSelect(int photoId) {
        try {
            for (Iterator<PhotoInfo> iterator = mSelectPhotoList.iterator(); iterator.hasNext(); ) {
                PhotoInfo info = iterator.next();
                if (info != null && info.getPhotoId() == photoId) {
                    iterator.remove();
                    break;
                }
            }
        } catch (Exception e) {
        }

        refreshAdapter();
    }


    private void refreshAdapter() {
        mHanlder.sendEmptyMessageDelayed(HANDLER_REFRESH_LIST_EVENT, 100);
    }

    protected void takeRefreshGallery(PhotoInfo photoInfo, boolean selected) {
        if (isFinishing() || photoInfo == null) {
            return;
        }

        Message message = mHanlder.obtainMessage();
        message.obj = photoInfo;
        message.what = HANLDER_TAKE_PHOTO_EVENT;
        mSelectPhotoList.add(photoInfo);
        mHanlder.sendMessageDelayed(message, 100);
    }

    /**
     * 解决在5.0手机上刷新Gallery问题，从startActivityForResult回到Activity把数据添加到集合中然后理解跳转到下一个页面，
     * adapter的getCount与list.size不一致，所以我这里用了延迟刷新数据
     *
     * @param photoInfo
     */
    private void takeRefreshGallery(PhotoInfo photoInfo) {
        mCurPhotoList.add(0, photoInfo);
        mPhotoListAdapter.notifyDataSetChanged();

        //添加到集合中
        List<PhotoInfo> photoInfoList = mAllPhotoFolderList.get(0).getPhotoList();
        if (photoInfoList == null) {
            photoInfoList = new ArrayList<>();
        }
        photoInfoList.add(1, photoInfo);
        mAllPhotoFolderList.get(0).setPhotoList(photoInfoList);

        if (mFolderListAdapter.getSelectFolder() != null) {
            PhotoFolderInfo photoFolderInfo = mFolderListAdapter.getSelectFolder();
            List<PhotoInfo> list = photoFolderInfo.getPhotoList();
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(1, photoInfo);
            if (list.size() == 1) {
                photoFolderInfo.setCoverPhoto(photoInfo);
            }
            mFolderListAdapter.getSelectFolder().setPhotoList(list);
        } else {
            String folderA = new File(photoInfo.getPhotoPath()).getParent();
            for (int i = 1; i < mAllPhotoFolderList.size(); i++) {
                PhotoFolderInfo folderInfo = mAllPhotoFolderList.get(i);
                String folderB = null;
                if (!StringUtils.isEmpty(photoInfo.getPhotoPath())) {
                    folderB = new File(photoInfo.getPhotoPath()).getParent();
                }
                if (TextUtils.equals(folderA, folderB)) {
                    List<PhotoInfo> list = folderInfo.getPhotoList();
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(1, photoInfo);
                    folderInfo.setPhotoList(list);
                    if (list.size() == 1) {
                        folderInfo.setCoverPhoto(photoInfo);
                    }
                }
            }
        }
    }

    @Override
    protected void takeResult(PhotoInfo photoInfo) {

        Message message = mHanlder.obtainMessage();
        message.obj = photoInfo;
        message.what = HANLDER_TAKE_PHOTO_EVENT;
        mSelectPhotoList.add(photoInfo);
        mHanlder.sendMessageDelayed(message, 300);
    }

    /**
     * 执行裁剪
     */
    protected void toPhotoEdit() {
        Intent intent = new Intent(this, PhotoEditActivity.class);
        intent.putExtra(PhotoEditActivity.SELECT_MAP, mSelectPhotoList);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_show_llfloder) {
            if (mLlFolderPanel.getVisibility() == View.VISIBLE) {
                mLlFolderPanel.setVisibility(View.GONE);
                mLlFolderPanel.setAnimation(loadAnimation(this, R.anim.my_gf_flip_horizontal_out));
            } else {
                mLlFolderPanel.setAnimation(loadAnimation(this, R.anim.my_gf_flip_horizontal_in));
                mLlFolderPanel.setVisibility(View.VISIBLE);
                CURR_STATE = ShowState.folder;
            }
        }/* else if (id == R.id.iv_take_photo) {
            //判断是否达到多选最大数量
            if (GalleryFinal.getFunctionConfig().isMutiSelect() && mSelectPhotoList.size() == GalleryFinal.getFunctionConfig().getMaxSize()) {
                toast(getString(R.string.select_max_tips));
                return;
            }

            if (!DeviceUtils.existSDCard()) {
                toast(getString(R.string.empty_sdcard));
                return;
            }

            takePhotoAction();
        }*/ else if (id == back) {
            onBackPressed();
        } else if (id == R.id.btn_sub) {
            if (mSelectPhotoList.size() > 0) {
                if (!GalleryFinal.getFunctionConfig().isEditPhoto()) {
                    for (PhotoInfo info : mSelectPhotoList) {
                        if (!info.isCheck())
                            mSelectPhotoList.remove(info);
                    }
                    resultData(mSelectPhotoList);
                } else {
                    toPhotoEdit();
                }
            }
        } /*else if (id == R.id.iv_clear) {
            mSelectPhotoList.clear();
            mPhotoListAdapter.notifyDataSetChanged();
            refreshSelectCount();
        }*/ else if (id == R.id.btn_preview) {
            openFragment(mSelectPhotoList, 0);
        } else if (id == R.id.bottom_left_lin || id == R.id.check_left) {
            if (id != R.id.check_left) {
                leftCheckbox.setChecked(!leftCheckbox.isChecked());
            }

        } else if (id == R.id.bottom_right_lin || id != R.id.check_right) {
            if (id != R.id.check_right) {
                rightCheckbox.setChecked(!rightCheckbox.isChecked());
            }
            PhotoInfo photoInfo = photoPreviewFragment.gettCheckPhoto(rightCheckbox.isChecked());
            if (mSelectPhotoList.contains(photoInfo)) {
                mSelectPhotoList.get(mSelectPhotoList.indexOf(photoInfo)).setCheck(rightCheckbox.isChecked());
            } else {
                mSelectPhotoList.add(photoInfo);
            }
            refreshSelectCount();
        }
    }

    private void openFragment(List<PhotoInfo> list, int position) {
        if (CURR_STATE == ShowState.folder) {
            CURR_STATE = allphoto;
            mLlFolderPanel.setVisibility(View.GONE);
            mLlFolderPanel.setAnimation(loadAnimation(this, R.anim.my_gf_flip_horizontal_out));
        }
        if (CURR_STATE != prewview) {
            CURR_STATE = prewview;
            photoPreviewFragment = new PhotoPreviewFragment(list, position);
            getSupportFragmentManager().beginTransaction()
                    .replace
                            (R.id.fl, photoPreviewFragment, photoPreviewFragment.getClass()
                                    .getSimpleName())
                    .addToBackStack(photoPreviewFragment.getClass()
                            .getSimpleName()).commit();
            showPreviewBottomView();
        } else {
            CURR_STATE = allphoto;
            getSupportFragmentManager().popBackStack();
            photoPreviewFragment = null;
        }
    }

    View firstV = null;
    View secondV = null;
    private boolean isShowPreview;

    public void showPreviewBottomView() {

        if (bottom_preview.getVisibility() == View.VISIBLE) {
            firstV = bottom_preview;
            secondV = bottomral;
            isShowPreview = false;
        } else if (bottomral.getVisibility() == View.VISIBLE) {
            firstV = bottomral;
            secondV = bottom_preview;
            isShowPreview = true;
        }

        Animation animation1Out = AnimationUtils.loadAnimation(this, R.anim.my_gf_flip_horizontal_out);
        animation1Out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                firstV.setVisibility(View.GONE);
                secondV.setVisibility(View.VISIBLE);
                Animation animation1IN = AnimationUtils.loadAnimation(PhotoSelectActivity.this, R.anim.my_gf_flip_horizontal_in);
                secondV.setAnimation(animation1IN);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        firstV.setAnimation(animation1Out);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int parentId = parent.getId();
        if (parentId == R.id.lv_folder_list) {
            folderItemClick(position);
        } else if (position != 0) {
            photoItemClick(position, view);
        }
    }

    boolean isEnerFolder = false;

    private void folderItemClick(int position) {
        isEnerFolder = true;
        CURR_STATE = folderphoto;
        mLlFolderPanel.setVisibility(View.GONE);
        mCurPhotoList.clear();
        PhotoFolderInfo photoFolderInfo = mAllPhotoFolderList.get(position);
        if (photoFolderInfo.getPhotoList() != null) {
            mCurPhotoList.addAll(photoFolderInfo.getPhotoList());
        }
        mPhotoListAdapter.notifyDataSetChanged();

        if (position == 0) {
            mPhotoTargetFolder = null;
        } else {
            PhotoInfo photoInfo = photoFolderInfo.getCoverPhoto();
            if (photoInfo != null && !StringUtils.isEmpty(photoInfo.getPhotoPath())) {
                mPhotoTargetFolder = new File(photoInfo.getPhotoPath()).getParent();
            } else {
                mPhotoTargetFolder = null;
            }
        }
        mFolderListAdapter.setSelectFolder(photoFolderInfo);
        mFolderListAdapter.notifyDataSetChanged();

        if (mCurPhotoList.size() == 0) {
            mTvEmptyView.setText(R.string.no_photo);
        }
    }

    private void photoItemClick(int position, View view) {
        openFragment(mCurPhotoList, position);
    }


    private boolean photoItemCheck(int position, View view) {
        PhotoInfo info = mCurPhotoList.get(position);
        if (!info.isCheck()) {
            if (mSelectPhotoList.size() >= GalleryFinal.getFunctionConfig().getMaxSize()) {
                toast(getString(R.string.select_max_tips));
                return false;
            } else if (!mSelectPhotoList.contains(info)) {
                mSelectPhotoList.add(info);
                info.setCheck(true);
            }
        } else {
            info.setCheck(false);
            mSelectPhotoList.remove(info);
        }
        refreshSelectCount();
        return true;
    }

    public void refreshSelectCount() {
        int size = mSelectPhotoList.size();
        if (mSelectPhotoList.size() > 0) {
            btn_preview.setEnabled(true);
            btn_sub.setEnabled(true);
            btn_sub.setText(getString(R.string.sure, size, GalleryFinal.getFunctionConfig().getMaxSize()));
            btn_preview.setText(getString(R.string.preview, size));
        } else {
            btn_sub.setEnabled(false);
            btn_preview.setEnabled(false);
            btn_preview.setText("预览");
            btn_sub.setText("确定");
        }
    }

    @Override
    public void onPermissionsGranted(List<String> list) {
        getPhotos();
    }

    @Override
    public void onPermissionsDenied(List<String> list) {
        mTvEmptyView.setText(R.string.permissions_denied_tips);
    }

    /**
     * 获取所有图片
     */
    @AfterPermissionGranted(GalleryFinal.PERMISSIONS_CODE_GALLERY)
    private void requestGalleryPermission() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            getPhotos();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, getString(R.string.permissions_tips_gallery),
                    GalleryFinal.PERMISSIONS_CODE_GALLERY, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    private void getPhotos() {
        isEnerFolder = false;
        mTvEmptyView.setText(R.string.waiting);
        mGvPhotoList.setEnabled(false);
        btn_show_llfloder.setEnabled(false);
        new Thread() {
            @Override
            public void run() {
                super.run();

                mAllPhotoFolderList.clear();
                List<PhotoFolderInfo> allFolderList = PhotoTools.getAllPhotoFolder(PhotoSelectActivity.this, mSelectPhotoList);
                mAllPhotoFolderList.addAll(allFolderList);

                mCurPhotoList.clear();
                if (allFolderList.size() > 0) {
                    if (allFolderList.get(0).getPhotoList() != null) {
                        mCurPhotoList.addAll(allFolderList.get(0).getPhotoList());
                    }
                }

                refreshAdapter();
            }
        }.start();
    }


    @Override
    public void onBackPressed() {
        Log.d("CURR_STATE", CURR_STATE.toString());
        switch (CURR_STATE) {
            case allphoto:
                PhotoSelectActivity.this.finish();
                break;
            case folder:
                if (isEnerFolder) {
                    getPhotos();
                }
                mLlFolderPanel.setVisibility(View.GONE);
                mLlFolderPanel.setAnimation(loadAnimation(this, R.anim.my_gf_flip_horizontal_out));
                CURR_STATE = allphoto;
                break;
            case folderphoto:
//                mLlFolderPanel.setAnimation(loadAnimation(this, R.anim.my_gf_flip_horizontal_in));
                mLlFolderPanel.setVisibility(View.VISIBLE);
                CURR_STATE = ShowState.folder;
                break;
            case prewview:

                getSupportFragmentManager().popBackStack();
                if (isEnerFolder) {
                    CURR_STATE = folderphoto;
                } else {
                    CURR_STATE = allphoto;
                }

                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mHasRefreshGallery) {
            mHasRefreshGallery = false;
            requestGalleryPermission();
        }

        if (null != mSelectPhotoList)
            for (PhotoInfo photoInfo : mSelectPhotoList) {
                if (!photoInfo.isCheck()) {
                    mSelectPhotoList.remove(photoInfo);
                }
            }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (GalleryFinal.getCoreConfig() != null &&
                GalleryFinal.getCoreConfig().getImageLoader() != null) {
            GalleryFinal.getCoreConfig().getImageLoader().clearMemoryCache();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPhotoTargetFolder = null;
        mSelectPhotoList.clear();
        System.gc();
    }


    @Override
    public boolean onCheck(int position) {
        return photoItemCheck(position, null);
    }

    @Override
    public void onTakePhoto() {
        //判断是否达到多选最大数量
        if (mSelectPhotoList.size() >= GalleryFinal.getFunctionConfig().getMaxSize()) {
            toast(getString(R.string.select_max_tips));
            return;
        }
        if (!DeviceUtils.existSDCard()) {
            toast(getString(R.string.empty_sdcard));
            return;
        }
        takePhotoAction();
    }

    public void setWindowStatusBarColor(Activity activity, int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(colorResId);

                //底部导航栏
//                window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
