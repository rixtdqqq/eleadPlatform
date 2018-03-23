package com.elead.operate.activity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.elead.activity.BaseActivity;
import com.elead.eplatform.R;
import com.elead.operate.widget.Detail;
import com.elead.utils.Util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/4 0004.
 */

public class DetaiCardActivity extends BaseActivity{

    private LinearLayout ll;
    private Map<String,String>map=new LinkedHashMap<String,String>();
    private String Name[] = {"项目名称", "提交日期", "供应商", "类型", "技术平台", "项目编号", "华为项目经理", "供方经理", "中标人天", "合同金额(元)", "PO编码", "eR资源申请", "eR招投标", "关联产品"};
    private String Content[] = {"三朵云_MTL颗粒V02R03_I5_ios", "2016/03/29~10:04:30", "上海易立德企业管理有限公司", "低端", "移动类(android&ios)", "AP-615-894", "C0868888869", "zWx008699", "200", "xxxxxx", "RM20160337", "2016/03/02~2016/03/21", "2016/03/02~2016/03/21", "iCaptain"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_check_detail_activity);
        setTitle("详情",TitleType.STATISTICAL);
        setDatas();
        initViews();
    }

    private void setDatas() {

        for(int i=0;i<Name.length;i++){
            map.put(Name[i], Content[i]);
        }
    }


    private void initViews() {
        ll=(LinearLayout)findViewById(R.id.detail_card_ll);
//
        //Detail d=new Detail(this);
//
        //ll.addView(d);
        int index=0;

        for(Map.Entry<String,String>enSet:map.entrySet()){

//

           LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
            Detail n= new Detail(this);
            if(index==2){
                params.topMargin= Util.dpToPx(getResources(),13);
                //n.setPaddingRelative(0, 60, 0, 0);
                n.setLayoutParams(params);
            }
            n.setDatas(enSet.getKey(),enSet.getValue());
            index++;
            ll.addView(n);

        }


    }
}
