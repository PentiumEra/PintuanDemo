package com.hoadong.diy.activities;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hoadong.diy.R;
import com.hoadong.diy.deleagates.BaseDelegate;

import java.util.List;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.ExtraTransaction;
import me.yokeyword.fragmentation.ISupportActivity;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportActivityDelegate;
import me.yokeyword.fragmentation.SupportHelper;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public abstract class BaseActivity extends AppCompatActivity implements ISupportActivity{
    final SupportActivityDelegate mDelegate = new SupportActivityDelegate(this);
    public abstract int getContentLayoutId();
    protected void initWidget(){
        ButterKnife.bind(this);
    }
    protected void initData(){

    }
    protected void initWindows(){

    }
    /**
     * 初始化相关参数
     * @param bundle
     * @return 如果参数正确 返回true  错误返回false
     */
    protected boolean initArgs(Bundle bundle){
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化窗口
        initWindows();

        if (initArgs(getIntent().getExtras())){
            //得到界面id并设置到界面中
            int layoutId=getContentLayoutId();
            setContentView(layoutId);
            initWidget();
            initData();
        }else {
            finish();
        }
        mDelegate.onCreate(savedInstanceState);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDelegate.onPostCreate(savedInstanceState);
    }


    @Override
    protected void onDestroy() {
        mDelegate.onDestroy();
        super.onDestroy();
        System.gc();
        System.runFinalization();

    }

    @Override
    public SupportActivityDelegate getSupportDelegate() {
        return mDelegate;
    }

    @Override
    public ExtraTransaction extraTransaction() {
        return mDelegate.extraTransaction();
    }

    @Override
    public FragmentAnimator getFragmentAnimator() {
        return mDelegate.getFragmentAnimator();
    }

    @Override
    public void setFragmentAnimator(FragmentAnimator fragmentAnimator) {
        mDelegate.setFragmentAnimator(fragmentAnimator);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return mDelegate.onCreateFragmentAnimator();
    }

    @Override
    public void post(Runnable runnable) {
        mDelegate.post(runnable);

    }

    @Override
    public void onBackPressedSupport() {
        mDelegate.onBackPressedSupport();

    }

    @Override
    public void onBackPressed() {
        //当activity中有多层fragment
        List<Fragment> fragments=getSupportFragmentManager().getFragments();
        if(fragments!=null&&fragments.size()>0){
            // 如果fragment是我们自己写的fragment(是否是我们自己能处理的类型)
            for(Fragment fragment:fragments){
                if (fragment instanceof BaseDelegate){
                    // 判断是否拦截了返回按钮
                    if (((BaseDelegate) fragment).onBackPressed()){
                        // 如果有 直接return
                        return;
                    }

                }
            }
        }
        super.onBackPressed();
        finish();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    /**
     * 得到位于栈顶Fragment
     */
    public ISupportFragment getTopFragment() {
        return SupportHelper.getTopFragment(getSupportFragmentManager());
    }

    /**
     * 获取栈内的fragment对象
     */
    public <T extends ISupportFragment> T findFragment(Class<T> fragmentClass) {
        return SupportHelper.findFragment(getSupportFragmentManager(), fragmentClass);
    }
}
