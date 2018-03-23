package com.elead.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.elead.application.MyApplication;
import com.elead.eplatform.R;
import com.elead.module.EpUser;
import com.elead.views.PhoneContactList;

public class AddressActivity extends BaseActivity {
    private PhoneContactList contactListLayout;
    private ListView listView;
    private MyApplication myApplication;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_address);
        setTitle("手机通讯录",TitleType.NORMAL);
        myApplication = (MyApplication) getApplication();
        contactListLayout = (PhoneContactList) findViewById( R.id.address_list);
        listView = contactListLayout.getListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EpUser user = (EpUser) listView.getItemAtPosition(position);

            }
        });
        contactListLayout.init();
    }

}
