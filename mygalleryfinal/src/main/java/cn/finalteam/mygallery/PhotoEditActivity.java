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

import android.view.View;
import android.widget.AdapterView;

import java.io.File;

import cn.finalteam.mygallery.model.PhotoInfo;
import cn.finalteam.mygallery.toolsfinal.ActivityManager;

/**
 * Desction:图片裁剪
 * Author:pengjianbo
 * Date:15/10/10 下午5:40
 */
public class PhotoEditActivity extends cn.finalteam.mygallery.widget.crop.CropImageActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void setCropSaveSuccess(File file) {

    }

    @Override
    public void setCropSaveException(Throwable throwable) {

    }

    @Override
    protected void takeResult(PhotoInfo info) {

    }

    static final String CROP_PHOTO_ACTION = "crop_photo_action";
    static final String TAKE_PHOTO_ACTION = "take_photo_action";
    static final String EDIT_PHOTO_ACTION = "edit_photo_action";

    static final String SELECT_MAP = "select_map";
//    private final int CROP_SUC = 1;//裁剪成功
//    private final int CROP_FAIL = 2;//裁剪失败
//    private final int UPDATE_PATH = 3;//更新path
//
//    private TextView mTvEmptyView;
//    private cn.finalteam.mygallery.widget.FloatingActionButton mFabCrop;
//    private HorizontalListView mLvGallery;
//    private LinearLayout mLlGallery;
//
//    private ArrayList<cn.finalteam.mygallery.model.PhotoInfo> mPhotoList;
//    private PhotoEditListAdapter mPhotoEditListAdapter;
//    private int mSelectIndex = 0;
//    private boolean mCropState;
//    private ProgressDialog mProgressDialog;
//    private boolean mRotating;
//
//    private ArrayList<cn.finalteam.mygallery.model.PhotoInfo> mSelectPhotoList;
//    private LinkedHashMap<Integer, PhotoTempModel> mPhotoTempMap;
//    private File mEditPhotoCacheFile;
//
//    private Drawable mDefaultDrawable;
//
//
//    private boolean mCropPhotoAction;//裁剪图片动作
//    private boolean mEditPhotoAction;//编辑图片动作
//    private Button btnsub;
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putSerializable("selectPhotoMap", mSelectPhotoList);
//        outState.putSerializable("editPhotoCacheFile", mEditPhotoCacheFile);
//        outState.putSerializable("photoTempMap", mPhotoTempMap);
//
//        outState.putInt("selectIndex", mSelectIndex);
//        outState.putBoolean("cropState", mCropState);
//        outState.putBoolean("rotating", mRotating);
//
//        outState.putBoolean("takePhotoAction", mTakePhotoAction);
//        outState.putBoolean("cropPhotoAction", mCropPhotoAction);
//        outState.putBoolean("editPhotoAction", mEditPhotoAction);
//
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        mSelectPhotoList = (ArrayList<cn.finalteam.mygallery.model.PhotoInfo>) getIntent().getSerializableExtra("selectPhotoMap");
//        mEditPhotoCacheFile = (File) savedInstanceState.getSerializable("editPhotoCacheFile");
//        mPhotoTempMap = new LinkedHashMap<>((HashMap<Integer, PhotoTempModel>) getIntent().getSerializableExtra("results"));
//
//        mSelectIndex = savedInstanceState.getInt("selectIndex");
//        mCropState = savedInstanceState.getBoolean("cropState");
//        mRotating = savedInstanceState.getBoolean("rotating");
//
//        mTakePhotoAction = savedInstanceState.getBoolean("takePhotoAction");
//        mCropPhotoAction = savedInstanceState.getBoolean("cropPhotoAction");
//        mEditPhotoAction = savedInstanceState.getBoolean("editPhotoAction");
//    }
//
//    private android.os.Handler mHanlder = new android.os.Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (msg.what == CROP_SUC) {
//                String path = (String) msg.obj;
//                cn.finalteam.mygallery.model.PhotoInfo photoInfo = mPhotoList.get(mSelectIndex);
//                try {
//                    Iterator<Map.Entry<Integer, PhotoTempModel>> entries = mPhotoTempMap.entrySet().iterator();
//                    while (entries.hasNext()) {
//                        Map.Entry<Integer, PhotoTempModel> entry = entries.next();
//                        if (entry.getKey() == photoInfo.getPhotoId()) {
//                            PhotoTempModel tempModel = entry.getValue();
//                            tempModel.setSourcePath(path);
//                            tempModel.setOrientation(0);
//                        }
//                    }
//                } catch (Exception e) {
//                }
//                toast(getString(R.string.crop_suc));
//
//                Message message = mHanlder.obtainMessage();
//                message.what = UPDATE_PATH;
//                message.obj = path;
//                mHanlder.sendMessage(message);
//
//            } else if (msg.what == CROP_FAIL) {
//                toast(getString(R.string.crop_fail));
//            } else if (msg.what == UPDATE_PATH) {
//                if (mPhotoList.get(mSelectIndex) != null) {
//                    cn.finalteam.mygallery.model.PhotoInfo photoInfo = mPhotoList.get(mSelectIndex);
//                    String path = (String) msg.obj;
//                    //photoInfo.setThumbPath(path);
//                    try {
//                        for (Iterator<cn.finalteam.mygallery.model.PhotoInfo> iterator = mSelectPhotoList.iterator(); iterator.hasNext(); ) {
//                            cn.finalteam.mygallery.model.PhotoInfo info = iterator.next();
//                            if (info != null && info.getPhotoId() == photoInfo.getPhotoId()) {
//                                info.setPhotoPath(path);
//                            }
//                        }
//                    } catch (Exception e) {
//                    }
//                    photoInfo.setPhotoPath(path);
//
//                    loadImage(photoInfo);
//                    mPhotoEditListAdapter.notifyDataSetChanged();
//                }
//
//                if (cn.finalteam.mygallery.GalleryFinal.getFunctionConfig().isForceCrop() && !cn.finalteam.mygallery.GalleryFinal.getFunctionConfig().isForceCropEdit()) {
//                    resultAction();
//                }
//            }
//            corpPageState(false);
//            mCropState = false;
//        }
//    };
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.my_gf_activity_photo_edit);
//        mDefaultDrawable = getResources().getDrawable(R.drawable.ic_gf_default_photo);
//
//        mSelectPhotoList = (ArrayList<cn.finalteam.mygallery.model.PhotoInfo>) getIntent().getSerializableExtra(SELECT_MAP);
//        mTakePhotoAction = this.getIntent().getBooleanExtra(TAKE_PHOTO_ACTION, false);
//        mCropPhotoAction = this.getIntent().getBooleanExtra(CROP_PHOTO_ACTION, false);
//        mEditPhotoAction = this.getIntent().getBooleanExtra(EDIT_PHOTO_ACTION, false);
//
//        if (mSelectPhotoList == null) {
//            mSelectPhotoList = new ArrayList<>();
//        }
//        mPhotoTempMap = new LinkedHashMap<>();
//        mPhotoList = new ArrayList<>(mSelectPhotoList);
//
//        mEditPhotoCacheFile = cn.finalteam.mygallery.GalleryFinal.getCoreConfig().getEditPhotoCacheFolder();
//
//        if (mPhotoList == null) {
//            mPhotoList = new ArrayList<>();
//        }
//
//        for (cn.finalteam.mygallery.model.PhotoInfo info : mPhotoList) {
//            mPhotoTempMap.put(info.getPhotoId(), new PhotoTempModel(info.getPhotoPath()));
//        }
//
//        findViews();
//        setListener();
//
//        mPhotoEditListAdapter = new PhotoEditListAdapter(this, mPhotoList, mScreenWidth);
//        mLvGallery.setAdapter(mPhotoEditListAdapter);
//
//        try {
//            File nomediaFile = new File(mEditPhotoCacheFile, ".nomedia");
//            if (!nomediaFile.exists()) {
//                nomediaFile.createNewFile();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        if (mPhotoList.size() > 0 && !mTakePhotoAction) {
//            loadImage(mPhotoList.get(0));
//        }
//
//        if (mTakePhotoAction) {
//            //打开相机
//            takePhotoAction();
//        }
//        if (!mCropPhotoAction) {
//            hasForceCrop();
//        }
//    }
//
//
//    private void findViews() {
//
//
//        mIvTakePhoto = (ImageView) findViewById(R.id.iv_take_photo);
//        mIvCropPhoto = (CropImageView) findViewById(R.id.iv_crop_photo);
//        mIvSourcePhoto = (PhotoView) findViewById(R.id.iv_source_photo);
//        mLvGallery = (HorizontalListView) findViewById(R.id.lv_gallery);
//        mLlGallery = (LinearLayout) findViewById(R.id.ll_gallery);
//        mIvBack = (ImageView) findViewById(R.id.iv_back);
//        mTvEmptyView = (TextView) findViewById(R.id.tv_empty_view);
//        mFabCrop = (cn.finalteam.mygallery.widget.FloatingActionButton) findViewById(R.id.fab_crop);
//        mIvCrop = (ImageView) findViewById(R.id.iv_crop);
//        mIvRotate = (ImageView) findViewById(R.id.iv_rotate);
//        mTvTitle = (TextView) findViewById(R.id.tv_title);
//        mTitlebar = (LinearLayout) findViewById(R.id.titlebar);
//        mIvPreView = (ImageView) findViewById(R.id.iv_preview);
//
//
//        View inflate = LinearLayout.inflate(this, R.layout.my_gf_title, null);
//        setTitleView(inflate);
//        btnsub = (Button) getActionBar().getCustomView().findViewById(R.id.btn_sub);
//        linback = (LinearLayout) getActionBar().getCustomView().findViewById(R.id.back);
//    }
//
//    private void setListener() {
//        mIvTakePhoto.setOnClickListener(this);
//        mIvBack.setOnClickListener(this);
//        mLvGallery.setOnItemClickListener(this);
//        mFabCrop.setOnClickListener(this);
//        mIvCrop.setOnClickListener(this);
//        mIvRotate.setOnClickListener(this);
//        mIvPreView.setOnClickListener(this);
//    }
//
//    @Override
//    protected void takeResult(cn.finalteam.mygallery.model.PhotoInfo info) {
//        mPhotoList.add(0, info);
//        mSelectPhotoList.add(info);
//        mPhotoTempMap.put(info.getPhotoId(), new PhotoTempModel(info.getPhotoPath()));
//        if (!cn.finalteam.mygallery.GalleryFinal.getFunctionConfig().isEditPhoto() && mTakePhotoAction) {
//            resultAction();
//        } else {
//            if (cn.finalteam.mygallery.GalleryFinal.getFunctionConfig().isEnablePreview()) {
//                mIvPreView.setVisibility(View.VISIBLE);
//            }
//            mPhotoEditListAdapter.notifyDataSetChanged();
//
//            PhotoSelectActivity activity = (PhotoSelectActivity) ActivityManager.getActivityManager().getActivity(PhotoSelectActivity.class.getName());
//            if (activity != null) {
//                activity.takeRefreshGallery(info, true);
//            }
//            loadImage(info);
//
//            hasForceCrop();
//        }
//    }
//
//    private void loadImage(cn.finalteam.mygallery.model.PhotoInfo photo) {
//        mTvEmptyView.setVisibility(View.GONE);
//        mIvSourcePhoto.setVisibility(View.VISIBLE);
//        mIvCropPhoto.setVisibility(View.GONE);
//
//        String path = "";
//        if (photo != null) {
//            path = photo.getPhotoPath();
//        }
//        if (cn.finalteam.mygallery.GalleryFinal.getFunctionConfig().isCrop()) {
//            setSourceUri(Uri.fromFile(new File(path)));
//        }
//
//        cn.finalteam.mygallery.GalleryFinal.getCoreConfig().getImageLoader().displayImage(this, path, mIvSourcePhoto, mDefaultDrawable, mScreenWidth, mScreenHeight);
//    }
//
    public void deleteIndex(int position, cn.finalteam.mygallery.model.PhotoInfo dPhoto) {
        if (dPhoto != null) {
            PhotoSelectActivity activity = (PhotoSelectActivity) ActivityManager.getActivityManager().getActivity(PhotoSelectActivity.class.getName());
            if (activity != null) {
                activity.deleteSelect(dPhoto.getPhotoId());
            }

//            try {
//                for (Iterator<PhotoInfo> iterator = mSelectPhotoList.iterator(); iterator.hasNext(); ) {
//                    cn.finalteam.mygallery.model.PhotoInfo info = iterator.next();
//                    if (info != null && info.getPhotoId() == dPhoto.getPhotoId()) {
//                        iterator.remove();
//                        break;
//                    }
//                }
//            } catch (Exception e) {
            }
        }
