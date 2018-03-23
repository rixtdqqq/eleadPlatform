package com.elead.approval.fragment;

import android.content.Context;
import android.text.InputType;
import android.view.View;

import com.elead.approval.widget.ChooseView;
import com.elead.approval.widget.EdiTextItem;
import com.elead.eplatform.R;

import java.util.List;

/**
 * 用印申请
 */

public class ApprovalYongYinApply extends ApprovalBaseFragment {
    private ChooseView bumen, date, fileType, yinzhang;
    private EdiTextItem jingbanPeo, fileName, count, note;

    @Override
    public void initView(Context context) {
        getActivity().setTitle(getResources().getString(R.string.please_my_yong_yin_apply));

        bumen = new ChooseView(mContext);
        bumen.inIt(ChooseView.Type.time, getResources().getString(R.string.please_yong_yin_department), 10, 0);
        addView(bumen,10);

        jingbanPeo = new EdiTextItem(mContext);
        jingbanPeo.init(EdiTextItem.LineType.signLine, getResources().getString(R.string.approval_agent), getResources().getString(R.string.please_write_agent));
        addView(jingbanPeo,10);


        date = new ChooseView(mContext);
        date.inIt(ChooseView.Type.time, getResources().getString(R.string.approval_date), 10, 0);
        addView(date,10);

        fileName = new EdiTextItem(mContext);
        fileName.init(EdiTextItem.LineType.signLine, getResources().getString(R.string.approval_yong_yin_file_name), getResources().getString(R.string.approval_please_write_yong_yin_file_name));
        addView(fileName,10);

        count = new EdiTextItem(mContext);
        count.init(EdiTextItem.LineType.signLine, InputType.TYPE_NUMBER_VARIATION_NORMAL, getResources().getString(R.string.approval_file_count), getResources().getString(R.string.approval_pleast_files_count));
        addView(count,10);

        fileType = new ChooseView(mContext);
        fileType.inIt(ChooseView.Type.time, getResources().getString(R.string.approval_file_type), 10, 0);
        addView(fileType,10);

        yinzhang = new ChooseView(mContext);
        yinzhang.inIt(ChooseView.Type.time, getResources().getString(R.string.approval_gai_sha_yin_zhang), 10, 0);
        addView(yinzhang,10);

        note = new EdiTextItem(mContext);
        note.init(EdiTextItem.LineType.moreLine, getResources().getString(R.string.approval_note), getResources().getString(R.string.approval_pleast_write_note));
        addView(note,10);

    }

    @Override
    public boolean onSubmitClick(View v) {
        return false;
    }

    @Override
    public void onPhotoUploadSuccess(List<String> appvalUsers, List<String> imgUrls) {

    }
}
