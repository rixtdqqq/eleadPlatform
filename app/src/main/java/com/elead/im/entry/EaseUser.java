/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.elead.im.entry;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.elead.application.MyApplication;
import com.elead.im.util.EaseCommonUtils;
import com.hyphenate.chat.EMContact;

public class EaseUser extends EMContact {

    /**
     * initial letter for nickname
     */
    protected String initialLetter;
    /**
     * avatar of the user
     */
    protected String avatar;
    public static SharedPreferences sp = MyApplication.mContext.getSharedPreferences("NickName", Context.MODE_PRIVATE);

    public EaseUser(String username) {
        this.username = username;
    }


    @Override
    public String getNick() {
        return this.getNickname();
    }

    @Override
    public String getNickname() {
        String nick = super.getNickname();
        if (nick.equals(username) || TextUtils.isEmpty(nick)) {

            return sp.getString(username, username);

        } else {

            return nick;

        }

    }

    public String getInitialLetter() {
        if (initialLetter == null) {
            EaseCommonUtils.setUserInitialLetter(this);
        }
        return initialLetter;
    }

    public void setInitialLetter(String initialLetter) {
        this.initialLetter = initialLetter;
    }


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public int hashCode() {
        return 17 * getUsername().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o != null) {
            if (o instanceof EaseUser) {
                return getUsername().equals(((EaseUser) o).getUsername());
            } else {
                return o.toString().equals(getUsername());
            }
        } else return false;
    }

    @Override
    public String toString() {
        return nick == null ? username : nick;
    }
}
