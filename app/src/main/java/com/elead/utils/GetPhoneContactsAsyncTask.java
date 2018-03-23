package com.elead.utils;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.widget.BaseAdapter;

import com.elead.module.EpUser;

import java.util.ArrayList;
import java.util.List;

public class GetPhoneContactsAsyncTask extends
        AsyncTask<String, Integer, List<EpUser>> {
    private Context mContext;
    private ProgressDialog progressBar;
    private Cursor cursor;
    private BaseAdapter adapter;
    private int szie;
    private List<EpUser> list;

    public GetPhoneContactsAsyncTask(Context mContext, BaseAdapter adapter, List<EpUser> list) {
        this.mContext = mContext;
        this.adapter = adapter;
        this.list = list;
    }

    @SuppressLint("NewApi")
    @Override
    protected void onPreExecute() {
        cursor = mContext.getContentResolver().query(Phone.CONTENT_URI,
                new String[]{Phone.DISPLAY_NAME, Phone.NUMBER}, null, null, "sort_key");
        szie = cursor.getCount();
        progressBar = new ProgressDialog(mContext);
        progressBar.setTitle("加载中~");
        progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.setProgressNumberFormat("%1d /%2d ");
        progressBar.setMax(szie);
        progressBar.show();
    }

    @Override
    protected List<EpUser> doInBackground(String... params) {
        try {
            List<EpUser> allContactList = new ArrayList<>();
            int count = 0;
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
                    .moveToNext()) {
                try {
                    String name = cursor.getString(0);
                    String phoneNum = cursor.getString(1);
                    EpUser m = new EpUser(new String[]{phoneNum, name});
                    allContactList.add(m);
                    count++;
                    publishProgress(count);
                } catch (Exception e) {

                }
            }
            cursor.close();
            return allContactList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... progresses) {
        progressBar.setProgress(progresses[0]);
    }

    @Override
    protected void onPostExecute(List<EpUser> result) {
        if (null != result) {
            if (null == list) {
                list = new ArrayList<>();
            } else {
                list.clear();
            }
            list.addAll(result);
            adapter.notifyDataSetChanged();
        }
        progressBar.cancel();
    }

    @Override
    protected void onCancelled() {
        progressBar.cancel();
    }
}
