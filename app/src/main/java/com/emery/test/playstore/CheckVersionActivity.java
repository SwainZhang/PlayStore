package com.emery.test.playstore;

import android.view.View;

import base.LoadingPager;
import base.SimpleTitleActivity;
import holder.CheckVersionHolder;

/**
 * Created by MyPC on 2016/12/20.
 */

public class CheckVersionActivity extends SimpleTitleActivity {
    @Override
    public LoadingPager.LoadingDataResult onLoadData() {
        return LoadingPager.LoadingDataResult.SUCCESS;
    }

    @Override
    public View onSuccessView() {
        return new CheckVersionHolder().getHolderView();
    }
}
