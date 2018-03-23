package com.elead.im.entry;

public class GroupPickUser extends EaseUser {
    public boolean isChecked;
    public boolean isEnable = true;

    public GroupPickUser(String username) {
        super(username);
        this.username = username;
    }

    public GroupPickUser(EaseUser user) {
        super(user.getUsername());
        this.nick = user.getNick();
        this.username = user.getUsername();
        this.avatar = user.getAvatar();
        this.initialLetter = user.getInitialLetter();
    }
}
