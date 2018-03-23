package com.elead.approval.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.elead.approval.adapter.ChoosePhotoListAdapter;
import com.elead.eplatform.R;
import com.elead.utils.PicassoImageLoader;
import com.elead.utils.ToastUtil;
import com.elead.views.FixGridView;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.mygallery.CoreConfig;
import cn.finalteam.mygallery.FunctionConfig;
import cn.finalteam.mygallery.GalleryFinal;
import cn.finalteam.mygallery.listener.PicassoPauseOnScrollListener;
import cn.finalteam.mygallery.model.PhotoInfo;


/**
 * Created by Administrator on 2016/9/19 0019.
 */
public class ChoosePhotoL extends RelativeLayout {

    private FunctionConfig functionConfig;
    private FunctionConfig.Builder functionConfigBuilder;
    private View inflate;
    private final int REQUEST_CODE_GALLERY = 1001;
    private FixGridView grd_approval_choose_pic;
    private ChoosePhotoListAdapter adapter;
    private List<PhotoInfo> mPhotoList = new ArrayList<>();
    private TextView pitureIv;

    public ChoosePhotoL(Context context) {
        this(context, null);
    }

    public ChoosePhotoL(Context context, AttributeSet attrs) {

        this(context, attrs, 0);
    }

    public ChoosePhotoL(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inIt();
        initImageLoader(context);
    }

    private void initGall(int maxsize) {
        functionConfigBuilder = new FunctionConfig.Builder();
        functionConfigBuilder.setMutiSelectMaxSize(maxsize);
        functionConfigBuilder.setEnableEdit(false);
        functionConfigBuilder.setEnableCrop(true);
        functionConfigBuilder.setCropSquare(true);
        functionConfigBuilder.setEnablePreview(true);
        functionConfigBuilder.setRotateReplaceSource(true);
        functionConfigBuilder.setCropReplaceSource(true);
        functionConfigBuilder.setFilter(mPhotoList);
        functionConfigBuilder.setSelected(mPhotoList);//添加过滤集合
        functionConfig = functionConfigBuilder.build();
        CoreConfig coreConfig = new CoreConfig.Builder(getContext(), new PicassoImageLoader())
                .setFunctionConfig(functionConfig)
                .setPauseOnScrollListener(new PicassoPauseOnScrollListener(false, true))
                .setNoAnimcation(true)
                .build();
        GalleryFinal.init(coreConfig);
    }

    private void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

    private void inIt() {
        inflate = LayoutInflater.from(getContext()).inflate(R.layout.approval_choose_photo_lin, this);
        pitureIv = (TextView) inflate.findViewById(R.id.picture_title_iv);
        adapter = new ChoosePhotoListAdapter((Activity) getContext(), mPhotoList, pitureIv);
        grd_approval_choose_pic = (FixGridView) inflate.findViewById(R.id.grd_approval_choose_pic);

        grd_approval_choose_pic.setAdapter(adapter);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPhotoList.size() >= 9) {
                    ToastUtil.showToast(getContext(), getResources().getString(R.string.select_max_tips), 2000);
                    return;
                } else {
                    initGall(9 - mPhotoList.size());
                    GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, functionConfig, mOnHanlderResultCallback);
                }

            }
        });

        pitureIv.setText(getResources().getString(R.string.photo) + "(" + 0 + ")");

    }

    GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                if (resultList.size() != 0) {
                    grd_approval_choose_pic.setVisibility(View.VISIBLE);
                } else {
                    grd_approval_choose_pic.setVisibility(View.GONE);
                }
                mPhotoList.addAll(resultList);
                pitureIv.setText(getResources().getString(R.string.photo) + "(" + mPhotoList.size() + ")");
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            ToastUtil.showToast(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
        }
    };

}
