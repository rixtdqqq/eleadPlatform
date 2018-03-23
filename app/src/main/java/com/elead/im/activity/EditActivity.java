package com.elead.im.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.elead.activity.BaseActivity;
import com.elead.eplatform.R;

public class EditActivity extends BaseActivity {
    private EditText editText;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.em_activity_edit);

        editText = (EditText) findViewById(R.id.edittext);
        String title = getIntent().getStringExtra("title");
        setTitle(title, TitleType.NORMAL);
        String data = getIntent().getStringExtra("data");
        if (title != null)
            ((TextView) findViewById(R.id.title)).setText(title);
        if (data != null)
            editText.setText(data);
        editText.setSelection(editText.length());

    }


    public void save(View v) {
        setResult(RESULT_OK, new Intent().putExtra("data", editText.getText().toString()));
        finish();
    }
}
