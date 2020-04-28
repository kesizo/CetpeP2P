package com.kesizo.cetpe.front.ui.components;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class UserGroupMemberLayout extends VerticalLayout {
    private String userGroupId;

    public UserGroupMemberLayout(String userGroupId) {
        this.userGroupId = userGroupId;
    }

    public String getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(String userGroupId) {
        this.userGroupId = userGroupId;
    }
}
