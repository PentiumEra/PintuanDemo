package com.hoadong.diy.deleagates;

/**
 * Created by linghaoDo on 2018/8/13
 */
public abstract  class DiyDelegate extends PermissionCheckerDelegate {
    @SuppressWarnings("unchecked")
    public <T extends DiyDelegate>T getParentDelegate(){
        return (T) getParentDelegate();
    }
}
