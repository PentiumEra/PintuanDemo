package com.hoadong.pingtuan.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.hoadong.diy.activities.BaseActivity;
import com.hoadong.helper.NavHelper;
import com.hoadong.pingtuan.R;
import com.hoadong.pingtuan.frags.main.LocationFragment;
import com.hoadong.pingtuan.frags.main.MainFragment;
import com.hoadong.pingtuan.frags.main.MineFragment;
import com.hoadong.pingtuan.frags.main.PintuanFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener,
        NavHelper.OnTabChangedListener<Integer>{
    @BindView(R.id.navigation)
    BottomNavigationView mNavigationView;
    private NavHelper<Integer> mNavHelper = null;

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_main2;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mNavHelper=new NavHelper<Integer>(MainActivity.this,R.id.lay_container,getSupportFragmentManager(),this);
        mNavHelper.add(R.id.action_main,new NavHelper.Tab<Integer>(MainFragment.class,R.string.action_main))
                .add(R.id.action_location,new NavHelper.Tab<Integer>(LocationFragment.class,R.string.action_location))
                .add(R.id.action_pintuan,new NavHelper.Tab<Integer>(PintuanFragment.class,R.string.action_pintuan))
                .add(R.id.action_mine,new NavHelper.Tab<Integer>(MineFragment.class,R.string.action_mine));
        // 底部menu的点击监听
        mNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        //从底部接管我们的menu，然后进行手动的触发第一次点击
        Menu menu = mNavigationView.getMenu();
        //触发首次选中home
        menu.performIdentifierAction(R.id.action_main, 0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return  mNavHelper.performClickMenu(item.getItemId());
    }

    @Override
    public void onTabChanged(NavHelper.Tab<Integer> newTab, NavHelper.Tab<Integer> oldTab) {

    }
}
