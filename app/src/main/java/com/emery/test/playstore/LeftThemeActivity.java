package com.emery.test.playstore;

import android.view.View;

import base.LoadingPager;
import base.SimpleTitleActivity;
import holder.LeftThemeHolder;

/**
 * Created by MyPC on 2017/3/25.
 */

public class LeftThemeActivity extends SimpleTitleActivity {
    @Override
    public LoadingPager.LoadingDataResult onLoadData() {
        return LoadingPager.LoadingDataResult.SUCCESS;
    }

    @Override
    public View onSuccessView() {
        return new LeftThemeHolder().getHolderView();
    }
}