//
//        if (mPhotoList.size() == 0) {
//            mSelectIndex = 0;
//            mTvEmptyView.setText(R.string.no_photo);
//            mTvEmptyView.setVisibility(View.VISIBLE);
//            mIvSourcePhoto.setVisibility(View.GONE);
//            mIvCropPhoto.setVisibility(View.GONE);
//            mIvPreView.setVisibility(View.GONE);
//        } else {
//            if (position == 0) {
//                mSelectIndex = 0;
//            } else if (position == mPhotoList.size()) {
//                mSelectIndex = position - 1;
//            } else {
//                mSelectIndex = position;
//            }
//
//            cn.finalteam.mygallery.model.PhotoInfo photoInfo = mPhotoList.get(mSelectIndex);
//            loadImage(photoInfo);
//        }
//    }
//
//    @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//        mSelectIndex = i;
//        cn.finalteam.mygallery.model.PhotoInfo photoInfo = mPhotoList.get(i);
//        loadImage(photoInfo);
//    }
//
//    @Override
//    public void setCropSaveSuccess(final File file) {
//        Message message = mHanlder.obtainMessage();
//        message.what = CROP_SUC;
//        message.obj = file.getAbsolutePath();
//        mHanlder.sendMessage(message);
//    }
//
//    @Override
//    public void setCropSaveException(Throwable throwable) {
//        mHanlder.sendEmptyMessage(CROP_FAIL);
//    }
//
//    @Override
//    public void onClick(View v) {
//        int id = v.getId();
//        if (id == R.id.fab_crop) {
//            if (mPhotoList.size() == 0) {
//                return;
//            }
//            if (mCropState) {
//                System.gc();
//                cn.finalteam.mygallery.model.PhotoInfo photoInfo = mPhotoList.get(mSelectIndex);
//                try {
//                    String ext = FilenameUtils.getExtension(photoInfo.getPhotoPath());
//                    File toFile;
//                    if (cn.finalteam.mygallery.GalleryFinal.getFunctionConfig().isCropReplaceSource()) {
//                        toFile = new File(photoInfo.getPhotoPath());
//                    } else {
//                        toFile = new File(mEditPhotoCacheFile, Utils.getFileName(photoInfo.getPhotoPath()) + "_crop." + ext);
//                    }
//
//                    FileUtils.mkdirs(toFile.getParentFile());
//                    onSaveClicked(toFile);//保存裁剪
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            } else { //完成选择
//                resultAction();
//            }
//        } else if (id == R.id.iv_crop) {
//
//            if (mPhotoList.size() > 0) {
//                cn.finalteam.mygallery.model.PhotoInfo photoInfo = mPhotoList.get(mSelectIndex);
//                String ext = FilenameUtils.getExtension(photoInfo.getPhotoPath());
//                if (StringUtils.isEmpty(ext) || !(ext.equalsIgnoreCase("png") || ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg"))) {
//                    toast(getString(R.string.edit_letoff_photo_format));
//                } else {
//                    if (mCropState) {
//                        setCropEnabled(false);
//
//                        corpPageState(false);
//
//                        mTvTitle.setText(R.string.photo_edit);
//                    } else {
//                        corpPageState(true);
//                        setCropEnabled(true);
//
//                        mTvTitle.setText(R.string.photo_crop);
//                    }
//                    mCropState = !mCropState;
//                }
//            }
//        } else if (id == R.id.iv_rotate) {
//            rotatePhoto();
//        } else if (id == R.id.iv_take_photo) {
//            takePhotoAction();
//        } else if (id == R.id.iv_back) {
//            if (mCropState && !(mCropPhotoAction && !cn.finalteam.mygallery.GalleryFinal.getFunctionConfig().isRotate())) {
//                if ((cn.finalteam.mygallery.GalleryFinal.getFunctionConfig().isForceCrop() && cn.finalteam.mygallery.GalleryFinal.getFunctionConfig().isForceCropEdit())) {
//                    mIvCrop.performClick();
//                    return;
//                }
//            }
//            finish();
//        } else if (id == R.id.iv_preview) {
//            Intent intent = new Intent(this, PhotoPreviewFragment.class);
//            intent.putExtra(PhotoPreviewFragment.PHOTO_LIST, mSelectPhotoList);
//            startActivity(intent);
//        }
//    }
//
//    private void resultAction() {
//        resultData(mSelectPhotoList);
//    }
//
//    private void hasForceCrop() {
//        if (cn.finalteam.mygallery.GalleryFinal.getFunctionConfig().isForceCrop()) {
//            mIvCrop.performClick();//进入裁剪状态
//            if (!cn.finalteam.mygallery.GalleryFinal.getFunctionConfig().isForceCropEdit()) {//强制裁剪后是否可以编辑
//                mIvCrop.setVisibility(View.GONE);
//            }
//        }
//    }
//
//    /**
//     * 图片旋转
//     */
//    private void rotatePhoto() {
//        if (mPhotoList.size() > 0 && mPhotoList.get(mSelectIndex) != null && !mRotating) {
//            final cn.finalteam.mygallery.model.PhotoInfo photoInfo = mPhotoList.get(mSelectIndex);
//            final String ext = FilenameUtils.getExtension(photoInfo.getPhotoPath());
//            if (StringUtils.isEmpty(ext) || !(ext.equalsIgnoreCase("png") || ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg"))) {
//                toast(getString(R.string.edit_letoff_photo_format));
//                return;
//            }
//            mRotating = true;
//            if (photoInfo != null) {
//                final PhotoTempModel photoTempModel = mPhotoTempMap.get(photoInfo.getPhotoId());
//                final String path = photoTempModel.getSourcePath();
//
//                File file;
//                if (cn.finalteam.mygallery.GalleryFinal.getFunctionConfig().isRotateReplaceSource()) { //裁剪覆盖源文件
//                    file = new File(path);
//                } else {
//                    file = new File(mEditPhotoCacheFile, Utils.getFileName(path) + "_rotate." + ext);
//                }
//
//                final File rotateFile = file;
//                new AsyncTask<Void, Void, Bitmap>() {
//                    @Override
//                    protected void onPreExecute() {
//                        super.onPreExecute();
//                        mTvEmptyView.setVisibility(View.VISIBLE);
//                        mProgressDialog = ProgressDialog.show(PhotoEditActivity.this, "", getString(R.string.waiting), true, false);
//                    }
//
//                    @Override
//                    protected Bitmap doInBackground(Void... params) {
//                        int orientation;
//                        if (cn.finalteam.mygallery.GalleryFinal.getFunctionConfig().isRotateReplaceSource()) {
//                            orientation = 90;
//                        } else {
//                            orientation = photoTempModel.getOrientation() + 90;
//                        }
//                        Bitmap bitmap = Utils.rotateBitmap(path, orientation, mScreenWidth, mScreenHeight);
//                        if (bitmap != null) {
//                            Bitmap.CompressFormat format;
//                            if (ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg")) {
//                                format = Bitmap.CompressFormat.JPEG;
//                            } else {
//                                format = Bitmap.CompressFormat.PNG;
//                            }
//                            Utils.saveBitmap(bitmap, format, rotateFile);
//                        }
//                        return bitmap;
//                    }
//
//                    @Override
//                    protected void onPostExecute(Bitmap bitmap) {
//                        super.onPostExecute(bitmap);
//                        if (mProgressDialog != null) {
//                            mProgressDialog.dismiss();
//                            mProgressDialog = null;
//                        }
//                        if (bitmap != null) {
//                            bitmap.recycle();
//
//                            mTvEmptyView.setVisibility(View.GONE);
//
//                            if (!cn.finalteam.mygallery.GalleryFinal.getFunctionConfig().isRotateReplaceSource()) {
//                                int orientation = photoTempModel.getOrientation() + 90;
//                                if (orientation == 360) {
//                                    orientation = 0;
//                                }
//                                photoTempModel.setOrientation(orientation);
//                            }
//
//                            Message message = mHanlder.obtainMessage();
//                            message.what = UPDATE_PATH;
//                            message.obj = rotateFile.getAbsolutePath();
//                            mHanlder.sendMessage(message);
//                        } else {
//                            mTvEmptyView.setText(R.string.no_photo);
//                        }
//                        loadImage(photoInfo);
//                        mRotating = false;
//                    }
//                }.execute();
//            }
//        }
//    }
//
//    private void corpPageState(boolean crop) {
//        if (crop) {
//            mIvSourcePhoto.setVisibility(View.GONE);
//            mIvCropPhoto.setVisibility(View.VISIBLE);
//            mLlGallery.setVisibility(View.GONE);
//            if (cn.finalteam.mygallery.GalleryFinal.getFunctionConfig().isCrop()) {
//                mIvCrop.setVisibility(View.VISIBLE);
//            }
//            if (cn.finalteam.mygallery.GalleryFinal.getFunctionConfig().isRotate()) {
//                mIvRotate.setVisibility(View.GONE);
//            }
//
//        } else {
//            mIvSourcePhoto.setVisibility(View.VISIBLE);
//            mIvCropPhoto.setVisibility(View.GONE);
//            if (cn.finalteam.mygallery.GalleryFinal.getFunctionConfig().isCrop()) {
//                mIvCrop.setVisibility(View.VISIBLE);
//            }
//            if (cn.finalteam.mygallery.GalleryFinal.getFunctionConfig().isRotate()) {
//                mIvRotate.setVisibility(View.VISIBLE);
//            }
//
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        RecycleViewBitmapUtils.recycleImageView(mIvCropPhoto);
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (mCropState && !(mCropPhotoAction)) {
//                if ((cn.finalteam.mygallery.GalleryFinal.getFunctionConfig().isForceCrop() && cn.finalteam.mygallery.GalleryFinal.getFunctionConfig().isForceCropEdit())) {
//                    mIvCrop.performClick();
//                    return true;
//                }
//            }
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}
