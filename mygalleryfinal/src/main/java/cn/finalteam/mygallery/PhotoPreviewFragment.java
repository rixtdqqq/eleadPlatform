package cn.finalteam.mygallery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.finalteam.mygallery.adapter.PhotoPreviewAdapter;
import cn.finalteam.mygallery.model.PhotoInfo;
import cn.finalteam.mygallery.widget.GFViewPager;

/**
 * Desction:
 * Author:pengjianbo
 * Date:2015/12/29 0029 14:43
 */
public class PhotoPreviewFragment extends Fragment implements ViewPager.OnPageChangeListener {

    private GFViewPager mVpPager;
    private List<PhotoInfo> mPhotoList;
    private PhotoPreviewAdapter mPhotoPreviewAdapter;
    private int position;
    private View view;

    public PhotoPreviewFragment(List<PhotoInfo> mPhotoList, int position) {
        this.mPhotoList = mPhotoList;
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null == view) {
            view = inflater.inflate(R.layout.my_gf_activity_photo_preview, container, false);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findViews();
        setListener();
        mPhotoPreviewAdapter = new PhotoPreviewAdapter(getActivity(), mPhotoList);
        mVpPager.setOffscreenPageLimit(2);
        mVpPager.setAdapter(mPhotoPreviewAdapter);
        mVpPager.setCurrentItem(position - 1);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((PhotoSelectActivity) getActivity()).settitleText("选择照片");
        ((PhotoSelectActivity) getActivity()).showPreviewBottomView();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((PhotoSelectActivity) getActivity()).settitleText((mVpPager.getCurrentItem() + 1) + "/" + mPhotoList.size());
    }

    private void findViews() {
        mVpPager = (GFViewPager) view.findViewById(R.id.vp_pager);
    }

    private void setListener() {
        mVpPager.addOnPageChangeListener(this);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        ((PhotoSelectActivity) getActivity()).settitleText((position + 1) + "/" + mPhotoList.size());
        ((PhotoSelectActivity) getActivity()).rightCheckbox.setChecked(mPhotoList.get(position).isCheck());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public PhotoInfo gettCheckPhoto(boolean b) {
        PhotoInfo photoInfo = mPhotoList.get(mVpPager.getCurrentItem());
        photoInfo.setCheck(b);
        return photoInfo;
    }
}
