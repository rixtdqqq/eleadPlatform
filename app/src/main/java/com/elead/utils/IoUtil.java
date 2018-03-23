package com.elead.utils;

import android.content.Context;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2016/11/15 0015.
 */

public class IoUtil {
    public static String getFromAssets(Context context, String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;

            return JSON.parse(Result.replace(" ", "")).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
