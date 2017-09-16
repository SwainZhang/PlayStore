package com.emery.test.playstore;


import android.view.View;

import base.LoadingPager;
import base.SimpleTitleActivity;
import holder.LeftSettingHolder;

/**
 * Created by MyPC on 2016/12/9.
 */

public class LeftSettingActivity extends SimpleTitleActivity {

    @Override
    public LoadingPager.LoadingDataResult onLoadData() {
        return LoadingPager.LoadingDataResult.SUCCESS;
    }

    @Override
    public View onSuccessView() {
        return new LeftSettingHolder().getHolderView();
    }
}
