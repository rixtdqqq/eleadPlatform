package com.elead.module;

import com.elead.utils.Pinyin4jUtil;

import java.io.Serializable;
import java.util.List;

public class MGroup implements Serializable {
    private String groupName;
    private String groupId;
    private List<MGroup> childGroups;
    private List<EpUser> epUsers;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<EpUser> getEpUsers() {
        return epUsers;
    }

    public void setEpUsers(List<EpUser> epUsers) {
        this.epUsers = epUsers;
    }

    public MGroup() {
        super();
        // TODO Auto-generated constructor stub
    }

    public List<MGroup> getChildGroups() {
        return childGroups;
    }

    public void setChildGroups(List<MGroup> childGroups) {
        this.childGroups = childGroups;
    }

    public MGroup(String groupName, String groupId, List<MGroup> childGroups,
                  List<EpUser> epUsers) {
        super();
        this.groupName = groupName;
        this.groupId = groupId;
        this.childGroups = childGroups;
        this.epUsers = epUsers;
    }

    public EpUser search(String str, List<EpUser> comformUsers) {
        if (null != epUsers) {
            for (EpUser user : epUsers) {
                if (isConform(user, str)) {
                    comformUsers.add(user);
                }
            }
        }
        if (null != childGroups) {
            for (MGroup group : childGroups) {
                group.search(str, comformUsers);
            }
        }
        return null;
    }

    private boolean isConform(EpUser user, String str) {
        if (Pinyin4jUtil.getPinYin(user.getRole_name()).contains(str) || Pinyin4jUtil.getPinYinHeadChar(user.getRole_name()).contains(str))
            return true;
        else
            return false;
    }

}
