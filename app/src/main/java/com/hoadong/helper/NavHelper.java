package com.hoadong.helper;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;

/**
 * Created by linghaoDo
 * 解决对fragment的调度与重用问题，达到最优的fragment切换
 */
public class NavHelper<T> {
    // 所有的tab集合
    private final SparseArray<Tab<T>> TABS = new SparseArray<>();
    private final Context CONTEXT;
    private final int CONTAINER_ID;
    private final FragmentManager FRAGMENTMANAGER;
    private final OnTabChangedListener<T> LISTENER;
    private Tab<T> currentTab = null;

    public NavHelper(Context context, int containerId, FragmentManager fragmentManager, OnTabChangedListener<T> listener) {
        this.CONTEXT = context;
        this.CONTAINER_ID = containerId;
        this.FRAGMENTMANAGER = fragmentManager;
        this.LISTENER = listener;
    }

    public NavHelper<T> add(int menuId, Tab<T> tab) {
        TABS.put(menuId, tab);
        return this;
    }

    /**
     * 获取当前显示的tab
     *
     * @return
     */
    public Tab<T> getCurrentTab() {
        return currentTab;
    }


    /**
     * 执行点击菜单操作
     *
     * @param menuId
     * @return true:能够处理点击事件
     */
    public boolean performClickMenu(int menuId) {
        Tab<T> tab = TABS.get(menuId);
        if (tab != null) {
            doSelect(tab);
            return true;
        }
        return false;
    }

    /**
     * tab选择操作
     *
     * @param tab
     */
    private void doSelect(Tab<T> tab) {
        Tab<T> oldtab = null;
        if (currentTab != null) {
            oldtab = currentTab;
            if (oldtab == tab) {
                notifyTabReselect(tab);
                return;
            }
        }
        //赋值  并调用切换方法
        currentTab = tab;
        doTabChanged(currentTab, oldtab);
    }

    private void doTabChanged(Tab<T> newTab, Tab<T> oldTab) {
        FragmentTransaction ft = FRAGMENTMANAGER.beginTransaction();
        if (oldTab != null) {
            if (oldTab.fragment != null) {
                // 从界面移除，并缓存
                ft.detach(oldTab.fragment);

            }
        }
        if (newTab != null) {
            if (newTab.fragment == null) {
                //首次创建
                Fragment fragment = Fragment.instantiate(CONTEXT, newTab.clx.getName(), null);
                //缓存
                newTab.fragment = fragment;
                // 提交 并添加你tag
                ft.add(CONTAINER_ID, fragment, newTab.clx.getName());
            } else {
                ft.attach(newTab.fragment);
            }
        }
        ft.commit();
        notifyTabSelected(newTab, oldTab);
    }

    private void notifyTabSelected(Tab<T> newTab, Tab<T> oldTab) {
        if (LISTENER!=null){
            LISTENER.onTabChanged(newTab, oldTab);
        }
    }


    /**
     * 二次点击
     *
     * @param tab
     */
    private void notifyTabReselect(Tab<T> tab) {

    }

    /**
     * 所有Tab的基本属性
     *
     * @param <T> Tab的额外参数
     */
    public static class Tab<T> {

        // 实例化fragment由内部调度， fragment对应的class信息
        public Class<?> clx;
        // 额外的字段，用户自行决定需要使用什么东西
        public T extra;
        //内部缓存定义的fragment
        Fragment fragment;

        public Tab(Class<?> clx, T extra) {
            this.clx = clx;
            this.extra = extra;
        }
    }

    /**
     * 定义时间处理完成后的回调接口
     *
     * @param <T>
     */
    public interface OnTabChangedListener<T> {
        void onTabChanged(Tab<T> newTab, Tab<T> oldTab);
    }
}
