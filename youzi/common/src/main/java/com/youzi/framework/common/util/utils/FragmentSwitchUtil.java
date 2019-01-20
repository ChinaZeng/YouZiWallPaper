package com.youzi.framework.common.util.utils;

import android.support.annotation.IdRes;
import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.youzi.framework.common.widgets.NestRadioGroup;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Administrator on 2017\11\17 0017.
 */

public class FragmentSwitchUtil {
    private FragmentManager fragmentManager;
    private Fragment currentVisibleFragment = null;//当前显示的Fragment
    @IdRes
    private int containerResId;

    public static final int MODE_HIDE_SHOW = 1;
    public static final int MODE_REPLACE = 2;

    @IntDef({MODE_HIDE_SHOW, MODE_REPLACE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {

    }

    public FragmentSwitchUtil(FragmentManager fragmentManager, int containerResId) {
        this.fragmentManager = fragmentManager;
        this.containerResId = containerResId;
    }

    /**
     * 切换fragment
     *
     * @param fragment 切换至的目标
     * @param mode     切换模式
     */
    public void switchFragment(Fragment fragment, @Mode int mode, String tag) {
        switch (mode) {
            case MODE_HIDE_SHOW:
                switchFragmentHideShow(fragment, tag);
                break;
            case MODE_REPLACE:
                switchFragmentReplace(fragment, tag);
                break;
            default:
                break;
        }
    }

    /**
     * 以替换的方式切换fragment
     *
     * @param fragment
     */
    private void switchFragmentReplace(Fragment fragment, String tag) {
        if (currentVisibleFragment == fragment || fragment == null) {
            return;
        }

        fragmentManager.beginTransaction().replace(containerResId, fragment).commitAllowingStateLoss();
    }

    /**
     * 以显示隐藏的方式切换fragment
     *
     * @param fragment
     */
    private void switchFragmentHideShow(Fragment fragment, String tag) {
        if (currentVisibleFragment == fragment || fragment == null) {
            return;
        }

        FragmentTransaction transaction = this.fragmentManager.beginTransaction();
        if (currentVisibleFragment != null) {//如果当前有显示内容,先隐藏
            transaction.hide(currentVisibleFragment);
        }

        //解决Fragment重叠问题
        Fragment tagFragment = this.fragmentManager.findFragmentByTag(tag);
        if (tagFragment != null && tagFragment != fragment) {
            transaction.remove(tagFragment);
        }

        if (!fragment.isAdded()) {//判断制定的Fragment是否已经添加到Manager中,若没有,则添加
            transaction.add(containerResId, fragment, tag);
        }
        transaction.show(fragment);
        this.currentVisibleFragment = fragment;

        transaction.commitAllowingStateLoss();//显示并提交
    }

    public static void init(FragmentManager supportFragmentManager, @IdRes final int fragmentContainerId, RadioGroup radioGroup, @IdRes final int[] radioButtonIds, final Fragment[] fragments, int firstVisibleIndex, final int mode) {
        final FragmentSwitchUtil fragmentSwitchUtil = new FragmentSwitchUtil(supportFragmentManager, fragmentContainerId);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                int index = 0;
                for (int i = 0; i < radioButtonIds.length; i++) {
                    if (checkedId == radioButtonIds[i]) {
                        index = i;
                        break;
                    }
                }

                String tag = FragmentSwitchUtil.class.getSimpleName() + "_tag_" + checkedId;
                fragmentSwitchUtil.switchFragment(fragments[index], mode, tag);
            }
        });

        RadioButton radioButton = radioGroup.findViewById(radioButtonIds[firstVisibleIndex]);
        radioButton.setChecked(true);
    }

    public static void init(FragmentManager supportFragmentManager, @IdRes final int fragmentContainerId, NestRadioGroup radioGroup, @IdRes final int[] radioButtonIds, final Fragment[] fragments, int firstVisibleIndex, final int mode) {
        final FragmentSwitchUtil fragmentSwitchUtil = new FragmentSwitchUtil(supportFragmentManager, fragmentContainerId);
        radioGroup.setOnCheckedChangeListener(new NestRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(NestRadioGroup group, int checkedId) {
                int index = 0;
                for (int i = 0; i < radioButtonIds.length; i++) {
                    if (checkedId == radioButtonIds[i]) {
                        index = i;
                        break;
                    }
                }

                String tag = FragmentSwitchUtil.class.getSimpleName() + "_tag_" + checkedId;
                fragmentSwitchUtil.switchFragment(fragments[index], mode, tag);
            }
        });

        RadioButton radioButton = radioGroup.findViewById(radioButtonIds[firstVisibleIndex]);
        radioButton.setChecked(true);
    }
}
