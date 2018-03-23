//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.elead.im.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.elead.activity.BaseFragmentActivity;
import com.elead.im.controller.EaseUI;

@SuppressLint({"NewApi", "Registered"})
public class EaseBaseActivity extends BaseFragmentActivity {
    protected InputMethodManager inputMethodManager;

    public EaseBaseActivity() {
    }

    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        if(!this.isTaskRoot()) {
            Intent intent = this.getIntent();
            String action = intent.getAction();
            if(intent.hasCategory("android.intent.category.LAUNCHER") && action.equals("android.intent.action.MAIN")) {
                this.finish();
                return;
            }
        }

        this.inputMethodManager = (InputMethodManager)this.getSystemService(INPUT_METHOD_SERVICE);
    }

    protected void onResume() {
        super.onResume();
        EaseUI.getInstance().getNotifier().reset();
    }

    protected void hideSoftKeyboard() {
        if(this.getWindow().getAttributes().softInputMode != 2 && this.getCurrentFocus() != null) {
            this.inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 2);
        }

    }

    public void back(View view) {
        this.finish();
    }
}
