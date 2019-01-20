package com.youzi.youziwallpaper.app.ui.activities;

import com.youzi.framework.base.BaseMvpActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.youzi.youziwallpaper.R;
import com.youzi.youziwallpaper.app.mvp.contracts.EditPersonInfoActivityContract;
import com.youzi.youziwallpaper.di.DaggerAppComponent;

public class EditPersonInfoActivity extends BaseMvpActivity<EditPersonInfoActivityContract.Presenter> implements EditPersonInfoActivityContract.View {

    @Override
    protected void daggerInject() {
        DaggerAppComponent.create().inject(this);
    }

    @Override
    protected View provideLayoutView() {
        return inflate(R.layout.activity_edit_person_info);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView save = new TextView(this);
        save.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT));
        save.setText("保存");
        save.setPadding(0, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics()),
                0);
        save.setTextColor(16);
        save.setTextColor(0xffFA742B);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        getToolbar().addRightButton(save);

    }

    void save() {

    }
}